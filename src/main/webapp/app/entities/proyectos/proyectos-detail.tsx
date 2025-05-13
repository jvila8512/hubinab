import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './proyectos.reducer';

export const ProyectosDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const proyectosEntity = useAppSelector(state => state.proyectos.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="proyectosDetailsHeading">
          <Translate contentKey="jhipsterApp.proyectos.detail.title">Proyectos</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{proyectosEntity.id}</dd>
          <dt>
            <span id="nombre">
              <Translate contentKey="jhipsterApp.proyectos.nombre">Nombre</Translate>
            </span>
          </dt>
          <dd>{proyectosEntity.nombre}</dd>
          <dt>
            <span id="descripcion">
              <Translate contentKey="jhipsterApp.proyectos.descripcion">Descripcion</Translate>
            </span>
          </dt>
          <dd>{proyectosEntity.descripcion}</dd>
          <dt>
            <span id="autor">
              <Translate contentKey="jhipsterApp.proyectos.autor">Autor</Translate>
            </span>
          </dt>
          <dd>{proyectosEntity.autor}</dd>
          <dt>
            <span id="necesidad">
              <Translate contentKey="jhipsterApp.proyectos.necesidad">Necesidad</Translate>
            </span>
          </dt>
          <dd>{proyectosEntity.necesidad}</dd>
          <dt>
            <span id="fechaInicio">
              <Translate contentKey="jhipsterApp.proyectos.fechaInicio">Fecha Inicio</Translate>
            </span>
          </dt>
          <dd>
            {proyectosEntity.fechaInicio ? (
              <TextFormat value={proyectosEntity.fechaInicio} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="fechaFin">
              <Translate contentKey="jhipsterApp.proyectos.fechaFin">Fecha Fin</Translate>
            </span>
          </dt>
          <dd>
            {proyectosEntity.fechaFin ? <TextFormat value={proyectosEntity.fechaFin} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="logoUrl">
              <Translate contentKey="jhipsterApp.proyectos.logoUrl">Logo Url</Translate>
            </span>
          </dt>
          <dd>
            {proyectosEntity.logoUrl ? (
              <div>
                {proyectosEntity.logoUrlContentType ? (
                  <a onClick={openFile(proyectosEntity.logoUrlContentType, proyectosEntity.logoUrl)}>
                    <img
                      src={`data:${proyectosEntity.logoUrlContentType};base64,${proyectosEntity.logoUrl}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                ) : null}
                <span>
                  {proyectosEntity.logoUrlContentType}, {byteSize(proyectosEntity.logoUrl)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="jhipsterApp.proyectos.user">User</Translate>
          </dt>
          <dd>{proyectosEntity.user ? proyectosEntity.user.login : ''}</dd>
          <dt>
            <Translate contentKey="jhipsterApp.proyectos.sector">Sector</Translate>
          </dt>
          <dd>
            {proyectosEntity.sectors
              ? proyectosEntity.sectors.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.sector}</a>
                    {proyectosEntity.sectors && i === proyectosEntity.sectors.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="jhipsterApp.proyectos.lineaInvestigacion">Linea Investigacion</Translate>
          </dt>
          <dd>
            {proyectosEntity.lineaInvestigacions
              ? proyectosEntity.lineaInvestigacions.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.linea}</a>
                    {proyectosEntity.lineaInvestigacions && i === proyectosEntity.lineaInvestigacions.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="jhipsterApp.proyectos.ods">Ods</Translate>
          </dt>
          <dd>
            {proyectosEntity.ods
              ? proyectosEntity.ods.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.ods}</a>
                    {proyectosEntity.ods && i === proyectosEntity.ods.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="jhipsterApp.proyectos.ecosistema">Ecosistema</Translate>
          </dt>
          <dd>{proyectosEntity.ecosistema ? proyectosEntity.ecosistema.nombre : ''}</dd>
          <dt>
            <Translate contentKey="jhipsterApp.proyectos.tipoProyecto">Tipo Proyecto</Translate>
          </dt>
          <dd>{proyectosEntity.tipoProyecto ? proyectosEntity.tipoProyecto.tipoProyecto : ''}</dd>
        </dl>
        <Button tag={Link} to="/entidad/proyectos" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/entidad/proyectos/${proyectosEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProyectosDetail;
