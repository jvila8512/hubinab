import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TipoIdea from './tipo-idea';
import TipoIdeaDetail from './tipo-idea-detail';
import TipoIdeaUpdate from './tipo-idea-update';
import TipoIdeaDeleteDialog from './tipo-idea-delete-dialog';
import TipoIdeaCRUD from './tipoIdeaCrud';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TipoIdeaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/tipoIdea-crud`} component={TipoIdeaCRUD} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TipoIdeaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TipoIdeaDetail} />
      <ErrorBoundaryRoute path={match.url} component={TipoIdea} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TipoIdeaDeleteDialog} />
  </>
);

export default Routes;
