import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITramite } from 'app/shared/model/tramite.model';
import { getEntities } from './tramite.reducer';

export const Tramite = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const tramiteList = useAppSelector(state => state.tramite.entities);
  const loading = useAppSelector(state => state.tramite.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="tramite-heading" data-cy="TramiteHeading">
        <Translate contentKey="jhipsterApp.tramite.home.title">Tramites</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="jhipsterApp.tramite.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/tramite/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="jhipsterApp.tramite.home.createLabel">Create new Tramite</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {tramiteList && tramiteList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="jhipsterApp.tramite.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.tramite.inscripcion">Inscripcion</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.tramite.pruebaExperimental">Prueba Experimental</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.tramite.exmanenEvaluacion">Exmanen Evaluacion</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.tramite.dictamen">Dictamen</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.tramite.concesion">Concesion</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.tramite.denegado">Denegado</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.tramite.reclamacion">Reclamacion</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.tramite.anulacion">Anulacion</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.tramite.fechaNotificacion">Fecha Notificacion</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.tramite.fecaCertificado">Feca Certificado</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.tramite.observacion">Observacion</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {tramiteList.map((tramite, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/tramite/${tramite.id}`} color="link" size="sm">
                      {tramite.id}
                    </Button>
                  </td>
                  <td>{tramite.inscripcion}</td>
                  <td>{tramite.pruebaExperimental}</td>
                  <td>{tramite.exmanenEvaluacion}</td>
                  <td>{tramite.dictamen}</td>
                  <td>{tramite.concesion ? 'true' : 'false'}</td>
                  <td>{tramite.denegado ? 'true' : 'false'}</td>
                  <td>{tramite.reclamacion ? 'true' : 'false'}</td>
                  <td>{tramite.anulacion ? 'true' : 'false'}</td>
                  <td>
                    {tramite.fechaNotificacion ? (
                      <TextFormat type="date" value={tramite.fechaNotificacion} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {tramite.fecaCertificado ? (
                      <TextFormat type="date" value={tramite.fecaCertificado} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{tramite.observacion}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/tramite/${tramite.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/tramite/${tramite.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/tramite/${tramite.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="jhipsterApp.tramite.home.notFound">No Tramites found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Tramite;
