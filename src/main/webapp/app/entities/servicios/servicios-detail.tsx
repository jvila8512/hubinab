import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './servicios.reducer';

export const ServiciosDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const serviciosEntity = useAppSelector(state => state.servicios.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="serviciosDetailsHeading">
          <Translate contentKey="jhipsterApp.servicios.detail.title">Servicios</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{serviciosEntity.id}</dd>
          <dt>
            <span id="servicio">
              <Translate contentKey="jhipsterApp.servicios.servicio">Servicio</Translate>
            </span>
          </dt>
          <dd>{serviciosEntity.servicio}</dd>
          <dt>
            <span id="descripcion">
              <Translate contentKey="jhipsterApp.servicios.descripcion">Descripcion</Translate>
            </span>
          </dt>
          <dd>{serviciosEntity.descripcion}</dd>
          <dt>
            <span id="publicado">
              <Translate contentKey="jhipsterApp.servicios.publicado">Publicado</Translate>
            </span>
          </dt>
          <dd>{serviciosEntity.publicado ? 'true' : 'false'}</dd>
          <dt>
            <span id="foto">
              <Translate contentKey="jhipsterApp.servicios.foto">Foto</Translate>
            </span>
          </dt>
          <dd>
            {serviciosEntity.foto ? (
              <div>
                {serviciosEntity.fotoContentType ? (
                  <a onClick={openFile(serviciosEntity.fotoContentType, serviciosEntity.foto)}>
                    <img src={`data:${serviciosEntity.fotoContentType};base64,${serviciosEntity.foto}`} style={{ maxHeight: '30px' }} />
                  </a>
                ) : null}
                <span>
                  {serviciosEntity.fotoContentType}, {byteSize(serviciosEntity.foto)}
                </span>
              </div>
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/servicios" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/servicios/${serviciosEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ServiciosDetail;
