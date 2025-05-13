import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ContactoServicio from './contacto-servicio';
import ContactoServicioDetail from './contacto-servicio-detail';
import ContactoServicioUpdate from './contacto-servicio-update';
import ContactoServicioDeleteDialog from './contacto-servicio-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ContactoServicioUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ContactoServicioUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ContactoServicioDetail} />
      <ErrorBoundaryRoute path={match.url} component={ContactoServicio} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ContactoServicioDeleteDialog} />
  </>
);

export default Routes;
