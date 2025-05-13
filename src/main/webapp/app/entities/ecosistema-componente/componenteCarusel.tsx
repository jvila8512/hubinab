import React, { useEffect, useState } from 'react';
import './carusel.css';
import { Carousel } from 'primereact/carousel';
import { Button } from 'primereact/button';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntitiesCarrusel } from 'app/entities/ecosistema/ecosistema.reducer';
import { Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { createEntity, getEntities, reset, updateEntity, getComponentesbyEcosistema, deleteEntity } from './ecosistema-componente.reducer';
import { ScrollPanel } from 'primereact/scrollpanel';
import { Dialog } from 'primereact/dialog';
import { getEntities as getComponentes } from 'app/entities/componentes/componentes.reducer';
import { Translate, openFile } from 'react-jhipster';
import { Row } from 'reactstrap';
import { getArchivo } from 'app/entities/Files/files.reducer';
import SpinnerCar from '../loader/spinner';

const ComponenteCarusel = props => {
  const dispatch = useAppDispatch();

  const [componenteDialogNew, setComponenteDialogNew] = useState(false);
  const [isNew, setNew] = useState(false);

  const componenteList = useAppSelector(state => state.ecosistemaComponente.entities);
  const loading = useAppSelector(state => state.ecosistemaComponente.loading);
  const componentes = useAppSelector(state => state.componentes.entities);

  const [selectedComponente, setSelectedComponente] = useState(null);

  const responsiveOptions = [
    {
      breakpoint: '1024px',
      numVisible: 1,
      numScroll: 1,
    },
    {
      breakpoint: '600px',
      numVisible: 1,
      numScroll: 1,
    },
    {
      breakpoint: '480px',
      numVisible: 1,
      numScroll: 1,
    },
  ];
  const emptyComponente = {
    id: null,
    link: null,
    documentoUrlContentType: null,
    descripcion: null,
    componentehijo: null,
    documentoUrl: '',
    componente: null,
    ecosistema: null,
  };

  useEffect(() => {
    dispatch(reset());
    dispatch(getComponentesbyEcosistema(props.id));
    dispatch(getComponentes({}));
  }, []);

  const productTemplate = selectedComponente1 => {
    return (
      <div className="flex  border-round-xl relative h-10rem mb-2">
        <div className="flex flex-column xl:flex-row xl:align-items-center justify-content-center p-2 gap-4">
          <div className="flex flex-column   align-items-center  flex-1 gap-4">
            <div className="flex flex-column  gap-3">
              <div className="text-base font-bold ">
                {selectedComponente1?.componente?.componente} : {selectedComponente1?.componentehijo}{' '}
              </div>

              <div
                className="surface-overlay  w-full 
                 h-3rem  mb-2  overflow-hidden text-overflow-ellipsis"
              >
                {selectedComponente1?.descripcion}
              </div>

              <div className="flex justify-content-end absolute top-0 right-0 mt-3 mr-2">
                <div onClick={() => verReto(selectedComponente1)} className="flex justify-content-start manito">
                  <FontAwesomeIcon icon="eye" size="lg" />
                  <span className="d-none d-md-inline sm"></span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  };
  const productTemplate1 = selectedComponente1 => {
    return (
      <div className=" border-round-xl relative h-10rem mb-2">
        <div className="flex flex-column xl:flex-row xl:align-items-center justify-content-center p-2 gap-4">
          <div className="flex flex-column   align-items-center  flex-1 gap-4"></div>
        </div>
      </div>
    );
  };
  const verReto = componente1 => {
    setComponenteDialogNew(true);
    setSelectedComponente(componente1);
  };
  const hideDialogNuevo = () => {
    setComponenteDialogNew(false);
    setSelectedComponente(null);
  };
  const handleFileDownload = (base64Data, fileName) => {
    const linkk = document.createElement('a');
    linkk.setAttribute('href', 'data:aplication/pdf;base64,' + base64Data);
    linkk.setAttribute('download', fileName);
    document.body.appendChild(linkk);
    linkk.click();
  };

  const descargarDocumento = row => {
    const retosFiltrar = getArchivo(row.documentoUrlContentType);
    retosFiltrar
      .then(response => {
        handleFileDownload(response.data, row.documentoUrlContentType);
      })
      .catch(error => {
        // Aquí puedes mostrar algo en caso de una respuesta negativa
        console.error('Error al obtener el archivo:', error);
      });
  };

  return (
    <div className="card flex border-round-3xl mt-3 mx-1">
      {componenteList && componenteList.length > 0 ? (
        <Carousel
          value={componenteList}
          numVisible={1}
          numScroll={1}
          className="m-0 p-0"
          responsiveOptions={responsiveOptions}
          autoplayInterval={4000}
          itemTemplate={productTemplate}
          header={
            <div className="flex mb-2">
              <div className="flex text-900 text-2xl text-blue-600 font-medium ">Componentes del Ecosistema</div>
              {props.logueado?.authorities?.find(rol => rol === 'ROLE_ADMINECOSISTEMA') &&
                props.ecosistemaEntity1.user?.id === props.logueado?.id && (
                  <Link to={`/entidad/ecosistema-componente/crud/${props.id}/${props.index}`}>
                    <div className="flex justify-content-start flex-wrap  absolute top-0 right-0 mt-2 mr-4">
                      <FontAwesomeIcon icon="pencil" size="lg" /> <span className="d-none d-md-inline sm"></span>
                    </div>
                  </Link>
                )}
            </div>
          }
        />
      ) : loading ? (
        <div className="grid mb-2">
          <div className="col-12">
            <div className="flex flex-row text-900 text-2xl text-blue-600 font-medium ">Componentes del Ecosistema</div>
            <div className="flex  flex-row">
              {props.logueado?.authorities?.find(rol => rol === 'ROLE_ADMINECOSISTEMA') &&
                props.ecosistemaEntity1.user?.id === props.logueado?.id && (
                  <Link to={`/entidad/ecosistema-componente/crud/${props.id}/${props.index}`}>
                    <div className="flex justify-content-start flex-wrap  absolute top-0 right-0 mt-2 mr-4">
                      <FontAwesomeIcon icon="pencil" size="lg" /> <span className="d-none d-md-inline sm"></span>
                    </div>
                  </Link>
                )}
            </div>
          </div>
          <div className="col-12">
            <div className="flex  flex-column">
              <div className="col-12">
                <SpinnerCar />
              </div>
            </div>
          </div>
        </div>
      ) : (
        <div className="grid mb-2">
          <div className="col-12">
            <div className="flex flex-row text-900 text-2xl text-blue-600 font-medium ">Componentes del Ecosistema</div>
            <div className="flex  flex-row">
              {props.logueado?.authorities?.find(rol => rol === 'ROLE_ADMINECOSISTEMA') &&
                props.ecosistemaEntity1.user?.id === props.logueado?.id && (
                  <Link to={`/entidad/ecosistema-componente/crud/${props.id}/${props.index}`}>
                    <div className="flex justify-content-start flex-wrap  absolute top-0 right-0 mt-2 mr-4">
                      <FontAwesomeIcon icon="pencil" size="lg" /> <span className="d-none d-md-inline sm"></span>
                    </div>
                  </Link>
                )}
            </div>
          </div>
          <div className="col-12">
            <div className="flex  flex-column">
              <div className="col-12">
                <div className="alert alert-warning mt-4">No hay Componentes.</div>
              </div>
            </div>
          </div>
        </div>
      )}

      <Dialog
        visible={componenteDialogNew}
        style={{ width: '500px' }}
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
                    <div className="text-base font-bold ">Tipo de Componente: {selectedComponente?.componente?.componente} </div>
                    <div className="text-base font-bold ">Componente: {selectedComponente?.componentehijo} </div>

                    <div className="surface-overlay w-full  mb-2  overflow-hidden text-overflow-ellipsis">
                      {selectedComponente?.descripcion}
                    </div>

                    {selectedComponente?.documentoUrlContentType ? (
                      <div className="flex text-base font-bold ">
                        <div className="mt-3"> Documento:</div>
                        <Button
                          label="Descargar"
                          className="p-button-secondary p-button-text ml-3"
                          onClick={() => descargarDocumento(selectedComponente)}
                        />
                      </div>
                    ) : null}

                    {selectedComponente?.link ? (
                      <div className=" flex text-base font-bold ">
                        <div> Link: &nbsp; &nbsp;</div>
                        <a href={selectedComponente?.link} target="_blank" rel="noopener noreferrer">
                          {selectedComponente?.link}
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
    </div>
  );
};

export default ComponenteCarusel;
