import { useAppDispatch, useAppSelector } from 'app/config/store';
import React, { useEffect, useRef, useState } from 'react';
import { createEntity, getEntities, reset, updateEntity, getComponentesbyEcosistema, deleteEntity } from './ecosistema-componente.reducer';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Toolbar } from 'primereact/toolbar';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';

import { FilterMatchMode, FilterOperator } from 'primereact/api';

import { InputText } from 'primereact/inputtext';
import { Button } from 'primereact/button';
import { Translate, ValidatedBlobField, ValidatedField, ValidatedForm, openFile, translate } from 'react-jhipster';
import { Dialog } from 'primereact/dialog';
import { Row } from 'reactstrap';
import { IComponentes } from 'app/shared/model/componentes.model';
import { getEntities as getComponentes } from 'app/entities/componentes/componentes.reducer';
import { IEcosistema } from 'app/shared/model/ecosistema.model';
import { getEntity as getEcosistema } from 'app/entities/ecosistema/ecosistema.reducer';
import { IEcosistemaComponente } from 'app/shared/model/ecosistema-componente.model';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

const ComponentesCrud = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const componenteList = useAppSelector(state => state.ecosistemaComponente.entities);
  const loading = useAppSelector(state => state.ecosistemaComponente.loading);
  const ecosistemaEntity = useAppSelector(state => state.ecosistema.entity);
  const componenteEntity = useAppSelector(state => state.ecosistemaComponente.entity);
  const updating = useAppSelector(state => state.ecosistemaComponente.updating);
  const updateSuccess = useAppSelector(state => state.ecosistemaComponente.updateSuccess);

  const componentes = useAppSelector(state => state.componentes.entities);

  const emptyComponente = {
    id: null,
    link: null,
    documentoUrlContentType: null,
    descripcion: null,
    documentoUrl: '',
    componente: null,
    ecosistema: null,
  };
  const [selectedComponente, setSelectedComponente] = useState(emptyComponente);

  const [isNew, setNew] = useState(true);
  const dt = useRef(null);
  const account = useAppSelector(state => state.authentication.account);
  const [globalFilter, setGlobalFilterValue] = useState('');
  const [componenteDialogNew, setComponenteDialogNew] = useState(false);
  const [componenteDeleteDialog, setDeleteComponenteDialog] = useState(false);

  useEffect(() => {
    dispatch(getComponentesbyEcosistema(props.match.params.id));
    dispatch(getComponentes({}));
    dispatch(getEcosistema(props.match.params.id));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      setComponenteDialogNew(false);
      setNew(false);
      dispatch(getComponentesbyEcosistema(props.match.params.id));
      dispatch(reset());
    }
  }, [updateSuccess]);
  const { match } = props;

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
  const verDialogNuevo = () => {
    setComponenteDialogNew(true);
    setNew(true);
  };

  const rightToolbarTemplate = () => {
    return (
      <React.Fragment>
        {account?.authorities?.find(rol => rol === 'ROLE_ADMINECOSISTEMA') &&
          ecosistemaEntity?.users?.find(user => user.id === account?.id) && (
            <Button label="Nuevo Componente" icon="pi pi-plus" className="p-button-info" onClick={verDialogNuevo} />
          )}
      </React.Fragment>
    );
  };
  const header = (
    <div className="flex flex-column md:flex-row md:justify-content-between md:align-items-center">
      <h3 className="m-0">Componentes del Ecosistema: {ecosistemaEntity.nombre}</h3>
    </div>
  );
  const nombreBodyTemplate = rowData => {
    return <>{rowData.componente.componente}</>;
  };
  const documentoBodyTemplate = rowData => {
    return (
      <>
        {rowData.documentoUrlContentType ? (
          <a className="text-blue" onClick={openFile(rowData.documentoUrlContentType, rowData.documentoUrl)}>
            Descargar
          </a>
        ) : null}
      </>
    );
  };
  const linkBodyTemplate = rowData => {
    return (
      <>
        {rowData.link ? (
          <a href={rowData.link} rel="noreferrer" className="text-blue" target="_blank">
            {rowData.link}
          </a>
        ) : null}
      </>
    );
  };
  const descripcionBodyTemplate = rowData => {
    return (
      <>
        <span className="pl-5"> {rowData.componente.descripcion}</span>
      </>
    );
  };
  const confirmDeleteSelected = rowEcosistema => {
    setDeleteComponenteDialog(true);
    setSelectedComponente(rowEcosistema);
  };
  const actualizarEcosistema = componenteact => {
    setSelectedComponente(componenteact);
    setComponenteDialogNew(true);
    setNew(false);
  };
  const actionBodyTemplate = rowData => {
    return (
      <div className="align-items-center justify-content-center">
        <Button icon="pi pi-trash" className="p-button-rounded p-button-danger ml-2 mb-1" onClick={() => confirmDeleteSelected(rowData)} />
        <Button icon="pi pi-pencil" className="p-button-rounded p-button-warning ml-2 mb-1" onClick={() => actualizarEcosistema(rowData)} />
      </div>
    );
  };
  const hideDialogNuevo = () => {
    setComponenteDialogNew(false);
  };
  const saveEntity = values => {
    const entity = {
      ...values,
      componente: componentes.find(it => it.id.toString() === values.componente.toString()),
      ecosistema: ecosistemaEntity,
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
          ...selectedComponente,
          componente: selectedComponente?.componente,
          ecosistema: ecosistemaEntity,
        };
  const hideDeleteComponenteDialog = () => {
    setDeleteComponenteDialog(false);
  };
  const deleteEcosistema = () => {
    dispatch(deleteEntity(selectedComponente.id));
    setDeleteComponenteDialog(false);
    setSelectedComponente(null);
  };
  const deleteComponenteDialogFooter = (
    <>
      <Button label="No" icon="pi pi-times" className="p-button-text" onClick={hideDeleteComponenteDialog} />

      <Button label="Sí" icon="pi pi-check" className="p-button-text" onClick={deleteEcosistema} />
    </>
  );
  const buscarComponenteEcositema = u => {
    const componente = componenteList.find(it => it.componente.id === u.id);

    if (componente) return false;
    else return true;
  };
  const filtradoComponentes = () => {
    return componentes.filter(comp => comp.id === buscarComponenteEcositema(comp));
  };

  return (
    <>
      <div className="grid crud-demo mt-3 mb-4">
        <div className="col-12">
          <div className="card">
            <Toolbar className="mb-4" left={leftToolbarTemplate} right={rightToolbarTemplate}></Toolbar>

            <DataTable
              ref={dt}
              value={componenteList}
              selection={selectedComponente}
              onSelectionChange={e => setSelectedComponente(e.value)}
              dataKey="id"
              rows={10}
              className="datatable-responsive"
              loading={loading}
              emptyMessage="No hay Componentes para el Ecosistema..."
              header={header}
              responsiveLayout="stack"
            >
              <Column field="id" header="Id" hidden headerStyle={{ minWidth: '15rem' }}></Column>
              <Column field="nombre" header="Nombre" sortable body={nombreBodyTemplate} headerStyle={{ minWidth: '15rem' }}></Column>
              <Column
                field="componente"
                header="Descripción"
                sortable
                body={descripcionBodyTemplate}
                style={{ width: '50%' }}
                headerStyle={{ minWidth: '15rem' }}
              ></Column>
              <Column
                field="documentoUrl"
                header="Documento"
                sortable
                body={documentoBodyTemplate}
                headerStyle={{ minWidth: '15rem' }}
              ></Column>

              <Column field="link" header="Dirección" sortable body={linkBodyTemplate} headerStyle={{ minWidth: '15rem' }}></Column>

              <Column body={actionBodyTemplate} headerStyle={{ minWidth: '10rem' }}></Column>
            </DataTable>

            <Dialog
              visible={componenteDialogNew}
              style={{ width: '450px' }}
              header="Componentes"
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
                        id="ecosistema-componente-id"
                        label={translate('global.field.id')}
                        validate={{ required: true }}
                      />
                    ) : null}
                    <ValidatedField
                      label={translate('jhipsterApp.ecosistemaComponente.link')}
                      id="ecosistema-componente-link"
                      name="link"
                      data-cy="link"
                      type="text"
                    />
                    <ValidatedBlobField
                      label={translate('jhipsterApp.ecosistemaComponente.documentoUrl')}
                      id="ecosistema-componente-documentoUrl"
                      name="documentoUrl"
                      data-cy="documentoUrl"
                      openActionLabel={translate('entity.action.open')}
                    />
                    <ValidatedField
                      id="ecosistema-componente-componente"
                      name="componente"
                      data-cy="componente"
                      label={translate('jhipsterApp.ecosistemaComponente.componente')}
                      type="select"
                      validate={{
                        required: { value: true, message: translate('entity.validation.required') },
                      }}
                    >
                      <option value="" key="0" />
                      {componentes
                        ? componentes.map(
                            otherEntity =>
                              buscarComponenteEcositema(otherEntity) && (
                                <option value={otherEntity.id} key={otherEntity.id}>
                                  {otherEntity.componente}
                                </option>
                              )
                          )
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
              visible={componenteDeleteDialog}
              style={{ width: '450px' }}
              header="Confirmar"
              modal
              footer={deleteComponenteDialogFooter}
              onHide={hideDeleteComponenteDialog}
            >
              <div className="flex align-items-center justify-content-center">
                <i className="pi pi-exclamation-triangle mr-3" style={{ fontSize: '2rem' }} />
                {selectedComponente && (
                  <span>
                    ¿Seguro que quiere eliminar el Componente: <b>{selectedComponente?.componente?.componente}</b> del Ecosistema?
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

export default ComponentesCrud;
