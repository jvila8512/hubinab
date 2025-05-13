import React from 'react';
import { Switch } from 'react-router-dom';
import PrivateRoute from 'app/shared/auth/private-route';
import { AUTHORITIES } from 'app/config/constants';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';
import Expertos from './expertos';

const Routes = ({ match }) => (
  <div>
    <Switch>
      <ErrorBoundaryRoute path="/comunidad/expertos" component={Expertos} />
    </Switch>
  </div>
);
export default Routes;
