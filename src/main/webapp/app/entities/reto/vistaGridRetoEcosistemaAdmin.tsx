import React, { useEffect, useState } from 'react';
import './publico.css';

import { DataView, DataViewLayoutOptions } from 'primereact/dataview';
import { Rating } from 'primereact/rating';
import { Tag } from 'primereact/tag';
import { Skeleton } from 'primereact/skeleton';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { Link, RouteComponentProps } from 'react-router-dom';
import { TextFormat, Translate } from 'react-jhipster';
import { getEntities, getEntitiesByEcosistema, reset as resetRetos, getEntitiestodosAdmin } from './reto.reducer';
import { APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Alert } from 'reactstrap';
import { Toolbar } from 'primereact/toolbar';
import { Button } from 'primereact/button';
import SpinnerCar from '../loader/spinner';

import { getEntity } from '../ecosistema/ecosistema.reducer';
import { ASC } from 'app/shared/util/pagination.constants';

const VistaGridRetoAdmin = () => {
  const dispatch = useAppDispatch();
  const [forma, setLayout] = useState(null);

  const retoList = useAppSelector(state => state.reto.entities);
  const loading = useAppSelector(state => state.reto.loading);

  const ecosistemaUserEntity = useAppSelector(state => state.ecosistema.entity);

  const account = useAppSelector(state => state.authentication.account);

  const [retos, setRetos] = useState([]);

  useEffect(() => {
    dispatch(getEntitiestodosAdmin());

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

  const itemTemplate = (reto, layout1) => {
    if (!reto) {
      return;
    }

    if (layout1 === 'list') return listItem(reto);
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

  const rightToolbarTemplate = () => {
    return (
      <React.Fragment>
        <div className="my-2"></div>
      </React.Fragment>
    );
  };

  const leftToolbarTemplate = () => {
    return (
      <React.Fragment>
        <div className="my-2"></div>
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
            value={retoList}
            header={header}
            layout={forma}
            itemTemplate={itemTemplate}
            emptyMessage="No hay Retos"
            sortField="activo"
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

export default VistaGridRetoAdmin;
