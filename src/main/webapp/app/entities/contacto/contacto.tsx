import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IContacto } from 'app/shared/model/contacto.model';
import { getEntities } from './contacto.reducer';

export const Contacto = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const contactoList = useAppSelector(state => state.contacto.entities);
  const loading = useAppSelector(state => state.contacto.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div className="mt-6">
      <h2 id="contacto-heading" data-cy="ContactoHeading">
        <Translate contentKey="jhipsterApp.contacto.home.title">Contactos</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="jhipsterApp.contacto.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/nomencladores/contacto/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="jhipsterApp.contacto.home.createLabel">Create new Contacto</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {contactoList && contactoList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="jhipsterApp.contacto.nombre">Nombre</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.contacto.telefono">Telefono</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.contacto.correo">Correo</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.contacto.mensaje">Mensaje</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.contacto.fechaContacto">Fecha Contacto</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.contacto.tipoContacto">Tipo Contacto</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {contactoList.map((contacto, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>{contacto.nombre}</td>
                  <td>{contacto.telefono}</td>
                  <td>{contacto.correo}</td>
                  <td>{contacto.mensaje}</td>
                  <td>
                    {contacto.fechaContacto ? (
                      <TextFormat type="date" value={contacto.fechaContacto} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {contacto.tipoContacto ? (
                      <Link to={`/tipo-contacto/${contacto.tipoContacto.id}`}>{contacto.tipoContacto.tipoContacto}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/nomencladores/contacto/${contacto.id}/edit`}
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
                        to={`/nomencladores/contacto/${contacto.id}/delete`}
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
              <Translate contentKey="jhipsterApp.contacto.home.notFound">No Contactos found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Contacto;
