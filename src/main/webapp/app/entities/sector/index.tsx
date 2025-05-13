import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Sector from './sector';
import SectorDetail from './sector-detail';
import SectorUpdate from './sector-update';
import SectorDeleteDialog from './sector-delete-dialog';
import TipoSectorCRUD from './tipoSectorCrud';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SectorUpdate} />

      <ErrorBoundaryRoute exact path={`${match.url}/sector-crud`} component={TipoSectorCRUD} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SectorUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SectorDetail} />
      <ErrorBoundaryRoute path={match.url} component={Sector} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SectorDeleteDialog} />
  </>
);

export default Routes;
