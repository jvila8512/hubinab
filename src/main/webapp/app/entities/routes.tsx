import React from 'react';
import { Switch } from 'react-router-dom';
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TipoIdea from './tipo-idea';
import Idea from './idea';
import Reto from './reto';
import Ecosistema from './ecosistema';
import UsuarioEcosistema from './usuario-ecosistema';
import Componentes from './componentes';
import EcosistemaRol from './ecosistema-rol';
import EcosistemaPeticiones from './ecosistema-peticiones';
import Proyectos from './proyectos';
import Participantes from './participantes';
import InnovacionRacionalizacion from './innovacion-racionalizacion';
import Tramite from './tramite';
import Noticias from './noticias';
import Notificacion from './notificacion';
import TipoNoticia from './tipo-noticia';
import Contacto from './contacto';
import TipoContacto from './tipo-contacto';
import ComenetariosIdea from './comenetarios-idea';
import LikeIdea from './like-idea';
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
import Anirista from './anirista';
import EcosistemaComponente from './ecosistema-componente';
import Comunidades from './comunidad';
import Footer from 'app/shared/layout/footer/footer';

/* jhipster-needle-add-route-import - JHipster will add routes here */

export default ({ match }) => {
  return (
    <div>
      <Switch>
        {/* prettier-ignore */}

        <ErrorBoundaryRoute path={`${match.url}/idea`} component={Idea} />
        <ErrorBoundaryRoute path={`${match.url}/notificacion`} component={Notificacion} />
        <ErrorBoundaryRoute path={`${match.url}/reto`} component={Reto} />
        <ErrorBoundaryRoute path={`${match.url}/like-idea`} component={LikeIdea} />
        <ErrorBoundaryRoute path={`${match.url}/ecosistema`} component={Ecosistema} />
        <ErrorBoundaryRoute path={`${match.url}/usuario-ecosistema`} component={UsuarioEcosistema} />
        <ErrorBoundaryRoute path={`${match.url}/componentes`} component={Componentes} />
        <ErrorBoundaryRoute path={`${match.url}/proyectos`} component={Proyectos} />
        <ErrorBoundaryRoute path={`${match.url}/innovacion-racionalizacion`} component={InnovacionRacionalizacion} />
        <ErrorBoundaryRoute path={`${match.url}/tramite`} component={Tramite} />
        <ErrorBoundaryRoute path={`${match.url}/noticias`} component={Noticias} />
        <ErrorBoundaryRoute path={`${match.url}/contacto`} component={Contacto} />
        <ErrorBoundaryRoute path={`${match.url}/servicios`} component={Servicios} />
        <ErrorBoundaryRoute path={`${match.url}/contacto-servicio`} component={ContactoServicio} />
        <ErrorBoundaryRoute path={`${match.url}/change-macker`} component={ChangeMacker} />
        <ErrorBoundaryRoute path={`${match.url}/anirista`} component={Anirista} />
        <ErrorBoundaryRoute path={`${match.url}/contacto-change-macker`} component={ContactoChangeMacker} />
        <ErrorBoundaryRoute path={`${match.url}/ecosistema-componente`} component={EcosistemaComponente} />
        <ErrorBoundaryRoute path={`${match.url}/comunidad`} component={Comunidades} />

        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </Switch>
    </div>
  );
};
