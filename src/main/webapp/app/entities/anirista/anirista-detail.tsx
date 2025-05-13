import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './anirista.reducer';

export const AniristaDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const aniristaEntity = useAppSelector(state => state.anirista.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="aniristaDetailsHeading">
          <Translate contentKey="jhipsterApp.anirista.detail.title">Anirista</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{aniristaEntity.id}</dd>
          <dt>
            <span id="nombre">
              <Translate contentKey="jhipsterApp.anirista.nombre">Nombre</Translate>
            </span>
          </dt>
          <dd>{aniristaEntity.nombre}</dd>
          <dt>
            <span id="fechaEntrada">
              <Translate contentKey="jhipsterApp.anirista.fechaEntrada">Fecha Entrada</Translate>
            </span>
          </dt>
          <dd>
            {aniristaEntity.fechaEntrada ? (
              <TextFormat value={aniristaEntity.fechaEntrada} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="descripcion">
              <Translate contentKey="jhipsterApp.anirista.descripcion">Descripcion</Translate>
            </span>
          </dt>
          <dd>{aniristaEntity.descripcion}</dd>
          <dt>
            <Translate contentKey="jhipsterApp.anirista.ecosistema">Ecosistema</Translate>
          </dt>
          <dd>{aniristaEntity.ecosistema ? aniristaEntity.ecosistema.nombre : ''}</dd>
        </dl>
        <Button tag={Link} to="/entidad/anirista" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/entidad/anirista/${aniristaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AniristaDetail;
