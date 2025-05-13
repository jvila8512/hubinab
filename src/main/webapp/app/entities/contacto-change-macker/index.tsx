import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ContactoChangeMacker from './contacto-change-macker';
import ContactoChangeMackerDetail from './contacto-change-macker-detail';
import ContactoChangeMackerUpdate from './contacto-change-macker-update';
import ContactoChangeMackerDeleteDialog from './contacto-change-macker-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ContactoChangeMackerUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ContactoChangeMackerUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ContactoChangeMackerDetail} />
      <ErrorBoundaryRoute path={match.url} component={ContactoChangeMacker} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ContactoChangeMackerDeleteDialog} />
  </>
);

export default Routes;
