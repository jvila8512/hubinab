import React from 'react';
import { Translate } from 'react-jhipster';

import { NavItem, NavLink, NavbarBrand } from 'reactstrap';
import { NavLink as Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Avatar } from 'primereact/avatar';
import { Image } from 'primereact/image';

export const BrandIcon = props => (
  <div {...props} className="brand-icon">
    <Image src="content/images/hub.png" alt="Logo" className="" />
  </div>
);

export const Brand = () => (
  <NavbarBrand tag={Link} to="/" className="brand-logo -mt-3 ">
    <BrandIcon />
  </NavbarBrand>
);

export const Home = ({ on }) => (
  <NavItem onClick={on}>
    <NavLink tag={Link} to="/" className="d-flex align-items-center">
      <FontAwesomeIcon icon="home" />
    </NavLink>
  </NavItem>
);

export const Panel = ({ url, on, user }) => (
  <NavItem onClick={on}>
    <NavLink tag={Link} to={url} className="d-flex align-items-center">
      <Avatar
        label={`${user.login}`}
        image={`content/uploads/${user.imageUrl}`}
        shape="circle"
        className=""
        style={{ width: '1.5rem', height: '1.5rem' }}
      ></Avatar>
    </NavLink>
  </NavItem>
);
