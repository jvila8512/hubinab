import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './usuario-ecosistema.reducer';

export const UsuarioEcosistemaDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const usuarioEcosistemaEntity = useAppSelector(state => state.usuarioEcosistema.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="usuarioEcosistemaDetailsHeading">
          <Translate contentKey="jhipsterApp.usuarioEcosistema.detail.title">Usuario de Ecosistema</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{usuarioEcosistemaEntity.id}</dd>
          <dt>
            <span id="fechaIngreso">
              <Translate contentKey="jhipsterApp.usuarioEcosistema.fechaIngreso">Fecha Ingreso</Translate>
            </span>
          </dt>
          <dd>
            {usuarioEcosistemaEntity.fechaIngreso ? (
              <TextFormat value={usuarioEcosistemaEntity.fechaIngreso} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="bloqueado">
              <Translate contentKey="jhipsterApp.usuarioEcosistema.bloqueado">Bloqueado</Translate>
            </span>
          </dt>
          <dd>{usuarioEcosistemaEntity.bloqueado ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="jhipsterApp.usuarioEcosistema.user">User</Translate>
          </dt>
          <dd>{usuarioEcosistemaEntity.user ? usuarioEcosistemaEntity.user.login : ''}</dd>
          <dt>
            <Translate contentKey="jhipsterApp.usuarioEcosistema.categoriaUser">Categoria User</Translate>
          </dt>
          <dd>{usuarioEcosistemaEntity.categoriaUser ? usuarioEcosistemaEntity.categoriaUser.categoriaUser : ''}</dd>
          <dt>
            <Translate contentKey="jhipsterApp.usuarioEcosistema.ecosistema">Ecosistema</Translate>
          </dt>
          <dd>
            {usuarioEcosistemaEntity.ecosistemas
              ? usuarioEcosistemaEntity.ecosistemas.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.nombre}</a>
                    {usuarioEcosistemaEntity.ecosistemas && i === usuarioEcosistemaEntity.ecosistemas.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/entidad/usuario-ecosistema" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/entidad/usuario-ecosistema/${usuarioEcosistemaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default UsuarioEcosistemaDetail;
