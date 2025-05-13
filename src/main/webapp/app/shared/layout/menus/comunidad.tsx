import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, translate } from 'react-jhipster';
import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';
import MenuItemComunidad from './menu-item-comunidad';
import { faPager } from '@fortawesome/free-solid-svg-icons';

export const Comunidad = props => (
  <NavDropdown name="Comunidad" id="comunidad-menu" style={{ maxHeight: '80vh', overflow: 'auto' }}>
    {props.activas?.map((comunidad, i) => (
      <MenuItemComunidad key={comunidad.id} icon={faPager} to={comunidad.link} on={props.on}>
        {' '}
        {comunidad.comunidad}
      </MenuItemComunidad>
    ))}
  </NavDropdown>
);
export default Comunidad;
