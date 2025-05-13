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
import { IEcosistemaRol } from 'app/shared/model/ecosistema-rol.model';
import { getEntities as getEcosistemaRols } from 'app/entities/ecosistema-rol/ecosistema-rol.reducer';
import { IUsuarioEcosistema } from 'app/shared/model/usuario-ecosistema.model';
import { getEntities as getUsuarioEcosistemas } from 'app/entities/usuario-ecosistema/usuario-ecosistema.reducer';
import { IEcosistema } from 'app/shared/model/ecosistema.model';
import { getEntity, updateEntity, createEntity, reset } from './ecosistema.reducer';

export const EcosistemaUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const users = useAppSelector(state => state.userManagement.users);
  const ecosistemaRols = useAppSelector(state => state.ecosistemaRol.entities);
  const usuarioEcosistemas = useAppSelector(state => state.usuarioEcosistema.entities);
  const ecosistemaEntity = useAppSelector(state => state.ecosistema.entity);
  const loading = useAppSelector(state => state.ecosistema.loading);
  const updating = useAppSelector(state => state.ecosistema.updating);
  const updateSuccess = useAppSelector(state => state.ecosistema.updateSuccess);
  const handleClose = () => {
    props.history.push('/ecosistema');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getUsers({}));
    dispatch(getEcosistemaRols({}));
    dispatch(getUsuarioEcosistemas({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...ecosistemaEntity,
      ...values,
      users: mapIdList(values.users),
      ecosistemaRol: ecosistemaRols.find(it => it.id.toString() === values.ecosistemaRol.toString()),
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
          ...ecosistemaEntity,
          users: ecosistemaEntity?.users?.map(e => e.id.toString()),
          ecosistemaRol: ecosistemaEntity?.ecosistemaRol?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhipsterApp.ecosistema.home.createOrEditLabel" data-cy="EcosistemaCreateUpdateHeading">
            <Translate contentKey="jhipsterApp.ecosistema.home.createOrEditLabel">Create or edit a Ecosistema</Translate>
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
                  id="ecosistema-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('jhipsterApp.ecosistema.nombre')}
                id="ecosistema-nombre"
                name="nombre"
                data-cy="nombre"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  maxLength: { value: 100, message: translate('entity.validation.maxlength', { max: 100 }) },
                }}
              />
              <ValidatedField
                label={translate('jhipsterApp.ecosistema.tematica')}
                id="ecosistema-tematica"
                name="tematica"
                data-cy="tematica"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterApp.ecosistema.activo')}
                id="ecosistema-activo"
                name="activo"
                data-cy="activo"
                check
                type="checkbox"
              />
              <ValidatedBlobField
                label={translate('jhipsterApp.ecosistema.logoUrl')}
                id="ecosistema-logoUrl"
                name="logoUrl"
                data-cy="logoUrl"
                isImage
                accept="image/*"
              />
              <ValidatedField
                label={translate('jhipsterApp.ecosistema.ranking')}
                id="ecosistema-ranking"
                name="ranking"
                data-cy="ranking"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterApp.ecosistema.usuariosCant')}
                id="ecosistema-usuariosCant"
                name="usuariosCant"
                data-cy="usuariosCant"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterApp.ecosistema.retosCant')}
                id="ecosistema-retosCant"
                name="retosCant"
                data-cy="retosCant"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterApp.ecosistema.ideasCant')}
                id="ecosistema-ideasCant"
                name="ideasCant"
                data-cy="ideasCant"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterApp.ecosistema.user')}
                id="ecosistema-user"
                data-cy="user"
                type="select"
                multiple
                name="users"
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
                id="ecosistema-ecosistemaRol"
                name="ecosistemaRol"
                data-cy="ecosistemaRol"
                label={translate('jhipsterApp.ecosistema.ecosistemaRol')}
                type="select"
              >
                <option value="" key="0" />
                {ecosistemaRols
                  ? ecosistemaRols.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.ecosistemaRol}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/ecosistema" replace color="info">
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

export default EcosistemaUpdate;
