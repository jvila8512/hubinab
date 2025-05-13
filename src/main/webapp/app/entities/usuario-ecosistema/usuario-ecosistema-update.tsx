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
import { ICategoriaUser } from 'app/shared/model/categoria-user.model';
import { getEntities as getCategoriaUsers } from 'app/entities/categoria-user/categoria-user.reducer';
import { IEcosistema } from 'app/shared/model/ecosistema.model';
import { getEntities as getEcosistemas } from 'app/entities/ecosistema/ecosistema.reducer';
import { IUsuarioEcosistema } from 'app/shared/model/usuario-ecosistema.model';
import { getEntity, updateEntity, createEntity, reset } from './usuario-ecosistema.reducer';

export const UsuarioEcosistemaUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const users = useAppSelector(state => state.userManagement.users);
  const categoriaUsers = useAppSelector(state => state.categoriaUser.entities);
  const ecosistemas = useAppSelector(state => state.ecosistema.entities);
  const usuarioEcosistemaEntity = useAppSelector(state => state.usuarioEcosistema.entity);
  const loading = useAppSelector(state => state.usuarioEcosistema.loading);
  const updating = useAppSelector(state => state.usuarioEcosistema.updating);
  const updateSuccess = useAppSelector(state => state.usuarioEcosistema.updateSuccess);
  const handleClose = () => {
    props.history.push('/entidad/usuario-ecosistema');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getUsers({}));
    dispatch(getCategoriaUsers({}));
    dispatch(getEcosistemas({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...usuarioEcosistemaEntity,
      ...values,
      ecosistemas: mapIdList(values.ecosistemas),
      user: users.find(it => it.id.toString() === values.user.toString()),
      categoriaUser: categoriaUsers.find(it => it.id.toString() === values.categoriaUser.toString()),
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
          ...usuarioEcosistemaEntity,
          user: usuarioEcosistemaEntity?.user?.id,
          categoriaUser: usuarioEcosistemaEntity?.categoriaUser?.id,
          ecosistemas: usuarioEcosistemaEntity?.ecosistemas?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhipsterApp.usuarioEcosistema.home.createOrEditLabel" data-cy="UsuarioEcosistemaCreateUpdateHeading">
            <Translate contentKey="jhipsterApp.usuarioEcosistema.home.createOrEditLabel">Create or edit a UsuarioEcosistema</Translate>
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
                  id="usuario-ecosistema-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('jhipsterApp.usuarioEcosistema.fechaIngreso')}
                id="usuario-ecosistema-fechaIngreso"
                name="fechaIngreso"
                data-cy="fechaIngreso"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterApp.usuarioEcosistema.bloqueado')}
                id="usuario-ecosistema-bloqueado"
                name="bloqueado"
                data-cy="bloqueado"
                check
                type="checkbox"
              />
              <ValidatedField
                id="usuario-ecosistema-user"
                name="user"
                data-cy="user"
                label={translate('jhipsterApp.usuarioEcosistema.user')}
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
                id="usuario-ecosistema-categoriaUser"
                name="categoriaUser"
                data-cy="categoriaUser"
                label={translate('jhipsterApp.usuarioEcosistema.categoriaUser')}
                type="select"
              >
                <option value="" key="0" />
                {categoriaUsers
                  ? categoriaUsers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.categoriaUser}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label={translate('jhipsterApp.usuarioEcosistema.ecosistema')}
                id="usuario-ecosistema-ecosistema"
                data-cy="ecosistema"
                type="select"
                multiple
                name="ecosistemas"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/entidad/usuario-ecosistema" replace color="info">
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

export default UsuarioEcosistemaUpdate;
