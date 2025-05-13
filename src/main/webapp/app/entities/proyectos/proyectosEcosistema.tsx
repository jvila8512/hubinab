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
import { APP_LOCAL_DATE_FORMAT, AUTHORITIES } from 'app/config/constants';
import { RouteComponentProps } from 'react-router-dom';
import { Col, Row } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { hasAnyAuthority } from 'app/shared/auth/private-route';
import { Tag } from 'primereact/tag';
import { getEntities as getProyectos } from './proyectos.reducer';

import { ISector } from 'app/shared/model/sector.model';
import { getEntities as getSectors } from 'app/entities/sector/sector.reducer';
import { ILineaInvestigacion } from 'app/shared/model/linea-investigacion.model';
import { getEntities as getLineaInvestigacions } from 'app/entities/linea-investigacion/linea-investigacion.reducer';
import { IOds } from 'app/shared/model/ods.model';
import { getEntities as getOds } from 'app/entities/ods/ods.reducer';
import { ITipoProyecto } from 'app/shared/model/tipo-proyecto.model';
import { getEntities as getTipoProyectos } from 'app/entities/tipo-proyecto/tipo-proyecto.reducer';
import { IProyectos } from 'app/shared/model/proyectos.model';
import { getEntity, updateEntity, createEntity, reset, deleteEntity, getEntitiesEcosistema } from './proyectos.reducer';
import { getEntity as getEcosistema } from 'app/entities/ecosistema/ecosistema.reducer';
import { mapIdList } from 'app/shared/util/entity-utils';
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
import { Calendar } from 'primereact/calendar';
import dayjs from 'dayjs';
import { Chip } from 'primereact/chip';

const ProyectosEcosistemas = (props: RouteComponentProps<{ id: string; index: string }>) => {
  const dispatch = useAppDispatch();
  const proyectosList = useAppSelector(state => state.proyectos.entities);
  const proyectosEntity = useAppSelector(state => state.proyectos.entity);
  const loading = useAppSelector(state => state.proyectos.loading);
  const updating = useAppSelector(state => state.proyectos.updating);
  const updateSuccess = useAppSelector(state => state.proyectos.updateSuccess);
  const [isNew, setNew] = useState(true);

  const sectors = useAppSelector(state => state.sector.entities);
  const lineaInvestigacions = useAppSelector(state => state.lineaInvestigacion.entities);
  const ods = useAppSelector(state => state.ods.entities);
  const tipoProyectos = useAppSelector(state => state.tipoProyecto.entities);

  const [retoDialog, setRetoDialog] = useState(false);
  const [retoDialogNew, setRetoDialogNew] = useState(false);
  const [globalFilter, setGlobalFilterValue] = useState('');
  const [deleteRetoDialog, setDeleteRetoDialog] = useState(false);

  const dt = useRef(null);

  const account = useAppSelector(state => state.authentication.account);
  const ecosistemaEntity = useAppSelector(state => state.ecosistema.entity);

  const emptyProyecto = {
    id: null,
    nombre: '',
    descripcion: '',
    autor: '',
    necesidad: '',
    fechaInicio: '',
    fechaFin: '',
    logoUrlContentType: '',
    logoUrl: null,
    partipantes: null,
    user: null,
    sectors: null,
    lineaInvestigacions: null,
    ods: null,
    ecosistema: null,
    tipoProyecto: null,
    entidadGestora: '',
    entidad_Ejecutora: '',
    entidadParticipantes: '',
  };
  const [proyecto, setProyecto] = useState(null);
  const [selectedProyecto, setSelectedProyecto] = useState(emptyProyecto);

  const fileUploadRef = useRef(null);
  const file = useAppSelector(state => state.files.entity);
  const updatingFile = useAppSelector(state => state.files.updating);
  const updateSuccessFile = useAppSelector(state => state.files.updateSuccess);
  const loadingFile = useAppSelector(state => state.files.loading);

  const [selectedFile, setSelectedFile] = useState(null);
  const [fileModificar, setfileModificar] = useState(null);

  useEffect(() => {
    dispatch(getEcosistema(props.match.params.id));
    dispatch(getEntitiesEcosistema(props.match.params.id));
    dispatch(getSectors({}));
    dispatch(getLineaInvestigacions({}));
    dispatch(getOds({}));
    dispatch(getTipoProyectos({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      dispatch(getEntitiesEcosistema(props.match.params.id));
      setRetoDialogNew(false);
      setSelectedProyecto(null);
    }

    if (updateSuccess && !isNew) {
      setfileModificar(null);
      selectedFile && fileUploadRef.current.upload();
      setRetoDialogNew(false);
    }
  }, [updateSuccess]);

  const borrar = icono => {
    const consulta = deletefile(icono);
    consulta.then(response => {
      setRetoDialogNew(false);
    });
  };

  useEffect(() => {
    if (updateSuccessFile && !isNew) {
      if (proyecto?.logoUrlContentType) borrar(proyecto.logoUrlContentType);
      setProyecto(null);
    }
  }, [updateSuccessFile]);

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

  const [filters, setFilters] = useState({
    global: { value: null, matchMode: FilterMatchMode.CONTAINS },
    nombre: { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.STARTS_WITH }] },
    descripcion: { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.STARTS_WITH }] },
    autor: { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.STARTS_WITH }] },
    fechaInicio: { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.DATE_IS }] },
    fechaFin: { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.DATE_IS }] },
  });

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
  };

  const actualizar = retoact => {
    setProyecto({ ...retoact });
    setRetoDialogNew(true);
    setNew(false);
  };

  const atras = () => {
    props.history.push(`/usuario-panel/${props.match.params.index}`);
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

  const rightToolbarTemplate = () => {
    return (
      <React.Fragment>
        {((ecosistemaEntity.user?.login === account.login && account?.authorities?.find(rol => rol === 'ROLE_ADMINECOSISTEMA')) ||
          (account?.authorities?.find(rol => rol === 'ROLE_GESTOR') && ecosistemaEntity?.users?.find(user => user.id === account?.id))) && (
          <Button label="Nuevo Proyecto" icon="pi pi-plus" className="p-button-info" onClick={verDialogNuevo} />
        )}
      </React.Fragment>
    );
  };
  const header = (
    <div className="grid">
      <div className="col">
        <div className="flex justify-content-start  font-bold  m-2">
          <div className="text-primary text-xl">Proyectos</div>
        </div>
      </div>
      <div className="col">
        <div className="flex align-items-center justify-content-center font-bold  m-2">
          <h5 className="text-primary">Ecosistema: {ecosistemaEntity.nombre}</h5>
        </div>
      </div>
      <div className="col">
        <div className="flex align-items-center justify-content-end  m-2">
          <span className=" block  p-input-icon-left">
            <i className="pi pi-search" />
            <InputText value={globalFilter} type="search" onInput={onGlobalFilterChange} placeholder="Buscar..." />
          </span>
        </div>
      </div>
    </div>
  );

  const retoBodyTemplate = rowData => {
    return <>{rowData.nombre}</>;
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
        <Button icon="pi pi-eye" className="p-button-rounded p-button-info ml-2 mb-1" onClick={() => verReto(rowData)} />

        {ecosistemaEntity.user?.login === account.login && account?.authorities?.find(rol => rol === 'ROLE_ADMINECOSISTEMA') && (
          <Button
            icon="pi pi-trash"
            className="p-button-rounded p-button-danger ml-2 mb-1     disabled={!(account.id === rowData?.user?.id)}"
            onClick={() => verIdeas(rowData)}
          />
        )}

        {ecosistemaEntity.user?.login === account.login && account?.authorities?.find(rol => rol === 'ROLE_ADMINECOSISTEMA') && (
          <Button icon="pi pi-pencil" className="p-button-rounded p-button-warning ml-2 mb-1" onClick={() => actualizar(rowData)} />
        )}
      </>
    );
  };

  const fechaInicioBodyTemplate = rowData => {
    return <>{rowData.fechaInicio}</>;
  };

  const fechaFinBodyTemplate = rowData => {
    return <>{rowData.fechaFin}</>;
  };
  const hideDialog = () => {
    setRetoDialog(false);
    setNew(true);
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
    setSelectedProyecto(product);
    setRetoDialog(true);

    setProyecto({ ...product });

    setNew(false);
  };
  const deleteReto = () => {
    dispatch(deleteEntity(selectedProyecto.id));
    setDeleteRetoDialog(false);
  };

  const hideDeleteRetoDialog = () => {
    setDeleteRetoDialog(false);
    setSelectedProyecto(null);
  };
  const verIdeas = product => {
    setDeleteRetoDialog(true);

    setSelectedProyecto(product);
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

  const saveEntity = values => {
    const entity = {
      ...values,

      ecosistema: ecosistemaEntity,
      sectors: mapIdList(values.sectors),
      lineaInvestigacions: mapIdList(values.lineaInvestigacions),
      ods: mapIdList(values.ods),
      tipoProyecto: tipoProyectos.find(it => it.id.toString() === values.tipoProyecto.toString()),
      logoUrlContentType: selectedFile ? selectedFile.name : values.logoUrlContentType,
    };

    if (isNew) {
      entity.user = account;
      dispatch(createEntity(entity));
      selectedFile && fileUploadRef.current.upload();
    } else {
      dispatch(updateEntity(entity));
    }
  };
  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...proyecto,

          ecosistema: ecosistemaEntity,
          sectors: proyecto?.sectors?.map(e => e.id.toString()),
          lineaInvestigacions: proyecto?.lineaInvestigacions?.map(e => e.id.toString()),
          ods: proyecto?.ods?.map(e => e.id.toString()),
          tipoProyecto: proyecto?.tipoProyecto?.id,
        };

  return (
    <div className="grid crud-demo mt-3 mb-4">
      <div className="col-12">
        <div className="card">
          <Toolbar className="mb-4" left={leftToolbarTemplate} right={rightToolbarTemplate}></Toolbar>

          <DataTable
            ref={dt}
            value={proyectosList}
            selection={selectedProyecto}
            onSelectionChange={e => setSelectedProyecto(e.value)}
            dataKey="id"
            paginator
            rows={10}
            rowsPerPageOptions={[5, 10, 25]}
            className="datatable-responsive"
            paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
            currentPageReportTemplate="Mostrando del {first} al {last} de {totalRecords} Proyectos"
            filters={filters as DataTableFilterMeta}
            filterDisplay="menu"
            loading={loading}
            emptyMessage="No hay Proyectos..."
            header={header}
            responsiveLayout="stack"
          >
            <Column field="id" header="Id" hidden headerStyle={{ minWidth: '15rem' }}></Column>
            <Column field="user.login" header="Usuario" hidden headerStyle={{ minWidth: '4rem' }}></Column>
            <Column field="nombre" header="Nombre" sortable body={retoBodyTemplate} headerStyle={{ minWidth: '15rem' }}></Column>
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

            <Column field="sector" header="Nombre" hidden sortable headerStyle={{ minWidth: '15rem' }}></Column>
            <Column field="lineaInvestigacion" hidden header="Nombre" sortable headerStyle={{ minWidth: '15rem' }}></Column>
            <Column field="ods" header="Nombre" hidden sortable headerStyle={{ minWidth: '15rem' }}></Column>
            <Column field="tipoProyecto" header="Nombre" hidden sortable headerStyle={{ minWidth: '15rem' }}></Column>
            <Column field="autor" header="Nombre" hidden sortable headerStyle={{ minWidth: '15rem' }}></Column>
            <Column field="necesidad" header="Nombre" hidden sortable headerStyle={{ minWidth: '15rem' }}></Column>

            <Column body={actionBodyTemplate} headerStyle={{ minWidth: '10rem' }}></Column>
          </DataTable>

          <Dialog
            visible={retoDialog}
            style={{ width: '500px' }}
            header="Proyecto"
            modal
            className="p-fluid"
            footer={productDialogFooter}
            onHide={hideDialog}
          >
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {selectedProyecto?.logoUrlContentType && (
                <img
                  src={`content/uploads/${selectedProyecto.logoUrlContentType}`}
                  style={{ maxHeight: '300px' }}
                  className="mt-0 mx-auto mb-5 block shadow-2 w-full"
                />
              )}
              <ValidatedField
                label={translate('jhipsterApp.proyectos.nombre')}
                id="proyectos-nombre"
                name="nombre"
                data-cy="nombre"
                type="text"
                disabled
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterApp.proyectos.descripcion')}
                id="proyectos-descripcion"
                name="descripcion"
                data-cy="descripcion"
                type="textarea"
                disabled
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterApp.proyectos.autor')}
                id="proyectos-autor"
                name="autor"
                data-cy="autor"
                type="text"
                disabled
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterApp.proyectos.necesidad')}
                id="proyectos-necesidad"
                name="necesidad"
                data-cy="necesidad"
                type="text"
                disabled
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterApp.proyectos.fechaInicio')}
                id="proyectos-fechaInicio"
                name="fechaInicio"
                data-cy="fechaInicio"
                type="date"
                disabled
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterApp.proyectos.fechaFin')}
                id="proyectos-fechaFin"
                name="fechaFin"
                data-cy="fechaFin"
                type="date"
                disabled
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterApp.proyectos.entidad_gestora')}
                id="proyectos-entidadGestora"
                name="entidadGestora"
                data-cy="entidadGestora"
                type="text"
                disabled
              />
              <ValidatedField
                label={translate('jhipsterApp.proyectos.entidad_ejecutora')}
                id="proyectos-entidad_Ejecutora"
                name="entidad_Ejecutora"
                data-cy="entidad_Ejecutora"
                type="text"
                disabled
              />
              <ValidatedField
                label={translate('jhipsterApp.proyectos.entidad_participantes')}
                id="proyectos-entidadParticipantes"
                name="entidadParticipantes"
                data-cy="entidadParticipantes"
                type="text"
                disabled
              />
              <h4 className="text-900 text-sm text-blue-600 font-medium">Sectores</h4>
              <div className="flex flex-column ">
                {proyecto?.sector
                  ? proyecto?.sector.map((val, j) => <Chip key={j} label={val.sector} icon="pi pi-verified" className="mr-3 mb-2" />)
                  : null}
              </div>
              <h4 className="text-900 text-sm text-blue-600 font-medium">Prioridad</h4>
              <div className="flex flex-column ">
                {proyecto?.lineaInvestigacions
                  ? proyecto?.lineaInvestigacions.map((val, j) => (
                      <Chip key={j} label={val.linea} icon="pi pi-verified" className="mr-3 mb-2" />
                    ))
                  : null}
              </div>
              <h4 className="text-900 text-sm text-blue-600 font-medium">ODS</h4>
              <div className="flex flex-column ">
                {proyecto?.ods
                  ? proyecto?.ods.map((val, j) => <Chip key={j} label={val.ods} icon="pi pi-verified" className="mr-3 mb-2" />)
                  : null}
              </div>
              &nbsp;
            </ValidatedForm>
          </Dialog>

          <Dialog visible={retoDialogNew} style={{ width: '600px' }} header="Proyecto" modal className="p-fluid  " onHide={hideDialogNuevo}>
            <Row className="justify-content-center">
              {loading ? (
                <p>Cargando...</p>
              ) : (
                <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
                  {!isNew ? (
                    <ValidatedField
                      name="id"
                      required
                      readOnly
                      hidden
                      id="proyectos-id"
                      label={translate('global.field.id')}
                      validate={{ required: true }}
                    />
                  ) : null}
                  <ValidatedField
                    name="logoUrlContentType"
                    data-cy="logoUrlContentType"
                    required
                    hidden
                    readOnly
                    id="logoUrlContentType"
                    type="text"
                  />
                  <FileUpload
                    ref={fileUploadRef}
                    name="demo[1]"
                    accept="image/*"
                    maxFileSize={1000000}
                    chooseLabel={isNew ? 'Suba el Icono' : 'Suba el Icono nuevo'}
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
                    label={translate('jhipsterApp.proyectos.nombre')}
                    id="proyectos-nombre"
                    name="nombre"
                    data-cy="nombre"
                    type="text"
                    validate={{
                      required: { value: true, message: translate('entity.validation.required') },
                    }}
                  />
                  <ValidatedField
                    label={translate('jhipsterApp.proyectos.descripcion')}
                    id="proyectos-descripcion"
                    name="descripcion"
                    data-cy="descripcion"
                    type="textarea"
                    validate={{
                      required: { value: true, message: translate('entity.validation.required') },
                    }}
                  />
                  <ValidatedField
                    label={translate('jhipsterApp.proyectos.autor')}
                    id="proyectos-autor"
                    name="autor"
                    data-cy="autor"
                    type="text"
                    validate={{
                      required: { value: true, message: translate('entity.validation.required') },
                    }}
                  />
                  <ValidatedField
                    label={translate('jhipsterApp.proyectos.necesidad')}
                    id="proyectos-necesidad"
                    name="necesidad"
                    data-cy="necesidad"
                    type="text"
                    validate={{
                      required: { value: true, message: translate('entity.validation.required') },
                    }}
                  />
                  <ValidatedField
                    label={translate('jhipsterApp.proyectos.fechaInicio')}
                    id="proyectos-fechaInicio"
                    name="fechaInicio"
                    data-cy="fechaInicio"
                    type="date"
                    validate={{
                      required: { value: true, message: translate('entity.validation.required') },
                    }}
                  />
                  <ValidatedField
                    label={translate('jhipsterApp.proyectos.fechaFin')}
                    id="proyectos-fechaFin"
                    name="fechaFin"
                    data-cy="fechaFin"
                    type="date"
                    validate={{
                      required: { value: true, message: translate('entity.validation.required') },
                    }}
                  />
                  <ValidatedField
                    label={translate('jhipsterApp.proyectos.entidad_gestora')}
                    id="proyectos-entidadGestora"
                    name="entidadGestora"
                    data-cy="entidadGestora"
                    type="text"
                  />
                  <ValidatedField
                    label={translate('jhipsterApp.proyectos.entidad_ejecutora')}
                    id="proyectos-entidad_Ejecutora"
                    name="entidad_Ejecutora"
                    data-cy="entidad_Ejecutora"
                    type="text"
                  />
                  <ValidatedField
                    label={translate('jhipsterApp.proyectos.entidad_participantes')}
                    id="proyectos-entidadParticipantes"
                    name="entidadParticipantes"
                    data-cy="entidadParticipantes"
                    type="text"
                  />
                  <ValidatedField
                    label={translate('jhipsterApp.proyectos.sector')}
                    id="proyectos-sector"
                    data-cy="sector"
                    type="select"
                    multiple
                    name="sectors"
                  >
                    <option value="" key="0" />
                    {sectors
                      ? sectors.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.sector}
                          </option>
                        ))
                      : null}
                  </ValidatedField>
                  <ValidatedField
                    label={translate('jhipsterApp.proyectos.lineaInvestigacion')}
                    id="proyectos-lineaInvestigacion"
                    data-cy="lineaInvestigacion"
                    type="select"
                    multiple
                    name="lineaInvestigacions"
                  >
                    <option value="" key="0" />
                    {lineaInvestigacions
                      ? lineaInvestigacions.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.linea}
                          </option>
                        ))
                      : null}
                  </ValidatedField>
                  <ValidatedField
                    label={translate('jhipsterApp.proyectos.ods')}
                    id="proyectos-ods"
                    data-cy="ods"
                    type="select"
                    multiple
                    name="ods"
                  >
                    <option value="" key="0" />
                    {ods
                      ? ods.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.ods}
                          </option>
                        ))
                      : null}
                  </ValidatedField>
                  <ValidatedField
                    id="proyectos-tipoProyecto"
                    name="tipoProyecto"
                    data-cy="tipoProyecto"
                    label={translate('jhipsterApp.proyectos.tipoProyecto')}
                    type="select"
                  >
                    <option value="" key="0" />
                    {tipoProyectos
                      ? tipoProyectos.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.tipoProyecto}
                          </option>
                        ))
                      : null}
                  </ValidatedField>
                  &nbsp;
                  <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
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
              {selectedProyecto && (
                <span>
                  ¿Seguro que quiere eliminar el Proyecto: <b>{selectedProyecto.nombre}</b>?
                </span>
              )}
            </div>
          </Dialog>
        </div>
      </div>
    </div>
  );
};

export default ProyectosEcosistemas;
