import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Noticias from './noticias';
import NoticiasDetail from './noticias-detail';
import NoticiasUpdate from './noticias-update';
import NoticiasDeleteDialog from './noticias-delete-dialog';
import noticiasVista1 from './noticiasVista1';
import NoticiasCrud from './noticiascrud';
import VistaGridNoticias from './vistaGridNoticias';
import NoticiasCrudAdmin from './noticiascrudAdmin';
import noticiasVistaGrid from './noticiasVistaGrid';
import noticiasVistaGrid1 from './noticiasVistaGrid1';
import PrivateRouteComponent from 'app/shared/auth/private-route';
import { AUTHORITIES } from 'app/config/constants';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={NoticiasUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/ver-noticia/:id/:index`} component={noticiasVista1} />
      <ErrorBoundaryRoute exact path={`${match.url}/noticias/:id/:idecosistema/:index`} component={noticiasVistaGrid} />
      <ErrorBoundaryRoute exact path={`${match.url}/noticia/:id/:idecosistema/:index`} component={noticiasVistaGrid1} />

      <PrivateRouteComponent
        exact
        path={`${match.url}/noticias-admin`}
        component={NoticiasCrudAdmin}
        hasAnyAuthorities={[AUTHORITIES.ADMIN]}
      />

      <ErrorBoundaryRoute exact path={`${match.url}/grid-noticias/:id/:index`} component={VistaGridNoticias} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={NoticiasUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={NoticiasDetail} />
      <ErrorBoundaryRoute exact path={`${match.url}/noticias-crud/:id/:index`} component={NoticiasCrud} />
      <ErrorBoundaryRoute path={match.url} component={Noticias} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={NoticiasDeleteDialog} />
  </>
);

export default Routes;
