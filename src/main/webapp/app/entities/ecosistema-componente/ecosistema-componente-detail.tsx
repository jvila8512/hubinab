import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './ecosistema-componente.reducer';

export const EcosistemaComponenteDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const ecosistemaComponenteEntity = useAppSelector(state => state.ecosistemaComponente.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="ecosistemaComponenteDetailsHeading">
          <Translate contentKey="jhipsterApp.ecosistemaComponente.detail.title">EcosistemaComponente</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{ecosistemaComponenteEntity.id}</dd>
          <dt>
            <span id="link">
              <Translate contentKey="jhipsterApp.ecosistemaComponente.link">Link</Translate>
            </span>
          </dt>
          <dd>{ecosistemaComponenteEntity.link}</dd>
          <dt>
            <span id="documentoUrl">
              <Translate contentKey="jhipsterApp.ecosistemaComponente.documentoUrl">Documento Url</Translate>
            </span>
          </dt>
          <dd>
            {ecosistemaComponenteEntity.documentoUrl ? (
              <div>
                {ecosistemaComponenteEntity.documentoUrlContentType ? (
                  <a onClick={openFile(ecosistemaComponenteEntity.documentoUrlContentType, ecosistemaComponenteEntity.documentoUrl)}>
                    <Translate contentKey="entity.action.open">Open</Translate>&nbsp;
                  </a>
                ) : null}
                <span>
                  {ecosistemaComponenteEntity.documentoUrlContentType}, {byteSize(ecosistemaComponenteEntity.documentoUrl)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="jhipsterApp.ecosistemaComponente.componente">Componente</Translate>
          </dt>
          <dd>{ecosistemaComponenteEntity.componente ? ecosistemaComponenteEntity.componente.componente : ''}</dd>
          <dt>
            <Translate contentKey="jhipsterApp.ecosistemaComponente.ecosistema">Ecosistema</Translate>
          </dt>
          <dd>{ecosistemaComponenteEntity.ecosistema ? ecosistemaComponenteEntity.ecosistema.nombre : ''}</dd>
        </dl>
        <Button tag={Link} to="/ecosistema-componente" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/ecosistema-componente/${ecosistemaComponenteEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default EcosistemaComponenteDetail;
