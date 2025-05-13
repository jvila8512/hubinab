import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './tramite.reducer';

export const TramiteDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const tramiteEntity = useAppSelector(state => state.tramite.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="tramiteDetailsHeading">
          <Translate contentKey="jhipsterApp.tramite.detail.title">Tramite</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{tramiteEntity.id}</dd>
          <dt>
            <span id="inscripcion">
              <Translate contentKey="jhipsterApp.tramite.inscripcion">Inscripcion</Translate>
            </span>
          </dt>
          <dd>{tramiteEntity.inscripcion}</dd>
          <dt>
            <span id="pruebaExperimental">
              <Translate contentKey="jhipsterApp.tramite.pruebaExperimental">Prueba Experimental</Translate>
            </span>
          </dt>
          <dd>{tramiteEntity.pruebaExperimental}</dd>
          <dt>
            <span id="exmanenEvaluacion">
              <Translate contentKey="jhipsterApp.tramite.exmanenEvaluacion">Exmanen Evaluacion</Translate>
            </span>
          </dt>
          <dd>{tramiteEntity.exmanenEvaluacion}</dd>
          <dt>
            <span id="dictamen">
              <Translate contentKey="jhipsterApp.tramite.dictamen">Dictamen</Translate>
            </span>
          </dt>
          <dd>{tramiteEntity.dictamen}</dd>
          <dt>
            <span id="concesion">
              <Translate contentKey="jhipsterApp.tramite.concesion">Concesion</Translate>
            </span>
          </dt>
          <dd>{tramiteEntity.concesion ? 'true' : 'false'}</dd>
          <dt>
            <span id="denegado">
              <Translate contentKey="jhipsterApp.tramite.denegado">Denegado</Translate>
            </span>
          </dt>
          <dd>{tramiteEntity.denegado ? 'true' : 'false'}</dd>
          <dt>
            <span id="reclamacion">
              <Translate contentKey="jhipsterApp.tramite.reclamacion">Reclamacion</Translate>
            </span>
          </dt>
          <dd>{tramiteEntity.reclamacion ? 'true' : 'false'}</dd>
          <dt>
            <span id="anulacion">
              <Translate contentKey="jhipsterApp.tramite.anulacion">Anulacion</Translate>
            </span>
          </dt>
          <dd>{tramiteEntity.anulacion ? 'true' : 'false'}</dd>
          <dt>
            <span id="fechaNotificacion">
              <Translate contentKey="jhipsterApp.tramite.fechaNotificacion">Fecha Notificacion</Translate>
            </span>
          </dt>
          <dd>
            {tramiteEntity.fechaNotificacion ? (
              <TextFormat value={tramiteEntity.fechaNotificacion} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="fecaCertificado">
              <Translate contentKey="jhipsterApp.tramite.fecaCertificado">Feca Certificado</Translate>
            </span>
          </dt>
          <dd>
            {tramiteEntity.fecaCertificado ? (
              <TextFormat value={tramiteEntity.fecaCertificado} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="observacion">
              <Translate contentKey="jhipsterApp.tramite.observacion">Observacion</Translate>
            </span>
          </dt>
          <dd>{tramiteEntity.observacion}</dd>
        </dl>
        <Button tag={Link} to="/tramite" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/tramite/${tramiteEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TramiteDetail;
