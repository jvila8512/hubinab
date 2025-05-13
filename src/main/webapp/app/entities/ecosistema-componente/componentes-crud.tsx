import { useAppDispatch, useAppSelector } from 'app/config/store';
import React, { useEffect, useRef, useState } from 'react';
import { createEntity, getEntities, reset, updateEntity, getComponentesbyEcosistema, deleteEntity } from './ecosistema-componente.reducer';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Toolbar } from 'primereact/toolbar';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';

import { FilterMatchMode, FilterOperator } from 'primereact/api';

import { InputText } from 'primereact/inputtext';
import { Button } from 'primereact/button';
import { Translate, ValidatedBlobField, ValidatedField, ValidatedForm, openFile, translate } from 'react-jhipster';
import { Dialog } from 'primereact/dialog';
import { Row } from 'reactstrap';
import { IComponentes } from 'app/shared/model/componentes.model';
import { getEntities as getComponentes } from 'app/entities/componentes/componentes.reducer';
import { IEcosistema } from 'app/shared/model/ecosistema.model';
import { getEntity as getEcosistema } from 'app/entities/ecosistema/ecosistema.reducer';
import { IEcosistemaComponente } from 'app/shared/model/ecosistema-componente.model';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
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
import { Tag } from 'primereact/tag';
import Cargando from '../loader/cargando';
import { toast } from 'react-toastify';

const ComponentesCrud = (props: RouteComponentProps<{ id: string; index: string }>) => {
  const dispatch = useAppDispatch();

  const componenteList = useAppSelector(state => state.ecosistemaComponente.entities);
  const loading = useAppSelector(state => state.ecosistemaComponente.loading);
  const ecosistemaEntity = useAppSelector(state => state.ecosistema.entity);
  const componenteEntity = useAppSelector(state => state.ecosistemaComponente.entity);
  const updating = useAppSelector(state => state.ecosistemaComponente.updating);
  const updateSuccess = useAppSelector(state => state.ecosistemaComponente.updateSuccess);

  const componentes = useAppSelector(state => state.componentes.entities);

  // const regex = /^(?:https?:\/\/)[a-zA-Z0-9.-]+(:\d+)?(\/\w+)*$/;
  // const regex = /^(?:https?:\/\/)[a-zA-Z0-9.-]+(:\d+)?(\/\w+)*(\/)?$/;
  // para guiones
  const regex = /^(?:https?:\/\/)[a-zA-Z0-9.-]+(:\d+)?(\/[\w-]+)*(\/)?$/;

  const emptyComponente = {
    id: null,
    link: null,
    documentoUrlContentType: null,
    descripcion: null,
    documentoUrl: '',
    componente: null,
    ecosistema: null,
  };
  const [selectedComponente, setSelectedComponente] = useState(emptyComponente);

  const [isNew, setNew] = useState(true);
  const dt = useRef(null);
  const account = useAppSelector(state => state.authentication.account);
  const [globalFilter, setGlobalFilterValue] = useState('');
  const [componenteDialogNew, setComponenteDialogNew] = useState(false);
  const [componenteDeleteDialog, setDeleteComponenteDialog] = useState(false);
  const [link, setLink] = useState(null);

  useEffect(() => {
    dispatch(getComponentesbyEcosistema(props.match.params.id));
    dispatch(getComponentes({}));
    dispatch(getEcosistema(props.match.params.id));
  }, []);

  useEffect(() => {
    if (updateSuccess && isNew) setComponenteDialogNew(false);

    if (updateSuccess && !isNew) {
      setfileModificar(null);
      selectedFile && fileUploadRef.current.upload();
      setComponenteDialogNew(false);
    }
    dispatch(getComponentesbyEcosistema(props.match.params.id));
  }, [updateSuccess]);

  const { match } = props;

  const fileUploadRef = useRef(null);
  const file = useAppSelector(state => state.files.entity);
  const updatingFile = useAppSelector(state => state.files.updating);
  const updateSuccessFile = useAppSelector(state => state.files.updateSuccess);
  const loadingFile = useAppSelector(state => state.files.loading);

  const [cargandoFile, setcargandoFile] = useState(false);

  const [selectedFile, setSelectedFile] = useState(null);
  const [fileModificar, setfileModificar] = useState(null);

  const borrar = icono => {
    const consulta = deletefile(icono);
    consulta.then(response => {
      setComponenteDialogNew(false);
    });
  };

  useEffect(() => {
    if (updateSuccessFile && !isNew) {
      borrar(selectedComponente.documentoUrlContentType);
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
            Puede arrastrar y soltar aquí
          </span>
        ) : (
          <span style={{ fontSize: '1.2em', color: 'var(--text-color-secondary)' }} className="my-5">
            Puede arrastrar y soltar el documento para Modificar
          </span>
        )}
      </div>
    );
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
    setComponenteDialogNew(true);
    setNew(true);
  };

  const rightToolbarTemplate = () => {
    return (
      <React.Fragment>
        {account?.authorities?.find(rol => rol === 'ROLE_ADMINECOSISTEMA') &&
          ecosistemaEntity?.users?.find(user => user.id === account?.id) && (
            <Button label="Nuevo Componente" icon="pi pi-plus" className="p-button-info" onClick={verDialogNuevo} />
          )}
      </React.Fragment>
    );
  };
  const header = (
    <div className="flex flex-column md:flex-row  md:align-items-start">
      <h3 className="m-0 text-700 text-xl text-blue-600 font-medium ">Componentes del Ecosistema: {ecosistemaEntity.nombre}</h3>
    </div>
  );
  const nombreBodyTemplate = rowData => {
    return <>{rowData.componente.componente}</>;
  };
  const componenteBodyTemplate = rowData => {
    return <>{rowData.componentehijo}</>;
  };
  const handleFileDownload = (base64Data, fileName) => {
    const linkk = document.createElement('a');
    linkk.setAttribute('href', 'data:aplication/pdf;base64,' + base64Data);
    linkk.setAttribute('download', fileName);
    document.body.appendChild(linkk);
    linkk.click();
    setcargandoFile(false);
  };

  const descargarDocumento = row => {
    setcargandoFile(true);
    const retosFiltrar = getArchivo(row.documentoUrlContentType);
    retosFiltrar
      .then(response => {
        handleFileDownload(response.data, row.documentoUrlContentType);
      })
      .catch(error => {
        // eslint-disable-next-line no-console
        console.error('Error al obtener el archivo: ', error);
        toast.error('Error al obtener el archivo');
      });
  };

  const documentoBodyTemplate = rowData => {
    return (
      <>
        {rowData.documentoUrlContentType ? (
          <Button label="Descargar" className="p-button-secondary p-button-text" onClick={() => descargarDocumento(rowData)} />
        ) : null}
      </>
    );
  };
  const linkBodyTemplate = rowData => {
    return (
      <>
        {rowData.link ? (
          <a href={rowData.link} rel="noreferrer" className="text-blue" target="_blank">
            {rowData.link}
          </a>
        ) : null}
      </>
    );
  };
  const descripcionBodyTemplate = rowData => {
    return (
      <>
        <span className="pl-5 text-justify"> {rowData.descripcion}</span>
      </>
    );
  };
  const confirmDeleteSelected = rowEcosistema => {
    setDeleteComponenteDialog(true);
    setSelectedComponente(rowEcosistema);
  };
  const actualizarEcosistema = componenteact => {
    setSelectedComponente(componenteact);
    setComponenteDialogNew(true);
    setLink(componenteact.link);
    setNew(false);
  };
  const actionBodyTemplate = rowData => {
    return (
      <div className="align-items-center justify-content-center">
        <Button icon="pi pi-trash" className="p-button-rounded p-button-danger ml-2 mb-1" onClick={() => confirmDeleteSelected(rowData)} />
        <Button icon="pi pi-pencil" className="p-button-rounded p-button-warning ml-2 mb-1" onClick={() => actualizarEcosistema(rowData)} />
      </div>
    );
  };
  const hideDialogNuevo = () => {
    setComponenteDialogNew(false);
  };
  const saveEntity = values => {
    const entity = {
      ...values,
      componente: componentes.find(it => it.id.toString() === values.componente.toString()),
      ecosistema: ecosistemaEntity,
      documentoUrlContentType: selectedFile ? selectedFile.name : values.documentoUrlContentType,
    };

    if (isNew) {
      fileUploadRef.current.upload();
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...selectedComponente,
          componente: selectedComponente?.componente?.id,
          ecosistema: ecosistemaEntity,
        };
  const hideDeleteComponenteDialog = () => {
    setDeleteComponenteDialog(false);
  };
  const deleteEcosistema = () => {
    dispatch(deleteEntity(selectedComponente.id));
    borrar(selectedComponente.documentoUrlContentType);
    setDeleteComponenteDialog(false);
    setSelectedComponente(null);
  };
  const deleteComponenteDialogFooter = (
    <>
      <Button label="No" icon="pi pi-times" className="p-button-text" onClick={hideDeleteComponenteDialog} />
      <Button label="Sí" icon="pi pi-check" className="p-button-text" onClick={deleteEcosistema} />
    </>
  );
  const buscarComponenteEcositema = u => {
    const componente = componenteList.find(it => it.componente.id === u.id);

    if (componente) return false;
    else return true;
  };
  const filtradoComponentes = () => {
    return componentes.filter(comp => comp.id === buscarComponenteEcositema(comp));
  };

  return (
    <div className="grid crud-demo mt-3 mb-4">
      <div className="col-12">
        <div className="card">
          <Toolbar className="mb-4" left={leftToolbarTemplate} right={rightToolbarTemplate}></Toolbar>
          <DataTable
            ref={dt}
            value={componenteList}
            selection={selectedComponente}
            onSelectionChange={e => setSelectedComponente(e.value)}
            dataKey="id"
            rows={10}
            className="datatable-responsive"
            emptyMessage="No hay Componentes para el Ecosistema..."
            header={header}
            responsiveLayout="stack"
          >
            <Column field="id" header="Id" hidden headerStyle={{ minWidth: '15rem' }}></Column>
            <Column field="nombre" header="Tipo Componente" sortable body={nombreBodyTemplate} headerStyle={{ minWidth: '15rem' }}></Column>
            <Column
              field="componentehijo"
              header="Componente"
              sortable
              body={componenteBodyTemplate}
              headerStyle={{ minWidth: '15rem' }}
            ></Column>

            <Column
              field="componente"
              header="Descripción"
              sortable
              body={descripcionBodyTemplate}
              style={{ width: '50%' }}
              headerStyle={{ minWidth: '15rem' }}
            ></Column>
            <Column
              field="documentoUrl"
              header="Documento"
              sortable
              body={documentoBodyTemplate}
              headerStyle={{ minWidth: '15rem' }}
            ></Column>

            <Column field="link" header="Dirección" sortable body={linkBodyTemplate} headerStyle={{ minWidth: '15rem' }}></Column>

            <Column body={actionBodyTemplate} headerStyle={{ minWidth: '10rem' }}></Column>
          </DataTable>
          {cargandoFile && <Cargando />}
          <Dialog
            visible={componenteDialogNew}
            style={{ width: '450px' }}
            header="Componente"
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
                      id="ecosistema-componente-id"
                      label={translate('global.field.id')}
                      validate={{ required: true }}
                    />
                  ) : null}
                  <ValidatedField
                    label={translate('jhipsterApp.ecosistemaComponente.componentehijo')}
                    id="ecosistema-componente-componentehijo"
                    name="componentehijo"
                    data-cy="componentehijo"
                    type="text"
                    validate={{
                      required: { value: true, message: translate('entity.validation.required') },
                    }}
                  />
                  <ValidatedField
                    label={translate('jhipsterApp.ecosistemaComponente.descripcion')}
                    id="ecosistema-componente-descripcion"
                    name="descripcion"
                    data-cy="descripcion"
                    type="textarea"
                    validate={{
                      required: { value: true, message: translate('entity.validation.required') },
                    }}
                  />
                  <ValidatedField
                    label={translate('jhipsterApp.ecosistemaComponente.link')}
                    id="ecosistema-componente-link"
                    name="link"
                    data-cy="link"
                    type="text"
                    validate={{
                      validate: v =>
                        (!(v.length === 0) ? regex.test(v) : true) ||
                        'La URL debe comenzar con "https:// o http://" y tener un dominio válido',
                    }}
                  />
                  <ValidatedField
                    name="documentoUrlContentType"
                    data-cy="documentoUrlContentType"
                    required
                    readOnly
                    hidden
                    id="documentoUrlContentType"
                    type="text"
                  />
                  <FileUpload
                    ref={fileUploadRef}
                    name="demo[1]"
                    accept="application/pdf"
                    maxFileSize={1000000}
                    chooseLabel={isNew ? 'Suba el documento' : 'Suba el documento nuevo'}
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
                    id="ecosistema-componente-componente"
                    name="componente"
                    data-cy="componente"
                    label={translate('jhipsterApp.ecosistemaComponente.componente')}
                    type="select"
                    validate={{
                      required: { value: true, message: translate('entity.validation.required') },
                    }}
                  >
                    <option value="" key="0" />
                    {componentes
                      ? componentes.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.componente}
                          </option>
                        ))
                      : null}
                  </ValidatedField>
                  &nbsp;
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
            visible={componenteDeleteDialog}
            style={{ width: '450px' }}
            header="Confirmar"
            modal
            footer={deleteComponenteDialogFooter}
            onHide={hideDeleteComponenteDialog}
          >
            <div className="flex align-items-center justify-content-center">
              <i className="pi pi-exclamation-triangle mr-3" style={{ fontSize: '2rem' }} />
              {selectedComponente && (
                <span>
                  ¿Seguro que quiere eliminar el Componente: <b>{selectedComponente?.componente?.componente}</b> del Ecosistema?
                </span>
              )}
            </div>
          </Dialog>
        </div>
      </div>
    </div>
  );
};

export default ComponentesCrud;
