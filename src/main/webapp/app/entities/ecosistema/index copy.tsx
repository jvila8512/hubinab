import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Ecosistema from './ecosistema';
import EcosistemaDetail from './ecosistema-detail';
import EcosistemaUpdate from './ecosistema-update';
import EcosistemaDeleteDialog from './ecosistema-delete-dialog';
import TodosCard from './todosCard';
import EcosistemaPeticionesDialog from './ecosistema-peticiones-dialog';
import Modal1 from './modal';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EcosistemaUpdate} />
      <ErrorBoundaryRoute path={`${match.url}/card`} component={TodosCard} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EcosistemaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EcosistemaDetail} />
      <ErrorBoundaryRoute path={match.url} component={Ecosistema} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={EcosistemaDeleteDialog} />
    <ErrorBoundaryRoute exact path={`${match.url}/:id/peticion`} component={EcosistemaPeticionesDialog} />
    <ErrorBoundaryRoute path={`${match.url}/card/:id/modal`} component={Modal1} />
  </>
);

export default Routes;
