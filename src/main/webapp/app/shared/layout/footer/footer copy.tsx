import './footer.scss';

import React from 'react';
import { Translate } from 'react-jhipster';
import { Col, Row } from 'reactstrap';
import { Chip } from 'primereact/chip';
import { Link } from 'react-router-dom';

const Footer = () => (
  <div className="footer page-content">
    <Row>
      <Col md="3">
        <h4 className="text-white font-bold m-2 ">Que es Innov@Crece</h4>
        <h6 className="mb-3 mt-3 text-100 ">
          Es un espacio para articular actores de innovaciones, impulsar a Ecosistema de innovaciones, fortalecer comunidades innovadoras, y
          conectar con ChangeMacker, con uso de innovación abierta desde un entorno on-line.
        </h6>
      </Col>
      <Col md="3">
        <h4 className="text-white font-bold m-2">Que es Innovación Abierta</h4>
        <h6 className="mb-3 mt-3  text-100">
          Es un modelo de innovación concebido como unos de los método de la inteligencia colectiva, que ayuda a fortalecer la
          competitividad, el crecimiento y desarrollo economico, desde lo interno y externo a las organizaciones y territorios.
        </h6>
      </Col>
      <Col md="3">
        <h4 className="text-white font-bold m-2">¿Por qué elegirnos?</h4>
        <h6 className="mb-3 mt-3 text-100">
          <ul className="list-disc">
            <li>Somos un instrumento de fortalecimiento a la Transformación digital.</li>
            <li>
              Colaboras, participas e interactúas desde el conocimiento, por tú bienestar y el de la familia, con agentes de innovaciones y
              de cambio,de forma ilimitada, y accedes de forma gratuita al Libro de Registro de Innovaciones de los territorios.
            </li>
            <li>Con nosotros creces económicamente desde un enfoque ganar-ganar.</li>
            <li>
              Te ayudamos impulsando y promoviendo las ideas creativas, proyectos y negocios, desde la Economía del Conocimiento e
              Innovación.
            </li>
          </ul>
        </h6>
      </Col>
      <Col md="3">
        <h4 className="text-white font-bold mt-6">Contacto</h4>
        <Link to={`https://www.facebook.com/`}>
          <Chip label="Facebook" icon="pi pi-facebook" className="mr-2 mb-2 mt-5" />
        </Link>

        <Chip label="Twitter" icon="pi pi-twitter" className="mr-2 mb-2" />
      </Col>
    </Row>

    <Row>
      <Col md="12">
        <p className="text-white">
          ©2023 Innov@Crece | Información Legal | Si tienes alguna pregunta acerca de nuestra plataforma de innovación abierta, ponte en
          contacto con nosotros.
        </p>
      </Col>
    </Row>
  </div>
);

export default Footer;
