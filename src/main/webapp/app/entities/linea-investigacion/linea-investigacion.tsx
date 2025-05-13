import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILineaInvestigacion } from 'app/shared/model/linea-investigacion.model';
import { getEntities } from './linea-investigacion.reducer';

export const LineaInvestigacion = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const lineaInvestigacionList = useAppSelector(state => state.lineaInvestigacion.entities);
  const loading = useAppSelector(state => state.lineaInvestigacion.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div className="mt-6">
      <h2 id="linea-investigacion-heading" data-cy="LineaInvestigacionHeading">
        <Translate contentKey="jhipsterApp.lineaInvestigacion.home.title">Linea Investigacions</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="jhipsterApp.lineaInvestigacion.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/nomencladores/linea-investigacion/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="jhipsterApp.lineaInvestigacion.home.createLabel">Create new Linea Investigacion</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {lineaInvestigacionList && lineaInvestigacionList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="jhipsterApp.lineaInvestigacion.linea">Linea</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {lineaInvestigacionList.map((lineaInvestigacion, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>{lineaInvestigacion.linea}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/nomencladores/linea-investigacion/${lineaInvestigacion.id}/edit`}
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
                        to={`/nomencladores/linea-investigacion/${lineaInvestigacion.id}/delete`}
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
              <Translate contentKey="jhipsterApp.lineaInvestigacion.home.notFound">No Linea Investigacions found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default LineaInvestigacion;
