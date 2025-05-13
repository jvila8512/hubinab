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
import { Col, Row, Spinner } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { hasAnyAuthority } from 'app/shared/auth/private-route';
import { Tag } from 'primereact/tag';

import { IComunidad } from 'app/shared/model/comunidad.model';
import { getEntity, updateEntity, createEntity, reset, deleteEntity, getEntities, getEntitiesActivas } from './comunidad.reducer';
import { mapIdList } from 'app/shared/util/entity-utils';
import { Avatar } from 'primereact/avatar';
import { Calendar } from 'primereact/calendar';
import dayjs from 'dayjs';
import { Chip } from 'primereact/chip';

const Comunidades = (props: RouteComponentProps<{ id: string; index: string }>) => {
  const dispatch = useAppDispatch();
  const comunidadList = useAppSelector(state => state.comunidad.entities);
  const comunidadEntity = useAppSelector(state => state.comunidad.entity);
  const loading = useAppSelector(state => state.comunidad.loading);
  const updating = useAppSelector(state => state.comunidad.updating);
  const updateSuccess = useAppSelector(state => state.comunidad.updateSuccess);
  const [isNew, setNew] = useState(true);

  const [retoDialog, setRetoDialog] = useState(false);
  const [retoDialogNew, setRetoDialogNew] = useState(false);
  const [globalFilter, setGlobalFilterValue] = useState('');
  const [deleteRetoDialog, setDeleteRetoDialog] = useState(false);

  const dt = useRef(null);

  const account = useAppSelector(state => state.authentication.account);

  const emptyComunidad = {
    id: null,
    comunidad: '',
    descripcion: '',
    activo: false,
    link: '',
  };
  const [proyecto, setProyecto] = useState(null);
  const [selectedProyecto, setSelectedProyecto] = useState(emptyComunidad);

  const fileUploadRef = useRef(null);
  const file = useAppSelector(state => state.files.entity);
  const updatingFile = useAppSelector(state => state.files.updating);
  const updateSuccessFile = useAppSelector(state => state.files.updateSuccess);
  const loadingFile = useAppSelector(state => state.files.loading);

  const [selectedFile, setSelectedFile] = useState(null);
  const [fileModificar, setfileModificar] = useState(null);
  const regex = /^(?:https?:\/\/)[a-zA-Z0-9.-]+(:\d+)?(\/\w+)*$/;

  useEffect(() => {
    if (updateSuccess && isNew) {
      setRetoDialogNew(false);
      setSelectedProyecto(null);
    }

    if (updateSuccess && !isNew) {
      setRetoDialogNew(false);
    }
  }, [updateSuccess]);

  const handleFileUpload = event => {
    const fileupload = event.files[0];
    const formData = new FormData();
    formData.append('files', selectedFile);
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
    comunidad: { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.STARTS_WITH }] },
    descripcion: { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.STARTS_WITH }] },
    link: { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.STARTS_WITH }] },
  });

  useEffect(() => {
    dispatch(getEntities({}));
    return () => {
      dispatch(getEntitiesActivas());
    };
  }, []);

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

  const rightToolbarTemplate = () => {
    return (
      <React.Fragment>
        {account?.authorities?.find(rol => rol === 'ROLE_ADMIN') && (
          <Button label="Nueva Comunidad" icon="pi pi-plus" className="p-button-info" onClick={verDialogNuevo} />
        )}
      </React.Fragment>
    );
  };
  const header = (
    <div className="grid">
      <div className="col">
        <div className="flex justify-content-start  font-bold  m-2">
          <div className="text-primary text-xl">Comunidad</div>
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
    return <>{rowData.comunidad}</>;
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
        <Button icon="pi pi-trash" className="p-button-rounded p-button-danger ml-2 mb-1" onClick={() => verIdeas(rowData)} />

        <Button icon="pi pi-pencil" className="p-button-rounded p-button-warning ml-2 mb-1" onClick={() => actualizar(rowData)} />
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
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };
  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...proyecto,
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
  const activos = user => {
    return <div className="flex flex-column ">{user.activo && <Tag value="Activo" severity="success"></Tag>}</div>;
  };

  return (
    <div className="grid crud-demo mt-3 mb-4">
      <div className="col-12">
        <div className="card">
          <Toolbar className="mb-4" left={leftToolbarTemplate} right={rightToolbarTemplate}></Toolbar>

          <DataTable
            ref={dt}
            value={comunidadList}
            selection={selectedProyecto}
            onSelectionChange={e => setSelectedProyecto(e.value)}
            dataKey="id"
            paginator
            rows={10}
            rowsPerPageOptions={[5, 10, 25]}
            className="datatable-responsive"
            paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
            currentPageReportTemplate="Mostrando del {first} al {last} de {totalRecords} Comunidades"
            filters={filters as DataTableFilterMeta}
            filterDisplay="menu"
            loading={loading}
            emptyMessage="No hay Comunidades..."
            header={header}
            responsiveLayout="stack"
          >
            <Column field="id" header="Id" hidden headerStyle={{ minWidth: '15rem' }}></Column>
            <Column field="comunidad" header="Comunidad" sortable body={retoBodyTemplate} headerStyle={{ minWidth: '15rem' }}></Column>
            <Column
              field="descricion"
              header="Descripción"
              style={{ width: '30%', alignContent: 'right' }}
              sortable
              body={nameBodyTemplate}
              headerStyle={{ minWidth: '15rem' }}
            ></Column>
            <Column field="link" header="Dirección" sortable body={linkBodyTemplate} headerStyle={{ minWidth: '15rem' }}></Column>
            <Column field="activo" header="Estado" sortable body={activos} headerStyle={{ minWidth: '5rem' }}></Column>

            <Column body={actionBodyTemplate} headerStyle={{ minWidth: '10rem' }}></Column>
          </DataTable>

          <Dialog
            visible={retoDialog}
            style={{ width: '500px' }}
            header="Comunidad"
            modal
            className="p-fluid"
            footer={productDialogFooter}
            onHide={hideDialog}
          >
            <div className="field">
              <label htmlFor="name">Comunidad</label>
              <InputText id="name" readOnly value={selectedProyecto?.comunidad} autoFocus />
            </div>
            <div className="field">
              <label htmlFor="ob">Descripción</label>
              <InputTextarea id="ob" value={selectedProyecto?.descripcion} rows={3} cols={20} />
            </div>
            <div className="field">
              <label htmlFor="name">Dirección</label>
              <InputText id="name" readOnly value={selectedProyecto?.link} />
            </div>
          </Dialog>

          <Dialog
            visible={retoDialogNew}
            style={{ width: '450px' }}
            header="Comunidad"
            modal
            className="p-fluid  "
            onHide={hideDialogNuevo}
          >
            <Row className="justify-content-center">
              {loading ? (
                <Spinner></Spinner>
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
                    label="Comunidad"
                    id="comunidad-comunidad"
                    name="comunidad"
                    data-cy="comunidad"
                    type="text"
                    validate={{
                      required: { value: true, message: translate('entity.validation.required') },
                    }}
                  />
                  <ValidatedField
                    label="Descripción"
                    id="comunidad-descripcion"
                    name="descripcion"
                    data-cy="descripcion"
                    type="textarea"
                    validate={{
                      required: { value: true, message: translate('entity.validation.required') },
                    }}
                  />
                  <ValidatedField
                    label="Dirección Web"
                    id="comunidad-link"
                    name="link"
                    data-cy="link"
                    type="text"
                    validate={{
                      validate: v =>
                        (!(v.length === 0) ? regex.test(v) : true) ||
                        'La URL debe comenzar con "https:// o http://" y tener un dominio válido',
                    }}
                  />
                  <ValidatedField label="Activo" id="comunidad-activo" name="activo" data-cy="activo" check type="checkbox" />
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
                  ¿Seguro que quiere eliminar la Comunidad: <b>{selectedProyecto.comunidad}</b>?
                </span>
              )}
            </div>
          </Dialog>
        </div>
      </div>
    </div>
  );
};

export default Comunidades;
