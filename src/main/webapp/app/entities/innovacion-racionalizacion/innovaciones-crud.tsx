import React, { useState, useEffect } from 'react';
import { FilterMatchMode, FilterOperator } from 'primereact/api';
import { DataTable, DataTableFilterMeta } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { InputText } from 'primereact/inputtext';
import { InputTextarea } from 'primereact/inputtextarea';
import { Dropdown } from 'primereact/dropdown';
import { InputNumber } from 'primereact/inputnumber';
import { Button } from 'primereact/button';
import { ProgressBar } from 'primereact/progressbar';
import { Calendar } from 'primereact/calendar';
import { MultiSelect } from 'primereact/multiselect';
import { Slider } from 'primereact/slider';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { mapIdList, overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import {
  getTodasInnovacionesByPublicasAndUserId,
  getEntity,
  updateEntity,
  createEntity,
  reset,
  deleteEntity,
} from './innovacion-racionalizacion.reducer';

import { Dialog } from 'primereact/dialog';

import { RouteComponentProps } from 'react-router-dom';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { Translate, ValidatedBlobField, ValidatedField, ValidatedForm, getSortState, isNumber, translate } from 'react-jhipster';
import { Row } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { ITipoIdea } from 'app/shared/model/tipo-idea.model';
import { getEntities as getTipoIdeas } from 'app/entities/tipo-idea/tipo-idea.reducer';

import { IInnovacionRacionalizacion } from 'app/shared/model/innovacion-racionalizacion.model';
import { Toolbar } from 'primereact/toolbar';
import { Tag } from 'primereact/tag';

import { ISector } from 'app/shared/model/sector.model';
import { getEntities as getSectors } from 'app/entities/sector/sector.reducer';
import { ILineaInvestigacion } from 'app/shared/model/linea-investigacion.model';
import { getEntities as getLineaInvestigacions } from 'app/entities/linea-investigacion/linea-investigacion.reducer';
import { IOds } from 'app/shared/model/ods.model';
import { getEntities as getOds } from 'app/entities/ods/ods.reducer';
import { Chip } from 'primereact/chip';
import { FilterService } from 'primereact/api';

const InnovacionesCrud = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const emptyInnovacion = {
    id: null,
    tematica: '',
    fecha: '',
    autores: '',
    numeroIdentidad: '',
    observacion: '',
    aprobada: '',
    publico: null,
    tipoIdea: null,
    user: null,
    sector: null,
    lineaInvestigacions: null,
    ods: null,
  };

  // Definimos la función de filtrado personalizada
  const odsFilter = (value, filter) => {
    // eslint-disable-next-line no-console
    console.log(filter);

    // eslint-disable-next-line no-console
    console.log(value);

    if (!filter) {
      return true; // Si no hay filtro, mostramos todos
    }

    // Comprobamos si el valor es un array y si contiene el filtro
    if (value.length === 0 && filter) return false;
    // eslint-disable-next-line no-console
    else {
      const si = value.map(item => item.ods);
      const elementos = value.filter(item => item.ods.includes(filter));

      // Mostrar los IDs en la consola
      // eslint-disable-next-line no-console
      console.log(elementos);
      if (elementos.length !== 0) {
        return true;
      } else return false;
    }
    return true;
  };

  FilterService.register('ods', odsFilter);

  const [filters, setFilters] = useState({
    global: { value: null, matchMode: FilterMatchMode.CONTAINS },
    'tipoIdea.tipoIdea': { value: null, matchMode: FilterMatchMode.STARTS_WITH },
    tematica: { value: null, matchMode: FilterMatchMode.STARTS_WITH },
    titulo: { value: null, matchMode: FilterMatchMode.STARTS_WITH },
    sindicato: { value: null, matchMode: FilterMatchMode.STARTS_WITH },
    fecha: { value: null, matchMode: FilterMatchMode.STARTS_WITH },
    fechaPractica: { value: null, matchMode: FilterMatchMode.STARTS_WITH },
    aprobada: { value: null, matchMode: FilterMatchMode.EQUALS },
    ods: { value: null, matchMode: FilterMatchMode.CONTAINS },
    sectors: { value: null, matchMode: FilterMatchMode.CONTAINS },
    lineaIlineaInvestigacions: { value: null, matchMode: FilterMatchMode.CONTAINS },
  });

  const [globalFilterValue, setGlobalFilterValue] = useState('');
  const [innovacionDialog, setInnovacionDialog] = useState(false);

  const ListInno = useAppSelector(state => state.innovacionRacionalizacion.entities);
  const [listInnoFiltrada, setlistInnoFiltrada] = useState([]);
  const tipoIdeas = useAppSelector(state => state.tipoIdea.entities);
  const innovacionRacionalizacionEntity = useAppSelector(state => state.innovacionRacionalizacion.entity);
  const loading = useAppSelector(state => state.innovacionRacionalizacion.loading);
  const updating = useAppSelector(state => state.innovacionRacionalizacion.updating);
  const updateSuccess = useAppSelector(state => state.innovacionRacionalizacion.updateSuccess);

  const [isNew, setNew] = useState(true);
  const [selectedinnovacion, setselectedInnovacion] = useState(emptyInnovacion);

  const [innoDialogNew, setInnoDialogNew] = useState(false);
  const [innoDialogNew1, setInnoDialogNew1] = useState(false);
  const [deleteInnoDialog, setDeleteInnoDialog] = useState(false);

  const account = useAppSelector(state => state.authentication.account);

  const sectors = useAppSelector(state => state.sector.entities);
  const lineaInvestigacions = useAppSelector(state => state.lineaInvestigacion.entities);
  const ods = useAppSelector(state => state.ods.entities);

  const onGlobalFilterChange = e => {
    const value = e.target.value;
    const _filters = { ...filters };
    _filters['global'].value = value;
    setFilters(_filters);
    setGlobalFilterValue(value);
  };

  const buscar = e => {
    const value = e;
    const _filters = { ...filters };
    _filters['global'].value = value;

    setFilters(_filters);
    setGlobalFilterValue(value);
  };
  const hideDialog = () => {
    setInnovacionDialog(false);
  };

  const productDialogFooter = (
    <React.Fragment>
      <Button label="Cerrar" icon="pi pi-times" className="p-button-text" onClick={hideDialog} />
    </React.Fragment>
  );

  const renderHeader = () => {
    return (
      <div className="flex justify-content-between align-items-center">
        <h5 className="m-0">Innovaciones</h5>
        <span className="p-input-icon-left">
          <i className="pi pi-search" />
          <InputText value={globalFilterValue} onChange={onGlobalFilterChange} placeholder="Buscar por palabra Clave" />
        </span>
      </div>
    );
  };
  useEffect(() => {
    setlistInnoFiltrada(ListInno);
  }, [ListInno]);

  useEffect(() => {
    dispatch(getTodasInnovacionesByPublicasAndUserId(account.id));
    dispatch(getTipoIdeas({}));

    dispatch(getSectors({}));
    dispatch(getLineaInvestigacions({}));
    dispatch(getOds({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      setInnoDialogNew(false);
      setNew(true);
      dispatch(reset());
      dispatch(getTodasInnovacionesByPublicasAndUserId(account.id));
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...innovacionRacionalizacionEntity,
      ...values,
      tipoIdea: tipoIdeas.find(it => it.id.toString() === values.tipoIdea.toString()),
      user: account,
      sector: mapIdList(values.sector),
      lineaInvestigacions: mapIdList(values.lineaInvestigacions),
      ods: mapIdList(values.ods),
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
          ...selectedinnovacion,
          tipoIdea: selectedinnovacion?.tipoIdea?.id,
          sector: selectedinnovacion?.sector?.map(e => e.id.toString()),
          lineaInvestigacions: selectedinnovacion?.lineaInvestigacions?.map(e => e.id.toString()),
          ods: selectedinnovacion?.ods?.map(e => e.id.toString()),
        };

  const editProduct = innovacion1 => {
    setselectedInnovacion(innovacion1);
    setInnoDialogNew1(true);
    setNew(false);
  };
  const confirmDeleteSelected = rowEcosistema => {
    setDeleteInnoDialog(true);
    setselectedInnovacion(rowEcosistema);
  };
  const actualizarInno = noticiasact => {
    setselectedInnovacion(noticiasact);
    setInnoDialogNew(true);
    setNew(false);
  };
  const actionBodyTemplate = rowData => {
    return (
      <div className="align-items-center justify-content-center">
        <Button icon="pi pi-eye" className="p-button-rounded p-button-info ml-2 mb-1" onClick={() => editProduct(rowData)} />
        {account?.authorities?.find(rol => rol === 'ROLE_ADMINECOSISTEMA') && (
          <Button
            icon="pi pi-trash"
            className="p-button-rounded p-button-danger ml-2 mb-1"
            onClick={() => confirmDeleteSelected(rowData)}
          />
        )}

        {account?.authorities?.find(rol => rol === 'ROLE_ADMINECOSISTEMA') && (
          <Button icon="pi pi-pencil" className="p-button-rounded p-button-warning ml-2 mb-1" onClick={() => actualizarInno(rowData)} />
        )}
      </div>
    );
  };

  const hideDialogNuevo = () => {
    setInnoDialogNew(false);
    setNew(true);
  };
  const hideDialogNuevo1 = () => {
    setInnoDialogNew1(false);
  };
  const atras = () => {
    props.history.push(`/usuario-panel`);
  };
  const header = renderHeader();
  const leftToolbarTemplate = () => {
    return (
      <React.Fragment>
        <div className="my-2">
          <Button label="Atrás" icon="pi pi-arrow-left" className="p-button-secondary mr-2" onClick={atras} />
        </div>
        {account?.authorities?.find(rol => rol === 'ROLE_GESTOR') && (
          <Button label="Nueva Innovación" icon="pi pi-plus" className="p-button-info" onClick={verDialogNuevo} />
        )}
      </React.Fragment>
    );
  };
  const verDialogNuevo = () => {
    setInnoDialogNew(true);
    setNew(true);
  };
  const atrasvista = () => {
    props.history.push(`/entidad/noticias/grid-noticias/${props.match.params.id}`);
  };
  const [buscarOpcion, setBuscarOpcion] = useState(null);
  const [selectedCities2, setSelectedCities2] = useState(null);
  const [selectedbuscar, setSelectedBuscar] = useState(true);
  const filtrarPorAlguno = e => {
    const value = { ...e.value };
    collapseAll();
    setBuscarOpcion(value);

    if (selectedCities2.code === 'ods') {
      filtrarbuscarODS(e.value);
    }
    if (selectedCities2.code === 'sc') {
      filtrarbuscarSector(e.value);
    }
    if (selectedCities2.code === 'pri') {
      filtrarbuscarLinea(e.value);
    }
  };
  const filtrarbuscarODS = value => {
    if (value) {
      const valueFiltrado = ListInno.filter(objeto => objeto.ods.some(elemento => elemento.id === value.id));

      setlistInnoFiltrada(valueFiltrado);
    } else setlistInnoFiltrada(ListInno);
  };
  const filtrarbuscarSector = value => {
    if (value) {
      const valueFiltrado = ListInno.filter(objeto => objeto.sector.some(elemento => elemento.id === value.id));

      setlistInnoFiltrada(valueFiltrado);
    } else setlistInnoFiltrada(ListInno);
  };
  const filtrarbuscarLinea = value => {
    if (value) {
      const valueFiltrado = ListInno.filter(objeto => objeto.lineaInvestigacions.some(elemento => elemento.id === value.id));

      setlistInnoFiltrada(valueFiltrado);
    } else setlistInnoFiltrada(ListInno);
  };
  const busqueda = [
    { name: 'Sector', code: 'sc' },
    { name: 'ODS', code: 'ods' },
    { name: 'Prioridad', code: 'pri' },
  ];
  // eslint-disable-next-line prefer-const
  const onGlobalFilterChangeInno1 = e => {
    setSelectedCities2(e.value);
    setSelectedBuscar(false);

    if (selectedCities2) {
      setSelectedCities2(e.value);
      setSelectedBuscar(false);
      setBuscarOpcion(null);
      setlistInnoFiltrada(ListInno);
    } else {
      setBuscarOpcion(null);
      setSelectedBuscar(false);
      setlistInnoFiltrada(ListInno);
    }
  };
  const rightToolbarTemplate = () => {
    return (
      <React.Fragment>
        <div className=" ">
          <span className="p-input-icon-left">
            {selectedCities2?.code === 'ods' ? (
              <Dropdown
                value={buscarOpcion}
                options={ods}
                onChange={filtrarPorAlguno}
                optionLabel="ods"
                disabled={selectedbuscar}
                placeholder="Seleccionar ODS"
                dropdownIcon="pi pi-search"
                showClear
              />
            ) : selectedCities2?.code === 'sc' ? (
              <Dropdown
                value={buscarOpcion}
                options={sectors}
                onChange={filtrarPorAlguno}
                optionLabel="sector"
                disabled={selectedbuscar}
                placeholder="Seleccionar Sector"
                dropdownIcon="pi pi-search"
                showClear
              />
            ) : selectedCities2?.code === 'pri' ? (
              <Dropdown
                value={buscarOpcion}
                options={lineaInvestigacions}
                onChange={filtrarPorAlguno}
                optionLabel="linea"
                disabled={selectedbuscar}
                placeholder="Seleccionar prioridad"
                dropdownIcon="pi pi-search"
                showClear
              />
            ) : null}

            <Dropdown
              value={selectedCities2}
              options={busqueda}
              onChange={onGlobalFilterChangeInno1}
              optionLabel="name"
              placeholder="Filtrar por.."
              dropdownIcon="pi pi-search"
              showClear
              className="ml-1"
            />
          </span>
        </div>
      </React.Fragment>
    );
  };
  const hideDeleteInnoDialog = () => {
    setDeleteInnoDialog(false);
  };
  const deleteInno = () => {
    dispatch(deleteEntity(selectedinnovacion.id));
    setDeleteInnoDialog(false);
    setselectedInnovacion(null);
  };
  const deleteInnoDialogFooter = (
    <>
      <Button label="No" icon="pi pi-times" className="p-button-text" onClick={hideDeleteInnoDialog} />

      <Button label="Sí" icon="pi pi-check" className="p-button-text" onClick={deleteInno} />
    </>
  );
  const tematicaBodyTemplate = rowData => {
    return (
      <>
        <span className="pl-5">{rowData.tematica}</span>
      </>
    );
  };
  const lineaBodyTemplate = rowData => {
    return (
      <div className="flex flex-column ">
        {rowData.lineaInvestigacions
          ? rowData?.lineaInvestigacions.map((val, j) => <Chip key={j} label={val.linea} icon="pi pi-verified" className="mr-3 mb-2" />)
          : null}
      </div>
    );
  };
  const odsBodyTemplate = rowData => {
    const allOdsValues = rowData?.ods.map(val => val.ods).join(', ');
    return <div className="flex flex-column ">{allOdsValues}</div>;
  };
  const sectorBodyTemplate = rowData => {
    return (
      <div className="flex flex-column ">
        {rowData.sector
          ? rowData?.sector.map((val, j) => <Chip key={j} label={val.sector} icon="pi pi-verified" className="mr-3 mb-2" />)
          : null}
      </div>
    );
  };
  const estadoTemplate = rowData => {
    return <>{rowData.aprobada ? <Tag value="Aprobada" severity="success"></Tag> : <Tag value="No Aprobada" severity="danger"></Tag>}</>;
  };
  const [expandedRows, setExpandedRows] = useState(null);
  const rowExpansionTemplate = data => {
    return (
      <div className="flex flex-row">
        <div className="formgrid grid">
          <div className="field col-12 md:col-2">
            <label htmlFor="city">Fecha Puesta en Práctica</label>
            <input
              id="city"
              type="text"
              defaultValue={data.fechaPractica}
              className="text-base text-color readOnly font-bold surface-overlay p-2 border-1 border-solid surface-border border-round appearance-none outline-none focus:border-primary w-full"
            ></input>
          </div>

          <div className="field col-12 md:col-4">
            <label htmlFor="lastname6">Autor(es)</label>
            <input
              id="lastname7"
              type="text"
              defaultValue={data.autores}
              className="text-base text-color readOnly font-bold surface-overlay p-2 border-1 border-solid surface-border border-round appearance-none outline-none focus:border-primary w-full"
            ></input>
          </div>
          <div className="field col-6">
            <label htmlFor="address">Observación</label>
            <textarea
              id="address"
              rows={4}
              defaultValue={data.observacion}
              className="text-base text-color readOnly font-bold surface-overlay p-2 border-1 border-solid surface-border border-round appearance-none outline-none focus:border-primary w-full"
            ></textarea>
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
  const allowExpansion = rowData => {
    return true;
  };

  const matchModes = [{ label: 'ODS', value: 'ods' }];

  const odsRowFilterTemplate = options => {
    return (
      <InputText
        value={globalFilterValue}
        onChange={e => {
          setGlobalFilterValue(e.target.value);
          options.filterApplyCallback(e.target.value);
        }}
        placeholder="Buscar.."
        className="p-column-filter"
      />
    );
  };
  const customFilter = (value, fields, filterValue, filterMatchMode, filterLocale) => {
    // Implementación de tu filtro personalizado
    return value.filter(item => item[fields[0]].toLowerCase().includes(filterValue.toLowerCase()));
  };

  const collapseAll = () => {
    setExpandedRows(null);
  };
  const [estadofecha, setEstadoFecha] = useState('');
  const [estadoTitulo, setEstadoTitulo] = useState('');
  const [estadoTematica, setEstadoTematica] = useState('');
  const [estadoSindicato, setEstadoSindicato] = useState('');

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
  const tematicaFilterTemplate = options => {
    return (
      <InputText
        value={estadoTematica}
        type="search"
        onChange={e => {
          setEstadoTematica(e.target.value);
          collapseAll();
          options.filterApplyCallback(e.target.value);
        }}
        placeholder="Buscar.."
      />
    );
  };
  const tituloFilterTemplate = options => {
    return (
      <InputText
        value={estadoTitulo}
        type="search"
        onChange={e => {
          setEstadoTitulo(e.target.value);
          collapseAll();
          options.filterApplyCallback(e.target.value);
        }}
        placeholder="Buscar.."
      />
    );
  };
  const sindicatoFilterTemplate = options => {
    return (
      <InputText
        value={estadoSindicato}
        type="search"
        onChange={e => {
          setEstadoSindicato(e.target.value);
          collapseAll();
          options.filterApplyCallback(e.target.value);
        }}
        placeholder="Buscar.."
      />
    );
  };
  const [selectedValue, setSelectedValue] = useState(null);

  const statuses = ['Aprobada', 'No Aprobada'];

  const statusItemTemplate = option => {
    return (
      <>{option === 'Aprobada' ? <Tag value="Aprobada" severity="success"></Tag> : <Tag value="No Aprobada" severity="danger"></Tag>}</>
    );
  };
  const statusRowFilterTemplate = options => {
    return (
      <Dropdown
        value={selectedValue}
        options={statuses}
        onChange={e => {
          collapseAll();
          setSelectedValue(e.value);
          options.filterApplyCallback(e.value === 'Aprobada' ? true : false);
        }}
        itemTemplate={statusItemTemplate}
        placeholder="Seleccione"
        className="p-column-filter"
        showClear
      />
    );
  };
  const representativesItemTemplate = option => {
    return (
      <div className="p-multiselect-representative-option">
        <span className="image-text">{option.tipoIdea}</span>
      </div>
    );
  };

  const [selectedValueTipoIdea, setSelectedValueTipoIdea] = useState(null);

  const representativeRowFilterTemplate = options => {
    return (
      <Dropdown
        value={selectedValueTipoIdea}
        options={tipoIdeas}
        itemTemplate={representativesItemTemplate}
        onChange={e => {
          collapseAll();
          setSelectedValueTipoIdea(e.value);
          options.filterApplyCallback(e.value.tipoIdea);
        }}
        optionLabel="tipoIdea"
        placeholder="Buscar.."
        className="p-column-filter"
      />
    );
  };
  // Función para renderizar el ícono del expander
  const expanderTemplate = rowData => {
    return (
      <div className="align-items-center justify-content-center">
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
            expandedRows && expandedRows[rowData.id]
              ? 'p-button-rounded p-button-secondary ml-2 mb-1'
              : 'p-button-rounded p-button-info ml-2 mb-1'
          }
        />
        {account?.authorities?.find(rol => rol === 'ROLE_ADMINECOSISTEMA') && (
          <Button
            icon="pi pi-trash"
            className="p-button-rounded p-button-danger ml-2 mb-1"
            onClick={() => confirmDeleteSelected(rowData)}
          />
        )}
        {account?.authorities?.find(rol => rol === 'ROLE_ADMINECOSISTEMA') && (
          <Button icon="pi pi-pencil" className="p-button-rounded p-button-warning ml-2 mb-1" onClick={() => actualizarInno(rowData)} />
        )}
      </div>
    );
  };

  return (
    <div className="grid crud-demo mt-3 mb-4">
      <div className="col-12">
        <div className="card">
          <Toolbar className="mb-4" left={leftToolbarTemplate} right={rightToolbarTemplate}></Toolbar>
          <DataTable
            value={listInnoFiltrada}
            paginator
            className="p-datatable-customers"
            header={header}
            rows={10}
            paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
            rowsPerPageOptions={[10, 25, 50]}
            dataKey="id"
            rowHover
            selection={selectedinnovacion}
            onSelectionChange={e => setselectedInnovacion(e.value)}
            filters={filters as DataTableFilterMeta}
            filterDisplay="row"
            loading={loading}
            responsiveLayout="stack"
            globalFilterFields={['titulo', 'tematica', 'fecha', 'autores', 'ods', 'tipoIdea.tipoIdea']}
            emptyMessage="No existen Innovaciones."
            currentPageReportTemplate="Mostrar {first} al {last} de {totalRecords} Innovaciones"
            expandedRows={expandedRows}
            onRowToggle={e => setExpandedRows(e.data)}
            rowExpansionTemplate={rowExpansionTemplate}
          >
            <Column
              field="fecha"
              header="Fecha"
              filter
              filterPlaceholder="Año-mes-día"
              showFilterMenu={false}
              filterMenuStyle={{ width: '10rem' }}
              sortable
              style={{ minWidth: '10rem' }}
              filterElement={fechaRowFilterTemplate}
            />

            <Column
              field="tipoIdea.tipoIdea"
              header="Tipo Idea"
              sortable
              style={{ minWidth: '10rem' }}
              filterField="tipoIdea.tipoIdea"
              showFilterMenu={false}
              filterMenuStyle={{ width: '12rem' }}
              filter
              filterElement={representativeRowFilterTemplate}
            />

            <Column
              filterField="titulo"
              field="titulo"
              filter
              filterPlaceholder="Buscar.."
              filterMenuStyle={{ width: '14rem' }}
              header="Título"
              sortable
              showFilterMenu={false}
              style={{ minWidth: '14rem' }}
              filterElement={tituloFilterTemplate}
            />

            <Column
              field="tematica"
              filter
              header="Tematica"
              body={tematicaBodyTemplate}
              sortable
              style={{ minWidth: '14rem' }}
              showFilterMenu={false}
              filterMatchMode="startsWith"
              filterMatchModeOptions={[
                { value: 'startsWith', label: 'Comienza con' },
                { value: 'contains', label: 'Contiene' },
                { value: 'equals', label: 'Igual a' },
              ]}
              filterPlaceholder="Buscar.."
              filterElement={tematicaFilterTemplate}
            />
            <Column
              field="sindicato"
              header="Sindicato"
              sortable
              style={{ minWidth: '12rem' }}
              filter
              showFilterMenu={false}
              filterPlaceholder="Buscar.."
              filterElement={sindicatoFilterTemplate}
            />
            <Column
              field="aprobada"
              filter
              showFilterMenu={false}
              filterElement={statusRowFilterTemplate}
              filterMenuStyle={{ width: '6rem' }}
              header="Escalable"
              sortable
              body={estadoTemplate}
              headerStyle={{ minWidth: '7rem' }}
            ></Column>

            <Column expander={allowExpansion} body={expanderTemplate} style={{ width: '1em' }} />
          </DataTable>
        </div>

        <Dialog visible={innoDialogNew} style={{ width: '600px' }} header="Innovación" modal className="p-fluid  " onHide={hideDialogNuevo}>
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
                    id="innovacion-racionalizacion-id"
                    label={translate('global.field.id')}
                    validate={{ required: true }}
                  />
                ) : null}
                <ValidatedField
                  id="innovacion-racionalizacion-tipoIdea"
                  name="tipoIdea"
                  data-cy="tipoIdea"
                  label={translate('jhipsterApp.innovacionRacionalizacion.tipoIdea')}
                  type="select"
                >
                  <option value="" key="0" />
                  {tipoIdeas
                    ? tipoIdeas.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.tipoIdea}
                        </option>
                      ))
                    : null}
                </ValidatedField>
                <ValidatedField
                  label={translate('jhipsterApp.innovacionRacionalizacion.titulo')}
                  id="innovacion-racionalizacion-titulo"
                  name="titulo"
                  data-cy="titulo"
                  type="text"
                  validate={{
                    required: { value: true, message: translate('entity.validation.required') },
                  }}
                />
                <ValidatedField
                  label={translate('jhipsterApp.innovacionRacionalizacion.tematica')}
                  id="innovacion-racionalizacion-tematica"
                  name="tematica"
                  data-cy="tematica"
                  type="text"
                  validate={{
                    required: { value: true, message: translate('entity.validation.required') },
                  }}
                />
                <ValidatedField
                  label={translate('jhipsterApp.innovacionRacionalizacion.sindicato')}
                  id="innovacion-racionalizacion-sindicato"
                  name="sindicato"
                  data-cy="sindicato"
                  type="text"
                  validate={{
                    required: { value: true, message: translate('entity.validation.required') },
                  }}
                />
                <ValidatedField
                  label={translate('jhipsterApp.innovacionRacionalizacion.fecha')}
                  id="innovacion-racionalizacion-fecha"
                  name="fecha"
                  data-cy="fecha"
                  type="date"
                  validate={{
                    required: { value: true, message: translate('entity.validation.required') },
                  }}
                />
                <ValidatedField
                  label={translate('jhipsterApp.innovacionRacionalizacion.fechaPractica')}
                  id="innovacion-racionalizacion-fechaPractica"
                  name="fechaPractica"
                  data-cy="fechaPractica"
                  type="date"
                  validate={{
                    required: { value: true, message: translate('entity.validation.required') },
                  }}
                />
                <ValidatedField
                  label={translate('jhipsterApp.innovacionRacionalizacion.autores')}
                  id="innovacion-racionalizacion-autores"
                  name="autores"
                  data-cy="autores"
                  type="text"
                  validate={{
                    required: { value: true, message: translate('entity.validation.required') },
                  }}
                />
                <ValidatedField
                  label={translate('jhipsterApp.innovacionRacionalizacion.numeroIdentidad')}
                  id="innovacion-racionalizacion-numeroIdentidad"
                  name="numeroIdentidad"
                  data-cy="numeroIdentidad"
                  type="text"
                  validate={{
                    required: { value: true, message: translate('entity.validation.required') },
                    validate: v => isNumber(v) || translate('entity.validation.number'),
                  }}
                />
                <ValidatedField
                  label={translate('jhipsterApp.innovacionRacionalizacion.observacion')}
                  id="innovacion-racionalizacion-observacion"
                  name="observacion"
                  data-cy="observacion"
                  type="textarea"
                />
                <ValidatedField
                  label={translate('jhipsterApp.innovacionRacionalizacion.aprobada')}
                  id="innovacion-racionalizacion-aprobada"
                  name="aprobada"
                  data-cy="aprobada"
                  check
                  type="checkbox"
                />
                <ValidatedField
                  label={translate('jhipsterApp.innovacionRacionalizacion.publico')}
                  id="innovacion-racionalizacion-publico"
                  name="publico"
                  data-cy="publico"
                  check
                  type="checkbox"
                />
                <ValidatedField
                  label={translate('jhipsterApp.proyectos.sector')}
                  id="innovacion-racionalizacion-sector"
                  data-cy="sector"
                  type="select"
                  multiple
                  name="sector"
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
                  id="innovacion-racionalizacion-lineaInvestigacion"
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
                  id="innovacion-racionalizacion-ods"
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
          visible={deleteInnoDialog}
          style={{ width: '450px' }}
          header="Confirmar"
          modal
          footer={deleteInnoDialogFooter}
          onHide={hideDeleteInnoDialog}
        >
          <div className="flex align-items-center justify-content-center">
            <i className="pi pi-exclamation-triangle mr-3" style={{ fontSize: '2rem' }} />
            {selectedinnovacion && (
              <span>
                ¿Seguro que quiere eliminar la Innovación: <b>{selectedinnovacion.tematica}</b>?
              </span>
            )}
          </div>
        </Dialog>

        <Dialog
          visible={innoDialogNew1}
          style={{ width: '600px' }}
          header="Innovación"
          modal
          className="p-fluid  "
          onHide={hideDialogNuevo1}
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
                    id="innovacion-racionalizacion-id"
                    label={translate('global.field.id')}
                    validate={{ required: true }}
                  />
                ) : null}
                <ValidatedField
                  id="innovacion-racionalizacion-tipoIdea"
                  name="tipoIdea"
                  data-cy="tipoIdea"
                  disabled
                  label={translate('jhipsterApp.innovacionRacionalizacion.tipoIdea')}
                  type="select"
                >
                  <option value="" key="0" />
                  {tipoIdeas
                    ? tipoIdeas.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.tipoIdea}
                        </option>
                      ))
                    : null}
                </ValidatedField>
                <ValidatedField
                  label={translate('jhipsterApp.innovacionRacionalizacion.titulo')}
                  id="innovacion-racionalizacion-titulo"
                  name="titulo"
                  data-cy="titulo"
                  readOnly
                  type="text"
                  validate={{
                    required: { value: true, message: translate('entity.validation.required') },
                  }}
                />
                <ValidatedField
                  label={translate('jhipsterApp.innovacionRacionalizacion.tematica')}
                  id="innovacion-racionalizacion-tematica"
                  name="tematica"
                  data-cy="tematica"
                  type="text"
                  readOnly
                  validate={{
                    required: { value: true, message: translate('entity.validation.required') },
                  }}
                />
                <ValidatedField
                  label={translate('jhipsterApp.innovacionRacionalizacion.sindicato')}
                  id="innovacion-racionalizacion-sindicato"
                  name="sindicato"
                  data-cy="sindicato"
                  type="text"
                  readOnly
                  validate={{
                    required: { value: true, message: translate('entity.validation.required') },
                  }}
                />
                <ValidatedField
                  label={translate('jhipsterApp.innovacionRacionalizacion.fecha')}
                  id="innovacion-racionalizacion-fecha"
                  name="fecha"
                  data-cy="fecha"
                  type="date"
                  readOnly
                  validate={{
                    required: { value: true, message: translate('entity.validation.required') },
                  }}
                />
                <ValidatedField
                  label={translate('jhipsterApp.innovacionRacionalizacion.fechaPractica')}
                  id="innovacion-racionalizacion-fechaPractica"
                  name="fechaPractica"
                  data-cy="fechaPractica"
                  type="date"
                  readOnly
                  validate={{
                    required: { value: true, message: translate('entity.validation.required') },
                  }}
                />

                <ValidatedField
                  label={translate('jhipsterApp.innovacionRacionalizacion.autores')}
                  id="innovacion-racionalizacion-autores"
                  name="autores"
                  data-cy="autores"
                  type="text"
                  readOnly
                  validate={{
                    required: { value: true, message: translate('entity.validation.required') },
                  }}
                />
                <ValidatedField
                  label={translate('jhipsterApp.innovacionRacionalizacion.numeroIdentidad')}
                  id="innovacion-racionalizacion-numeroIdentidad"
                  name="numeroIdentidad"
                  data-cy="numeroIdentidad"
                  type="text"
                  readOnly
                  validate={{
                    required: { value: true, message: translate('entity.validation.required') },
                    validate: v => isNumber(v) || translate('entity.validation.number'),
                  }}
                />
                <ValidatedField
                  label={translate('jhipsterApp.innovacionRacionalizacion.observacion')}
                  id="innovacion-racionalizacion-observacion"
                  name="observacion"
                  data-cy="observacion"
                  type="textarea"
                  readOnly
                />
                <ValidatedField
                  label={translate('jhipsterApp.innovacionRacionalizacion.aprobada')}
                  id="innovacion-racionalizacion-aprobada"
                  name="aprobada"
                  data-cy="aprobada"
                  check
                  type="checkbox"
                  readOnly
                />
                <ValidatedField
                  label={translate('jhipsterApp.innovacionRacionalizacion.publico')}
                  id="innovacion-racionalizacion-publico"
                  name="publico"
                  data-cy="publico"
                  check
                  type="checkbox"
                  readOnly
                />
                <h4 className="text-900 text-sm text-blue-600 font-medium">Sectores</h4>

                <div className="flex flex-column ">
                  {selectedinnovacion.sector
                    ? selectedinnovacion?.sector.map((val, j) => (
                        <Chip key={j} label={val.sector} icon="pi pi-verified" className="mr-3 mb-2" />
                      ))
                    : null}
                </div>
                <h4 className="text-900 text-sm text-blue-600 font-medium">Lineas de Ivestigación</h4>

                <div className="flex flex-column ">
                  {selectedinnovacion.lineaInvestigacions
                    ? selectedinnovacion?.lineaInvestigacions.map((val, j) => (
                        <Chip key={j} label={val.linea} icon="pi pi-verified" className="mr-3 mb-2" />
                      ))
                    : null}
                </div>
                <h4 className="text-900 text-sm text-blue-600 font-medium">ODS</h4>

                <div className="flex flex-column ">
                  {selectedinnovacion.ods
                    ? selectedinnovacion?.ods.map((val, j) => <Chip key={j} label={val.ods} icon="pi pi-verified" className="mr-3 mb-2" />)
                    : null}
                </div>
              </ValidatedForm>
            )}
          </Row>
        </Dialog>
      </div>
    </div>
  );
};

export default InnovacionesCrud;
