import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntiAdmin = ({ on }) => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/nomencladores/tipo-idea/tipoIdea-crud" on={on}>
        <Translate contentKey="global.menu.entities.tipoIdea" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/nomencladores/ecosistema-rol/rol-crud" on={on}>
        <Translate contentKey="global.menu.entities.ecosistemaRol" />
      </MenuItem>

      <MenuItem icon="asterisk" to="/nomencladores/tipo-noticia/tipoNoticia-crud" on={on}>
        <Translate contentKey="global.menu.entities.tipoNoticia" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/nomencladores/tipo-notificacion" on={on}>
        <Translate contentKey="global.menu.entities.tipoNotificacion" />
      </MenuItem>

      <MenuItem icon="asterisk" to="/nomencladores/tipo-contacto/tipoContacto-crud" on={on}>
        <Translate contentKey="global.menu.entities.tipoContacto" />
      </MenuItem>

      <MenuItem icon="asterisk" to="/nomencladores/entidad/entidad-crud" on={on}>
        <Translate contentKey="global.menu.entities.entidad" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/nomencladores/tipo-proyecto/tipoProyecto-crud" on={on}>
        <Translate contentKey="global.menu.entities.tipoProyecto" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/nomencladores/sector/sector-crud" on={on}>
        <Translate contentKey="global.menu.entities.sector" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/nomencladores/linea-investigacion/linea-crud" on={on}>
        <Translate contentKey="global.menu.entities.lineaInvestigacion" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/nomencladores/ods/ods-crud" on={on}>
        <Translate contentKey="global.menu.entities.ods" />
      </MenuItem>

      <MenuItem icon="asterisk" to="/nomencladores/categoria-user/categoria-crud" on={on}>
        <Translate contentKey="global.menu.entities.categoriaUser" />
      </MenuItem>

      <MenuItem icon="asterisk" to="/nomencladores/footer/footer-crud" on={on}>
        <Translate contentKey="global.menu.entities.footer" />
      </MenuItem>

      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntiAdmin as React.ComponentType<any>;
