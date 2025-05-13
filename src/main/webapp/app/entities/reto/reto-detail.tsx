import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './reto.reducer';

export const RetoDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const retoEntity = useAppSelector(state => state.reto.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="retoDetailsHeading">
          <Translate contentKey="jhipsterApp.reto.detail.title">Reto</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{retoEntity.id}</dd>
          <dt>
            <span id="reto">
              <Translate contentKey="jhipsterApp.reto.reto">Reto</Translate>
            </span>
          </dt>
          <dd>{retoEntity.reto}</dd>
          <dt>
            <span id="descripcion">
              <Translate contentKey="jhipsterApp.reto.descripcion">Descripcion</Translate>
            </span>
          </dt>
          <dd>{retoEntity.descripcion}</dd>
          <dt>
            <span id="motivacion">
              <Translate contentKey="jhipsterApp.reto.motivacion">Motivacion</Translate>
            </span>
          </dt>
          <dd>{retoEntity.motivacion}</dd>
          <dt>
            <span id="fechaInicio">
              <Translate contentKey="jhipsterApp.reto.fechaInicio">Fecha Inicio</Translate>
            </span>
          </dt>
          <dd>
            {retoEntity.fechaInicio ? <TextFormat value={retoEntity.fechaInicio} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="fechaFin">
              <Translate contentKey="jhipsterApp.reto.fechaFin">Fecha Fin</Translate>
            </span>
          </dt>
          <dd>{retoEntity.fechaFin ? <TextFormat value={retoEntity.fechaFin} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="activo">
              <Translate contentKey="jhipsterApp.reto.activo">Activo</Translate>
            </span>
          </dt>
          <dd>{retoEntity.activo ? 'true' : 'false'}</dd>
          <dt>
            <span id="validado">
              <Translate contentKey="jhipsterApp.reto.validado">Validado</Translate>
            </span>
          </dt>
          <dd>{retoEntity.validado ? 'true' : 'false'}</dd>
          <dt>
            <span id="urlFoto">
              <Translate contentKey="jhipsterApp.reto.urlFoto">Url Foto</Translate>
            </span>
          </dt>
          <dd>
            {retoEntity.urlFoto ? (
              <div>
                {retoEntity.urlFotoContentType ? (
                  <a onClick={openFile(retoEntity.urlFotoContentType, retoEntity.urlFoto)}>
                    <img src={`data:${retoEntity.urlFotoContentType};base64,${retoEntity.urlFoto}`} style={{ maxHeight: '30px' }} />
                  </a>
                ) : null}
                <span>
                  {retoEntity.urlFotoContentType}, {byteSize(retoEntity.urlFoto)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="visto">
              <Translate contentKey="jhipsterApp.reto.visto">Visto</Translate>
            </span>
          </dt>
          <dd>{retoEntity.visto}</dd>
          <dt>
            <Translate contentKey="jhipsterApp.reto.user">User</Translate>
          </dt>
          <dd>{retoEntity.user ? retoEntity.user.login : ''}</dd>
          <dt>
            <Translate contentKey="jhipsterApp.reto.ecosistema">Ecosistema</Translate>
          </dt>
          <dd>{retoEntity.ecosistema ? retoEntity.ecosistema.nombre : ''}</dd>
        </dl>
        <Button tag={Link} to="/entidad/reto" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/entidad/reto/${retoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RetoDetail;
