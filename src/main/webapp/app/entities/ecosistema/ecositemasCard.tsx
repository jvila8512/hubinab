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
} from 'app/entities/usuario-ecosistema/usuario-ecosistema.reducer';
import { mapIdList } from 'app/shared/util/entity-utils';
import { AsyncThunkAction } from '@reduxjs/toolkit';
import { IUsuarioEcosistema } from 'app/shared/model/usuario-ecosistema.model';
import { AxiosResponse } from 'axios';
import { getEntity as getEcosistema, getEntities } from 'app/entities/ecosistema/ecosistema.reducer';

const EcositemasCard = props => {
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

  useEffect(() => {
    if (updateSuccess) {
      setUsuario(props.lista.find(it => it.id === props.ecosistema.id));
    }
  }, [updateSuccess]);

  const title = <p className="surface-overlay  h-8rem mb-2  overflow-hidden text-overflow-ellipsis">{props.ecosistema.nombre}</p>;
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
    <div>
      <Card
        title={title}
        style={{ height: '30rem', width: '25rem', marginBottom: '2em' }}
        footer={footer}
        header={header}
        className="shadow-6 mt-4"
      >
        <p className="surface-overlay  h-6rem mb-2  overflow-hidden text-overflow-ellipsis">{props.ecosistema.tematica}</p>
      </Card>
    </div>
  );
};

export default EcositemasCard;
