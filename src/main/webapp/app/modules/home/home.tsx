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
  const [texto1, setTexto1] = useState('');

  const isAdmin = useAppSelector(state => hasAnyAuthority(state.authentication.account.authorities, [AUTHORITIES.ADMIN]));
  const isAdminEcosistema = useAppSelector(state =>
    hasAnyAuthority(state.authentication.account.authorities, [AUTHORITIES.ADMINECOSISTEMA])
  );
  const isGestorEcosistema = useAppSelector(state => hasAnyAuthority(state.authentication.account.authorities, [AUTHORITIES.GESTOR]));
  const isUser = useAppSelector(state => hasAnyAuthority(state.authentication.account.authorities, [AUTHORITIES.USER]));
  const isAuthenticated = useAppSelector(state => state.authentication.isAuthenticated);

  const idea = () => {
    !isAuthenticated ? props.history.push('/login') : props.history.push('/usuario-panel');
  };

  const reto = () => {
    !isAuthenticated ? props.history.push('/login') : props.history.push('/usuario-panel');
  };
  const login = () => {
    props.history.push('/login');
  };
  const registrar = () => {
    props.history.push('/account/register');
  };
  return (
    <>
      <Row>
        <div
          id="hero"
          className="flex flex-column  px-4 lg:px-8 overflow-hidden relative"
          style={{
            background:
              'linear-gradient(0deg, rgba(255, 255, 255, 0.2), rgba(255, 255, 255, 0.2)), radial-gradient(77.36% 256.97% at 77.36% 57.52%, #EEEFAF 0%, #C3E3FA 100%)',
            clipPath: 'ellipse(150% 87% at 93% 13%)',
          }}
        >
          <div className=" flex  justify-content-end sm:flex-row mt-2">
            <div className=" flex  mt-2">
              {account?.login ? (
                <div></div>
              ) : (
                <Button
                  icon="pi pi-users"
                  onClick={registrar}
                  className="p-button-rounded p-button-help p-button-outlined mx-2"
                  aria-label="USer"
                />
              )}
              {account?.login ? (
                <div></div>
              ) : (
                <Button
                  icon="pi pi-sign-in"
                  onClick={login}
                  className="p-button-rounded p-button-help p-button-outlined mx-2"
                  aria-label="Login"
                />
              )}
            </div>
          </div>
          <div className=" sm:mx-8 mt-0 md:mt-2">
            <h1 className=" text-2xl sm:text-6xl font-bold text-gray-900 line-height-2 mt-2">
              <span className="font-light block">Ideas creativas y Oportunidades</span>
            </h1>
            <p className=" text-mg   font-normal sm:text-2xl line-height-3 md:mt-3 text-gray-700">
              Ayudar a monetizar tu talento es nuestro propósito.{' '}
            </p>

            <div className=" flex flex-row mt-10">
              <Button
                type="button"
                onClick={reto}
                icon="pi pi-check"
                iconPos="right"
                label="Lanza Tú Reto"
                className=" text-xs p-button-rounded sm:text-xl border-none mt-3 ml-3 mb-3 bg-blue-500 font-normal line-height-3 px-3 text-white"
              ></Button>
              <Button
                type="button"
                onClick={idea}
                icon="pi pi-check"
                iconPos="right"
                label="Aporta Tú Idea"
                className="text-xs p-button-rounded sm:text-xl border-none mt-3 ml-3 mb-3 bg-blue-500 font-normal line-height-3 px-3 text-white"
              ></Button>
            </div>
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
    </>
  );
};

export default Home;
