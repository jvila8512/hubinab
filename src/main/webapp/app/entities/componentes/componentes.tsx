import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { byteSize, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IComponentes } from 'app/shared/model/componentes.model';
import { getEntities } from './componentes.reducer';

export const Componentes = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const componentesList = useAppSelector(state => state.componentes.entities);
  const loading = useAppSelector(state => state.componentes.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div className="mt-4">
      <h2 id="componentes-heading" data-cy="ComponentesHeading">
        <Translate contentKey="jhipsterApp.componentes.home.title">Componentes</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="jhipsterApp.componentes.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/entidad/componentes/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="jhipsterApp.componentes.home.createLabel">Create new Componentes</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {componentesList && componentesList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="jhipsterApp.componentes.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.componentes.componente">Componente</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.componentes.descripcion">Descripcion</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.componentes.activo">Activo</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {componentesList.map((componentes, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/entidad/componentes/${componentes.id}`} color="link" size="sm">
                      {componentes.id}
                    </Button>
                  </td>
                  <td>{componentes.componente}</td>
                  <td>{componentes.descripcion}</td>
                  <td>{componentes.activo ? 'true' : 'false'}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/entidad/componentes/${componentes.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/entidad/componentes/${componentes.id}/edit`}
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
                        to={`/entidad/componentes/${componentes.id}/delete`}
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
              <Translate contentKey="jhipsterApp.componentes.home.notFound">No Componentes found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Componentes;
