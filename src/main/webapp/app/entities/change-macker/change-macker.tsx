import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IChangeMacker } from 'app/shared/model/change-macker.model';
import { getEntities } from './change-macker.reducer';

export const ChangeMacker = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const changeMackerList = useAppSelector(state => state.changeMacker.entities);
  const loading = useAppSelector(state => state.changeMacker.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div className="mt-4">
      <h2 id="change-macker-heading" data-cy="ChangeMackerHeading">
        <Translate contentKey="jhipsterApp.changeMacker.home.title">Change Mackers</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="jhipsterApp.changeMacker.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/entidad/change-macker/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="jhipsterApp.changeMacker.home.createLabel">Create new Change Macker</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {changeMackerList && changeMackerList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="jhipsterApp.changeMacker.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.changeMacker.foto">Foto</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.changeMacker.nombre">Nombre</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.changeMacker.tema">Tema</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.changeMacker.descripcion">Descripcion</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {changeMackerList.map((changeMacker, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/change-macker/${changeMacker.id}`} color="link" size="sm">
                      {changeMacker.id}
                    </Button>
                  </td>
                  <td>
                    {changeMacker.foto ? (
                      <div>
                        {changeMacker.fotoContentType ? (
                          <a onClick={openFile(changeMacker.fotoContentType, changeMacker.foto)}>
                            <img src={`data:${changeMacker.fotoContentType};base64,${changeMacker.foto}`} style={{ maxHeight: '30px' }} />
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {changeMacker.fotoContentType}, {byteSize(changeMacker.foto)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{changeMacker.nombre}</td>
                  <td>{changeMacker.tema}</td>
                  <td>{changeMacker.descripcion}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/entidad/change-macker/${changeMacker.id}`}
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
                        to={`/entidad/change-macker/${changeMacker.id}/edit`}
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
                        to={`/entidad/change-macker/${changeMacker.id}/delete`}
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
              <Translate contentKey="jhipsterApp.changeMacker.home.notFound">No Change Mackers found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default ChangeMacker;
