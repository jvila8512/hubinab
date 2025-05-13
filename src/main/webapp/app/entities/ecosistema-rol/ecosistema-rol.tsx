import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { byteSize, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEcosistemaRol } from 'app/shared/model/ecosistema-rol.model';
import { getEntities } from './ecosistema-rol.reducer';

export const EcosistemaRol = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const ecosistemaRolList = useAppSelector(state => state.ecosistemaRol.entities);
  const loading = useAppSelector(state => state.ecosistemaRol.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div className="mt-4">
      <h2 id="ecosistema-rol-heading" data-cy="EcosistemaRolHeading">
        <Translate contentKey="jhipsterApp.ecosistemaRol.home.title">Ecosistema Rols</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="jhipsterApp.ecosistemaRol.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/nomencladores/ecosistema-rol/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="jhipsterApp.ecosistemaRol.home.createLabel">Create new Ecosistema Rol</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {ecosistemaRolList && ecosistemaRolList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="jhipsterApp.ecosistemaRol.ecosistemaRol">Ecosistema Rol</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.ecosistemaRol.descripcion">Descripcion</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {ecosistemaRolList.map((ecosistemaRol, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>{ecosistemaRol.ecosistemaRol}</td>
                  <td>{ecosistemaRol.descripcion}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/nomencladores/ecosistema-rol/${ecosistemaRol.id}/edit`}
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
                        to={`/nomencladores/ecosistema-rol/${ecosistemaRol.id}/delete`}
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
              <Translate contentKey="jhipsterApp.ecosistemaRol.home.notFound">No Ecosistema Rols found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default EcosistemaRol;
