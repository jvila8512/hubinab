import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITipoIdea } from 'app/shared/model/tipo-idea.model';
import { getEntities as getTipoIdeas } from 'app/entities/tipo-idea/tipo-idea.reducer';
import { IInnovacionRacionalizacion } from 'app/shared/model/innovacion-racionalizacion.model';
import { getEntity, updateEntity, createEntity, reset } from './innovacion-racionalizacion.reducer';

export const InnovacionRacionalizacionUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const tipoIdeas = useAppSelector(state => state.tipoIdea.entities);
  const innovacionRacionalizacionEntity = useAppSelector(state => state.innovacionRacionalizacion.entity);
  const loading = useAppSelector(state => state.innovacionRacionalizacion.loading);
  const updating = useAppSelector(state => state.innovacionRacionalizacion.updating);
  const updateSuccess = useAppSelector(state => state.innovacionRacionalizacion.updateSuccess);
  const handleClose = () => {
    props.history.push('/entidad/innovacion-racionalizacion' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getTipoIdeas({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...innovacionRacionalizacionEntity,
      ...values,
      tipoIdea: tipoIdeas.find(it => it.id.toString() === values.tipoIdea.toString()),
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
          ...innovacionRacionalizacionEntity,
          tipoIdea: innovacionRacionalizacionEntity?.tipoIdea?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhipsterApp.innovacionRacionalizacion.home.createOrEditLabel" data-cy="InnovacionRacionalizacionCreateUpdateHeading">
            <Translate contentKey="jhipsterApp.innovacionRacionalizacion.home.createOrEditLabel">
              Create or edit a InnovacionRacionalizacion
            </Translate>
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
                  id="innovacion-racionalizacion-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('jhipsterApp.innovacionRacionalizacion.tematica')}
                id="innovacion-racionalizacion-tematica"
                name="tematica"
                data-cy="tematica"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterApp.innovacionRacionalizacion.fecha')}
                id="innovacion-racionalizacion-fecha"
                name="fecha"
                data-cy="fecha"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterApp.innovacionRacionalizacion.vp')}
                id="innovacion-racionalizacion-vp"
                name="vp"
                data-cy="vp"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('jhipsterApp.innovacionRacionalizacion.autores')}
                id="innovacion-racionalizacion-autores"
                name="autores"
                data-cy="autores"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterApp.innovacionRacionalizacion.numeroIdentidad')}
                id="innovacion-racionalizacion-numeroIdentidad"
                name="numeroIdentidad"
                data-cy="numeroIdentidad"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('jhipsterApp.innovacionRacionalizacion.observacion')}
                id="innovacion-racionalizacion-observacion"
                name="observacion"
                data-cy="observacion"
                type="textarea"
              />
              <ValidatedField
                label={translate('jhipsterApp.innovacionRacionalizacion.aprobada')}
                id="innovacion-racionalizacion-aprobada"
                name="aprobada"
                data-cy="aprobada"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('jhipsterApp.innovacionRacionalizacion.publico')}
                id="innovacion-racionalizacion-publico"
                name="publico"
                data-cy="publico"
                check
                type="checkbox"
              />
              <ValidatedField
                id="innovacion-racionalizacion-tipoIdea"
                name="tipoIdea"
                data-cy="tipoIdea"
                label={translate('jhipsterApp.innovacionRacionalizacion.tipoIdea')}
                type="select"
              >
                <option value="" key="0" />
                {tipoIdeas
                  ? tipoIdeas.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.tipoIdea}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/entidad/innovacion-racionalizacion"
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

export default InnovacionRacionalizacionUpdate;
