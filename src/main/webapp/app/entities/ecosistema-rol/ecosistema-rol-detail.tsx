import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './ecosistema-rol.reducer';

export const EcosistemaRolDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const ecosistemaRolEntity = useAppSelector(state => state.ecosistemaRol.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="ecosistemaRolDetailsHeading">
          <Translate contentKey="jhipsterApp.ecosistemaRol.detail.title">EcosistemaRol</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{ecosistemaRolEntity.id}</dd>
          <dt>
            <span id="ecosistemaRol">
              <Translate contentKey="jhipsterApp.ecosistemaRol.ecosistemaRol">Ecosistema Rol</Translate>
            </span>
          </dt>
          <dd>{ecosistemaRolEntity.ecosistemaRol}</dd>
          <dt>
            <span id="descripcion">
              <Translate contentKey="jhipsterApp.ecosistemaRol.descripcion">Descripcion</Translate>
            </span>
          </dt>
          <dd>{ecosistemaRolEntity.descripcion}</dd>
        </dl>
        <Button tag={Link} to="/nomencladores/ecosistema-rol" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/nomencladores/ecosistema-rol/${ecosistemaRolEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default EcosistemaRolDetail;
