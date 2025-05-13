import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IProyectos } from 'app/shared/model/proyectos.model';
import { getEntities } from './proyectos.reducer';

export const Proyectos = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );

  const proyectosList = useAppSelector(state => state.proyectos.entities);
  const loading = useAppSelector(state => state.proyectos.loading);
  const totalItems = useAppSelector(state => state.proyectos.totalItems);

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
    <div className="mt-4">
      <h2 id="proyectos-heading" data-cy="ProyectosHeading">
        <Translate contentKey="jhipsterApp.proyectos.home.title">Proyectos</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="jhipsterApp.proyectos.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/entidad/proyectos/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="jhipsterApp.proyectos.home.createLabel">Create new Proyectos</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {proyectosList && proyectosList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="jhipsterApp.proyectos.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('nombre')}>
                  <Translate contentKey="jhipsterApp.proyectos.nombre">Nombre</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('descripcion')}>
                  <Translate contentKey="jhipsterApp.proyectos.descripcion">Descripcion</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('autor')}>
                  <Translate contentKey="jhipsterApp.proyectos.autor">Autor</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('necesidad')}>
                  <Translate contentKey="jhipsterApp.proyectos.necesidad">Necesidad</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('fechaInicio')}>
                  <Translate contentKey="jhipsterApp.proyectos.fechaInicio">Fecha Inicio</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('fechaFin')}>
                  <Translate contentKey="jhipsterApp.proyectos.fechaFin">Fecha Fin</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('logoUrl')}>
                  <Translate contentKey="jhipsterApp.proyectos.logoUrl">Logo Url</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.proyectos.user">User</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.proyectos.ecosistema">Ecosistema</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.proyectos.tipoProyecto">Tipo Proyecto</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {proyectosList.map((proyectos, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/entidad/proyectos/${proyectos.id}`} color="link" size="sm">
                      {proyectos.id}
                    </Button>
                  </td>
                  <td>{proyectos.nombre}</td>
                  <td>{proyectos.descripcion}</td>
                  <td>{proyectos.autor}</td>
                  <td>{proyectos.necesidad}</td>
                  <td>
                    {proyectos.fechaInicio ? <TextFormat type="date" value={proyectos.fechaInicio} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {proyectos.fechaFin ? <TextFormat type="date" value={proyectos.fechaFin} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {proyectos.logoUrl ? (
                      <div>
                        {proyectos.logoUrlContentType ? (
                          <a onClick={openFile(proyectos.logoUrlContentType, proyectos.logoUrl)}>
                            <img src={`data:${proyectos.logoUrlContentType};base64,${proyectos.logoUrl}`} style={{ maxHeight: '30px' }} />
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {proyectos.logoUrlContentType}, {byteSize(proyectos.logoUrl)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{proyectos.user ? proyectos.user.login : ''}</td>
                  <td>
                    {proyectos.ecosistema ? (
                      <Link to={`/entidad/ecosistema/${proyectos.ecosistema.id}`}>{proyectos.ecosistema.nombre}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {proyectos.tipoProyecto ? (
                      <Link to={`/tipo-proyecto/${proyectos.tipoProyecto.id}`}>{proyectos.tipoProyecto.tipoProyecto}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/entidad/proyectos/${proyectos.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/entidad/proyectos/${proyectos.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`/entidad/proyectos/${proyectos.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="jhipsterApp.proyectos.home.notFound">No Proyectos found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={proyectosList && proyectosList.length > 0 ? '' : 'd-none'}>
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

export default Proyectos;
