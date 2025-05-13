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
import { IIdea } from 'app/shared/model/idea.model';
import { getEntities as getIdeas } from 'app/entities/idea/idea.reducer';
import { IComenetariosIdea } from 'app/shared/model/comenetarios-idea.model';
import { getEntity, updateEntity, createEntity, reset } from './comenetarios-idea.reducer';

export const ComenetariosIdeaUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const users = useAppSelector(state => state.userManagement.users);
  const ideas = useAppSelector(state => state.idea.entities);
  const comenetariosIdeaEntity = useAppSelector(state => state.comenetariosIdea.entity);
  const loading = useAppSelector(state => state.comenetariosIdea.loading);
  const updating = useAppSelector(state => state.comenetariosIdea.updating);
  const updateSuccess = useAppSelector(state => state.comenetariosIdea.updateSuccess);
  const handleClose = () => {
    props.history.push('/comenetarios-idea');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getUsers({}));
    dispatch(getIdeas({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...comenetariosIdeaEntity,
      ...values,
      user: users.find(it => it.id.toString() === values.user.toString()),
      idea: ideas.find(it => it.id.toString() === values.idea.toString()),
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
          ...comenetariosIdeaEntity,
          user: comenetariosIdeaEntity?.user?.id,
          idea: comenetariosIdeaEntity?.idea?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhipsterApp.comenetariosIdea.home.createOrEditLabel" data-cy="ComenetariosIdeaCreateUpdateHeading">
            <Translate contentKey="jhipsterApp.comenetariosIdea.home.createOrEditLabel">Create or edit a ComenetariosIdea</Translate>
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
                  id="comenetarios-idea-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('jhipsterApp.comenetariosIdea.comentario')}
                id="comenetarios-idea-comentario"
                name="comentario"
                data-cy="comentario"
                type="textarea"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterApp.comenetariosIdea.fechaInscripcion')}
                id="comenetarios-idea-fechaInscripcion"
                name="fechaInscripcion"
                data-cy="fechaInscripcion"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="comenetarios-idea-user"
                name="user"
                data-cy="user"
                label={translate('jhipsterApp.comenetariosIdea.user')}
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
                id="comenetarios-idea-idea"
                name="idea"
                data-cy="idea"
                label={translate('jhipsterApp.comenetariosIdea.idea')}
                type="select"
              >
                <option value="" key="0" />
                {ideas
                  ? ideas.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.titulo}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/comenetarios-idea" replace color="info">
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

export default ComenetariosIdeaUpdate;
