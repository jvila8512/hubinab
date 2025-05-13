import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IContactoChangeMacker } from 'app/shared/model/contacto-change-macker.model';
import { getEntities } from './contacto-change-macker.reducer';

export const ContactoChangeMacker = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const contactoChangeMackerList = useAppSelector(state => state.contactoChangeMacker.entities);
  const loading = useAppSelector(state => state.contactoChangeMacker.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div className="mt-4">
      <h2 id="contacto-change-macker-heading" data-cy="ContactoChangeMackerHeading">
        <Translate contentKey="jhipsterApp.contactoChangeMacker.home.title">Contacto Change Mackers</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="jhipsterApp.contactoChangeMacker.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/entidad/contacto-change-macker/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="jhipsterApp.contactoChangeMacker.home.createLabel">Create new Contacto Change Macker</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {contactoChangeMackerList && contactoChangeMackerList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="jhipsterApp.contactoChangeMacker.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.contactoChangeMacker.nombre">Nombre</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.contactoChangeMacker.telefono">Telefono</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.contactoChangeMacker.correo">Correo</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.contactoChangeMacker.mensaje">Mensaje</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.contactoChangeMacker.fechaContacto">Fecha Contacto</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.contactoChangeMacker.changeMacker">Change Macker</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {contactoChangeMackerList.map((contactoChangeMacker, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/contacto-change-macker/${contactoChangeMacker.id}`} color="link" size="sm">
                      {contactoChangeMacker.id}
                    </Button>
                  </td>
                  <td>{contactoChangeMacker.nombre}</td>
                  <td>{contactoChangeMacker.telefono}</td>
                  <td>{contactoChangeMacker.correo}</td>
                  <td>{contactoChangeMacker.mensaje}</td>
                  <td>
                    {contactoChangeMacker.fechaContacto ? (
                      <TextFormat type="date" value={contactoChangeMacker.fechaContacto} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {contactoChangeMacker.changeMacker ? (
                      <Link to={`/change-macker/${contactoChangeMacker.changeMacker.id}`}>{contactoChangeMacker.changeMacker.nombre}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/entidad/contacto-change-macker/${contactoChangeMacker.id}`}
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
                        to={`/entidad/contacto-change-macker/${contactoChangeMacker.id}/edit`}
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
                        to={`/entidad/contacto-change-macker/${contactoChangeMacker.id}/delete`}
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
              <Translate contentKey="jhipsterApp.contactoChangeMacker.home.notFound">No Contacto Change Mackers found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default ContactoChangeMacker;
