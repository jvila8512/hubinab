import React from 'react';
import { translate } from 'react-jhipster';
import { NavDropdown } from './menu-components';
import EntitiesMenuAdmin from 'app/entities/menuAdmin';

export const EntidadesAdmin = props => (
  <NavDropdown name="Nomencladores" id="entity-menu" data-cy="entity" style={{ maxHeight: '80vh', overflow: 'auto' }}>
    <EntitiesMenuAdmin on={props.on} />
  </NavDropdown>
);
