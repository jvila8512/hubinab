import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITipoIdea } from 'app/shared/model/tipo-idea.model';
import { getEntities } from './tipo-idea.reducer';

export const TipoIdea = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const tipoIdeaList = useAppSelector(state => state.tipoIdea.entities);
  const loading = useAppSelector(state => state.tipoIdea.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div className="mt-4">
      <h2 id="tipo-idea-heading" data-cy="TipoIdeaHeading">
        <Translate contentKey="jhipsterApp.tipoIdea.home.title">Tipo Ideas</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="jhipsterApp.tipoIdea.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/nomencladores/tipo-idea/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="jhipsterApp.tipoIdea.home.createLabel">Create new Tipo Idea</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {tipoIdeaList && tipoIdeaList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="jhipsterApp.tipoIdea.tipoIdea">Tipo Idea</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {tipoIdeaList.map((tipoIdea, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>{tipoIdea.tipoIdea}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/nomencladores/tipo-idea/${tipoIdea.id}/edit`}
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
                        to={`/nomencladores/tipo-idea/${tipoIdea.id}/delete`}
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
              <Translate contentKey="jhipsterApp.tipoIdea.home.notFound">No Tipo Ideas found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default TipoIdea;
