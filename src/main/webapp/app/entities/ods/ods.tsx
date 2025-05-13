import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IOds } from 'app/shared/model/ods.model';
import { getEntities } from './ods.reducer';

export const Ods = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const odsList = useAppSelector(state => state.ods.entities);
  const loading = useAppSelector(state => state.ods.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div className="mt-6">
      <h2 id="ods-heading" data-cy="OdsHeading">
        <Translate contentKey="jhipsterApp.ods.home.title">Ods</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="jhipsterApp.ods.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/nomencladores/ods/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="jhipsterApp.ods.home.createLabel">Create new Ods</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {odsList && odsList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="jhipsterApp.ods.ods">Ods</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {odsList.map((ods, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>{ods.ods}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/nomencladores/ods/${ods.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/nomencladores/ods/${ods.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="jhipsterApp.ods.home.notFound">No Ods found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Ods;
