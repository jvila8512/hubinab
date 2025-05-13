import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './comenetarios-idea.reducer';

export const ComenetariosIdeaDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const comenetariosIdeaEntity = useAppSelector(state => state.comenetariosIdea.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="comenetariosIdeaDetailsHeading">
          <Translate contentKey="jhipsterApp.comenetariosIdea.detail.title">ComenetariosIdea</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{comenetariosIdeaEntity.id}</dd>
          <dt>
            <span id="comentario">
              <Translate contentKey="jhipsterApp.comenetariosIdea.comentario">Comentario</Translate>
            </span>
          </dt>
          <dd>{comenetariosIdeaEntity.comentario}</dd>
          <dt>
            <span id="fechaInscripcion">
              <Translate contentKey="jhipsterApp.comenetariosIdea.fechaInscripcion">Fecha Inscripcion</Translate>
            </span>
          </dt>
          <dd>
            {comenetariosIdeaEntity.fechaInscripcion ? (
              <TextFormat value={comenetariosIdeaEntity.fechaInscripcion} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="jhipsterApp.comenetariosIdea.user">User</Translate>
          </dt>
          <dd>{comenetariosIdeaEntity.user ? comenetariosIdeaEntity.user.login : ''}</dd>
          <dt>
            <Translate contentKey="jhipsterApp.comenetariosIdea.idea">Idea</Translate>
          </dt>
          <dd>{comenetariosIdeaEntity.idea ? comenetariosIdeaEntity.idea.titulo : ''}</dd>
        </dl>
        <Button tag={Link} to="/comenetarios-idea" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/comenetarios-idea/${comenetariosIdeaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ComenetariosIdeaDetail;
