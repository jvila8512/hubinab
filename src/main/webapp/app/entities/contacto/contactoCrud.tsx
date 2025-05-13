import { useAppDispatch, useAppSelector } from 'app/config/store';
import { useEffect, useRef, useState } from 'react';
import { RouteComponentProps } from 'react-router-dom';
import { getEntities } from './contacto.reducer';
import { IEcosistema } from 'app/shared/model/ecosistema.model';
import { getEntities as getEcosistemas } from 'app/entities/ecosistema/ecosistema.reducer';
import { IAnirista } from 'app/shared/model/anirista.model';
import { getEntity, updateEntity, createEntity, reset, deleteEntity } from './contacto.reducer';
import React from 'react';
import { Button } from 'primereact/button';
import { InputText } from 'primereact/inputtext';
import { FilterMatchMode, FilterOperator } from 'primereact/api';
import { Dialog } from 'primereact/dialog';
import { translate, Translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Row } from 'reactstrap';
import Spinner from '../loader/spinner';
import { InputTextarea } from 'primereact/inputtextarea';
import { DataTable, DataTableFilterMeta } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Toolbar } from 'primereact/toolbar';
import { ITipoContacto } from 'app/shared/model/tipo-contacto.model';
import { getEntities as getTipoContactos } from 'app/entities/tipo-contacto/tipo-contacto.reducer';

const ContactoCRUD = (props: RouteComponentProps<{ id: string; index: string }>) => {
  const dispatch = useAppDispatch();
  const [isNew, setNew] = useState(true);
  const contactoList = useAppSelector(state => state.contacto.entities);
  const ecosistemas = useAppSelector(state => state.ecosistema.entities);
  const contactoEntity = useAppSelector(state => state.contacto.entity);
  const loading = useAppSelector(state => state.contacto.loading);
  const updating = useAppSelector(state => state.contacto.updating);
  const updateSuccess = useAppSelector(state => state.contacto.updateSuccess);
  const [retoDialog, setRetoDialog] = useState(false);
  const [retoDialogNew, setRetoDialogNew] = useState(false);
  const [globalFilter, setGlobalFilterValue] = useState('');
  const [deleteRetoDialog, setDeleteRetoDialog] = useState(false);
  const tipoContactos = useAppSelector(state => state.tipoContacto.entities);

  useEffect(() => {
    dispatch(getEntities({}));

    dispatch(getTipoContactos({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  const dt = useRef(null);

  const account = useAppSelector(state => state.authentication.account);

  const emptyContacto = {
    id: null,
    nombre: '',
    telefono: '',
    correo: '',
    mensaje: '',
    fechaContacto: '',
    tipoContacto: null,
  };
  const [contacto, setContacto] = useState(null);
  const [selectedContacto, setSelectedContacto] = useState(emptyContacto);

  const [filters, setFilters] = useState({
    global: { value: null, matchMode: FilterMatchMode.CONTAINS },
    nombre: { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.STARTS_WITH }] },
    telefono: { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.STARTS_WITH }] },
    mensaje: { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.STARTS_WITH }] },
    correo: { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.STARTS_WITH }] },
  });
  const onGlobalFilterChange = e => {
    const value = e.target.value;
    const _filters = { ...filters };
    _filters['global'].value = value;

    setFilters(_filters);
    setGlobalFilterValue(value);
  };

  useEffect(() => {
    if (updateSuccess && isNew) {
      setRetoDialogNew(false);
      setSelectedContacto(null);
    }

    if (updateSuccess && !isNew) {
      setRetoDialogNew(false);
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...contactoEntity,
      ...values,
      tipoContacto: tipoContactos.find(it => it.id.toString() === values.tipoContacto.toString()),
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
          ...contacto,
          tipoContacto: contacto?.tipoContacto?.id,
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
  const confirmDeleteSelected = () => {
    setDeleteRetoDialog(true);
  };

  const verDialogNuevo = () => {
    setRetoDialogNew(true);
    setNew(true);
  };

  const actualizar = retoact => {
    setContacto({ ...retoact });
    setRetoDialogNew(true);
    setNew(false);
  };

  const rightToolbarTemplate = () => {
    return <React.Fragment></React.Fragment>;
  };
  const rightToolbarTemplate11 = () => {
    return (
      <React.Fragment>
        <Button label="Nuevo Contacto" icon="pi pi-plus" className="p-button-info" onClick={verDialogNuevo} />
      </React.Fragment>
    );
  };
  const header = (
    <div className="grid">
      <div className="col">
        <div className="flex justify-content-start  font-bold  m-2">
          <div className="text-primary text-xl">Contactos</div>
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
  const verAnirista = product => {
    setDeleteRetoDialog(true);

    setSelectedContacto(product);
  };
  const hideDialog = () => {
    setRetoDialog(false);
    setNew(true);
  };
  const actionBodyTemplate = rowData => {
    return (
      <>
        <Button icon="pi pi-pencil" className="p-button-rounded p-button-warning ml-2 mb-1" onClick={() => actualizar(rowData)} />
      </>
    );
  };
  const productDialogFooter = (
    <>
      <Button label="Cerrar" icon="pi pi-times" className="p-button-text" onClick={hideDialog} />
    </>
  );
  const hideDialogNuevo = () => {
    setRetoDialogNew(false);
  };

  const deleteReto = () => {
    dispatch(deleteEntity(selectedContacto.id));
    setDeleteRetoDialog(false);
  };

  const hideDeleteRetoDialog = () => {
    setDeleteRetoDialog(false);
    setSelectedContacto(null);
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

  return (
    <div className="grid crud-demo mt-3 mb-4">
      <div className="col-12">
        <div className="card">
          <Toolbar className="mb-4" left={leftToolbarTemplate} right={rightToolbarTemplate}></Toolbar>

          <DataTable
            ref={dt}
            value={contactoList}
            selection={selectedContacto}
            onSelectionChange={e => setSelectedContacto(e.value)}
            dataKey="id"
            paginator
            rows={10}
            rowsPerPageOptions={[5, 10, 25]}
            className="datatable-responsive"
            paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
            currentPageReportTemplate="Mostrando del {first} al {last} de {totalRecords} Contactos"
            filters={filters as DataTableFilterMeta}
            filterDisplay="menu"
            loading={loading}
            emptyMessage="No hay Contactos..."
            header={header}
            responsiveLayout="stack"
          >
            <Column field="id" header="Id" hidden headerStyle={{ minWidth: '15rem' }}></Column>
            <Column field="nombre" header="Nombre" sortable headerStyle={{ minWidth: '15rem' }}></Column>

            <Column field="fechaContacto" header="Fecha Contacto" sortable headerStyle={{ minWidth: '15rem' }}></Column>
            <Column field="mensaje" header="Mensaje" sortable headerStyle={{ minWidth: '15rem' }}></Column>

            <Column field="correo" header="Correo" sortable headerStyle={{ minWidth: '5rem' }}></Column>

            <Column body={actionBodyTemplate} headerStyle={{ minWidth: '10rem' }}></Column>
          </DataTable>

          <Dialog
            visible={retoDialog}
            style={{ width: '500px' }}
            header="Anirista"
            modal
            className="p-fluid"
            footer={productDialogFooter}
            onHide={hideDialog}
          >
            <div className="field">
              <label htmlFor="name">Nombre</label>
              <InputText id="name" readOnly value={selectedContacto?.nombre} autoFocus />
            </div>
            <div className="field">
              <label htmlFor="ob">Fecha Contacto</label>
              <InputText id="ob" value={selectedContacto?.fechaContacto} />
            </div>
            <div className="field">
              <label htmlFor="name">Mensaje</label>
              <InputText id="name" readOnly value={selectedContacto?.mensaje} />
            </div>
          </Dialog>

          <Dialog visible={retoDialogNew} style={{ width: '450px' }} header="Contacto" modal className="p-fluid  " onHide={hideDialogNuevo}>
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
                    label={translate('jhipsterApp.contacto.nombre')}
                    id="contacto-nombre"
                    name="nombre"
                    data-cy="nombre"
                    type="text"
                    validate={{
                      required: { value: true, message: translate('entity.validation.required') },
                    }}
                  />
                  <ValidatedField
                    label={translate('jhipsterApp.contacto.telefono')}
                    id="contacto-telefono"
                    name="telefono"
                    data-cy="telefono"
                    type="text"
                    validate={{
                      required: { value: true, message: translate('entity.validation.required') },
                    }}
                  />
                  <ValidatedField
                    label={translate('jhipsterApp.contacto.correo')}
                    id="contacto-correo"
                    name="correo"
                    data-cy="correo"
                    type="text"
                    validate={{
                      required: { value: true, message: translate('entity.validation.required') },
                    }}
                  />
                  <ValidatedField
                    label={translate('jhipsterApp.contacto.mensaje')}
                    id="contacto-mensaje"
                    name="mensaje"
                    data-cy="mensaje"
                    type="text"
                    validate={{
                      required: { value: true, message: translate('entity.validation.required') },
                    }}
                  />
                  <ValidatedField
                    label={translate('jhipsterApp.contacto.fechaContacto')}
                    id="contacto-fechaContacto"
                    name="fechaContacto"
                    data-cy="fechaContacto"
                    type="date"
                  />
                  <ValidatedField
                    id="contacto-tipoContacto"
                    name="tipoContacto"
                    data-cy="tipoContacto"
                    label={translate('jhipsterApp.contacto.tipoContacto')}
                    type="select"
                  >
                    <option value="" key="0" />
                    {tipoContactos
                      ? tipoContactos.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.tipoContacto}
                          </option>
                        ))
                      : null}
                  </ValidatedField>
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
              {selectedContacto && (
                <span>
                  ¿Seguro que quiere eliminar el Anirista: <b>{selectedContacto.nombre}</b>?
                </span>
              )}
            </div>
          </Dialog>
        </div>
      </div>
    </div>
  );
};
export default ContactoCRUD;
