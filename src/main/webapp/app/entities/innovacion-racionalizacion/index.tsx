import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import InnovacionRacionalizacion from './innovacion-racionalizacion';
import InnovacionRacionalizacionDetail from './innovacion-racionalizacion-detail';
import InnovacionRacionalizacionUpdate from './innovacion-racionalizacion-update';
import InnovacionRacionalizacionDeleteDialog from './innovacion-racionalizacion-delete-dialog';
import BuscarInnovaciones from './buscarInnovaciones';
import InnovacionesCrud from './innovaciones-crud';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={InnovacionRacionalizacionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/crud/`} component={InnovacionesCrud} />
      <ErrorBoundaryRoute exact path={`${match.url}/buscar/`} component={BuscarInnovaciones} />
      <ErrorBoundaryRoute exact path={`${match.url}/buscar/:texto`} component={BuscarInnovaciones} />

      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={InnovacionRacionalizacionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={InnovacionRacionalizacionDetail} />
      <ErrorBoundaryRoute path={match.url} component={InnovacionRacionalizacion} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={InnovacionRacionalizacionDeleteDialog} />
  </>
);

export default Routes;
