import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IContactoServicio } from 'app/shared/model/contacto-servicio.model';
import { getEntities } from './contacto-servicio.reducer';

export const ContactoServicio = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const contactoServicioList = useAppSelector(state => state.contactoServicio.entities);
  const loading = useAppSelector(state => state.contactoServicio.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div className="mt-4">
      <h2 id="contacto-servicio-heading" data-cy="ContactoServicioHeading">
        <Translate contentKey="jhipsterApp.contactoServicio.home.title">Contacto Servicios</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="jhipsterApp.contactoServicio.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/entidad/contacto-servicio/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="jhipsterApp.contactoServicio.home.createLabel">Create new Contacto Servicio</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {contactoServicioList && contactoServicioList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="jhipsterApp.contactoServicio.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.contactoServicio.nombre">Nombre</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.contactoServicio.telefono">Telefono</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.contactoServicio.correo">Correo</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.contactoServicio.mensaje">Mensaje</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.contactoServicio.fechaContacto">Fecha Contacto</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.contactoServicio.servicios">Servicios</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {contactoServicioList.map((contactoServicio, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/contacto-servicio/${contactoServicio.id}`} color="link" size="sm">
                      {contactoServicio.id}
                    </Button>
                  </td>
                  <td>{contactoServicio.nombre}</td>
                  <td>{contactoServicio.telefono}</td>
                  <td>{contactoServicio.correo}</td>
                  <td>{contactoServicio.mensaje}</td>
                  <td>
                    {contactoServicio.fechaContacto ? (
                      <TextFormat type="date" value={contactoServicio.fechaContacto} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {contactoServicio.servicios ? (
                      <Link to={`/entidad/servicios/${contactoServicio.servicios.id}`}>{contactoServicio.servicios.servicio}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/entidad/contacto-servicio/${contactoServicio.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/entidad/contacto-servicio/${contactoServicio.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/entidad/contacto-servicio/${contactoServicio.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="jhipsterApp.contactoServicio.home.notFound">No Contacto Servicios found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default ContactoServicio;
