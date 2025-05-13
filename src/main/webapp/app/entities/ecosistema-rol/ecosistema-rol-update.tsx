import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEcosistemaRol } from 'app/shared/model/ecosistema-rol.model';
import { getEntity, updateEntity, createEntity, reset } from './ecosistema-rol.reducer';

export const EcosistemaRolUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const ecosistemaRolEntity = useAppSelector(state => state.ecosistemaRol.entity);
  const loading = useAppSelector(state => state.ecosistemaRol.loading);
  const updating = useAppSelector(state => state.ecosistemaRol.updating);
  const updateSuccess = useAppSelector(state => state.ecosistemaRol.updateSuccess);
  const handleClose = () => {
    props.history.push('/nomencladores/ecosistema-rol');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...ecosistemaRolEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...ecosistemaRolEntity,
        };

  return (
    <div className="mt-6">
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhipsterApp.ecosistemaRol.home.createOrEditLabel" data-cy="EcosistemaRolCreateUpdateHeading">
            <Translate contentKey="jhipsterApp.ecosistemaRol.home.createOrEditLabel">Create or edit a EcosistemaRol</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Cargando...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="ecosistema-rol-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('jhipsterApp.ecosistemaRol.ecosistemaRol')}
                id="ecosistema-rol-ecosistemaRol"
                name="ecosistemaRol"
                data-cy="ecosistemaRol"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterApp.ecosistemaRol.descripcion')}
                id="ecosistema-rol-descripcion"
                name="descripcion"
                data-cy="descripcion"
                type="textarea"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/nomencladores/ecosistema-rol"
                replace
                color="info"
              >
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default EcosistemaRolUpdate;
