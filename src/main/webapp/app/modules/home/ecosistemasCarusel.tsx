import React, { useEffect, useState } from 'react';
import { Carousel } from 'primereact/carousel';
import { Button } from 'primereact/button';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntitiesCarrusel } from 'app/entities/ecosistema/ecosistema.reducer';
import { Link } from 'react-router-dom';
import { getEntities as getUsuarioEcosistema, getEntityByUserId } from 'app/entities/usuario-ecosistema/usuario-ecosistema.reducer';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import SkaletonEcositemas from './SkaletonEcositemas';

const EcosistemasCarusel = props => {
  const dispatch = useAppDispatch();

  const ecosistemaList = useAppSelector(state => state.ecosistema.entities);
  const loading = useAppSelector(state => state.ecosistema.loading);
  const [reto, setReto] = useState(0);
  const [noticia, setNoticias] = useState(0);
  const [proyecto, setProyectos] = useState(0);

  const account = useAppSelector(state => state.authentication.account);
  const usuarioEcosistema = useAppSelector(state => state.usuarioEcosistema.entity);

  const responsiveOptions = [
    {
      breakpoint: '1024px',
      numVisible: 2,
      numScroll: 2,
    },
    {
      breakpoint: '600px',
      numVisible: 2,
      numScroll: 2,
    },
    {
      breakpoint: '480px',
      numVisible: 1,
      numScroll: 1,
    },
  ];

  useEffect(() => {
    dispatch(getEntitiesCarrusel());
    if (account?.login) dispatch(getEntityByUserId(account?.id));
  }, []);

  const productTemplate = ecosistema => {
    return (
      <div className="product-item ml-2 mb-2">
        <div className="product-item-content ">
          <Link to={`ecosistema/vistaprincipal/${ecosistema.id}`} id="jh-tre" data-cy="ecosistemas">
            <div className="mb-3">
              <img
                src={`content/uploads/${ecosistema.logoUrlContentType}`}
                className="w-full h-14rem  shadow-2  sm:w-full sm:14rem md:w-10rem md:h-8rem"
              />
              <img src={`data:${ecosistema.logoUrlContentType};base64,${ecosistema.logoUrl}`} style={{ width: '300px', height: '200px' }} />
            </div>
          </Link>
          <div className="mb-3 font-bold text-2xl">
            <span className="  text-blue-600">{ecosistema.nombre}</span>
          </div>

          <div>
            <h6 className="mb-3 mt-3">{ecosistema.tematica}</h6>

            <div className="align-items-end ">
              <i className="pi pi-globe mr-2"></i>
              <span> {ecosistema.retosCant} Retos</span>
            </div>
          </div>
        </div>
      </div>
    );
  };
  const buscarUsuarioEcositema = u => {
    const usuario = usuarioEcosistema?.ecosistemas?.find(it => it.id === u.id);

    if (usuario) return true;
    else return false;
  };

  const buscarÎndexUsuarioEcositema = u => {
    const index = usuarioEcosistema?.ecosistemas?.findIndex(it => it.id === u.id);

    if (index) return index;
    else return 0;
  };

  return (
    <>
      {loading ? (
        <SkaletonEcositemas />
      ) : ecosistemaList && ecosistemaList.length > 0 ? (
        <div className="surface-section   px-1 py-4 md:px-6 ">
          <div className="mb-3 font-bold text-900 text-2xl sm:text-5xl  ">
            <span className="text-blue-600">Ecosistemas</span>
          </div>

          <div className="flex  grid">
            {ecosistemaList.map((ecosistema, i) => (
              <div key={ecosistema.id} className=" col-12 md:col-4 mb-4 px-5 ">
                {account?.login && buscarUsuarioEcositema(ecosistema) ? (
                  <Link to={`/usuario-panel/${buscarÎndexUsuarioEcositema(ecosistema)}`} id="jh-tre" data-cy="ecosistemas">
                    <div className="flex flex-column align-items-center gap-3 py-5">
                      <img src={`content/uploads/${ecosistema.logoUrlContentType}`} style={{ maxHeight: '200px' }} />
                    </div>
                  </Link>
                ) : (
                  <Link to={`/entidad/ecosistema/card`} id="jh-tre" data-cy="ecosistemas">
                    <div className="flex flex-column align-items-center gap-3 py-5">
                      <img src={`content/uploads/${ecosistema.logoUrlContentType}`} style={{ maxHeight: '200px' }} />
                    </div>
                  </Link>
                )}

                <div className="mb-3 font-bold  text-xl sm:text-1xl">
                  <span className="  text-blue-600">{ecosistema.nombre}</span>
                </div>

                <div>
                  <div className=" surface-overlay w-full h-3rem mb-2  overflow-hidden text-overflow-ellipsis">{ecosistema.tematica}</div>

                  <div className="align-items-end ">
                    <i className="pi pi-globe mr-2"></i>
                    <span> {ecosistema.retosCant} Retos</span>
                  </div>
                </div>
              </div>
            ))}
          </div>
        </div>
      ) : null}
    </>
  );
};

export default EcosistemasCarusel;
