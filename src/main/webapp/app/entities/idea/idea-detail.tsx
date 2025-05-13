import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './idea.reducer';

export const IdeaDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const ideaEntity = useAppSelector(state => state.idea.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="ideaDetailsHeading">
          <Translate contentKey="jhipsterApp.idea.detail.title">Idea</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{ideaEntity.id}</dd>
          <dt>
            <span id="numeroRegistro">
              <Translate contentKey="jhipsterApp.idea.numeroRegistro">Numero Registro</Translate>
            </span>
          </dt>
          <dd>{ideaEntity.numeroRegistro}</dd>
          <dt>
            <span id="titulo">
              <Translate contentKey="jhipsterApp.idea.titulo">Titulo</Translate>
            </span>
          </dt>
          <dd>{ideaEntity.titulo}</dd>
          <dt>
            <span id="descripcion">
              <Translate contentKey="jhipsterApp.idea.descripcion">Descripcion</Translate>
            </span>
          </dt>
          <dd>{ideaEntity.descripcion}</dd>
          <dt>
            <span id="autor">
              <Translate contentKey="jhipsterApp.idea.autor">Autor</Translate>
            </span>
          </dt>
          <dd>{ideaEntity.autor}</dd>
          <dt>
            <span id="fechaInscripcion">
              <Translate contentKey="jhipsterApp.idea.fechaInscripcion">Fecha Inscripcion</Translate>
            </span>
          </dt>
          <dd>
            {ideaEntity.fechaInscripcion ? (
              <TextFormat value={ideaEntity.fechaInscripcion} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="visto">
              <Translate contentKey="jhipsterApp.idea.visto">Visto</Translate>
            </span>
          </dt>
          <dd>{ideaEntity.visto}</dd>
          <dt>
            <span id="foto">
              <Translate contentKey="jhipsterApp.idea.foto">Foto</Translate>
            </span>
          </dt>
          <dd>
            {ideaEntity.foto ? (
              <div>
                {ideaEntity.fotoContentType ? (
                  <a onClick={openFile(ideaEntity.fotoContentType, ideaEntity.foto)}>
                    <img src={`data:${ideaEntity.fotoContentType};base64,${ideaEntity.foto}`} style={{ maxHeight: '30px' }} />
                  </a>
                ) : null}
                <span>
                  {ideaEntity.fotoContentType}, {byteSize(ideaEntity.foto)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="aceptada">
              <Translate contentKey="jhipsterApp.idea.aceptada">Aceptada</Translate>
            </span>
          </dt>
          <dd>{ideaEntity.aceptada ? 'true' : 'false'}</dd>
          <dt>
            <span id="publica">
              <Translate contentKey="jhipsterApp.idea.publica">Publica</Translate>
            </span>
          </dt>
          <dd>{ideaEntity.publica ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="jhipsterApp.idea.user">User</Translate>
          </dt>
          <dd>{ideaEntity.user ? ideaEntity.user.login : ''}</dd>
          <dt>
            <Translate contentKey="jhipsterApp.idea.reto">Reto</Translate>
          </dt>
          <dd>{ideaEntity.reto ? ideaEntity.reto.reto : ''}</dd>
          <dt>
            <Translate contentKey="jhipsterApp.idea.tipoIdea">Tipo Idea</Translate>
          </dt>
          <dd>{ideaEntity.tipoIdea ? ideaEntity.tipoIdea.tipoIdea : ''}</dd>
          <dt>
            <Translate contentKey="jhipsterApp.idea.entidad">Entidad</Translate>
          </dt>
          <dd>{ideaEntity.entidad ? ideaEntity.entidad.entidad : ''}</dd>
        </dl>
        <Button tag={Link} to="/entidad/idea" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/entidad/idea/${ideaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default IdeaDetail;
