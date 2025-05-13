import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './componentes.reducer';

export const ComponentesDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const componentesEntity = useAppSelector(state => state.componentes.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="componentesDetailsHeading">
          <Translate contentKey="jhipsterApp.componentes.detail.title">Componentes</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{componentesEntity.id}</dd>
          <dt>
            <span id="componente">
              <Translate contentKey="jhipsterApp.componentes.componente">Componente</Translate>
            </span>
          </dt>
          <dd>{componentesEntity.componente}</dd>
          <dt>
            <span id="descripcion">
              <Translate contentKey="jhipsterApp.componentes.descripcion">Descripcion</Translate>
            </span>
          </dt>
          <dd>{componentesEntity.descripcion}</dd>
          <dt>
            <span id="activo">
              <Translate contentKey="jhipsterApp.componentes.activo">Activo</Translate>
            </span>
          </dt>
          <dd>{componentesEntity.activo ? 'true' : 'false'}</dd>
        </dl>
        <Button tag={Link} to="/entidad/componentes" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/entidad/componentes/${componentesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ComponentesDetail;
