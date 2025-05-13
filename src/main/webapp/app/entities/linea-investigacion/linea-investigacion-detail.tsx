import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './linea-investigacion.reducer';

export const LineaInvestigacionDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const lineaInvestigacionEntity = useAppSelector(state => state.lineaInvestigacion.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="lineaInvestigacionDetailsHeading">
          <Translate contentKey="jhipsterApp.lineaInvestigacion.detail.title">LineaInvestigacion</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{lineaInvestigacionEntity.id}</dd>
          <dt>
            <span id="linea">
              <Translate contentKey="jhipsterApp.lineaInvestigacion.linea">Linea</Translate>
            </span>
          </dt>
          <dd>{lineaInvestigacionEntity.linea}</dd>
        </dl>
        <Button tag={Link} to="/nomencladores/linea-investigacion" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/nomencladores/linea-investigacion/${lineaInvestigacionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default LineaInvestigacionDetail;
