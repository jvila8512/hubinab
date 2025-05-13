import tipoIdea from 'app/entities/tipo-idea/tipo-idea.reducer';
import idea from 'app/entities/idea/idea.reducer';
import reto from 'app/entities/reto/reto.reducer';
import ecosistema from 'app/entities/ecosistema/ecosistema.reducer';
import usuarioEcosistema from 'app/entities/usuario-ecosistema/usuario-ecosistema.reducer';
import componentes from 'app/entities/componentes/componentes.reducer';
import ecosistemaRol from 'app/entities/ecosistema-rol/ecosistema-rol.reducer';
import ecosistemaPeticiones from 'app/entities/ecosistema-peticiones/ecosistema-peticiones.reducer';
import proyectos from 'app/entities/proyectos/proyectos.reducer';
import participantes from 'app/entities/participantes/participantes.reducer';
import innovacionRacionalizacion from 'app/entities/innovacion-racionalizacion/innovacion-racionalizacion.reducer';
import tramite from 'app/entities/tramite/tramite.reducer';
import noticias from 'app/entities/noticias/noticias.reducer';
import notificacion from 'app/entities/notificacion/notificacion.reducer';
import tipoNotificacion from 'app/entities/tipo-notificacion/tipo-notificacion.reducer';
import tipoNoticia from 'app/entities/tipo-noticia/tipo-noticia.reducer';
import contacto from 'app/entities/contacto/contacto.reducer';
import tipoContacto from 'app/entities/tipo-contacto/tipo-contacto.reducer';
import comenetariosIdea from 'app/entities/comenetarios-idea/comenetarios-idea.reducer';
import likeIdea from 'app/entities/like-idea/like-idea.reducer';
import entidad from 'app/entities/entidad/entidad.reducer';
import tipoProyecto from 'app/entities/tipo-proyecto/tipo-proyecto.reducer';
import sector from 'app/entities/sector/sector.reducer';
import lineaInvestigacion from 'app/entities/linea-investigacion/linea-investigacion.reducer';
import ods from 'app/entities/ods/ods.reducer';
import redesSociales from 'app/entities/redes-sociales/redes-sociales.reducer';
import categoriaUser from 'app/entities/categoria-user/categoria-user.reducer';
import files from 'app/entities/Files/files.reducer';

import servicios from 'app/entities/servicios/servicios.reducer';
import contactoServicio from 'app/entities/contacto-servicio/contacto-servicio.reducer';
import changeMacker from 'app/entities/change-macker/change-macker.reducer';
import contactoChangeMacker from 'app/entities/contacto-change-macker/contacto-change-macker.reducer';
import anirista from 'app/entities/anirista/anirista.reducer';
import ecosistemaComponente from 'app/entities/ecosistema-componente/ecosistema-componente.reducer';
import comunidad from 'app/entities/comunidad/comunidad.reducer';
import footer from 'app/entities/footerSection/footerSection.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  tipoIdea,
  idea,
  reto,
  ecosistema,
  usuarioEcosistema,
  componentes,
  ecosistemaRol,
  ecosistemaPeticiones,
  proyectos,
  participantes,
  innovacionRacionalizacion,
  tramite,
  noticias,
  tipoNoticia,
  contacto,
  tipoContacto,
  comenetariosIdea,
  likeIdea,
  entidad,
  tipoProyecto,
  sector,
  lineaInvestigacion,
  ods,
  redesSociales,
  categoriaUser,
  files,
  servicios,
  contactoServicio,
  changeMacker,
  contactoChangeMacker,
  anirista,
  ecosistemaComponente,
  tipoNotificacion,
  notificacion,
  comunidad,
  footer,

  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
