import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './contacto-servicio.reducer';

export const ContactoServicioDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const contactoServicioEntity = useAppSelector(state => state.contactoServicio.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="contactoServicioDetailsHeading">
          <Translate contentKey="jhipsterApp.contactoServicio.detail.title">ContactoServicio</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{contactoServicioEntity.id}</dd>
          <dt>
            <span id="nombre">
              <Translate contentKey="jhipsterApp.contactoServicio.nombre">Nombre</Translate>
            </span>
          </dt>
          <dd>{contactoServicioEntity.nombre}</dd>
          <dt>
            <span id="telefono">
              <Translate contentKey="jhipsterApp.contactoServicio.telefono">Telefono</Translate>
            </span>
          </dt>
          <dd>{contactoServicioEntity.telefono}</dd>
          <dt>
            <span id="correo">
              <Translate contentKey="jhipsterApp.contactoServicio.correo">Correo</Translate>
            </span>
          </dt>
          <dd>{contactoServicioEntity.correo}</dd>
          <dt>
            <span id="mensaje">
              <Translate contentKey="jhipsterApp.contactoServicio.mensaje">Mensaje</Translate>
            </span>
          </dt>
          <dd>{contactoServicioEntity.mensaje}</dd>
          <dt>
            <span id="fechaContacto">
              <Translate contentKey="jhipsterApp.contactoServicio.fechaContacto">Fecha Contacto</Translate>
            </span>
          </dt>
          <dd>
            {contactoServicioEntity.fechaContacto ? (
              <TextFormat value={contactoServicioEntity.fechaContacto} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="jhipsterApp.contactoServicio.servicios">Servicios</Translate>
          </dt>
          <dd>{contactoServicioEntity.servicios ? contactoServicioEntity.servicios.servicio : ''}</dd>
        </dl>
        <Button tag={Link} to="/entidad/contacto-servicio" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/entidad/contacto-servicio/${contactoServicioEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ContactoServicioDetail;
