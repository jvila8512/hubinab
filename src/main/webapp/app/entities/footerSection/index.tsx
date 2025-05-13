import React from 'react';
import { Switch } from 'react-router-dom';
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';
import FooterCRUD from './footerCrud';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/footer-crud`} component={FooterCRUD} />
    </Switch>
  </>
);

export default Routes;
