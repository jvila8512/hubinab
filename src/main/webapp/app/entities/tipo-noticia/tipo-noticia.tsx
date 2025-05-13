import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITipoNoticia } from 'app/shared/model/tipo-noticia.model';
import { getEntities } from './tipo-noticia.reducer';

export const TipoNoticia = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const tipoNoticiaList = useAppSelector(state => state.tipoNoticia.entities);
  const loading = useAppSelector(state => state.tipoNoticia.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div className="mt-6">
      <h2 id="tipo-noticia-heading" data-cy="TipoNoticiaHeading">
        <Translate contentKey="jhipsterApp.tipoNoticia.home.title">Tipo Noticias</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="jhipsterApp.tipoNoticia.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/nomencladores/tipo-noticia/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="jhipsterApp.tipoNoticia.home.createLabel">Create new Tipo Noticia</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {tipoNoticiaList && tipoNoticiaList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="jhipsterApp.tipoNoticia.tipoNoticia">Tipo Noticia</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {tipoNoticiaList.map((tipoNoticia, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>{tipoNoticia.tipoNoticia}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/nomencladores/tipo-noticia/${tipoNoticia.id}/edit`}
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
                        to={`/nomencladores/tipo-noticia/${tipoNoticia.id}/delete`}
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
              <Translate contentKey="jhipsterApp.tipoNoticia.home.notFound">No Tipo Noticias found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default TipoNoticia;
