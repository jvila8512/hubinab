import React, { useEffect } from 'react';
import { Carousel } from 'primereact/carousel';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntities, getNoticias } from 'app/entities/noticias/noticias.reducer';
import { Link } from 'react-router-dom';

const NoticiasCarusel = () => {
  const dispatch = useAppDispatch();

  const noticiasList = useAppSelector(state => state.noticias.entities);
  const loading = useAppSelector(state => state.noticias.loading);

  const responsiveOptions = [
    {
      breakpoint: '1024px',
      numVisible: 3,
      numScroll: 3,
    },
    {
      breakpoint: '600px',
      numVisible: 1,
      numScroll: 1,
    },
    {
      breakpoint: '480px',
      numVisible: 1,
      numScroll: 1,
    },
  ];

  useEffect(() => {
    dispatch(getNoticias());
  }, []);

  const productTemplate = noticias => {
    return (
      <div className="product-item">
        <div className="product-item-content">
          <Link to={`/noticias/ver-noticias`} id="jh-tre" data-cy="ecosistemas">
            <div className="mb-3">
              <img src={`data:${noticias.urlFotoContentType};base64,${noticias.urlFoto}`} style={{ maxHeight: '200px' }} />
            </div>
          </Link>

          <div className="mb-3 font-bold text-2xl">
            <span className="  text-blue-600">{noticias.titulo}</span>
          </div>

          <div>
            <h6 className="mb-3 mt-3">{noticias.descripcion}</h6>
          </div>
        </div>
      </div>
    );
  };

  return (
    <div className="carousel-demo">
      <div className="surface-0 text-700 text-center">
        <Carousel
          value={noticiasList}
          numVisible={3}
          numScroll={1}
          responsiveOptions={responsiveOptions}
          className="border-none"
          autoplayInterval={5000}
          itemTemplate={productTemplate}
          header={<div className="text-900 font-bold text-5xl text-blue-600 mb-3">Actualidad</div>}
        />
      </div>
    </div>
  );
};

export default NoticiasCarusel;
