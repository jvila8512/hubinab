import './home.scss';

import React, { useState } from 'react';
import { Link, Redirect, RouteComponentProps } from 'react-router-dom';
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
import { AUTHORITIES } from 'app/config/constants';
import { hasAnyAuthority } from 'app/shared/auth/private-route';
import VistaNoticia from './vistaNoticias';

export const Home = (props: RouteComponentProps<any>) => {
  const account = useAppSelector(state => state.authentication.account);
  const [texto, setTexto] = useState('');

  const isAdmin = useAppSelector(state => hasAnyAuthority(state.authentication.account.authorities, [AUTHORITIES.ADMIN]));
  const isAdminEcosistema = useAppSelector(state =>
    hasAnyAuthority(state.authentication.account.authorities, [AUTHORITIES.ADMINECOSISTEMA])
  );
  const isGestorEcosistema = useAppSelector(state => hasAnyAuthority(state.authentication.account.authorities, [AUTHORITIES.GESTOR]));
  const isUser = useAppSelector(state => hasAnyAuthority(state.authentication.account.authorities, [AUTHORITIES.USER]));
  const isAuthenticated = useAppSelector(state => state.authentication.isAuthenticated);

  const idea = () => {
    props.history.push('/usuario-panel');
  };

  const reto = () => {
    if (isAuthenticated) {
      props.history.push('/usuario-panel');
    }
  };
  return (
    <>
      <Row>
        <div
          id="hero"
          className="flex flex-column pt-1 px-4 lg:px-8 overflow-hidden"
          style={{
            background:
              'linear-gradient(0deg, rgba(255, 255, 255, 0.2), rgba(255, 255, 255, 0.2)), radial-gradient(77.36% 256.97% at 77.36% 57.52%, #EEEFAF 0%, #C3E3FA 100%)',
            clipPath: 'ellipse(150% 87% at 93% 13%)',
          }}
        >
          <div className="relative">
            <div className="sm: w-4  absolute top-5 right-0  flex align-items-end justify-content-end ">
              <div className="p-inputgroup sm:w-10">
                <InputText value={texto} placeholder="Búsqueda-Innovaciones" onChange={e => setTexto(e.target.value)} />
                {texto ? (
                  <Link to={`/entidad/innovacion-racionalizacion/buscar/${texto}`} className="btn btn-primary" onClick={e => setTexto('')}>
                    <i className="pi pi-search pt-2" style={{ fontSize: '1em' }}></i>
                  </Link>
                ) : (
                  <Link to={`#`} className="btn btn-secondary" onClick={e => setTexto('')}>
                    <i className="pi pi-search pt-2" style={{ fontSize: '1em' }}></i>
                  </Link>
                )}
              </div>
            </div>
          </div>

          <div className="mx-4 md:mx-8 mt-0 md:mt-4">
            <h1 className="text-6xl font-bold text-gray-900 line-height-2 mt-5">
              <span className="font-light block">Ideas creativas y Oportunidades</span>
            </h1>
            <p className="font-normal text-2xl line-height-3 md:mt-3 text-gray-700">Ayudar a monetizar tu talento es nuestro propósito. </p>

            <div className="mt-10 ">
              <Button
                type="button"
                onClick={reto}
                icon="pi pi-check"
                iconPos="right"
                label="Lanza Tú Reto"
                className="p-button-rounded text-xl border-none mt-3 ml-3 mb-4 bg-blue-500 font-normal line-height-3 px-3 text-white"
              ></Button>
              <Button
                type="button"
                onClick={idea}
                icon="pi pi-check"
                iconPos="right"
                label="Aporta Tú Idea"
                className="p-button-rounded text-xl border-none mt-3 ml-3 mb-4 bg-blue-500 font-normal line-height-3 px-3 text-white"
              ></Button>
            </div>
            {account?.login ? (
              <div></div>
            ) : (
              <Link to="/account/register" className="font-bold px-5 py-3 p-button-raised p-button-rounded white-space-nowrap">
                <span className="d-none d-md-inline text-2xl">Registrate</span>
              </Link>
            )}
          </div>
          <div className="flex justify-content-center md:justify-content-end">
            <div className="fondo"></div>
          </div>
        </div>
      </Row>

      <Row>
        <div className="surface-0 text-center p-3">
          <EcosistemasCarusel logueado={account} />
        </div>
      </Row>

      <Row>
        <div className="surface-0 text-center p-3">
          <VistaNoticia />
        </div>
      </Row>

      <Row>
        <div className="surface-0 text-center p-3">
          <ChangeMacker />
        </div>
      </Row>

      <Row>
        <div className="surface-0 p-3">
          <ServiciosCarrousel />
        </div>
      </Row>
    </>
  );
};

export default Home;
