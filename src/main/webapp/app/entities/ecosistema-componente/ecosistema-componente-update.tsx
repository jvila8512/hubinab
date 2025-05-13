import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IComponentes } from 'app/shared/model/componentes.model';
import { getEntities as getComponentes } from 'app/entities/componentes/componentes.reducer';
import { IEcosistema } from 'app/shared/model/ecosistema.model';
import { getEntities as getEcosistemas } from 'app/entities/ecosistema/ecosistema.reducer';
import { IEcosistemaComponente } from 'app/shared/model/ecosistema-componente.model';
import { getEntity, updateEntity, createEntity, reset } from './ecosistema-componente.reducer';

export const EcosistemaComponenteUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const componentes = useAppSelector(state => state.componentes.entities);
  const ecosistemas = useAppSelector(state => state.ecosistema.entities);
  const ecosistemaComponenteEntity = useAppSelector(state => state.ecosistemaComponente.entity);
  const loading = useAppSelector(state => state.ecosistemaComponente.loading);
  const updating = useAppSelector(state => state.ecosistemaComponente.updating);
  const updateSuccess = useAppSelector(state => state.ecosistemaComponente.updateSuccess);
  const handleClose = () => {
    props.history.push('/entidad/ecosistema-componente');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getComponentes({}));
    dispatch(getEcosistemas({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...ecosistemaComponenteEntity,
      ...values,
      componente: componentes.find(it => it.id.toString() === values.componente.toString()),
      ecosistema: ecosistemas.find(it => it.id.toString() === values.ecosistema.toString()),
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
          ...ecosistemaComponenteEntity,
          componente: ecosistemaComponenteEntity?.componente?.id,
          ecosistema: ecosistemaComponenteEntity?.ecosistema?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhipsterApp.ecosistemaComponente.home.createOrEditLabel" data-cy="EcosistemaComponenteCreateUpdateHeading">
            <Translate contentKey="jhipsterApp.ecosistemaComponente.home.createOrEditLabel">
              Create or edit a EcosistemaComponente
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
                  id="ecosistema-componente-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('jhipsterApp.ecosistemaComponente.link')}
                id="ecosistema-componente-link"
                name="link"
                data-cy="link"
                type="text"
              />
              <ValidatedBlobField
                label={translate('jhipsterApp.ecosistemaComponente.documentoUrl')}
                id="ecosistema-componente-documentoUrl"
                name="documentoUrl"
                data-cy="documentoUrl"
                openActionLabel={translate('entity.action.open')}
              />
              <ValidatedField
                id="ecosistema-componente-componente"
                name="componente"
                data-cy="componente"
                label={translate('jhipsterApp.ecosistemaComponente.componente')}
                type="select"
              >
                <option value="" key="0" />
                {componentes
                  ? componentes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.componente}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="ecosistema-componente-ecosistema"
                name="ecosistema"
                data-cy="ecosistema"
                label={translate('jhipsterApp.ecosistemaComponente.ecosistema')}
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
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/entidad/ecosistema-componente"
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

export default EcosistemaComponenteUpdate;
