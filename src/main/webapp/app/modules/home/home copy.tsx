import './home.scss';

import React from 'react';
import { Link } from 'react-router-dom';
import { Translate } from 'react-jhipster';
import { Row, Col, Alert } from 'reactstrap';

import { Button } from 'primereact/button';

import { useAppSelector } from 'app/config/store';
import EcosistemasCarusel from './ecosistemasCarusel';

export const Home = () => {
  const account = useAppSelector(state => state.authentication.account);

  return (
    <>
      <Row>
        <div
          id="hero"
          className="flex flex-column pt-4 px-4 lg:px-8 overflow-hidden"
          style={{
            background:
              'linear-gradient(0deg, rgba(255, 255, 255, 0.2), rgba(255, 255, 255, 0.2)), radial-gradient(77.36% 256.97% at 77.36% 57.52%, #EEEFAF 0%, #C3E3FA 100%)',
            clipPath: 'ellipse(150% 87% at 93% 13%)',
          }}
        >
          <div className="mx-4 md:mx-8 mt-0 md:mt-4">
            <h1 className="text-6xl font-bold text-gray-900 line-height-2">
              <span className="font-light block">Eu sem integer</span>eget magna fermentum
            </h1>
            <p className="font-normal text-2xl line-height-3 md:mt-3 text-gray-700">
              Sed blandit libero volutpat sed cras. Fames ac turpis egestas integer. Placerat in egestas erat...{' '}
            </p>
            <Button
              type="button"
              label="Get Started"
              className="p-button-rounded text-xl border-none mt-3 bg-blue-500 font-normal line-height-3 px-3 text-white"
            ></Button>
          </div>
          <div className="flex justify-content-center md:justify-content-end">
            <span className="fondo "></span>
          </div>
        </div>
      </Row>
      <Row className="mb-8">
        <div className="surface-0 text-700 text-center">
          <div className="text-900 font-bold text-5xl mb-3">Proyecto Innov@Crece</div>
          <div className="text-700 text-2xl mb-5">Ecosistema de Innovación Digital</div>

          <div className="flex justify-content-center justify-content-end">
            <img src="../../../content/images/image.png" alt="Hero Image" className="w-9 md:w-auto" />
          </div>

          <p className="mt-0 mb-4 text-700 line-height-3">
            Este proyecto tiene el objetivo de fomentar la innovación e incrementar el impacto de la ciencia y la tecnología en diversas
            esferas y territorios, y para ello tendrá el apoyo de los organismos de la administración central del Estado, las Organizaciones
            Superiores de Dirección Empresarial, entidades nacionales y gobiernos territoriales.
          </p>
          {account?.login ? (
            <div></div>
          ) : (
            <Link to="/account/register" className="font-bold px-5 py-3 p-button-raised p-button-rounded white-space-nowrap">
              <span className="d-none d-md-inline">Registrate</span>
            </Link>
          )}
        </div>
      </Row>
      <Row>
        <EcosistemasCarusel />
      </Row>

      <Row>
        <div className="grid grid-nogutter surface-0 text-800">
          <div className="col-12 md:col-6 p-6 text-center md:text-left flex align-items-center ">
            <section>
              <span className="block text-4xl font-bold mb-1">Ecosistemas de innovación abierta</span>
              <div className="text-1xl text-primary font-bold mb-3">Transforma tu negocio mediante hubs de innovación tecnológica</div>
              <p className="mt-0 mb-4 text-700 line-height-3">
                Los ecosistemas de innovación abierta son hubs donde expertos, startups, empresas, ... se unen para desarrollar nuevos
                productos, servicios o modelos de negocio innovadores. Cada ecosistema puede ser creado por una o un grupo de empresas
                (sponsors) y su temática es estratégica para sus miembros.
              </p>

              <Button label="Leer más.." type="button" className="mr-3 p-button-raised" />
            </section>
          </div>
          <div className="col-12 md:col-6 overflow-hidden">
            <span className="foto1"></span>
          </div>
        </div>
      </Row>
      <Row>
        <div className="grid grid-nogutter surface-0 text-800">
          <div className="p-6 text-center md:text-left flex align-items-center ">
            <section>
              <span className="block text-4xl font-bold mb-1">Como funcionan los ecosistemas de innovación abierta </span>
              <p className="mt-0 mt-2 mb-2 text-700 line-height-3">
                Un ecosistema de innovación permite a sus sponsors colaborar con expertos, startups, MIPYMES en la identificación de
                oportunidades y la co-creación, prototipado y validación de nuevas soluciones tecnológicas. Los sponsors del ecosistema
                obtienen grandes sinergias, por ejemplo aunando la capacidad de atraer a las startups más relevantes de un sector.
              </p>
              <p className="mt-0 mb-2 text-700 line-height-3">
                Una empresa o un grupo de empresas pueden promover y crear un ecosistema de innovación nuevo o bien unirse a uno ya
                existente. Cada ecosistema se construye alrededor de una temática que es estratégica para sus sponsors y miembros. Un
                ecosistema es una herramienta muy poderosa para la innovación más estratégica y permite construir y desarrollar relaciones
                de largo plazo entre los sponsors y sus miembros.
              </p>
              <p className="mt-0 mb-2 text-700 line-height-3">
                Cada ecosistema creado sobre la plataforma se nutre de la comunidad de miembros de nuestro ecosistema raíz de ingeniería, a
                día de hoy con más de 20.000 miembros. Adicionalmente, ennomotive ofrece servicios para escalar la red de miembros de cada
                ecosistema nuevo.
              </p>
            </section>
          </div>
        </div>
      </Row>
      <Row>
        <div className="grid grid-nogutter surface-0 text-800">
          <span className="block text-4xl font-bold mb-1 ml-6">Qué pueden hacer los promotores de un ecosistema</span>

          <div className="col-12 md:col-6 overflow-hidden">
            <span className="foto2"></span>
          </div>

          <div className="col-12 md:col-6 p-6 text-center md:text-left flex align-items-center ">
            <ul className="list-none p-0 m-0 flex-grow-1">
              <li className="flex align-items-center mb-3">
                <i className="pi pi-check-circle text-green-500 mr-2"></i>
                <span>Descubrir startups de tu interés, relacionadas con las tecnologías e industrias propias del ecosistema.</span>
              </li>
              <li className="flex align-items-center mb-3">
                <i className="pi pi-check-circle text-green-500 mr-2"></i>
                <span>
                  Conectar con startups y Pymes para llevar a cabo proyectos colaborativos, codesarrollar prototipos o probar nuevas
                  soluciones tecnológicas.
                </span>
              </li>
              <li className="flex align-items-center mb-3">
                <i className="pi pi-check-circle text-green-500 mr-2"></i>
                <span>
                  Comunicar directamente con otras empresas de tu ecosistema para establecer colaboraciones y encontrar soluciones a
                  desafíos comunes.
                </span>
              </li>
              <li className="flex align-items-center mb-3">
                <i className="pi pi-check-circle text-green-500 mr-2"></i>
                <span>Compartir tus logros en materia de innovación y tecnología.</span>
              </li>
              <li className="flex align-items-center mb-3">
                <i className="pi pi-check-circle text-green-500 mr-2"></i>
                <span>
                  Lanzar desafíos para encontrar empresas, startups y expertos que puedan colaborar en sus proyectos más innovadores
                  aportando soluciones novedosas de otras industrias.
                </span>
              </li>
            </ul>
          </div>
        </div>
      </Row>
      <Row>
        <div className="grid grid-nogutter surface-0 text-800">
          <span className="block text-4xl font-bold mb-1 ml-6">Qué pueden hacer los miembros de un ecosistema</span>

          <div className="col-12 md:col-6 overflow-hidden">
            <span className="foto3"></span>
          </div>

          <div className="col-12 md:col-6 p-6 text-center md:text-left flex align-items-center ">
            <ul className="list-none p-0 m-0 flex-grow-1">
              <li className="flex align-items-center mb-3">
                <i className="pi pi-check-circle text-blue-500 mr-2"></i>
                <span>
                  Dar visibilidad a sus productos y servicios en la red de miembros para encontrar clientes potenciales y atraer inversores.
                </span>
              </li>
              <li className="flex align-items-center mb-3">
                <i className="pi pi-check-circle text-blue-500 mr-2"></i>
                <span>Captar oportunidades de negocio conociendo las necesidades y desafíos de los clientes.</span>
              </li>
              <li className="flex align-items-center mb-3">
                <i className="pi pi-check-circle text-blue-500 mr-2"></i>
                <span>Conectar con empresas líderes en su sector y mostrarles sus tecnologías para establecer colaboraciones.</span>
              </li>
              <li className="flex align-items-center mb-3">
                <i className="pi pi-check-circle text-blue-500 mr-2"></i>
                <span>Participar en desafíos para co-desarrollar nuevos productos y servicios junto a empresas y organizaciones.</span>
              </li>
              <li className="flex align-items-center mb-3">
                <i className="pi pi-check-circle text-blue-500 mr-2"></i>
                <span>Vigilancia tecnológica sobre productos y servicios de empresas de la competencia.</span>
              </li>
            </ul>
          </div>
        </div>
      </Row>
      <Row>
        <div className="surface-0 text-center p-3">
          <div className="mb-3 font-bold text-2xl">
            <span className="text-blue-600">Patrocinadores</span>
          </div>
          <div className="text-700 text-sm mb-6">Ac turpis egestas maecenas pharetra convallis posuere morbi leo urna.</div>
          <div className="grid">
            <div className="col-12 md:col-3 mb-3 px-5">
              <span className="gobierno"></span>

              <div className="text-900 mb-3 font-medium">Gobierno Provincial</div>
              <span className="text-700 text-sm line-height-3">
                Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.
              </span>
            </div>

            <div className="col-12 md:col-3 mb-3 px-5">
              <span className="anir"></span>

              <div className="text-900 mb-3 font-medium">ANIR</div>
              <span className="text-700 text-sm line-height-3">
                Risus nec feugiat in fermentum posuere urna nec. Posuere sollicitudin aliquam ultrices sagittis.
              </span>
            </div>
            <div className="col-12 md:col-3 mb-3 px-5">
              <span className="uic"></span>

              <div className="text-900 mb-3 font-medium">Unión de Informáticos de Cuba</div>
              <span className="text-700 text-sm line-height-3">
                Ornare suspendisse sed nisi lacus sed viverra tellus. Neque volutpat ac tincidunt vitae semper.
              </span>
            </div>

            <div className="col-12 md:col-3 mb-3 px-5">
              <span className="universidad"></span>

              <div className="text-900 mb-3 font-medium">Universidad de Las Tunas</div>
              <span className="text-700 text-sm line-height-3">
                Fermentum et sollicitudin ac orci phasellus egestas tellus rutrum tellus.
              </span>
            </div>
          </div>
        </div>
      </Row>
    </>
  );
};

export default Home;
