import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './change-macker.reducer';

export const ChangeMackerDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const changeMackerEntity = useAppSelector(state => state.changeMacker.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="changeMackerDetailsHeading">
          <Translate contentKey="jhipsterApp.changeMacker.detail.title">ChangeMacker</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{changeMackerEntity.id}</dd>
          <dt>
            <span id="foto">
              <Translate contentKey="jhipsterApp.changeMacker.foto">Foto</Translate>
            </span>
          </dt>
          <dd>
            {changeMackerEntity.foto ? (
              <div>
                {changeMackerEntity.fotoContentType ? (
                  <a onClick={openFile(changeMackerEntity.fotoContentType, changeMackerEntity.foto)}>
                    <img
                      src={`data:${changeMackerEntity.fotoContentType};base64,${changeMackerEntity.foto}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                ) : null}
                <span>
                  {changeMackerEntity.fotoContentType}, {byteSize(changeMackerEntity.foto)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="nombre">
              <Translate contentKey="jhipsterApp.changeMacker.nombre">Nombre</Translate>
            </span>
          </dt>
          <dd>{changeMackerEntity.nombre}</dd>
          <dt>
            <span id="tema">
              <Translate contentKey="jhipsterApp.changeMacker.tema">Tema</Translate>
            </span>
          </dt>
          <dd>{changeMackerEntity.tema}</dd>
          <dt>
            <span id="descripcion">
              <Translate contentKey="jhipsterApp.changeMacker.descripcion">Descripcion</Translate>
            </span>
          </dt>
          <dd>{changeMackerEntity.descripcion}</dd>
        </dl>
        <Button tag={Link} to="/entidad/change-macker" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/entidad/change-macker/${changeMackerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ChangeMackerDetail;
