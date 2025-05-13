import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { Translate, translate } from 'react-jhipster';
import { NavDropdown } from './menu-components';

const accountMenuItemsAuthenticated = ({ on }) => (
  <>
    <MenuItem icon="wrench" to="/account/settings" data-cy="settings" on={on}>
      Ajustes
    </MenuItem>
    <MenuItem icon="lock" to="/account/password" data-cy="passwordItem" on={on}>
      Contraseña
    </MenuItem>
    <MenuItem icon="sign-out-alt" to="/logout" data-cy="logout" on={on}>
      Cerrar Sesión
    </MenuItem>
  </>
);

const accountMenuItems = ({ on }) => (
  <>
    <MenuItem id="login-item" icon="sign-in-alt" to="/login" data-cy="login" on={on}>
      Iniciar Sesión
    </MenuItem>
    <MenuItem icon="user-plus" to="/account/register" data-cy="register" on={on}>
      Crear una Cuenta
    </MenuItem>
  </>
);

export const AccountMenu = ({ isAuthenticated = false, on }) => (
  <NavDropdown
    id="account-menu"
    data-cy="accountMenu"
    name="Usuario"
    style={{ maxHeight: '80vh', overflow: 'auto', left: 'auto', right: '0' }}
  >
    {isAuthenticated ? accountMenuItemsAuthenticated({ on }) : accountMenuItems({ on })}
  </NavDropdown>
);

export default AccountMenu;
