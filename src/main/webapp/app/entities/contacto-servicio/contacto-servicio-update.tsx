import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IServicios } from 'app/shared/model/servicios.model';
import { getEntities as getServicios } from 'app/entities/servicios/servicios.reducer';
import { IContactoServicio } from 'app/shared/model/contacto-servicio.model';
import { getEntity, updateEntity, createEntity, reset } from './contacto-servicio.reducer';

export const ContactoServicioUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const servicios = useAppSelector(state => state.servicios.entities);
  const contactoServicioEntity = useAppSelector(state => state.contactoServicio.entity);
  const loading = useAppSelector(state => state.contactoServicio.loading);
  const updating = useAppSelector(state => state.contactoServicio.updating);
  const updateSuccess = useAppSelector(state => state.contactoServicio.updateSuccess);
  const handleClose = () => {
    props.history.push('/entidad/contacto-servicio');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getServicios({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...contactoServicioEntity,
      ...values,
      servicios: servicios.find(it => it.id.toString() === values.servicios.toString()),
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
          ...contactoServicioEntity,
          servicios: contactoServicioEntity?.servicios?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhipsterApp.contactoServicio.home.createOrEditLabel" data-cy="ContactoServicioCreateUpdateHeading">
            <Translate contentKey="jhipsterApp.contactoServicio.home.createOrEditLabel">Create or edit a ContactoServicio</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Cargando...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="contacto-servicio-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('jhipsterApp.contactoServicio.nombre')}
                id="contacto-servicio-nombre"
                name="nombre"
                data-cy="nombre"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterApp.contactoServicio.telefono')}
                id="contacto-servicio-telefono"
                name="telefono"
                data-cy="telefono"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterApp.contactoServicio.correo')}
                id="contacto-servicio-correo"
                name="correo"
                data-cy="correo"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterApp.contactoServicio.mensaje')}
                id="contacto-servicio-mensaje"
                name="mensaje"
                data-cy="mensaje"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterApp.contactoServicio.fechaContacto')}
                id="contacto-servicio-fechaContacto"
                name="fechaContacto"
                data-cy="fechaContacto"
                type="date"
              />
              <ValidatedField
                id="contacto-servicio-servicios"
                name="servicios"
                data-cy="servicios"
                label={translate('jhipsterApp.contactoServicio.servicios')}
                type="select"
              >
                <option value="" key="0" />
                {servicios
                  ? servicios.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.servicio}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/entidad/contacto-servicio" replace color="info">
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

export default ContactoServicioUpdate;
