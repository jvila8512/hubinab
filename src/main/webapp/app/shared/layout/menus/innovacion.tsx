import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, translate } from 'react-jhipster';
import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const InnovacionMenu = props => (
  <NavDropdown name="Ecosistemas" id="eco-menu1" style={{ maxHeight: '80vh', overflow: 'auto' }}>
    <MenuItem icon="gears" to="/usuario-panel" on={props.on}>
      Lanzar un Reto
    </MenuItem>

    <MenuItem icon="search" to="/entidad/ecosistema/card" on={props.on}>
      Explorar Ecosistemas
    </MenuItem>
  </NavDropdown>
);
export default InnovacionMenu;
