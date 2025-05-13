import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IServicios } from 'app/shared/model/servicios.model';
import { getEntities } from './servicios.reducer';

export const Servicios = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const serviciosList = useAppSelector(state => state.servicios.entities);
  const loading = useAppSelector(state => state.servicios.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div className="mt-4">
      <h2 id="servicios-heading" data-cy="ServiciosHeading">
        <Translate contentKey="jhipsterApp.servicios.home.title">Servicios</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="jhipsterApp.servicios.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/entidad/servicios/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="jhipsterApp.servicios.home.createLabel">Create new Servicios</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {serviciosList && serviciosList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="jhipsterApp.servicios.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.servicios.servicio">Servicio</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.servicios.descripcion">Descripcion</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.servicios.publicado">Publicado</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.servicios.foto">Foto</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {serviciosList.map((servicios, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/servicios/${servicios.id}`} color="link" size="sm">
                      {servicios.id}
                    </Button>
                  </td>
                  <td>{servicios.servicio}</td>
                  <td>{servicios.descripcion}</td>
                  <td>{servicios.publicado ? 'true' : 'false'}</td>
                  <td>
                    {servicios.foto ? (
                      <div>
                        {servicios.fotoContentType ? (
                          <a onClick={openFile(servicios.fotoContentType, servicios.foto)}>
                            <img src={`data:${servicios.fotoContentType};base64,${servicios.foto}`} style={{ maxHeight: '30px' }} />
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {servicios.fotoContentType}, {byteSize(servicios.foto)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/entidad/servicios/${servicios.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/entidad/servicios/${servicios.id}/edit`}
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
                        to={`/entidad/servicios/${servicios.id}/delete`}
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
              <Translate contentKey="jhipsterApp.servicios.home.notFound">No Servicios found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Servicios;
