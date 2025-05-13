import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './noticias.reducer';

export const NoticiasDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const noticiasEntity = useAppSelector(state => state.noticias.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="noticiasDetailsHeading">
          <Translate contentKey="jhipsterApp.noticias.detail.title">Noticias</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{noticiasEntity.id}</dd>
          <dt>
            <span id="titulo">
              <Translate contentKey="jhipsterApp.noticias.titulo">Titulo</Translate>
            </span>
          </dt>
          <dd>{noticiasEntity.titulo}</dd>
          <dt>
            <span id="descripcion">
              <Translate contentKey="jhipsterApp.noticias.descripcion">Descripcion</Translate>
            </span>
          </dt>
          <dd>{noticiasEntity.descripcion}</dd>
          <dt>
            <span id="urlFoto">
              <Translate contentKey="jhipsterApp.noticias.urlFoto">Url Foto</Translate>
            </span>
          </dt>
          <dd>
            {noticiasEntity.urlFoto ? (
              <div>
                {noticiasEntity.urlFotoContentType ? (
                  <a onClick={openFile(noticiasEntity.urlFotoContentType, noticiasEntity.urlFoto)}>
                    <img src={`data:${noticiasEntity.urlFotoContentType};base64,${noticiasEntity.urlFoto}`} style={{ maxHeight: '30px' }} />
                  </a>
                ) : null}
                <span>
                  {noticiasEntity.urlFotoContentType}, {byteSize(noticiasEntity.urlFoto)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="publica">
              <Translate contentKey="jhipsterApp.noticias.publica">Publica</Translate>
            </span>
          </dt>
          <dd>{noticiasEntity.publica ? 'true' : 'false'}</dd>
          <dt>
            <span id="publicar">
              <Translate contentKey="jhipsterApp.noticias.publicar">Publicar</Translate>
            </span>
          </dt>
          <dd>{noticiasEntity.publicar ? 'true' : 'false'}</dd>
          <dt>
            <span id="fechaCreada">
              <Translate contentKey="jhipsterApp.noticias.fechaCreada">Fecha Creada</Translate>
            </span>
          </dt>
          <dd>
            {noticiasEntity.fechaCreada ? (
              <TextFormat value={noticiasEntity.fechaCreada} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="jhipsterApp.noticias.user">User</Translate>
          </dt>
          <dd>{noticiasEntity.user ? noticiasEntity.user.login : ''}</dd>
          <dt>
            <Translate contentKey="jhipsterApp.noticias.ecosistema">Ecosistema</Translate>
          </dt>
          <dd>{noticiasEntity.ecosistema ? noticiasEntity.ecosistema.nombre : ''}</dd>
          <dt>
            <Translate contentKey="jhipsterApp.noticias.tipoNoticia">Tipo Noticia</Translate>
          </dt>
          <dd>{noticiasEntity.tipoNoticia ? noticiasEntity.tipoNoticia.tipoNoticia : ''}</dd>
        </dl>
        <Button tag={Link} to="/entidad/noticias" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/entidad/noticias/${noticiasEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default NoticiasDetail;
