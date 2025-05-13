import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEcosistemaComponente } from 'app/shared/model/ecosistema-componente.model';
import { getEntities } from './ecosistema-componente.reducer';

export const EcosistemaComponente = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const ecosistemaComponenteList = useAppSelector(state => state.ecosistemaComponente.entities);
  const loading = useAppSelector(state => state.ecosistemaComponente.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="ecosistema-componente-heading" data-cy="EcosistemaComponenteHeading">
        <Translate contentKey="jhipsterApp.ecosistemaComponente.home.title">Ecosistema Componentes</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="jhipsterApp.ecosistemaComponente.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/entidad/ecosistema-componente/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="jhipsterApp.ecosistemaComponente.home.createLabel">Create new Ecosistema Componente</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {ecosistemaComponenteList && ecosistemaComponenteList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="jhipsterApp.ecosistemaComponente.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.ecosistemaComponente.link">Link</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.ecosistemaComponente.documentoUrl">Documento Url</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.ecosistemaComponente.componente">Componente</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.ecosistemaComponente.ecosistema">Ecosistema</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {ecosistemaComponenteList.map((ecosistemaComponente, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/entidad/ecosistema-componente/${ecosistemaComponente.id}`} color="link" size="sm">
                      {ecosistemaComponente.id}
                    </Button>
                  </td>
                  <td>{ecosistemaComponente.link}</td>
                  <td>
                    {ecosistemaComponente.documentoUrl ? (
                      <div>
                        {ecosistemaComponente.documentoUrlContentType ? (
                          <a
                            className="text-primary"
                            onClick={openFile(ecosistemaComponente.documentoUrlContentType, ecosistemaComponente.documentoUrl)}
                          >
                            <Translate contentKey="entity.action.open">Open</Translate>
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {ecosistemaComponente.documentoUrlContentType}, {byteSize(ecosistemaComponente.documentoUrl)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {ecosistemaComponente.componente ? (
                      <Link to={`/entidad/componentes/${ecosistemaComponente.componente.id}`}>
                        {ecosistemaComponente.componente.componente}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {ecosistemaComponente.ecosistema ? (
                      <Link to={`/entidad/ecosistema/${ecosistemaComponente.ecosistema.id}`}>{ecosistemaComponente.ecosistema.nombre}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/entidad/ecosistema-componente/${ecosistemaComponente.id}`}
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
                        to={`/entidad/ecosistema-componente/${ecosistemaComponente.id}/edit`}
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
                        to={`/entidad/ecosistema-componente/${ecosistemaComponente.id}/delete`}
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
              <Translate contentKey="jhipsterApp.ecosistemaComponente.home.notFound">No Ecosistema Componentes found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default EcosistemaComponente;
