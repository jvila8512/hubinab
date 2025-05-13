import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './innovacion-racionalizacion.reducer';

export const InnovacionRacionalizacionDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const innovacionRacionalizacionEntity = useAppSelector(state => state.innovacionRacionalizacion.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="innovacionRacionalizacionDetailsHeading">
          <Translate contentKey="jhipsterApp.innovacionRacionalizacion.detail.title">InnovacionRacionalizacion</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{innovacionRacionalizacionEntity.id}</dd>
          <dt>
            <span id="tematica">
              <Translate contentKey="jhipsterApp.innovacionRacionalizacion.tematica">Tematica</Translate>
            </span>
          </dt>
          <dd>{innovacionRacionalizacionEntity.tematica}</dd>
          <dt>
            <span id="fecha">
              <Translate contentKey="jhipsterApp.innovacionRacionalizacion.fecha">Fecha</Translate>
            </span>
          </dt>
          <dd>
            {innovacionRacionalizacionEntity.fecha ? (
              <TextFormat value={innovacionRacionalizacionEntity.fecha} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="vp">
              <Translate contentKey="jhipsterApp.innovacionRacionalizacion.vp">Vp</Translate>
            </span>
          </dt>
          <dd>{innovacionRacionalizacionEntity.vp}</dd>
          <dt>
            <span id="autores">
              <Translate contentKey="jhipsterApp.innovacionRacionalizacion.autores">Autores</Translate>
            </span>
          </dt>
          <dd>{innovacionRacionalizacionEntity.autores}</dd>
          <dt>
            <span id="numeroIdentidad">
              <Translate contentKey="jhipsterApp.innovacionRacionalizacion.numeroIdentidad">Numero Identidad</Translate>
            </span>
          </dt>
          <dd>{innovacionRacionalizacionEntity.numeroIdentidad}</dd>
          <dt>
            <span id="observacion">
              <Translate contentKey="jhipsterApp.innovacionRacionalizacion.observacion">Observacion</Translate>
            </span>
          </dt>
          <dd>{innovacionRacionalizacionEntity.observacion}</dd>
          <dt>
            <span id="aprobada">
              <Translate contentKey="jhipsterApp.innovacionRacionalizacion.aprobada">Aprobada</Translate>
            </span>
          </dt>
          <dd>{innovacionRacionalizacionEntity.aprobada ? 'true' : 'false'}</dd>
          <dt>
            <span id="publico">
              <Translate contentKey="jhipsterApp.innovacionRacionalizacion.publico">Publico</Translate>
            </span>
          </dt>
          <dd>{innovacionRacionalizacionEntity.publico ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="jhipsterApp.innovacionRacionalizacion.tipoIdea">Tipo Idea</Translate>
          </dt>
          <dd>{innovacionRacionalizacionEntity.tipoIdea ? innovacionRacionalizacionEntity.tipoIdea.tipoIdea : ''}</dd>
        </dl>
        <Button tag={Link} to="/entidad/innovacion-racionalizacion" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/entidad/innovacion-racionalizacion/${innovacionRacionalizacionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default InnovacionRacionalizacionDetail;
