import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ComenetariosIdea from './comenetarios-idea';
import ComenetariosIdeaDetail from './comenetarios-idea-detail';
import ComenetariosIdeaUpdate from './comenetarios-idea-update';
import ComenetariosIdeaDeleteDialog from './comenetarios-idea-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ComenetariosIdeaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ComenetariosIdeaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ComenetariosIdeaDetail} />
      <ErrorBoundaryRoute path={match.url} component={ComenetariosIdea} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ComenetariosIdeaDeleteDialog} />
  </>
);

export default Routes;
