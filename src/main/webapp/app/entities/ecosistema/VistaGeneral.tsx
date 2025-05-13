import { useAppDispatch, useAppSelector } from 'app/config/store';
import { Button } from 'primereact/button';
import { Toolbar } from 'primereact/toolbar';
import React, { useEffect, useRef, useState } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import VistaPrincipalNoticia from '../noticias/vistaPrincipalNoticiasEcosistema';
import VistaPrincipalProyectoEcosistema from '../proyectos/vistaPrincipalProyectoEcosistema';
import VistaPrincipal from '../reto/vistaPrincipalRetoEcosistema';
import { getEntity } from './ecosistema.reducer';
import { contarRetosbyEcosistemas } from '../reto/reto.reducer';
import { contarNoticiasbyEcosistemas } from '../noticias/noticias.reducer';
import { contarProyectosbyEcosistemas } from '../proyectos/proyectos.reducer';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import VistaComponente from '../ecosistema-componente/vistaComponente';
import ExpandableTableComponente from '../ecosistema-componente/expandableTableComponente';
import { Skeleton } from 'primereact/skeleton';
import { InputSwitch } from 'primereact/inputswitch';
import VistaPrincipalRetoGeneral from '../reto/vistaPrincipalRetoGeneral';
import VistaPrincipalNoticiaSolo from '../noticias/vistaPrincipalNoticiasEcosistemaSolo';
import { getEntitiesByEcosistemaIdbyActivoDeTodoslosEcosistemasUSerLogueado } from 'app/entities/reto/reto.reducer';
import { Avatar } from 'primereact/avatar';

import { faEyeSlash, faNewspaper } from '@fortawesome/free-solid-svg-icons';
import ComponenteCarusel from '../ecosistema-componente/componenteCarusel';
import { Row } from 'reactstrap';
import VistaPrincipalGeneralNoticias from '../noticias/vistaPrincipalGeneralNoticias';
import { ScrollPanel } from 'primereact/scrollpanel';
import VistaGeneralProyectoEcosistema from '../proyectos/vistaGeneralProyectoEcosistema';

function VistaGeneral(props) {
  const dispatch = useAppDispatch();
  const ecosistemaEntity = useAppSelector(state => state.ecosistema.entity);
  const retoList = useAppSelector(state => state.reto.entities);
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
      <div className="col-12  md:col-6">
        <div className="col-12 ">
          <div className="card mt-3 border-round-3xl   ">
            <div className="flex justify-content-between">
              <div className="flex justify-content-start ">
                <div ref={retosMio} className="text-900 text-2xl text-blue-600 font-medium ">
                  Últimos retos activos
                </div>
              </div>
            </div>

            <ScrollPanel style={{ width: '100%', height: '650px' }}>
              <VistaPrincipalRetoGeneral id={props.id} index={props.index} usuariologeado={props.logueado} milista={props.miLista} />
            </ScrollPanel>
          </div>
        </div>
      </div>

      <div className="col-12  md:col-6">
        <div className="col-12 ">
          <div className="card mt-3 border-round-3xl   ">
            <div className="flex justify-content-between">
              <div className="flex justify-content-start ">
                <div ref={noticiasMias} className="text-900 text-2xl text-blue-600 font-medium ">
                  Últimas noticias
                </div>
              </div>
            </div>

            <ScrollPanel style={{ width: '100%', height: '650px' }}>
              <VistaPrincipalGeneralNoticias
                id={props.id}
                layout="list"
                index={props.index}
                usuariologeado={props.logueado}
                milista={props.miLista}
              />
            </ScrollPanel>
          </div>
        </div>
      </div>

      <div className="col-12  md:col-6">
        <div className="col-12 ">
          <div className="card mt-3 border-round-3xl   ">
            <div className="flex justify-content-start ">
              <div className="text-900 text-2xl text-blue-600 font-medium ">Proyectos</div>
            </div>
            <ScrollPanel style={{ width: '100%', height: '650px' }}>
              <VistaGeneralProyectoEcosistema id={props.id} milista={props.miLista} index={props.index} />
            </ScrollPanel>
          </div>
        </div>
      </div>
    </div>
  );
}

export default VistaGeneral;
