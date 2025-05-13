import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILikeIdea } from 'app/shared/model/like-idea.model';
import { getEntities } from './like-idea.reducer';

export const LikeIdea = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const likeIdeaList = useAppSelector(state => state.likeIdea.entities);
  const loading = useAppSelector(state => state.likeIdea.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="like-idea-heading" data-cy="LikeIdeaHeading">
        <Translate contentKey="jhipsterApp.likeIdea.home.title">Like Ideas</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="jhipsterApp.likeIdea.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/entidad/like-idea/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="jhipsterApp.likeIdea.home.createLabel">Create new Like Idea</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {likeIdeaList && likeIdeaList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="jhipsterApp.likeIdea.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.likeIdea.like">Like</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.likeIdea.fechaInscripcion">Fecha Inscripcion</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.likeIdea.user">User</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.likeIdea.idea">Idea</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {likeIdeaList.map((likeIdea, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/entidad/like-idea/${likeIdea.id}`} color="link" size="sm">
                      {likeIdea.id}
                    </Button>
                  </td>
                  <td>{likeIdea.like}</td>
                  <td>
                    {likeIdea.fechaInscripcion ? (
                      <TextFormat type="date" value={likeIdea.fechaInscripcion} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{likeIdea.user ? likeIdea.user.login : ''}</td>
                  <td>{likeIdea.idea ? <Link to={`/entidad/idea/${likeIdea.idea.id}`}>{likeIdea.idea.titulo}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/entidad/like-idea/${likeIdea.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/entidad/like-idea/${likeIdea.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/entidad/like-idea/${likeIdea.id}/delete`}
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
              <Translate contentKey="jhipsterApp.likeIdea.home.notFound">No Like Ideas found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default LikeIdea;
