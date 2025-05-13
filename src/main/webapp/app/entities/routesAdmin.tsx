import React from 'react';
import { Switch } from 'react-router-dom';
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TipoIdea from './tipo-idea';
import Componentes from './componentes';
import EcosistemaRol from './ecosistema-rol';
import EcosistemaPeticiones from './ecosistema-peticiones';
import TipoNoticia from './tipo-noticia';
import TipoNotificacion from './tipo-notificacion';
import Contacto from './contacto';
import TipoContacto from './tipo-contacto';
import Entidad from './entidad';
import TipoProyecto from './tipo-proyecto';
import Sector from './sector';
import LineaInvestigacion from './linea-investigacion';
import Ods from './ods';
import RedesSociales from './redes-sociales';
import CategoriaUser from './categoria-user';
import Servicios from './servicios';
import ContactoServicio from './contacto-servicio';
import ChangeMacker from './change-macker';
import ContactoChangeMacker from './contacto-change-macker';
import Footer from './footerSection';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default ({ match }) => {
  return (
    <div>
      <Switch>
        {/* prettier-ignore */}
        <ErrorBoundaryRoute path={`${match.url}/tipo-idea`} component={TipoIdea} />
        <ErrorBoundaryRoute path={`${match.url}/componentes`} component={Componentes} />
        <ErrorBoundaryRoute path={`${match.url}/ecosistema-rol`} component={EcosistemaRol} />
        <ErrorBoundaryRoute path={`${match.url}/ecosistema-peticiones`} component={EcosistemaPeticiones} />
        <ErrorBoundaryRoute path={`${match.url}/tipo-noticia`} component={TipoNoticia} />
        <ErrorBoundaryRoute path={`${match.url}/tipo-notificacion`} component={TipoNotificacion} />
        <ErrorBoundaryRoute path={`${match.url}/contacto`} component={Contacto} />
        <ErrorBoundaryRoute path={`${match.url}/tipo-contacto`} component={TipoContacto} />
        <ErrorBoundaryRoute path={`${match.url}/entidad`} component={Entidad} />
        <ErrorBoundaryRoute path={`${match.url}/tipo-proyecto`} component={TipoProyecto} />
        <ErrorBoundaryRoute path={`${match.url}/sector`} component={Sector} />
        <ErrorBoundaryRoute path={`${match.url}/linea-investigacion`} component={LineaInvestigacion} />
        <ErrorBoundaryRoute path={`${match.url}/ods`} component={Ods} />
        <ErrorBoundaryRoute path={`${match.url}/redes-sociales`} component={RedesSociales} />
        <ErrorBoundaryRoute path={`${match.url}/categoria-user`} component={CategoriaUser} />
        <ErrorBoundaryRoute path={`${match.url}/servicios`} component={Servicios} />
        <ErrorBoundaryRoute path={`${match.url}/contacto-servicio`} component={ContactoServicio} />
        <ErrorBoundaryRoute path={`${match.url}/change-macker`} component={ChangeMacker} />
        <ErrorBoundaryRoute path={`${match.url}/contacto-change-macker`} component={ContactoChangeMacker} />
        <ErrorBoundaryRoute path={`${match.url}/footer`} component={Footer} />

        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </Switch>
    </div>
  );
};
