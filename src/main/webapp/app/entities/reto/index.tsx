import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Reto from './reto';
import RetoDetail from './reto-detail';
import RetoUpdate from './reto-update';
import RetoDeleteDialog from './reto-delete-dialog';
import RetoIdeas from './retosIdeas';
import { useAppSelector } from 'app/config/store';
import { hasAnyAuthority } from 'app/shared/auth/private-route';
import { AUTHORITIES } from 'app/config/constants';
import VistaPrincipal from './vistaPrincipalRetoEcosistema';
import VistaReto from './VistaReto';
import VistaReto1 from './VistaReto1';
import VistaGridReto from './vistaGridRetoEcosistema';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={RetoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={RetoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={RetoDetail} />

      <ErrorBoundaryRoute exact path={`${match.url}/reto_ideas/:idreto/:idecosistema/:index`} component={VistaReto1} />
      <ErrorBoundaryRoute exact path={`${match.url}/reto_idea/:id/:index`} component={VistaReto} />
      <ErrorBoundaryRoute exact path={`${match.url}/retoecosistema/:id/:index`} component={RetoIdeas} />
      <ErrorBoundaryRoute exact path={`${match.url}/retogrid/:id/:index`} component={VistaGridReto} />

      <ErrorBoundaryRoute path={match.url} component={Reto} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={RetoDeleteDialog} />
  </>
);

export default Routes;
