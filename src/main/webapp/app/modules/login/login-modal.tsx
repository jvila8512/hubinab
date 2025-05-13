import './login.css';

import React, { useState } from 'react';
import { Translate, translate, ValidatedField } from 'react-jhipster';
import { Button, Modal, ModalHeader, ModalBody, ModalFooter, Alert, Row, Col, Form } from 'reactstrap';
import { Link } from 'react-router-dom';
import { useForm } from 'react-hook-form';
import Cargando from 'app/entities/loader/cargando';
import SpinnerCar from 'app/entities/loader/spinner';

export interface ILoginModalProps {
  showModal: boolean;
  loginError: boolean;
  cargando: boolean;
  handleLogin: (username: string, password: string, rememberMe: boolean) => void;
  handleClose: () => void;
}

const LoginModal = (props: ILoginModalProps) => {
  const login = ({ username, password, rememberMe }) => {
    props.handleLogin(username, password, rememberMe);
  };
  const [shown, setShown] = useState(false);
  const [mostrarLoading, setmostrarLoading] = useState(false);

  const {
    handleSubmit,
    register,
    watch,
    formState: { errors, touchedFields },
  } = useForm({ mode: 'onTouched' });

  const { loginError, handleClose } = props;

  const handleLoginSubmit = e => {
    handleSubmit(login)(e);
    setmostrarLoading(true);
  };
  const cambiar = () => {
    setShown(!shown);
  };

  return (
    <Modal isOpen={props.showModal} toggle={handleClose} backdrop="static" id="login-page" autoFocus={false}>
      <Form onSubmit={handleLoginSubmit}>
        <ModalHeader id="login-title" data-cy="loginTitle" toggle={handleClose}>
          <Translate contentKey="login.title">Sign in</Translate>
        </ModalHeader>
        <ModalBody>
          <Row className="d-flex justify-content-center">
            <div className="d-flex justify-content-center">
              <div className="innova"></div>
            </div>
            <Col md="12">
              {loginError ? (
                <Alert color="danger" data-cy="loginError">
                  <Translate contentKey="login.messages.error.authentication">
                    <strong>Failed to sign in!</strong> Please check your credentials and try again.
                  </Translate>
                </Alert>
              ) : null}
            </Col>
            <Col md="12">
              <ValidatedField
                name="username"
                label={translate('global.form.username.label') + ' o Correo Electrónico'}
                placeholder={translate('global.form.username.placeholder') + ' o Correo Electrónico'}
                required
                autoFocus
                data-cy="username"
                validate={{ required: '¡El nombre de usuario no puede estar vacío!' }}
                register={register}
                error={errors.username}
                isTouched={touchedFields.username}
              />

              <Row>
                <ValidatedField
                  name="password"
                  type={shown ? 'text' : 'password'}
                  label={translate('login.form.password')}
                  placeholder={translate('login.form.password.placeholder')}
                  required
                  data-cy="password"
                  validate={{
                    required: '¡La contraseña no puede estar vacía!',
                  }}
                  register={register}
                  error={errors.password}
                  isTouched={touchedFields.password}
                />
              </Row>
            </Col>
          </Row>
          <ValidatedField
            name="mostrar"
            type="checkbox"
            check
            label={translate('login.form.rememberme.password')}
            className="flex gap-2 justify-content-end "
            onChange={cambiar}
            register={register}
          />
          <ValidatedField
            name="rememberMe"
            type="checkbox"
            check
            checked
            hidden
            label={translate('login.form.rememberme')}
            value={true}
            register={register}
          />

          <Row></Row>
          <div className="mt-1">&nbsp;</div>
          <Alert color="warning">
            <Link to="/account/reset/request" data-cy="forgetYourPasswordSelector">
              <Translate contentKey="login.password.forgot">Did you forget your password?</Translate>
            </Link>
          </Alert>
          <Alert color="warning">
            <span>
              <Translate contentKey="global.messages.info.register.noaccount">You don&apos;t have an account yet?</Translate>
            </span>{' '}
            <Link to="/account/register">
              <Translate contentKey="global.messages.info.register.link">Register a new account</Translate>
            </Link>
          </Alert>

          {props.cargando && <SpinnerCar />}
        </ModalBody>
        <ModalFooter>
          <Button color="secondary" onClick={handleClose} tabIndex={1}>
            <Translate contentKey="entity.action.cancel">Cancel</Translate>
          </Button>{' '}
          <Button color="primary" type="submit" data-cy="submit" disabled={props.cargando}>
            <Translate contentKey="login.form.button">Sign in</Translate>
          </Button>
        </ModalFooter>
      </Form>
    </Modal>
  );
};

export default LoginModal;
