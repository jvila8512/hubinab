import './home.scss';

import React from 'react';
import { Link, Redirect } from 'react-router-dom';
import { Translate } from 'react-jhipster';
import { Row, Col, Alert } from 'reactstrap';

import { Button } from 'primereact/button';
import { InputText } from 'primereact/inputtext';

import { useAppSelector } from 'app/config/store';
import EcosistemasCarusel from './ecosistemasCarusel';
import ChangeMacker from './changeMacker';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import NoticiasCarusel from './NoticiasCarusel';
import ServiciosCarrousel from './serviciosCarrousel';

export const Home = () => {
  const account = useAppSelector(state => state.authentication.account);
  const idea = () => {
    alert('Holaaaaaaaaaa');

    <Redirect to={'/idea'} />;
  };
  return (
    <>
      <Row>
        <div className="fondo">
          <div className="grid ">
            <div className="col-12 md:col-6 p-6 text-center md:text-left flex align-items-center "></div>

            <div className="col-12 md:col-6   ">
              <h1 className="text-6xl font-bold text-gray-900 line-height-2 mt-5">
                <span className="font-light block">Ideas creativas y Oportunidades</span>
              </h1>
              <p className="font-normal text-2xl line-height-3 md:mt-3 text-gray-700">
                Ayudar a monetizar TÚ talento es nuestro propósito.{' '}
              </p>

              <div className="mt-10 ">
                <Link to={`/reto`} className="btn btn-primary rounded-pill m-3 " id="jh-tre" data-cy="reto">
                  <span className="  text-2xl p-2">Lanza Tú Reto</span>
                  <i className="pi pi-comment" style={{ fontSize: '2em' }}></i>
                </Link>

                <Link to={`/idea`} className="btn btn-primary rounded-pill " id="jh-tre" data-cy="ideas">
                  <span className=" text-2xl p-2">Aporta Tú Idea</span>
                  <i className="pi pi-comment" style={{ fontSize: '2em' }}></i>
                </Link>
              </div>
            </div>
          </div>
        </div>
      </Row>

      <Row className="mb-8">
        {account?.login ? (
          <div></div>
        ) : (
          <Link to="/account/register" className="font-bold px-5 py-3 p-button-raised p-button-rounded white-space-nowrap">
            <span className="d-none d-md-inline">Registrate</span>
          </Link>
        )}
      </Row>

      <Row>
        <EcosistemasCarusel />
      </Row>

      <Row>
        <NoticiasCarusel />
      </Row>

      <Row>
        <div className="surface-0 text-center p-3">
          <ChangeMacker />
        </div>
      </Row>

      <Row>
        <div className="surface-0 text-center p-3">
          <ServiciosCarrousel />
        </div>
      </Row>

      <Row>
        <div className="surface-0 text-center p-3">
          <div className="mb-3 font-bold text-2xl">
            <span className="text-blue-600">Patrocinadores</span>
          </div>
          <div className="text-700 text-sm mb-6">Ac turpis egestas maecenas pharetra convallis posuere morbi leo urna.</div>
          <div className="grid">
            <div className="col-12 md:col-3 mb-3 px-5">
              <span className="gobierno"></span>

              <div className="text-900 mb-3 font-medium">Gobierno Provincial</div>
              <span className="text-700 text-sm line-height-3">
                Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.
              </span>
            </div>

            <div className="col-12 md:col-3 mb-3 px-5">
              <span className="anir"></span>

              <div className="text-900 mb-3 font-medium">ANIR</div>
              <span className="text-700 text-sm line-height-3">
                Risus nec feugiat in fermentum posuere urna nec. Posuere sollicitudin aliquam ultrices sagittis.
              </span>
            </div>
            <div className="col-12 md:col-3 mb-3 px-5">
              <span className="uic"></span>

              <div className="text-900 mb-3 font-medium">Unión de Informáticos de Cuba</div>
              <span className="text-700 text-sm line-height-3">
                Ornare suspendisse sed nisi lacus sed viverra tellus. Neque volutpat ac tincidunt vitae semper.
              </span>
            </div>

            <div className="col-12 md:col-3 mb-3 px-5">
              <span className="universidad"></span>

              <div className="text-900 mb-3 font-medium">Universidad de Las Tunas</div>
              <span className="text-700 text-sm line-height-3">
                Fermentum et sollicitudin ac orci phasellus egestas tellus rutrum tellus.
              </span>
            </div>
          </div>
        </div>
      </Row>
    </>
  );
};

export default Home;
