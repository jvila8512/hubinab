import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './tipo-contacto.reducer';

export const TipoContactoDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const tipoContactoEntity = useAppSelector(state => state.tipoContacto.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="tipoContactoDetailsHeading">
          <Translate contentKey="jhipsterApp.tipoContacto.detail.title">TipoContacto</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{tipoContactoEntity.id}</dd>
          <dt>
            <span id="tipoContacto">
              <Translate contentKey="jhipsterApp.tipoContacto.tipoContacto">Tipo Contacto</Translate>
            </span>
          </dt>
          <dd>{tipoContactoEntity.tipoContacto}</dd>
        </dl>
        <Button tag={Link} to="/nomencladores/tipo-contacto" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/nomencladores/tipo-contacto/${tipoContactoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TipoContactoDetail;
