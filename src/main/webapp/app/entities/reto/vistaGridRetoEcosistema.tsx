import React, { useEffect, useState } from 'react';
import './publico.css';

import { DataView, DataViewLayoutOptions } from 'primereact/dataview';
import { Rating } from 'primereact/rating';
import { Tag } from 'primereact/tag';
import { Skeleton } from 'primereact/skeleton';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { Link, RouteComponentProps } from 'react-router-dom';
import { TextFormat, Translate } from 'react-jhipster';
import { getEntitiesByEcosistema, reset as resetRetos } from './reto.reducer';
import { APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Alert } from 'reactstrap';
import { Toolbar } from 'primereact/toolbar';
import { Button } from 'primereact/button';
import SpinnerCar from '../loader/spinner';

import { getEntity } from '../../entities/ecosistema/ecosistema.reducer';
import { ASC } from 'app/shared/util/pagination.constants';

const VistaGridReto = (props: RouteComponentProps<{ id: string; index: string }>) => {
  const dispatch = useAppDispatch();
  const [forma, setLayout] = useState(null);

  const retoList = useAppSelector(state => state.reto.entities);
  const loading = useAppSelector(state => state.reto.loading);

  const ecosistemaUserEntity = useAppSelector(state => state.ecosistema.entity);

  const account = useAppSelector(state => state.authentication.account);

  const [retos, setRetos] = useState([]);

  useEffect(() => {
    dispatch(getEntitiesByEcosistema(props.match.params.id));
    dispatch(getEntity(props.match.params.id));
    setLayout('grid');
  }, []);

  useEffect(() => {
    if (!loading) setRetos(retoList);
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
        return 'No Activo';

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
          <div className="flex flex-column ">
            <img
              className="w-9 sm:w-10rem xl:w-8rem shadow-2 block xl:block mx-auto border-round"
              src={`content/uploads/${reto.urlFotoContentType}`}
              alt={reto.reto}
            />
          </div>
          <div className="flex flex-column sm:flex-row justify-content-between align-items-center xl:align-items-start flex-1 gap-4">
            <div className="flex flex-column align-content-end align-items-center sm:align-items-start gap-3">
              <div className=" font-bold text-base ">{reto.reto}</div>

              <div className="surface-overlay w-full h-3rem mb-2  overflow-hidden text-overflow-ellipsis">{reto.descripcion}</div>

              <div className=" flex flex-row sm:flex-row justify-content-between align-items-center gap-3">
                <div className="flex align-items-center gap-3">
                  <span className="flex align-items-center gap-2">
                    <i className="pi pi-calendar"></i>
                    {reto.fechaInicio ? <TextFormat type="date" value={reto.fechaInicio} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </span>
                </div>
                <div className="flex align-items-center gap-3">
                  <span className="flex align-items-center gap-2">
                    <i className="pi pi-calendar-times"></i>
                    {reto.fechaFin ? <TextFormat type="date" value={reto.fechaFin} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </span>
                </div>
              </div>
            </div>

            <div className="flex flex-column align-items-center sm:align-items-end gap-1 sm:gap-2"></div>
          </div>
        </div>
      </div>
    );
  };

  const renderDevRibbon = reto => (reto.publico === true ? <div className="curso"></div> : null);

  const gridItem = reto => {
    return (
      <div className="flex md:justify-content-center sm:justify-content-center col-12 sm:col-12 md:col-6 lg:col-6 xl:col-3 mt-4">
        <div className=" h-26rem w-24rem max-w-30rem max-h-30rem p-4 border-round-xl shadow-4 mb-2 relative ">
          <div className={` ${reto.publico && 'curso'}`}></div>

          <div className="flex flex-column align-items-center gap-3 ">
            <img className=" h-13rem w-full  border-round" src={`content/uploads/${reto.urlFotoContentType}`} alt={reto.reto} />
          </div>

          <div className="text-base text-sm font-bold text-900 mb-2 mt-2">{reto.reto}</div>

          <div className=" surface-overlay w-20rem h-4rem  overflow-hidden text-overflow-ellipsis text-justify  flex mb-8  absolute bottom-0   text-500 text-sm  ">
            Motivación: {reto.motivacion}
          </div>
          <div className="flex flex-column align-content-end align-items-center sm:align-items-start gap-3">
            <div className="flex align-items-center justify-content-end gap-4 mb-3 mt-2 absolute bottom-0 ml-8 text-sm">
              <span className="flex align-items-center gap-2">
                <i className="pi pi-eye"></i>
                {reto.visto}
              </span>
              <span className="flex align-items-center gap-2">
                <i className="pi pi-tag"></i>
                {reto?.ideas?.length > 1 ? (
                  <span className="font-semibold">{reto?.ideas?.length} Ideas </span>
                ) : (
                  <span className="font-semibold">{reto?.ideas?.length} Idea </span>
                )}
              </span>
            </div>

            <div className="flex align-items-center justify-content-end gap-4 mb-6 mt-2 absolute bottom-0 ml-1">
              <span className="flex align-items-center gap-2 text-sm">
                <i className="pi pi-calendar"></i>
                {reto.fechaInicio ? <TextFormat type="date" value={reto.fechaInicio} format={APP_LOCAL_DATE_FORMAT} /> : null}
              </span>
              <span className="flex align-items-center gap-2 text-sm">
                <i className="pi pi-calendar-times"></i>
                {reto.fechaFin ? <TextFormat type="date" value={reto.fechaFin} format={APP_LOCAL_DATE_FORMAT} /> : null}
              </span>
            </div>
          </div>

          <div className="flex align-items-center justify-content-end gap-4 mb-3 mt-2 absolute bottom-0 left-2 ">
            <Tag value={getActivo(reto)} severity={getSeverity(reto)}></Tag>
          </div>
          <div className="flex justify-content-end absolute bottom-0 right-0 mb-4 mr-4">
            {(reto.activo || reto.publico) && (
              <Link to={`/entidad/reto/reto_ideas/${reto.id}/${props.match.params.id}/${props.match.params.index} `}>
                <div className="flex justify-content-start">
                  <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline"></span>
                </div>
              </Link>
            )}
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

  const renderHeader = () => {
    return (
      <div className="grid grid-nogutter">
        <div className="col-4" style={{ textAlign: 'left' }}>
          <h5 className="m-0 text-blue-600">Retos</h5>
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
  const atrasvista = () => {
    props.history.push(`/entidad/reto/retoecosistema/${props.match.params.id}/${props.match.params.index}`);
  };
  const rightToolbarTemplate = () => {
    return (
      <React.Fragment>
        <div className="my-2">
          {(ecosistemaUserEntity.user?.login === account?.login && account?.authorities?.find(rol => rol === 'ROLE_ADMINECOSISTEMA')) ||
          (ecosistemaUserEntity.users?.find(user => user.id === account?.id) &&
            account?.authorities?.find(rol => rol === 'ROLE_GESTOR')) ? (
            <Button label="Cambiar Vista" icon="pi pi-arrow-up" className="p-button-primary mr-2" onClick={atrasvista} />
          ) : null}
        </div>
      </React.Fragment>
    );
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
  const order = () => {
    return 4;
  };

  const header = renderHeader();
  return (
    <>
      <div className="card mt-4 mb-4">
        <Toolbar className="mt-1" left={leftToolbarTemplate} right={rightToolbarTemplate}></Toolbar>
        {retoList && retoList.length > 0 ? (
          <DataView
            className="mt-4 mb-4"
            value={retos}
            header={header}
            layout={forma}
            itemTemplate={itemTemplate}
            emptyMessage="No hay Retos"
            sortField="fechaInicio"
            sortOrder={-1}
            lazy
          />
        ) : loading ? (
          <SpinnerCar />
        ) : (
          <div className="alert alert-warning mt-4">No hay Retos.</div>
        )}
      </div>
    </>
  );
};

export default VistaGridReto;
