import { useAppDispatch, useAppSelector } from 'app/config/store';
import { useEffect, useRef, useState } from 'react';
import { RouteComponentProps } from 'react-router-dom';
import { getEntities } from './anirista.reducer';
import { IEcosistema } from 'app/shared/model/ecosistema.model';
import { getEntities as getEcosistemas } from 'app/entities/ecosistema/ecosistema.reducer';
import { IAnirista } from 'app/shared/model/anirista.model';
import { getEntity, updateEntity, createEntity, reset, deleteEntity } from './anirista.reducer';
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

const AniristasCRUD = (props: RouteComponentProps<{ id: string; index: string }>) => {
  const dispatch = useAppDispatch();
  const [isNew, setNew] = useState(true);
  const aniristaList = useAppSelector(state => state.anirista.entities);
  const ecosistemas = useAppSelector(state => state.ecosistema.entities);
  const aniristaEntity = useAppSelector(state => state.anirista.entity);
  const loading = useAppSelector(state => state.anirista.loading);
  const updating = useAppSelector(state => state.anirista.updating);
  const [retoDialog, setRetoDialog] = useState(false);
  const [retoDialogNew, setRetoDialogNew] = useState(false);
  const [globalFilter, setGlobalFilterValue] = useState('');
  const [deleteRetoDialog, setDeleteRetoDialog] = useState(false);

  const updateSuccess = useAppSelector(state => state.anirista.updateSuccess);
  useEffect(() => {
    dispatch(getEntities({}));
    dispatch(getEcosistemas({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  const dt = useRef(null);

  const account = useAppSelector(state => state.authentication.account);

  const emptyAnirista = {
    id: null,
    nombre: '',
    fechaEntrada: '',
    descripcion: '',
    ecosistema: null,
  };
  const [anirista, setAnirista] = useState(null);
  const [selectedAnirista, setSelectedAnirista] = useState(emptyAnirista);

  const [filters, setFilters] = useState({
    global: { value: null, matchMode: FilterMatchMode.CONTAINS },
    nombre: { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.STARTS_WITH }] },
    ecosistema: { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.STARTS_WITH }] },
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
      setSelectedAnirista(null);
    }

    if (updateSuccess && !isNew) {
      setRetoDialogNew(false);
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...aniristaEntity,
      ...values,
      ecosistema: ecosistemas.find(it => it.id.toString() === values.ecosistema.toString()),
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
          ...anirista,
          ecosistema: anirista?.ecosistema?.id,
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
    setAnirista({ ...retoact });
    setRetoDialogNew(true);
    setNew(false);
  };

  const rightToolbarTemplate = () => {
    return (
      <React.Fragment>
        {(account?.authorities?.find(rol => rol === 'ROLE_ADMINECOSISTEMA') || account?.authorities?.find(rol => rol === 'ROLE_ADMIN')) && (
          <Button label="Nuevo Anirista" icon="pi pi-plus" className="p-button-info" onClick={verDialogNuevo} />
        )}
      </React.Fragment>
    );
  };
  const header = (
    <div className="grid">
      <div className="col">
        <div className="flex justify-content-start  font-bold  m-2">
          <div className="text-primary text-xl">Aniristas</div>
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

    setSelectedAnirista(product);
  };
  const hideDialog = () => {
    setRetoDialog(false);
    setNew(true);
  };
  const actionBodyTemplate = rowData => {
    return (
      <>
        <Button icon="pi pi-trash" className="p-button-rounded p-button-danger ml-2 mb-1" onClick={() => verAnirista(rowData)} />

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
    dispatch(deleteEntity(selectedAnirista.id));
    setDeleteRetoDialog(false);
  };

  const hideDeleteRetoDialog = () => {
    setDeleteRetoDialog(false);
    setSelectedAnirista(null);
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
            value={aniristaList}
            selection={selectedAnirista}
            onSelectionChange={e => setSelectedAnirista(e.value)}
            dataKey="id"
            paginator
            rows={10}
            rowsPerPageOptions={[5, 10, 25]}
            className="datatable-responsive"
            paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
            currentPageReportTemplate="Mostrando del {first} al {last} de {totalRecords} Aniristas"
            filters={filters as DataTableFilterMeta}
            filterDisplay="menu"
            loading={loading}
            emptyMessage="No hay Aniristas..."
            header={header}
            responsiveLayout="stack"
          >
            <Column field="id" header="Id" hidden headerStyle={{ minWidth: '15rem' }}></Column>
            <Column field="nombre" header="Nombre" sortable headerStyle={{ minWidth: '15rem' }}></Column>

            <Column field="fechaEntrada" header="Fecha Entrada" sortable headerStyle={{ minWidth: '15rem' }}></Column>
            <Column field="descripcion" header="Descripción" sortable headerStyle={{ minWidth: '15rem' }}></Column>

            <Column field="ecosistema.nombre" header="Ecosistema" sortable headerStyle={{ minWidth: '5rem' }}></Column>

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
              <InputText id="name" readOnly value={selectedAnirista?.nombre} autoFocus />
            </div>
            <div className="field">
              <label htmlFor="ob">Fecha Entrada</label>
              <InputText id="ob" value={selectedAnirista?.fechaEntrada} />
            </div>
            <div className="field">
              <label htmlFor="name">Ecositema</label>
              <InputText id="name" readOnly value={selectedAnirista?.ecosistema?.nombre} />
            </div>
          </Dialog>

          <Dialog visible={retoDialogNew} style={{ width: '450px' }} header="Anirista" modal className="p-fluid  " onHide={hideDialogNuevo}>
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
                    label={translate('jhipsterApp.anirista.nombre')}
                    id="anirista-nombre"
                    name="nombre"
                    data-cy="nombre"
                    type="text"
                    validate={{
                      required: { value: true, message: translate('entity.validation.required') },
                    }}
                  />
                  <ValidatedField
                    label={translate('jhipsterApp.anirista.fechaEntrada')}
                    id="anirista-fechaEntrada"
                    name="fechaEntrada"
                    data-cy="fechaEntrada"
                    type="date"
                    validate={{
                      required: { value: true, message: translate('entity.validation.required') },
                    }}
                  />
                  <ValidatedField
                    label={translate('jhipsterApp.anirista.descripcion')}
                    id="anirista-descripcion"
                    name="descripcion"
                    data-cy="descripcion"
                    type="textarea"
                    validate={{
                      required: { value: true, message: translate('entity.validation.required') },
                    }}
                  />
                  <ValidatedField
                    id="anirista-ecosistema"
                    name="ecosistema"
                    data-cy="ecosistema"
                    label={translate('jhipsterApp.anirista.ecosistema')}
                    type="select"
                  >
                    <option value="" key="0" />
                    {ecosistemas
                      ? ecosistemas.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.nombre}
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
              {selectedAnirista && (
                <span>
                  ¿Seguro que quiere eliminar el Anirista: <b>{selectedAnirista.nombre}</b>?
                </span>
              )}
            </div>
          </Dialog>
        </div>
      </div>
    </div>
  );
};
export default AniristasCRUD;
