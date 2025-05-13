import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import EcosistemaRol from './ecosistema-rol';
import EcosistemaRolDetail from './ecosistema-rol-detail';
import EcosistemaRolUpdate from './ecosistema-rol-update';
import EcosistemaRolDeleteDialog from './ecosistema-rol-delete-dialog';
import EcosistemaRolCRUD from './EcosistemaRolCrud';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EcosistemaRolUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/rol-crud`} component={EcosistemaRolCRUD} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EcosistemaRolUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EcosistemaRolDetail} />
      <ErrorBoundaryRoute path={match.url} component={EcosistemaRol} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={EcosistemaRolDeleteDialog} />
  </>
);

export default Routes;
