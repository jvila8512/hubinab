import { useAppDispatch, useAppSelector } from 'app/config/store';
import { Button } from 'primereact/button';

import { SplitButton } from 'primereact/splitbutton';
import { TabPanel, TabView } from 'primereact/tabview';
import { Toolbar } from 'primereact/toolbar';
import React, { useEffect, useState } from 'react';
import { RouteComponentProps } from 'react-router-dom';
import { getTodasInnovaciones } from 'app/entities/innovacion-racionalizacion/innovacion-racionalizacion.reducer';
import { getEntities as getTipoIdeas } from 'app/entities/tipo-idea/tipo-idea.reducer';
import { FilterMatchMode, FilterService } from 'primereact/api';
import { Row } from 'reactstrap';
import { Tag } from 'primereact/tag';
import { Calendar } from 'primereact/calendar';
import { isNumber, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { Dialog } from 'primereact/dialog';
import { DataTable, DataTableFilterMeta } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { InputText } from 'primereact/inputtext';
import { Dropdown } from 'primereact/dropdown';
import { MultiSelect } from 'primereact/multiselect';
import { Chip } from 'primereact/chip';
import ProyectosAdmin from './proyectosAdmin';
import { ISector } from 'app/shared/model/sector.model';
import { getEntities as getSectors } from 'app/entities/sector/sector.reducer';
import { ILineaInvestigacion } from 'app/shared/model/linea-investigacion.model';
import { getEntities as getLineaInvestigacions } from 'app/entities/linea-investigacion/linea-investigacion.reducer';
import { IOds } from 'app/shared/model/ods.model';
import { getEntities as getOds } from 'app/entities/ods/ods.reducer';

const BusquedaGeneral = (props: RouteComponentProps<{ orden: string; texto: string }>) => {
  const dispatch = useAppDispatch();
  const [activeIndex, setActiveIndex] = useState(0);
  const [texto, setTexto] = useState('');
  const sectors = useAppSelector(state => state.sector.entities);
  const lineaInvestigacions = useAppSelector(state => state.lineaInvestigacion.entities);
  const ods = useAppSelector(state => state.ods.entities);

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

  const [globalFilterValueInno, setGlobalFilterValueInno] = useState('');
  const [innovacionDialog, setInnovacionDialog] = useState(false);
  const loading = useAppSelector(state => state.innovacionRacionalizacion.loading);
  const [innovacion, setInnovacion] = useState(emptyInnovacion);
  const [selectedCustomers, setSelectedCustomers] = useState(null);

  const ListInno = useAppSelector(state => state.innovacionRacionalizacion.entities);
  const loadingListInno = useAppSelector(state => state.innovacionRacionalizacion.loading);
  const [listInnoFiltrada, setlistInnoFiltrada] = useState([]);
  const tipoIdeas = useAppSelector(state => state.tipoIdea.entities);

  useEffect(() => {
    dispatch(getTodasInnovaciones());
    dispatch(getTipoIdeas({}));
    dispatch(getSectors({}));
    dispatch(getLineaInvestigacions({}));
    dispatch(getOds({}));

    if (props.match.params.orden === 'prioridad') setSelectedCities2({ name: 'Prioridad', code: 'pri' });
    else if (props.match.params.orden === 'sector') setSelectedCities2({ name: 'Sector', code: 'sc' });
    else if (props.match.params.orden === 'ods') setSelectedCities2({ name: 'ODS', code: 'ods' });
    else {
      setSelectedBuscar(true);
      setSelectedCities2(null);
      const value = props.match.params.texto;
      const _filters = { ...filtersInno };
      _filters['global'].value = value;
      setFiltersInno(_filters);
      setGlobalFilterValueInno(value);
    }

    setSelectedBuscar(false);
  }, []);

  useEffect(() => {
    setlistInnoFiltrada(ListInno);
  }, [ListInno]);

  const [selectedCities2, setSelectedCities2] = useState(null);
  const [selectedbuscar, setSelectedBuscar] = useState(true);

  const [buscarOpcion, setBuscarOpcion] = useState(null);
  const [globalFilterValue, setGlobalFilterValue] = useState('');

  const [filtersInno, setFiltersInno] = useState({
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
  const customFilterMatchModes = {
    startsWith: 'Comienza con',
    contains: 'Contiene',
    endsWith: 'Termina con',
    equals: 'Igual a',
    notEquals: 'No igual a',
    in: 'En',
    lt: 'Menor que',
    lte: 'Menor o igual que',
    gt: 'Mayor que',
    gte: 'Mayor o igual que',
    custom: 'Personalizado',
  };

  const onGlobalFilterChangeInno = e => {
    collapseAll();
    const value = e.target.value;
    const _filters = { ...filtersInno };
    _filters['global'].value = value;
    setFiltersInno(_filters);
    setGlobalFilterValueInno(value);
  };

  const hideDialog = () => {
    setInnovacionDialog(false);
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

  const productDialogFooter = (
    <React.Fragment>
      <Button label="Cerrar" icon="pi pi-times" className="p-button-text" onClick={hideDialog} />
    </React.Fragment>
  );

  const renderHeader = () => {
    return (
      <div className="flex justify-content-between align-items-center">
        <div className="flex justify-content-start ">
          <div className="text-900 text-2xl text-blue-600 font-medium ">Innovaciones</div>
        </div>

        <div className="flex justify-content-end ">
          <div className="text-900 text-2xl text-blue-300 font-medium mr-1 pt-1">Palabra Clave</div>
          <span className="block mt-2 md:mt-0 p-input-icon-left">
            <i className="pi pi-search" />
            <InputText
              value={globalFilterValueInno}
              type="search"
              onInput={onGlobalFilterChangeInno}
              placeholder="Buscar por palabra Clave"
            />
          </span>
        </div>
      </div>
    );
  };
  const [tipoIdeasArray, setNamesArray] = useState([]);
  /**
 * 
 * useEffect(() => {
    const _filters = { ...filtersInno };
    setSelectedBuscar(false);

    dispatch(getTodasInnovaciones());
    if (props.match.params.orden === 'prioridad') {
      setSelectedCities2([{ name: 'Prioridad', code: 'pri' }]);
      _filters['titulo'].value = props.match.params.texto;
    } else {
      if (props.match.params.orden === 'ods') {
        setSelectedCities2([{ name: 'ODS', code: 'ods' }]);
        _filters['tematica'].value = props.match.params.texto;
      } else {
        setSelectedCities2([{ name: 'Sector', code: 'sc' }]);
        _filters['fecha'].value = props.match.params.texto;
      }
    }

    setFiltersInno(_filters);
    setGlobalFilterValueInno(props.match.params.texto);
    dispatch(getTipoIdeas({}));
  }, []);
 * 
 * 
 */

  const editProduct = innovacion1 => {
    setInnovacion({ ...innovacion1 });
    setInnovacionDialog(true);
    setNew(false);
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
  const filtrarPorSector = e => {
    const value = { ...e.value };

    setBuscarOpcion(value);

    if (selectedCities2.code === 'sc') {
      filtrarbuscarSector(e.value);
    }
  };
  const filtrarPorLinea = e => {
    const value = { ...e.value };

    setBuscarOpcion(value);

    if (selectedCities2.code === 'pri') {
      filtrarbuscarLinea(e.value);
    }
  };

  const actionBodyTemplate = rowData => {
    return (
      <React.Fragment>
        <Button icon="pi pi-eye" className="p-button-rounded p-button-info mr-2" />
      </React.Fragment>
    );
  };
  const tematicaBodyTemplate = rowData => {
    return (
      <>
        <span className="pl-5">{rowData.tematica}</span>
      </>
    );
  };
  const estadoTemplate = rowData => {
    return <>{rowData.aprobada ? <Tag value="Aprobada" severity="success"></Tag> : <Tag value="No Aprobada" severity="danger"></Tag>}</>;
  };

  const saveEntity = values => {};
  const [isNew, setNew] = useState(true);

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...innovacion,
          tipoIdea: innovacion?.tipoIdea?.id,
        };

  const header = renderHeader();
  const formatDate = date => {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0'); // Los meses son 0-indexed
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
  };

  const [selectedValueDate, setSelectedValueDate] = useState(null);
  const dateFilterTemplate = options => {
    return (
      <Calendar
        value={options.value}
        onChange={e => {
          const formattedDate = formatDate(e.value);
          setSelectedValueDate(e.value);
          options.filterCallback(e.value);
        }}
        dateFormat="yy-mm-dd"
        placeholder="Año-Mes-Día"
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

  const filterClearTemplateIdea = options => {
    setSelectedValueTipoIdea(null);

    return <Button type="button" icon="pi pi-times" onClick={options.filterClearCallback} className="p-button-secondary"></Button>;
  };

  const formatDate1 = value => {
    return value.toLocaleDateString('es-ES', {
      day: '2-digit',
      month: '2-digit',
      year: 'numeric',
    });
  };
  const dateBodyTemplate = rowData => {
    return formatDate(rowData.date);
  };

  // fin Innovaciones

  const tabHeaderITemplate = (options, i) => {
    return (
      <div
        className="flex align-items-center border-round-2xl shadow-4 pt-2"
        style={{ cursor: 'pointer' }}
        onClick={e => setActiveIndex(i)}
      >
        <h5 className="text-700 text-xs  sm:text-md text-blue-600 font-medium  p-2 "> {options}</h5>
      </div>
    );
  };
  const atras = () => {
    props.history.push(`/usuario-panel`);
  };
  const items = [
    {
      label: 'Prioridad',
      icon: 'pi pi-search',
      // eslint-disable-next-line object-shorthand
      command: () => {
        const value = texto;
        const _filters = { ...filtersInno };
        _filters['fecha'].value = value;

        setFiltersInno(_filters);
        setGlobalFilterValueInno(value);
      },
    },
    {
      label: 'ODS',
      icon: 'pi pi-search',
    },
    {
      label: 'Sector',
      icon: 'pi pi-search',
    },
  ];
  const leftToolbarTemplate = () => {
    return (
      <React.Fragment>
        <div className="my-2">
          <Button label="Atrás" icon="pi pi-arrow-left" className="p-button-secondary mr-2" onClick={atras} />
        </div>
        <div className="my-2">
          <h5 className="text-900 text-2xl text-blue-600 font-medium mt-2 mb-2 ml-4">Buscar Innovaciones o Proyectos</h5>
        </div>
      </React.Fragment>
    );
  };

  const rightToolbarTemplate = () => {
    return (
      <div className="flex justify-content-end ">
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
      </div>
    );
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
  // Definimos la función de filtrado personalizada
  const odsFilter1 = (value, filter, filterLocale, params) => {
    return true;
  };
  // Definimos la función de filtrado personalizada
  const odsFilter = (value, filter, filterLocale) => {
    if (!filter) {
      return true; // Si no hay filtro, mostramos todos
    }
    // Comprobamos si el valor es un array y si contiene el filtro
    if (value.length === 0 && filter) return false;
    // eslint-disable-next-line no-console
    else {
      const elementos = value.filter(item => item.id === filter.id);

      if (elementos.length !== 0) {
        return true;
      } else return false;
    }

    //  return value.filter(item => item.ods?.toLowerCase().includes(filter.toLowerCase()));
  };
  const customFilter = (value, fields, filterValue, filterMatchMode, filterLocale) => {
    // Implementación de tu filtro personalizado
    return value.filter(item => item[fields[0]].toLowerCase().includes(filterValue.toLowerCase()));
  };

  FilterService.register('ods', odsFilter);
  const matchModes = [{ label: 'ODS', value: 'ods' }];
  const [estado, setEstado] = useState('');
  const odsRowFilterTemplate = options => {
    return (
      <Dropdown
        value={estado}
        options={ods}
        onChange={e => {
          setEstado(e.value);
          options.filterApplyCallback(e.value);
        }}
        optionLabel="ods"
        // disabled={selectedbuscar}
        placeholder="Seleccionar."
        dropdownIcon="pi pi-search"
        showClear
      />
    );
  };
  const odsBodyTemplate = rowData => {
    const allOdsValues = rowData?.ods.map(val => val.ods).join(', ');
    return <div className="flex flex-column ">{allOdsValues}</div>;
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

  return (
    <div className="grid p-0 mt-5">
      <div className="col-12  mb-4 p-0">
        <div className="card">
          <Toolbar className="mb-4" left={leftToolbarTemplate} right={rightToolbarTemplate}></Toolbar>
          <TabView className="p-0" activeIndex={activeIndex} onTabChange={e => setActiveIndex(e.index)}>
            <TabPanel
              key={`card-0}`}
              header="Innovaciones"
              headerTemplate={tabHeaderITemplate('Innovaciones', 0)}
              headerClassName="flex align-items-center p-2"
              className="p-0"
            >
              <div className="card mt-5">
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
                  selection={selectedCustomers}
                  onSelectionChange={e => setSelectedCustomers(e.value)}
                  filters={filtersInno as DataTableFilterMeta}
                  filterDisplay="row"
                  loading={loading}
                  responsiveLayout="stack"
                  emptyMessage="En su búsqueda no se encontraron Innovaciones en el sistema."
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
                  <Column field="sector.sector" hidden header="Sector" sortable headerStyle={{ minWidth: '15rem' }}></Column>
                  <Column
                    field="lineaInvestigacions.linea"
                    hidden
                    header="Linea de Investigación"
                    sortable
                    headerStyle={{ minWidth: '15rem' }}
                  ></Column>
                  <Column
                    field="ods.ods"
                    header="ODS"
                    sortable
                    hidden
                    // filter
                    //  body={odsBodyTemplate}
                    // headerStyle={{ minWidth: '15rem' }}
                    // filterMatchModeOptions={matchModes}
                    // filterElement={odsRowFilterTemplate}
                    // filterMatchMode="custom"
                    // filterFunction={odsFilter1}

                    // hidden
                  ></Column>
                  <Column expander={allowExpansion} body={expanderTemplate} style={{ width: '1em' }} />
                </DataTable>
              </div>
              <Dialog
                visible={innovacionDialog}
                style={{ width: '600px' }}
                header="Detalles Innovación"
                modal
                className="p-fluid"
                footer={productDialogFooter}
                onHide={hideDialog}
              >
                <Row className="justify-content-center">
                  {loading ? (
                    <p>Cargando...</p>
                  ) : (
                    <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
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
                        {innovacion.sector
                          ? innovacion?.sector.map((val, j) => (
                              <Chip key={j} label={val.sector} icon="pi pi-verified" className="mr-3 mb-2" />
                            ))
                          : null}
                      </div>
                      <h4 className="text-900 text-sm text-blue-600 font-medium">Lineas de Ivestigación</h4>

                      <div className="flex flex-column ">
                        {innovacion.lineaInvestigacions
                          ? innovacion?.lineaInvestigacions.map((val, j) => (
                              <Chip key={j} label={val.linea} icon="pi pi-verified" className="mr-3 mb-2" />
                            ))
                          : null}
                      </div>
                      <h4 className="text-900 text-sm text-blue-600 font-medium">ODS</h4>

                      <div className="flex flex-column ">
                        {innovacion.ods
                          ? innovacion?.ods.map((val, j) => <Chip key={j} label={val.ods} icon="pi pi-verified" className="mr-3 mb-2" />)
                          : null}
                      </div>
                    </ValidatedForm>
                  )}
                </Row>
              </Dialog>
            </TabPanel>

            <TabPanel
              key={`card-1}`}
              header="Proyectos"
              headerTemplate={tabHeaderITemplate('Proyectos', 1)}
              headerClassName="flex align-items-center p-2"
              className="p-0"
            >
              <ProyectosAdmin filtrarPor={selectedCities2} valueBuscar={buscarOpcion} texto={props.match.params.texto} />
            </TabPanel>
          </TabView>
        </div>
      </div>
    </div>
  );
};

export default BusquedaGeneral;
