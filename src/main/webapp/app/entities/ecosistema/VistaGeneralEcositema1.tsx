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
import { getEntitiesByEcosistematodasFiltrarFechasSinRespuesta, reset as resetRetos } from 'app/entities/reto/reto.reducer';
import { Avatar } from 'primereact/avatar';

import { faEyeSlash, faNewspaper } from '@fortawesome/free-solid-svg-icons';
import ComponenteCarusel from '../ecosistema-componente/componenteCarusel';
import { Row, Spinner } from 'reactstrap';
import { ScrollPanel } from 'primereact/scrollpanel';
import SpinnerCar from '../loader/spinner';

function VistaGeneralEcositema1(props) {
  const dispatch = useAppDispatch();
  const ecosistemaEntity = useAppSelector(state => state.ecosistema.entity);
  const loading = useAppSelector(state => state.ecosistema.loading);
  const updating = useAppSelector(state => state.ecosistema.updating);
  const updateSuccess = useAppSelector(state => state.ecosistema.updateSuccess);
  const [reto, setReto] = useState(null);
  const [noticia, setNoticias] = useState(null);
  const [proyecto, setProyectos] = useState(null);
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
    dispatch(resetRetos());
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
  const handleSyncList = () => {};

  return (
    <div className=" grid grid-nogutter surface-0 mt-2">
      <div className="col-12  md:col-6 p-0">
        <div className="flex  flex-row sm:flex-row  justify-content-start sm:justify-content-center  align-items-center text-700">
          <div className=" flex   align-items-center mt-1 ml-1 ">
            <div className="surface-0 p-1 sm:p-3 border-1 border-50 border-round">
              <div className="flex justify-content-between mb-3">
                <div>
                  <span className=" text-sm sm:text-xl text-500  font-medium  mb-3"> Retos</span>

                  {reto == null ? (
                    <div className="text-600 font-medium text-2xl text-center">
                      <Spinner></Spinner>
                    </div>
                  ) : (
                    <div className=" text-sm sm:text-xl text-600 font-medium  text-center">{reto}</div>
                  )}
                </div>
                <div
                  className="flex align-items-center justify-content-center bg-orange-100  border-round ml-1"
                  style={{ width: '2.5rem', height: '2.5rem' }}
                >
                  <i className="pi pi-inbox text-orange-500 text-xl"></i>
                </div>
              </div>
            </div>
          </div>

          <div className=" flex   align-items-center mt-1 ml-1 ">
            <div className="surface-0  p-1 sm:p-3 border-1 border-50 border-round">
              <div className="flex justify-content-between mb-3">
                <div>
                  <span className=" text-500  font-medium text-sm sm:text-xl mb-3"> Noticias</span>

                  {noticia == null ? (
                    <div className="text-600 font-medium text-sm sm:text-xl text-center">
                      <Spinner></Spinner>
                    </div>
                  ) : (
                    <div className="text-600 font-medium text-sm sm:text-xl text-center">{noticia}</div>
                  )}
                </div>
                <div
                  className="flex align-items-center justify-content-center   bg-cyan-100 border-round ml-1"
                  style={{ width: '2.5rem', height: '2.5rem' }}
                >
                  <FontAwesomeIcon icon={faNewspaper} size="lg" />
                </div>
              </div>
            </div>
          </div>

          <div className=" flex   align-items-center mt-1 ml-1 ">
            <div className="surface-0 p-1 sm:p-3 border-1 border-50 border-round">
              <div className="flex justify-content-between mb-3">
                <div>
                  <span className=" text-500  font-medium text-sm sm:text-xl mb-3"> Proyectos</span>
                  {proyecto == null ? (
                    <div className="text-600 font-medium text-2xl text-center">
                      <Spinner></Spinner>
                    </div>
                  ) : (
                    <div className="text-600 font-medium text-sm sm:text-xl text-center">{proyecto}</div>
                  )}
                </div>
                <div
                  className="flex align-items-center justify-content-center bg-purple-100 border-round ml-1"
                  style={{ width: '2.5rem', height: '2.5rem' }}
                >
                  <i className="pi pi-map-marker text-blue-500 text-xl"></i>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div className="col-12  md:col-6 p-0">
        <ComponenteCarusel id={props.id} logueado={props.logueado} ecosistemaEntity1={ecosistemaEntity} index={props.index} />
      </div>

      <div className=" col-12  md:col-6 ">
        <div className=" col-12 p-1 ">
          <div className="card mt-3 border-round-3xl   ">
            <div className="flex flex-row justify-content-between">
              <div className="flex justify-content-start ">
                <div ref={retosMio} className="text-900 text-2xl text-blue-600 font-medium ">
                  Últimos retos activos
                </div>
              </div>

              {(ecosistemaEntity.user?.login === props.logueado.login &&
                props.logueado?.authorities?.find(rol => rol === 'ROLE_ADMINECOSISTEMA')) ||
              (ecosistemaEntity.users?.find(user => user.login === props.logueado.login) &&
                props.logueado?.authorities?.find(rol => rol === 'ROLE_GESTOR')) ? (
                <div className="flex justify-content-end ">
                  <InputSwitch checked={misRetos} onChange={e => actualizarMisRetos(e)} />
                </div>
              ) : null}
            </div>

            <Link to={`/entidad/reto/retogrid/${props.id}/${props.index}`}>
              <div className="flex justify-content-start">
                <div className="text-400  text-blue-800 font-medium ">Ver todos los retos</div>
              </div>
            </Link>

            {!misRetos ? (
              <VistaPrincipal id={props.id} index={props.index} usuariologeado={props.logueado} />
            ) : (
              <VistaPrincipalFiltrado id={props.id} index={props.index} usuariologeado={props.logueado} validar={misRetos} />
            )}
          </div>
        </div>
      </div>
      <div className="col-12  md:col-6 ">
        <div className="col-12 p-1">
          <div className="card mt-3 border-round-3xl   ">
            <div className="flex justify-content-between">
              <div className="flex justify-content-start ">
                <div ref={noticiasMias} className="text-900 text-2xl text-blue-600 font-medium ">
                  Últimas noticias
                </div>
              </div>

              {ecosistemaEntity.user?.login === props.logueado.login &&
              props.logueado?.authorities?.find(rol => rol === 'ROLE_ADMINECOSISTEMA') ? (
                <div className="flex justify-content-end ">
                  <InputSwitch checked={misNoticias} onChange={e => actualizarMisNoticias(e)} />
                </div>
              ) : null}
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

      <div className="col-12  md:col-6 ">
        <div className="col-12 p-1">
          <div className="card mt-4 border-round-3xl  sm:h-auto">
            <div className="flex justify-content-start ">
              <div className="text-900 text-2xl text-blue-600 font-medium ">Proyectos</div>
            </div>
            <Link to={`/entidad/proyectos/proyectos_ecosistema/${props.id}/${props.index}`}>
              <div className="flex justify-content-start">
                <div className="text-400  text-blue-800 font-medium ">Ver todos los Proyectos</div>
              </div>
            </Link>
            <ScrollPanel style={{ width: '100%', height: '650px' }}>
              <VistaPrincipalProyectoEcosistema id={props.id} index={props.index} />
            </ScrollPanel>
          </div>
        </div>
      </div>
    </div>
  );
}

export default VistaGeneralEcositema1;
