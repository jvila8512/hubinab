import { useAppDispatch, useAppSelector } from 'app/config/store';
import React, { useEffect, useRef, useState } from 'react';
import { INotificacion } from 'app/shared/model/notificacion.model';
import {
  getTodasEntities,
  getEntity,
  updateEntity,
  createEntity,
  reset,
  getTodasEntitiesbyUser,
  vistaUpdateEntity,
} from './notificacion.reducer';
import { getEntities as getTipoNotificaciones } from 'app/entities/tipo-notificacion/tipo-notificacion.reducer';
import { FilterMatchMode, FilterOperator } from 'primereact/api';
import { Button } from 'primereact/button';
import { InputText } from 'primereact/inputtext';
import { RouteComponentProps } from 'react-router-dom';
import { Toolbar } from 'primereact/toolbar';
import { DataTable, DataTableFilterMeta } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Tag } from 'primereact/tag';
import { APP_DATE_FORMAT } from 'app/config/constants';
import { TextFormat, Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { Dialog } from 'primereact/dialog';
import { Row, Spinner } from 'reactstrap';
import SpinnerCar from '../loader/spinner';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { Avatar } from 'primereact/avatar';
import dayjs from 'dayjs';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEyeSlash } from '@fortawesome/free-solid-svg-icons';

const NotificacionList = (props: RouteComponentProps<{ idUserLogueado: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew, setNew] = useState(true);

  const users = useAppSelector(state => state.userManagement.users);
  const tipoNotificacion = useAppSelector(state => state.tipoNotificacion.entities);
  const account = useAppSelector(state => state.authentication.account);

  const notificacionList = useAppSelector(state => state.notificacion.entities);
  const notiEntity = useAppSelector(state => state.notificacion.entity);
  const loading = useAppSelector(state => state.notificacion.loading);
  const updating = useAppSelector(state => state.notificacion.updating);
  const updateSuccess = useAppSelector(state => state.notificacion.updateSuccess);
  const totalItems = useAppSelector(state => state.notificacion.totalItems);

  const emptyNotificacion = {
    id: null,
    descripcion: '',
    visto: null,
    fecha: '',
    user: null,
    usercreada: null,
    imagenTipoNotificacion: '',
    imagenUserCreada: '',
    tipoNotificacion: null,
  };

  const [globalFilter, setGlobalFilterValue] = useState('');
  const [notificacionDialogNew, setNotificacionDialogNew] = useState(false);
  const [deleteNotificacionDialog, setDeleteNotificacionDialog] = useState(false);
  const [selectedNotificacion, setSelectedNotificacion] = useState(emptyNotificacion);
  const [notiVistaDialog, setNotiVistaDialog] = useState(false);

  const [NoVista, setNoVista] = useState(true);

  const dt = useRef(null);
  const [filters, setFilters] = useState({
    global: { value: null, matchMode: FilterMatchMode.CONTAINS },
    visto: { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.STARTS_WITH }] },
    descripcion: { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.STARTS_WITH }] },
    fecha: { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.DATE_IS }] },
    tipoNotificacion: { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.STARTS_WITH }] },
  });

  const onGlobalFilterChange = e => {
    const value = e.target.value;
    const _filters = { ...filters };
    _filters['global'].value = value;

    setFilters(_filters);
    setGlobalFilterValue(value);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    }
    dispatch(getTodasEntitiesbyUser(props.match.params.idUserLogueado));
    dispatch(getUsers({}));
    dispatch(getTipoNotificaciones({}));
  }, []);

  const atras = () => {
    props.history.push(`/usuario-panel`);
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
  const verDialogNuevo = () => {
    setNotificacionDialogNew(true);
    setNew(true);
  };
  const confirmDeleteSelected = rowNoticia => {
    setDeleteNotificacionDialog(true);
    setSelectedNotificacion(rowNoticia);
  };
  const atrasvista = () => {
    props.history.push(`/entidad/noticias/grid-noticias/`);
  };
  const rightToolbarTemplate = () => {
    return <React.Fragment></React.Fragment>;
  };
  const header = (
    <div className="flex flex-column md:flex-row md:justify-content-between md:align-items-center">
      <div className="flex justify-content-start">
        <h5 className="text-900 text-2xl  font-medium  mb-2">Notificaciones</h5>
      </div>
    </div>
  );

  const tituloBodyTemplate = rowData => {
    return <>{rowData.titulo}</>;
  };

  const descripcionBodyTemplate = rowData => {
    return (
      <>
        <>
          <span className="pl-5">{rowData.descripcion}</span>
        </>
      </>
    );
  };

  const hideNotivistaDialog = () => {
    setNotiVistaDialog(false);

    if (notiEntity.visto !== NoVista) {
      if (NoVista) {
        const entity = {
          ...notiEntity,
          visto: 1,
        };
        dispatch(vistaUpdateEntity(entity));
      } else {
        const entity = {
          ...notiEntity,
          visto: 0,
        };
        dispatch(vistaUpdateEntity(entity));
      }
    }
    setNoVista(true);
  };

  const actualizarNoticias = noticiasact => {
    setSelectedNotificacion(noticiasact);
    setNotificacionDialogNew(true);
    setNew(false);
  };

  const verNotificacion = noticiasact => {
    setNotiVistaDialog(true);
    setSelectedNotificacion(noticiasact);
    dispatch(getEntity(noticiasact.id));
  };
  const noVista = () => {
    setNoVista(!NoVista);
  };

  const actionBodyTemplate = rowData => {
    return (
      <div className="align-items-center justify-content-center">
        <Button icon="pi pi-eye" className="p-button-rounded p-button-info ml-2 mb-1" onClick={() => verNotificacion(rowData)} />
      </div>
    );
  };
  const fechaBodyTemplate = rowData => {
    return <>{dayjs(rowData?.fecha).utc().format('dddd, D MMMM , YYYY h:mm A')}</>;
  };
  const userBodyTemplate = rowData => {
    return <>{rowData.user?.login}</>;
  };
  const userBodyTemplate1 = rowData => {
    return <>{rowData.usercreada?.login}</>;
  };
  const publicaTemplate = rowData => {
    return <>{rowData.visto ? <Tag value="Vista" severity="success"></Tag> : <Tag value="No Vista" severity="danger"></Tag>}</>;
  };
  const tipoNotificacionTemplate = rowData => {
    return (
      <>
        {
          <Avatar
            image={`data:image;base64,${rowData.imagenTipoNotificacion}`}
            shape="circle"
            className="p-overlay-badge text-center "
            size="normal"
          />
        }
      </>
    );
  };

  const userCreoTemplate = rowData => {
    return (
      <>{<Avatar image={`data:image;base64,${rowData.imagenUserCreada}`} shape="circle" className="p-overlay-badge " size="xlarge" />}</>
    );
  };
  useEffect(() => {
    if (updateSuccess) {
      setNotificacionDialogNew(false);
      dispatch(getTodasEntitiesbyUser(props.match.params.idUserLogueado));
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.fecha = convertDateTimeToServer(values.fecha);

    const entity = {
      ...notiEntity,
      ...values,
      user: users.find(it => it.id.toString() === values.user.toString()),
      usercreada: users.find(it => it.id.toString() === values.usercreada.toString()),
      tipoNotificacion: tipoNotificacion.find(it => it.id.toString() === values.tipoNotificacion.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          fecha: displayDefaultDateTime(),
        }
      : {
          ...notiEntity,
          fecha: convertDateTimeFromServer(notiEntity.fecha),
          user: notiEntity?.user?.id,
          usercreada: notiEntity?.usercreada?.id,
          tipoNotificacion: tipoNotificacion.tipoNotificacion.id,
        };
  const hideDialogNuevo = () => {
    setNotificacionDialogNew(false);
  };

  return (
    <>
      <div className="grid crud-demo mt-3 mb-4">
        <div className="col-12">
          <div className="card">
            <Toolbar className="mb-4" left={leftToolbarTemplate} right={rightToolbarTemplate}></Toolbar>

            <DataTable
              ref={dt}
              value={notificacionList}
              selection={selectedNotificacion}
              onSelectionChange={e => setSelectedNotificacion(e.value)}
              dataKey="id"
              paginator
              sortField="fechaCreada"
              rows={10}
              rowsPerPageOptions={[5, 10, 25]}
              className="datatable-responsive"
              paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
              currentPageReportTemplate="Mostrando del {first} al {last} de {totalRecords} Notificaciones"
              filters={filters as DataTableFilterMeta}
              filterDisplay="menu"
              emptyMessage="No hay Notificaciones..."
              header={header}
              responsiveLayout="stack"
            >
              <div className="flex justify-content-end absolute bottom-0 right-0 -mb-2 -mr-1 "></div>
              <Column field="id" header="Id" hidden headerStyle={{ minWidth: '15rem' }}></Column>
              <Column field="usercreada" sortable header="Usuario" body={userCreoTemplate} headerStyle={{ minWidth: '7rem' }}></Column>

              <Column
                field="tipoNotificacion"
                sortable
                header="Tipo Notificación"
                body={tipoNotificacionTemplate}
                headerStyle={{ minWidth: '7rem' }}
              ></Column>

              <Column
                field="descripcion"
                header="Descripción"
                sortable
                body={descripcionBodyTemplate}
                style={{ width: '40%', alignContent: 'right' }}
                headerStyle={{ minWidth: '15rem' }}
              ></Column>

              <Column field="visto" sortable header="Estado" body={publicaTemplate} headerStyle={{ minWidth: '7rem' }}></Column>

              <Column field="fecha" sortable header="Fecha" body={fechaBodyTemplate} headerStyle={{ minWidth: '7rem' }}></Column>

              <Column body={actionBodyTemplate} headerStyle={{ minWidth: '10rem' }}></Column>
            </DataTable>

            <Dialog
              visible={notificacionDialogNew}
              style={{ width: '600px' }}
              header="Tipo Notificación"
              modal
              className="p-fluid  "
              onHide={hideDialogNuevo}
            >
              <Row className="justify-content-center">
                {loading ? (
                  <SpinnerCar />
                ) : (
                  <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
                    {!isNew ? (
                      <ValidatedField
                        name="id"
                        required
                        readOnly
                        id="noti-id"
                        label={translate('global.field.id')}
                        validate={{ required: true }}
                      />
                    ) : null}
                    <ValidatedField
                      label={translate('jhipsterApp.noti.descripcion')}
                      id="noti-descripcion"
                      name="descripcion"
                      data-cy="descripcion"
                      type="text"
                      validate={{
                        required: { value: true, message: translate('entity.validation.required') },
                      }}
                    />
                    <ValidatedField
                      label={translate('jhipsterApp.noti.visto')}
                      id="noti-visto"
                      name="visto"
                      data-cy="visto"
                      check
                      type="checkbox"
                    />
                    <ValidatedField
                      label={translate('jhipsterApp.noti.fecha')}
                      id="noti-fecha"
                      name="fecha"
                      data-cy="fecha"
                      type="datetime-local"
                      placeholder="YYYY-MM-DD HH:mm"
                      validate={{
                        required: { value: true, message: translate('entity.validation.required') },
                      }}
                    />
                    <ValidatedField id="noti-user" name="user" data-cy="user" label={translate('jhipsterApp.noti.user')} type="select">
                      <option value="" key="0" />
                      {users
                        ? users.map(otherEntity => (
                            <option value={otherEntity.id} key={otherEntity.id}>
                              {otherEntity.login}
                            </option>
                          ))
                        : null}
                    </ValidatedField>
                    <ValidatedField
                      id="noti-usercreada"
                      name="usercreada"
                      data-cy="usercreada"
                      label={translate('jhipsterApp.noti.usercreada')}
                      type="select"
                    >
                      <option value="" key="0" />
                      {users
                        ? users.map(otherEntity => (
                            <option value={otherEntity.id} key={otherEntity.id}>
                              {otherEntity.login}
                            </option>
                          ))
                        : null}
                    </ValidatedField>
                    <ValidatedField
                      id="noti-tipoNotificacion"
                      name="tipoNotificacion"
                      data-cy="tipoNotificacion"
                      label={translate('jhipsterApp.tipoNotificacion.tipoNotificacion')}
                      type="select"
                    >
                      <option value="" key="0" />
                      {tipoNotificacion
                        ? tipoNotificacion.map(otherEntity => (
                            <option value={otherEntity.id} key={otherEntity.id}>
                              {otherEntity.tipoNotificacion}
                            </option>
                          ))
                        : null}
                    </ValidatedField>
                    <Button
                      className="mt-2"
                      color="primary"
                      id="save-entity"
                      data-cy="entityCreateSaveButton"
                      type="submit"
                      disabled={updating}
                    >
                      <span className="m-auto pl-2">
                        <FontAwesomeIcon icon="save" />
                        &nbsp;
                        <Translate contentKey="entity.action.save">Save</Translate>
                      </span>
                    </Button>
                  </ValidatedForm>
                )}
              </Row>
            </Dialog>

            <Dialog visible={notiVistaDialog} style={{ width: '500px' }} header="Notificación" modal onHide={hideNotivistaDialog}>
              {loading ? (
                <Spinner></Spinner>
              ) : (
                <Row className="justify-content-center">
                  <div className="col-12">
                    <div className="flex flex-column xl:flex-row   gap-3">
                      <div className="flex flex-column ">
                        <Avatar
                          image={`data:image;base64,${selectedNotificacion?.imagenUserCreada}`}
                          shape="circle"
                          className="p-overlay-badge "
                          size="xlarge"
                        >
                          <div className="flex justify-content-end absolute bottom-0 right-0 -mb-2 -mr-1 ">
                            <Avatar
                              image={`data:image;base64,${selectedNotificacion?.imagenTipoNotificacion}`}
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
                            {selectedNotificacion?.descripcion}
                          </div>
                          <div className="flex flex-row align-items-center gap-3 ">
                            <span className="flex align-items-center gap-2">
                              <i className="pi pi-calendar"></i>
                              {selectedNotificacion?.fecha
                                ? dayjs(selectedNotificacion?.fecha).utc().format('dddd, D MMMM , YYYY h:mm A')
                                : null}
                            </span>
                          </div>

                          <div className="flex justify-content-end absolute bottom-0 right-0 mb-1 mr-2">
                            <div onClick={noVista} className="flex justify-content-start manito">
                              {NoVista ? <FontAwesomeIcon icon="eye" size="lg" /> : <FontAwesomeIcon icon={faEyeSlash} size="lg" />}

                              <span className="d-none d-md-inline sm"></span>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </Row>
              )}
            </Dialog>
          </div>
        </div>
      </div>
    </>
  );
};

export default NotificacionList;
