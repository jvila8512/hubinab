import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Anirista from './anirista';
import AniristaDetail from './anirista-detail';
import AniristaUpdate from './anirista-update';
import AniristaDeleteDialog from './anirista-delete-dialog';
import AniristasCRUD from './aniristaCrud';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AniristaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/anirista-crud`} component={AniristasCRUD} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AniristaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AniristaDetail} />
      <ErrorBoundaryRoute path={match.url} component={Anirista} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={AniristaDeleteDialog} />
  </>
);

export default Routes;
