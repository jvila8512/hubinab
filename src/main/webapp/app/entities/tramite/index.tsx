import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Tramite from './tramite';
import TramiteDetail from './tramite-detail';
import TramiteUpdate from './tramite-update';
import TramiteDeleteDialog from './tramite-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TramiteUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TramiteUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TramiteDetail} />
      <ErrorBoundaryRoute path={match.url} component={Tramite} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TramiteDeleteDialog} />
  </>
);

export default Routes;
