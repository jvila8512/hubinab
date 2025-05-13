import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { ISector } from 'app/shared/model/sector.model';
import { getEntities as getSectors } from 'app/entities/sector/sector.reducer';
import { ILineaInvestigacion } from 'app/shared/model/linea-investigacion.model';
import { getEntities as getLineaInvestigacions } from 'app/entities/linea-investigacion/linea-investigacion.reducer';
import { IOds } from 'app/shared/model/ods.model';
import { getEntities as getOds } from 'app/entities/ods/ods.reducer';

import { IEcosistema } from 'app/shared/model/ecosistema.model';

import { getEntities as getEcosistemas } from 'app/entities/ecosistema/ecosistema.reducer';

import { ITipoProyecto } from 'app/shared/model/tipo-proyecto.model';
import { getEntities as getTipoProyectos } from 'app/entities/tipo-proyecto/tipo-proyecto.reducer';
import { IProyectos } from 'app/shared/model/proyectos.model';
import { getEntity, updateEntity, createEntity, reset } from './proyectos.reducer';

export const ProyectosUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const users = useAppSelector(state => state.userManagement.users);
  const sectors = useAppSelector(state => state.sector.entities);
  const lineaInvestigacions = useAppSelector(state => state.lineaInvestigacion.entities);
  const ods = useAppSelector(state => state.ods.entities);
  const ecosistemas = useAppSelector(state => state.ecosistema.entities);
  const tipoProyectos = useAppSelector(state => state.tipoProyecto.entities);
  const proyectosEntity = useAppSelector(state => state.proyectos.entity);
  const loading = useAppSelector(state => state.proyectos.loading);
  const updating = useAppSelector(state => state.proyectos.updating);
  const updateSuccess = useAppSelector(state => state.proyectos.updateSuccess);
  const handleClose = () => {
    props.history.push('/entidad/proyectos' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getUsers({}));
    dispatch(getSectors({}));
    dispatch(getLineaInvestigacions({}));
    dispatch(getOds({}));
    dispatch(getEcosistemas({}));

    dispatch(getTipoProyectos({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...proyectosEntity,
      ...values,
      sectors: mapIdList(values.sectors),
      lineaInvestigacions: mapIdList(values.lineaInvestigacions),
      ods: mapIdList(values.ods),
      user: users.find(it => it.id.toString() === values.user.toString()),
      ecosistema: ecosistemas.find(it => it.id.toString() === values.ecosistema.toString()),
      tipoProyecto: tipoProyectos.find(it => it.id.toString() === values.tipoProyecto.toString()),
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
          ...proyectosEntity,
          user: proyectosEntity?.user?.id,
          sectors: proyectosEntity?.sectors?.map(e => e.id.toString()),
          lineaInvestigacions: proyectosEntity?.lineaInvestigacions?.map(e => e.id.toString()),
          ods: proyectosEntity?.ods?.map(e => e.id.toString()),
          ecosistema: proyectosEntity?.ecosistema?.id,
          tipoProyecto: proyectosEntity?.tipoProyecto?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhipsterApp.proyectos.home.createOrEditLabel" data-cy="ProyectosCreateUpdateHeading">
            <Translate contentKey="jhipsterApp.proyectos.home.createOrEditLabel">Create or edit a Proyectos</Translate>
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
                  id="proyectos-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('jhipsterApp.proyectos.nombre')}
                id="proyectos-nombre"
                name="nombre"
                data-cy="nombre"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterApp.proyectos.descripcion')}
                id="proyectos-descripcion"
                name="descripcion"
                data-cy="descripcion"
                type="textarea"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterApp.proyectos.autor')}
                id="proyectos-autor"
                name="autor"
                data-cy="autor"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterApp.proyectos.necesidad')}
                id="proyectos-necesidad"
                name="necesidad"
                data-cy="necesidad"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterApp.proyectos.fechaInicio')}
                id="proyectos-fechaInicio"
                name="fechaInicio"
                data-cy="fechaInicio"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterApp.proyectos.fechaFin')}
                id="proyectos-fechaFin"
                name="fechaFin"
                data-cy="fechaFin"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedBlobField
                label={translate('jhipsterApp.proyectos.logoUrl')}
                id="proyectos-logoUrl"
                name="logoUrl"
                data-cy="logoUrl"
                isImage
                accept="image/*"
              />
              <ValidatedField id="proyectos-user" name="user" data-cy="user" label={translate('jhipsterApp.proyectos.user')} type="select">
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.login}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label={translate('jhipsterApp.proyectos.sector')}
                id="proyectos-sector"
                data-cy="sector"
                type="select"
                multiple
                name="sectors"
              >
                <option value="" key="0" />
                {sectors
                  ? sectors.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.sector}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label={translate('jhipsterApp.proyectos.lineaInvestigacion')}
                id="proyectos-lineaInvestigacion"
                data-cy="lineaInvestigacion"
                type="select"
                multiple
                name="lineaInvestigacions"
              >
                <option value="" key="0" />
                {lineaInvestigacions
                  ? lineaInvestigacions.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.linea}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label={translate('jhipsterApp.proyectos.ods')}
                id="proyectos-ods"
                data-cy="ods"
                type="select"
                multiple
                name="ods"
              >
                <option value="" key="0" />
                {ods
                  ? ods.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.ods}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="proyectos-ecosistema"
                name="ecosistema"
                data-cy="ecosistema"
                label={translate('jhipsterApp.proyectos.ecosistema')}
                type="select"
              >
                <option value="" key="0" />
                {ecosistemas
                  ? ecosistemas.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.nombre}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="proyectos-tipoProyecto"
                name="tipoProyecto"
                data-cy="tipoProyecto"
                label={translate('jhipsterApp.proyectos.tipoProyecto')}
                type="select"
              >
                <option value="" key="0" />
                {tipoProyectos
                  ? tipoProyectos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.tipoProyecto}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/entidad/proyectos" replace color="info">
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

export default ProyectosUpdate;
