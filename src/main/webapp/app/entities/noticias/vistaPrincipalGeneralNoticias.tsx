import React, { useEffect, useState } from 'react';

import { DataView, DataViewLayoutOptions } from 'primereact/dataview';
import { Rating } from 'primereact/rating';
import { Tag } from 'primereact/tag';
import { Skeleton } from 'primereact/skeleton';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { Link, RouteComponentProps } from 'react-router-dom';
import { TextFormat, Translate } from 'react-jhipster';
import { getEntitiesByEcosistemaIdbyPublicoDeTodoslosEcosistemasUSerLogueado, reset } from './noticias.reducer';
import { APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Alert, Button } from 'reactstrap';
import SpinnerCar from '../loader/spinner';

const vistaPrincipalGeneralNoticias = props => {
  const dispatch = useAppDispatch();
  const [forma, setLayout] = useState(null);
  const account = useAppSelector(state => state.authentication.account);

  const noticiasList = useAppSelector(state => state.noticias.entities);
  const loading = useAppSelector(state => state.noticias.loading);

  const [noticias, setNoticias] = useState([]);

  useEffect(() => {
    dispatch(reset());

    dispatch(getEntitiesByEcosistemaIdbyPublicoDeTodoslosEcosistemasUSerLogueado(props.milista));

    setLayout(props.layout);
  }, []);

  useEffect(() => {
    if (!loading) setNoticias(noticiasList);
  }, [loading]);

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
      <div className="col-12 border-round-xl  mb-2">
        <div className="flex flex-column xl:flex-row xl:align-items-start p-4 gap-4">
          <img
            className="w-full h-14rem  shadow-2  sm:w-full sm:14rem md:w-10rem md:h-8rem"
            src={`content/uploads/${reto.urlFotoContentType}`}
            alt={reto.titulo}
          />
          <div className="flex flex-column sm:flex-row justify-content-between align-items-center xl:align-items-start flex-1 gap-4">
            <div className="flex flex-column align-content-end align-items-center sm:align-items-start gap-3">
              <div className="text-base font-bold ">{reto.titulo}</div>

              <div
                className="
              surface-overlay 
              w-full 
              h-3rem 
              mb-2  
              overflow-hidden 
              text-overflow-ellipsis
              "
              >
                {reto.descripcion}
              </div>

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
                to={`/entidad/noticias/ver-noticia/${reto.id}/${props.index} `}
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
      <div className="col-12  border-round-xl  mb-2 ">
        <div className="p-4 border-1 surface-border surface-card border-round">
          <div className="flex flex-column align-items-center gap-3 py-5">
            <Link to={`/noticias/ver-noticias`} id="jh-tre" data-cy="ecosistemas">
              <img
                className="w-full h-14rem  shadow-2  sm:w-full sm:14rem md:w-10rem md:h-8rem"
                src={`data:${reto.urlFotoContentType};base64,${reto.urlFoto}`}
                alt={reto.titulo}
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
      {noticiasList && noticiasList.length > 0 ? (
        <DataView value={noticias} layout={forma} itemTemplate={itemTemplate} emptyMessage="No hay Noticias" lazy />
      ) : loading ? (
        <SpinnerCar />
      ) : (
        <div className="col-12 border-round-xl  mb-2">
          <div className="flex flex-column lg:flex-row xl:flex-row xl:align-items-start ">
            <div className="flex flex-column w-full">
              <div className="alert alert-warning mt-4">No hay Noticias.</div>
            </div>
          </div>
        </div>
      )}
    </>
  );
};

export default vistaPrincipalGeneralNoticias;
