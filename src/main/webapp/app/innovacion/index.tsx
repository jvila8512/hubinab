import React from 'react';
import { Switch } from 'react-router-dom';
import PrivateRoute from 'app/shared/auth/private-route';
import { AUTHORITIES } from 'app/config/constants';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';
import Ecosistema from './ecosistema';

const Routes = ({ match }) => (
  <div>
    <Switch>
      <ErrorBoundaryRoute path="/innovacion/ecosistema" component={Ecosistema} />
    </Switch>
  </div>
);
export default Routes;
