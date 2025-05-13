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
import { ITipoNoticia } from 'app/shared/model/tipo-noticia.model';
import { getEntities as getTipoNoticias } from 'app/entities/tipo-noticia/tipo-noticia.reducer';
import { INoticias } from 'app/shared/model/noticias.model';
import { getEntity, updateEntity, createEntity, reset } from './noticias.reducer';

export const NoticiasUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const users = useAppSelector(state => state.userManagement.users);
  const ecosistemas = useAppSelector(state => state.ecosistema.entities);
  const tipoNoticias = useAppSelector(state => state.tipoNoticia.entities);
  const noticiasEntity = useAppSelector(state => state.noticias.entity);
  const loading = useAppSelector(state => state.noticias.loading);
  const updating = useAppSelector(state => state.noticias.updating);
  const updateSuccess = useAppSelector(state => state.noticias.updateSuccess);
  const handleClose = () => {
    props.history.push('/entidad/noticias' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getUsers({}));
    dispatch(getEcosistemas({}));
    dispatch(getTipoNoticias({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...noticiasEntity,
      ...values,
      user: users.find(it => it.id.toString() === values.user.toString()),
      ecosistema: ecosistemas.find(it => it.id.toString() === values.ecosistema.toString()),
      tipoNoticia: tipoNoticias.find(it => it.id.toString() === values.tipoNoticia.toString()),
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
          ...noticiasEntity,
          user: noticiasEntity?.user?.id,
          ecosistema: noticiasEntity?.ecosistema?.id,
          tipoNoticia: noticiasEntity?.tipoNoticia?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhipsterApp.noticias.home.createOrEditLabel" data-cy="NoticiasCreateUpdateHeading">
            <Translate contentKey="jhipsterApp.noticias.home.createOrEditLabel">Create or edit a Noticias</Translate>
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
                  id="noticias-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('jhipsterApp.noticias.titulo')}
                id="noticias-titulo"
                name="titulo"
                data-cy="titulo"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterApp.noticias.descripcion')}
                id="noticias-descripcion"
                name="descripcion"
                data-cy="descripcion"
                type="textarea"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedBlobField
                label={translate('jhipsterApp.noticias.urlFoto')}
                id="noticias-urlFoto"
                name="urlFoto"
                data-cy="urlFoto"
                isImage
                accept="image/*"
              />
              <ValidatedField
                label={translate('jhipsterApp.noticias.publica')}
                id="noticias-publica"
                name="publica"
                data-cy="publica"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('jhipsterApp.noticias.publicar')}
                id="noticias-publicar"
                name="publicar"
                data-cy="publicar"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('jhipsterApp.noticias.fechaCreada')}
                id="noticias-fechaCreada"
                name="fechaCreada"
                data-cy="fechaCreada"
                type="date"
              />
              <ValidatedField id="noticias-user" name="user" data-cy="user" label={translate('jhipsterApp.noticias.user')} type="select">
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
                id="noticias-ecosistema"
                name="ecosistema"
                data-cy="ecosistema"
                label={translate('jhipsterApp.noticias.ecosistema')}
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
              <ValidatedField
                id="noticias-tipoNoticia"
                name="tipoNoticia"
                data-cy="tipoNoticia"
                label={translate('jhipsterApp.noticias.tipoNoticia')}
                type="select"
              >
                <option value="" key="0" />
                {tipoNoticias
                  ? tipoNoticias.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.tipoNoticia}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/entidad/noticias" replace color="info">
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

export default NoticiasUpdate;
