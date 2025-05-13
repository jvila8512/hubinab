import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ChangeMacker from './change-macker';
import ChangeMackerDetail from './change-macker-detail';
import ChangeMackerUpdate from './change-macker-update';
import ChangeMackerDeleteDialog from './change-macker-delete-dialog';
import VistachangeMaker from './vista-changeMaker';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ChangeMackerUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/vista-changeMacker`} component={VistachangeMaker} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ChangeMackerUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ChangeMackerDetail} />
      <ErrorBoundaryRoute path={match.url} component={ChangeMacker} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ChangeMackerDeleteDialog} />
  </>
);

export default Routes;
