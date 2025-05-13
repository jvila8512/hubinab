import './ecosistemas.scss';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import React, { useEffect, useRef, useState } from 'react';
import { RouteComponentProps } from 'react-router-dom';
import { getEntities, createEntity, deleteEntity, updateEntity, reset } from './ecosistema.reducer';
import { IEcosistema } from 'app/shared/model/ecosistema.model';
import { FilterMatchMode, FilterOperator } from 'primereact/api';

import { IUser } from 'app/shared/model/user.model';
import { getUsersTodos } from 'app/modules/administration/user-management/user-management.reducer';
import { IEcosistemaRol } from 'app/shared/model/ecosistema-rol.model';
import { getEntities as getEcosistemaRols } from 'app/entities/ecosistema-rol/ecosistema-rol.reducer';
import { IUsuarioEcosistema } from 'app/shared/model/usuario-ecosistema.model';
import { getEntities as getUsuarioEcosistemas } from 'app/entities/usuario-ecosistema/usuario-ecosistema.reducer';
import { Button } from 'primereact/button';
import { Column } from 'primereact/column';
import { DataTable, DataTableFilterMeta } from 'primereact/datatable';
import { Dialog } from 'primereact/dialog';
import { InputNumber } from 'primereact/inputnumber';
import { InputText } from 'primereact/inputtext';
import { InputTextarea } from 'primereact/inputtextarea';
import { Toolbar } from 'primereact/toolbar';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { Tag } from 'primereact/tag';
import { Row } from 'reactstrap';
import { mapIdList } from 'app/shared/util/entity-utils';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { Skeleton } from 'primereact/skeleton';
import { MultiSelect } from 'primereact/multiselect';
import { Dropdown } from 'primereact/dropdown';
import { contarRetosbyEcosistemas } from '../../entities/reto/reto.reducer';
import { contarNoticiasbyEcosistemas } from '../../entities/noticias/noticias.reducer';
import { contarProyectosbyEcosistemas } from '../../entities/proyectos/proyectos.reducer';
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
import { Chip } from 'primereact/chip';
import SpinnerCar from '../loader/spinner';

const EcosistemaCrud = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();
  const ecosistemaList = useAppSelector(state => state.ecosistema.entities);
  const loading = useAppSelector(state => state.ecosistema.loading);
  const { match } = props;
  const [filtrado, setFiltrado] = useState([]);

  const [isNew, setNew] = useState(true);

  const users = useAppSelector(state => state.userManagement.users);
  const ecosistemaRols = useAppSelector(state => state.ecosistemaRol.entities);
  const usuarioEcosistemas = useAppSelector(state => state.usuarioEcosistema.entities);
  const ecosistemaEntity = useAppSelector(state => state.ecosistema.entity);
  const updating = useAppSelector(state => state.ecosistema.updating);
  const updateSuccess = useAppSelector(state => state.ecosistema.updateSuccess);

  const dt = useRef(null);

  const account = useAppSelector(state => state.authentication.account);
  const [globalFilter, setGlobalFilterValue] = useState('');
  const [ecosistemaDialogNew, setEcosistemaDialogNew] = useState(false);
  const [deleteEcosistemaDialog, setDeleteEcosistemaDialog] = useState(false);
  const [gestorDialog, setGestorDialog] = useState(false);

  const fileUploadRef = useRef(null);
  const file = useAppSelector(state => state.files.entity);
  const updatingFile = useAppSelector(state => state.files.updating);
  const updateSuccessFile = useAppSelector(state => state.files.updateSuccess);
  const loadingFile = useAppSelector(state => state.files.loading);
  const [selectedFile, setSelectedFile] = useState(null);
  const [fileModificar, setfileModificar] = useState(null);

  const handleClose = () => {
    props.history.push('/ecosistema');
  };

  useEffect(() => {
    dispatch(getEntities({}));
    dispatch(getUsersTodos());
    dispatch(getEcosistemaRols({}));
    dispatch(getUsuarioEcosistemas({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };
  const emptyEcosistema = {
    id: null,
    nombre: '',
    tematica: '',
    activo: null,
    logoUrlContentType: '',
    logoUrl: null,
    ranking: null,
    usuariosCant: null,
    retosCant: null,
    ideasCant: null,
    retos: null,
    ecosistemaComponentes: null,
    users: null,
    ecosistemaRol: null,
    usurioecosistemas: null,
  };
  const [ecosistema, setProyecto] = useState(null);
  const [selectedEcosistema, setSelectedEcosistema] = useState(emptyEcosistema);
  const [selectedEcosistemaUsuario, setSelectedEcosistemaUsuario] = useState(null);
  const [selectedEcosistemaFiltrado, setSelectedEcosistemaFiltrado] = useState(emptyEcosistema);
  const [selectedEcosistemaFiltradoSelecionado, setselectedEcosistemaFiltradoSelecionado] = useState(false);

  const [reto, setReto] = useState(0);
  const [noticia, setNoticias] = useState(0);
  const [proyecto, setProyectos] = useState(0);
  const [usuario, setUsurios] = useState(0);

  const [selectedAdmin, setSelectedAdmin] = useState(null);

  const [filters, setFilters] = useState({
    global: { value: null, matchMode: FilterMatchMode.CONTAINS },
    nombre: { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.STARTS_WITH }] },
    tematica: { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.STARTS_WITH }] },
  });

  const onGlobalFilterChange = e => {
    const value = e.target.value;
    const _filters = { ...filters };
    _filters['global'].value = value;

    setFilters(_filters);
    setGlobalFilterValue(value);
  };
  const nombreBodyTemplate = rowData => {
    return (
      <>
        <span className="pl-5">{rowData.nombre}</span>
      </>
    );
  };
  const adminBodyTemplate = rowData => {
    return (
      <div className="flex flex-row country-item">
        <Avatar
          image={`content/uploads/${rowData.user.imageUrl}`}
          shape="circle"
          className=""
          style={{ width: '3rem', height: '3rem' }}
        ></Avatar>
        <div className="ml-3 text-lg font-bold text-primary">{rowData.user.login}</div>
      </div>
    );
  };

  const tematicaBodyTemplate = rowData => {
    return (
      <>
        <span className="pl-5">{rowData.tematica}</span>
      </>
    );
  };

  const userBodyTemplate = rowData => {
    return (
      <div className="flex flex-column ">
        {rowData.users
          ? rowData?.users.map((val, j) => (
              <Chip key={j} label={val.login} image={`content/uploads/${val.imageUrl}`} className="mr-3 mb-2" />
            ))
          : null}
      </div>
    );
  };
  const userBodyTemplateFiltrado = rowData => {
    return (
      <>
        <span className="text-lg font-bold text-primary">{rowData.login}</span>
      </>
    );
  };

  const imageBodyTemplate = rowData => {
    return (
      <>
        <img src={`content/uploads/${rowData.urlFotoContentType}`} style={{ maxHeight: '30px' }} />
      </>
    );
  };
  const atras = () => {
    props.history.push('/usuario-panel');
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
    setEcosistemaDialogNew(true);
    setNew(true);
    setSelectedFile(null);
    dispatch(resetFile());
  };

  const rightToolbarTemplate = () => {
    return (
      <React.Fragment>
        {account.authorities.find(rol => rol === 'ROLE_ADMIN') && (
          <Button label="Nuevo Ecosistema" icon="pi pi-plus" className="p-button-info" onClick={verDialogNuevo} />
        )}
      </React.Fragment>
    );
  };
  const rightToolbarTemplateGestores = () => {
    return (
      <React.Fragment>
        {account.authorities.find(rol => rol === 'ROLE_ADMIN') && (
          <Button icon="pi pi-user-plus" className="p-button-rounded p-button-secondary ml-2 mb-1" onClick={verDialogNuevo} />
        )}
      </React.Fragment>
    );
  };
  const header = (
    <div className="flex flex-column md:flex-row md:justify-content-between md:align-items-center">
      <h3 className="text-900 text-2xl text-blue-600 font-medium">Ecosistemas:</h3>

      <span className="block mt-2 md:mt-0 p-input-icon-left">
        <i className="pi pi-search" />
        <InputText value={globalFilter} type="search" onInput={onGlobalFilterChange} placeholder="Buscar..." />
      </span>
    </div>
  );
  const confirmDeleteSelected = rowEcosistema => {
    setDeleteEcosistemaDialog(true);
    setSelectedEcosistema(rowEcosistema);
  };
  const irEcosistema = rowEcosistema => {
    props.history.push(`/entidad/ecosistema/vistaprincipal/${rowEcosistema.id}`);
  };

  const confirmDeleteSelectedUsuario = rowUsuarios => {
    const entity = {
      ...selectedEcosistemaFiltrado,
      users: selectedEcosistemaFiltrado?.users.filter(user => user.login !== rowUsuarios.login),
    };

    const copia = { ...selectedEcosistemaFiltrado };

    const userfiltrado = selectedEcosistemaFiltrado?.users.filter(user => user.login !== rowUsuarios.login);

    copia.users = [...userfiltrado];
    dispatch(updateEntity(entity));

    setSelectedEcosistemaFiltrado(copia);
  };

  const actualizarEcosistema = retoact => {
    setSelectedEcosistema(retoact);
    setEcosistemaDialogNew(true);
    setNew(false);
    setSelectedFile(null);
    setSelectedAdmin(buscarUserAdmin(retoact.user));
    const retos = contarRetosbyEcosistemas(retoact.id);
    retos.then(response => {
      setReto(response.data);
    });
  };
  const gestorEcosistema = retoact => {
    setSelectedEcosistemaFiltrado(retoact);
    setGestorDialog(true);
    setNew(false);
  };

  const actionBodyTemplate = rowData => {
    return (
      <div className="align-items-center justify-content-center">
        <Button icon="pi pi-trash" className="p-button-rounded p-button-danger ml-2 mb-1" onClick={() => confirmDeleteSelected(rowData)} />
        <Button icon="pi pi-pencil" className="p-button-rounded p-button-warning ml-2 mb-1" onClick={() => actualizarEcosistema(rowData)} />
        <Button
          icon="pi pi-user-plus"
          className="p-button-rounded p-button-secondary ml-2 mb-1"
          onClick={() => gestorEcosistema(rowData)}
        />
      </div>
    );
  };

  const actionBodyTemplateFiltrado = rowData => {
    return (
      <div className="align-items-center justify-content-center">
        <Button
          icon="pi pi-trash"
          className="p-button-rounded p-button-danger ml-2 mb-1"
          onClick={() => confirmDeleteSelectedUsuario(rowData)}
        />
      </div>
    );
  };
  const estadoTemplate = rowData => {
    return <>{rowData.activo ? <Tag value="Activo" severity="success"></Tag> : <Tag value="No Activo" severity="danger"></Tag>}</>;
  };
  const hideDialogNuevo = () => {
    setSelectedAdmin(null);
    setEcosistemaDialogNew(false);
  };
  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...selectedEcosistema,
          ecosistemaRol: selectedEcosistema?.ecosistemaRol?.id,
        };

  const saveEntity = values => {
    const entity = {
      ...values,
      ranking: 0,
      usuariosCant: 0,
      retosCant: 0,
      ideasCant: 0,
      logoUrlContentType: selectedFile && selectedFile.name,
      ecosistemaRol: ecosistemaRols.find(it => it.id.toString() === values.ecosistemaRol.toString()),
      user: selectedAdmin ? selectedAdmin : account,
    };
    const entity1 = {
      ...values,
      retosCant: reto,
      logoUrlContentType: selectedFile ? selectedFile.name : values.logoUrlContentType,

      ecosistemaRol: ecosistemaRols.find(it => it.id.toString() === values.ecosistemaRol.toString()),
      user: selectedAdmin ? selectedAdmin : account,
    };

    if (isNew) {
      fileUploadRef.current.upload();
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity1));
    }
  };

  useEffect(() => {
    if (updateSuccess && !isNew) {
      setfileModificar(null);
      selectedFile && fileUploadRef.current.upload();
      setEcosistemaDialogNew(false);
    }
    if (updateSuccess) {
      setEcosistemaDialogNew(false);
      setSelectedEcosistemaUsuario(null);
      setSelectedAdmin(null);
      setselectedEcosistemaFiltradoSelecionado(false);
    }
  }, [updateSuccess]);

  const borrar = icono => {
    const consulta = deletefile(icono);
    consulta.then(response => {
      setDeleteEcosistemaDialog(false);
    });
  };

  useEffect(() => {
    if (updateSuccessFile && !isNew) {
      borrar(selectedEcosistema.logoUrlContentType);
      setSelectedEcosistema(null);
    }
  }, [updateSuccessFile]);

  const hideDeleteEcosistemaDialog = () => {
    setDeleteEcosistemaDialog(false);
  };
  const hideGestorDialog = () => {
    setGestorDialog(false);
    setSelectedEcosistemaFiltrado(null);
  };
  const deleteEcosistema = () => {
    dispatch(deleteEntity(selectedEcosistema.id));
    setDeleteEcosistemaDialog(false);
    borrar(selectedEcosistema.logoUrlContentType);
    setSelectedEcosistema(null);
    setSelectedFile(null);
  };

  const ActualizarUsuarioEcosistema = () => {
    const li = [...selectedEcosistemaFiltrado.users, selectedEcosistemaUsuario];

    const entity = {
      ...selectedEcosistemaFiltrado,
      users: li,
    };

    const copia = { ...selectedEcosistemaFiltrado };

    copia.users = [...li];
    dispatch(updateEntity(entity));

    setSelectedEcosistemaFiltrado(copia);
  };

  const deleteEcosistemaDialogFooter = (
    <>
      <Button label="No" icon="pi pi-times" className="p-button-text" onClick={hideDeleteEcosistemaDialog} />

      <Button label="Sí" icon="pi pi-check" className="p-button-text" onClick={deleteEcosistema} />
    </>
  );

  const countryTemplate = option => {
    return (
      <div className="country-item">
        <div>{option.login}</div>
      </div>
    );
  };
  const selectedCountriesTemplate = option => {
    if (option) {
      return (
        <div className="country-item country-item-value">
          <div>{option.login}</div>
        </div>
      );
    }
    return 'Selecione Usuario';
  };
  const panelFooterTemplate = () => {
    const selectedItems = selectedEcosistemaUsuario;
    const length = selectedItems ? selectedItems.length : 0;
    return (
      <div className="py-2 px-3">
        <b>{length}</b> Elemento{length > 1 ? 's' : ''} seleccionados.
      </div>
    );
  };

  const buscarUser = u => {
    const usuario1 = selectedEcosistemaFiltrado?.users?.find(it => it.login === u.login);
    if (usuario1) return usuario1.login;
  };
  const buscarUserAdmin = u => {
    const usuario1 = users.find(it => it.login === u.login);
    if (usuario1) return usuario1;
  };

  const buscarRolGestor = u => {
    const rolGestor = u.authorities?.find(rol => rol === 'ROLE_GESTOR');
    if (rolGestor) return true;
    else return false;
  };

  const buscarRolAdminEcosistema = u => {
    const rolGestor = u.authorities?.find(rol => rol === 'ROLE_ADMINECOSISTEMA');
    if (rolGestor) return true;
    else return false;
  };

  const filtradoUser = () => {
    return users?.filter(user => user.login !== buscarUser(user) && buscarRolGestor(user));
  };
  const filtradoUserAdmin = () => {
    return users?.filter(user => user.login !== buscarUser(user) && buscarRolAdminEcosistema(user));
  };

  const filtradoUser1 = () => {
    return filtrado;
  };
  const onUsuariosChange = e => {
    setselectedEcosistemaFiltradoSelecionado(true);
    setSelectedEcosistemaUsuario(e.value);
  };
  const onAdminChange = e => {
    setSelectedAdmin(e.value);
  };

  const selectedAdminTemplate = (option, propss) => {
    if (option) {
      return (
        <div className="flex flex-row country-item">
          <Avatar
            image={`content/uploads/${option.imageUrl}`}
            shape="circle"
            className=""
            style={{ width: '1.5rem', height: '1.5rem' }}
          ></Avatar>
          <div className="ml-3">{option.login}</div>
        </div>
      );
    }

    return <span>{propss.placeholder}</span>;
  };
  const adminOptionTemplate = option => {
    return (
      <div className="flex flex-row country-item">
        <Avatar
          image={`content/uploads/${option.imageUrl}`}
          shape="circle"
          className=""
          style={{ width: '1.5rem', height: '1.5rem' }}
        ></Avatar>
        <div className="ml-2">{option.login + '--' + option.firstName + ' ' + option.lastName}</div>
      </div>
    );
  };

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
  return (
    <div className="grid crud-demo mt-3 mb-4">
      <div className="col-12">
        <div className="card">
          <Toolbar className="mb-4" left={leftToolbarTemplate} right={rightToolbarTemplate}></Toolbar>

          <DataTable
            ref={dt}
            value={ecosistemaList}
            selection={selectedEcosistema}
            onSelectionChange={e => setSelectedEcosistema(e.value)}
            dataKey="id"
            paginator
            rows={10}
            rowsPerPageOptions={[5, 10, 25]}
            className="datatable-responsive"
            paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
            currentPageReportTemplate="Mostrando del {first} al {last} de {totalRecords} Ecosistemas"
            filters={filters as DataTableFilterMeta}
            filterDisplay="menu"
            emptyMessage="No hay Ecosistemas..."
            header={header}
            responsiveLayout="stack"
          >
            <Column field="id" header="Id" hidden headerStyle={{ minWidth: '15rem' }}></Column>
            <Column field="activo" header="Estado" body={estadoTemplate} headerStyle={{ minWidth: '7rem' }}></Column>
            <Column field="nombre" header="Nombre" sortable body={nombreBodyTemplate} headerStyle={{ minWidth: '15rem' }}></Column>

            <Column
              field="tematica"
              header="Temática"
              sortable
              body={tematicaBodyTemplate}
              style={{ width: '50%', alignContent: 'right' }}
              headerStyle={{ minWidth: '15rem' }}
            ></Column>
            <Column field="user" header="Administrador" sortable body={adminBodyTemplate} headerStyle={{ minWidth: '15rem' }}></Column>

            <Column field="user" header="Gestores" sortable body={userBodyTemplate} headerStyle={{ minWidth: '15rem' }}></Column>

            <Column body={actionBodyTemplate} headerStyle={{ minWidth: '10rem' }}></Column>
          </DataTable>

          <Dialog
            visible={ecosistemaDialogNew}
            style={{ width: '450px' }}
            header="Ecosistema"
            modal
            className="p-fluid  "
            onHide={hideDialogNuevo}
          >
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
                      id="ecosistema-id"
                      label={translate('global.field.id')}
                      validate={{ required: true }}
                    />
                  ) : null}
                  <ValidatedField
                    label={translate('jhipsterApp.ecosistema.nombre')}
                    id="ecosistema-nombre"
                    name="nombre"
                    data-cy="nombre"
                    type="text"
                    validate={{
                      required: { value: true, message: translate('entity.validation.required') },
                      maxLength: { value: 100, message: translate('entity.validation.maxlength', { max: 100 }) },
                    }}
                  />
                  <ValidatedField
                    label={translate('jhipsterApp.ecosistema.tematica')}
                    id="ecosistema-tematica"
                    name="tematica"
                    data-cy="tematica"
                    type="textarea"
                    validate={{
                      required: { value: true, message: translate('entity.validation.required') },
                    }}
                  />
                  <ValidatedField
                    label={translate('jhipsterApp.ecosistema.activo')}
                    id="ecosistema-activo"
                    name="activo"
                    data-cy="activo"
                    check
                    type="checkbox"
                  />
                  <ValidatedField
                    name="logoUrlContentType"
                    data-cy="logoUrlContentType"
                    required
                    readOnly
                    hidden
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
                    id="ecosistema-ecosistemaRol"
                    name="ecosistemaRol"
                    data-cy="ecosistemaRol"
                    label={translate('jhipsterApp.ecosistema.ecosistemaRol')}
                    validate={{
                      required: { value: true, message: 'Debe seleccionar un Rol' },
                    }}
                    type="select"
                  >
                    <option value="" key="0" />
                    {ecosistemaRols
                      ? ecosistemaRols.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.ecosistemaRol}
                          </option>
                        ))
                      : null}
                  </ValidatedField>
                  <h6>{translate('jhipsterApp.ecosistema.user')}</h6>
                  <Dropdown
                    id="ecosistema-user"
                    name="user"
                    value={selectedAdmin}
                    options={filtradoUserAdmin()}
                    onChange={onAdminChange}
                    optionLabel="name"
                    filter
                    showClear
                    filterBy="login"
                    placeholder="Seleccione Administrador"
                    valueTemplate={selectedAdminTemplate}
                    itemTemplate={adminOptionTemplate}
                  />
                  &nbsp;
                  <Button
                    color="primary"
                    id="save-entity"
                    data-cy="entityCreateSaveButton"
                    type="submit"
                    disabled={updating || (!selectedFile && isNew)}
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

          <Dialog
            visible={deleteEcosistemaDialog}
            style={{ width: '450px' }}
            header="Confirmar"
            modal
            footer={deleteEcosistemaDialogFooter}
            onHide={hideDeleteEcosistemaDialog}
          >
            <div className="flex align-items-center justify-content-center">
              <i className="pi pi-exclamation-triangle mr-3" style={{ fontSize: '2rem' }} />
              {selectedEcosistema && (
                <span>
                  ¿Seguro que quiere eliminar el Ecosistema: <b>{selectedEcosistema.nombre}</b>?
                </span>
              )}
            </div>
          </Dialog>

          <Dialog visible={gestorDialog} style={{ width: '450px' }} header="Gestores" modal onHide={hideGestorDialog}>
            <div className="mt-3 mb-4">
              {loading ? (
                <SpinnerCar />
              ) : (
                <div className=" justify-content-center">
                  <div className="row">
                    <div className="col-12">
                      <div className="p-inputgroup">
                        <Dropdown
                          value={selectedEcosistemaUsuario}
                          options={filtradoUser()}
                          onChange={onUsuariosChange}
                          optionLabel="login"
                          placeholder="Seleccione el Usuario Gestor"
                          filter
                          emptyMessage="No hay Usuarios tipo Gestor"
                          emptyFilterMessage="No hay resultados"
                          itemTemplate={countryTemplate}
                        />

                        <Button
                          icon="pi pi-user-plus"
                          type="submit"
                          onClick={ActualizarUsuarioEcosistema}
                          className="p-button-primary"
                          disabled={!selectedEcosistemaFiltradoSelecionado}
                        />
                      </div>
                    </div>
                  </div>
                  <div className="row">
                    <DataTable
                      ref={dt}
                      value={selectedEcosistemaFiltrado?.users}
                      selection={selectedEcosistemaFiltrado}
                      onSelectionChange={e => setSelectedEcosistemaFiltrado(e.value)}
                      dataKey="id"
                      rows={10}
                      className="datatable-responsive"
                      emptyMessage="No hay Usuarios..."
                      responsiveLayout="stack"
                    >
                      <Column
                        field="user"
                        header="Gestores"
                        sortable
                        body={userBodyTemplateFiltrado}
                        headerStyle={{ minWidth: '15rem' }}
                      ></Column>

                      <Column body={actionBodyTemplateFiltrado} headerStyle={{ minWidth: '10rem' }}></Column>
                    </DataTable>
                  </div>
                </div>
              )}
            </div>
          </Dialog>
        </div>
      </div>
    </div>
  );
};

export default EcosistemaCrud;
