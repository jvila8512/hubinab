import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './ecosistema-peticiones.reducer';

export const EcosistemaPeticionesDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const ecosistemaPeticionesEntity = useAppSelector(state => state.ecosistemaPeticiones.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="ecosistemaPeticionesDetailsHeading">
          <Translate contentKey="jhipsterApp.ecosistemaPeticiones.detail.title">EcosistemaPeticiones</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{ecosistemaPeticionesEntity.id}</dd>
          <dt>
            <span id="motivo">
              <Translate contentKey="jhipsterApp.ecosistemaPeticiones.motivo">Motivo</Translate>
            </span>
          </dt>
          <dd>{ecosistemaPeticionesEntity.motivo}</dd>
          <dt>
            <span id="fechaPeticion">
              <Translate contentKey="jhipsterApp.ecosistemaPeticiones.fechaPeticion">Fecha Peticion</Translate>
            </span>
          </dt>
          <dd>
            {ecosistemaPeticionesEntity.fechaPeticion ? (
              <TextFormat value={ecosistemaPeticionesEntity.fechaPeticion} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="aprobada">
              <Translate contentKey="jhipsterApp.ecosistemaPeticiones.aprobada">Aprobada</Translate>
            </span>
          </dt>
          <dd>{ecosistemaPeticionesEntity.aprobada ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="jhipsterApp.ecosistemaPeticiones.user">User</Translate>
          </dt>
          <dd>{ecosistemaPeticionesEntity.user ? ecosistemaPeticionesEntity.user.login : ''}</dd>
          <dt>
            <Translate contentKey="jhipsterApp.ecosistemaPeticiones.ecosistema">Ecosistema</Translate>
          </dt>
          <dd>{ecosistemaPeticionesEntity.ecosistema ? ecosistemaPeticionesEntity.ecosistema.nombre : ''}</dd>
        </dl>
        <Button tag={Link} to="/nomencladores/ecosistema-peticiones" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/nomencladores/ecosistema-peticiones/${ecosistemaPeticionesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default EcosistemaPeticionesDetail;
