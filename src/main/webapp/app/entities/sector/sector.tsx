import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISector } from 'app/shared/model/sector.model';
import { getEntities } from './sector.reducer';

export const Sector = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const sectorList = useAppSelector(state => state.sector.entities);
  const loading = useAppSelector(state => state.sector.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div className="mt-6">
      <h2 id="sector-heading" data-cy="SectorHeading">
        <Translate contentKey="jhipsterApp.sector.home.title">Sectors</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="jhipsterApp.sector.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/nomencladores/sector/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="jhipsterApp.sector.home.createLabel">Create new Sector</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {sectorList && sectorList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="jhipsterApp.sector.sector">Sector</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {sectorList.map((sector, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>{sector.sector}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/nomencladores/sector/${sector.id}/edit`}
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
                        to={`/nomencladores/sector/${sector.id}/delete`}
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
              <Translate contentKey="jhipsterApp.sector.home.notFound">No Sectors found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Sector;
