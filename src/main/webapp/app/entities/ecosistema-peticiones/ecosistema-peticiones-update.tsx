import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IEcosistema } from 'app/shared/model/ecosistema.model';
import { getEntities as getEcosistemas } from 'app/entities/ecosistema/ecosistema.reducer';
import { IEcosistemaPeticiones } from 'app/shared/model/ecosistema-peticiones.model';
import { getEntity, updateEntity, createEntity, reset } from './ecosistema-peticiones.reducer';

export const EcosistemaPeticionesUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const users = useAppSelector(state => state.userManagement.users);
  const ecosistemas = useAppSelector(state => state.ecosistema.entities);
  const ecosistemaPeticionesEntity = useAppSelector(state => state.ecosistemaPeticiones.entity);
  const loading = useAppSelector(state => state.ecosistemaPeticiones.loading);
  const updating = useAppSelector(state => state.ecosistemaPeticiones.updating);
  const updateSuccess = useAppSelector(state => state.ecosistemaPeticiones.updateSuccess);
  const handleClose = () => {
    props.history.push('/nomencladores/ecosistema-peticiones');
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
      ...ecosistemaPeticionesEntity,
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
          ...ecosistemaPeticionesEntity,
          user: ecosistemaPeticionesEntity?.user?.id,
          ecosistema: ecosistemaPeticionesEntity?.ecosistema?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhipsterApp.ecosistemaPeticiones.home.createOrEditLabel" data-cy="EcosistemaPeticionesCreateUpdateHeading">
            <Translate contentKey="jhipsterApp.ecosistemaPeticiones.home.createOrEditLabel">
              Create or edit a EcosistemaPeticiones
            </Translate>
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
                  id="ecosistema-peticiones-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('jhipsterApp.ecosistemaPeticiones.motivo')}
                id="ecosistema-peticiones-motivo"
                name="motivo"
                data-cy="motivo"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterApp.ecosistemaPeticiones.fechaPeticion')}
                id="ecosistema-peticiones-fechaPeticion"
                name="fechaPeticion"
                data-cy="fechaPeticion"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterApp.ecosistemaPeticiones.aprobada')}
                id="ecosistema-peticiones-aprobada"
                name="aprobada"
                data-cy="aprobada"
                check
                type="checkbox"
              />
              <ValidatedField
                id="ecosistema-peticiones-user"
                name="user"
                data-cy="user"
                label={translate('jhipsterApp.ecosistemaPeticiones.user')}
                type="select"
              >
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
                id="ecosistema-peticiones-ecosistema"
                name="ecosistema"
                data-cy="ecosistema"
                label={translate('jhipsterApp.ecosistemaPeticiones.ecosistema')}
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
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/nomencladores/ecosistema-peticiones"
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

export default EcosistemaPeticionesUpdate;
