import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Componentes from './componentes';
import ComponentesDetail from './componentes-detail';
import ComponentesUpdate from './componentes-update';
import ComponentesDeleteDialog from './componentes-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ComponentesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ComponentesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ComponentesDetail} />
      <ErrorBoundaryRoute path={match.url} component={Componentes} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ComponentesDeleteDialog} />
  </>
);

export default Routes;
