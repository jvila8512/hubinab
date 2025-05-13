import { Badge } from 'primereact/badge';
import './menu-notificaciones.css';
import {
  getTodasEntitiesbyUser,
  getTodasEntitiesbyUserVista,
  getTodasEntitiesbyUserNoVista,
  reset,
} from 'app/entities/notificacion/notificacion.reducer';

import React, { useState, useEffect, useRef } from 'react';

import { Link } from 'react-router-dom';
import { Button } from 'reactstrap';
import VistaPrincipal from 'app/entities/reto/vistaPrincipalRetoEcosistema';
import NotificacionUser from 'app/entities/notificacion/notificacionUser';
import { AsyncThunkAction } from '@reduxjs/toolkit';
import { INotificacion } from 'app/shared/model/notificacion.model';
import { AxiosResponse } from 'axios';
import { useAppDispatch, useAppSelector } from 'app/config/store';

const MenuUsuarioNotificaciones = props => {
  const dispatch = useAppDispatch();
  const [open1, setOpen1] = useState(false);
  const menuRef = useRef();
  const notiList = useAppSelector(state => state.notificacion.entities);
  const handler1 = e => {
    setOpen1(false);
  };

  const open = e => {
    setOpen1(e);
  };

  useEffect(() => {
    dispatch(getTodasEntitiesbyUserNoVista(props.usuariologeado.id));

    document.addEventListener('mousedown', handler1);

    return () => {
      document.removeEventListener('mousedown', handler1);
      dispatch(reset());
    };
  }, []);

  return (
    <div className="flex ">
      <div className="flex flex-row" ref={menuRef}>
        <div
          className="menu-trigger1 flex flex-row"
          onClick={() => {
            setOpen1(true);
          }}
          onMouseEnter={() => {
            setOpen1(true);
          }}
        >
          <i className="  pi pi-envelope p-overlay-badge mr-3 manito relative" style={{ fontSize: '2rem' }}>
            {notiList.length > 0 ? (
              <Badge
                className="scalein animation-duration-3000 animation-iteration-infinite -mt-3 -mr-2 "
                severity="danger"
                value={notiList ? notiList.length + '' : '0'}
              ></Badge>
            ) : (
              <Badge severity="danger" value={notiList ? notiList.length + '' : '0'} />
            )}
          </i>
        </div>

        <div className={`dropdown-menu11  ${open1 ? 'active' : 'inactive'} card max-w-30rem`}>
          <div className="flex flex-row mt-2 relative">
            <div className="flex justify-content-start mb-3">
              <h5 className="text-900 text-2xl  font-medium  mb-2">Notificaciones</h5>
              <div className="flex justify-content-start flex-wrap  absolute top-0 right-0  mr-4 ">
                <Link to={`/entidad/notificacion/notificacion/${props.usuariologeado.id}`} className=" " id="jh-tre11" data-cy="menu111">
                  <span className="text-xl text-blue-600 p-2">Ver todas</span>
                </Link>
              </div>
            </div>
          </div>
          <NotificacionUser handler={handler1} />
        </div>
      </div>
    </div>
  );
};

function DropdownItem(props) {
  return (
    <li className="dropdownItem1">
      <img src="content/images/user.jpg" />

      <Button tag={Link} to={props.url} color="white" size="sm">
        {props.text}
      </Button>
    </li>
  );
}

export default MenuUsuarioNotificaciones;
