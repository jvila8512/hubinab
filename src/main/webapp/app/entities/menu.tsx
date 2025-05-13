import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';
import { IconProp } from '@fortawesome/fontawesome-svg-core';
import { faBell } from '@fortawesome/free-solid-svg-icons';

const EntitiesMenu = ({ on }) => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon={faBell as IconProp} to="/entidad/comunidad/comunidades" on={on}>
        Comunidades
      </MenuItem>

      <MenuItem icon={faBell as IconProp} to="/entidad/ecosistema/crud" on={on}>
        <Translate contentKey="global.menu.entities.ecosistema" />
      </MenuItem>

      <MenuItem icon={faBell as IconProp} to="/entidad/innovacion-racionalizacion/crud" on={on}>
        <Translate contentKey="global.menu.entities.innovacionRacionalizacion" />
      </MenuItem>

      <MenuItem icon={faBell as IconProp} to="/entidad/noticias/noticias-admin" on={on}>
        <Translate contentKey="global.menu.entities.noticias" />
      </MenuItem>

      <MenuItem icon={faBell as IconProp} to="/entidad/proyectos/proyectos_admin" on={on}>
        <Translate contentKey="global.menu.entities.proyectos" />
      </MenuItem>
      <MenuItem icon={faBell as IconProp} to="/entidad/contacto/contacto-crud" on={on}>
        <Translate contentKey="global.menu.entities.contacto" />
      </MenuItem>

      <MenuItem icon={faBell as IconProp} to="/entidad/anirista/anirista-crud" on={on}>
        <Translate contentKey="global.menu.entities.anirista" />
      </MenuItem>

      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu as React.ComponentType<any>;
