import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEntidad } from 'app/shared/model/entidad.model';
import { getEntities } from './entidad.reducer';

export const Entidad = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const entidadList = useAppSelector(state => state.entidad.entities);
  const loading = useAppSelector(state => state.entidad.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div className="mt-4">
      <h2 id="entidad-heading" data-cy="EntidadHeading">
        <Translate contentKey="jhipsterApp.entidad.home.title">Entidades</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="jhipsterApp.entidad.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/nomencladores/entidad/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="jhipsterApp.entidad.home.createLabel">Create new Entidad</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {entidadList && entidadList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="jhipsterApp.entidad.entidad">Entidad</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {entidadList.map((entidad, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>{entidad.entidad}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/nomencladores/entidad/${entidad.id}/edit`}
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
                        to={`/nomencladores/entidad/${entidad.id}/delete`}
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
              <Translate contentKey="jhipsterApp.entidad.home.notFound">No Entidads found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Entidad;
