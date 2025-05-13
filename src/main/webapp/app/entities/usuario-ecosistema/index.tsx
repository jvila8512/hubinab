import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import UsuarioEcosistema from './usuario-ecosistema';
import UsuarioEcosistemaDetail from './usuario-ecosistema-detail';
import UsuarioEcosistemaUpdate from './usuario-ecosistema-update';
import UsuarioEcosistemaDeleteDialog from './usuario-ecosistema-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={UsuarioEcosistemaUpdate} />

      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={UsuarioEcosistemaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={UsuarioEcosistemaDetail} />
      <ErrorBoundaryRoute path={match.url} component={UsuarioEcosistema} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={UsuarioEcosistemaDeleteDialog} />
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={UsuarioEcosistemaDeleteDialog} />
  </>
);

export default Routes;
