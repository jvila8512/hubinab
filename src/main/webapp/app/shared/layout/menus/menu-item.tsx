import React from 'react';
import { DropdownItem } from 'reactstrap';
import { NavLink as Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IconProp } from '@fortawesome/fontawesome-svg-core';
import { faTachometerAlt } from '@fortawesome/free-solid-svg-icons';

export interface IMenuItem {
  icon: IconProp;
  to: string;
  id?: string;
  'data-cy'?: string;
  on: null;
}

export default class MenuItem extends React.Component<IMenuItem> {
  render() {
    const { to, icon, id, children, on } = this.props;

    return (
      <DropdownItem tag={Link} to={to} id={id} data-cy={this.props['data-cy']} onClick={on}>
        <FontAwesomeIcon icon={icon} fixedWidth /> {children}
      </DropdownItem>
    );
  }
}
