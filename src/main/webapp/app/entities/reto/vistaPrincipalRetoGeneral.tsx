import './spiner.scss';

import React, { useEffect, useState } from 'react';

import { DataView, DataViewLayoutOptions } from 'primereact/dataview';
import { Rating } from 'primereact/rating';
import { Tag } from 'primereact/tag';
import { Skeleton } from 'primereact/skeleton';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { ProgressSpinner } from 'primereact/progressspinner';

import { Link, RouteComponentProps } from 'react-router-dom';
import { TextFormat, Translate, getSortState } from 'react-jhipster';
import { IReto } from 'app/shared/model/reto.model';
import {
  getEntitiesByEcosistema1,
  getEntitiesByEcosistemabyIdUser,
  reset,
  getEntitiesByEcosistemaIdbyActivoDeTodoslosEcosistemasUSerLogueado,
} from './reto.reducer';
import { APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Alert, Button } from 'reactstrap';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import Cargando from '../loader/cargando';
import SpinnerCar from '../loader/spinner';

const vistaPrincipalRetoGeneral = props => {
  const dispatch = useAppDispatch();
  const [forma, setLayout] = useState(null);
  const retoList = useAppSelector(state => state.reto.entities);
  const loading = useAppSelector(state => state.reto.loading);
  const totalItems = useAppSelector(state => state.reto.totalItems);

  const [retos, setRetos] = useState([]);

  useEffect(() => {
    dispatch(reset());
    dispatch(getEntitiesByEcosistemaIdbyActivoDeTodoslosEcosistemasUSerLogueado(props.milista));

    setLayout('list');
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
    props.history.push(`/usuario-panel/${props.index}`);
  };

  const listItem = reto => {
    return (
      <div className="col-12 border-round-xl  mb-2">
        <div className="flex flex-column lg:flex-row xl:flex-row xl:align-items-start p-4 gap-4">
          <div className="flex flex-column ">
            <img
              className="w-full h-14rem  shadow-2  sm:w-full sm:14rem md:w-10rem md:h-8rem"
              src={`content/uploads/${reto.urlFotoContentType}`}
              alt={reto.reto}
            />
            <div className=" font-bold text-base ">{reto.ecosistema.nombre}</div>
          </div>
          <div className="flex flex-column sm:flex-row justify-content-between align-items-center xl:align-items-start flex-1 gap-4">
            <div className="flex flex-column align-content-end align-items-center sm:align-items-start gap-3">
              <div className=" font-bold text-base ">{reto.reto}</div>

              <div
                className="
            surface-overlay 
            w-full 
            h-3rem 
            mb-2  
            overflow-hidden 
            text-overflow-ellipsis"
              >
                {reto.descripcion}
              </div>

              <div className="flex flex-column sm:flex-row justify-content-between align-items-center gap-3">
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
                <div className="flex align-items-center gap-3">
                  <span className="flex align-items-center gap-2">
                    <i className="pi pi-eye"></i>
                    {reto.visto}
                  </span>
                </div>
                <div className="flex align-items-center gap-3">
                  <span className="flex align-items-center gap-2">
                    <i className="pi pi-tag"></i>
                    {reto?.ideas?.length > 1 ? (
                      <span className="font-semibold">{reto?.ideas?.length} Ideas </span>
                    ) : (
                      <span className="font-semibold">{reto?.ideas?.length} Idea </span>
                    )}
                  </span>
                </div>
              </div>
            </div>

            <div className="flex sm:flex-column align-items-center sm:align-items-end gap-3 sm:gap-2">
              <Button
                tag={Link}
                to={`/entidad/reto/reto_idea/${reto.id}/${props.index} `}
                color="primary"
                size="sm"
                data-cy="entityEditButton"
              >
                <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline"></span>
              </Button>
            </div>
          </div>
        </div>
      </div>
    );
  };

  const gridItem = reto => {
    return (
      <div className="col-12  sm:col-6 lg:col-12 xl:col-4 p-2 ">
        <div className="p-4 border-1 surface-border surface-card border-round">
          <div className="flex flex-column align-items-center gap-3 py-5">
            <img
              className="w-9 h-6 sm:w-16rem xl:w-10rem shadow-2 block xl:block mx-auto border-round"
              src={`data:${reto.urlFotoContentType};base64,${reto.urlFoto}`}
              alt={reto.reto}
            />
          </div>
          <div className="text-2xl font-bold text-900">{reto.reto}</div>

          <div className=" w-auto text-800 text-2xl mb-1  ">{reto.descripcion}</div>

          <div className=" w-auto text-500 text-2xl mb-1  ">Motivación: {reto.motivacion}</div>

          <div className="flex flex-column sm:flex-row justify-content-between align-items-center gap-3 mb-2">
            <div className="flex align-items-center gap-3">
              <span className="flex align-items-center gap-2">
                <i className="pi pi-eye"></i>
                {reto.visto}
              </span>
            </div>
            <span className="flex align-items-center gap-2">
              <i className="pi pi-tag"></i>
              <span className="font-semibold">{reto?.ideas?.length} Ideas </span>
            </span>

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

          <div className="flex align-items-center justify-content-end">
            <Tag value={getActivo(reto)} severity={getSeverity(reto)}></Tag>
            <Button icon="pi pi-eye" className="p-button ml-2" disabled={reto.activo === false}></Button>
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
      {retoList && retoList.length > 0 ? (
        <DataView value={retos} layout={forma} itemTemplate={itemTemplate} emptyMessage="No hay Retos" sortField="fechaInicio" lazy />
      ) : (
        <div className="col-12 border-round-xl  mb-2">
          {loading ? (
            <SpinnerCar />
          ) : (
            <div className="col-12 border-round-xl  mb-2">
              <div className="flex flex-column lg:flex-row xl:flex-row xl:align-items-start ">
                <div className="flex flex-column w-full">
                  <div className="alert alert-warning mt-4">No hay Retos.</div>
                </div>
              </div>
            </div>
          )}
        </div>
      )}
    </>
  );
};

export default vistaPrincipalRetoGeneral;
