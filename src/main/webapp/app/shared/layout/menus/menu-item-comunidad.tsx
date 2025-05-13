import React from 'react';
import { DropdownItem } from 'reactstrap';
import { NavLink as Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IconProp } from '@fortawesome/fontawesome-svg-core';

export interface IMenuItem {
  icon: IconProp;
  to: string;
  id?: string;
  'data-cy'?: string;
  on: null;
}

export default class MenuItemComunidad extends React.Component<IMenuItem> {
  render() {
    const { to, icon, id, children, on } = this.props;

    return (
      <DropdownItem target="_blank" id={id} data-cy={this.props['data-cy']} onClick={on} href={to}>
        <FontAwesomeIcon icon={icon} fixedWidth /> {children}
      </DropdownItem>
    );
  }
}
