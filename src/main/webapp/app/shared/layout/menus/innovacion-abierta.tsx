import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, translate } from 'react-jhipster';
import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const InnovacionAbiertaMenu = props => (
  <NavDropdown name="Innovación Abierta" id="innovacion-menu2" style={{ maxHeight: '80vh', overflow: 'auto' }}>
    <MenuItem icon="asterisk" to="/innovacion-abierta/blog" on={props.on}>
      Blog
    </MenuItem>
  </NavDropdown>
);
export default InnovacionAbiertaMenu;
