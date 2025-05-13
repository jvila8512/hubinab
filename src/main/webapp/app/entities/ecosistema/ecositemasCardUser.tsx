import React, { useEffect, useState } from 'react';
import { Card } from 'primereact/card';

import { Link, Redirect } from 'react-router-dom';
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
import { Button } from 'reactstrap';

function EcositemasCardUser(props) {
  const account = useAppSelector(state => state.authentication.account);

  const salvar = () => {
    props.history?.push(`/ecosistema/vista-principal/${props.ecosistema.id}`);
  };

  const header = (
    <img src={`data:${props.ecosistema.logoUrlContentType};base64,${props.ecosistema.logoUrl}`} style={{ maxHeight: '150px' }} />
  );
  const footer = (
    <span>
      <Button tag={Link} to={`/ecosistema/vistaprincipal/${props.ecosistema.id}`} color="primary" size="sm">
        Entrar
      </Button>
    </span>
  );

  return (
    <div className="ml-2">
      <Card
        title={props.ecosistema.nombre}
        style={{ width: '25rem', marginBottom: '2em' }}
        footer={footer}
        header={header}
        className="surface-border ;_ border-blue-500 mt-4"
      >
        <p className="m-0" style={{ lineHeight: '1.5' }}>
          {props.ecosistema.tematica}
        </p>
      </Card>
    </div>
  );
}

export default EcositemasCardUser;
