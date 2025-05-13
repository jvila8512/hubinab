import React, { useEffect, useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';

import { translate, Translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { Link, RouteComponentProps } from 'react-router-dom';
import { getEntities as getEcosistemas } from 'app/entities/ecosistema/ecosistema.reducer';

import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { createEntity, reset } from '../ecosistema-peticiones/ecosistema-peticiones.reducer';

function Modal1(props: RouteComponentProps<{ id: string }>) {
  const dispatch = useAppDispatch();
  const [loadModal, setLoadModal] = useState(false);

  const account = useAppSelector(state => state.authentication.account);
  const ecosistemaPeticionesEntity = useAppSelector(state => state.ecosistemaPeticiones.entity);

  const loading = useAppSelector(state => state.ecosistemaPeticiones.loading);
  const updating = useAppSelector(state => state.ecosistemaPeticiones.updating);
  const updateSuccess = useAppSelector(state => state.ecosistemaPeticiones.updateSuccess);
  const users = useAppSelector(state => state.userManagement.users);
  const ecosistemas = useAppSelector(state => state.ecosistema.entities);

  useEffect(() => {
    dispatch(reset());
    dispatch(getUsers({}));

    dispatch(getEcosistemas({}));
    setLoadModal(true);
  }, []);

  useEffect(() => {
    if (updateSuccess && loadModal) {
      handleClose();
      setLoadModal(false);
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...ecosistemaPeticionesEntity,
      ...values,
      user: account,
      fechaPeticion: new Date(),
      ecosistema: ecosistemas.find(it => it.id.toString() === props.match.params.id),
    };

    dispatch(createEntity(entity));
  };

  const handleClose = () => {
    props.history.push('/ecosistema/card');
  };

  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose} data-cy="ecosistemaDeleteDialogHeading">
        Petición
      </ModalHeader>
      <ModalBody id="jhipsterApp.ecosistema.delete.question">
        <ValidatedForm onSubmit={saveEntity}>
          <ValidatedField
            label={translate('jhipsterApp.ecosistemaPeticiones.motivo')}
            id="ecosistema-peticiones-motivo"
            name="motivo"
            data-cy="motivo"
            type="text"
          />
          <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/ecosistema/card" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />
            &nbsp;
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit">
            <FontAwesomeIcon icon="save" />
            &nbsp;
            <Translate contentKey="entity.action.save">Save</Translate>
          </Button>
        </ValidatedForm>
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp;
          <Translate contentKey="entity.action.cancel">Cancel</Translate>
        </Button>
      </ModalFooter>
    </Modal>
  );
}

export default Modal1;
