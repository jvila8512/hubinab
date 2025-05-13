import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEcosistema } from 'app/shared/model/ecosistema.model';
import { getEntities } from './ecosistema.reducer';

export const Ecosistema = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const ecosistemaList = useAppSelector(state => state.ecosistema.entities);
  const loading = useAppSelector(state => state.ecosistema.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="ecosistema-heading" data-cy="EcosistemaHeading">
        <Translate contentKey="jhipsterApp.ecosistema.home.title">Ecosistemas</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="jhipsterApp.ecosistema.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/ecosistema/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="jhipsterApp.ecosistema.home.createLabel">Create new Ecosistema</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {ecosistemaList && ecosistemaList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="jhipsterApp.ecosistema.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.ecosistema.nombre">Nombre</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.ecosistema.tematica">Tematica</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.ecosistema.activo">Activo</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.ecosistema.logoUrl">Logo Url</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.ecosistema.ranking">Ranking</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.ecosistema.usuariosCant">Usuarios Cant</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.ecosistema.retosCant">Retos Cant</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.ecosistema.ideasCant">Ideas Cant</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.ecosistema.user">User</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.ecosistema.ecosistemaRol">Ecosistema Rol</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {ecosistemaList.map((ecosistema, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/ecosistema/${ecosistema.id}`} color="link" size="sm">
                      {ecosistema.id}
                    </Button>
                  </td>
                  <td>{ecosistema.nombre}</td>
                  <td>{ecosistema.tematica}</td>
                  <td>{ecosistema.activo ? 'true' : 'false'}</td>
                  <td>
                    {ecosistema.logoUrl ? (
                      <div>
                        {ecosistema.logoUrlContentType ? (
                          <a onClick={openFile(ecosistema.logoUrlContentType, ecosistema.logoUrl)}>
                            <img src={`data:${ecosistema.logoUrlContentType};base64,${ecosistema.logoUrl}`} style={{ maxHeight: '30px' }} />
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {ecosistema.logoUrlContentType}, {byteSize(ecosistema.logoUrl)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{ecosistema.ranking}</td>
                  <td>{ecosistema.usuariosCant}</td>
                  <td>{ecosistema.retosCant}</td>
                  <td>{ecosistema.ideasCant}</td>
                  <td>
                    {ecosistema.users
                      ? ecosistema.users.map((val, j) => (
                          <span key={j}>
                            {val.login}
                            {j === ecosistema.users.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {ecosistema.ecosistemaRol ? (
                      <Link to={`/ecosistema-rol/${ecosistema.ecosistemaRol.id}`}>{ecosistema.ecosistemaRol.ecosistemaRol}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/ecosistema/${ecosistema.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/ecosistema/${ecosistema.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/ecosistema/${ecosistema.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="jhipsterApp.ecosistema.home.notFound">No Ecosistemas found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Ecosistema;
