import React, { useEffect, useState } from 'react';

import { DataView, DataViewLayoutOptions } from 'primereact/dataview';
import { Rating } from 'primereact/rating';
import { Tag } from 'primereact/tag';
import { Skeleton } from 'primereact/skeleton';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { Link, RouteComponentProps } from 'react-router-dom';
import { TextFormat, Translate } from 'react-jhipster';
import { getNoticiasByEcosistemaId } from './noticias.reducer';
import { APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Alert } from 'reactstrap';
import { Toolbar } from 'primereact/toolbar';
import { Button } from 'primereact/button';

import { getEntity } from '../../entities/ecosistema/ecosistema.reducer';
import SpinnerCar from '../loader/spinner';

const VistaGridNoticias = (props: RouteComponentProps<{ id: string; index: string }>) => {
  const dispatch = useAppDispatch();
  const [forma, setLayout] = useState(null);

  const noticiasList = useAppSelector(state => state.noticias.entities);
  const loading = useAppSelector(state => state.noticias.loading);
  const account = useAppSelector(state => state.authentication.account);
  const ecosistemaUserEntity = useAppSelector(state => state.ecosistema.entity);

  const [noticias, setNoticias] = useState([]);

  useEffect(() => {
    dispatch(getNoticiasByEcosistemaId(props.match.params.id));
    setLayout('grid');
    dispatch(getEntity(props.match.params.id));
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
        <div className="flex flex-column  justify-content-center align-items-center">
          <img
            className="w-full h-14rem  shadow-2  sm:w-full sm:14rem md:w-10rem md:h-8rem"
            src={`data:${reto.urlFotoContentType};base64,${reto.urlFoto}`}
            alt={reto.titulo}
          />
        </div>
        <div className="flex flex-column xl:flex-row xl:align-items-start p-4 gap-4">
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
            <div className="flex sm:flex-column align-items-center sm:align-items-end gap-3 sm:gap-2"></div>
          </div>
        </div>
      </div>
    );
  };

  const gridItem = reto => {
    return (
      <div className="flex  col-12 sm:col-12 md:col-6 lg:col-6 xl:col-3 pl-2 ">
        <div className=" h-26rem w-24rem max-w-30rem max-h-30rem p-4 border-round-xl shadow-4 mb-2 relative  ">
          <div className="flex flex-column align-items-center gap-1 py-1">
            <img className=" h-13rem w-full  border-round" src={`content/uploads/${reto.urlFotoContentType}`} alt={reto.titulo} />
          </div>

          <div className="text-sm font-bold text-900 mt-2">{reto.titulo}</div>

          <div className="surface-overlay w-full h-6rem mb-2  overflow-hidden text-overflow-ellipsis">{reto.descripcion}</div>

          <div className="flex  sm:flex-row justify-content-between align-items-center gap-8 mb-2 absolute bottom-0 left-3">
            <span className="flex align-items-center gap-2">
              <i className="pi pi-calendar"></i>
              {reto.fechaCreada ? <TextFormat type="date" value={reto.fechaCreada} format={APP_LOCAL_DATE_FORMAT} /> : null}
            </span>
          </div>
          <div className="flex justify-content-end absolute bottom-0 right-0 mb-2 mr-5">
            <Link to={`/entidad/noticias/noticias/${reto.id}/${props.match.params.id}/${props.match.params.index}`}>
              <div className="flex justify-content-start">
                <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline"></span>
              </div>
            </Link>
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

  const atras = () => {
    props.history.push(`/usuario-panel/${props.match.params.index}`);
  };
  const leftToolbarTemplate = () => {
    return (
      <React.Fragment>
        <div className="my-2">
          <Button label="Atrás" icon="pi pi-arrow-left" className="p-button-secondary mr-2" onClick={atras} />
        </div>
      </React.Fragment>
    );
  };
  const atrasvista = () => {
    props.history.push(`/entidad/noticias/noticias-crud/${props.match.params.id}/${props.match.params.index}`);
  };
  const rightToolbarTemplate = () => {
    return (
      <React.Fragment>
        <div className="my-2">
          {((ecosistemaUserEntity.user?.login === account.login && account?.authorities?.find(rol => rol === 'ROLE_ADMINECOSISTEMA')) ||
            (ecosistemaUserEntity.users?.find(user => user.id === account?.id) &&
              account?.authorities?.find(rol => rol === 'ROLE_GESTOR'))) && (
            <Button label="Cambiar Vista" icon="pi pi-arrow-up" className="p-button-primary mr-2" onClick={atrasvista} />
          )}
        </div>
      </React.Fragment>
    );
  };
  const renderHeader = () => {
    return (
      <div className="grid grid-nogutter">
        <div className="col-4" style={{ textAlign: 'left' }}>
          <h5 className="m-0 text-blue-600">Noticias</h5>
        </div>
        <div className="col-4" style={{ textAlign: 'center' }}>
          <h5 className="m-0 ">
            <span className="text-blue-600"> Ecosistema: {ecosistemaUserEntity.nombre}</span>
          </h5>
        </div>
        <div className="col-4" style={{ textAlign: 'left' }}></div>
      </div>
    );
  };
  const header = renderHeader();

  return (
    <>
      <div className="card mt-4 mb-4">
        <Toolbar className="mt-1" left={leftToolbarTemplate} right={rightToolbarTemplate}></Toolbar>
        {noticiasList && noticiasList.length > 0 ? (
          <DataView
            className=" mt-4 mb-4"
            value={noticias}
            header={header}
            layout={forma}
            itemTemplate={itemTemplate}
            emptyMessage="No hay Noticias"
            lazy
          />
        ) : !loading ? (
          <SpinnerCar />
        ) : (
          <div className="alert alert-warning mt-4">No hay Noticias.</div>
        )}
      </div>
    </>
  );
};

export default VistaGridNoticias;
