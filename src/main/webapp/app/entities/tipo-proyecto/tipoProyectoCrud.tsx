import { useAppDispatch, useAppSelector } from 'app/config/store';
import { useEffect, useRef, useState } from 'react';
import { RouteComponentProps } from 'react-router-dom';

import { ITipoProyecto } from 'app/shared/model/tipo-proyecto.model';
import { getEntity, updateEntity, createEntity, reset, getEntities, deleteEntity } from './tipo-proyecto.reducer';
import React from 'react';

import { Button } from 'primereact/button';
import { InputText } from 'primereact/inputtext';
import { FilterMatchMode, FilterOperator } from 'primereact/api';
import { Dialog } from 'primereact/dialog';
import { translate, Translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Row } from 'reactstrap';
import Spinner from '../loader/spinner';

import { DataTable, DataTableFilterMeta } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Toolbar } from 'primereact/toolbar';

const TipoProyectoCRUD = (props: RouteComponentProps<{ id: string; index: string }>) => {
  const dispatch = useAppDispatch();
  const [isNew, setNew] = useState(true);

  const tipoProyectoList = useAppSelector(state => state.tipoProyecto.entities);

  const tipoProyectoEntity = useAppSelector(state => state.tipoProyecto.entity);
  const loading = useAppSelector(state => state.tipoProyecto.loading);
  const updating = useAppSelector(state => state.tipoProyecto.updating);
  const updateSuccess = useAppSelector(state => state.tipoProyecto.updateSuccess);

  const [retoDialog, setRetoDialog] = useState(false);
  const [retoDialogNew, setRetoDialogNew] = useState(false);
  const [globalFilter, setGlobalFilterValue] = useState('');
  const [deleteRetoDialog, setDeleteRetoDialog] = useState(false);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  const dt = useRef(null);

  const account = useAppSelector(state => state.authentication.account);

  const emptyTipoIdea = {
    id: null,
    tipoProyecto: null,
  };
  const [tipoIdea, setTipoIdea] = useState(null);
  const [selectedTipoIdea, setSelectedTipoIdea] = useState(emptyTipoIdea);

  const [filters, setFilters] = useState({
    global: { value: null, matchMode: FilterMatchMode.CONTAINS },
    tipoContacto: { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.STARTS_WITH }] },
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
      setSelectedTipoIdea(null);
    }

    if (updateSuccess && !isNew) {
      setRetoDialogNew(false);
    }
  }, [updateSuccess]);

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }
  }, []);

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
          ...tipoIdea,
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
    setTipoIdea(null);
  };

  const actualizar = retoact => {
    setTipoIdea({ ...retoact });
    setRetoDialogNew(true);
    setNew(false);
  };

  const rightToolbarTemplate = () => {
    return (
      <React.Fragment>
        <Button label="Nuevo Tipo de Proyecto" icon="pi pi-plus" className="p-button-info" onClick={verDialogNuevo} />
      </React.Fragment>
    );
  };
  const rightToolbarTemplate11 = () => {
    return (
      <React.Fragment>
        <Button label="Nuevo Tipo de Contacto" icon="pi pi-plus" className="p-button-info" onClick={verDialogNuevo} />
      </React.Fragment>
    );
  };
  const header = (
    <div className="grid">
      <div className="col">
        <div className="flex justify-content-start  font-bold  m-2">
          <div className="text-primary text-xl">Tipos de Proyectos</div>
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

    setSelectedTipoIdea(product);
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
    dispatch(deleteEntity(selectedTipoIdea.id));
    setDeleteRetoDialog(false);
  };

  const hideDeleteRetoDialog = () => {
    setDeleteRetoDialog(false);
    setSelectedTipoIdea(null);
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
            value={tipoProyectoList}
            selection={selectedTipoIdea}
            onSelectionChange={e => setSelectedTipoIdea(e.value)}
            dataKey="id"
            paginator
            rows={10}
            rowsPerPageOptions={[5, 10, 25]}
            className="datatable-responsive"
            paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
            currentPageReportTemplate="Mostrando del {first} al {last} de {totalRecords} Tipo Proyecto"
            filters={filters as DataTableFilterMeta}
            filterDisplay="menu"
            loading={loading}
            emptyMessage="No hay Tipo de Proyecto.."
            header={header}
            responsiveLayout="stack"
          >
            <Column field="id" header="Id" hidden></Column>
            <Column field="tipoProyecto" header="Tipo Proyeto" sortable headerStyle={{ minWidth: '50rem' }}></Column>

            <Column body={actionBodyTemplate} headerStyle={{ minWidth: '10rem' }}></Column>
          </DataTable>

          <Dialog
            visible={retoDialogNew}
            style={{ width: '450px' }}
            header="Tipo Proyecto"
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
                      id="tipo-noticia-id"
                      label={translate('global.field.id')}
                      validate={{ required: true }}
                    />
                  ) : null}
                  <ValidatedField
                    label={translate('jhipsterApp.tipoProyecto.tipoProyecto')}
                    id="tipo-proyecto-tipoProyecto"
                    name="tipoProyecto"
                    data-cy="tipoProyecto"
                    type="text"
                    validate={{
                      required: { value: true, message: translate('entity.validation.required') },
                    }}
                  />
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
              {selectedTipoIdea && (
                <span>
                  ¿Seguro que quiere eliminar el Tipo de Proyecto: <b>{selectedTipoIdea.tipoProyecto}</b>?
                </span>
              )}
            </div>
          </Dialog>
        </div>
      </div>
    </div>
  );
};
export default TipoProyectoCRUD;
