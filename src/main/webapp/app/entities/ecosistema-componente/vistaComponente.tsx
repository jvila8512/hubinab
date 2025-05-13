import React, { useEffect, useState } from 'react';

import { DataView, DataViewLayoutOptions } from 'primereact/dataview';
import { Rating } from 'primereact/rating';
import { Tag } from 'primereact/tag';
import { Skeleton } from 'primereact/skeleton';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { Link, RouteComponentProps } from 'react-router-dom';
import { TextFormat, Translate, ValidatedBlobField, ValidatedField, ValidatedForm, openFile, translate } from 'react-jhipster';

import { APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Alert, Button, Row } from 'reactstrap';
import { createEntity, getEntities, reset, updateEntity, getComponentesbyEcosistema, deleteEntity } from './ecosistema-componente.reducer';
import { ScrollPanel } from 'primereact/scrollpanel';
import { Dialog } from 'primereact/dialog';
import { getEntities as getComponentes } from 'app/entities/componentes/componentes.reducer';

const VistaComponente = props => {
  const dispatch = useAppDispatch();
  const [forma, setLayout] = useState(null);
  const [componenteDialogNew, setComponenteDialogNew] = useState(false);
  const [isNew, setNew] = useState(false);

  const componenteList = useAppSelector(state => state.ecosistemaComponente.entities);
  const loading = useAppSelector(state => state.ecosistemaComponente.loading);
  const componentes = useAppSelector(state => state.componentes.entities);
  const emptyComponente = {
    id: null,
    link: null,
    documentoUrlContentType: null,
    documentoUrl: '',
    componente: null,
    ecosistema: null,
  };
  const [selectedComponente, setSelectedComponente] = useState(emptyComponente);
  const [componente, setComponente] = useState([]);

  useEffect(() => {
    dispatch(getComponentesbyEcosistema(props.id));
    dispatch(getComponentes({}));
    setLayout('list');
  }, []);

  useEffect(() => {
    if (!loading) setComponente(componenteList);
  }, [loading]);

  const getSeverity = reto => {
    switch (reto.activo) {
      case true:
        return 'success';

      case false:
        return 'danger';

      default:
        return null;
    }
  };

  const getActivo = reto => {
    switch (reto.activo) {
      case true:
        return 'Activo';

      case false:
        return 'Activo';

      default:
        return null;
    }
  };
  const verReto = componente1 => {
    setComponenteDialogNew(true);
    setSelectedComponente(componente1);
  };

  const listItem = proyecto => {
    return (
      <div className="col-12 border-round-xl  mb-2">
        <div className="flex  xl:flex-row xl:align-items-center p-2 gap-4">
          <div className="flex  sm:flex-row justify-content-between align-items-center xl:align-items-start flex-1 gap-4">
            <div className="flex flex-column align-content-end align-items-center sm:align-items-start gap-3">
              <div className="text-base font-bold ">{proyecto.componente.componente}</div>
            </div>
            <div className="flex flex-column align-content-end align-items-center sm:align-items-center gap-3">
              <Button onClick={() => verReto(proyecto)}>
                <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline"></span>
              </Button>
            </div>
          </div>
        </div>
      </div>
    );
  };

  const gridItem = reto => {
    return (
      <div className="col-12  sm:col-6 lg:col-12 xl:col-4 p-2 ">
        <div className="p-4 border-1 surface-border surface-card border-round">
          <div className="flex flex-column align-items-center gap-3 py-5">
            <img
              className="w-9 sm:w-16rem xl:w-10rem shadow-2 block xl:block mx-auto border-round"
              src={`data:${reto.urlFotoContentType};base64,${reto.urlFoto}`}
              alt={reto.reto}
            />
          </div>
          <div className="text-2xl font-bold text-900">{reto.reto}</div>

          <div className=" w-auto text-800 text-2xl mb-1  ">{reto.descripcion}</div>

          <div className=" w-auto text-500 text-2xl mb-1  ">Motivación: {reto.motivacion}</div>

          <div className="flex flex-column sm:flex-row justify-content-between align-items-center gap-3 mb-2">
            <div className="flex align-items-center gap-3">
              <span className="flex align-items-center gap-2">
                <i className="pi pi-eye"></i>
                {reto.visto}
              </span>
            </div>
            <span className="flex align-items-center gap-2">
              <i className="pi pi-tag"></i>
              <span className="font-semibold">{reto?.ideas?.length} Ideas </span>
            </span>

            <div className="flex align-items-center gap-3">
              <span className="flex align-items-center gap-2">
                <i className="pi pi-calendar"></i>
                {reto.fechaInicio ? <TextFormat type="date" value={reto.fechaInicio} format={APP_LOCAL_DATE_FORMAT} /> : null}
              </span>
            </div>
            <div className="flex align-items-center gap-3">
              <span className="flex align-items-center gap-2">
                <i className="pi pi-calendar-times"></i>
                {reto.fechaFin ? <TextFormat type="date" value={reto.fechaFin} format={APP_LOCAL_DATE_FORMAT} /> : null}
              </span>
            </div>
          </div>

          <div className="flex align-items-center justify-content-end">
            <Tag value={getActivo(reto)} severity={getSeverity(reto)}></Tag>
            <Button icon="pi pi-eye" className="p-button ml-2" disabled={reto.activo === false}></Button>
          </div>
        </div>
      </div>
    );
  };

  const itemTemplate = (reto, layout1) => {
    if (!reto) {
      return;
    }

    if (layout1 === 'list') return listItem(reto);
    else if (layout1 === 'grid') return gridItem(reto);
  };

  const header = () => {
    return <></>;
  };

  const hideDialogNuevo = () => {
    setComponenteDialogNew(false);
  };
  const saveEntity = values => {
    setComponenteDialogNew(false);
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...selectedComponente,
          componente: selectedComponente?.componente,
        };

  return (
    <>
      {componenteList && componenteList.length > 0 ? (
        <ScrollPanel style={{ width: '100%', height: '250px' }}>
          <DataView value={componente} layout={forma} lazy itemTemplate={itemTemplate} emptyMessage="No hay Componentes" />
        </ScrollPanel>
      ) : (
        !loading && <div className="alert alert-warning mt-4">No hay Componentes.</div>
      )}

      <Dialog
        visible={componenteDialogNew}
        style={{ width: '450px' }}
        header="Componente"
        modal
        className="p-fluid  "
        onHide={hideDialogNuevo}
      >
        <Row className="justify-content-center">
          {loading ? (
            <p>Cargando...</p>
          ) : (
            <div className="col-12 border-round-xl  mb-2">
              <div className="flex flex-column xl:flex-row xl:align-items-center p-2 gap-4">
                <div className="flex flex-column sm:flex-row justify-content-between align-items-center xl:align-items-start flex-1 gap-4">
                  <div className="flex flex-column align-content-end align-items-center sm:align-items-start gap-3">
                    <div className="text-base font-bold ">{selectedComponente?.componente?.componente}: </div>
                    <div className="surface-overlay w-full  mb-2  overflow-hidden text-overflow-ellipsis">
                      {selectedComponente?.componente?.descripcion}
                    </div>

                    <div className="text-base font-bold ">
                      Documento:
                      {selectedComponente.documentoUrlContentType ? (
                        <a
                          className="text-primary"
                          onClick={openFile(selectedComponente.documentoUrlContentType, selectedComponente.documentoUrl)}
                        >
                          &nbsp; &nbsp;
                          <Translate contentKey="entity.action.open"> Open</Translate>
                          &nbsp;
                        </a>
                      ) : null}
                    </div>

                    {selectedComponente.link ? (
                      <div className="text-base font-bold ">
                        Link: &nbsp; &nbsp;
                        <a href={selectedComponente.link} target="_blank" rel="noopener noreferrer">
                          {selectedComponente.link}
                        </a>
                      </div>
                    ) : null}
                  </div>
                </div>
              </div>
            </div>
          )}
        </Row>
      </Dialog>
    </>
  );
};

export default VistaComponente;
