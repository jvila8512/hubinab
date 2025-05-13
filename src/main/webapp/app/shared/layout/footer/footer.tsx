import './footer.scss';

import React, { useEffect, useState } from 'react';
import { Translate } from 'react-jhipster';
import { Col, Row } from 'reactstrap';
import { Chip } from 'primereact/chip';
import { Link } from 'react-router-dom';
import { Accordion, AccordionTab } from 'primereact/accordion';
import { obtenerAnoServer } from 'app/entities/ecosistema/ecosistema.reducer';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntities } from 'app/entities/footerSection/footerSection.reducer';

const Footer = () => {
  const [currentYear, setCurrentYear] = useState(new Date().getFullYear());

  const dispatch = useAppDispatch();
  const footerList = useAppSelector(state => state.footer.entities);
  const loading = useAppSelector(state => state.footer.loading);

  useEffect(() => {
    dispatch(getEntities());
    const retosFiltrar = obtenerAnoServer(10);
    retosFiltrar.then(response => {
      setCurrentYear(response.data);
    });
  }, []);

  // Función para formatear el contenido con puntos
  const formatContent = content => {
    if (!content) return <p>No hay contenido disponible</p>;

    return (
      <ul className="pl-3" style={{ listStyleType: 'disc' }}>
        {content
          .split('/')
          .filter(item => item.trim() !== '')
          .map((item, index) => (
            <li key={index} className="mb-2">
              {item.trim()}
            </li>
          ))}
      </ul>
    );
  };

  return (
    <div className="footer mt-4 ">
      <Row className="mt-4">
        {footerList.map(
          footerItem =>
            footerItem.active && (
              // Contenido para ítems activos
              <Col md="4" key={footerItem.id} className="mb-4">
                <Accordion expandIcon="pi pi-chevron-down" collapseIcon="pi pi-chevron-up" className="footer-accordion">
                  <AccordionTab className="text-100" header={footerItem.title || 'Sin título'}>
                    <div className="footer-content">{formatContent(footerItem.content)}</div>
                  </AccordionTab>
                </Accordion>
              </Col>
            )
        )}
      </Row>
      <Row>
        <Col md="12">
          <p className="text-white">
            ©{currentYear} Hubinab | Información Legal | Si tienes alguna pregunta acerca de nuestra plataforma de innovación abierta
            on-line, ponte en
            <Link to={`/entidad/contacto/contactar`}>
              <span className="text-blue font-bold mt-2 pl-1"> contacto </span>
            </Link>{' '}
            con nosotros.
          </p>
        </Col>
      </Row>
    </div>
  );
};
export default Footer;
