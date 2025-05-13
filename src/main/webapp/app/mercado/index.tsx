import React from 'react';
import { Switch } from 'react-router-dom';
import PrivateRoute from 'app/shared/auth/private-route';
import { AUTHORITIES } from 'app/config/constants';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

const Routes = ({ match }) => (
  <div>
    <Switch></Switch>
  </div>
);
export default Routes;
