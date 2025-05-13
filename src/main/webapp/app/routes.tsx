import React from 'react';
import { Switch } from 'react-router-dom';
import Loadable from 'react-loadable';

import Login from 'app/modules/login/login';
import Register from 'app/modules/account/register/register';
import Activate from 'app/modules/account/activate/activate';
import PasswordResetInit from 'app/modules/account/password-reset/init/password-reset-init';
import PasswordResetFinish from 'app/modules/account/password-reset/finish/password-reset-finish';
import Logout from 'app/modules/login/logout';
import Home from 'app/modules/home/home';
import EntitiesRoutes from 'app/entities/routes';
import EntitiesRoutesAdmin from 'app/entities/routesAdmin';
import PrivateRoute from 'app/shared/auth/private-route';
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';
import PageNotFound from 'app/shared/error/page-not-found';
import { AUTHORITIES } from 'app/config/constants';
import Innovacion from 'app/innovacion';
import InnovacionAbierta from 'app/innovacionAbierta';
import Administrador from './administrador/administrador';
import AdminEcosistema from './adminEcosistema/adminEcosistema';
import Gestor from './gestor/gestor';
import Usuario from './usuario/usuario';
import Ecosistema from './innovacion/ecosistema';
import { InnovacionAbiertaMenu, InnovacionMenu } from './shared/layout/menus';
import Comunidad from 'app/comunidad';
import Noticias from 'app/entities/noticias/noticiasVista';
import ContactoUpdatePrincipal from './entities/contacto/contactoPrincipal';
import BusquedaGeneral from './usuario/busquedaGeneral';

const loading = <div>loading ...</div>;

const Account = Loadable({
  loader: () => import(/* webpackChunkName: "account" */ 'app/modules/account'),
  loading: () => loading,
});

const Admin = Loadable({
  loader: () => import(/* webpackChunkName: "administration" */ 'app/modules/administration'),
  loading: () => loading,
});

const Routes = () => {
  return (
    <div className="view-routes">
      <Switch>
        <ErrorBoundaryRoute path="/login" component={Login} />
        <ErrorBoundaryRoute path="/logout" component={Logout} />
        <ErrorBoundaryRoute path="/account/register" component={Register} />
        <ErrorBoundaryRoute path="/account/activate/:key?" component={Activate} />
        <ErrorBoundaryRoute path="/account/reset/request" component={PasswordResetInit} />
        <ErrorBoundaryRoute path="/account/reset/finish/:key?" component={PasswordResetFinish} />
        <PrivateRoute path="/admin" component={Admin} hasAnyAuthorities={[AUTHORITIES.ADMIN]} />
        <PrivateRoute path="/account" component={Account} />
        <ErrorBoundaryRoute path="/innovacion" component={Innovacion} />
        <ErrorBoundaryRoute path="/innovacion-abierta" component={InnovacionAbierta} />
        <ErrorBoundaryRoute path="/comunidad" component={Comunidad} />
        <PrivateRoute path="/usuario-panel/:index" component={Usuario} />
        <PrivateRoute path="/usuario-panel" component={Usuario} />
        <PrivateRoute path="/buscar/:orden/:texto" component={BusquedaGeneral} />
        <PrivateRoute path="/nomencladores" component={EntitiesRoutesAdmin} hasAnyAuthorities={[AUTHORITIES.ADMIN]} />

        <ErrorBoundaryRoute path="/entidad/noticias/ver-noticias/:id" component={Noticias} />
        <ErrorBoundaryRoute path="/entidad/contacto/contactar" component={ContactoUpdatePrincipal} />
        <PrivateRoute path="/entidad" component={EntitiesRoutes} />
        <ErrorBoundaryRoute path="/" exact component={Home} />

        <ErrorBoundaryRoute path="/" component={PageNotFound} />
      </Switch>
    </div>
  );
};

export default Routes;
