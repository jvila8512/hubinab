import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './like-idea.reducer';

export const LikeIdeaDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const likeIdeaEntity = useAppSelector(state => state.likeIdea.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="likeIdeaDetailsHeading">
          <Translate contentKey="jhipsterApp.likeIdea.detail.title">LikeIdea</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{likeIdeaEntity.id}</dd>
          <dt>
            <span id="like">
              <Translate contentKey="jhipsterApp.likeIdea.like">Like</Translate>
            </span>
          </dt>
          <dd>{likeIdeaEntity.like}</dd>
          <dt>
            <span id="fechaInscripcion">
              <Translate contentKey="jhipsterApp.likeIdea.fechaInscripcion">Fecha Inscripcion</Translate>
            </span>
          </dt>
          <dd>
            {likeIdeaEntity.fechaInscripcion ? (
              <TextFormat value={likeIdeaEntity.fechaInscripcion} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="jhipsterApp.likeIdea.user">User</Translate>
          </dt>
          <dd>{likeIdeaEntity.user ? likeIdeaEntity.user.login : ''}</dd>
          <dt>
            <Translate contentKey="jhipsterApp.likeIdea.idea">Idea</Translate>
          </dt>
          <dd>{likeIdeaEntity.idea ? likeIdeaEntity.idea.titulo : ''}</dd>
        </dl>
        <Button tag={Link} to="/like-idea" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/like-idea/${likeIdeaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default LikeIdeaDetail;
