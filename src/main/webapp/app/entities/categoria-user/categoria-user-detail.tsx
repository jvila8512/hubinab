import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './categoria-user.reducer';

export const CategoriaUserDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const categoriaUserEntity = useAppSelector(state => state.categoriaUser.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="categoriaUserDetailsHeading">
          <Translate contentKey="jhipsterApp.categoriaUser.detail.title">CategoriaUser</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{categoriaUserEntity.id}</dd>
          <dt>
            <span id="categoriaUser">
              <Translate contentKey="jhipsterApp.categoriaUser.categoriaUser">Categoria User</Translate>
            </span>
          </dt>
          <dd>{categoriaUserEntity.categoriaUser}</dd>
        </dl>
        <Button tag={Link} to="/nomencladores/categoria-user" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/nomencladores/categoria-user/${categoriaUserEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CategoriaUserDetail;
