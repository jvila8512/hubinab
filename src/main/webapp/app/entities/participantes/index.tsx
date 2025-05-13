import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Participantes from './participantes';
import ParticipantesDetail from './participantes-detail';
import ParticipantesUpdate from './participantes-update';
import ParticipantesDeleteDialog from './participantes-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ParticipantesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ParticipantesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ParticipantesDetail} />
      <ErrorBoundaryRoute path={match.url} component={Participantes} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ParticipantesDeleteDialog} />
  </>
);

export default Routes;
