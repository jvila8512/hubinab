import React, { useEffect, useRef, useState } from 'react';

import { Button } from 'primereact/button';
import { Column } from 'primereact/column';
import { DataTable, DataTableFilterMeta } from 'primereact/datatable';
import { FilterMatchMode, FilterOperator } from 'primereact/api';
import { Dialog } from 'primereact/dialog';
import { FileUpload } from 'primereact/fileupload';
import { InputNumber } from 'primereact/inputnumber';
import { InputText } from 'primereact/inputtext';
import { InputTextarea } from 'primereact/inputtextarea';
import { RadioButton } from 'primereact/radiobutton';
import { Rating } from 'primereact/rating';
import { Toast } from 'primereact/toast';
import { Toolbar } from 'primereact/toolbar';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntitiesbyRetoId } from './idea.reducer';
import { getEntity as getEcosistema } from '../../entities/ecosistema/ecosistema.reducer';
import { getEntity as getReto } from '../../entities/reto/reto.reducer';
import { isNumber, TextFormat, Translate, translate, ValidatedBlobField, ValidatedField, ValidatedForm } from 'react-jhipster';
import { APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { RouteComponentProps } from 'react-router-dom';
import { Checkbox } from 'primereact/checkbox';

import { getEntities as getTipoIdeas } from '../../entities/tipo-idea/tipo-idea.reducer';
import { getEntities as getEntidades } from '../../entities/entidad/entidad.reducer';
import { updateEntity, createEntity as nuevaIdea, reset as resetearIdea, getEntity, deleteEntity } from '../../entities/idea/idea.reducer';
import { Col, Row } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

const IdeasbyRetos = (props: RouteComponentProps<{ id: string; idecosistema: string; index: string }>) => {
  const dispatch = useAppDispatch();
  const ideaList = useAppSelector(state => state.idea.entities);
  const retoEntity = useAppSelector(state => state.reto.entity);
  const loading = useAppSelector(state => state.idea.loading);

  const [retoDialog, setRetoDialog] = useState(false);
  const [globalFilter, setGlobalFilterValue] = useState('');
  const dt = useRef(null);

  const [ideaDialog, setIdeaDialog] = useState(false);
  const tipoIdeas = useAppSelector(state => state.tipoIdea.entities);
  const entidads = useAppSelector(state => state.entidad.entities);
  const ideaEntity = useAppSelector(state => state.idea.entity);
  const loadingIdea = useAppSelector(state => state.idea.loading);
  const updatingIdea = useAppSelector(state => state.idea.updating);
  const updateSuccessIdea = useAppSelector(state => state.idea.updateSuccess);

  const [isNewIdea, setNewIdea] = useState(true);
  const account = useAppSelector(state => state.authentication.account);

  const [deleteDialogIdea, setDeleteDialogIdea] = useState(false);

  const emptyIdea = {
    id: null,
    numeroRegistro: '',
    titulo: '',
    descripcion: '',
    autor: '',
    fechaInscripcion: '',
    visto: '',
    foto: null,
    fotoContentType: '',
    aceptada: '',
    entidad: '',
    tipoIdea: '',
  };
  const [idea, setIdea] = useState(emptyIdea);

  const [selectedIdea, setSelectedIdea] = useState(null);

  const [filters, setFilters] = useState({
    global: { value: null, matchMode: FilterMatchMode.CONTAINS },
    numeroRegistro: { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.STARTS_WITH }] },
    titulo: { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.STARTS_WITH }] },
    autor: { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.STARTS_WITH }] },
    descripcion: { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.STARTS_WITH }] },
    fechaInscripcion: { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.DATE_IS }] },
  });

  const atras = () => {
    props.history.push('/entidad/reto/retoecosistema/' + props.match.params.idecosistema + '/' + props.match.params.index);
  };

  useEffect(() => {
    dispatch(
      getEntitiesbyRetoId({
        id: props.match.params.id,
        iduser: account.id,
      })
    );
    dispatch(getReto(props.match.params.id));

    dispatch(getTipoIdeas({}));
    dispatch(getEntidades({}));
  }, []);

  useEffect(() => {
    if (updateSuccessIdea) {
      setIdeaDialog(false);
      setNewIdea(true);
      defaultValuesIdeas();
      dispatch(resetearIdea());
      dispatch(
        getEntitiesbyRetoId({
          id: props.match.params.id,
          iduser: account.id,
        })
      );
    }
  }, [updateSuccessIdea]);

  const confirmDeleteSelectedIdea = () => {
    setDeleteDialogIdea(true);
  };

  const leftToolbarTemplate = () => {
    return (
      <React.Fragment>
        <div className="my-2">
          <Button label="Atrás" icon="pi pi-arrow-left" className="p-button-secondary mr-2" onClick={atras} />
          <Button
            icon="pi pi-trash"
            className="p-button-danger"
            onClick={confirmDeleteSelectedIdea}
            disabled={!selectedIdea || !(account.id === selectedIdea?.user?.id)}
          />
        </div>
      </React.Fragment>
    );
  };
  const rightToolbarTemplate = () => {
    return (
      <React.Fragment>
        <Button label="Nueva Idea" icon="pi pi-plus" className="p-button-info" onClick={nuevaIdeas} />
      </React.Fragment>
    );
  };
  const onGlobalFilterChange = e => {
    const value = e.target.value;
    const _filters = { ...filters };
    _filters['global'].value = value;

    setFilters(_filters);
    setGlobalFilterValue(value);
  };

  const actualizarIdea = nueva => {
    setIdeaDialog(true);
  };
  const nuevaIdeas = () => {
    setIdeaDialog(true);
    setNewIdea(true);
  };

  const actualizarIdeas = filaIdea => {
    setIdeaDialog(true);
    dispatch(getEntity(filaIdea.id));
    setNewIdea(false);
  };

  const hideIdeaDialog = () => {
    setIdeaDialog(false);
    setNewIdea(false);
  };

  const saveIdea = values => {
    const entity = {
      ...values,
      user: account,
      reto: retoEntity,
      tipoIdea: tipoIdeas.find(it => it.id.toString() === values.tipoIdea.toString()),
      entidad: entidads.find(it => it.id.toString() === values.entidad.toString()),
    };

    if (isNewIdea) {
      dispatch(nuevaIdea(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValuesIdeas = () =>
    isNewIdea
      ? {}
      : {
          ...ideaEntity,
          user: account.id,
          reto: retoEntity.id,
          tipoIdea: ideaEntity?.tipoIdea?.id,
          entidad: ideaEntity?.entidad?.id,
        };

  const header = (
    <div className="flex flex-column md:flex-row md:justify-content-between md:align-items-center">
      <h5 className="m-0 text-blue-600">Ideas Públicas</h5>
      <h5 className="m-0 ">
        <span className="text-blue-600"> Reto: {retoEntity.reto}</span>
      </h5>
      <span className="block mt-2 md:mt-0 p-input-icon-left">
        <i className="pi pi-search" />
        <InputText value={globalFilter} type="search" onInput={onGlobalFilterChange} placeholder="Buscar..." />
      </span>
    </div>
  );

  const retoBodyTemplate = rowData => {
    return <>{rowData.titulo}</>;
  };
  const imageBodyTemplate = rowData => {
    return (
      <>
        <img src={`data:${rowData.fotoContentType};base64,${rowData.foto}`} style={{ maxHeight: '30px' }} />
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
        <Button icon="pi pi-eye" className="p-button-rounded p-button-info" onClick={() => verReto(rowData)} />

        {account.id === rowData?.user?.id && (
          <Button
            icon="pi pi-pencil"
            tooltip="Editar Idea"
            className="p-button-rounded p-button-warning ml-2"
            onClick={() => actualizarIdeas(rowData)}
          />
        )}
      </>
    );
  };

  const fechaInicioBodyTemplate = rowData => {
    return <>{rowData.fechaInscripcion}</>;
  };
  const publica = rowData => {
    return (
      <>
        <span className="font-bold">{rowData.publica ? 'Si' : 'No'}</span>
      </>
    );
  };

  const hideDialog = () => {
    setRetoDialog(false);
    setNewIdea(true);
  };
  const productDialogFooter = (
    <>
      <Button label="Cerrar" icon="pi pi-times" className="p-button-text" onClick={hideDialog} />
    </>
  );
  const verReto = product => {
    props.history.push(
      '/entidad/idea/verIdea/' +
        product.id +
        '/' +
        props.match.params.id +
        '/' +
        props.match.params.idecosistema +
        '/' +
        props.match.params.index
    );
  };
  const verRetocopia = product => {
    props.history.push('/entidad/reto/retoecosistema/' + props.match.params.idecosistema);
    setIdea({ ...product });
    setRetoDialog(true);
  };

  const hideDeleteDialogIdea = () => {
    setDeleteDialogIdea(false);
  };
  const deleteIdea = () => {
    dispatch(deleteEntity(selectedIdea.id));
    setDeleteDialogIdea(false);
    setSelectedIdea(null);
  };
  const deleteDialogFooterIdea = (
    <>
      <Button label="No" icon="pi pi-times" className="p-button-text" onClick={hideDeleteDialogIdea} />

      <Button label="Sí" icon="pi pi-check" className="p-button-text" onClick={deleteIdea} />
    </>
  );

  return (
    <div className="grid crud-demo pb-2">
      <div className="col-12">
        <div className="card">
          <Toolbar className="mb-4" left={leftToolbarTemplate} right={rightToolbarTemplate}></Toolbar>

          <DataTable
            ref={dt}
            value={ideaList}
            selection={selectedIdea}
            onSelectionChange={e => setSelectedIdea(e.value)}
            dataKey="id"
            paginator
            rows={10}
            rowsPerPageOptions={[5, 10, 25]}
            className="datatable-responsive"
            paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
            currentPageReportTemplate="Mostrando del {first} al {last} de {totalRecords} Ideas"
            filters={filters as DataTableFilterMeta}
            filterDisplay="menu"
            loading={loading}
            emptyMessage="No hay Ideas"
            header={header}
            responsiveLayout="stack"
          >
            <Column selectionMode="single" headerStyle={{ width: '4rem' }}></Column>
            <Column field="id" header="Id" hidden headerStyle={{ minWidth: '15rem' }}></Column>
            <Column field="titulo" header="Título" sortable body={retoBodyTemplate} headerStyle={{ minWidth: '15rem' }}></Column>
            <Column
              field="descricion"
              header="Descripción"
              style={{ width: '40%', height: '10%', textOverflow: 'ellipsis' }}
              sortable
              body={nameBodyTemplate}
              headerStyle={{ minWidth: '15rem' }}
            ></Column>
            <Column field="fechaInicio" header="Fecha Registrada" sortable body={fechaInicioBodyTemplate}></Column>
            <Column field="visto" header="Visto" sortable></Column>
            <Column field="publica" header="Publica" body={publica} sortable></Column>
            <Column field="entidad" header="Entidad" hidden sortable></Column>
            <Column field="tipoIdea" header="TipoIdea" hidden sortable></Column>
            <Column body={actionBodyTemplate} headerStyle={{ minWidth: '10rem' }}></Column>
          </DataTable>

          <Dialog
            visible={retoDialog}
            style={{ width: '450px' }}
            header="Idea"
            modal
            className="p-fluid"
            footer={productDialogFooter}
            onHide={hideDialog}
          >
            {idea.foto && (
              <img
                src={`data:${idea.fotoContentType};base64,${idea.foto}`}
                style={{ maxHeight: '200px' }}
                className="mt-0 mx-auto mb-5 block shadow-2"
              />
            )}

            <div className="field">
              <label htmlFor="name">No. Reg.</label>
              <InputText id="name" readOnly value={idea.numeroRegistro} />
            </div>

            <div className="field">
              <label htmlFor="name">Título</label>
              <InputText id="name" readOnly value={idea.titulo} />
            </div>

            <div className="field">
              <label htmlFor="name">Autor</label>
              <InputText id="name" readOnly value={idea.autor} />
            </div>
            <div className="field">
              <label htmlFor="ob">Descripción</label>
              <InputTextarea id="ob" readOnly value={idea.descripcion} rows={3} cols={20} />
            </div>
            <div className="field">
              <label htmlFor="ob">Visto</label>
              <InputText id="name" readOnly value={idea.visto} />
            </div>

            <div className="field-checkbox">
              <Checkbox inputId="checkOption2" readOnly name="option" checked={idea.aceptada} />
              <label htmlFor="checkOption2">Aceptada</label>
            </div>
          </Dialog>

          <Dialog visible={ideaDialog} style={{ width: '450px' }} header="Idea" modal onHide={hideIdeaDialog}>
            <Row className="justify-content-center">
              {loadingIdea ? (
                <p>Cargando...</p>
              ) : (
                <ValidatedForm defaultValues={defaultValuesIdeas()} onSubmit={saveIdea}>
                  <ValidatedField
                    label={translate('jhipsterApp.idea.numeroRegistro')}
                    id="idea-numeroRegistro"
                    name="numeroRegistro"
                    data-cy="numeroRegistro"
                    type="text"
                    validate={{
                      required: { value: true, message: translate('entity.validation.required') },
                      validate: v => isNumber(v) || translate('entity.validation.number'),
                    }}
                  />
                  <ValidatedField
                    label={translate('jhipsterApp.idea.titulo')}
                    id="idea-titulo"
                    name="titulo"
                    data-cy="titulo"
                    type="text"
                    validate={{
                      required: { value: true, message: translate('entity.validation.required') },
                    }}
                  />
                  <ValidatedField
                    label={translate('jhipsterApp.idea.descripcion')}
                    id="idea-descripcion"
                    name="descripcion"
                    data-cy="descripcion"
                    type="textarea"
                    validate={{
                      required: { value: true, message: translate('entity.validation.required') },
                    }}
                  />
                  <ValidatedField
                    label={translate('jhipsterApp.idea.autor')}
                    id="idea-autor"
                    name="autor"
                    data-cy="autor"
                    type="text"
                    validate={{
                      required: { value: true, message: translate('entity.validation.required') },
                    }}
                  />
                  <ValidatedField
                    label={translate('jhipsterApp.idea.fechaInscripcion')}
                    id="idea-fechaInscripcion"
                    name="fechaInscripcion"
                    data-cy="fechaInscripcion"
                    type="date"
                    validate={{
                      required: { value: true, message: translate('entity.validation.required') },
                    }}
                  />
                  <ValidatedBlobField
                    label={translate('jhipsterApp.idea.foto')}
                    id="idea-foto"
                    name="foto"
                    data-cy="foto"
                    isImage
                    accept="image/*"
                  />
                  <ValidatedField
                    label={translate('jhipsterApp.idea.publica')}
                    id="idea-publica"
                    name="publica"
                    data-cy="publica"
                    check
                    type="checkbox"
                  />
                  <ValidatedField
                    id="idea-tipoIdea"
                    name="tipoIdea"
                    data-cy="tipoIdea"
                    label={translate('jhipsterApp.idea.tipoIdea')}
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
                    id="idea-entidad"
                    name="entidad"
                    data-cy="entidad"
                    label={translate('jhipsterApp.idea.entidad')}
                    type="select"
                  >
                    <option value="" key="0" />
                    {entidads
                      ? entidads.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.entidad}
                          </option>
                        ))
                      : null}
                  </ValidatedField>
                  &nbsp;
                  <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updatingIdea}>
                    <FontAwesomeIcon icon="save" />
                    &nbsp;
                    <Translate contentKey="entity.action.save">Save</Translate>
                  </Button>
                </ValidatedForm>
              )}
            </Row>
          </Dialog>

          <Dialog
            visible={deleteDialogIdea}
            style={{ width: '450px' }}
            header="Confirmar"
            modal
            footer={deleteDialogFooterIdea}
            onHide={hideDeleteDialogIdea}
          >
            <div className="flex align-items-center justify-content-center">
              <i className="pi pi-exclamation-triangle mr-3" style={{ fontSize: '2rem' }} />
              {selectedIdea && (
                <span>
                  ¿Seguro que quiere eliminar la Idea: <b>{selectedIdea.titulo}</b>?
                </span>
              )}
            </div>
          </Dialog>
        </div>
      </div>
    </div>
  );
};

export default IdeasbyRetos;
