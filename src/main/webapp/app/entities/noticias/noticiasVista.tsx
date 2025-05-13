import React, { useEffect } from 'react';
import { RouteComponentProps } from 'react-router-dom';
import { Alert } from 'reactstrap';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IEcosistema } from 'app/shared/model/ecosistema.model';
import { getEntities as getEcosistemas } from 'app/entities/ecosistema/ecosistema.reducer';
import { ITipoNoticia } from 'app/shared/model/tipo-noticia.model';
import { getEntities as getTipoNoticias } from 'app/entities/tipo-noticia/tipo-noticia.reducer';
import { INoticias } from 'app/shared/model/noticias.model';
import { getEntity, updateEntity, createEntity, reset } from './noticias.reducer';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { TextFormat } from 'react-jhipster';
import { APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { Accordion, AccordionTab } from 'primereact/accordion';
import { AsyncThunkAction } from '@reduxjs/toolkit';
import { AxiosResponse } from 'axios';
import { Skeleton } from 'primereact/skeleton';
import { Toolbar } from 'primereact/toolbar';
import { Button } from 'primereact/button';
import SpinnerCar from '../loader/spinner';

const noticiasVista = (props: RouteComponentProps<{ id: any }>) => {
  const dispatch = useAppDispatch();
  const ecosistemas = useAppSelector(state => state.ecosistema.entities);
  const tipoNoticias = useAppSelector(state => state.tipoNoticia.entities);
  const noticiasEntity = useAppSelector(state => state.noticias.entity);
  const loading = useAppSelector(state => state.noticias.loading);
  const updating = useAppSelector(state => state.noticias.updating);
  const updateSuccess = useAppSelector(state => state.noticias.updateSuccess);

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);
  const atras = () => {
    props.history.push(`/`);
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

  return (
    <>
      <div className="card mt-4 mb-4">
        <Toolbar className="mt-1 flex flex-row " left={leftToolbarTemplate}></Toolbar>

        {loading ? (
          <SpinnerCar />
        ) : (
          <div className="grid  grid-nogutter 4surface-0 text-800">
            <div className="col-12 md:col-6">
              <div className="flex p-4 sm:justify-content-center text-center md:text-left flex align-items-center ">
                <section className="flex flex-column">
                  <h1 className="text-6xl font-bold text-gray-900 line-height-2">
                    <span className="font-light block">{noticiasEntity.titulo}</span>
                  </h1>
                  <p className="font-normal text-2xl line-height-3  text-gray-700">
                    Autor: {noticiasEntity?.user?.firstName + ' ' + noticiasEntity?.user?.lastName}
                  </p>
                  <p className="font-normal text-2xl line-height-3 text-gray-700">
                    Fecha de creada:{' '}
                    {noticiasEntity.fechaCreada ? (
                      <TextFormat type="date" value={noticiasEntity.fechaCreada} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </p>
                  <p className="font-normal text-2xl line-height-3 md:mt-3 text-gray-700">
                    Ecosistema: {noticiasEntity?.ecosistema?.nombre}
                  </p>
                  <br></br>
                </section>
              </div>
            </div>
            <div className="col-12 md:col-6">
              <div className="flex mt-6 sm:justify-content-center text-center md:text-left flex align-items-center ">
                {loading ? (
                  <div className="flex">
                    <Skeleton width="30rem" height="20rem"></Skeleton>
                  </div>
                ) : (
                  <img
                    className="flex w-9 h-20rem sm:w-8 sm:h-8 md:w-10 md:h-10 xl:w-8 xl:h-20rem shadow-2  sm:justify-content-center border-round"
                    src={`content/uploads/${noticiasEntity.urlFotoContentType}`}
                    alt={noticiasEntity.titulo}
                  />
                )}
              </div>
            </div>

            <div
              className="  p-4
      surface-overlay 
      w-full 
       
      mb-2  
      overflow-hidden 
      text-overflow-ellipsis
      text-center 
      md:text-left 
      flex align-items-center 
      text-2xl line-height-3"
            >
              {noticiasEntity.descripcion}
            </div>
          </div>
        )}
      </div>
    </>
  );
};

export default noticiasVista;
