import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TipoProyecto from './tipo-proyecto';
import TipoProyectoDetail from './tipo-proyecto-detail';
import TipoProyectoUpdate from './tipo-proyecto-update';
import TipoProyectoDeleteDialog from './tipo-proyecto-delete-dialog';
import TipoProyectoCRUD from './tipoProyectoCrud';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TipoProyectoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/tipoProyecto-crud`} component={TipoProyectoCRUD} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TipoProyectoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TipoProyectoDetail} />
      <ErrorBoundaryRoute path={match.url} component={TipoProyecto} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TipoProyectoDeleteDialog} />
  </>
);

export default Routes;
