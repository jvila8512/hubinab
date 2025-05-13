import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import LikeIdea from './like-idea';
import LikeIdeaDetail from './like-idea-detail';
import LikeIdeaUpdate from './like-idea-update';
import LikeIdeaDeleteDialog from './like-idea-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={LikeIdeaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={LikeIdeaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={LikeIdeaDetail} />
      <ErrorBoundaryRoute path={match.url} component={LikeIdea} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={LikeIdeaDeleteDialog} />
  </>
);

export default Routes;
