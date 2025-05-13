import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IParticipantes } from 'app/shared/model/participantes.model';
import { getEntities } from './participantes.reducer';

export const Participantes = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const participantesList = useAppSelector(state => state.participantes.entities);
  const loading = useAppSelector(state => state.participantes.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="participantes-heading" data-cy="ParticipantesHeading">
        <Translate contentKey="jhipsterApp.participantes.home.title">Participantes</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="jhipsterApp.participantes.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/participantes/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="jhipsterApp.participantes.home.createLabel">Create new Participantes</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {participantesList && participantesList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="jhipsterApp.participantes.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.participantes.nombre">Nombre</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.participantes.telefono">Telefono</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.participantes.correo">Correo</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.participantes.proyectos">Proyectos</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {participantesList.map((participantes, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/participantes/${participantes.id}`} color="link" size="sm">
                      {participantes.id}
                    </Button>
                  </td>
                  <td>{participantes.nombre}</td>
                  <td>{participantes.telefono}</td>
                  <td>{participantes.correo}</td>
                  <td>
                    {participantes.proyectos ? (
                      <Link to={`/proyectos/${participantes.proyectos.id}`}>{participantes.proyectos.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/participantes/${participantes.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/participantes/${participantes.id}/edit`}
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
                        to={`/participantes/${participantes.id}/delete`}
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
              <Translate contentKey="jhipsterApp.participantes.home.notFound">No Participantes found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Participantes;
