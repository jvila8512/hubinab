import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { Translate, translate } from 'react-jhipster';
import { NavDropdown } from './menu-components';
import { IconProp } from '@fortawesome/fontawesome-svg-core';
import { faThLarge } from '@fortawesome/free-solid-svg-icons';

const accountMenuItemsAuthenticated = ({ on }) => (
  <>
    <MenuItem icon={faThLarge as IconProp} to="/usuario-panel" data-cy="settings" on={on}>
      Panel Principal
    </MenuItem>
    <MenuItem icon="wrench" to="/account/settings" data-cy="settings" on={on}>
      Perfil
    </MenuItem>
    <MenuItem icon="lock" to="/account/password" data-cy="passwordItem" on={on}>
      Cambiar Contraseña
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

export const AccountMenuUser = ({ isAuthenticated = false, on, user }) => (
  <NavDropdown
    id="account-menu"
    data-cy="accountMenu"
    name={!isAuthenticated ? 'Usuario' : null}
    user={isAuthenticated ? user : null}
    style={{ maxHeight: '80vh', overflow: 'auto', left: 'auto', right: '0' }}
  >
    {isAuthenticated ? accountMenuItemsAuthenticated({ on }) : accountMenuItems({ on })}
  </NavDropdown>
);
export const AccountMenuUser1 = ({ isAuthenticated = false, on, user }) => (
  <NavDropdown
    id="account-menu"
    data-cy="accountMenu"
    name={!isAuthenticated ? 'Usuario' : null}
    user={isAuthenticated ? user : null}
    style={{ maxHeight: '80vh', overflow: 'auto', left: 'auto', right: '0' }}
  >
    {isAuthenticated ? accountMenuItemsAuthenticated({ on }) : accountMenuItems({ on })}
  </NavDropdown>
);

export default AccountMenuUser;
