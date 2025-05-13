import { Button } from 'primereact/button';
import { Avatar } from 'primereact/avatar';
import React, { useEffect, useRef, useState } from 'react';
import { Alert } from 'reactstrap';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import TodosCard from 'app/entities/ecosistema/todosCard';

import { getEntity, getEntityByUserId } from 'app/entities/usuario-ecosistema/usuario-ecosistema.reducer';
import { Translate } from 'react-jhipster';
import { Toolbar } from 'primereact/toolbar';
import { InputText } from 'primereact/inputtext';
import { Badge } from 'primereact/badge';
import EcositemasCardUser from 'app/entities/ecosistema/ecositemasCardUser';
import { TieredMenu } from 'primereact/tieredmenu';
import { toast } from 'react-toastify';
import { Menu } from 'primereact/menu';
import { Toast } from 'primereact/toast';

const Usuario = () => {
  const dispatch = useAppDispatch();
  const account = useAppSelector(state => state.authentication.account);
  const usuarioEcosistema = useAppSelector(state => state.usuarioEcosistema.entity);
  const [texto, setTexto] = useState('');

  const toast1 = useRef(null);
  useEffect(() => {
    dispatch(getEntityByUserId(account.id));
  }, []);

  const items = [
    {
      label: 'Ecosistemas',
      icon: 'pi pi-refresh',
      to: '/',
    },
    {
      label: 'Noticias',
      icon: 'pi pi-times',
    },
    {
      label: 'Proyectos',
      icon: 'pi pi-times',
    },
    {
      label: 'Retos',
      icon: 'pi pi-times',
    },
    {
      label: 'Usuarios',
      icon: 'pi pi-times',
    },
  ];

  const rightToolbarTemplate = () => {
    return (
      <div>
        <Toast ref={toast1}></Toast>
        <div className="p-inputgroup ">
          <InputText value={texto} placeholder="Búsqueda-Innovaciones" onChange={e => setTexto(e.target.value)} />
          <Link to={`/innovacion-racionalizacion/buscar/${texto}`} className="btn btn-primary" onClick={e => setTexto('')}>
            <i className="pi pi-search pt-2" style={{ fontSize: '1em' }}></i>
          </Link>
        </div>
      </div>
    );
  };
  const leftToolbarTemplate = () => {
    return (
      <div>
        <i className="pi pi-bell p-overlay-badge ml-3" style={{ fontSize: '2rem' }}>
          <Badge value="2"></Badge>
        </i>
        <i className="pi pi-calendar p-overlay-badge ml-3" style={{ fontSize: '2rem' }}>
          <Badge value="5+" severity="danger"></Badge>
        </i>
        <i className="pi pi-envelope p-overlay-badge ml-3" style={{ fontSize: '2rem' }}>
          <Badge severity="danger"></Badge>
        </i>
      </div>
    );
  };

  return (
    <>
      <div className="card mt-4 mb-4">
        <Toolbar className="mt-1" left={leftToolbarTemplate} right={rightToolbarTemplate}></Toolbar>

        <div className="grid mt-2">
          <div className="col-12 md:col-3">
            <div className="mt-2 text-center">
              <div className="card">
                <div className="card-body">
                  <img src="content/images/user.jpg" width="150" className="img-fluid rounded-circle" />
                  <h5 className="card-title">{account.firstName + ' ' + account.lastName}</h5>
                  <p className="card-text">Usuario: {account.login}</p>
                </div>
              </div>
            </div>

            <Link to={`/ecosistema/card`} className="btn btn-primary rounded-pill m-3 " id="jh-tre" data-cy="reto">
              <FontAwesomeIcon icon="plus" />
              <span className="font-white  p-2">Todos los Ecosistemas</span>
            </Link>
            <Menu model={items} />
          </div>

          <div className="col-12 md:col-9 ">
            <div className="flex align-items-center justify-content-center grid ">
              {usuarioEcosistema?.ecosistemas?.length > 0 ? (
                usuarioEcosistema.ecosistemas?.map((usuario, i) => (
                  <div key={`card-${i}`}>
                    <EcositemasCardUser ecosistema={usuario} />
                  </div>
                ))
              ) : (
                <div className="alert alert-warning">
                  No esta asociado a ningun ecosistema de la plataforma
                  <Link to={`/ecosistema/card`} className="btn btn-primary rounded-pill m-3 " id="jh-tre" data-cy="reto">
                    <span className="font-white  p-2">Todos los Ecosistemas</span>
                  </Link>
                </div>
              )}
            </div>
          </div>
        </div>
      </div>
    </>
  );
};
export default Usuario;
