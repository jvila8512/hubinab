import React from 'react';
import { Alert, Row } from 'reactstrap';
import 'primereact/resources/themes/lara-light-indigo/theme.css';
import 'primereact/resources/primereact.min.css';
import 'primeicons/primeicons.css';
import '/node_modules/primeflex/primeflex.css';

import './ecosistema.scss';

const Ecosistema = () => {
  return (
    <>
      <Row>
        <div className="surface-0 text-700 text-center">
          <div className=" text-blue-200 text-5xl font-bold mb-3">ECOSISTEMA DE INNOVACIÓN TECNOLÓGICA</div>
          <div className="text-blue-500 font-bold text-5xl mb-3">DESAFÍOS DE INGENIERÍA</div>
          <div className="text-blue-700 text-1xl mb-5">Donde empresas e ingenieros se unen para innovar</div>
        </div>
      </Row>
      <Row>
        <span className="foto"></span>
      </Row>

      <Row>
        <p className="mt-0 mb-4 mt-4 text-700 line-height-3">
          En Innov@Crece, desafiamos a los ingenieros a resolver problemas técnicos de empresas líderes en industrias tales como la
          automotriz, la energía, la construcción, la minería y otras muchas más. Dichos desafíos de ingeniería se describen y publican en
          nuestro sitio web para que cualquier ingeniero, desde su casa u oficina, pueda intercambiar ideas y presentar soluciones
          innovadoras, y si está entre los mejores, conseguir premios en efectivo y reconocimiento.
        </p>
      </Row>

      <Row>
        <div className=" text-blue-200 text-5xl font-bold mb-4">RESUELVE RETOS DE INGENIERÍA, GANA PREMIOS Y RECONOCIMIENTO</div>

        <p className="mt-1 mb-4  text-700 line-height-3">
          Innov@Crece da la bienvenida a los ingenieros que disfrutan resolviendo problemas, que les gusta destacar entre la multitud y
          están dispuestos a desarrollar nuevas patentes o a crear un nuevos negocios.
        </p>
        <p className="mt-1 mb-4  text-700 line-height-3">
          Hacer frente a desafíos reales es un ejercicio muy enriquecedor que ayuda a los ingenieros a formarse y desarrollar sus
          habilidades con el diseño de soluciones con un propósito claro.
        </p>
        <p className="mt-1 mb-4  text-700 line-height-3">
          Para conseguir este objetivo los ingenieros pueden encontrar en ennomotive desafíos con especificaciones de problemas, planos,
          imágenes y videos para que todos puedan comprender lo que buscan las empresas.
        </p>
      </Row>
    </>
  );
};
export default Ecosistema;
