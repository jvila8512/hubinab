import React, { useState, useEffect } from 'react';
import { Translate, translate, ValidatedField, ValidatedForm, isEmail } from 'react-jhipster';
import { Row, Col, Alert, Modal } from 'reactstrap';
import { toast } from 'react-toastify';

import PasswordStrengthBar from 'app/shared/layout/password/password-strength-bar';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { handleRegister, reset } from './register.reducer';
import { Redirect, RouteComponentProps } from 'react-router-dom';
import { Dialog } from 'primereact/dialog';
import { Button } from 'primereact/button';

export const RegisterPage = (props: RouteComponentProps<any>) => {
  const [password, setPassword] = useState('');

  const [isTermsAccepted, setIsTermsAccepted] = useState(false);
  const [showTermsDialog, setShowTermsDialog] = useState(false);

  // Obtener términos del estado Redux
  const footerList = useAppSelector(state => state.footer.entities);
  // Obtener el TÉRMINO INACTIVO
  const inactiveTerm = useAppSelector(
    state => state.footer.entities.find(item => !item.active) // Cambio clave aquí
  );

  const termsContent = inactiveTerm?.content || '';

  // Función para procesar el contenido
  const processContent = (content: string) => {
    return content
      .split('/')
      .filter(section => section.trim() !== '') // Eliminar secciones vacías
      .map(section => ({
        content: section.trim(),
        isNumbered: /^\d+\./.test(section.trim()), // Detectar secciones numeradas
      }));
  };

  const parsedSections = processContent(termsContent);

  const dispatch = useAppDispatch();

  useEffect(
    () => () => {
      dispatch(reset());
    },
    []
  );

  const handleAcceptTerms = () => {
    setIsTermsAccepted(true);
    setShowTermsDialog(false);
  };

  const currentLocale = useAppSelector(state => state.locale.currentLocale);

  const handleValidSubmit = ({ username, email, firstPassword }) => {
    dispatch(handleRegister({ login: username, email, password: firstPassword, langKey: currentLocale }));
  };

  const updatePassword = event => setPassword(event.target.value);

  const successMessage = useAppSelector(state => state.register.successMessage);

  useEffect(() => {
    if (successMessage) {
      toast.success(translate(successMessage));
      props.history.push('/');
    }
  }, [successMessage]);

  const termsDialogFooter = (
    <div>
      <Button label="Cerrar" icon="pi pi-times" onClick={() => setShowTermsDialog(false)} className="p-button-text" />
      <Button label="Aceptar" icon="pi pi-check" onClick={handleAcceptTerms} autoFocus />
    </div>
  );

  return (
    <div>
      <Row className="justify-content-center mt-5">
        <Col md="8">
          <h1 id="register-title" data-cy="registerTitle">
            <Translate contentKey="register.title">Registration</Translate>
          </h1>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          <ValidatedForm id="register-form" onSubmit={handleValidSubmit}>
            <ValidatedField
              name="username"
              label={translate('global.form.username.label')}
              placeholder={translate('global.form.username.placeholder')}
              validate={{
                required: { value: true, message: translate('register.messages.validate.login.required') },
                pattern: {
                  value: /^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$/,
                  message: translate('register.messages.validate.login.pattern'),
                },
                minLength: { value: 1, message: translate('register.messages.validate.login.minlength') },
                maxLength: { value: 50, message: translate('register.messages.validate.login.maxlength') },
              }}
              data-cy="username"
            />
            <ValidatedField
              name="email"
              label={translate('global.form.email.label')}
              placeholder={translate('global.form.email.placeholder')}
              type="email"
              validate={{
                required: { value: true, message: translate('global.messages.validate.email.required') },
                minLength: { value: 5, message: translate('global.messages.validate.email.minlength') },
                maxLength: { value: 254, message: translate('global.messages.validate.email.maxlength') },
                validate: v => isEmail(v) || translate('global.messages.validate.email.invalid'),
              }}
              data-cy="email"
            />
            <ValidatedField
              name="firstPassword"
              label={translate('global.form.newpassword.label')}
              placeholder={translate('global.form.newpassword.placeholder')}
              type="password"
              onChange={updatePassword}
              validate={{
                required: { value: true, message: translate('global.messages.validate.newpassword.required') },
                minLength: { value: 4, message: translate('global.messages.validate.newpassword.minlength') },
                maxLength: { value: 50, message: translate('global.messages.validate.newpassword.maxlength') },
              }}
              data-cy="firstPassword"
            />
            <PasswordStrengthBar password={password} />
            <ValidatedField
              name="secondPassword"
              label={translate('global.form.confirmpassword.label')}
              placeholder={translate('global.form.confirmpassword.placeholder')}
              type="password"
              validate={{
                required: { value: true, message: translate('global.messages.validate.confirmpassword.required') },
                minLength: { value: 4, message: translate('global.messages.validate.confirmpassword.minlength') },
                maxLength: { value: 50, message: translate('global.messages.validate.confirmpassword.maxlength') },
                validate: v => v === password || translate('global.messages.error.dontmatch'),
              }}
              data-cy="secondPassword"
            />
            <div className="field-checkbox">
              <input
                type="checkbox"
                id="termsCheckbox"
                checked={isTermsAccepted}
                onChange={e => setIsTermsAccepted(e.target.checked)}
                disabled={!inactiveTerm} // Deshabilitar si no hay términos
                required
              />
              <label
                htmlFor="termsCheckbox"
                onClick={() => inactiveTerm && setShowTermsDialog(true)}
                style={{ cursor: inactiveTerm ? 'pointer' : 'not-allowed' }}
              >
                {inactiveTerm ? 'Acepto los términos y condiciones de uso' : 'Términos no disponibles'}
              </label>
            </div>
            <Button id="register-submit" color="primary" type="submit" data-cy="submit" disabled={!isTermsAccepted} className="mt-4">
              <Translate contentKey="register.form.button">Register</Translate>
            </Button>
          </ValidatedForm>

          {/* Modal de Términos y Condiciones */}
          <Dialog
            visible={showTermsDialog}
            style={{ width: '600px' }}
            header={`Términos y Condiciones`}
            modal
            footer={termsDialogFooter}
            onHide={() => setShowTermsDialog(false)}
          >
            <div style={{ overflowY: 'auto' }}>
              {parsedSections.map((section, index) => (
                <div key={index} className="mb-3">
                  {section.isNumbered ? section.content : <p style={{ whiteSpace: 'pre-line' }}>{section.content}</p>}
                </div>
              ))}
            </div>
          </Dialog>
          <p>&nbsp;</p>
        </Col>
      </Row>
    </div>
  );
};

export default RegisterPage;
