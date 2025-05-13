import axios from 'axios';
import { Carousel } from 'primereact/carousel';
import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import datos from './serviciosJSON.json';

import { getEntitiesPublicado } from 'app/entities/servicios/servicios.reducer';

import { useAppDispatch, useAppSelector } from 'app/config/store';

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

  const servicios1 = useAppSelector(state => state.servicios.entities);
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
      <div className="carousel-demo">
        <div className="surface-0 text-700 text-center">
          <Carousel
            value={servicios1}
            numVisible={3}
            numScroll={1}
            responsiveOptions={responsiveOptions}
            className=""
            autoplayInterval={4000}
            itemTemplate={productTemplate}
            header={<div className="text-900 font-bold text-5xl  text-center text-blue-600 mb-3">Servicios</div>}
          />
        </div>
      </div>
    </>
  );
};

export default ServiciosCarrousel;
