import { useAppDispatch, useAppSelector } from 'app/config/store';
import { Button } from 'primereact/button';
import { Toolbar } from 'primereact/toolbar';
import React, { useEffect, useState } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import VistaPrincipalNoticia from '../noticias/vistaPrincipalNoticiasEcosistema';
import VistaPrincipalProyectoEcosistema from '../proyectos/vistaPrincipalProyectoEcosistema';
import VistaPrincipal from '../reto/vistaPrincipalRetoEcosistema';
import { getEntity } from './ecosistema.reducer';
import { contarRetosbyEcosistemas } from '../../entities/reto/reto.reducer';
import { contarNoticiasbyEcosistemas } from '../../entities/noticias/noticias.reducer';
import { contarProyectosbyEcosistemas } from '../../entities/proyectos/proyectos.reducer';

const VistaGeneralEcositema = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();
  const ecosistemaEntity = useAppSelector(state => state.ecosistema.entity);
  const loading = useAppSelector(state => state.ecosistema.loading);
  const updating = useAppSelector(state => state.ecosistema.updating);
  const updateSuccess = useAppSelector(state => state.ecosistema.updateSuccess);
  const account = useAppSelector(state => state.authentication.account);

  const [reto, setReto] = useState(0);
  const [noticia, setNoticias] = useState(0);
  const [proyecto, setProyectos] = useState(0);
  const [usuario, setUsurios] = useState(0);

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
    const retos = contarRetosbyEcosistemas(props.match.params.id);
    retos.then(response => {
      setReto(response.data);
    });
    const noticias = contarNoticiasbyEcosistemas(props.match.params.id);
    noticias.then(response => {
      setNoticias(response.data);
    });
    const proyectos = contarProyectosbyEcosistemas(props.match.params.id);
    proyectos.then(response => {
      setProyectos(response.data);
    });
  }, []);

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
  const rightToolbarTemplate = () => {
    return (
      <React.Fragment>
        <Button label="Nueva Idea" icon="pi pi-plus" className="p-button-info" />
      </React.Fragment>
    );
  };

  return (
    <div
      className="grid mt-4 mb-4"
      style={{
        backgroundColor: '#e2e8f0',
      }}
    >
      <div className="col-12 card">
        <Toolbar className="mb-4" left={leftToolbarTemplate}></Toolbar>
        <div className="flex align-items-center justify-content-center grid">
          <div className="col-12">
            <div className="flex align-items-center mt-3 ml-3">
              {account?.authorities?.find(rol => rol === 'ROLE_ADMINECOSISTEMA') &&
                ecosistemaEntity?.users?.find(user => user.id === account?.id) && (
                  <Link to={`/entidad/ecosistema-componente/crud/${ecosistemaEntity.id}`}>
                    <div className="flex justify-content-startml-3">
                      <div className="text-400  text-blue-800 font-medium ">Componentes</div>
                    </div>
                  </Link>
                )}

              {account?.authorities?.find(rol => rol === 'ROLE_ADMINECOSISTEMA') &&
                ecosistemaEntity?.users?.find(user => user.id === account?.id) && (
                  <Link to={`/#`}>
                    <div className="flex justify-content-start ml-3">
                      <div className="text-400  text-blue-800 font-medium "> Usuarios</div>
                    </div>
                  </Link>
                )}
            </div>
          </div>

          <div className="flex align-items-start flex-column lg:justify-content-between lg:flex-row">
            <div className="col-6 xl:col-6 sm:col-10 md:col-6">
              <div className="flex align-items-center text-700 flex-wrap">
                <div className=" flex align-items-center mt-3 ml-3 ">
                  <div
                    className="flex align-items-center justify-content-center bg-orange-100 border-round"
                    style={{ width: '2.5rem', height: '2.5rem' }}
                  >
                    <i className="pi pi-inbox text-orange-500 text-xl"></i>
                  </div>
                  <span className="ml-1"> {reto} Retos</span>
                </div>

                <div className="flex align-items-center mt-3 ml-3">
                  <div
                    className="flex align-items-center justify-content-center bg-cyan-100 border-round"
                    style={{ width: '2.5rem', height: '2.5rem' }}
                  >
                    <i className="pi pi-comment text-cyan-500 text-xl"></i>
                  </div>
                  <span className="ml-1"> {noticia} Noticias </span>
                </div>

                <div className="flex align-items-center mt-3 ml-3">
                  <div
                    className="flex align-items-center justify-content-center bg-purple-100 border-round"
                    style={{ width: '2.5rem', height: '2.5rem' }}
                  >
                    <i className="pi pi-map-marker text-purple-500 text-xl"></i>
                  </div>
                  <span className="ml-1"> {proyecto} Proyectos </span>
                </div>
              </div>
            </div>

            <div className="col-6">
              <div className="mt-3 lg:mt-0">
                <img
                  className="flex overflow-hidden lg:max-h-15rem md:max-h-18rem  sm:max-h-15rem xl:max-h-20rem"
                  src={`data:${ecosistemaEntity.logoUrlContentType};base64,${ecosistemaEntity.logoUrl}`}
                  style={{ maxHeight: '200px' }}
                />
              </div>
            </div>
          </div>

          <div className="xl:col-6 sm:col-12 md:col-12 lg:col-12">
            <div className="card mt-4 border-round-3xl shadow-6 sm:h-auto ">
              <div className="flex justify-content-start ">
                <div className="text-900 text-2xl text-blue-600 font-medium ">Últimos retos en este Ecosistema </div>
              </div>
              <Link to={`/entidad/reto/retogrid/${props.match.params.id}`}>
                <div className="flex justify-content-start">
                  <div className="text-400  text-blue-800 font-medium ">Ver todos los retos</div>
                </div>
              </Link>
              <VistaPrincipal id={props.match.params.id} />
            </div>
          </div>
          <div className="xl:col-6 sm:col-6 md:col-12">
            <div className="card mt-4 border-round-3xl shadow-6 sm:h-auto ">
              <div className="flex justify-content-start ">
                <div className="text-900 text-2xl text-blue-600 font-medium ">Últimas noticias de este Ecosistema </div>
              </div>
              <Link to={`/entidad/noticias/grid-noticias/${props.match.params.id}`}>
                <div className="flex justify-content-start">
                  <div className="text-400  text-blue-800 font-medium ">Ver todas las noticias</div>
                </div>
              </Link>
              <VistaPrincipalNoticia id={props.match.params.id} layout="list" />
            </div>
          </div>

          <div className="xl:col-6 sm:col-6 md:col-12 ">
            <div className="card mt-4 border-round-3xl shadow-6 sm:h-auto ">
              <div className="flex justify-content-start ">
                <div className="text-900 text-2xl text-blue-600 font-medium ">Proyectos de este Ecosistema </div>
              </div>
              <Link to={`/entidad/proyectos/proyectos_ecosistema/${props.match.params.id}`}>
                <div className="flex justify-content-start">
                  <div className="text-400  text-blue-800 font-medium ">Ver todos los Proyectos</div>
                </div>
              </Link>
              <VistaPrincipalProyectoEcosistema id={props.match.params.id} />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default VistaGeneralEcositema;
