import { classNames } from 'primereact/utils';
import React from 'react';
import { Alert, Row } from 'reactstrap';
import './eventos.scss';
import { Image } from 'primereact/image';

const Blog = () => {
  return (
    <div className="celia1">
      <div id="content">
        <div id="logo">
          <h1 className="celia mt-2">BLOG cultura en Innovación Abierta</h1>
        </div>
        <div className="intro">
          <h1 className="celia">
            Modelos de Negocios <span className="resaltar">e Innovación Abierta</span>
          </h1>
          <p className="celia text-lg"> Aumenta TÚ productividad e eficiencia.</p>
        </div>

        <div className="grid mt-2">
          <div className="col-8">
            <h2 className="celia ">
              Modelo de Negocio<span className="resaltar"> Abierto</span> para tú empresa
            </h2>
            <p className="celia text-xl">Autor: Hub Innov@crece</p>
            <p className="celia text-xl">Fecha: 10/09/2023</p>
            <br />

            <div className="grid">
              <div className="col-2">
                <Image src="uploads/embu1.jpg" alt="embu1.jpg" width="150" height="150" />
              </div>
              <div className="col-10">
                <p className="celia text-lg line-height-4">
                  La globalización, la competitividad, el crecimiento exponencial de las tecnologías, hace que las empresas busquen innovar
                  utilizando su entorno externo; pues de forma individual no pueden enfrentar los retos, que demandan los problemas
                  complejos, relacionados con el crecimiento y desarrollo económico.
                </p>
                <p className="celia text-lg line-height-4">
                  Esta forma de apertura de las empresas a sus investigaciones, a la colaboración en creación de nuevos valores en productos
                  y servicios, por aumentar su competitividad empresarial, marca como tendencia fomentar modelos de negocios abiertos.{' '}
                  <br />
                  El modelo de negocio abierto ha sido desarrollado por Chesbrough, y modelizados gráficamente por Osterwalder en su popular
                  libro sobre generación de modelos de negocio, partiendo de la conceptualización de Innovación abierta.
                </p>
                <p className="celia">
                  <span className="resaltar2">EJEMPLOS DE MODELOS ABIERTOS</span> Mas adelante...
                </p>
              </div>
            </div>
          </div>
          <div className="col-4">
            <h2 className="celia">
              <span className="resaltar">Acerca</span> de Innovación Abierta
            </h2>
            <Image src="uploads/innov1.jpg" alt="embu1.jpg" className="w-full" />

            <p className="mt-1 mb-4  text-lg line-height-4">
              El modelo de Innovación abierta, como nuevo modelo de innovación, fue acuñado en el 2003 por por Henry Chesbrough, en su libro
              Open Innovation(Innovación Abierta).{' '}
            </p>
            <p className="mt-1 mb-4  text-lg line-height-4">
              La Innovación Abierta es el intercambio de conocimientos, experiencias e ideas entre socios comerciales, clientes, ciudadanos,
              universidades, científicos e instituciones públicas; para resolver los problemas más difíciles a los que pueda enfrentarse una
              empresa o un territorio. Este flujo funciona a lo interno o externo, de una organización o territorio, y donde se aplican
              modelos de Innovación abierta como: La Cuádruple Hélice, y Quíntuple Hélice
            </p>
            <p className="celia">
              <span className="resaltar2">ARTICULACIÓN Y RIESGOS DE LOS MODELOS DE INNOVACIÓN ABIERTA</span>Mas adelante...
            </p>
          </div>
        </div>

        <div className="clear"></div>
      </div>
    </div>
  );
};
export default Blog;
