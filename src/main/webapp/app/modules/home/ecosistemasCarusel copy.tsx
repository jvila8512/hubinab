import React, { useEffect } from 'react';
import { Carousel } from 'primereact/carousel';
import { Button } from 'primereact/button';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntitiesCarrusel } from 'app/entities/ecosistema/ecosistema.reducer';
import { Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

const EcosistemasCarusel = () => {
  const dispatch = useAppDispatch();

  const ecosistemaList = useAppSelector(state => state.ecosistema.entities);
  const loading = useAppSelector(state => state.ecosistema.loading);

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
  }, []);

  const productTemplate = ecosistema => {
    return (
      <div className="product-item">
        <div className="product-item-content ">
          <div className="mb-3">
            <img src={`data:${ecosistema.logoUrlContentType};base64,${ecosistema.logoUrl}`} style={{ width: '300px', height: '200px' }} />
          </div>
          <div className="mb-3 font-bold text-2xl">
            <span className="  text-blue-600">{ecosistema.nombre}</span>
          </div>
          <span className="text-gray-600">Ranking: {ecosistema.ranking}</span>

          <div>
            <h6 className="mb-3 mt-3">{ecosistema.tematica}</h6>

            <div className="  align-items-end ">
              <i className="pi pi-users mr-2"></i>
              <span> {ecosistema.usuariosCant} Usuarios</span>
            </div>
            <div className="align-items-end ">
              <i className="pi pi-globe mr-2"></i>
              <span> {ecosistema.retosCant} Retos</span>
            </div>
            <div className="align-items-end ">
              <i className="pi pi-bell mr-2"></i>
              <span> {ecosistema.ideasCant} Ideas</span>
            </div>

            <div className="car-buttons mt-5 align-items-center">
              <Button icon="pi pi-facebook px-2" className="p-button p-button-rounded mr-2" />
              <Button icon="pi pi pi-twitter px-2" className="p-button-info p-button-rounded mr-2" />
              <Button icon="pi pi pi-youtube px-2" className="p-button-help p-button-rounded" />
            </div>
          </div>
        </div>

        <Link to={`/ecosistema/card`} className="btn btn-info rounded-pill align-items-center " id="jh-tre" data-cy="ecosistemas">
          <span className="font-white ">Explorar</span>
        </Link>
      </div>
    );
  };

  return (
    <div className="carousel-demo">
      <div className="surface-10 text-700 text-center">
        <Carousel
          value={ecosistemaList}
          numVisible={3}
          numScroll={1}
          responsiveOptions={responsiveOptions}
          className=""
          autoplayInterval={4000}
          itemTemplate={productTemplate}
          header={
            <div>
              <div className="text-900 font-bold text-5xl text-blue-600 mb-3">Ecosistemas</div>
            </div>
          }
        />
      </div>
    </div>
  );
};

export default EcosistemasCarusel;
