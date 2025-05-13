import React from 'react';
import { NavDropdown } from './menu-components';
import MenuItem from './menu-item';

export const Panel = props => (
  <NavDropdown icon="users-cog" name={Panel} id="admin-menu1" data-cy="adminMenu1">
    <MenuItem icon="sign-out-alt" to="/admin-panel" data-cy="logout" on={props.on}>
      Panel
    </MenuItem>
  </NavDropdown>
);

export default Panel;
