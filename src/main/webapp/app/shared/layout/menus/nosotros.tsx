import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, translate } from 'react-jhipster';
import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const NosotrosMenu = props => (
  <NavDropdown icon="th-list" name="Desafíos" id="eco-menuDesafios" style={{ maxHeight: '80vh', overflow: 'auto' }}>
    <MenuItem icon="asterisk" to="/desafios" on={props.on}>
      Prensa y Noticias
    </MenuItem>

    <MenuItem icon="asterisk" to="/innovacion/desafios" on={props.on}>
      Nuestro equipo
    </MenuItem>
    <MenuItem icon="asterisk" to="/innovacion/desafios" on={props.on}>
      Contacto
    </MenuItem>
  </NavDropdown>
);
export default NosotrosMenu;
