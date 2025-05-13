import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TipoNoticia from './tipo-noticia';
import TipoNoticiaDetail from './tipo-noticia-detail';
import TipoNoticiaUpdate from './tipo-noticia-update';
import TipoNoticiaDeleteDialog from './tipo-noticia-delete-dialog';
import TipoNoticiaCRUD from './tipoNoticiaCrud';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TipoNoticiaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/tipoNoticia-crud`} component={TipoNoticiaCRUD} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TipoNoticiaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TipoNoticiaDetail} />
      <ErrorBoundaryRoute path={match.url} component={TipoNoticia} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TipoNoticiaDeleteDialog} />
  </>
);

export default Routes;
