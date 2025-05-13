import React from 'react';

import { UncontrolledDropdown, DropdownToggle, DropdownMenu } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Avatar } from 'primereact/avatar';

export const NavDropdown = props => (
  <UncontrolledDropdown nav inNavbar id={props.id} data-cy={props['data-cy']}>
    <DropdownToggle nav caret className="d-flex  align-items-center ">
      {props.name && <span>{props.name}</span>}

      {props.user && (
        <Avatar
          image={`content/uploads/${props.user.imageUrl}`}
          shape="circle"
          className=""
          style={{ width: '2rem', height: '2rem' }}
        ></Avatar>
      )}
    </DropdownToggle>
    <DropdownMenu cssModule={{ 'dropdown-menu': 'dropdown-menu ' }} style={props.style}>
      {props.children}
    </DropdownMenu>
  </UncontrolledDropdown>
);
