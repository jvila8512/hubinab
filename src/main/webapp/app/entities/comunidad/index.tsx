import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Comunidades from './comunidades';
import PrivateRouteComponent from 'app/shared/auth/private-route';
import { AUTHORITIES } from 'app/config/constants';

const Routes = ({ match }) => (
  <>
    <Switch>
      <PrivateRouteComponent exact path={`${match.url}/comunidades`} component={Comunidades} hasAnyAuthorities={[AUTHORITIES.ADMIN]} />
    </Switch>
  </>
);

export default Routes;
