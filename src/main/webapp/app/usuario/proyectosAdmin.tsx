import React, { useEffect, useRef, useState } from 'react';

import { Button } from 'primereact/button';
import { Column } from 'primereact/column';
import { DataTable, DataTableFilterMeta } from 'primereact/datatable';
import { FilterMatchMode, FilterOperator, FilterService } from 'primereact/api';
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
import { getEntities as getProyectos } from 'app/entities/proyectos/proyectos.reducer';

import { ISector } from 'app/shared/model/sector.model';
import { getEntities as getSectors } from 'app/entities/sector/sector.reducer';
import { ILineaInvestigacion } from 'app/shared/model/linea-investigacion.model';
import { getEntities as getLineaInvestigacions } from 'app/entities/linea-investigacion/linea-investigacion.reducer';
import { IOds } from 'app/shared/model/ods.model';
import { getEntities as getOds } from 'app/entities/ods/ods.reducer';
import { ITipoProyecto } from 'app/shared/model/tipo-proyecto.model';
import { getEntities as getTipoProyectos } from 'app/entities/tipo-proyecto/tipo-proyecto.reducer';
import { IProyectos } from 'app/shared/model/proyectos.model';
import {
  getEntity,
  updateEntity,
  getEntities,
  createEntity,
  reset,
  deleteEntity,
  getEntitiesEcosistema,
  getEntitiesTodos,
} from 'app/entities/proyectos/proyectos.reducer';
import { getEntity as getEcosistema } from 'app/entities/ecosistema/ecosistema.reducer';
import { mapIdList } from 'app/shared/util/entity-utils';

import { IEcosistema } from 'app/shared/model/ecosistema.model';

import { getEntities as getEcosistemas } from 'app/entities/ecosistema/ecosistema.reducer';
import { Chip } from 'primereact/chip';
import { Dropdown } from 'primereact/dropdown';

const ProyectosAdmin = props => {
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

  const ecosistemas = useAppSelector(state => state.ecosistema.entities);

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
    entidadGestora: '',
    entidad_Ejecutora: '',
    entidadParticipantes: '',
  };
  const [proyecto, setProyecto] = useState(null);
  const [selectedProyecto, setSelectedProyecto] = useState(emptyProyecto);

  const [filters, setFilters] = useState({
    global: { value: null, matchMode: FilterMatchMode.CONTAINS },
    nombre: { value: null, matchMode: FilterMatchMode.CONTAINS },
    descripcion: { value: null, matchMode: FilterMatchMode.CONTAINS },
    'ecosistema.nombre': { value: null, matchMode: FilterMatchMode.STARTS_WITH },
    fechaInicio: { value: null, matchMode: FilterMatchMode.STARTS_WITH },
    fechaFin: { value: null, matchMode: FilterMatchMode.STARTS_WITH },
  });
  const [listProyectoFiltrada, setListProyectoFiltrada] = useState([]);

  useEffect(() => {
    dispatch(getEntitiesTodos());
    dispatch(getSectors({}));
    dispatch(getLineaInvestigacions({}));
    dispatch(getOds({}));
    dispatch(getTipoProyectos({}));
    dispatch(getEcosistemas({}));
  }, []);

  useEffect(() => {
    if (props.filtrarPor?.code && props.valueBuscar?.id) {
      filtrarProyectos(props.valueBuscar, props.filtrarPor);
    } else setListProyectoFiltrada(proyectosList);

    collapseAll();
  }, [props.filtrarPor, props.valueBuscar]);

  useEffect(() => {
    if (props.filtrarPor?.code && props.valueBuscar?.id) {
      filtrarProyectos(props.valueBuscar, props.filtrarPor);
    } else if (props.texto !== 'pr' && props.texto !== 'sc' && props.texto !== 'ods') {
      setListProyectoFiltrada(proyectosList);
      const value = props.texto;
      const _filters = { ...filters };
      _filters['global'].value = value;
      setFilters(_filters);
      setGlobalFilterValue(value);
    } else setListProyectoFiltrada(proyectosList);
    collapseAll();
  }, [proyectosList]);

  const filtrarbuscarODS = value => {
    const valueFiltrado = proyectosList.filter(objeto => objeto.ods.some(elemento => elemento.id === value.id));

    setListProyectoFiltrada(valueFiltrado);
  };
  const filtrarbuscarSector = value => {
    if (value) {
      const valueFiltrado = proyectosList.filter(objeto => objeto.sectors.some(elemento => elemento.id === value.id));

      if (valueFiltrado.length === 0) {
        setListProyectoFiltrada([]);
      } else setListProyectoFiltrada(valueFiltrado);
      setListProyectoFiltrada(valueFiltrado);
    } else setListProyectoFiltrada(proyectosList);
  };
  const filtrarbuscarLinea = value => {
    if (value) {
      const valueFiltrado = proyectosList.filter(objeto => objeto.lineaInvestigacions.some(elemento => elemento.id === value.id));

      if (valueFiltrado.length === 0) {
        setListProyectoFiltrada([]);
      } else setListProyectoFiltrada(valueFiltrado);
    } else {
      setListProyectoFiltrada(proyectosList);
    }
  };

  const filtrarPorAlguno = (e, code) => {
    if (e) {
      if (code === 'ods') {
        filtrarbuscarODS(e);
      }
      if (code === 'sc') {
        filtrarbuscarSector(e);
      }
      if (code === 'pri') {
        filtrarbuscarLinea(e);
      }
    } else {
      setListProyectoFiltrada(proyectosList);
    }
  };

  const filtrarProyectos = (e, code) => {
    code?.code === 'ods'
      ? filtrarbuscarODS(e)
      : code?.code === 'sc'
      ? filtrarbuscarSector(e)
      : code?.code === 'pri'
      ? filtrarbuscarLinea(e)
      : {};
  };

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

  const header = (
    <div className="flex justify-content-between align-items-center">
      <div className="flex justify-content-start ">
        <div className="text-900 text-2xl text-blue-600 font-medium ">Proyectos</div>
      </div>
      <div className="flex justify-content-end ">
        <div className="text-900 text-2xl text-blue-300 font-medium mr-1 pt-1">Palabra Clave</div>
        <span className="block mt-2 md:mt-0 p-input-icon-left">
          <i className="pi pi-search" />
          <InputText value={globalFilter} type="search" onInput={onGlobalFilterChange} placeholder="Buscar por palabra clave" />
        </span>
      </div>
    </div>
  );

  const retoBodyTemplate = rowData => {
    return (
      <>
        <span className="pl-5"> {rowData.nombre}</span>
      </>
    );
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
      <div className="align-items-center justify-content-center">
        <Button icon="pi pi-eye" className="p-button-rounded p-button-info ml-2 mb-1" onClick={() => actualizar(rowData)} />
      </div>
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
  const collapseAll = () => {
    setExpandedRows(null);
  };

  const saveEntity = values => {
    const entity = {
      ...values,
      user: account,
      ecosistema: ecosistemas.find(it => it.id.toString() === values.ecosistema.toString()),
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
          ecosistema: proyecto?.ecosistema?.id,
          sectors: proyecto?.sectors?.map(e => e.id.toString()),
          lineaInvestigacions: proyecto?.lineaInvestigacions?.map(e => e.id.toString()),
          ods: proyecto?.ods?.map(e => e.id.toString()),
          tipoProyecto: proyecto?.tipoProyecto?.id,
        };

  const allowExpansion = rowData => {
    return true;
  };
  const [expandedRows, setExpandedRows] = useState(null);
  const rowExpansionTemplate = data => {
    return (
      <div className="flex flex-row">
        <div className="formgrid grid">
          <div className="field col-12 md:col-2">
            {data?.logoUrlContentType && (
              <img
                src={`content/uploads/${data.logoUrlContentType}`}
                style={{ maxHeight: '300px' }}
                className="mt-0 mx-auto mb-5 block shadow-2 w-full"
              />
            )}
          </div>

          <div className="field col-12 md:col-10">
            <div className="grid ">
              <div className="field col-12 md:col-3">
                <label htmlFor="state">Autor</label>
                <input
                  id="city"
                  type="text"
                  defaultValue={data.autor}
                  className="text-base text-color readOnly font-bold  surface-overlay p-2 border-1 border-solid surface-border border-round appearance-none outline-none focus:border-primary w-full"
                ></input>
              </div>
              <div className="field col-12 md:col-3">
                <label htmlFor="state">Código</label>
                <input
                  id="city"
                  type="text"
                  defaultValue={data.necesidad}
                  className="text-base text-color readOnly font-bold surface-overlay p-2 border-1 border-solid surface-border border-round appearance-none outline-none focus:border-primary w-full"
                ></input>
              </div>

              <div className="field col-12 md:col-3">
                <label htmlFor="state">Entidad Gestora</label>
                <input
                  id="city"
                  type="text"
                  defaultValue={data.entidadGestora}
                  className="text-base text-color readOnly font-bold  surface-overlay p-2 border-1 border-solid surface-border border-round appearance-none outline-none focus:border-primary w-full"
                ></input>
              </div>
              <div className="field col-12 md:col-3">
                <label htmlFor="state">Entidad Ejecutora</label>
                <input
                  id="city"
                  type="text"
                  defaultValue={data.entidad_Ejecutora}
                  className="text-base text-color readOnly font-bold surface-overlay p-2 border-1 border-solid surface-border border-round appearance-none outline-none focus:border-primary w-full"
                ></input>
              </div>

              <div className="field col-12 md:col-6">
                <label htmlFor="firstname6">Entidades Participantes</label>
                <textarea
                  id="address"
                  rows={2}
                  defaultValue={data.entidadParticipantes}
                  className="text-base text-color surface-overlay p-2 readOnly font-bold border-1 border-solid surface-border border-round appearance-none outline-none focus:border-primary w-full"
                ></textarea>
              </div>

              <div className="field col-12 md:col-4">
                <label htmlFor="state">Tipo de Proyecto</label>
                <input
                  id="city"
                  type="text"
                  defaultValue={data.tipoProyecto.tipoProyecto}
                  className="text-base text-color readOnly font-bold surface-overlay p-2 border-1 border-solid surface-border border-round appearance-none outline-none focus:border-primary w-full"
                ></input>
              </div>
            </div>
          </div>

          <div className="field col-12 md:col-6">
            <label htmlFor="lastname6">Prioridad</label>
            <div className="flex flex-column ">
              {data.lineaInvestigacions
                ? data?.lineaInvestigacions.map((val, j) => <Chip key={j} label={val.linea} icon="pi pi-verified" className="mr-3 mb-2" />)
                : null}
            </div>
          </div>
          <div className="field col-12 md:col-6">
            <label htmlFor="lastname6">Sector</label>
            <div className="flex flex-column ">
              {data.sectors
                ? data?.sectors.map((val, j) => <Chip key={j} label={val.sector} icon="pi pi-verified" className="mr-3 mb-2" />)
                : null}
            </div>
          </div>

          <div className="field col-12">
            <label htmlFor="address">ODS</label>
            <div className="flex flex-column ">
              {data.ods ? data?.ods.map((val, j) => <Chip key={j} label={val.ods} icon="pi pi-verified" className="mr-3 mb-2" />) : null}
            </div>
          </div>
        </div>
      </div>
    );
  };
  const representativesItemTemplate = option => {
    return (
      <div className="p-multiselect-representative-option">
        <span className="image-text">{option.nombre}</span>
      </div>
    );
  };
  const [selectedValue1, setSelectedValue] = useState(null);
  const representativeRowFilterTemplate1 = options => {
    return (
      <Dropdown
        value={selectedValue1}
        options={ecosistemas}
        itemTemplate={representativesItemTemplate}
        onChange={e => {
          setSelectedValue(e.value);

          options.filterApplyCallback(e.value.nombre);
          collapseAll();
        }}
        optionLabel="nombre"
        placeholder="Buscar.."
        className="p-column-filter"
      />
    );
  };
  // Definimos la función de filtrado personalizada
  const odsFilter = (value, filter) => {
    if (!filter) {
      return true; // Si no hay filtro, mostramos todos
    }

    // Comprobamos si el valor es un array y si contiene el filtro
    if (value.length === 0 && filter) return false;
    else {
      const si = value.map(item => item.ods);
      const elementos = value.filter(item => item.ods.includes(filter));

      if (elementos.length !== 0) {
        return true;
      } else return false;
    }
    return true;
  };

  FilterService.register('ods', odsFilter);
  const matchModes = [{ label: 'ODS', value: 'ods' }];
  const odsRowFilterTemplate = options => {
    return (
      <InputText
        value={globalFilter}
        onChange={e => {
          setGlobalFilterValue(e.target.value);
          options.filterApplyCallback(e.target.value);
        }}
        placeholder="Buscar.."
        className="p-column-filter"
      />
    );
  };
  // Función para renderizar el ícono del expander
  const expanderTemplate = rowData => {
    return (
      <Button
        icon={expandedRows && expandedRows[rowData.id] ? 'pi pi-eye-slash' : 'pi pi-eye'}
        onClick={() => {
          setExpandedRows(prevExpandedRows => {
            // Si la fila ya está expandida, la eliminamos
            if (prevExpandedRows && prevExpandedRows[rowData.id]) {
              const { [rowData.id]: _, ...rest } = prevExpandedRows; // Eliminar la fila expandida
              return rest; // Retornar el resto del objeto
            } else {
              // Si no está expandida, la agregamos
              return { ...prevExpandedRows, [rowData.id]: true }; // Agregar la fila expandida
            }
          });
        }}
        className={
          expandedRows && expandedRows[rowData.id] ? 'p-button-rounded p-button-secondary mr-2' : 'p-button-rounded p-button-info mr-2'
        }
      />
    );
  };

  const [estadofecha, setEstadoFecha] = useState('');
  const [estadofechafin, setEstadoFechaFin] = useState('');
  const [estadoNombre, setEstadoNombre] = useState('');
  const [estadoDescripcion, setEstadoDescripcion] = useState('');

  const fechaRowFilterTemplate = options => {
    return (
      <InputText
        value={estadofecha}
        type="search"
        onChange={e => {
          setEstadoFecha(e.target.value);
          collapseAll();
          options.filterApplyCallback(e.target.value);
        }}
        placeholder="Año-mes-día"
      />
    );
  };
  const fechaFinFilterTemplate = options => {
    return (
      <InputText
        value={estadofechafin}
        type="search"
        onChange={e => {
          setEstadoFechaFin(e.target.value);
          collapseAll();
          options.filterApplyCallback(e.target.value);
        }}
        placeholder="Año-mes-día"
      />
    );
  };
  const nombreFilterTemplate = options => {
    return (
      <InputText
        value={estadoNombre}
        type="search"
        onChange={e => {
          setEstadoNombre(e.target.value);
          collapseAll();
          options.filterApplyCallback(e.target.value);
        }}
        placeholder="Buscar.."
      />
    );
  };
  const descripcionFilterTemplate = options => {
    return (
      <InputText
        value={estadoDescripcion}
        type="search"
        onChange={e => {
          setEstadoDescripcion(e.target.value);
          collapseAll();
          options.filterApplyCallback(e.target.value);
        }}
        placeholder="Buscar.."
      />
    );
  };

  return (
    <div className="grid crud-demo mt-3 mb-4">
      <div className="col-12">
        <div className="card">
          <DataTable
            ref={dt}
            value={listProyectoFiltrada}
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
            filterDisplay="row"
            loading={loading}
            emptyMessage="En su búsqueda no se encontraron Proyectos en el sistema."
            header={header}
            expandedRows={expandedRows}
            onRowToggle={e => setExpandedRows(e.data)}
            rowExpansionTemplate={rowExpansionTemplate}
            responsiveLayout="stack"
          >
            <Column field="id" header="Id" hidden headerStyle={{ minWidth: '15rem' }}></Column>
            <Column field="user.id" header="User" hidden headerStyle={{ minWidth: '15rem' }}></Column>
            <Column
              field="ecosistema.nombre"
              sortable
              header="Ecosistema"
              headerStyle={{ minWidth: '15rem' }}
              filterField="ecosistema.nombre"
              filterPlaceholder="Buscar.."
              showFilterMenu={false}
              filterMenuStyle={{ width: '13rem' }}
              filter
              filterElement={representativeRowFilterTemplate1}
            ></Column>
            <Column
              field="nombre"
              header="Nombre"
              style={{ alignContent: 'right' }}
              sortable
              body={retoBodyTemplate}
              headerStyle={{ minWidth: '15rem' }}
              filter
              filterPlaceholder="Buscar.."
              showFilterMenu={false}
              filterMenuStyle={{ width: '10rem' }}
              filterElement={nombreFilterTemplate}
            ></Column>
            <Column
              field="descripcion"
              header="Descripción"
              style={{ width: '30%', alignContent: 'right' }}
              sortable
              body={nameBodyTemplate}
              headerStyle={{ minWidth: '15rem' }}
              filter
              filterPlaceholder="Buscar.."
              showFilterMenu={false}
              filterMenuStyle={{ width: '10rem' }}
              filterElement={descripcionFilterTemplate}
            ></Column>
            <Column
              field="fechaInicio"
              header="Fecha.Inicio "
              sortable
              dataType="date"
              body={fechaInicioBodyTemplate}
              filter
              filterPlaceholder="Año-mes-día"
              showFilterMenu={false}
              filterMenuStyle={{ width: '12rem' }}
              headerStyle={{ minWidth: '12rem' }}
              filterElement={fechaRowFilterTemplate}
            ></Column>
            <Column
              field="fechaFin"
              header="Fecha.Fin"
              sortable
              dataType="date"
              body={fechaFinBodyTemplate}
              filter
              filterPlaceholder="Año-mes-día"
              showFilterMenu={false}
              filterMenuStyle={{ width: '12rem' }}
              headerStyle={{ minWidth: '12rem' }}
              filterElement={fechaFinFilterTemplate}
            ></Column>

            <Column field="sector.sector" hidden header="Sector" sortable headerStyle={{ minWidth: '15rem' }}></Column>
            <Column field="lineaInvestigacions.linea" hidden header="Prioridad" sortable headerStyle={{ minWidth: '15rem' }}></Column>
            <Column
              field="ods.ods"
              header="ODS"
              sortable
              filter
              headerStyle={{ minWidth: '15rem' }}
              // filterMatchMode="custom"
              filterFunction={odsFilter}
              showFilterMenu={true}
              filterMatchModeOptions={matchModes}
              filterElement={odsRowFilterTemplate}
              filterApply
              hidden
            ></Column>
            <Column field="tipoProyecto" header="Nombre" hidden sortable headerStyle={{ minWidth: '15rem' }}></Column>
            <Column field="autor" header="Nombre" hidden sortable headerStyle={{ minWidth: '15rem' }}></Column>
            <Column field="necesidad" header="Nombre" hidden sortable headerStyle={{ minWidth: '15rem' }}></Column>

            <Column expander={allowExpansion} body={expanderTemplate} style={{ width: '1em' }} />
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
              <h4 className="text-900 text-sm text-blue-600 font-medium">Lineas de Investigación</h4>
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
                  {proyecto?.logoUrlContentType && (
                    <img
                      src={`content/uploads/${proyecto.logoUrlContentType}`}
                      style={{ maxHeight: '300px' }}
                      className="mt-0 mx-auto mb-5 block shadow-2 w-full"
                    />
                  )}
                  <ValidatedField
                    id="proyectos-ecosistema"
                    name="ecosistema"
                    data-cy="ecosistema"
                    readOnly
                    disabled
                    label={translate('jhipsterApp.proyectos.ecosistema')}
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
                  <ValidatedField
                    label={translate('jhipsterApp.proyectos.nombre')}
                    id="proyectos-nombre"
                    name="nombre"
                    data-cy="nombre"
                    readOnly
                    type="text"
                    validate={{
                      required: { value: true, message: translate('entity.validation.required') },
                    }}
                  />
                  <ValidatedField
                    label={translate('jhipsterApp.proyectos.descripcion')}
                    id="proyectos-descripcion"
                    name="descripcion"
                    readOnly
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
                    readOnly
                    type="text"
                    validate={{
                      required: { value: true, message: translate('entity.validation.required') },
                    }}
                  />
                  <ValidatedField
                    label={translate('jhipsterApp.proyectos.necesidad')}
                    id="proyectos-necesidad"
                    name="necesidad"
                    readOnly
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
                    readOnly
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
                    readOnly
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
                  <h4 className="text-900 text-sm text-blue-600 font-medium">Lineas de Ivestigación</h4>
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

export default ProyectosAdmin;
