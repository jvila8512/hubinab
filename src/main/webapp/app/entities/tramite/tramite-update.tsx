import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITramite } from 'app/shared/model/tramite.model';
import { getEntity, updateEntity, createEntity, reset } from './tramite.reducer';

export const TramiteUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const tramiteEntity = useAppSelector(state => state.tramite.entity);
  const loading = useAppSelector(state => state.tramite.loading);
  const updating = useAppSelector(state => state.tramite.updating);
  const updateSuccess = useAppSelector(state => state.tramite.updateSuccess);
  const handleClose = () => {
    props.history.push('/tramite');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...tramiteEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...tramiteEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhipsterApp.tramite.home.createOrEditLabel" data-cy="TramiteCreateUpdateHeading">
            <Translate contentKey="jhipsterApp.tramite.home.createOrEditLabel">Create or edit a Tramite</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="tramite-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('jhipsterApp.tramite.inscripcion')}
                id="tramite-inscripcion"
                name="inscripcion"
                data-cy="inscripcion"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterApp.tramite.pruebaExperimental')}
                id="tramite-pruebaExperimental"
                name="pruebaExperimental"
                data-cy="pruebaExperimental"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterApp.tramite.exmanenEvaluacion')}
                id="tramite-exmanenEvaluacion"
                name="exmanenEvaluacion"
                data-cy="exmanenEvaluacion"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterApp.tramite.dictamen')}
                id="tramite-dictamen"
                name="dictamen"
                data-cy="dictamen"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterApp.tramite.concesion')}
                id="tramite-concesion"
                name="concesion"
                data-cy="concesion"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('jhipsterApp.tramite.denegado')}
                id="tramite-denegado"
                name="denegado"
                data-cy="denegado"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('jhipsterApp.tramite.reclamacion')}
                id="tramite-reclamacion"
                name="reclamacion"
                data-cy="reclamacion"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('jhipsterApp.tramite.anulacion')}
                id="tramite-anulacion"
                name="anulacion"
                data-cy="anulacion"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('jhipsterApp.tramite.fechaNotificacion')}
                id="tramite-fechaNotificacion"
                name="fechaNotificacion"
                data-cy="fechaNotificacion"
                type="date"
              />
              <ValidatedField
                label={translate('jhipsterApp.tramite.fecaCertificado')}
                id="tramite-fecaCertificado"
                name="fecaCertificado"
                data-cy="fecaCertificado"
                type="date"
              />
              <ValidatedField
                label={translate('jhipsterApp.tramite.observacion')}
                id="tramite-observacion"
                name="observacion"
                data-cy="observacion"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/tramite" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default TramiteUpdate;
