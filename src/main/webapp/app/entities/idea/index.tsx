import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Idea from './idea';
import IdeaDetail from './idea-detail';
import IdeaUpdate from './idea-update';
import IdeaDeleteDialog from './idea-delete-dialog';
import IdeasbyRetos from './ideasbyretos';
import VistaprincipalIdea from './vistaprincipalIdea';
import VistaprincipalIdeaReto from './vistaprincipalIdeaReto';
import VistaprincipalIdeaReto1 from './vistaprincipalIdeaReto1';
import VistaprincipalIdeaReto11 from './vistaprincipalIdeaReto11';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={IdeaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/ver_Idea/:id/:idreto`} component={VistaprincipalIdeaReto} />
      <ErrorBoundaryRoute exact path={`${match.url}/ver_Ideas/:id/:idreto/:index/:idecosistema`} component={VistaprincipalIdeaReto11} />
      <ErrorBoundaryRoute exact path={`${match.url}/ver_Idea/:id/:idreto/:index`} component={VistaprincipalIdeaReto1} />

      <ErrorBoundaryRoute exact path={`${match.url}/verIdea/:id/:idreto/:idecosistema/:index`} component={VistaprincipalIdea} />

      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={IdeaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={IdeaDetail} />
      <ErrorBoundaryRoute exact path={`${match.url}/ideasbyretos/:id/:idecosistema/:index`} component={IdeasbyRetos} />
      <ErrorBoundaryRoute path={match.url} component={Idea} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={IdeaDeleteDialog} />
  </>
);

export default Routes;
