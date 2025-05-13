import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import EcosistemaPeticiones from './ecosistema-peticiones';
import EcosistemaPeticionesDetail from './ecosistema-peticiones-detail';
import EcosistemaPeticionesUpdate from './ecosistema-peticiones-update';
import EcosistemaPeticionesDeleteDialog from './ecosistema-peticiones-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EcosistemaPeticionesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EcosistemaPeticionesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EcosistemaPeticionesDetail} />
      <ErrorBoundaryRoute path={match.url} component={EcosistemaPeticiones} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={EcosistemaPeticionesDeleteDialog} />
  </>
);

export default Routes;
