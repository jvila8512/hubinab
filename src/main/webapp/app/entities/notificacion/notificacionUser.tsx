import { useAppDispatch, useAppSelector } from 'app/config/store';
import React, { useEffect, useState } from 'react';
import { DataView, DataViewLayoutOptions } from 'primereact/dataview';
import { DataScroller } from 'primereact/datascroller';

import { INotificacion } from 'app/shared/model/notificacion.model';
import {
  getEntity,
  vistaUpdateEntity,
  updateEntity,
  getTodasEntitiesbyUser,
  getTodasEntitiesbyUserVista,
  getTodasEntitiesbyUserNoVista,
} from './notificacion.reducer';
import { TextFormat } from 'react-jhipster';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

import { Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEyeSlash } from '@fortawesome/free-solid-svg-icons';
import {
  getArchivo as getFile,
  getEntities as getFiles,
  createEntity as createFile,
  deleteEntity as deleFile,
  reset as resetFile,
} from 'app/entities/Files/files.reducer';
import { Avatar } from 'primereact/avatar';
import SpinnerCar from '../loader/spinner';
import { Button, Row } from 'reactstrap';
import { Dialog } from 'primereact/dialog';
import { Badge } from 'primereact/badge';
import dayjs from 'dayjs';

const NotificacionUser = props => {
  const dispatch = useAppDispatch();
  const [forma, setLayout] = useState(null);
  const notiList = useAppSelector(state => state.notificacion.entities);
  const notiEntity = useAppSelector(state => state.notificacion.entity);
  const loading = useAppSelector(state => state.notificacion.loading);
  const totalItems = useAppSelector(state => state.notificacion.totalItems);
  const updating = useAppSelector(state => state.notificacion.updating);
  const updateSuccess = useAppSelector(state => state.notificacion.updateSuccess);

  const account = useAppSelector(state => state.authentication.account);

  const [file, setfile] = useState(null);
  const [sms, setSms] = useState(null);

  const [notificaciones, setNotificaciones] = useState([]);
  const [notiDialog, setNotiDialog] = useState(false);
  const [NoVista, setNoVista] = useState(false);

  const [notiSelect, setNotiSelect] = useState(null);

  useEffect(() => {
    dispatch(getTodasEntitiesbyUserNoVista(account.id));
    setLayout('list');
  }, []);

  useEffect(() => {
    if (!loading) {
      setNotificaciones(notiList);
    }
  }, [loading]);

  useEffect(() => {
    if (updateSuccess) {
      dispatch(getTodasEntitiesbyUserNoVista(account.id));
    }
  }, [updateSuccess]);

  const vista = () => {
    setLayout('list');
  };

  const noVista = () => {
    setNoVista(!NoVista);
  };

  const verNotiDialog = noti => {
    setNotiDialog(true);
    setNotiSelect(noti);
    dispatch(getEntity(noti.id));
  };

  const listItem = noti => {
    return (
      <div className=" col-12 relative manito menuNotiOver">
        <div className="flex flex-column xl:flex-row   gap-3">
          <div className="flex flex-column ">
            <Avatar image={`content/uploads/${noti.usercreada.imageUrl}`} shape="circle" className="p-overlay-badge " size="xlarge">
              <div className="flex justify-content-end absolute bottom-0 right-0 -mb-2 -mr-1 ">
                <Avatar
                  image={`content/uploads/${noti.tipoNotificacion?.icono}`}
                  shape="circle"
                  className="p-overlay-badge "
                  size="normal"
                />
              </div>
            </Avatar>
          </div>
          <div className="flex flex-column sm:flex-row  align-items-center xl:align-items-start flex-1 gap-2">
            <div className="flex flex-column  xl:col-12 align-content-end align-items-center sm:align-items-start gap-3">
              <div
                className="                                             
                h-3rem                                
                overflow-hidden 
                text-overflow-ellipsis"
              >
                {noti.descripcion}
              </div>
              <div className="flex flex-row align-items-center gap-3 ">
                <span className="flex align-items-center gap-2">
                  <i className="pi pi-calendar"></i>
                  {noti.fecha ? dayjs(noti.fecha).utc().format('dddd, D MMMM , YYYY h:mm A') : null}
                </span>
              </div>

              <div className="flex justify-content-end absolute bottom-0 right-0 mb-4 mr-2">
                <div onMouseDown={() => verNotiDialog(noti)} className="flex justify-content-start manito">
                  <FontAwesomeIcon icon="eye" size="lg" /> <span className="d-none d-md-inline sm"></span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  };

  const itemTemplate = noti => {
    return listItem(noti);
  };

  const hideNotiDialog = () => {
    setNotiDialog(false);

    const entity = {
      ...notiEntity,
      visto: 1,
    };
    if (!NoVista) dispatch(vistaUpdateEntity(entity));

    setNoVista(false);
  };

  return (
    <div className="grid">
      {notiList && notiList.length > 0 ? (
        <DataScroller
          value={notiList}
          itemTemplate={itemTemplate}
          rows={20}
          inline
          scrollHeight="500px"
          className="p-0 bg-cyan-700 w-full"
        />
      ) : loading ? (
        <SpinnerCar />
      ) : (
        <div className="flex flex-row text-xl xl:col-12 alert alert-warning mt-4 sm:w-30rem w-full">No hay notificaciones. </div>
      )}

      <Dialog visible={notiDialog} style={{ width: '500px' }} header="Notificación" modal onHide={hideNotiDialog}>
        <Row className="justify-content-center">
          <div className="col-12">
            <div className="flex flex-column xl:flex-row gap-3">
              <div className="flex flex-column ">
                <Avatar image={`content/uploads/${notiSelect?.user.imageUrl}`} shape="circle" className="p-overlay-badge " size="xlarge">
                  <div className="flex justify-content-end absolute bottom-0 right-0 -mb-2 -mr-1 ">
                    <Avatar
                      image={`content/uploads/${notiSelect?.tipoNotificacion?.icono}`}
                      shape="circle"
                      className="p-overlay-badge "
                      size="normal"
                    />
                  </div>
                </Avatar>
              </div>
              <div className="flex flex-column sm:flex-row  align-items-center xl:align-items-start flex-1 gap-2">
                <div className="flex flex-column relative xl:col-12 align-content-end align-items-center sm:align-items-start gap-3">
                  <div
                    className="
                                                                              
                                        w-full                                         
                                        overflow-hidden 
                                        text-overflow-ellipsis"
                  >
                    {notiSelect?.descripcion}
                  </div>
                  <div className="flex flex-row align-items-center gap-3 ">
                    <span className="flex align-items-center gap-2">
                      <i className="pi pi-calendar"></i>
                      {notiSelect?.fecha ? dayjs(notiSelect.fecha).utc().format('dddd, D MMMM , YYYY h:mm A') : null}
                    </span>
                  </div>

                  <div className="flex justify-content-end absolute bottom-0 right-0 mb-1 mr-2">
                    <div onClick={noVista} className="flex justify-content-start manito">
                      {NoVista ? <FontAwesomeIcon icon={faEyeSlash} size="lg" /> : <FontAwesomeIcon icon="eye" size="lg" />}

                      <span className="d-none d-md-inline sm"></span>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </Row>
      </Dialog>
    </div>
  );
};

export default NotificacionUser;
