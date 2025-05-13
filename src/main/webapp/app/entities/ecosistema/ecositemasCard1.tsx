import React, { useEffect, useState } from 'react';
import { Card } from 'primereact/card';
import { Button } from 'primereact/button';
import { Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import {
  getEntities as getUsuarioEcosistema,
  getEntityByUserId,
  updateEntity,
  updateEntity1,
} from 'app/entities/usuario-ecosistema/usuario-ecosistema.reducer';
import { mapIdList } from 'app/shared/util/entity-utils';
import { AsyncThunkAction } from '@reduxjs/toolkit';
import { IUsuarioEcosistema } from 'app/shared/model/usuario-ecosistema.model';
import { AxiosResponse } from 'axios';
import { getEntity as getEcosistema, getEntities } from 'app/entities/ecosistema/ecosistema.reducer';

const EcositemasCard1 = props => {
  const dispatch = useAppDispatch();

  const [isNewUsuario, setNewIdea] = useState(true);
  const account = useAppSelector(state => state.authentication.account);

  const users = useAppSelector(state => state.userManagement.users);
  const categoriaUsers = useAppSelector(state => state.categoriaUser.entities);

  const ecosistemas = useAppSelector(state => state.ecosistema.entities);
  const ecosistema = useAppSelector(state => state.ecosistema.entity);

  const usuarioEcosistemaEntity = useAppSelector(state => state.usuarioEcosistema.entity);
  const loading = useAppSelector(state => state.usuarioEcosistema.loading);
  const updating = useAppSelector(state => state.usuarioEcosistema.updating);
  const updateSuccess = useAppSelector(state => state.usuarioEcosistema.updateSuccess);

  const [isUsuario, setUsuario] = useState(null);

  const [activeIndex, setActiveIndex] = useState(-1);

  useEffect(() => {
    setUsuario(props.lista?.find(it => it.id === props.ecosistema.id));
    setActiveIndex(props.lista?.findIndex(it => it.id === props.ecosistema.id));
  }, []);

  useEffect(() => {
    if (updateSuccess) setActiveIndex(props.lista?.findIndex(it => it.id === props.ecosistema.id));
  }, [updateSuccess]);

  const salvar = () => {
    const li = [...props.lista, props.ecosistema];

    const entity = {
      ...props.userEcosistema,
      ecosistemas: li,
    };

    dispatch(updateEntity(entity));
  };

  const entrar = () => {
    props.history.push(`/usuario-panel/${activeIndex}`);
  };
  const salir = () => {
    const li = [...props.lista, props.ecosistema];

    const nuevaLista = props.lista.filter(item => item.id !== props.ecosistema.id);

    const entity = {
      ...props.userEcosistema,
      ecosistemas: nuevaLista,
    };

    dispatch(updateEntity1(entity));
  };

  useEffect(() => {
    if (updateSuccess) {
      setUsuario(props.lista.find(it => it.id === props.ecosistema.id));
    }
  }, [updateSuccess]);

  const title = <p className="surface-overlay   te   h-8rem mb-2  overflow-hidden text-overflow-ellipsis">{props.ecosistema.nombre}</p>;
  const header = <img src={`content/uploads/${props.ecosistema.logoUrlContentType}`} style={{ maxHeight: '200px' }} />;
  const footer = !isUsuario ? (
    <span>
      <Button label="Unirse" icon="pi pi-plus" className="p-button-primary" onClick={salvar} disabled={updating} />
    </span>
  ) : (
    <span>
      <Button label="Entrar" icon="pi pi-sign-in" className="p-button-primary" onClick={entrar} />
    </span>
  );

  return (
    <div className="p-2 ">
      <div className=" h-30rem w-24rem max-h-30rem   border-round-xl shadow-4 mb-2 relative">
        <div className="flex flex-column align-items-center gap-1 py-1">
          <img
            className=" h-13rem w-full  border-round"
            src={`content/uploads/${props.ecosistema.logoUrlContentType}`}
            alt={props.ecosistema.nombre}
          />
        </div>
        <div className="text-xl text-400 pl-3 text-blue-800 font-medium">{props.ecosistema.nombre}</div>
        <p className=" pl-3 surface-overlay  h-6rem   overflow-hidden text-overflow-ellipsis">{props.ecosistema.tematica}</p>

        <div className="flex justify-content-end absolute bottom-0 right-0 mb-4 mr-4">
          <div className="flex align-items-center">
            {!isUsuario ? (
              <span>
                <Button label="Unirse" icon="pi pi-plus" className="p-button-primary" onClick={salvar} disabled={updating} />
              </span>
            ) : (
              <span>
                <Button label="Entrar" icon="pi pi-sign-in" className="p-button-primary" onClick={entrar} />
                <Button label="Salir" icon="pi pi-sign-out" className="p-button-secondary ml-2" onClick={salir} />
              </span>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default EcositemasCard1;
