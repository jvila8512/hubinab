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
import { getEntity, updateEntity, createEntity, getEntities, reset, deleteEntity, getEntitiesEcosistema } from './proyectos.reducer';
import { getEntity as getEcosistema } from 'app/entities/ecosistema/ecosistema.reducer';
import { mapIdList } from 'app/shared/util/entity-utils';

const ProyectosBuscar = (props: RouteComponentProps<{ texto: string }>) => {
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
  };
  const [proyecto, setProyecto] = useState(null);
  const [selectedProyecto, setSelectedProyecto] = useState(emptyProyecto);

  const [filters, setFilters] = useState({
    global: { value: null, matchMode: FilterMatchMode.CONTAINS },
    nombre: { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.STARTS_WITH }] },
    descripcion: { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.STARTS_WITH }] },
    autor: { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.STARTS_WITH }] },
    fechaInicio: { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.DATE_IS }] },
    fechaFin: { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.DATE_IS }] },
  });

  useEffect(() => {
    dispatch(getEntities({}));

    dispatch(getSectors({}));
    dispatch(getLineaInvestigacions({}));
    dispatch(getOds({}));
    dispatch(getTipoProyectos({}));
    buscar(props.match.params.texto);
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

  const rightToolbarTemplate = () => {
    return (
      <React.Fragment>
        {account.authorities.find(rol => rol === 'ROLE_ADMIN') && (
          <Button label="Nuevo Proyecto" icon="pi pi-plus" className="p-button-info" onClick={verDialogNuevo} />
        )}
      </React.Fragment>
    );
  };
  const header = (
    <div className="flex flex-column md:flex-row md:justify-content-between md:align-items-center">
      <h3 className="m-0">Proyectos</h3>

      <span className="block mt-2 md:mt-0 p-input-icon-left">
        <i className="pi pi-search" />
        <InputText value={globalFilter} type="search" onInput={onGlobalFilterChange} placeholder="Buscar..." />
      </span>
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

  useEffect(() => {
    if (updateSuccess) {
      dispatch(getEntities({}));
      setRetoDialogNew(false);
      setSelectedProyecto(null);
      setNew(true);

      dispatch(reset());
    }
  }, [updateSuccess]);

  const buscar = e => {
    const value = e;
    const _filters = { ...filters };
    _filters['global'].value = value;

    setFilters(_filters);
    setGlobalFilterValue(value);
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
      user: account,
      ecosistema: ecosistemaEntity,
      sectors: mapIdList(values.sectors),
      lineaInvestigacions: mapIdList(values.lineaInvestigacions),
      ods: mapIdList(values.ods),
      tipoProyecto: tipoProyectos.find(it => it.id.toString() === values.tipoProyecto.toString()),
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
          user: account,
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
          <Toolbar className="mb-4" left={leftToolbarTemplate}></Toolbar>

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
            <Column field="user.id" header="User" hidden headerStyle={{ minWidth: '15rem' }}></Column>
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
            style={{ width: '450px' }}
            header="Proyecto"
            modal
            className="p-fluid"
            footer={productDialogFooter}
            onHide={hideDialog}
          >
            {selectedProyecto?.logoUrl && (
              <img
                src={`data:${selectedProyecto?.logoUrlContentType};base64,${selectedProyecto?.logoUrl}`}
                style={{ maxHeight: '200px' }}
                className="mt-0 mx-auto mb-5 block shadow-2"
              />
            )}

            <div className="field">
              <label htmlFor="name">Nombre</label>
              <InputText id="name" value={selectedProyecto?.nombre} autoFocus />
            </div>
            <div className="field">
              <label htmlFor="ob">Descripción</label>
              <InputTextarea id="ob" value={selectedProyecto?.descripcion} rows={3} cols={20} />
            </div>
          </Dialog>

          <Dialog visible={retoDialogNew} style={{ width: '450px' }} header="Proyecto" modal className="p-fluid  " onHide={hideDialogNuevo}>
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
                      id="proyectos-id"
                      label={translate('global.field.id')}
                      validate={{ required: true }}
                    />
                  ) : null}
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
                  <ValidatedBlobField
                    label={translate('jhipsterApp.proyectos.logoUrl')}
                    id="proyectos-logoUrl"
                    name="logoUrl"
                    data-cy="logoUrl"
                    isImage
                    accept="image/*"
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
                    <FontAwesomeIcon icon="save" />
                    &nbsp;
                    <Translate contentKey="entity.action.save">Save</Translate>
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

export default ProyectosBuscar;
