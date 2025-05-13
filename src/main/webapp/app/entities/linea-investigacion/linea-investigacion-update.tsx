import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IProyectos } from 'app/shared/model/proyectos.model';
import { getEntities as getProyectos } from 'app/entities/proyectos/proyectos.reducer';
import { ILineaInvestigacion } from 'app/shared/model/linea-investigacion.model';
import { getEntity, updateEntity, createEntity, reset } from './linea-investigacion.reducer';

export const LineaInvestigacionUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const proyectos = useAppSelector(state => state.proyectos.entities);
  const lineaInvestigacionEntity = useAppSelector(state => state.lineaInvestigacion.entity);
  const loading = useAppSelector(state => state.lineaInvestigacion.loading);
  const updating = useAppSelector(state => state.lineaInvestigacion.updating);
  const updateSuccess = useAppSelector(state => state.lineaInvestigacion.updateSuccess);
  const handleClose = () => {
    props.history.push('/nomencladores/linea-investigacion');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getProyectos({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...lineaInvestigacionEntity,
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
          ...lineaInvestigacionEntity,
        };

  return (
    <div className="mt-6">
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhipsterApp.lineaInvestigacion.home.createOrEditLabel" data-cy="LineaInvestigacionCreateUpdateHeading">
            <Translate contentKey="jhipsterApp.lineaInvestigacion.home.createOrEditLabel">Create or edit a LineaInvestigacion</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="linea-investigacion-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('jhipsterApp.lineaInvestigacion.linea')}
                id="linea-investigacion-linea"
                name="linea"
                data-cy="linea"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/nomencladores/linea-investigacion"
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

export default LineaInvestigacionUpdate;
