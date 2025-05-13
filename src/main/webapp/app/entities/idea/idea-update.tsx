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
import { IReto } from 'app/shared/model/reto.model';
import { getEntities as getRetos } from 'app/entities/reto/reto.reducer';
import { ITipoIdea } from 'app/shared/model/tipo-idea.model';
import { getEntities as getTipoIdeas } from 'app/entities/tipo-idea/tipo-idea.reducer';
import { IEntidad } from 'app/shared/model/entidad.model';
import { getEntities as getEntidads } from 'app/entities/entidad/entidad.reducer';
import { IIdea } from 'app/shared/model/idea.model';
import { getEntity, updateEntity, createEntity, reset } from './idea.reducer';

export const IdeaUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const users = useAppSelector(state => state.userManagement.users);
  const retos = useAppSelector(state => state.reto.entities);
  const tipoIdeas = useAppSelector(state => state.tipoIdea.entities);
  const entidads = useAppSelector(state => state.entidad.entities);
  const ideaEntity = useAppSelector(state => state.idea.entity);
  const loading = useAppSelector(state => state.idea.loading);
  const updating = useAppSelector(state => state.idea.updating);
  const updateSuccess = useAppSelector(state => state.idea.updateSuccess);

  const handleClose = () => {
    props.history.push('/entidad/idea' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getUsers({}));
    dispatch(getRetos({}));
    dispatch(getTipoIdeas({}));
    dispatch(getEntidads({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...ideaEntity,
      ...values,
      user: users.find(it => it.id.toString() === values.user.toString()),
      reto: retos.find(it => it.id.toString() === values.reto.toString()),
      tipoIdea: tipoIdeas.find(it => it.id.toString() === values.tipoIdea.toString()),
      entidad: entidads.find(it => it.id.toString() === values.entidad.toString()),
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
          ...ideaEntity,
          user: ideaEntity?.user?.id,
          reto: ideaEntity?.reto?.id,
          tipoIdea: ideaEntity?.tipoIdea?.id,
          entidad: ideaEntity?.entidad?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhipsterApp.idea.home.createOrEditLabel" data-cy="IdeaCreateUpdateHeading">
            <Translate contentKey="jhipsterApp.idea.home.createOrEditLabel">Create or edit a Idea</Translate>
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
                  id="idea-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('jhipsterApp.idea.numeroRegistro')}
                id="idea-numeroRegistro"
                name="numeroRegistro"
                data-cy="numeroRegistro"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('jhipsterApp.idea.titulo')}
                id="idea-titulo"
                name="titulo"
                data-cy="titulo"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterApp.idea.descripcion')}
                id="idea-descripcion"
                name="descripcion"
                data-cy="descripcion"
                type="textarea"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterApp.idea.autor')}
                id="idea-autor"
                name="autor"
                data-cy="autor"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterApp.idea.fechaInscripcion')}
                id="idea-fechaInscripcion"
                name="fechaInscripcion"
                data-cy="fechaInscripcion"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField label={translate('jhipsterApp.idea.visto')} id="idea-visto" name="visto" data-cy="visto" type="text" />
              <ValidatedBlobField
                label={translate('jhipsterApp.idea.foto')}
                id="idea-foto"
                name="foto"
                data-cy="foto"
                isImage
                accept="image/*"
              />
              <ValidatedField
                label={translate('jhipsterApp.idea.aceptada')}
                id="idea-aceptada"
                name="aceptada"
                data-cy="aceptada"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('jhipsterApp.idea.publica')}
                id="idea-publica"
                name="publica"
                data-cy="publica"
                check
                type="checkbox"
              />
              <ValidatedField id="idea-user" name="user" data-cy="user" label={translate('jhipsterApp.idea.user')} type="select">
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.login}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="idea-reto" name="reto" data-cy="reto" label={translate('jhipsterApp.idea.reto')} type="select">
                <option value="" key="0" />
                {retos
                  ? retos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.reto}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="idea-tipoIdea"
                name="tipoIdea"
                data-cy="tipoIdea"
                label={translate('jhipsterApp.idea.tipoIdea')}
                type="select"
              >
                <option value="" key="0" />
                {tipoIdeas
                  ? tipoIdeas.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.tipoIdea}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="idea-entidad"
                name="entidad"
                data-cy="entidad"
                label={translate('jhipsterApp.idea.entidad')}
                type="select"
              >
                <option value="" key="0" />
                {entidads
                  ? entidads.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.entidad}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="entidad/idea" replace color="info">
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

export default IdeaUpdate;
