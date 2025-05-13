import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITipoProyecto } from 'app/shared/model/tipo-proyecto.model';
import { getEntities } from './tipo-proyecto.reducer';

export const TipoProyecto = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const tipoProyectoList = useAppSelector(state => state.tipoProyecto.entities);
  const loading = useAppSelector(state => state.tipoProyecto.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div className="mt-4">
      <h2 id="tipo-proyecto-heading" data-cy="TipoProyectoHeading">
        <Translate contentKey="jhipsterApp.tipoProyecto.home.title">Tipos de Proyectos</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="jhipsterApp.tipoProyecto.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/nomencladores/tipo-proyecto/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            href=""
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="jhipsterApp.tipoProyecto.home.createLabel">Create new Tipo Proyecto</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {tipoProyectoList && tipoProyectoList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="jhipsterApp.tipoProyecto.tipoProyecto">Tipo Proyecto</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {tipoProyectoList.map((tipoProyecto, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>{tipoProyecto.tipoProyecto}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/nomencladores/tipo-proyecto/${tipoProyecto.id}/edit`}
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
                        to={`/nomencladores/tipo-proyecto/${tipoProyecto.id}/delete`}
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
              <Translate contentKey="jhipsterApp.tipoProyecto.home.notFound">No Tipo Proyectos found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default TipoProyecto;
