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
import { ILikeIdea } from 'app/shared/model/like-idea.model';
import { getEntity, updateEntity, createEntity, reset } from './like-idea.reducer';

export const LikeIdeaUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const users = useAppSelector(state => state.userManagement.users);
  const ideas = useAppSelector(state => state.idea.entities);
  const likeIdeaEntity = useAppSelector(state => state.likeIdea.entity);
  const loading = useAppSelector(state => state.likeIdea.loading);
  const updating = useAppSelector(state => state.likeIdea.updating);
  const updateSuccess = useAppSelector(state => state.likeIdea.updateSuccess);
  const handleClose = () => {
    props.history.push('/entidad/like-idea');
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
      ...likeIdeaEntity,
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
          ...likeIdeaEntity,
          user: likeIdeaEntity?.user?.id,
          idea: likeIdeaEntity?.idea?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhipsterApp.likeIdea.home.createOrEditLabel" data-cy="LikeIdeaCreateUpdateHeading">
            <Translate contentKey="jhipsterApp.likeIdea.home.createOrEditLabel">Create or edit a LikeIdea</Translate>
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
                  id="like-idea-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('jhipsterApp.likeIdea.like')}
                id="like-idea-like"
                name="like"
                data-cy="like"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('jhipsterApp.likeIdea.fechaInscripcion')}
                id="like-idea-fechaInscripcion"
                name="fechaInscripcion"
                data-cy="fechaInscripcion"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField id="like-idea-user" name="user" data-cy="user" label={translate('jhipsterApp.likeIdea.user')} type="select">
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.login}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="like-idea-idea" name="idea" data-cy="idea" label={translate('jhipsterApp.likeIdea.idea')} type="select">
                <option value="" key="0" />
                {ideas
                  ? ideas.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.titulo}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/entidad/like-idea" replace color="info">
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

export default LikeIdeaUpdate;
