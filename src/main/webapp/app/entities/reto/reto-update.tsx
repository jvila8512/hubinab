import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IEcosistema } from 'app/shared/model/ecosistema.model';
import { getEntities as getEcosistemas } from 'app/entities/ecosistema/ecosistema.reducer';
import { IReto } from 'app/shared/model/reto.model';
import { getEntity, updateEntity, createEntity, reset } from './reto.reducer';

export const RetoUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const users = useAppSelector(state => state.userManagement.users);
  const ecosistemas = useAppSelector(state => state.ecosistema.entities);
  const retoEntity = useAppSelector(state => state.reto.entity);
  const loading = useAppSelector(state => state.reto.loading);
  const updating = useAppSelector(state => state.reto.updating);
  const updateSuccess = useAppSelector(state => state.reto.updateSuccess);
  const handleClose = () => {
    props.history.push('/entidad/reto' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getUsers({}));
    dispatch(getEcosistemas({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...retoEntity,
      ...values,
      user: users.find(it => it.id.toString() === values.user.toString()),
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
          ...retoEntity,
          user: retoEntity?.user?.id,
          ecosistema: retoEntity?.ecosistema?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhipsterApp.reto.home.createOrEditLabel" data-cy="RetoCreateUpdateHeading">
            <Translate contentKey="jhipsterApp.reto.home.createOrEditLabel">Create or edit a Reto</Translate>
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
                  id="reto-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('jhipsterApp.reto.reto')}
                id="reto-reto"
                name="reto"
                data-cy="reto"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterApp.reto.descripcion')}
                id="reto-descripcion"
                name="descripcion"
                data-cy="descripcion"
                type="textarea"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterApp.reto.motivacion')}
                id="reto-motivacion"
                name="motivacion"
                data-cy="motivacion"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterApp.reto.fechaInicio')}
                id="reto-fechaInicio"
                name="fechaInicio"
                data-cy="fechaInicio"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterApp.reto.fechaFin')}
                id="reto-fechaFin"
                name="fechaFin"
                data-cy="fechaFin"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterApp.reto.activo')}
                id="reto-activo"
                name="activo"
                data-cy="activo"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('jhipsterApp.reto.validado')}
                id="reto-validado"
                name="validado"
                data-cy="validado"
                check
                type="checkbox"
              />
              <ValidatedBlobField
                label={translate('jhipsterApp.reto.urlFoto')}
                id="reto-urlFoto"
                name="urlFoto"
                data-cy="urlFoto"
                isImage
                accept="image/*"
              />
              <ValidatedField label={translate('jhipsterApp.reto.visto')} id="reto-visto" name="visto" data-cy="visto" type="text" />
              <ValidatedField id="reto-user" name="user" data-cy="user" label={translate('jhipsterApp.reto.user')} type="select">
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.login}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="reto-ecosistema"
                name="ecosistema"
                data-cy="ecosistema"
                label={translate('jhipsterApp.reto.ecosistema')}
                type="select"
              >
                <option value="" key="0" />
                {ecosistemas
                  ? ecosistemas.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.nombre}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/entidad/reto" replace color="info">
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

export default RetoUpdate;
