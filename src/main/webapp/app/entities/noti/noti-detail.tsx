import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './noti.reducer';

export const NotiDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const notiEntity = useAppSelector(state => state.noti.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="notiDetailsHeading">
          <Translate contentKey="jhipsterApp.noti.detail.title">Noti</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{notiEntity.id}</dd>
          <dt>
            <span id="descripcion">
              <Translate contentKey="jhipsterApp.noti.descripcion">Descripcion</Translate>
            </span>
          </dt>
          <dd>{notiEntity.descripcion}</dd>
          <dt>
            <span id="visto">
              <Translate contentKey="jhipsterApp.noti.visto">Visto</Translate>
            </span>
          </dt>
          <dd>{notiEntity.visto ? 'true' : 'false'}</dd>
          <dt>
            <span id="fecha">
              <Translate contentKey="jhipsterApp.noti.fecha">Fecha</Translate>
            </span>
          </dt>
          <dd>{notiEntity.fecha ? <TextFormat value={notiEntity.fecha} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="jhipsterApp.noti.user">User</Translate>
          </dt>
          <dd>{notiEntity.user ? notiEntity.user.login : ''}</dd>
          <dt>
            <Translate contentKey="jhipsterApp.noti.usercreada">Usercreada</Translate>
          </dt>
          <dd>{notiEntity.usercreada ? notiEntity.usercreada.login : ''}</dd>
        </dl>
        <Button tag={Link} to="/noti" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/noti/${notiEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default NotiDetail;
