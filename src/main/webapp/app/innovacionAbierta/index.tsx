import React from 'react';
import { Switch } from 'react-router-dom';
import PrivateRoute from 'app/shared/auth/private-route';
import { AUTHORITIES } from 'app/config/constants';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Eventos from './eventos';
import Crowdsourcing from './crowdsourcing';
import Forum from './forum';
import Blog from './blog';

const Routes = ({ match }) => (
  <div>
    <Switch>
      <ErrorBoundaryRoute path="/innovacion-abierta/eventos" component={Eventos} />
      <ErrorBoundaryRoute path="/innovacion-abierta/crowdsourcing" component={Crowdsourcing} />
      <ErrorBoundaryRoute path="/innovacion-abierta/forum" component={Forum} />
      <ErrorBoundaryRoute path="/innovacion-abierta/blog" component={Blog} />
    </Switch>
  </div>
);
export default Routes;
