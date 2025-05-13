import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IChangeMacker } from 'app/shared/model/change-macker.model';
import { getEntities as getChangeMackers } from 'app/entities/change-macker/change-macker.reducer';
import { IContactoChangeMacker } from 'app/shared/model/contacto-change-macker.model';
import { getEntity, updateEntity, createEntity, reset } from './contacto-change-macker.reducer';

export const ContactoChangeMackerUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const changeMackers = useAppSelector(state => state.changeMacker.entities);
  const contactoChangeMackerEntity = useAppSelector(state => state.contactoChangeMacker.entity);
  const loading = useAppSelector(state => state.contactoChangeMacker.loading);
  const updating = useAppSelector(state => state.contactoChangeMacker.updating);
  const updateSuccess = useAppSelector(state => state.contactoChangeMacker.updateSuccess);
  const handleClose = () => {
    props.history.push('/entidad/contacto-change-macker');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getChangeMackers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...contactoChangeMackerEntity,
      ...values,
      changeMacker: changeMackers.find(it => it.id.toString() === values.changeMacker.toString()),
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
          ...contactoChangeMackerEntity,
          changeMacker: contactoChangeMackerEntity?.changeMacker?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhipsterApp.contactoChangeMacker.home.createOrEditLabel" data-cy="ContactoChangeMackerCreateUpdateHeading">
            <Translate contentKey="jhipsterApp.contactoChangeMacker.home.createOrEditLabel">
              Create or edit a ContactoChangeMacker
            </Translate>
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
                  id="contacto-change-macker-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('jhipsterApp.contactoChangeMacker.nombre')}
                id="contacto-change-macker-nombre"
                name="nombre"
                data-cy="nombre"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterApp.contactoChangeMacker.telefono')}
                id="contacto-change-macker-telefono"
                name="telefono"
                data-cy="telefono"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterApp.contactoChangeMacker.correo')}
                id="contacto-change-macker-correo"
                name="correo"
                data-cy="correo"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterApp.contactoChangeMacker.mensaje')}
                id="contacto-change-macker-mensaje"
                name="mensaje"
                data-cy="mensaje"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterApp.contactoChangeMacker.fechaContacto')}
                id="contacto-change-macker-fechaContacto"
                name="fechaContacto"
                data-cy="fechaContacto"
                type="date"
              />
              <ValidatedField
                id="contacto-change-macker-changeMacker"
                name="changeMacker"
                data-cy="changeMacker"
                label={translate('jhipsterApp.contactoChangeMacker.changeMacker')}
                type="select"
              >
                <option value="" key="0" />
                {changeMackers
                  ? changeMackers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.nombre}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/entidad/contacto-change-macker"
                replace
                color="info"
              >
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

export default ContactoChangeMackerUpdate;
