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
import localeEs from 'date-fns/locale/es';
import { MultiSelect } from 'primereact/multiselect';
import { Slider } from 'primereact/slider';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { getTodasInnovaciones } from './innovacion-racionalizacion.reducer';
import { getEntities as getTipoIdeas } from 'app/entities/tipo-idea/tipo-idea.reducer';

import { Dialog } from 'primereact/dialog';

import { RouteComponentProps } from 'react-router-dom';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getSortState, isNumber, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { Row } from 'reactstrap';
import { Tag } from 'primereact/tag';
import { Chip } from 'primereact/chip';

const BuscarInnovaciones = (props: RouteComponentProps<{ texto: string }>) => {
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
  const ListInno = useAppSelector(state => state.innovacionRacionalizacion.entities);

  const tipoIdeas = useAppSelector(state => state.tipoIdea.entities);

  const [selectedCustomers, setSelectedCustomers] = useState(null);

  const [filters, setFilters] = useState({
    global: { value: null, matchMode: FilterMatchMode.CONTAINS },
    'tipoIdea.tipoIdea': { value: null, matchMode: FilterMatchMode.STARTS_WITH },
    tematica: { value: null, matchMode: FilterMatchMode.STARTS_WITH },
    titulo: { value: null, matchMode: FilterMatchMode.STARTS_WITH },
    sindicato: { value: null, matchMode: FilterMatchMode.STARTS_WITH },
    fecha: { value: null, matchMode: FilterMatchMode.STARTS_WITH },
    fechaPractica: { value: null, matchMode: FilterMatchMode.STARTS_WITH },
    aprobada: { value: null, matchMode: FilterMatchMode.EQUALS },
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

  const [globalFilterValue, setGlobalFilterValue] = useState('');
  const [innovacionDialog, setInnovacionDialog] = useState(false);
  const loading = useAppSelector(state => state.innovacionRacionalizacion.loading);
  const [innovacion, setInnovacion] = useState(emptyInnovacion);

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
        <div className="flex justify-content-start ">
          <div className="text-900 text-2xl text-blue-600 font-medium ">Búsqueda de Innovaciones</div>
        </div>
        <span className="p-input-icon-left">
          <i className="pi pi-search" />
          <InputText value={globalFilterValue} onChange={onGlobalFilterChange} placeholder="Buscar por palabra Clave" />
        </span>
      </div>
    );
  };
  const [tipoIdeasArray, setNamesArray] = useState([]);

  useEffect(() => {
    dispatch(getTodasInnovaciones());
    buscar(props.match.params.texto);
    dispatch(getTipoIdeas({}));
  }, []);

  const editProduct = innovacion1 => {
    setInnovacion({ ...innovacion1 });
    setInnovacionDialog(true);
    setNew(false);
  };

  const actionBodyTemplate = rowData => {
    return (
      <React.Fragment>
        <Button icon="pi pi-eye" className="p-button-rounded p-button-info mr-2" onClick={() => editProduct(rowData)} />
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
          // eslint-disable-next-line no-console
          console.log(selectedValueDate) + ' ';
        }}
        dateFormat="yy-mm-dd"
        placeholder="Fecha"
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
          setSelectedValue(e.value);
          options.filterApplyCallback(e.value === 'Aprobada' ? true : false);
          // eslint-disable-next-line no-console
          console.log(selectedValue) + ' ';
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
          setSelectedValueTipoIdea(e.value);
          options.filterApplyCallback(e.value.tipoIdea);
        }}
        optionLabel="tipoIdea"
        placeholder="Buscar.."
        className="p-column-filter"
      />
    );
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
  const allowExpansion = rowData => {
    return true;
  };
  const [expandedRows, setExpandedRows] = useState(null);
  const rowExpansionTemplate = data => {
    return (
      <div className="flex flex-row">
        <div className="formgrid grid">
          <div className="field col-12 md:col-2">
            <label htmlFor="city">Fecha</label>
            <input
              id="city"
              type="text"
              value={data.fecha}
              className="text-base text-color readOnly font-bold surface-overlay p-2 border-1 border-solid surface-border border-round appearance-none outline-none focus:border-primary w-full"
            ></input>
          </div>
          <div className="field col-12 md:col-2">
            <label htmlFor="city">Fecha Puesta en Práctica</label>
            <input
              id="city"
              type="text"
              value={data.fechaPractica}
              className="text-base text-color readOnly font-bold surface-overlay p-2 border-1 border-solid surface-border border-round appearance-none outline-none focus:border-primary w-full"
            ></input>
          </div>
          <div className="field col-12 md:col-3">
            <label htmlFor="state">Tipo de Idea</label>
            <input
              id="city"
              type="text"
              value={data.tipoIdea.tipoIdea}
              className="text-base text-color readOnly font-bold  surface-overlay p-2 border-1 border-solid surface-border border-round appearance-none outline-none focus:border-primary w-full"
            ></input>
          </div>
          <div className="field col-12 md:col-3">
            <label htmlFor="state">Sindicato</label>
            <input
              id="city"
              type="text"
              value={data.sindicato}
              className="text-base text-color readOnly font-bold surface-overlay p-2 border-1 border-solid surface-border border-round appearance-none outline-none focus:border-primary w-full"
            ></input>
          </div>
          <div className="field col-12 md:col-1">
            <label htmlFor="zip">Escalable</label>
            <div className="mr-auto">
              {data.aprobada ? <Tag value="Aprobada" severity="success"></Tag> : <Tag value="No Aprobada" severity="danger"></Tag>}
            </div>
          </div>
          <div className="field col-12 md:col-1">
            <label htmlFor="zip">Estado</label>
            <div className="mr-auto">
              {data.publico ? <Tag value="Pública" severity="success"></Tag> : <Tag value="No Pública" severity="danger"></Tag>}
            </div>
          </div>

          <div className="field col-12 md:col-4">
            <label htmlFor="firstname6">Título</label>
            <input
              id="firstname5"
              type="text"
              value={data.titulo}
              className="text-base text-color readOnly font-bold surface-overlay p-2 border-1 border-solid surface-border border-round appearance-none outline-none focus:border-primary w-full"
            ></input>
          </div>
          <div className="field col-12 md:col-4">
            <label htmlFor="lastname6">Temática</label>
            <input
              id="lastname6"
              type="text"
              value={data.tematica}
              className="text-base text-color readOnly font-bold surface-overlay p-2 border-1 border-solid surface-border border-round appearance-none outline-none focus:border-primary w-full"
            ></input>
          </div>
          <div className="field col-12 md:col-4">
            <label htmlFor="lastname6">Autor(es)</label>
            <input
              id="lastname7"
              type="text"
              value={data.autores}
              className="text-base text-color readOnly font-bold surface-overlay p-2 border-1 border-solid surface-border border-round appearance-none outline-none focus:border-primary w-full"
            ></input>
          </div>
          <div className="field col-12">
            <label htmlFor="address">Observación</label>
            <textarea
              id="address"
              rows={4}
              value={data.observacion}
              className="text-base text-color readOnly font-bold surface-overlay p-2 border-1 border-solid surface-border border-round appearance-none outline-none focus:border-primary w-full"
            ></textarea>
          </div>

          <div className="field col-12 md:col-6">
            <label htmlFor="lastname6">Linea de Investigación</label>
            <div className="flex flex-column ">
              {data.lineaInvestigacions
                ? data?.lineaInvestigacions.map((val, j) => <Chip key={j} label={val.linea} icon="pi pi-verified" className="mr-3 mb-2" />)
                : null}
            </div>
          </div>
          <div className="field col-12 md:col-6">
            <label htmlFor="lastname6">Sector</label>
            <div className="flex flex-column ">
              {data.sector
                ? data?.sector.map((val, j) => <Chip key={j} label={val.sector} icon="pi pi-verified" className="mr-3 mb-2" />)
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
  return (
    <div className="datatable-doc-demo">
      <div className="card mt-5">
        <DataTable
          value={ListInno}
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
          filters={filters as DataTableFilterMeta}
          filterDisplay="row"
          loading={loading}
          responsiveLayout="stack"
          emptyMessage="No existen Innovaciones en el sistema."
          currentPageReportTemplate="Mostrar {first} al {last} de {totalRecords} Innovaciones"
          expandedRows={expandedRows}
          onRowToggle={e => setExpandedRows(e.data)}
          rowExpansionTemplate={rowExpansionTemplate}
        >
          <Column expander={allowExpansion} style={{ width: '3em' }} />
          <Column
            field="fecha"
            header="Fecha11"
            filter
            filterPlaceholder="Buscar.."
            showFilterMenu={false}
            filterMenuStyle={{ width: '12rem' }}
            sortable
            style={{ minWidth: '12rem' }}
          />

          <Column
            field="tipoIdea.tipoIdea"
            header="Tipo Idea"
            sortable
            style={{ minWidth: '12rem' }}
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
          />
          <Column
            field="sindicato"
            header="Sindicato"
            sortable
            style={{ minWidth: '12rem' }}
            filter
            showFilterMenu={false}
            filterPlaceholder="Buscar.."
          />
          <Column
            field="aprobada"
            filter
            showFilterMenu={false}
            filterElement={statusRowFilterTemplate}
            filterMenuStyle={{ width: '8rem' }}
            header="Escalable"
            sortable
            body={estadoTemplate}
            headerStyle={{ minWidth: '7rem' }}
          ></Column>
          <Column body={actionBodyTemplate} exportable={false} style={{ minWidth: '8rem' }}></Column>
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
            </ValidatedForm>
          )}
        </Row>
      </Dialog>
    </div>
  );
};

export default BuscarInnovaciones;
