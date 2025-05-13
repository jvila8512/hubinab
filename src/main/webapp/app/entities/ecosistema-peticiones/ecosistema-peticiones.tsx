import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEcosistemaPeticiones } from 'app/shared/model/ecosistema-peticiones.model';
import { getEntities } from './ecosistema-peticiones.reducer';

export const EcosistemaPeticiones = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const ecosistemaPeticionesList = useAppSelector(state => state.ecosistemaPeticiones.entities);
  const loading = useAppSelector(state => state.ecosistemaPeticiones.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="ecosistema-peticiones-heading" data-cy="EcosistemaPeticionesHeading">
        <Translate contentKey="jhipsterApp.ecosistemaPeticiones.home.title">Ecosistema Peticiones</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="jhipsterApp.ecosistemaPeticiones.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/nomencladores/ecosistema-peticiones/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="jhipsterApp.ecosistemaPeticiones.home.createLabel">Create new Ecosistema Peticiones</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {ecosistemaPeticionesList && ecosistemaPeticionesList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="jhipsterApp.ecosistemaPeticiones.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.ecosistemaPeticiones.motivo">Motivo</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.ecosistemaPeticiones.fechaPeticion">Fecha Peticion</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.ecosistemaPeticiones.aprobada">Aprobada</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.ecosistemaPeticiones.user">User</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.ecosistemaPeticiones.ecosistema">Ecosistema</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {ecosistemaPeticionesList.map((ecosistemaPeticiones, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/nomencladores/ecosistema-peticiones/${ecosistemaPeticiones.id}`} color="link" size="sm">
                      {ecosistemaPeticiones.id}
                    </Button>
                  </td>
                  <td>{ecosistemaPeticiones.motivo}</td>
                  <td>
                    {ecosistemaPeticiones.fechaPeticion ? (
                      <TextFormat type="date" value={ecosistemaPeticiones.fechaPeticion} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{ecosistemaPeticiones.aprobada ? 'true' : 'false'}</td>
                  <td>{ecosistemaPeticiones.user ? ecosistemaPeticiones.user.login : ''}</td>
                  <td>
                    {ecosistemaPeticiones.ecosistema ? (
                      <Link to={`/entidad/ecosistema/${ecosistemaPeticiones.ecosistema.id}`}>{ecosistemaPeticiones.ecosistema.nombre}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/nomencladores/ecosistema-peticiones/${ecosistemaPeticiones.id}`}
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
                        to={`/nomencladores/ecosistema-peticiones/${ecosistemaPeticiones.id}/edit`}
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
                        to={`/nomencladores/ecosistema-peticiones/${ecosistemaPeticiones.id}/delete`}
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
              <Translate contentKey="jhipsterApp.ecosistemaPeticiones.home.notFound">No Ecosistema Peticiones found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default EcosistemaPeticiones;
