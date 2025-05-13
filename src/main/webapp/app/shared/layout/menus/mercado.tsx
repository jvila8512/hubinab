import React from 'react';
import { NavDropdown } from './menu-components';
import MenuItem from './menu-item';

export const Mercado = props => (
  <NavDropdown name="Mercado" id="mercado-menu" style={{ maxHeight: '80vh', overflow: 'auto' }}>
    <MenuItem icon="asterisk" to="/entidad/innovacion-racionalizacion/buscar/ " on={props.on}>
      Búsqueda de Innovaciones
    </MenuItem>

    <MenuItem icon="asterisk" to="/mercado/explorar" on={props.on}>
      Validar Prototipo
    </MenuItem>

    <MenuItem icon="asterisk" to="/mercado/add-ecositemas" on={props.on}>
      Transferencias Tecnológicas
    </MenuItem>
    <MenuItem icon="asterisk" to="/mercado/add" on={props.on}>
      Entrenamientos (Bootcamps)
    </MenuItem>

    <MenuItem icon="asterisk" to="/mercado/add" on={props.on}>
      Gestión demanda de habilidades
    </MenuItem>
  </NavDropdown>
);

export default Mercado;
