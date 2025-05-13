import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITipoContacto } from 'app/shared/model/tipo-contacto.model';
import { getEntities } from './tipo-contacto.reducer';

export const TipoContacto = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const tipoContactoList = useAppSelector(state => state.tipoContacto.entities);
  const loading = useAppSelector(state => state.tipoContacto.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div className="mt-6">
      <h2 id="tipo-contacto-heading" data-cy="TipoContactoHeading">
        <Translate contentKey="jhipsterApp.tipoContacto.home.title">Tipo Contactos</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="jhipsterApp.tipoContacto.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/nomencladores/tipo-contacto/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="jhipsterApp.tipoContacto.home.createLabel">Create new Tipo Contacto</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {tipoContactoList && tipoContactoList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="jhipsterApp.tipoContacto.tipoContacto">Tipo Contacto</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {tipoContactoList.map((tipoContacto, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>{tipoContacto.tipoContacto}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/nomencladores/tipo-contacto/${tipoContacto.id}/edit`}
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
                        to={`/nomencladores/tipo-contacto/${tipoContacto.id}/delete`}
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
              <Translate contentKey="jhipsterApp.tipoContacto.home.notFound">No Tipo Contactos found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default TipoContacto;
