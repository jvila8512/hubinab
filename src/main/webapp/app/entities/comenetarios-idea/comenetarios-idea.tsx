import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { byteSize, Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IComenetariosIdea } from 'app/shared/model/comenetarios-idea.model';
import { getEntities } from './comenetarios-idea.reducer';

export const ComenetariosIdea = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const comenetariosIdeaList = useAppSelector(state => state.comenetariosIdea.entities);
  const loading = useAppSelector(state => state.comenetariosIdea.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="comenetarios-idea-heading" data-cy="ComenetariosIdeaHeading">
        <Translate contentKey="jhipsterApp.comenetariosIdea.home.title">Comenetarios Ideas</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="jhipsterApp.comenetariosIdea.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/comenetarios-idea/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="jhipsterApp.comenetariosIdea.home.createLabel">Create new Comenetarios Idea</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {comenetariosIdeaList && comenetariosIdeaList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="jhipsterApp.comenetariosIdea.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.comenetariosIdea.comentario">Comentario</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.comenetariosIdea.fechaInscripcion">Fecha Inscripcion</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.comenetariosIdea.user">User</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.comenetariosIdea.idea">Idea</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {comenetariosIdeaList.map((comenetariosIdea, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/comenetarios-idea/${comenetariosIdea.id}`} color="link" size="sm">
                      {comenetariosIdea.id}
                    </Button>
                  </td>
                  <td>{comenetariosIdea.comentario}</td>
                  <td>
                    {comenetariosIdea.fechaInscripcion ? (
                      <TextFormat type="date" value={comenetariosIdea.fechaInscripcion} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{comenetariosIdea.user ? comenetariosIdea.user.login : ''}</td>
                  <td>
                    {comenetariosIdea.idea ? <Link to={`/idea/${comenetariosIdea.idea.id}`}>{comenetariosIdea.idea.titulo}</Link> : ''}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/comenetarios-idea/${comenetariosIdea.id}`}
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
                        to={`/comenetarios-idea/${comenetariosIdea.id}/edit`}
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
                        to={`/comenetarios-idea/${comenetariosIdea.id}/delete`}
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
              <Translate contentKey="jhipsterApp.comenetariosIdea.home.notFound">No Comenetarios Ideas found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default ComenetariosIdea;
