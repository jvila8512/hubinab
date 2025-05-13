import React, { useEffect, useRef, useState } from 'react';

import { Button } from 'primereact/button';
import { Column } from 'primereact/column';
import { DataTable, DataTableFilterMeta } from 'primereact/datatable';
import { FilterMatchMode, FilterOperator } from 'primereact/api';
import { Dialog } from 'primereact/dialog';

import { InputNumber } from 'primereact/inputnumber';
import { InputText } from 'primereact/inputtext';
import { InputTextarea } from 'primereact/inputtextarea';

import { Toolbar } from 'primereact/toolbar';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import {
  getEntitiesByEcosistema,
  deleteEntity,
  createEntity,
  updateEntity,
  getEntity,
  reset,
  getEntitiesByEcosistematodasFiltrarFechas,
} from './reto.reducer';
import { TextFormat } from 'react-jhipster';
import { APP_LOCAL_DATE_FORMAT, APP_LOCAL_DATE_FORMAT1, AUTHORITIES } from 'app/config/constants';
import { RouteComponentProps } from 'react-router-dom';

import { getEntity as getEcosistema } from '../../entities/ecosistema/ecosistema.reducer';
import { getEntities as getTipoIdeas } from '../../entities/tipo-idea/tipo-idea.reducer';
import { getEntities as getEntidades } from '../../entities/entidad/entidad.reducer';
import { createEntity as nuevaIdea, reset as resetearIdea } from '../../entities/idea/idea.reducer';
import { Col, Row } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { hasAnyAuthority } from 'app/shared/auth/private-route';
import { Tag } from 'primereact/tag';

import {
  createEntity as crearNotificacion,
  reset as resetNotificacion,
  crearNotificacionRetosUserEcosistema,
} from '../../entities/notificacion/notificacion.reducer';
import { getEntities as getTipoNotificaciones } from 'app/entities/tipo-notificacion/tipo-notificacion.reducer';
import dayjs from 'dayjs';

import { Avatar } from 'primereact/avatar';
import {
  getEntity as getFile,
  getEntities as getFiles,
  createEntity as createFile,
  reset as resetFile,
  getArchivo,
  deletefile,
} from 'app/entities/Files/files.reducer';
import { FileUpload } from 'primereact/fileupload';

const RetosIdeas = (props: RouteComponentProps<{ id: string; index: string }>) => {
  const dispatch = useAppDispatch();
  const retoList = useAppSelector(state => state.reto.entities);
  const loading = useAppSelector(state => state.reto.loading);
  const retoEntity = useAppSelector(state => state.reto.entity);
  const updating = useAppSelector(state => state.reto.updating);
  const updateSuccess = useAppSelector(state => state.reto.updateSuccess);

  const currentDate = new Date().toISOString().split('T')[0];

  const [blobValue, setBlobValue] = useState(null);

  const [retoDialog, setRetoDialog] = useState(false);
  const [retoDialogNew, setRetoDialogNew] = useState(false);
  const [globalFilter, setGlobalFilterValue] = useState('');
  const [deleteRetoDialog, setDeleteRetoDialog] = useState(false);

  const [ideaDialog, setIdeaDialog] = useState(false);
  const tipoIdeas = useAppSelector(state => state.tipoIdea.entities);
  const entidads = useAppSelector(state => state.entidad.entities);
  const ideaEntity = useAppSelector(state => state.idea.entity);
  const loadingIdea = useAppSelector(state => state.idea.loading);
  const updatingIdea = useAppSelector(state => state.idea.updating);
  const updateSuccessIdea = useAppSelector(state => state.idea.updateSuccess);

  const tipoNotificacion = useAppSelector(state => state.tipoNotificacion.entities);

  const isAdmin = useAppSelector(state => hasAnyAuthority(state.authentication.account.authorities, [AUTHORITIES.ADMIN]));
  const isAdminEcosistema = useAppSelector(state =>
    hasAnyAuthority(state.authentication.account.authorities, [AUTHORITIES.ADMINECOSISTEMA])
  );
  const isGestorEcosistema = useAppSelector(state => hasAnyAuthority(state.authentication.account.authorities, [AUTHORITIES.GESTOR]));
  const isUser = useAppSelector(state => hasAnyAuthority(state.authentication.account.authorities, [AUTHORITIES.USER]));
  const isAuthenticated = useAppSelector(state => state.authentication.isAuthenticated);

  const dt = useRef(null);

  const account = useAppSelector(state => state.authentication.account);
  const ecosistemaEntity = useAppSelector(state => state.ecosistema.entity);

  const [isNew, setNew] = useState(true);
  const [isNewIdea, setNewIdea] = useState(true);

  const [fechaInicio, setfechaInicio] = useState(false);
  const [fechaInicioValidar, setfechaInicioValidar] = useState('');

  const emptyReto = {
    id: null,
    reto: '',
    descripcion: '',
    motivacion: '',
    fechaInicio: '',
    fechaFin: '',
    visto: null,
    urlFoto: null,
    urlFotoContentType: '',
    user: null,
  };
  const [reto, setReto] = useState(emptyReto);

  const [selectedReto, setSelectedReto] = useState(emptyReto);

  const [filters, setFilters] = useState({
    global: { value: null, matchMode: FilterMatchMode.CONTAINS },
    reto: { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.STARTS_WITH }] },
    descripcion: { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.STARTS_WITH }] },
    motivacion: { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.STARTS_WITH }] },
    fechaInicio: { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.DATE_IS }] },
    fechaFin: { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.DATE_IS }] },
  });

  const fileUploadRef = useRef(null);
  const file = useAppSelector(state => state.files.entity);
  const updatingFile = useAppSelector(state => state.files.updating);
  const updateSuccessFile = useAppSelector(state => state.files.updateSuccess);
  const loadingFile = useAppSelector(state => state.files.loading);

  const [selectedFile, setSelectedFile] = useState(null);
  const [fileModificar, setfileModificar] = useState(null);

  const borrar = icono => {
    const consulta = deletefile(icono);
    consulta.then(response => {
      setRetoDialogNew(false);
    });
  };

  useEffect(() => {
    if (updateSuccessFile && !isNew) {
      borrar(reto?.urlFotoContentType);
      setSelectedReto(null);
    }
  }, [updateSuccessFile]);

  useEffect(() => {
    if (updateSuccess && !isNew) {
      setfileModificar(null);
      selectedFile && fileUploadRef.current.upload();
      setRetoDialogNew(false);
    }

    if (updateSuccess && isNew) {
      const entityNotificacion = {
        ...emptyNotificacion,
        descripcion:
          'Ha sido lanzado un nuevo reto en el ecosistema ' +
          ecosistemaEntity.nombre +
          ' con el titulo ' +
          retoEntity.reto +
          '  y tiene hasta el ' +
          dayjs(retoEntity.fechaFin).utc().format('dddd, D MMMM , YYYY') +
          ' para aportar tus ideas',
        visto: 0,
        fecha: dayjs().toDate(),
        user: account,
        usercreada: account,
        tipoNotificacion: tipoNotificacion.find(it => it.tipoNotificacion === 'Reto'),
      };
      const retos = crearNotificacionRetosUserEcosistema(props.match.params.id, entityNotificacion);
      retos.then(response => {});
    }

    dispatch(getEntitiesByEcosistematodasFiltrarFechas(props.match.params.id));
    setRetoDialogNew(false);
  }, [updateSuccess]);

  useEffect(() => {
    if (updateSuccessIdea) {
      setIdeaDialog(false);
      dispatch(resetearIdea());
    }
  }, [updateSuccessIdea]);

  const handleFileUpload = event => {
    const fileupload = event.files[0];
    const formData = new FormData();
    formData.append('files', selectedFile);
    dispatch(createFile(formData));
  };

  const chooseOptions = {
    icon: 'pi pi-fw pi-images',
    className: 'custom-choose-btn p-button-rounded p-button-outlined',
  };
  const uploadOptions = {
    icon: 'pi pi-fw pi-cloud-upload',

    className: 'custom-upload-btn p-button-success p-button-rounded p-button-outlined p-3',
  };

  const cancelOptions = { label: 'Cancel', icon: 'pi pi-times', className: 'p-button-danger' };

  const onUpload = e => {};

  const onTemplateSelect = e => {
    setSelectedFile(e.files[0]);
  };

  const iconoTemplate = rowData => {
    return (
      <>
        <Avatar image={`content/uploads/${rowData.icono}`} shape="circle" className="p-overlay-badge " size="xlarge" />
      </>
    );
  };

  const headerTemplate = options => {
    const { className, chooseButton, uploadButton, cancelButton } = options;

    return (
      <div className={className + ' relative'} style={{ backgroundColor: 'transparent', display: 'flex', alignItems: 'center' }}>
        {chooseButton}
      </div>
    );
  };
  const onTemplateRemove = (file1, callback) => {
    setSelectedFile(null);
    callback();
  };
  const itemTemplate = (file1, props1) => {
    return (
      <div className="flex flex-wrap align-items-center">
        <div className="flex  align-items-center gap-3" style={{ width: '60%' }}>
          <img alt={file1.name} role="presentation" src={file1.objectURL} width={100} />
          <span className="flex flex-column text-left ml-3">
            {file1.name}
            <small>{new Date().toLocaleDateString()}</small>
          </span>
        </div>
        <Tag value={props1.formatSize} severity="warning" className="px-4 py-3" />

        <Button
          type="button"
          icon="pi pi-times"
          className=" p-button-outlined p-button-rounded p-button-danger ml-3 p-3"
          onClick={() => onTemplateRemove(file1, props1.onRemove)}
        />
      </div>
    );
  };

  const emptyTemplate = () => {
    return (
      <div className="flex align-items-center flex-column">
        {isNew ? (
          <span style={{ fontSize: '1.2em', color: 'var(--text-color-secondary)' }} className="my-5">
            Puede arrastrar y soltar el icono aquí
          </span>
        ) : (
          <span style={{ fontSize: '1.2em', color: 'var(--text-color-secondary)' }} className="my-5">
            Puede arrastrar y soltar el icono para Modificar
          </span>
        )}
      </div>
    );
  };

  useEffect(() => {
    dispatch(getEntitiesByEcosistematodasFiltrarFechas(props.match.params.id));
    dispatch(getEcosistema(props.match.params.id));
    dispatch(getTipoIdeas({}));
    dispatch(getEntidades({}));
    dispatch(getTipoNotificaciones({}));
  }, []);
  const emptyNotificacion = {
    id: null,
    descripcion: '',
    visto: null,
    fecha: '',
    publico: null,
    user: null,
    usercreada: null,
    tipoNotificacion: null,
  };

  const onGlobalFilterChange = e => {
    const value = e.target.value;
    const _filters = { ...filters };
    _filters['global'].value = value;

    setFilters(_filters);
    setGlobalFilterValue(value);
  };

  const confirmDeleteSelected = () => {
    setDeleteRetoDialog(true);
  };

  const verDialogNuevo = () => {
    setRetoDialogNew(true);
    setNew(true);
    setfechaInicio(true);
  };

  const actualizar = retoact => {
    setReto({ ...retoact });
    setRetoDialogNew(true);
    setNew(false);
    setfechaInicio(false);
    setfechaInicioValidar(retoact.fechaInicio);
  };

  const atras = () => {
    props.history.push(`/usuario-panel/${props.match.params.index}`);
  };

  const leftToolbarTemplate = () => {
    return (
      <React.Fragment>
        <div className="my-2">
          <Button label="Atrás" icon="pi pi-arrow-left" className="p-button-secondary mr-2" onClick={atras} />
          {account?.authorities?.find(rol => rol === 'ROLE_GESTOR') && ecosistemaEntity?.users?.find(user => user.id === account?.id) && (
            <Button
              icon="pi pi-trash"
              className="p-button-danger"
              onClick={confirmDeleteSelected}
              disabled={!selectedReto || !(account.id === selectedReto?.user?.id)}
            />
          )}
        </div>
      </React.Fragment>
    );
  };
  const atrasvista = () => {
    dispatch(reset());
    props.history.push(`/entidad/reto/retogrid/${props.match.params.id}/${props.match.params.index}`);
  };

  const rightToolbarTemplate = () => {
    return (
      <React.Fragment>
        {account?.authorities?.find(rol => rol === 'ROLE_GESTOR') && ecosistemaEntity?.users?.find(user => user.id === account?.id) && (
          <Button label="Nuevo Reto" icon="pi pi-plus" className="p-button-info" onClick={verDialogNuevo} />
        )}
        <Button label="Cambiar Vista" icon="pi pi-arrow-up" className="p-button-primary mr-2 ml-2" onClick={atrasvista} />
      </React.Fragment>
    );
  };
  const header = (
    <div className="flex flex-column md:flex-row md:justify-content-between md:align-items-center">
      <h5 className="m-0 text-blue-600">Retos</h5>
      <h5 className="m-0 ">
        <span className="text-blue-600"> Ecosistema: {ecosistemaEntity.nombre}</span>
      </h5>
      <span className="block mt-2 md:mt-0 p-input-icon-left">
        <i className="pi pi-search" />
        <InputText value={globalFilter} type="search" onInput={onGlobalFilterChange} placeholder="Buscar..." />
      </span>
    </div>
  );

  const retoBodyTemplate = rowData => {
    return <>{rowData.reto}</>;
  };
  const imageBodyTemplate = rowData => {
    return (
      <>
        <img src={`data:${rowData.urlFotoContentType};base64,${rowData.urlFoto}`} style={{ maxHeight: '30px' }} />
      </>
    );
  };
  const nameBodyTemplate = rowData => {
    return (
      <>
        <span className="pl-5">{rowData.descripcion}</span>
      </>
    );
  };
  const actionBodyTemplate = rowData => {
    return (
      <>
        {rowData.activo && (
          <Button
            icon="pi pi-eye"
            tooltip="Ver Reto"
            tooltipOptions={{ position: 'left' }}
            className="p-button-rounded p-button-info ml-2 mb-1"
            onClick={() => verReto(rowData)}
          />
        )}
        {rowData.activo && (
          <Button
            icon="pi pi-sun"
            tooltip="Ver Ideas de este Reto"
            tooltipOptions={{ position: 'left' }}
            className="p-button-rounded p-button-success ml-2 mb-1"
            onClick={() => verIdeas(rowData)}
          />
        )}

        {(account.id === rowData?.user?.id ||
          (account?.authorities?.find(rol => rol === 'ROLE_GESTOR') && ecosistemaEntity?.users?.find(user => user.id === account?.id))) && (
          <Button icon="pi pi-pencil" className="p-button-rounded p-button-warning ml-2 mb-1" onClick={() => actualizar(rowData)} />
        )}
      </>
    );
  };

  const fechaInicioBodyTemplate = rowData => {
    return (
      <>
        <TextFormat type="date" value={rowData.fechaInicio} format={APP_LOCAL_DATE_FORMAT1} />
      </>
    );
  };

  const estadoTemplate = rowData => {
    return <>{rowData.activo ? <Tag value="Activo" severity="success"></Tag> : <Tag value="No Activo" severity="danger"></Tag>}</>;
  };
  const fechaFinBodyTemplate = rowData => {
    return (
      <>
        <TextFormat type="date" value={rowData.fechaFin} format={APP_LOCAL_DATE_FORMAT1} />
      </>
    );
  };
  const hideDialog = () => {
    setRetoDialog(false);
    setNew(true);
    defaultValues();
  };
  const hideDialogNuevo = () => {
    setRetoDialogNew(false);
  };
  const productDialogFooter = (
    <>
      <Button label="Cerrar" icon="pi pi-times" className="p-button-text" onClick={hideDialog} />
    </>
  );
  const verReto = product => {
    setReto({ ...product });
    props.history.push(`/entidad/reto/reto_ideas/${product.id}/${props.match.params.id}/${props.match.params.index}`);
  };
  const deleteReto = () => {
    dispatch(deleteEntity(selectedReto.id));
    setDeleteRetoDialog(false);
  };

  const hideDeleteRetoDialog = () => {
    setDeleteRetoDialog(false);
  };
  const verIdeas = product => {
    props.history.push('/entidad/idea/ideasbyretos/' + product.id + '/' + props.match.params.id + '/' + props.match.params.index);
  };

  const nuevaIdeas = nueva => {
    dispatch(getEntity(nueva.id));
    setIdeaDialog(true);
  };
  const updateFechaInicio = event => {
    setfechaInicioValidar(event.target.value);

    setReto({
      ...reto,
      fechaInicio: event.target.value,
    });
    return event.target.value;
  };

  const deleteProductDialogFooter = (
    <>
      <Button label="No" icon="pi pi-times" className="p-button-text" onClick={hideDeleteRetoDialog} />

      <Button label="Sí" icon="pi pi-check" className="p-button-text" onClick={deleteReto} />
    </>
  );

  const hideDeleteProductDialog = () => {
    setDeleteRetoDialog(false);
  };

  const hideIdeaDialog = () => {
    setIdeaDialog(false);
  };

  const saveEntity = values => {
    const entity = {
      ...values,

      visto: isNew ? 0 : reto.visto,
      ecosistema: ecosistemaEntity,
      urlFotoContentType: selectedFile ? selectedFile.name : values.urlFotoContentType,
    };

    if (isNew) {
      entity.user = account;
      selectedFile && fileUploadRef.current.upload();
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };
  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...reto,

          ecosistema: ecosistemaEntity,
        };

  const defaultValuesIdeas = () =>
    !isNewIdea
      ? {}
      : {
          user: account.id,
          reto: retoEntity.id,
        };

  const saveIdea = values => {
    const entity = {
      ...values,
      user: account,
      reto: retoEntity,
      tipoIdea: tipoIdeas.find(it => it.id.toString() === values.tipoIdea.toString()),
      entidad: entidads.find(it => it.id.toString() === values.entidad.toString()),
    };

    dispatch(nuevaIdea(entity));
  };

  return (
    <div className="grid crud-demo mt-3 mb-4">
      <div className="col-12">
        <div className="card">
          <Toolbar className="mb-4" left={leftToolbarTemplate} right={rightToolbarTemplate}></Toolbar>

          <DataTable
            ref={dt}
            value={retoList}
            selection={selectedReto}
            onSelectionChange={e => setSelectedReto(e.value)}
            dataKey="id"
            paginator
            rows={10}
            rowsPerPageOptions={[5, 10, 25]}
            className="datatable-responsive"
            paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
            currentPageReportTemplate="Mostrando del {first} al {last} de {totalRecords} Retos"
            filters={filters as DataTableFilterMeta}
            filterDisplay="menu"
            emptyMessage="No hay Retos..."
            header={header}
            responsiveLayout="stack"
          >
            <Column selectionMode="single" headerStyle={{ width: '4rem' }}></Column>
            <Column field="id" header="Id" hidden headerStyle={{ minWidth: '15rem' }}></Column>
            <Column field="activo" header="Estado" body={estadoTemplate} headerStyle={{ minWidth: '7rem' }}></Column>
            <Column field="user.login" header="User" headerStyle={{ minWidth: '15rem' }}></Column>
            <Column field="reto" header="Reto" sortable body={retoBodyTemplate} headerStyle={{ minWidth: '15rem' }}></Column>
            <Column
              field="descricion"
              header="Descripción"
              style={{ width: '40%', alignContent: 'right' }}
              sortable
              body={nameBodyTemplate}
              headerStyle={{ minWidth: '15rem' }}
            ></Column>
            <Column field="fechaInicio" header="Fecha.Inicio  " sortable dataType="date" body={fechaInicioBodyTemplate}></Column>
            <Column field="fechaFin" header="Fecha.Fin  " sortable dataType="date" body={fechaFinBodyTemplate}></Column>
            <Column body={actionBodyTemplate} headerStyle={{ minWidth: '10rem' }}></Column>
          </DataTable>

          <Dialog
            visible={retoDialog}
            style={{ width: '600px' }}
            header="Reto"
            modal
            className="p-fluid"
            footer={productDialogFooter}
            onHide={hideDialog}
          >
            {reto.urlFoto && (
              <img
                src={`content/uploads/${reto.urlFotoContentType}`}
                style={{ maxHeight: '200px' }}
                className="mt-0 mx-auto mb-5 block shadow-2"
              />
            )}

            <div className="field">
              <label htmlFor="name">Reto</label>
              <InputText id="name" value={reto.reto} autoFocus />
            </div>
            <div className="field">
              <label htmlFor="ob">Observación</label>
              <InputTextarea id="ob" value={reto.descripcion} rows={3} cols={20} />
            </div>
          </Dialog>

          <Dialog visible={retoDialogNew} style={{ width: '450px' }} header="Reto" modal className="p-fluid  " onHide={hideDialogNuevo}>
            <Row className="justify-content-center">
              {loading ? (
                <p>Cargando...</p>
              ) : (
                <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
                  <ValidatedField
                    name="urlFotoContentType"
                    data-cy="urlFotoContentType"
                    required
                    readOnly
                    hidden
                    id="urlFotoContentType"
                    type="text"
                  />
                  <FileUpload
                    ref={fileUploadRef}
                    name="demo[1]"
                    accept="image/*"
                    maxFileSize={1000000}
                    chooseLabel={isNew ? 'Suba la imagen' : 'Suba la imagen nueva'}
                    uploadLabel="Modificar"
                    onSelect={onTemplateSelect}
                    onUpload={onUpload}
                    customUpload
                    uploadHandler={handleFileUpload}
                    headerTemplate={headerTemplate}
                    itemTemplate={itemTemplate}
                    invalidFileSizeMessageSummary="Tamaño del archivo no válido"
                    invalidFileSizeMessageDetail="El tamaño máximo de carga es de 1MB"
                    emptyTemplate={emptyTemplate}
                    chooseOptions={chooseOptions}
                    uploadOptions={uploadOptions}
                    cancelOptions={cancelOptions}
                  />
                  <ValidatedField
                    label={translate('jhipsterApp.reto.reto')}
                    id="reto-reto"
                    name="reto"
                    data-cy="reto"
                    type="text"
                    validate={{
                      required: { value: true, message: translate('entity.validation.required') },
                    }}
                  />
                  <ValidatedField
                    label={translate('jhipsterApp.reto.descripcion')}
                    id="reto-descripcion"
                    name="descripcion"
                    data-cy="descripcion"
                    type="textarea"
                    validate={{
                      required: { value: true, message: translate('entity.validation.required') },
                    }}
                  />
                  <ValidatedField
                    label={translate('jhipsterApp.reto.motivacion')}
                    id="reto-motivacion"
                    name="motivacion"
                    data-cy="motivacion"
                    type="text"
                    validate={{
                      required: { value: true, message: translate('entity.validation.required') },
                    }}
                  />
                  <ValidatedField
                    label={translate('jhipsterApp.reto.fechaInicio')}
                    id="reto-fechaInicio"
                    name="fechaInicio"
                    data-cy="fechaInicio"
                    type="date"
                    onChange={updateFechaInicio}
                    min={!fechaInicio ? '' : currentDate}
                    validate={{
                      required: { value: true, message: translate('entity.validation.required') },
                    }}
                  />
                  <ValidatedField
                    label={translate('jhipsterApp.reto.fechaFin')}
                    id="reto-fechaFin"
                    name="fechaFin"
                    data-cy="fechaFin"
                    type="date"
                    validate={{
                      required: { value: true, message: translate('entity.validation.required') },
                      validate: v => new Date(v) > new Date(fechaInicioValidar) || 'Fecha de fin debe ser superior a la fecha de Inicio',
                    }}
                  />
                  <ValidatedField
                    label={translate('jhipsterApp.reto.activo')}
                    id="reto-activo"
                    name="activo"
                    data-cy="activo"
                    check
                    type="checkbox"
                  />
                  <ValidatedField
                    label={translate('jhipsterApp.reto.validado')}
                    id="reto-validado"
                    name="validado"
                    data-cy="validado"
                    checked
                    check
                    type="checkbox"
                  />
                  <ValidatedField
                    label={translate('jhipsterApp.reto.publico')}
                    id="reto-validado"
                    name="publico"
                    data-cy="publico"
                    check
                    type="checkbox"
                  />
                  &nbsp;
                  <Button
                    className="w-full"
                    color="primary"
                    id="save-entity"
                    data-cy="entityCreateSaveButton"
                    type="submit"
                    disabled={updating}
                  >
                    <span className="m-auto">
                      <FontAwesomeIcon icon="save" />
                      &nbsp;
                      <Translate contentKey="entity.action.save">Save</Translate>
                    </span>
                  </Button>
                </ValidatedForm>
              )}
            </Row>
          </Dialog>

          <Dialog
            visible={deleteRetoDialog}
            style={{ width: '450px' }}
            header="Confirmar"
            modal
            footer={deleteProductDialogFooter}
            onHide={hideDeleteProductDialog}
          >
            <div className="flex align-items-center justify-content-center">
              <i className="pi pi-exclamation-triangle mr-3" style={{ fontSize: '2rem' }} />
              {selectedReto && (
                <span>
                  ¿Seguro que quiere eliminar el Reto: <b>{selectedReto.reto}</b>?
                </span>
              )}
            </div>
          </Dialog>

          <Dialog visible={ideaDialog} style={{ width: '450px' }} header="Idea" modal onHide={hideIdeaDialog}>
            <Row className="justify-content-center">
              {loadingIdea ? (
                <p>Cargando...</p>
              ) : (
                <ValidatedForm defaultValues={defaultValuesIdeas()} onSubmit={saveIdea}>
                  <ValidatedField
                    label={translate('jhipsterApp.idea.numeroRegistro')}
                    id="idea-numeroRegistro"
                    name="numeroRegistro"
                    data-cy="numeroRegistro"
                    type="text"
                    validate={{
                      required: { value: true, message: translate('entity.validation.required') },
                      validate: v => isNumber(v) || translate('entity.validation.number'),
                    }}
                  />
                  <ValidatedField
                    label={translate('jhipsterApp.idea.titulo')}
                    id="idea-titulo"
                    name="titulo"
                    data-cy="titulo"
                    type="text"
                    validate={{
                      required: { value: true, message: translate('entity.validation.required') },
                    }}
                  />
                  <ValidatedField
                    label={translate('jhipsterApp.idea.descripcion')}
                    id="idea-descripcion"
                    name="descripcion"
                    data-cy="descripcion"
                    type="textarea"
                    validate={{
                      required: { value: true, message: translate('entity.validation.required') },
                    }}
                  />
                  <ValidatedField
                    label={translate('jhipsterApp.idea.autor')}
                    id="idea-autor"
                    name="autor"
                    data-cy="autor"
                    type="text"
                    validate={{
                      required: { value: true, message: translate('entity.validation.required') },
                    }}
                  />
                  <ValidatedField
                    label={translate('jhipsterApp.idea.fechaInscripcion')}
                    id="idea-fechaInscripcion"
                    name="fechaInscripcion"
                    data-cy="fechaInscripcion"
                    type="date"
                    validate={{
                      required: { value: true, message: translate('entity.validation.required') },
                    }}
                  />
                  <ValidatedBlobField
                    label={translate('jhipsterApp.idea.foto')}
                    id="idea-foto"
                    name="foto"
                    data-cy="foto"
                    isImage
                    accept="image/*"
                  />
                  <ValidatedField
                    label={translate('jhipsterApp.idea.publica')}
                    id="idea-publica"
                    name="publica"
                    data-cy="publica"
                    check
                    type="checkbox"
                  />
                  <ValidatedField
                    id="idea-tipoIdea"
                    name="tipoIdea"
                    data-cy="tipoIdea"
                    label={translate('jhipsterApp.idea.tipoIdea')}
                    type="select"
                  >
                    <option value="" key="0" />
                    {tipoIdeas
                      ? tipoIdeas.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.tipoIdea}
                          </option>
                        ))
                      : null}
                  </ValidatedField>
                  <ValidatedField
                    id="idea-entidad"
                    name="entidad"
                    data-cy="entidad"
                    label={translate('jhipsterApp.idea.entidad')}
                    type="select"
                  >
                    <option value="" key="0" />
                    {entidads
                      ? entidads.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.entidad}
                          </option>
                        ))
                      : null}
                  </ValidatedField>
                  &nbsp;
                  <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updatingIdea}>
                    <FontAwesomeIcon icon="save" />
                    &nbsp;
                    <Translate contentKey="entity.action.save">Save</Translate>
                  </Button>
                </ValidatedForm>
              )}
            </Row>
          </Dialog>
        </div>
      </div>
    </div>
  );
};

export default RetosIdeas;
