import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './participantes.reducer';

export const ParticipantesDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const participantesEntity = useAppSelector(state => state.participantes.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="participantesDetailsHeading">
          <Translate contentKey="jhipsterApp.participantes.detail.title">Participantes</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{participantesEntity.id}</dd>
          <dt>
            <span id="nombre">
              <Translate contentKey="jhipsterApp.participantes.nombre">Nombre</Translate>
            </span>
          </dt>
          <dd>{participantesEntity.nombre}</dd>
          <dt>
            <span id="telefono">
              <Translate contentKey="jhipsterApp.participantes.telefono">Telefono</Translate>
            </span>
          </dt>
          <dd>{participantesEntity.telefono}</dd>
          <dt>
            <span id="correo">
              <Translate contentKey="jhipsterApp.participantes.correo">Correo</Translate>
            </span>
          </dt>
          <dd>{participantesEntity.correo}</dd>
          <dt>
            <Translate contentKey="jhipsterApp.participantes.proyectos">Proyectos</Translate>
          </dt>
          <dd>{participantesEntity.proyectos ? participantesEntity.proyectos.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/participantes" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/participantes/${participantesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ParticipantesDetail;
