import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Servicios from './servicios';
import ServiciosDetail from './servicios-detail';
import ServiciosUpdate from './servicios-update';
import ServiciosDeleteDialog from './servicios-delete-dialog';
import serviciosVista from './serviciosVista';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ServiciosUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/serviciovista`} component={serviciosVista} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ServiciosUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ServiciosDetail} />
      <ErrorBoundaryRoute path={match.url} component={Servicios} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ServiciosDeleteDialog} />
  </>
);

export default Routes;
