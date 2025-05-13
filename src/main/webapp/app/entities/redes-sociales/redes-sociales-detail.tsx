import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './redes-sociales.reducer';

export const RedesSocialesDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const redesSocialesEntity = useAppSelector(state => state.redesSociales.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="redesSocialesDetailsHeading">
          <Translate contentKey="jhipsterApp.redesSociales.detail.title">RedesSociales</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{redesSocialesEntity.id}</dd>
          <dt>
            <span id="redesUrl">
              <Translate contentKey="jhipsterApp.redesSociales.redesUrl">Redes Url</Translate>
            </span>
          </dt>
          <dd>{redesSocialesEntity.redesUrl}</dd>
          <dt>
            <span id="logoUrl">
              <Translate contentKey="jhipsterApp.redesSociales.logoUrl">Logo Url</Translate>
            </span>
          </dt>
          <dd>
            {redesSocialesEntity.logoUrl ? (
              <div>
                {redesSocialesEntity.logoUrlContentType ? (
                  <a onClick={openFile(redesSocialesEntity.logoUrlContentType, redesSocialesEntity.logoUrl)}>
                    <img
                      src={`data:${redesSocialesEntity.logoUrlContentType};base64,${redesSocialesEntity.logoUrl}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                ) : null}
                <span>
                  {redesSocialesEntity.logoUrlContentType}, {byteSize(redesSocialesEntity.logoUrl)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="jhipsterApp.redesSociales.ecosistema">Ecosistema</Translate>
          </dt>
          <dd>{redesSocialesEntity.ecosistema ? redesSocialesEntity.ecosistema.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/nomencladores/redes-sociales" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/nomencladores/redes-sociales/${redesSocialesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RedesSocialesDetail;
