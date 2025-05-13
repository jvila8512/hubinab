import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import EcosistemaComponente from './ecosistema-componente';
import EcosistemaComponenteDetail from './ecosistema-componente-detail';
import EcosistemaComponenteUpdate from './ecosistema-componente-update';
import EcosistemaComponenteDeleteDialog from './ecosistema-componente-delete-dialog';
import ComponentesCrud from './componentes-crud';
import expandableTableComponente from './expandableTableComponente';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EcosistemaComponenteUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/crud/:id/:index`} component={ComponentesCrud} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EcosistemaComponenteUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EcosistemaComponenteDetail} />
      <ErrorBoundaryRoute exact path={`${match.url}/expan/:id`} component={expandableTableComponente} />

      <ErrorBoundaryRoute path={match.url} component={EcosistemaComponente} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={EcosistemaComponenteDeleteDialog} />
  </>
);

export default Routes;
