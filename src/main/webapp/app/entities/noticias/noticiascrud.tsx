import React, { useEffect, useRef, useState } from 'react';
import { INoticias } from 'app/shared/model/noticias.model';
import {
  getEntity,
  updateEntity,
  createEntity,
  reset,
  getEntities,
  getNoticiasByEcosistemaId,
  deleteEntity,
  getNoticiasByPublicabyEcosistemaIdbyUserId,
} from './noticias.reducer';
import { RouteComponentProps } from 'react-router-dom';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IEcosistema } from 'app/shared/model/ecosistema.model';
import { getEntities as getEcosistemas, getEntity as getEcosistema } from 'app/entities/ecosistema/ecosistema.reducer';
import { ITipoNoticia } from 'app/shared/model/tipo-noticia.model';
import { getEntities as getTipoNoticias } from 'app/entities/tipo-noticia/tipo-noticia.reducer';
import { Toolbar } from 'primereact/toolbar';
import { Button } from 'primereact/button';
import { DataTable, DataTableFilterMeta } from 'primereact/datatable';
import { InputText } from 'primereact/inputtext';
import { FilterMatchMode, FilterOperator } from 'primereact/api';
import { Column } from 'primereact/column';
import { Dialog } from 'primereact/dialog';
import { Row } from 'reactstrap';
import { Translate, ValidatedBlobField, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Tag } from 'primereact/tag';

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

export const NoticiasCrud = (props: RouteComponentProps<{ id: string; index: string }>) => {
  const dispatch = useAppDispatch();
  const noticiasList = useAppSelector(state => state.noticias.entities);
  const [isNew, setNew] = useState(true);

  const users = useAppSelector(state => state.userManagement.users);
  const ecosistemaEntity = useAppSelector(state => state.ecosistema.entity);
  const tipoNoticias = useAppSelector(state => state.tipoNoticia.entities);
  const noticiasEntity = useAppSelector(state => state.noticias.entity);
  const loading = useAppSelector(state => state.noticias.loading);
  const updating = useAppSelector(state => state.noticias.updating);
  const updateSuccess = useAppSelector(state => state.noticias.updateSuccess);
  const account = useAppSelector(state => state.authentication.account);

  const [globalFilter, setGlobalFilterValue] = useState('');
  const [noticiasDialogNew, setNoticiasDialogNew] = useState(false);
  const [deleteNoticiasDialog, setDeleteNoticiasDialog] = useState(false);

  const [text2, setText2] = useState('');

  const emptyEcosistema = {
    id: null,
    titulo: '',
    descripcion: '',
    urlFotoContentType: '',
    urlFoto: '',
    publica: null,
    publicar: null,
    fechaCreada: null,
    user: null,
    ecosistema: null,
    tipoNoticia: null,
  };

  const [selectedNoticias, setSelectedNoticias] = useState(emptyEcosistema);

  const dt = useRef(null);

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
      setNoticiasDialogNew(false);
    });
  };
  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    }

    dispatch(
      getNoticiasByPublicabyEcosistemaIdbyUserId({
        id: props.match.params.id,
        iduser: account.id,
      })
    );
    dispatch(getUsers({}));
    dispatch(getEcosistema(props.match.params.id));
    dispatch(getTipoNoticias({}));
  }, []);

  useEffect(() => {
    if (updateSuccess && isNew) {
      dispatch(reset());
      setNoticiasDialogNew(false);
    }

    if (updateSuccess && !isNew) {
      setfileModificar(null);
      selectedFile && fileUploadRef.current.upload();
      setNoticiasDialogNew(false);
    }
    if (updateSuccess) {
      dispatch(
        getNoticiasByPublicabyEcosistemaIdbyUserId({
          id: props.match.params.id,
          iduser: account.id,
        })
      );
    }
  }, [updateSuccess]);

  useEffect(() => {
    if (updateSuccessFile && !isNew) {
      borrar(selectedNoticias.urlFotoContentType);
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
            Puede arrastrar y soltar la imagen aquí
          </span>
        ) : (
          <span style={{ fontSize: '1.2em', color: 'var(--text-color-secondary)' }} className="my-5">
            Puede arrastrar y soltar la imagen para Modificar
          </span>
        )}
      </div>
    );
  };

  const [filters, setFilters] = useState({
    global: { value: null, matchMode: FilterMatchMode.CONTAINS },
    titulo: { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.STARTS_WITH }] },
    descripcion: { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.STARTS_WITH }] },
    fechaCreada: { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.DATE_IS }] },
    user: { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.STARTS_WITH }] },
  });

  const onGlobalFilterChange = e => {
    const value = e.target.value;
    const _filters = { ...filters };
    _filters['global'].value = value;

    setFilters(_filters);
    setGlobalFilterValue(value);
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
  const verDialogNuevo = () => {
    setNoticiasDialogNew(true);
    setNew(true);
  };
  const confirmDeleteSelected = rowNoticia => {
    setDeleteNoticiasDialog(true);
    setSelectedNoticias(rowNoticia);
    setSelectedFile(null);
  };
  const atrasvista = () => {
    props.history.push(`/entidad/noticias/grid-noticias/${props.match.params.id}/${props.match.params.index}`);
  };
  const rightToolbarTemplate = () => {
    return (
      <React.Fragment>
        <Button label="Cambiar Vista" icon="pi pi-arrow-up" className="p-button-primary mr-2" onClick={atrasvista} />
        <Button label="Nueva Noticia" icon="pi pi-plus" className="p-button-info" onClick={verDialogNuevo} />
      </React.Fragment>
    );
  };
  const header = (
    <div className="flex flex-column md:flex-row md:justify-content-between md:align-items-center">
      <h5 className="m-0 text-blue-600">Noticias:</h5>
      <h5 className="m-0 ">
        <span className="text-blue-600"> Ecosistema: {ecosistemaEntity.nombre}</span>
      </h5>
      <span className="block mt-2 md:mt-0 p-input-icon-left">
        <i className="pi pi-search" />
        <InputText value={globalFilter} type="search" onInput={onGlobalFilterChange} placeholder="Buscar..." />
      </span>
    </div>
  );
  const tituloBodyTemplate = rowData => {
    return <>{rowData.titulo}</>;
  };
  const descripcionBodyTemplate = rowData => {
    return (
      <>
        <span className=" pl-2 surface-overlay w-full h-3rem overflow-hidden text-overflow-ellipsis">{rowData.descripcion}</span>
      </>
    );
  };
  const vernoticia = rowNoticia => {
    props.history.push(`/entidad/noticias/noticia/${rowNoticia.id}/${props.match.params.id}/${props.match.params.index}`);
  };

  const actionBodyTemplate = rowData => {
    return (
      <div className="align-items-center justify-content-center">
        {rowData.publica && (
          <Button icon="pi pi-eye" className="p-button-rounded p-button-info ml-2 mb-1" onClick={() => vernoticia(rowData)} />
        )}

        {(account?.authorities?.find(rol => rol === 'ROLE_ADMINECOSISTEMA') || rowData.user.id === account.id) && (
          <Button
            icon="pi pi-trash"
            className="p-button-rounded p-button-danger ml-2 mb-1"
            onClick={() => confirmDeleteSelected(rowData)}
          />
        )}

        {(account?.authorities?.find(rol => rol === 'ROLE_ADMINECOSISTEMA') || rowData.user.id === account.id) && (
          <Button icon="pi pi-pencil" className="p-button-rounded p-button-warning ml-2 mb-1" onClick={() => actualizarNoticias(rowData)} />
        )}
      </div>
    );
  };
  const saveEntity = values => {
    const entity = {
      ...values,

      ecosistema: ecosistemaEntity,
      urlFotoContentType: selectedFile ? selectedFile.name : values.urlFotoContentType,
      tipoNoticia: tipoNoticias.find(it => it.id.toString() === values.tipoNoticia.toString()),
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
          ...selectedNoticias,

          ecosistema: ecosistemaEntity,
          tipoNoticia: selectedNoticias?.tipoNoticia?.id,
        };
  const hideDialogNuevo = () => {
    setNoticiasDialogNew(false);
  };
  const hideDeleteNoticiasDialog = () => {
    setDeleteNoticiasDialog(false);
  };
  const deleteNoticia = () => {
    dispatch(deleteEntity(selectedNoticias.id));
    borrar(selectedNoticias.urlFotoContentType);
    setDeleteNoticiasDialog(false);
    setSelectedNoticias(null);
  };

  const deleteEcosistemaDialogFooter = (
    <>
      <Button label="No" icon="pi pi-times" className="p-button-text" onClick={hideDeleteNoticiasDialog} />

      <Button label="Sí" icon="pi pi-check" className="p-button-text" onClick={deleteNoticia} />
    </>
  );
  const publicaTemplate = rowData => {
    return <>{rowData.publica ? <Tag value="Visible" severity="success"></Tag> : <Tag value="No Visible" severity="danger"></Tag>}</>;
  };
  const publicarTemplate = rowData => {
    return <>{rowData.publicar ? <Tag value="Publicada" severity="success"></Tag> : <Tag value="No Publicada" severity="danger"></Tag>}</>;
  };
  const actualizarNoticias = noticiasact => {
    setSelectedNoticias(noticiasact);
    setNoticiasDialogNew(true);
    setNew(false);
  };
  const fechaBodyTemplate = rowData => {
    return <>{rowData.fechaCreada}</>;
  };
  const userBodyTemplate = rowData => {
    return <>{rowData.user.login}</>;
  };

  return (
    <>
      <div className="grid crud-demo mt-3 mb-4">
        <div className="col-12">
          <div className="card">
            <Toolbar className="mb-4" left={leftToolbarTemplate} right={rightToolbarTemplate}></Toolbar>

            <DataTable
              ref={dt}
              value={noticiasList}
              selection={selectedNoticias}
              onSelectionChange={e => setSelectedNoticias(e.value)}
              dataKey="id"
              paginator
              sortField="fechaCreada"
              rows={10}
              rowsPerPageOptions={[5, 10, 25]}
              className="datatable-responsive"
              paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
              currentPageReportTemplate="Mostrando del {first} al {last} de {totalRecords} Noticias"
              filters={filters as DataTableFilterMeta}
              filterDisplay="menu"
              emptyMessage="No hay Noticias..."
              header={header}
              responsiveLayout="stack"
            >
              <Column field="id1" header="Id" hidden headerStyle={{ minWidth: '15rem' }}></Column>

              <Column field="titulo" header="Titulo" sortable body={tituloBodyTemplate} headerStyle={{ minWidth: '15rem' }}></Column>

              <Column
                field="descripcion"
                header="Noticia"
                sortable
                body={descripcionBodyTemplate}
                style={{ width: '40%' }}
                headerStyle={{ minWidth: '15rem' }}
              ></Column>
              <Column field="publica" sortable header="Estado" body={publicaTemplate} headerStyle={{ minWidth: '7rem' }}></Column>
              <Column field="publicar" sortable header="Publicada" body={publicarTemplate} headerStyle={{ minWidth: '7rem' }}></Column>

              <Column field="fechaCreada" sortable header="Fecha" body={fechaBodyTemplate} headerStyle={{ minWidth: '7rem' }}></Column>
              <Column field="user" sortable header="Usuario" body={userBodyTemplate} headerStyle={{ minWidth: '7rem' }}></Column>

              <Column body={actionBodyTemplate} headerStyle={{ minWidth: '10rem' }}></Column>
            </DataTable>

            <Dialog
              visible={noticiasDialogNew}
              style={{ width: '600px' }}
              header="Noticia"
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
                        id="noticias-id"
                        label={translate('global.field.id')}
                        validate={{ required: true }}
                      />
                    ) : null}

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
                      label={translate('jhipsterApp.noticias.titulo')}
                      id="noticias-titulo"
                      name="titulo"
                      data-cy="titulo"
                      type="text"
                      validate={{
                        required: { value: true, message: translate('entity.validation.required') },
                      }}
                    />

                    <ValidatedField
                      label={translate('jhipsterApp.noticias.descripcion')}
                      id="noticias-descripcion"
                      name="descripcion"
                      data-cy="descripcion"
                      type="textarea"
                      validate={{
                        required: { value: true, message: translate('entity.validation.required') },
                      }}
                    />

                    <ValidatedField
                      label={translate('jhipsterApp.noticias.publica')}
                      id="noticias-publica"
                      name="publica"
                      data-cy="publica"
                      check
                      type="checkbox"
                    />

                    {account.authorities.find(rol => rol === 'ROLE_ADMINECOSISTEMA') && (
                      <ValidatedField
                        label={translate('jhipsterApp.noticias.publicar')}
                        id="noticias-publicar"
                        name="publicar"
                        data-cy="publicar"
                        check
                        type="checkbox"
                      />
                    )}

                    <ValidatedField
                      label={translate('jhipsterApp.noticias.fechaCreada')}
                      id="noticias-fechaCreada"
                      name="fechaCreada"
                      data-cy="fechaCreada"
                      type="date"
                      validate={{
                        required: { value: true, message: translate('entity.validation.required') },
                      }}
                    />

                    <ValidatedField
                      id="noticias-tipoNoticia"
                      name="tipoNoticia"
                      data-cy="tipoNoticia"
                      label={translate('jhipsterApp.noticias.tipoNoticia')}
                      type="select"
                      validate={{
                        required: { value: true, message: translate('entity.validation.required') },
                      }}
                    >
                      <option value="" key="0" />
                      {tipoNoticias
                        ? tipoNoticias.map(otherEntity => (
                            <option value={otherEntity.id} key={otherEntity.id}>
                              {otherEntity.tipoNoticia}
                            </option>
                          ))
                        : null}
                    </ValidatedField>

                    <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
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
              visible={deleteNoticiasDialog}
              style={{ width: '450px' }}
              header="Confirmar"
              modal
              footer={deleteEcosistemaDialogFooter}
              onHide={hideDeleteNoticiasDialog}
            >
              <div className="flex align-items-center justify-content-center">
                <i className="pi pi-exclamation-triangle mr-3" style={{ fontSize: '2rem' }} />
                {selectedNoticias && (
                  <span>
                    ¿Seguro que quiere eliminar la Noticia: <b>{selectedNoticias.titulo}</b>?
                  </span>
                )}
              </div>
            </Dialog>
          </div>
        </div>
      </div>
    </>
  );
};

export default NoticiasCrud;
