import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import RedesSociales from './redes-sociales';
import RedesSocialesDetail from './redes-sociales-detail';
import RedesSocialesUpdate from './redes-sociales-update';
import RedesSocialesDeleteDialog from './redes-sociales-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={RedesSocialesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={RedesSocialesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={RedesSocialesDetail} />
      <ErrorBoundaryRoute path={match.url} component={RedesSociales} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={RedesSocialesDeleteDialog} />
  </>
);

export default Routes;
