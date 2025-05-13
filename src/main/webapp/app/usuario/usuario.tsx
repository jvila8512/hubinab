import { Avatar } from 'primereact/avatar';
import React, { useEffect, useRef, useState } from 'react';
import { Alert } from 'reactstrap';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { Link, RouteComponentProps } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import TodosCard from 'app/entities/ecosistema/todosCard';
import { TabView, TabPanel } from 'primereact/tabview';
import { ScrollPanel } from 'primereact/scrollpanel';

import { getEntity, getEntityByUserId } from 'app/entities/usuario-ecosistema/usuario-ecosistema.reducer';
import { getEntity as getEntityEcosistema, encontrarUserEnEcosistemas } from 'app/entities/ecosistema/ecosistema.reducer';
import { getArchivo } from 'app/entities/Files/files.reducer';
import { updateUser, reset as resetUser, getUsersPorRol } from 'app/modules/administration/user-management/user-management.reducer';

import { Translate, getUrlParameter } from 'react-jhipster';
import { Toolbar } from 'primereact/toolbar';
import { InputText } from 'primereact/inputtext';
import { Badge } from 'primereact/badge';
import EcositemasCardUser from 'app/entities/ecosistema/ecositemasCardUser';
import { TieredMenu } from 'primereact/tieredmenu';
import { toast } from 'react-toastify';
import { Menu } from 'primereact/menu';
import { Toast } from 'primereact/toast';
import { SplitButton } from 'primereact/splitbutton';
import VistaGeneralEcositema1 from 'app/entities/ecosistema/VistaGeneralEcositema1';
import MenuUsuario from './menu';
import { Skeleton } from 'primereact/skeleton';
import SkeletonEcositemas from './SkeletonEcositemas';
import MenuUsuarioNotificaciones from './menu-notificaciones ';
import { reset as resetNoti } from 'app/entities/notificacion/notificacion.reducer';
import { getEntitiesByEcosistematodasFiltrarFechasSinRespuesta, reset as resetRetos } from 'app/entities/reto/reto.reducer';
import VistaGeneral from 'app/entities/ecosistema/VistaGeneral';
import { Button } from 'primereact/button';
import './ocultar.css';

const Usuario = (props: RouteComponentProps<{ index: any }>) => {
  const dispatch = useAppDispatch();
  const account = useAppSelector(state => state.authentication.account);
  const usuarioEcosistema = useAppSelector(state => state.usuarioEcosistema.entity);
  const success = useAppSelector(state => state.usuarioEcosistema.success);
  const loading = useAppSelector(state => state.usuarioEcosistema.loading);
  const [texto, setTexto] = useState('');
  const [texto1, setTexto1] = useState('');

  const [imagenRetos, setImagenRetos] = useState(null);
  const [imagenNoticias, setImagenNoticias] = useState(null);
  const [imagenProyectos, setImagenProyectos] = useState(null);

  const [activeIndex, setActiveIndex] = useState(props.match.params.index ? parseInt(props.match.params.index, 10) + 1 : 1);

  const [index, setIndex] = useState(parseInt(props.match.params.index, 10));

  const comunidadList = useAppSelector(state => state.comunidad.entities);

  const [miLista, setMiLista] = useState([]);

  const toast1 = useRef(null);
  const [existeUserEcosistema, setexisteUserEcosistema] = useState(false);

  useEffect(() => {
    dispatch(getEntityByUserId(account.id));
  }, []);

  useEffect(() => {
    if (!loading) {
      const myList = usuarioEcosistema.ecosistemas?.map(usuario => usuario.id) || [];
      setMiLista(myList);
    }
  }, [loading]);
  const rightToolbarTemplate = () => {
    return (
      <div className="flex  ">
        <MenuUsuarioNotificaciones id={1} index={1} usuariologeado={account} />
      </div>
    );
  };
  const leftToolbarTemplate = () => {
    return (
      <div className="flex justify-content-start">
        <MenuUsuario account1={account} />
      </div>
    );
  };
  const tabHeaderITemplate = (options, i) => {
    return (
      <div
        className="flex align-items-center border-round-2xl shadow-4 pt-2"
        style={{ cursor: 'pointer' }}
        onClick={e => setActiveIndex(i)}
      >
        <Avatar image={`content/uploads/${options.logoUrlContentType}`} shape="circle" className="ml-2" />
        <h5 className="text-700 text-xs  sm:text-md text-blue-600 font-medium  p-2 "> {options.nombre}</h5>
      </div>
    );
  };
  const tabHeaderITemplate1 = (options, i) => {
    return (
      <div
        className="flex align-items-center border-round-2xl shadow-4 pt-2"
        style={{ cursor: 'pointer' }}
        onClick={e => setActiveIndex(i)}
      >
        <h5 className="text-700 text-xs  sm:text-md text-blue-600 font-medium  p-2 "> {options}</h5>
      </div>
    );
  };
  const comunidad = () => {
    const comuni = comunidadList[0];

    window.open(`${comuni.link}`, '_blank');
  };

  const items = [
    {
      label: 'Palabra Clave',
      icon: 'pi pi-search',
      // eslint-disable-next-line object-shorthand
      command: () => {
        if (texto) props.history.push(`/buscar/clave/${texto}`);
        else toast.info('Escriba la palabra clave');
      },
    },
    {
      label: 'Prioridad',
      icon: 'pi pi-search',
      // eslint-disable-next-line object-shorthand
      command: () => {
        props.history.push(`/buscar/prioridad/pr`);
      },
    },
    {
      label: 'ODS',
      icon: 'pi pi-search',
      // eslint-disable-next-line object-shorthand
      command: () => {
        props.history.push(`/buscar/ods/ods`);
      },
    },
    {
      label: 'Sector',
      icon: 'pi pi-search',
      // eslint-disable-next-line object-shorthand
      command: () => {
        props.history.push(`/buscar/sector/sc`);
      },
    },
  ];

  return (
    <div className="card p-2 mt-2 mb-2">
      <Toast ref={toast1}></Toast>
      <Toolbar className="mt-1 flex flex-row bg-gray-200" left={leftToolbarTemplate} right={rightToolbarTemplate}></Toolbar>

      <div className="flex flex-column sm:flex-row mt-2 mb-4 ">
        <div className="flex justify-content-start">
          <h5 className="text-900 text-2xl text-blue-600 font-medium mt-2 mb-2 ml-4">Ecosistemas</h5>

          <Button
            onClick={comunidad}
            icon="pi  pi-globe"
            className="p-button-rounded p-button-info p-button-text scalein animation-duration-2000 animation-iteration-infinite"
            aria-label="User"
            tooltip="Comunidad de Innovación"
          ></Button>
        </div>
        <div className="flex justify-content-end ml-auto">
          <div className=" p-inputgroup  ">
            <InputText value={texto} placeholder="Buscar por palabra clave" onChange={e => setTexto(e.target.value)} />

            <SplitButton label="" model={items} className="ocultar"></SplitButton>
          </div>
        </div>
      </div>

      {!usuarioEcosistema?.ecosistemas ? (
        <SkeletonEcositemas />
      ) : usuarioEcosistema?.ecosistemas?.length > 0 ? (
        <div className="grid p-0">
          <div className="col-12  mb-4 p-0">
            <TabView className="p-0" activeIndex={activeIndex} onTabChange={e => setActiveIndex(e.index)} scrollable>
              <TabPanel
                key={`card-0}`}
                header="General"
                headerTemplate={tabHeaderITemplate1('Información General', 0)}
                headerClassName="flex align-items-center p-2"
                className="p-0"
              >
                <VistaGeneral miLista={miLista} logueado={account} index={0} />
              </TabPanel>

              {usuarioEcosistema.ecosistemas?.map(
                (usuario, i) =>
                  usuario.activo && (
                    <TabPanel
                      key={`card-${i + 1}`}
                      header={usuario.nombre}
                      headerTemplate={tabHeaderITemplate(usuario, i + 1)}
                      headerClassName="flex align-items-center p-2"
                      className="!p-0"
                    >
                      <ScrollPanel style={{ width: '100%', height: '1000px' }}>
                        <VistaGeneralEcositema1 id={usuario.id} logueado={account} index={i} />
                      </ScrollPanel>
                    </TabPanel>
                  )
              )}
            </TabView>
          </div>
        </div>
      ) : (
        <div className="alert alert-warning">
          No esta asociado a ningún ecosistema de la plataforma
          <Link to={`/entidad/ecosistema/card`} className="btn btn-primary rounded-pill m-3 " id="jh-tre" data-cy="reto">
            <span className="font-white  p-2">Unirse a un Ecosistema</span>
          </Link>
        </div>
      )}
    </div>
  );
};
export default Usuario;
