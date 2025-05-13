import { useAppDispatch, useAppSelector } from 'app/config/store';
import { FilterMatchMode, FilterOperator } from 'primereact/api';
import { Button } from 'primereact/button';
import { InputText } from 'primereact/inputtext';
import React, { useEffect, useRef, useState } from 'react';
import { RouteComponentProps } from 'react-router-dom';
import { ITipoNotificacion } from 'app/shared/model/tipo-notificacion.model';
import {
  getEntities as getTipoNotificacion,
  reset,
  updateEntity,
  deleteEntity,
  createEntity,
  getEntity,
} from './tipo-notificacion.reducer';
import { Dialog } from 'primereact/dialog';
import { Translate, ValidatedBlobField, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { Row } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Toolbar } from 'primereact/toolbar';
import { DataTable, DataTableFilterMeta } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { FileUpload } from 'primereact/fileupload';
import {
  getEntity as getFile,
  getEntities as getFiles,
  createEntity as createFile,
  reset as resetFile,
  getArchivo,
  deletefile,
} from 'app/entities/Files/files.reducer';

import { Avatar } from 'primereact/avatar';
import SpinnerCar from '../loader/spinner';
import { Tag } from 'primereact/tag';

const CrudNotificacion = (props: RouteComponentProps<{ index: string }>) => {
  const dispatch = useAppDispatch();
  const account = useAppSelector(state => state.authentication.account);
  const [globalFilter, setGlobalFilterValue] = useState('');
  const [notificacionDialogNew, setNotificacionDialogNew] = useState(false);
  const [deleteTipoNotificacionDialog, setDeleteTipoNotificacionDialog] = useState(false);
  const [isNew, setNew] = useState(true);
  const dt = useRef(null);

  const [iconoBorrar, setIconoBorrar] = useState('');
  const tiponotificacionList = useAppSelector(state => state.tipoNotificacion.entities);

  const tipoNotificacionEntity = useAppSelector(state => state.tipoNotificacion.entity);
  const loading = useAppSelector(state => state.tipoNotificacion.loading);
  const updating = useAppSelector(state => state.tipoNotificacion.updating);
  const updateSuccess = useAppSelector(state => state.tipoNotificacion.updateSuccess);

  const fileUploadRef = useRef(null);
  const file = useAppSelector(state => state.files.entity);
  const updatingFile = useAppSelector(state => state.files.updating);
  const updateSuccessFile = useAppSelector(state => state.files.updateSuccess);
  const loadingFile = useAppSelector(state => state.files.loading);

  const [selectedFile, setSelectedFile] = useState(null);
  const [fileModificar, setfileModificar] = useState(null);

  const [enviando, setEnviando] = useState(false);

  const emptyTipoNotificacion = {
    id: null,
    tipoNotificacion: '',
    icono: '',
  };
  useEffect(() => {
    if (isNew) {
      dispatch(reset());
      dispatch(resetFile());
    }
    dispatch(getTipoNotificacion({}));
  }, []);

  const borrar = icono => {
    const consulta = deletefile(icono);
    consulta.then(response => {
      setNotificacionDialogNew(false);
    });
  };

  useEffect(() => {
    if (updateSuccess && isNew) setNotificacionDialogNew(false);

    if (updateSuccess && !isNew) {
      setfileModificar(null);
      selectedFile && fileUploadRef.current.upload();
      setNotificacionDialogNew(false);
    }
  }, [updateSuccess]);

  useEffect(() => {
    if (updateSuccessFile && !isNew) {
      borrar(selectedTipoNotificacion.icono);
    }
  }, [updateSuccessFile]);

  const [selectedTipoNotificacion, setSelectedTipoNotificacion] = useState(emptyTipoNotificacion);

  const [filters, setFilters] = useState({
    global: { value: null, matchMode: FilterMatchMode.CONTAINS },
    tipo_notificacion: { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.STARTS_WITH }] },
  });

  const onGlobalFilterChange = e => {
    const value = e.target.value;
    const _filters = { ...filters };
    _filters['global'].value = value;
    setFilters(_filters);
    setGlobalFilterValue(value);
  };
  const atras = () => {
    props.history.push(`/usuario-panel/`);
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
    setSelectedFile(null);
    dispatch(resetFile());
    setNew(true);
  };
  const header = (
    <div className="flex flex-row">
      <h5 className="flex align-items-center justify-content-start">Tipo de Notificación:</h5>

      <div className="flex justify-content-end ml-auto">
        <span className="block mt-2 md:mt-0 p-input-icon-left ">
          <i className="pi pi-search" />
          <InputText value={globalFilter} type="search" onInput={onGlobalFilterChange} placeholder="Buscar..." />
        </span>
      </div>
    </div>
  );
  const confirmDeleteSelected = rowTipoNoti => {
    setDeleteTipoNotificacionDialog(true);
    setSelectedTipoNotificacion(rowTipoNoti);
  };

  const actualizarTipoNotificacion = noticiasact => {
    setNew(false);
    setNotificacionDialogNew(true);
    setSelectedTipoNotificacion(noticiasact);
    setSelectedFile(null);
    dispatch(getEntity(noticiasact.id));
  };

  const actionBodyTemplate = rowData => {
    return (
      <div className="align-items-center justify-content-center">
        <Button
          icon="pi pi-pencil"
          className="p-button-rounded p-button-warning ml-2 mb-1"
          onClick={() => actualizarTipoNotificacion(rowData)}
        />

        <Button icon="pi pi-trash" className="p-button-rounded p-button-danger ml-2 mb-1" onClick={() => confirmDeleteSelected(rowData)} />
      </div>
    );
  };
  const rightToolbarTemplate = () => {
    return (
      <React.Fragment>
        <Button label="Nuevo Tipo Notificación" icon="pi pi-plus" className="p-button-info" onClick={verDialogNuevo} />
      </React.Fragment>
    );
  };

  const hideDialogNuevo = () => {
    setNotificacionDialogNew(false);
    dispatch(resetFile());
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...selectedTipoNotificacion,
        };

  const handleFileUpload = event => {
    const fileupload = event.files[0];
    const formData = new FormData();
    formData.append('files', selectedFile);
    dispatch(createFile(formData));
  };

  const saveEntity = values => {
    const entity = {
      ...values,
      tipoNotificacionEntity,
      icono: selectedFile ? selectedFile.name : values.icono,
    };

    if (isNew) {
      fileUploadRef.current.upload();
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
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

  const hideDeleteEcosistemaDialog = () => {
    setDeleteTipoNotificacionDialog(false);
  };
  useEffect(() => {
    if (updateSuccess && deleteTipoNotificacionDialog) {
      borrar(selectedTipoNotificacion.icono);
      setDeleteTipoNotificacionDialog(false);
      setSelectedTipoNotificacion(null);
    }
  }, [updateSuccess]);

  const deleteTipoNoti = () => {
    dispatch(deleteEntity(selectedTipoNotificacion.id));
  };

  const onTemplateRemove = (file1, callback) => {
    setSelectedFile(null);
    callback();
  };
  const deleteEcosistemaDialogFooter = (
    <>
      <Button label="No" icon="pi pi-times" className="p-button-text" onClick={hideDeleteEcosistemaDialog} />

      <Button label="Sí" icon="pi pi-check" className="p-button-text" onClick={deleteTipoNoti} />
    </>
  );

  return (
    <>
      <div className="grid crud-demo mt-3 mb-4">
        <div className="col-12">
          <div className="card">
            <Toolbar className="mb-4" left={leftToolbarTemplate} right={rightToolbarTemplate}></Toolbar>

            <DataTable
              ref={dt}
              value={tiponotificacionList}
              selection={selectedTipoNotificacion}
              onSelectionChange={e => setSelectedTipoNotificacion(e.value)}
              dataKey="id"
              paginator
              rows={10}
              rowsPerPageOptions={[5, 10, 25]}
              className="datatable-responsive"
              paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
              currentPageReportTemplate="Mostrando del {first} al {last} de {totalRecords} Tipo Notificación"
              filters={filters as DataTableFilterMeta}
              filterDisplay="menu"
              emptyMessage="No hay Tipo Notificación..."
              header={header}
              responsiveLayout="stack"
            >
              <Column field="id" header="Id" hidden headerStyle={{ minWidth: '15rem' }}></Column>

              <Column field="tipoNotificacion" header="Tipo de Notificación" sortable headerStyle={{ minWidth: '40rem' }}></Column>

              <Column field="icono" header="Icono" sortable body={iconoTemplate} headerStyle={{ minWidth: '15rem' }}></Column>

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
                        hidden
                        id="noticias-id"
                        label={translate('global.field.id')}
                        validate={{ required: true }}
                      />
                    ) : null}

                    <ValidatedField
                      label={translate('jhipsterApp.tipoNotificacion.tipoNotificacion')}
                      id="tipo-notificacion"
                      name="tipoNotificacion"
                      data-cy="tipoNotificacion"
                      type="text"
                      validate={{
                        required: { value: true, message: translate('entity.validation.required') },
                      }}
                    />
                    <ValidatedField name="icono" required readOnly hidden id="icono" type="text" label={translate('global.field.id')} />

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

                    <Button
                      className="mt-2"
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
              visible={deleteTipoNotificacionDialog}
              style={{ width: '450px' }}
              header="Confirmar"
              modal
              footer={deleteEcosistemaDialogFooter}
              onHide={hideDeleteEcosistemaDialog}
            >
              <div className="flex align-items-center justify-content-center">
                <i className="pi pi-exclamation-triangle mr-3" style={{ fontSize: '2rem' }} />
                {selectedTipoNotificacion && (
                  <span>
                    ¿Seguro que quiere eliminar el Tipo de Notificación: <b>{selectedTipoNotificacion.tipoNotificacion}</b>?
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

export default CrudNotificacion;
