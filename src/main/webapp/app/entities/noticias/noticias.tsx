import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { INoticias } from 'app/shared/model/noticias.model';
import { getEntities } from './noticias.reducer';

export const Noticias = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );

  const noticiasList = useAppSelector(state => state.noticias.entities);
  const loading = useAppSelector(state => state.noticias.loading);
  const totalItems = useAppSelector(state => state.noticias.totalItems);

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
      <h2 id="noticias-heading" data-cy="NoticiasHeading">
        <Translate contentKey="jhipsterApp.noticias.home.title">Noticias</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="jhipsterApp.noticias.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/entidad/noticias/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="jhipsterApp.noticias.home.createLabel">Create new Noticias</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {noticiasList && noticiasList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="jhipsterApp.noticias.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('titulo')}>
                  <Translate contentKey="jhipsterApp.noticias.titulo">Titulo</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('descripcion')}>
                  <Translate contentKey="jhipsterApp.noticias.descripcion">Descripcion</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('urlFoto')}>
                  <Translate contentKey="jhipsterApp.noticias.urlFoto">Url Foto</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('publica')}>
                  <Translate contentKey="jhipsterApp.noticias.publica">Publica</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('publicar')}>
                  <Translate contentKey="jhipsterApp.noticias.publicar">Publicar</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('fechaCreada')}>
                  <Translate contentKey="jhipsterApp.noticias.fechaCreada">Fecha Creada</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.noticias.user">User</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.noticias.ecosistema">Ecosistema</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.noticias.tipoNoticia">Tipo Noticia</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {noticiasList.map((noticias, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/noticias/${noticias.id}`} color="link" size="sm">
                      {noticias.id}
                    </Button>
                  </td>
                  <td>{noticias.titulo}</td>
                  <td>{noticias.descripcion}</td>
                  <td>
                    {noticias.urlFoto ? (
                      <div>
                        {noticias.urlFotoContentType ? (
                          <a onClick={openFile(noticias.urlFotoContentType, noticias.urlFoto)}>
                            <img src={`data:${noticias.urlFotoContentType};base64,${noticias.urlFoto}`} style={{ maxHeight: '30px' }} />
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {noticias.urlFotoContentType}, {byteSize(noticias.urlFoto)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{noticias.publica ? 'true' : 'false'}</td>
                  <td>{noticias.publicar ? 'true' : 'false'}</td>
                  <td>
                    {noticias.fechaCreada ? <TextFormat type="date" value={noticias.fechaCreada} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>{noticias.user ? noticias.user.login : ''}</td>
                  <td>
                    {noticias.ecosistema ? <Link to={`/ecosistema/${noticias.ecosistema.id}`}>{noticias.ecosistema.nombre}</Link> : ''}
                  </td>
                  <td>
                    {noticias.tipoNoticia ? (
                      <Link to={`/tipo-noticia/${noticias.tipoNoticia.id}`}>{noticias.tipoNoticia.tipoNoticia}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/entidad/noticias/${noticias.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/entidad/noticias/${noticias.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`/entidad/noticias/${noticias.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="jhipsterApp.noticias.home.notFound">No Noticias found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={noticiasList && noticiasList.length > 0 ? '' : 'd-none'}>
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

export default Noticias;
