import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import LineaInvestigacion from './linea-investigacion';
import LineaInvestigacionDetail from './linea-investigacion-detail';
import LineaInvestigacionUpdate from './linea-investigacion-update';
import LineaInvestigacionDeleteDialog from './linea-investigacion-delete-dialog';
import LineaCRUD from './LineaCrud';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={LineaInvestigacionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/linea-crud`} component={LineaCRUD} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={LineaInvestigacionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={LineaInvestigacionDetail} />
      <ErrorBoundaryRoute path={match.url} component={LineaInvestigacion} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={LineaInvestigacionDeleteDialog} />
  </>
);

export default Routes;
