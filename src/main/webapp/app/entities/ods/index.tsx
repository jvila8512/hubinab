import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Ods from './ods';
import OdsDetail from './ods-detail';
import OdsUpdate from './ods-update';
import OdsDeleteDialog from './ods-delete-dialog';
import OdsCRUD from './tipoSectorCrud';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={OdsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/ods-crud`} component={OdsCRUD} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={OdsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={OdsDetail} />
      <ErrorBoundaryRoute path={match.url} component={Ods} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={OdsDeleteDialog} />
  </>
);

export default Routes;
