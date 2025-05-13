import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './ecosistema.reducer';

export const EcosistemaDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const ecosistemaEntity = useAppSelector(state => state.ecosistema.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="ecosistemaDetailsHeading">
          <Translate contentKey="jhipsterApp.ecosistema.detail.title">Ecosistema</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{ecosistemaEntity.id}</dd>
          <dt>
            <span id="nombre">
              <Translate contentKey="jhipsterApp.ecosistema.nombre">Nombre</Translate>
            </span>
          </dt>
          <dd>{ecosistemaEntity.nombre}</dd>
          <dt>
            <span id="tematica">
              <Translate contentKey="jhipsterApp.ecosistema.tematica">Tematica</Translate>
            </span>
          </dt>
          <dd>{ecosistemaEntity.tematica}</dd>
          <dt>
            <span id="activo">
              <Translate contentKey="jhipsterApp.ecosistema.activo">Activo</Translate>
            </span>
          </dt>
          <dd>{ecosistemaEntity.activo ? 'true' : 'false'}</dd>
          <dt>
            <span id="logoUrl">
              <Translate contentKey="jhipsterApp.ecosistema.logoUrl">Logo Url</Translate>
            </span>
          </dt>
          <dd>
            {ecosistemaEntity.logoUrl ? (
              <div>
                {ecosistemaEntity.logoUrlContentType ? (
                  <a onClick={openFile(ecosistemaEntity.logoUrlContentType, ecosistemaEntity.logoUrl)}>
                    <img
                      src={`data:${ecosistemaEntity.logoUrlContentType};base64,${ecosistemaEntity.logoUrl}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                ) : null}
                <span>
                  {ecosistemaEntity.logoUrlContentType}, {byteSize(ecosistemaEntity.logoUrl)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="ranking">
              <Translate contentKey="jhipsterApp.ecosistema.ranking">Ranking</Translate>
            </span>
          </dt>
          <dd>{ecosistemaEntity.ranking}</dd>
          <dt>
            <span id="usuariosCant">
              <Translate contentKey="jhipsterApp.ecosistema.usuariosCant">Usuarios Cant</Translate>
            </span>
          </dt>
          <dd>{ecosistemaEntity.usuariosCant}</dd>
          <dt>
            <span id="retosCant">
              <Translate contentKey="jhipsterApp.ecosistema.retosCant">Retos Cant</Translate>
            </span>
          </dt>
          <dd>{ecosistemaEntity.retosCant}</dd>
          <dt>
            <span id="ideasCant">
              <Translate contentKey="jhipsterApp.ecosistema.ideasCant">Ideas Cant</Translate>
            </span>
          </dt>
          <dd>{ecosistemaEntity.ideasCant}</dd>
          <dt>
            <Translate contentKey="jhipsterApp.ecosistema.user">User</Translate>
          </dt>
          <dd>
            {ecosistemaEntity.users
              ? ecosistemaEntity.users.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.login}</a>
                    {ecosistemaEntity.users && i === ecosistemaEntity.users.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="jhipsterApp.ecosistema.ecosistemaRol">Ecosistema Rol</Translate>
          </dt>
          <dd>{ecosistemaEntity.ecosistemaRol ? ecosistemaEntity.ecosistemaRol.ecosistemaRol : ''}</dd>
        </dl>
        <Button tag={Link} to="/ecosistema" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/ecosistema/${ecosistemaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default EcosistemaDetail;
