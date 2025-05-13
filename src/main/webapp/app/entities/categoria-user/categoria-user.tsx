import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICategoriaUser } from 'app/shared/model/categoria-user.model';
import { getEntities } from './categoria-user.reducer';

export const CategoriaUser = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const categoriaUserList = useAppSelector(state => state.categoriaUser.entities);
  const loading = useAppSelector(state => state.categoriaUser.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div className="mt-6">
      <h2 id="categoria-user-heading" data-cy="CategoriaUserHeading">
        <Translate contentKey="jhipsterApp.categoriaUser.home.title">Categoria Users</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="jhipsterApp.categoriaUser.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/nomencladores/categoria-user/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="jhipsterApp.categoriaUser.home.createLabel">Create new Categoria User</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {categoriaUserList && categoriaUserList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="jhipsterApp.categoriaUser.categoriaUser">Categoria User</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {categoriaUserList.map((categoriaUser, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>{categoriaUser.categoriaUser}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/nomencladores/categoria-user/${categoriaUser.id}/edit`}
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
                        to={`/nomencladores/categoria-user/${categoriaUser.id}/delete`}
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
              <Translate contentKey="jhipsterApp.categoriaUser.home.notFound">No Categoria Users found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default CategoriaUser;
