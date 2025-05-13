import axios from 'axios';
import { Carousel } from 'primereact/carousel';
import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import datos from './serviciosJSON.json';

import { getEntitiesPublicado } from 'app/entities/servicios/servicios.reducer';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import SkaletonServicios from './SkaletonServicios';

const productTemplate = servicios => {
  return (
    <div className="product-item">
      <div className="product-item-content">
        <div className="mb-3">
          <img src={`data:${servicios.fotoContentType};base64,${servicios.foto}`} style={{ maxHeight: '200px' }} />
        </div>

        <div className="mb-3 font-bold text-2xl">
          <span className="  text-blue-600">{servicios.servicio}</span>
        </div>
      </div>
      <div className="align-items-center">
        <Link to={`/ecosistema/card`} className="btn btn-primary rounded-pill align-items-center" id="jh-tre" data-cy="ecosistemas">
          <span className="font-bold text-2xl p-2 font-white ">Conozca más</span>
        </Link>
      </div>
    </div>
  );
};

const ServiciosCarrousel = () => {
  const dispatch = useAppDispatch();

  const serviciosList = useAppSelector(state => state.servicios.entities);
  const loading = useAppSelector(state => state.servicios.loading);

  useEffect(() => {
    dispatch(getEntitiesPublicado());
  }, []);

  const responsiveOptions = [
    {
      breakpoint: '1024px',
      numVisible: 3,
      numScroll: 3,
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

  return (
    <>
      {loading ? (
        <SkaletonServicios />
      ) : serviciosList && serviciosList.length > 0 ? (
        <div className="surface-section px-4 py-8 md:px-6 lg:px-8 text-center">
          <div className="mb-3 font-bold text-900 text-5xl  ">
            <span className="text-blue-600">Servicios</span>
          </div>
          <div className="text-700 text-sm mb-6">Acelera tu innovación abierta a través de nuestros servicios de consultoría</div>
          <div className="flex align-items-center justify-content-center grid">
            {serviciosList.map((servicios, i) => (
              <div key={servicios.id} className=" col-12 md:col-4 mb-4 px-5 ">
                <Link to={`/servicios/serviciovista`} id="jh-tre" data-cy="ideas">
                  <div className="mb-3">
                    <img src={`data:${servicios.fotoContentType};base64,${servicios.foto}`} style={{ maxHeight: '200px' }} />
                  </div>
                </Link>
                <div className="text-900 mb-3 font-medium">{servicios.servicio}</div>
                <span className="text-700 text-sm line-height-3">{servicios.descripcion}</span>
              </div>
            ))}
          </div>
        </div>
      ) : null}
    </>
  );
};

export default ServiciosCarrousel;
