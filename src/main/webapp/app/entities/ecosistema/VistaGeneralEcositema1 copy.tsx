import { useAppDispatch, useAppSelector } from 'app/config/store';
import { Button } from 'primereact/button';
import { Toolbar } from 'primereact/toolbar';
import React, { useEffect, useRef, useState } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import VistaPrincipalNoticia from '../noticias/vistaPrincipalNoticiasEcosistema';
import VistaPrincipalProyectoEcosistema from '../proyectos/vistaPrincipalProyectoEcosistema';
import VistaPrincipal from '../reto/vistaPrincipalRetoEcosistema';
import { getEntity } from './ecosistema.reducer';
import { contarRetosbyEcosistemas } from '../../entities/reto/reto.reducer';
import { contarNoticiasbyEcosistemas } from '../../entities/noticias/noticias.reducer';
import { contarProyectosbyEcosistemas } from '../../entities/proyectos/proyectos.reducer';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import VistaComponente from '../ecosistema-componente/vistaComponente';
import ExpandableTableComponente from '../ecosistema-componente/expandableTableComponente';
import { Skeleton } from 'primereact/skeleton';
import { InputSwitch } from 'primereact/inputswitch';
import VistaPrincipalFiltrado from '../reto/vistaPrincipalRetoEcosistemaFiltrado';
import VistaPrincipalNoticiaSolo from '../noticias/vistaPrincipalNoticiasEcosistemaSolo';
import { getEntitiesByEcosistematodasFiltrarFechasSinRespuesta } from 'app/entities/reto/reto.reducer';
import { Avatar } from 'primereact/avatar';

import { faEyeSlash, faNewspaper } from '@fortawesome/free-solid-svg-icons';
import ComponenteCarusel from '../ecosistema-componente/componenteCarusel';
import { Row } from 'reactstrap';

function VistaGeneralEcositema1(props) {
  const dispatch = useAppDispatch();
  const ecosistemaEntity = useAppSelector(state => state.ecosistema.entity);
  const loading = useAppSelector(state => state.ecosistema.loading);
  const updating = useAppSelector(state => state.ecosistema.updating);
  const updateSuccess = useAppSelector(state => state.ecosistema.updateSuccess);
  const [reto, setReto] = useState(0);
  const [noticia, setNoticias] = useState(0);
  const [proyecto, setProyectos] = useState(0);
  const [usuario, setUsurios] = useState(0);

  const retosMio = useRef(null);
  const noticiasMias = useRef(null);

  const [retosFiltrados, setretosFiltrados] = useState(false);

  const [misRetos, setMisRetos] = useState(false);
  const [misNoticias, setMisNoticias] = useState(false);

  useEffect(() => {
    dispatch(getEntity(props.id));

    const retosFiltrar = getEntitiesByEcosistematodasFiltrarFechasSinRespuesta(props.id);
    retosFiltrar.then(response => {
      setretosFiltrados(response.data);
    });

    const retos = contarRetosbyEcosistemas(props.id);
    retos.then(response => {
      setReto(response.data);
    });
    const noticias = contarNoticiasbyEcosistemas(props.id);
    noticias.then(response => {
      setNoticias(response.data);
    });
    const proyectos = contarProyectosbyEcosistemas(props.id);
    proyectos.then(response => {
      setProyectos(response.data);
    });
  }, []);

  const atras = () => {
    props.history.push(`/usuario-panel/${props.index}`);
  };
  const actualizarMisRetos = e => {
    if (!misRetos) retosMio.current.textContent = 'Tus Retos en este ecosistema';
    else retosMio.current.textContent = 'Últimos retos';

    setMisRetos(!misRetos);
  };

  const actualizarMisNoticias = e => {
    if (!misNoticias) noticiasMias.current.textContent = 'Tus Noticias en este ecosistema';
    else noticiasMias.current.textContent = 'Últimas noticias';

    setMisNoticias(!misNoticias);
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
        <Button label="Nueva Idea" icon="pi pi-plus" className="p-button-info" />
      </React.Fragment>
    );
  };

  return (
    <div className=" grid mt-2 mb-4">
      <div className="flex sm:flex-row xl:col-6 lg:col-12 sm:col-12 md:col-12">
        <div className="col-12 ">
          <ComponenteCarusel id={props.id} logueado={props.logueado} ecosistemaEntity1={ecosistemaEntity} index={props.index} />
        </div>
      </div>
      <div className="flex  sm:flex-row xl:col-6 lg:col-12 sm:col-12 md:col-12">
        <div className=" col-12 xl:col-12 sm:col-12 md:col-12">
          <div className="flex sm:justify-content-end align-items-center text-700">
            <div className=" flex  align-items-center mt-1 ml-3 ">
              <div className="surface-0 shadow-3 p-3 border-1 border-50 border-round">
                <div className="flex justify-content-between mb-3">
                  <div>
                    <span className="block text-500 font-medium text-1xl mb-3">Retos</span>
                    <div className="text-600 font-medium text-2xl text-center">{reto}</div>
                  </div>
                  <div
                    className="flex align-items-center justify-content-center bg-orange-100  border-round ml-4"
                    style={{ width: '2.5rem', height: '2.5rem' }}
                  >
                    <i className="pi pi-inbox text-orange-500 text-xl"></i>
                  </div>
                </div>
              </div>
            </div>

            <div className=" flex  align-items-center mt-1 ml-3 ">
              <div className="surface-0 shadow-3 p-3 border-1 border-50 border-round">
                <div className="flex justify-content-between mb-3">
                  <div>
                    <span className="block text-500 font-medium text-1xl mb-3"> Noticias</span>
                    <div className="text-600 font-medium text-2xl text-center">{noticia}</div>
                  </div>
                  <div
                    className="flex align-items-center justify-content-center  text-cyan-500 bg-cyan-100 border-round ml-4"
                    style={{ width: '2.5rem', height: '2.5rem' }}
                  >
                    <FontAwesomeIcon icon={faNewspaper} size="lg" />
                  </div>
                </div>
              </div>
            </div>

            <div className=" flex  align-items-center mt-1 ml-3 ">
              <div className="surface-0 shadow-3 p-3 border-1 border-50 border-round">
                <div className="flex justify-content-between mb-3">
                  <div>
                    <span className="block text-500 font-medium text-1xl mb-3"> Proyectos</span>
                    <div className="text-600 font-medium text-2xl text-center">{proyecto}</div>
                  </div>
                  <div
                    className="flex align-items-center justify-content-center bg-purple-100 border-round ml-4"
                    style={{ width: '2.5rem', height: '2.5rem' }}
                  >
                    <i className="pi pi-map-marker text-blue-500 text-xl"></i>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div className="flex sm:flex-row xl:col-6 lg:col-12 sm:col-12 md:col-12">
        <div className="col-12 ">
          <div className="card mt-4 border-round-3xl  sm:h-auto ">
            <div className="flex justify-content-between">
              <div className="flex justify-content-start ">
                <div ref={retosMio} className="text-900 text-2xl text-blue-600 font-medium ">
                  Últimos retos
                </div>
              </div>
              <div className="flex justify-content-end ">
                <InputSwitch checked={misRetos} onChange={e => actualizarMisRetos(e)} />
              </div>
            </div>

            <Link to={`/entidad/reto/retogrid/${props.id}/${props.index}`}>
              <div className="flex justify-content-start">
                <div className="text-400  text-blue-800 font-medium ">Ver todos los retos</div>
              </div>
            </Link>

            {retosFiltrados && !misRetos ? (
              <VistaPrincipal id={props.id} index={props.index} usuariologeado={props.logueado} />
            ) : (
              <VistaPrincipalFiltrado id={props.id} index={props.index} usuariologeado={props.logueado} />
            )}
          </div>
        </div>
      </div>
      <div className="flex sm:flex-row xl:col-6 lg:col-12 sm:col-12 md:col-12">
        <div className="col-12 ">
          <div className="card mt-4 border-round-3xl   ">
            <div className="flex justify-content-between">
              <div className="flex justify-content-start ">
                <div ref={noticiasMias} className="text-900 text-2xl text-blue-600 font-medium ">
                  Últimas noticias
                </div>
              </div>
              <div className="flex justify-content-end ">
                <InputSwitch checked={misNoticias} onChange={e => actualizarMisNoticias(e)} />
              </div>
            </div>

            <Link to={`/entidad/noticias/grid-noticias/${props.id}/${props.index}`}>
              <div className="flex justify-content-start">
                <div className="text-400  text-blue-800 font-medium ">Ver todas las noticias</div>
              </div>
            </Link>
            {!misNoticias ? (
              <VistaPrincipalNoticia id={props.id} layout="list" index={props.index} usuariologeado={props.logueado} />
            ) : (
              <VistaPrincipalNoticiaSolo id={props.id} layout="list" index={props.index} usuariologeado={props.logueado} />
            )}
          </div>
        </div>
      </div>

      <div className="flex sm:flex-row xl:col-6 lg:col-12 sm:col-12 md:col-12">
        <div className="col-12 ">
          <div className="card mt-4 border-round-3xl  sm:h-auto">
            <div className="flex justify-content-start ">
              <div className="text-900 text-2xl text-blue-600 font-medium ">Proyectos</div>
            </div>
            <Link to={`/entidad/proyectos/proyectos_ecosistema/${props.id}`}>
              <div className="flex justify-content-start">
                <div className="text-400  text-blue-800 font-medium ">Ver todos los Proyectos</div>
              </div>
            </Link>
            <VistaPrincipalProyectoEcosistema id={props.id} />
          </div>
        </div>
      </div>

      <div className="flex card col-12 xl:col-12 sm:col-12 md:col-12 lg:col-12  mt-1 border-round-3xl ">
        <div className="flex mb-2">
          <div className="text-900 text-2xl text-blue-600 font-medium ">Componentes del Ecosistema</div>
          {props.logueado?.authorities?.find(rol => rol === 'ROLE_ADMINECOSISTEMA') &&
            ecosistemaEntity?.users?.find(user => user.id === props.logueado?.id) && (
              <Link to={`/entidad/ecosistema-componente/crud/${props.id}/${props.index}`}>
                <div className="flex justify-content-start flex-wrap  absolute top-0 right-0 mt-2 mr-4">
                  <FontAwesomeIcon icon="pencil" size="lg" /> <span className="d-none d-md-inline sm"></span>
                  <div className="text-400  text-blue-800 font-medium ">Componentes</div>
                </div>
              </Link>
            )}
        </div>
        <div className="flex mb-2">
          <ExpandableTableComponente id={props.id} />
        </div>
      </div>
    </div>
  );
}

export default VistaGeneralEcositema1;
