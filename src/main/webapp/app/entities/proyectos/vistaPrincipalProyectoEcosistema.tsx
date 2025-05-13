import React, { useEffect, useState } from 'react';

import { DataView, DataViewLayoutOptions } from 'primereact/dataview';
import { Rating } from 'primereact/rating';
import { Tag } from 'primereact/tag';
import { Skeleton } from 'primereact/skeleton';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { Link, RouteComponentProps } from 'react-router-dom';
import { TextFormat, Translate } from 'react-jhipster';

import { APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Alert, Button } from 'reactstrap';
import { getEntities, getEntitiesEcosistema } from './proyectos.reducer';
import SpinnerCar from '../loader/spinner';

const VistaPrincipalProyectoEcosistema = props => {
  const dispatch = useAppDispatch();
  const [forma, setLayout] = useState(null);

  const proyectosList = useAppSelector(state => state.proyectos.entities);
  const loading = useAppSelector(state => state.proyectos.loading);

  const [proyectos, setProyectos] = useState([]);

  useEffect(() => {
    dispatch(getEntitiesEcosistema(props.id));

    setLayout('list');
  }, []);

  useEffect(() => {
    if (!loading) setProyectos(proyectosList);
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

  const listItem = proyecto => {
    return (
      <div className="col-12 border-round-xl  mb-2">
        <div className="flex flex-column xl:flex-row xl:align-items-center p-2 gap-4">
          {proyecto.logoUrlContentType ? (
            <img
              className="w-full h-14rem  shadow-2  sm:w-full sm:14rem md:w-10rem md:h-8rem"
              src={`content/uploads/${proyecto.logoUrlContentType}`}
            />
          ) : (
            <img className="w-full h-14rem  sm:w-full sm:14rem md:w-10rem md:h-10rem" src={`content/uploads/ProyectosSF.png`} />
          )}

          <div className="flex flex-column sm:flex-row justify-content-between align-items-center xl:align-items-start flex-1 gap-4">
            <div className="flex flex-column align-content-end align-items-center sm:align-items-start gap-3">
              <div className="text-base font-bold ">{proyecto.nombre}</div>

              <div className=" surface-overlay w-full h-3rem mb-2  overflow-hidden text-overflow-ellipsis">{proyecto.descripcion}</div>

              <div className=" w-auto text-lg mb-1  ">Tipo Proyecto: {proyecto?.tipoProyecto?.tipoProyecto}</div>

              <div className="flex flex-column sm:flex-row justify-content-between align-content-center gap-3">
                <div className="flex align-content-start gap-3">
                  <span className="flex align-items-center gap-2">
                    <i className="pi pi-calendar"></i>
                    {proyecto.fechaInicio ? <TextFormat type="date" value={proyecto.fechaInicio} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </span>
                </div>
                <div className="flex align-content-end gap-3">
                  <span className="flex align-items-center gap-2">
                    <i className="pi pi-calendar-times"></i>
                    {proyecto.fechaFin ? <TextFormat type="date" value={proyecto.fechaFin} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </span>
                </div>
              </div>
            </div>
            <div className="flex flex-column align-content-end align-items-center sm:align-items-center gap-3">
              <Button
                tag={Link}
                to={`/entidad/proyectos/proyectos_ecosistema/${props.id}/${props.index}`}
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
              className="w-9 sm:w-16rem xl:w-10rem shadow-2 block xl:block mx-auto border-round"
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
      {proyectosList && proyectosList.length > 0 ? (
        <DataView
          value={proyectos}
          layout={forma}
          lazy
          itemTemplate={itemTemplate}
          emptyMessage="No hay Proyectos"
          sortField="fechaInicio"
        />
      ) : loading ? (
        <SpinnerCar />
      ) : (
        <div className="alert alert-warning mt-4">No hay Proyectos.</div>
      )}
    </>
  );
};

export default VistaPrincipalProyectoEcosistema;
