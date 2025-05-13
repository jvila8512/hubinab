import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CategoriaUser from './categoria-user';
import CategoriaUserDetail from './categoria-user-detail';
import CategoriaUserUpdate from './categoria-user-update';
import CategoriaUserDeleteDialog from './categoria-user-delete-dialog';
import CategoriaCRUD from './categoriaCrud';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CategoriaUserUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/categoria-crud`} component={CategoriaCRUD} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CategoriaUserUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CategoriaUserDetail} />
      <ErrorBoundaryRoute path={match.url} component={CategoriaUser} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CategoriaUserDeleteDialog} />
  </>
);

export default Routes;
