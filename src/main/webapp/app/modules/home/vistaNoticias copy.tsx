import React, { useEffect, useState } from 'react';

import { DataView, DataViewLayoutOptions } from 'primereact/dataview';
import { Rating } from 'primereact/rating';
import { Tag } from 'primereact/tag';
import { Skeleton } from 'primereact/skeleton';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { Link, RouteComponentProps } from 'react-router-dom';
import { TextFormat, Translate } from 'react-jhipster';
import { getNoticiasByEcosistemaId, getNoticias } from 'app/entities/noticias/noticias.reducer';
import { APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Alert, Button } from 'reactstrap';

const VistaNoticia = props => {
  const dispatch = useAppDispatch();
  const [forma, setLayout] = useState(null);

  const noticiasList = useAppSelector(state => state.noticias.entities);
  const loading = useAppSelector(state => state.noticias.loading);

  const [noticias, setNoticias] = useState([]);

  useEffect(() => {
    dispatch(getNoticias());
    setLayout('grid');
    setNoticias(noticiasList);
  }, []);

  useEffect(() => {
    if (!loading) setNoticias(noticiasList);
  }, [loading]);

  const getSeverity = reto => {
    switch (reto.activo) {
      case true:
        return 'success';

      case false:
        return 'danger';

      default:
        return null;
    }
  };

  const getActivo = reto => {
    switch (reto.activo) {
      case true:
        return 'Activo';

      case false:
        return 'Activo';

      default:
        return null;
    }
  };
  const verReto = id => {
    props.history.push('/usuario-panel');
  };

  const listItem = reto => {
    return (
      <div className="col-12 border-round-xl shadow-4 mb-2">
        <div className="flex flex-column xl:flex-row xl:align-items-start p-4 gap-4">
          <img
            className="w-9 sm:w-10rem xl:w-8rem shadow-2 block xl:block mx-auto border-round"
            src={`data:${reto.urlFotoContentType};base64,${reto.urlFoto}`}
            alt={reto.titulo}
          />
          <div className="flex flex-column sm:flex-row justify-content-between align-items-center xl:align-items-start flex-1 gap-4">
            <div className="flex flex-column align-content-end align-items-center sm:align-items-start gap-3">
              <div className="text-base font-bold ">{reto.titulo}</div>

              <div className=" surface-overlay w-full h-3rem mb-2  overflow-hidden text-overflow-ellipsis">{reto.descripcion}</div>

              <div className="flex flex-column sm:flex-row justify-content-between align-items-center gap-3">
                <div className="flex align-items-center gap-3">
                  <span className="flex align-items-center gap-2">
                    <i className="pi pi-calendar"></i>
                    {reto.fechaCreada ? <TextFormat type="date" value={reto.fechaCreada} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </span>
                </div>
              </div>
            </div>
            <div className="flex sm:flex-column align-items-center sm:align-items-end gap-3 sm:gap-2">
              <Button
                tag={Link}
                to={`/reto/retoecosistema/${reto.id} `}
                disabled={reto.activo === false}
                color="primary"
                size="sm"
                data-cy="entityEditButton"
              >
                <FontAwesomeIcon icon="eye" />
                {'   '}
                <span className="d-none d-md-inline"></span>
              </Button>
            </div>
          </div>
        </div>
      </div>
    );
  };

  const gridItem = reto => {
    return (
      <div className="col-12  sm:col-6 lg:col-6 xl:col-4 p-2 ">
        <div className="p-4 flex flex-column align-items-center">
          <div className="flex flex-column align-items-center gap-3 py-5">
            <Link to={`/noticias/ver-noticias`} id="jh-tre" data-cy="ecosistemas">
              <img
                className="mb-1"
                src={`data:${reto.urlFotoContentType};base64,${reto.urlFoto}`}
                alt={reto.titulo}
                style={{ maxHeight: '200px' }}
              />
            </Link>
          </div>
          <div className="text-2xl font-bold text-900">{reto.titulo}</div>

          <div className=" surface-overlay w-full h-3rem mb-2  overflow-hidden text-overflow-ellipsis">{reto.descripcion}</div>

          <div className="flex flex-column sm:flex-row justify-content-between align-items-center gap-3 mb-2">
            <div className="flex align-items-center gap-3">
              <div className="flex align-items-center gap-3">
                <span className="flex align-items-center gap-2">
                  <i className="pi pi-calendar"></i>
                  {reto.fechaCreada ? <TextFormat type="date" value={reto.fechaCreada} format={APP_LOCAL_DATE_FORMAT} /> : null}
                </span>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  };

  const itemTemplate = (reto, layout1) => {
    if (!reto) {
      return;
    }

    if (layout1 === 'list') return listItem(reto);
    else if (layout1 === 'grid') return gridItem(reto);
  };

  const header = () => {
    return <></>;
  };

  return (
    <>
      <div className="mb-4 font-bold text-900 text-5xl text-center ">
        <span className="text-blue-600">Actualidad</span>
      </div>
      <div className="surface-section px-2 py-4 md:px-6 lg:px-8 text-center">
        <div className="flex align-items-center justify-content-center grid">
          {noticiasList && noticiasList.length > 0 ? (
            <DataView
              className="align-items-center justify-content-center grid"
              value={noticias}
              layout={forma}
              itemTemplate={itemTemplate}
              emptyMessage="No hay Noticias."
              lazy
            />
          ) : (
            !loading && <div className="alert alert-warning mt-4">No hay Noticias.</div>
          )}
        </div>
      </div>
    </>
  );
};

export default VistaNoticia;
