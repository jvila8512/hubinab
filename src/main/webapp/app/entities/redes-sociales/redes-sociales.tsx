import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IRedesSociales } from 'app/shared/model/redes-sociales.model';
import { getEntities } from './redes-sociales.reducer';

export const RedesSociales = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const redesSocialesList = useAppSelector(state => state.redesSociales.entities);
  const loading = useAppSelector(state => state.redesSociales.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div className="mt-4">
      <h2 id="redes-sociales-heading" data-cy="RedesSocialesHeading">
        <Translate contentKey="jhipsterApp.redesSociales.home.title">Redes Sociales</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="jhipsterApp.redesSociales.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/redes-sociales/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="jhipsterApp.redesSociales.home.createLabel">Create new Redes Sociales</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {redesSocialesList && redesSocialesList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="jhipsterApp.redesSociales.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.redesSociales.redesUrl">Redes Url</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.redesSociales.logoUrl">Logo Url</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.redesSociales.ecosistema">Ecosistema</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {redesSocialesList.map((redesSociales, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/redes-sociales/${redesSociales.id}`} color="link" size="sm">
                      {redesSociales.id}
                    </Button>
                  </td>
                  <td>{redesSociales.redesUrl}</td>
                  <td>
                    {redesSociales.logoUrl ? (
                      <div>
                        {redesSociales.logoUrlContentType ? (
                          <a onClick={openFile(redesSociales.logoUrlContentType, redesSociales.logoUrl)}>
                            <img
                              src={`data:${redesSociales.logoUrlContentType};base64,${redesSociales.logoUrl}`}
                              style={{ maxHeight: '30px' }}
                            />
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {redesSociales.logoUrlContentType}, {byteSize(redesSociales.logoUrl)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {redesSociales.ecosistema ? (
                      <Link to={`/ecosistema/${redesSociales.ecosistema.id}`}>{redesSociales.ecosistema.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/redes-sociales/${redesSociales.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/redes-sociales/${redesSociales.id}/edit`}
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
                        to={`/redes-sociales/${redesSociales.id}/delete`}
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
              <Translate contentKey="jhipsterApp.redesSociales.home.notFound">No Redes Sociales found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default RedesSociales;
