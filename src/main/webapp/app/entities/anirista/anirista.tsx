import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { byteSize, Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAnirista } from 'app/shared/model/anirista.model';
import { getEntities } from './anirista.reducer';

export const Anirista = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const aniristaList = useAppSelector(state => state.anirista.entities);
  const loading = useAppSelector(state => state.anirista.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div className="mt-4">
      <h2 id="anirista-heading" data-cy="AniristaHeading">
        <Translate contentKey="jhipsterApp.anirista.home.title">Aniristas</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="jhipsterApp.anirista.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/entidad/anirista/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="jhipsterApp.anirista.home.createLabel">Create new Anirista</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {aniristaList && aniristaList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="jhipsterApp.anirista.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.anirista.nombre">Nombre</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.anirista.fechaEntrada">Fecha Entrada</Translate>
                </th>

                <th>
                  <Translate contentKey="jhipsterApp.anirista.ecosistema">Ecosistema</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {aniristaList.map((anirista, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/anirista/${anirista.id}`} color="link" size="sm">
                      {anirista.id}
                    </Button>
                  </td>
                  <td>{anirista.nombre}</td>
                  <td>
                    {anirista.fechaEntrada ? <TextFormat type="date" value={anirista.fechaEntrada} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>

                  <td>
                    {anirista.ecosistema ? <Link to={`/ecosistema/${anirista.ecosistema.id}`}>{anirista.ecosistema.nombre}</Link> : ''}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/entidad/anirista/${anirista.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/entidad/anirista/${anirista.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/entidad/anirista/${anirista.id}/delete`}
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
              <Translate contentKey="jhipsterApp.anirista.home.notFound">No Aniristas found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Anirista;
