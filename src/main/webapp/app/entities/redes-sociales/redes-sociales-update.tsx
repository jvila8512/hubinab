import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEcosistema } from 'app/shared/model/ecosistema.model';
import { getEntities as getEcosistemas } from 'app/entities/ecosistema/ecosistema.reducer';
import { IRedesSociales } from 'app/shared/model/redes-sociales.model';
import { getEntity, updateEntity, createEntity, reset } from './redes-sociales.reducer';

export const RedesSocialesUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const ecosistemas = useAppSelector(state => state.ecosistema.entities);
  const redesSocialesEntity = useAppSelector(state => state.redesSociales.entity);
  const loading = useAppSelector(state => state.redesSociales.loading);
  const updating = useAppSelector(state => state.redesSociales.updating);
  const updateSuccess = useAppSelector(state => state.redesSociales.updateSuccess);
  const handleClose = () => {
    props.history.push('/nomencladores/redes-sociales');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getEcosistemas({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...redesSocialesEntity,
      ...values,
      ecosistema: ecosistemas.find(it => it.id.toString() === values.ecosistema.toString()),
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
          ...redesSocialesEntity,
          ecosistema: redesSocialesEntity?.ecosistema?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhipsterApp.redesSociales.home.createOrEditLabel" data-cy="RedesSocialesCreateUpdateHeading">
            <Translate contentKey="jhipsterApp.redesSociales.home.createOrEditLabel">Create or edit a RedesSociales</Translate>
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
                  id="redes-sociales-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('jhipsterApp.redesSociales.redesUrl')}
                id="redes-sociales-redesUrl"
                name="redesUrl"
                data-cy="redesUrl"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedBlobField
                label={translate('jhipsterApp.redesSociales.logoUrl')}
                id="redes-sociales-logoUrl"
                name="logoUrl"
                data-cy="logoUrl"
                isImage
                accept="image/*"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="redes-sociales-ecosistema"
                name="ecosistema"
                data-cy="ecosistema"
                label={translate('jhipsterApp.redesSociales.ecosistema')}
                type="select"
              >
                <option value="" key="0" />
                {ecosistemas
                  ? ecosistemas.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/nomencladores/redes-sociales"
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

export default RedesSocialesUpdate;
