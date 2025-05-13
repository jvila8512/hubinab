import React, { useEffect, useState } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { translate, Translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { createEntity, getEntity } from '../ecosistema-peticiones/ecosistema-peticiones.reducer';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { getEntities as getEcosistemas } from 'app/entities/ecosistema/ecosistema.reducer';

export const EcosistemaPeticionesDialog = (props: RouteComponentProps<{ id: string }>) => {
  const [loadModal, setLoadModal] = useState(false);

  const dispatch = useAppDispatch();

  const users = useAppSelector(state => state.userManagement.users);
  const ecosistemas = useAppSelector(state => state.ecosistema.entities);
  const ecosistemaPeticionesEntity = useAppSelector(state => state.ecosistemaPeticiones.entity);
  const loading = useAppSelector(state => state.ecosistemaPeticiones.loading);
  const updating = useAppSelector(state => state.ecosistemaPeticiones.updating);
  const updateSuccess = useAppSelector(state => state.ecosistemaPeticiones.updateSuccess);

  useEffect(() => {
    dispatch(reset());

    dispatch(getUsers({}));

    dispatch(getEcosistemas({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...ecosistemaPeticionesEntity,
      ...values,
      user: users.find(it => it.id.toString() === values.user.toString()),
      ecosistema: ecosistemas.find(it => it.id.toString() === values.ecosistema.toString()),
    };

    dispatch(createEntity(entity));
  };

  const defaultValues = () => {};

  useEffect(() => {
    setLoadModal(true);
  }, []);

  const handleClose = () => {
    props.history.push('/ecosistema/card');
  };

  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose} data-cy="ecosistemaPeticionesDeleteDialogHeading">
        <Translate contentKey="entity.delete.title">Solicitud de Incorporación</Translate>
      </ModalHeader>
      <ModalBody id="jhipsterApp.ecosistemaPeticiones.delete.question"></ModalBody>
      <ModalFooter></ModalFooter>
    </Modal>
  );
};

export default EcosistemaPeticionesDialog;

function reset(): any {
  throw new Error('Function not implemented.');
}
