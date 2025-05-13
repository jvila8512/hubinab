import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, translate } from 'react-jhipster';
import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const DesafioMenu = props => (
  <NavDropdown icon="th-list" name="Desafíos" id="eco-menu2" style={{ maxHeight: '80vh', overflow: 'auto' }}>
    <MenuItem icon="asterisk" to="/desafios" on={props.on}>
      ¿Como Funcionan?
    </MenuItem>

    <MenuItem icon="asterisk" to="/innovacion/desafios" on={props.on}>
      Desafios Recientes
    </MenuItem>
    <MenuItem icon="asterisk" to="/innovacion/desafios" on={props.on}>
      Ránking Innov@Crece 2022
    </MenuItem>
  </NavDropdown>
);
export default DesafioMenu;
