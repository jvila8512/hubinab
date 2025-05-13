import './header.scss';

import React, { useEffect, useState } from 'react';
import { Translate, Storage, ValidatedForm } from 'react-jhipster';
import { Navbar, Nav, NavbarToggler, Collapse } from 'reactstrap';
import LoadingBar from 'react-redux-loading-bar';

import { Home, Brand, Panel } from './header-components';
import {
  AdminMenu,
  EntitiesMenu,
  AccountMenu,
  LocaleMenu,
  InnovacionMenu,
  DesafioMenu,
  Mercado,
  InnovacionAbiertaMenu,
  EntidadesAdmin,
} from '../menus';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { setLocale } from 'app/shared/reducers/locale';
import Comunidad from '../menus/comunidad';
import { InputText } from 'primereact/inputtext';
import { Button } from 'primereact/button';
import { Link, Redirect } from 'react-router-dom';
import { values } from 'lodash';
import { Avatar } from 'primereact/avatar';
import { hasAnyAuthority } from 'app/shared/auth/private-route';
import { AUTHORITIES } from 'app/config/constants';
import EntitiesMenuAdmin from 'app/entities/menuAdmin';

export interface IHeaderProps {
  isAuthenticated: boolean;
  isAdmin: boolean;
  ribbonEnv: string;
  isInProduction: boolean;
  isOpenAPIEnabled: boolean;
  currentLocale: string;
}

const Header = (props: IHeaderProps) => {
  const [menuOpen, setMenuOpen] = useState(false);

  const [texto, setTexto] = useState('/');

  const dispatch = useAppDispatch();
  const isAdmin = useAppSelector(state => hasAnyAuthority(state.authentication.account.authorities, [AUTHORITIES.ADMIN]));
  const isAdminEcosistema = useAppSelector(state =>
    hasAnyAuthority(state.authentication.account.authorities, [AUTHORITIES.ADMINECOSISTEMA])
  );
  const account = useAppSelector(state => state.authentication.account);
  const isGestorEcosistema = useAppSelector(state => hasAnyAuthority(state.authentication.account.authorities, [AUTHORITIES.GESTOR]));
  const isUser = useAppSelector(state => hasAnyAuthority(state.authentication.account.authorities, [AUTHORITIES.USER]));
  const isAuthenticated1 = useAppSelector(state => state.authentication.isAuthenticated);

  useEffect(() => {
    if (isAuthenticated1) {
      setTexto('/usuario-panel');
    }
  }, [isAuthenticated1]);

  const handleLocaleChange = event => {
    const langKey = event.target.value;
    Storage.session.set('locale', langKey);
    dispatch(setLocale(langKey));
  };

  const buscar = () => {};

  const renderDevRibbon = () =>
    props.isInProduction === false ? (
      <div className="ribbon dev">
        <a href="">
          <Translate contentKey={`global.ribbon.${props.ribbonEnv}`} />
        </a>
      </div>
    ) : null;

  const toggleMenu = () => setMenuOpen(!menuOpen);

  /* jhipster-needle-add-element-to-menu - JHipster will add new menu items here */

  return (
    <div id="app-header">
      {renderDevRibbon()}
      <LoadingBar className="loading-bar" />

      <Navbar bg="light" data-cy="navbar" dark container="fluid" expand="xl" fixed="top" className="jh-navbar mr-auto">
        <NavbarToggler aria-label="Menu" onClick={toggleMenu} className="me-2" />
        <Brand />

        <Collapse isOpen={menuOpen} navbar className="">
          <Nav id="header-tabs" className="" navbar>
            <Home on={toggleMenu} />
            {<InnovacionMenu on={toggleMenu} />}
            {<InnovacionAbiertaMenu on={toggleMenu} />}
            {<Comunidad on={toggleMenu} />}
            {<Mercado on={toggleMenu} />}
            {props.isAuthenticated && isAdmin && <EntitiesMenu on={toggleMenu} />}
            {props.isAuthenticated && isAdmin && <EntidadesAdmin on={toggleMenu} />}
            {props.isAuthenticated && props.isAdmin && <AdminMenu on={toggleMenu} showOpenAPI={props.isOpenAPIEnabled} />}
            <LocaleMenu currentLocale={props.currentLocale} onClick={handleLocaleChange} />

            <AccountMenu isAuthenticated={isAuthenticated1} on={toggleMenu} />
            {props.isAuthenticated && <Panel url={texto} on={toggleMenu} user={account} />}
          </Nav>
        </Collapse>
      </Navbar>
    </div>
  );
};

export default Header;
