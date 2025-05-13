import React, { useEffect, useRef, useState } from 'react';
import { INoticias } from 'app/shared/model/noticias.model';
import {
  getEntity,
  updateEntity,
  createEntity,
  reset,
  getEntities,
  getNoticiasByEcosistemaId,
  deleteEntity,
  getNoticiasByPublicabyEcosistemaIdbyUserId,
} from './noticias.reducer';
import { RouteComponentProps } from 'react-router-dom';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IEcosistema } from 'app/shared/model/ecosistema.model';
import { getEntities as getEcosistemas, getEntity as getEcosistema } from 'app/entities/ecosistema/ecosistema.reducer';
import { ITipoNoticia } from 'app/shared/model/tipo-noticia.model';
import { getEntities as getTipoNoticias } from 'app/entities/tipo-noticia/tipo-noticia.reducer';
import { Toolbar } from 'primereact/toolbar';
import { Button } from 'primereact/button';
import { DataTable, DataTableFilterMeta } from 'primereact/datatable';
import { InputText } from 'primereact/inputtext';
import { FilterMatchMode, FilterOperator } from 'primereact/api';
import { Column } from 'primereact/column';
import { Dialog } from 'primereact/dialog';
import { Row } from 'reactstrap';
import { Translate, ValidatedBlobField, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Tag } from 'primereact/tag';

export const NoticiasCrudAdmin = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();
  const noticiasList = useAppSelector(state => state.noticias.entities);
  const [isNew, setNew] = useState(true);

  const users = useAppSelector(state => state.userManagement.users);
  const ecosistemaEntity = useAppSelector(state => state.ecosistema.entity);
  const tipoNoticias = useAppSelector(state => state.tipoNoticia.entities);
  const noticiasEntity = useAppSelector(state => state.noticias.entity);
  const loading = useAppSelector(state => state.noticias.loading);
  const updating = useAppSelector(state => state.noticias.updating);
  const updateSuccess = useAppSelector(state => state.noticias.updateSuccess);
  const account = useAppSelector(state => state.authentication.account);
  const ecosistemas = useAppSelector(state => state.ecosistema.entities);

  const [globalFilter, setGlobalFilterValue] = useState('');
  const [noticiasDialogNew, setNoticiasDialogNew] = useState(false);
  const [deleteNoticiasDialog, setDeleteNoticiasDialog] = useState(false);

  const [text2, setText2] = useState('');

  const emptyEcosistema = {
    id: null,
    titulo: '',
    descripcion: '',
    urlFotoContentType: '',
    urlFoto: '',
    publica: null,
    publicar: null,
    fechaCreada: null,
    user: null,
    ecosistema: null,
    tipoNoticia: null,
  };

  const [selectedNoticias, setSelectedNoticias] = useState(emptyEcosistema);

  const dt = useRef(null);

  const [filters, setFilters] = useState({
    global: { value: null, matchMode: FilterMatchMode.CONTAINS },
    titulo: { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.STARTS_WITH }] },
    publica: { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.STARTS_WITH }] },
    descripcion: { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.STARTS_WITH }] },
    fechaCreada: { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.DATE_IS }] },
  });

  const onGlobalFilterChange = e => {
    const value = e.target.value;
    const _filters = { ...filters };
    _filters['global'].value = value;

    setFilters(_filters);
    setGlobalFilterValue(value);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    }

    dispatch(getEcosistemas({}));
    dispatch(getEntities({}));
    dispatch(getUsers({}));

    dispatch(getTipoNoticias({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      dispatch(getEntities({}));
      setNew(true);
      dispatch(reset());
      setNoticiasDialogNew(false);
    }
  }, [updateSuccess]);

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
  const verDialogNuevo = () => {
    setNoticiasDialogNew(true);
    setNew(true);
  };
  const confirmDeleteSelected = rowNoticia => {
    setDeleteNoticiasDialog(true);
    setSelectedNoticias(rowNoticia);
  };
  const atrasvista = () => {
    props.history.push(`/entidad/noticias/grid-noticias/`);
  };
  const rightToolbarTemplate = () => {
    return (
      <React.Fragment>
        <Button label="Nueva Noticia" icon="pi pi-plus" className="p-button-info" onClick={verDialogNuevo} />
      </React.Fragment>
    );
  };
  const header = (
    <div className="flex flex-column md:flex-row md:justify-content-between md:align-items-center">
      <h3 className="m-0">Todas las Noticias:</h3>

      <span className="block mt-2 md:mt-0 p-input-icon-left">
        <i className="pi pi-search" />
        <InputText value={globalFilter} type="search" onInput={onGlobalFilterChange} placeholder="Buscar..." />
      </span>
    </div>
  );
  const tituloBodyTemplate = rowData => {
    return <>{rowData.titulo}</>;
  };
  const ecosistemaBodyTemplate = rowData => {
    return <>{rowData.ecosistema?.nombre}</>;
  };
  const descripcionBodyTemplate = rowData => {
    return (
      <>
        <>
          <span className="pl-5">{rowData.descripcion}</span>
        </>
      </>
    );
  };
  const actionBodyTemplate = rowData => {
    return (
      <div className="align-items-center justify-content-center">
        {rowData.publica && <Button icon="pi pi-eye" className="p-button-rounded p-button-info ml-2 mb-1" />}
        {rowData.user?.id === account.id && (
          <Button
            icon="pi pi-trash"
            className="p-button-rounded p-button-danger ml-2 mb-1"
            onClick={() => confirmDeleteSelected(rowData)}
          />
        )}

        {rowData.user?.id === account.id && (
          <Button icon="pi pi-pencil" className="p-button-rounded p-button-warning ml-2 mb-1" onClick={() => actualizarNoticias(rowData)} />
        )}
      </div>
    );
  };
  const saveEntity = values => {
    const entity = {
      ...noticiasEntity,
      ...values,
      user: account,

      ecosistema: ecosistemas.find(it => it.id.toString() === values.ecosistema.toString()),
      tipoNoticia: tipoNoticias.find(it => it.id.toString() === values.tipoNoticia.toString()),
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
          ...selectedNoticias,
          user: account,
          ecosistema: selectedNoticias?.ecosistema?.id,
          tipoNoticia: selectedNoticias?.tipoNoticia?.id,
        };
  const hideDialogNuevo = () => {
    setNoticiasDialogNew(false);
  };
  const hideDeleteNoticiasDialog = () => {
    setDeleteNoticiasDialog(false);
  };
  const deleteNoticia = () => {
    dispatch(deleteEntity(selectedNoticias.id));
    setDeleteNoticiasDialog(false);
    setSelectedNoticias(null);
  };

  const deleteEcosistemaDialogFooter = (
    <>
      <Button label="No" icon="pi pi-times" className="p-button-text" onClick={hideDeleteNoticiasDialog} />

      <Button label="Sí" icon="pi pi-check" className="p-button-text" onClick={deleteNoticia} />
    </>
  );
  const publicaTemplate = rowData => {
    return <>{rowData.publica ? <Tag value="Visible" severity="success"></Tag> : <Tag value="No Visible" severity="danger"></Tag>}</>;
  };
  const publicarTemplate = rowData => {
    return <>{rowData.publicar ? <Tag value="Publicada" severity="success"></Tag> : <Tag value="No Publicada" severity="danger"></Tag>}</>;
  };
  const actualizarNoticias = noticiasact => {
    setSelectedNoticias(noticiasact);
    setNoticiasDialogNew(true);
    setNew(false);
  };
  const fechaBodyTemplate = rowData => {
    return <>{rowData.fechaCreada}</>;
  };
  const userBodyTemplate = rowData => {
    return <>{rowData.user?.login}</>;
  };

  return (
    <>
      <div className="grid crud-demo mt-3 mb-4">
        <div className="col-12">
          <div className="card">
            <Toolbar className="mb-4" left={leftToolbarTemplate} right={rightToolbarTemplate}></Toolbar>

            <DataTable
              ref={dt}
              value={noticiasList}
              selection={selectedNoticias}
              onSelectionChange={e => setSelectedNoticias(e.value)}
              dataKey="id"
              paginator
              sortField="fechaCreada"
              rows={10}
              rowsPerPageOptions={[5, 10, 25]}
              className="datatable-responsive"
              paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
              currentPageReportTemplate="Mostrando del {first} al {last} de {totalRecords} Noticias"
              filters={filters as DataTableFilterMeta}
              filterDisplay="menu"
              loading={loading}
              emptyMessage="No hay Noticias..."
              header={header}
              responsiveLayout="stack"
            >
              <Column field="id" header="Id" hidden headerStyle={{ minWidth: '15rem' }}></Column>
              <Column
                field="ecosistema.nombre"
                header="Ecosistema"
                sortable
                body={ecosistemaBodyTemplate}
                headerStyle={{ minWidth: '15rem' }}
              ></Column>
              <Column field="titulo" header="Titulo" sortable body={tituloBodyTemplate} headerStyle={{ minWidth: '15rem' }}></Column>

              <Column
                field="descripcion"
                header="Noticia"
                sortable
                body={descripcionBodyTemplate}
                style={{ width: '40%', alignContent: 'right' }}
                headerStyle={{ minWidth: '15rem' }}
              ></Column>
              <Column field="publica" sortable header="Estado" body={publicaTemplate} headerStyle={{ minWidth: '7rem' }}></Column>
              <Column field="publicar" sortable header="Publicada" body={publicarTemplate} headerStyle={{ minWidth: '7rem' }}></Column>

              <Column field="fechaCreada" sortable header="Fecha" body={fechaBodyTemplate} headerStyle={{ minWidth: '7rem' }}></Column>
              <Column field="user" sortable header="Usuario" body={userBodyTemplate} headerStyle={{ minWidth: '7rem' }}></Column>

              <Column body={actionBodyTemplate} headerStyle={{ minWidth: '10rem' }}></Column>
            </DataTable>

            <Dialog
              visible={noticiasDialogNew}
              style={{ width: '600px' }}
              header="Noticia"
              modal
              className="p-fluid  "
              onHide={hideDialogNuevo}
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
                        id="noticias-id"
                        label={translate('global.field.id')}
                        validate={{ required: true }}
                      />
                    ) : null}
                    <ValidatedBlobField
                      label={translate('jhipsterApp.noticias.urlFoto')}
                      id="noticias-urlFoto"
                      name="urlFoto"
                      data-cy="urlFoto"
                      isImage
                      accept="image/*"
                    />
                    <ValidatedField
                      label={translate('jhipsterApp.noticias.titulo')}
                      id="noticias-titulo"
                      name="titulo"
                      data-cy="titulo"
                      type="text"
                      validate={{
                        required: { value: true, message: translate('entity.validation.required') },
                      }}
                    />

                    <ValidatedField
                      label={translate('jhipsterApp.noticias.descripcion')}
                      id="noticias-descripcion"
                      name="descripcion"
                      data-cy="descripcion"
                      type="textarea"
                      validate={{
                        required: { value: true, message: translate('entity.validation.required') },
                      }}
                    />

                    <ValidatedField
                      label={translate('jhipsterApp.noticias.publica')}
                      id="noticias-publica"
                      name="publica"
                      data-cy="publica"
                      check
                      type="checkbox"
                    />

                    {account.authorities.find(rol => rol === 'ROLE_ADMIN') && (
                      <ValidatedField
                        label={translate('jhipsterApp.noticias.publicar')}
                        id="noticias-publicar"
                        name="publicar"
                        data-cy="publicar"
                        check
                        type="checkbox"
                      />
                    )}
                    <ValidatedField
                      id="noticias-ecosistema"
                      name="ecosistema"
                      data-cy="ecosistema"
                      label={translate('jhipsterApp.noticias.ecosistema')}
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
                      label={translate('jhipsterApp.noticias.fechaCreada')}
                      id="noticias-fechaCreada"
                      name="fechaCreada"
                      data-cy="fechaCreada"
                      type="date"
                    />

                    <ValidatedField
                      id="noticias-tipoNoticia"
                      name="tipoNoticia"
                      data-cy="tipoNoticia"
                      label={translate('jhipsterApp.noticias.tipoNoticia')}
                      type="select"
                    >
                      <option value="" key="0" />
                      {tipoNoticias
                        ? tipoNoticias.map(otherEntity => (
                            <option value={otherEntity.id} key={otherEntity.id}>
                              {otherEntity.tipoNoticia}
                            </option>
                          ))
                        : null}
                    </ValidatedField>

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
              visible={deleteNoticiasDialog}
              style={{ width: '450px' }}
              header="Confirmar"
              modal
              footer={deleteEcosistemaDialogFooter}
              onHide={hideDeleteNoticiasDialog}
            >
              <div className="flex align-items-center justify-content-center">
                <i className="pi pi-exclamation-triangle mr-3" style={{ fontSize: '2rem' }} />
                {selectedNoticias && (
                  <span>
                    ¿Seguro que quiere eliminar la Noticia: <b>{selectedNoticias.titulo}</b>?
                  </span>
                )}
              </div>
            </Dialog>
          </div>
        </div>
      </div>
    </>
  );
};

export default NoticiasCrudAdmin;
