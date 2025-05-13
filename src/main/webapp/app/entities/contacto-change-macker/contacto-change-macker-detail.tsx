import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './contacto-change-macker.reducer';

export const ContactoChangeMackerDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const contactoChangeMackerEntity = useAppSelector(state => state.contactoChangeMacker.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="contactoChangeMackerDetailsHeading">
          <Translate contentKey="jhipsterApp.contactoChangeMacker.detail.title">ContactoChangeMacker</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{contactoChangeMackerEntity.id}</dd>
          <dt>
            <span id="nombre">
              <Translate contentKey="jhipsterApp.contactoChangeMacker.nombre">Nombre</Translate>
            </span>
          </dt>
          <dd>{contactoChangeMackerEntity.nombre}</dd>
          <dt>
            <span id="telefono">
              <Translate contentKey="jhipsterApp.contactoChangeMacker.telefono">Telefono</Translate>
            </span>
          </dt>
          <dd>{contactoChangeMackerEntity.telefono}</dd>
          <dt>
            <span id="correo">
              <Translate contentKey="jhipsterApp.contactoChangeMacker.correo">Correo</Translate>
            </span>
          </dt>
          <dd>{contactoChangeMackerEntity.correo}</dd>
          <dt>
            <span id="mensaje">
              <Translate contentKey="jhipsterApp.contactoChangeMacker.mensaje">Mensaje</Translate>
            </span>
          </dt>
          <dd>{contactoChangeMackerEntity.mensaje}</dd>
          <dt>
            <span id="fechaContacto">
              <Translate contentKey="jhipsterApp.contactoChangeMacker.fechaContacto">Fecha Contacto</Translate>
            </span>
          </dt>
          <dd>
            {contactoChangeMackerEntity.fechaContacto ? (
              <TextFormat value={contactoChangeMackerEntity.fechaContacto} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="jhipsterApp.contactoChangeMacker.changeMacker">Change Macker</Translate>
          </dt>
          <dd>{contactoChangeMackerEntity.changeMacker ? contactoChangeMackerEntity.changeMacker.nombre : ''}</dd>
        </dl>
        <Button tag={Link} to="/entidad/contacto-change-macker" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/entidad/contacto-change-macker/${contactoChangeMackerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ContactoChangeMackerDetail;
