import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TipoContacto from './tipo-contacto';
import TipoContactoDetail from './tipo-contacto-detail';
import TipoContactoUpdate from './tipo-contacto-update';
import TipoContactoDeleteDialog from './tipo-contacto-delete-dialog';
import TipoContactoCRUD from './tipoContactoCrud';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TipoContactoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/tipoContacto-crud`} component={TipoContactoCRUD} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TipoContactoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TipoContactoDetail} />
      <ErrorBoundaryRoute path={match.url} component={TipoContacto} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TipoContactoDeleteDialog} />
  </>
);

export default Routes;
