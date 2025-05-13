import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { byteSize, Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IInnovacionRacionalizacion } from 'app/shared/model/innovacion-racionalizacion.model';
import { getEntities } from './innovacion-racionalizacion.reducer';

export const InnovacionRacionalizacion = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );

  const innovacionRacionalizacionList = useAppSelector(state => state.innovacionRacionalizacion.entities);
  const loading = useAppSelector(state => state.innovacionRacionalizacion.loading);
  const totalItems = useAppSelector(state => state.innovacionRacionalizacion.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (props.location.search !== endURL) {
      props.history.push(`${props.location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(props.location.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [props.location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  const { match } = props;

  return (
    <div>
      <h2 id="innovacion-racionalizacion-heading" data-cy="InnovacionRacionalizacionHeading">
        <Translate contentKey="jhipsterApp.innovacionRacionalizacion.home.title">Innovacion Racionalizacions</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="jhipsterApp.innovacionRacionalizacion.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/entidad/innovacion-racionalizacion/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="jhipsterApp.innovacionRacionalizacion.home.createLabel">Create new Innovacion Racionalizacion</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {innovacionRacionalizacionList && innovacionRacionalizacionList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="jhipsterApp.innovacionRacionalizacion.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('tematica')}>
                  <Translate contentKey="jhipsterApp.innovacionRacionalizacion.tematica">Tematica</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('fecha')}>
                  <Translate contentKey="jhipsterApp.innovacionRacionalizacion.fecha">Fecha</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('vp')}>
                  <Translate contentKey="jhipsterApp.innovacionRacionalizacion.vp">Vp</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('autores')}>
                  <Translate contentKey="jhipsterApp.innovacionRacionalizacion.autores">Autores</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('numeroIdentidad')}>
                  <Translate contentKey="jhipsterApp.innovacionRacionalizacion.numeroIdentidad">Numero Identidad</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('observacion')}>
                  <Translate contentKey="jhipsterApp.innovacionRacionalizacion.observacion">Observacion</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('aprobada')}>
                  <Translate contentKey="jhipsterApp.innovacionRacionalizacion.aprobada">Aprobada</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('publico')}>
                  <Translate contentKey="jhipsterApp.innovacionRacionalizacion.publico">Publico</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.innovacionRacionalizacion.tipoIdea">Tipo Idea</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {innovacionRacionalizacionList.map((innovacionRacionalizacion, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/entidad/innovacion-racionalizacion/${innovacionRacionalizacion.id}`} color="link" size="sm">
                      {innovacionRacionalizacion.id}
                    </Button>
                  </td>
                  <td>{innovacionRacionalizacion.tematica}</td>
                  <td>
                    {innovacionRacionalizacion.fecha ? (
                      <TextFormat type="date" value={innovacionRacionalizacion.fecha} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{innovacionRacionalizacion.vp}</td>
                  <td>{innovacionRacionalizacion.autores}</td>
                  <td>{innovacionRacionalizacion.numeroIdentidad}</td>
                  <td>{innovacionRacionalizacion.observacion}</td>
                  <td>{innovacionRacionalizacion.aprobada ? 'true' : 'false'}</td>
                  <td>{innovacionRacionalizacion.publico ? 'true' : 'false'}</td>
                  <td>
                    {innovacionRacionalizacion.tipoIdea ? (
                      <Link to={`/tipo-idea/${innovacionRacionalizacion.tipoIdea.id}`}>{innovacionRacionalizacion.tipoIdea.tipoIdea}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/entidad/innovacion-racionalizacion/${innovacionRacionalizacion.id}`}
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
                        to={`/entidad/innovacion-racionalizacion/${innovacionRacionalizacion.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`/innovacion-racionalizacion/${innovacionRacionalizacion.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="jhipsterApp.innovacionRacionalizacion.home.notFound">No Innovacion Racionalizacions found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={innovacionRacionalizacionList && innovacionRacionalizacionList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default InnovacionRacionalizacion;
