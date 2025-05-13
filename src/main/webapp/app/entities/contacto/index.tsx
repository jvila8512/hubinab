import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Contacto from './contacto';
import ContactoDetail from './contacto-detail';
import ContactoUpdate from './contacto-update';
import ContactoDeleteDialog from './contacto-delete-dialog';
import ContactoCRUD from './contactoCrud';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ContactoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/contacto-crud`} component={ContactoCRUD} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ContactoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ContactoDetail} />

      <ErrorBoundaryRoute path={match.url} component={Contacto} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ContactoDeleteDialog} />
  </>
);

export default Routes;
