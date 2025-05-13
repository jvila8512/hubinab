import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Noti from './noti';
import NotiDetail from './noti-detail';
import NotiUpdate from './noti-update';
import NotiDeleteDialog from './noti-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={NotiUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={NotiUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={NotiDetail} />
      <ErrorBoundaryRoute path={match.url} component={Noti} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={NotiDeleteDialog} />
  </>
);

export default Routes;
