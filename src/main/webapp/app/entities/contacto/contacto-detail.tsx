import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './contacto.reducer';

export const ContactoDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const contactoEntity = useAppSelector(state => state.contacto.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="contactoDetailsHeading">
          <Translate contentKey="jhipsterApp.contacto.detail.title">Contacto</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{contactoEntity.id}</dd>
          <dt>
            <span id="nombre">
              <Translate contentKey="jhipsterApp.contacto.nombre">Nombre</Translate>
            </span>
          </dt>
          <dd>{contactoEntity.nombre}</dd>
          <dt>
            <span id="telefono">
              <Translate contentKey="jhipsterApp.contacto.telefono">Telefono</Translate>
            </span>
          </dt>
          <dd>{contactoEntity.telefono}</dd>
          <dt>
            <span id="correo">
              <Translate contentKey="jhipsterApp.contacto.correo">Correo</Translate>
            </span>
          </dt>
          <dd>{contactoEntity.correo}</dd>
          <dt>
            <span id="mensaje">
              <Translate contentKey="jhipsterApp.contacto.mensaje">Mensaje</Translate>
            </span>
          </dt>
          <dd>{contactoEntity.mensaje}</dd>
          <dt>
            <span id="fechaContacto">
              <Translate contentKey="jhipsterApp.contacto.fechaContacto">Fecha Contacto</Translate>
            </span>
          </dt>
          <dd>
            {contactoEntity.fechaContacto ? (
              <TextFormat value={contactoEntity.fechaContacto} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="jhipsterApp.contacto.tipoContacto">Tipo Contacto</Translate>
          </dt>
          <dd>{contactoEntity.tipoContacto ? contactoEntity.tipoContacto.tipoContacto : ''}</dd>
        </dl>
        <Button tag={Link} to="/nomencladores/contacto" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/nomencladores/contacto/${contactoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ContactoDetail;
