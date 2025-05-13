import './menu.css';

import React, { useState, useEffect, useRef } from 'react';
import { getArchivo } from 'app/entities/Files/files.reducer';

import { Link } from 'react-router-dom';

import { Avatar } from 'primereact/avatar';
import { FileUpload } from 'primereact/fileupload';
import { Button } from 'primereact/button';
import { Dialog } from 'primereact/dialog';
import { Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, isEmail, translate } from 'react-jhipster';

import { getSession } from 'app/shared/reducers/authentication';
import { saveAccountSettings, reset } from 'app/modules/account/settings/settings.reducer';
import { updateUser, reset as resetUser, getUsersPorRol } from 'app/modules/administration/user-management/user-management.reducer';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import account from 'app/modules/account';
import { toast } from 'react-toastify';
import { createEntity as createFile, reset as resetFile, deletefile } from 'app/entities/Files/files.reducer';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { getEntities as getEcosistemaRols } from 'app/entities/ecosistema-rol/ecosistema-rol.reducer';
import {
  getEntities,
  createEntitySolicitud,
  deleteEntity,
  updateEntity,
  encontrarUserEnEcosistemas,
} from 'app/entities/ecosistema/ecosistema.reducer';
import dayjs from 'dayjs';

import {
  createEntity as crearNotificacion,
  reset as resetNotificacion,
  crearNotificacionRetosUserEcosistema,
  createEntitySinMensaje,
} from 'app/entities/notificacion/notificacion.reducer';
import { getEntities as getTipoNotificaciones } from 'app/entities/tipo-notificacion/tipo-notificacion.reducer';
import { forEach } from 'lodash';

const MenuUsuario = props => {
  const dispatch = useAppDispatch();
  const [open, setOpen] = useState(false);
  const menuRef = useRef();
  const [perfilDialog, setPerfilDialog] = useState(false);

  const [fileupload, setFileupload] = useState(null);

  const accountAutenticado = useAppSelector(state => state.authentication.account);

  const successMessage = useAppSelector(state => state.settings.successMessage);

  const file = useAppSelector(state => state.files.entity);
  const updatingFile = useAppSelector(state => state.files.updating);
  const updateSuccessFile = useAppSelector(state => state.files.updateSuccess);
  const loadingFile = useAppSelector(state => state.files.loading);

  const updateSuccess = useAppSelector(state => state.userManagement.updateSuccess);
  const users = useAppSelector(state => state.userManagement.users);

  const [ecosistemaDialogNew, setEcosistemaDialogNew] = useState(false);

  const [imagenUser, setImagenUser] = useState(null);
  const tipoNotificacion = useAppSelector(state => state.tipoNotificacion.entities);

  const notiEntity = useAppSelector(state => state.notificacion.entity);

  const [existe, setExiste] = useState(false);

  useEffect(() => {
    const handler = e => {
      setOpen(false);
    };
    dispatch(getSession());
    dispatch(getEcosistemaRols({}));
    dispatch(getTipoNotificaciones({}));
    dispatch(getUsersPorRol('ROLE_ADMIN'));
    const userEcositema = encontrarUserEnEcosistemas(accountAutenticado.id);
    userEcositema.then(response => {
      setExiste(response.data);
    });
    document.addEventListener('mousedown', handler);
    return () => {
      document.removeEventListener('mousedown', handler);
      dispatch(reset());
    };
  }, []);

  const borrar = icono => {
    const consulta = deletefile(icono);
    consulta.then(response => {});
  };
  useEffect(() => {
    if (updateSuccessFile) {
      if (accountAutenticado.imageUrl !== 'userDesconocido.png') {
        borrar(accountAutenticado.imageUrl);

        if (fileupload)
          dispatch(
            saveAccountSettings({
              ...accountAutenticado,
              imageUrl: fileupload?.name,
            })
          );

        setFileupload(null);
      }
    }
  }, [updateSuccessFile]);

  useEffect(() => {
    if (updateSuccess) {
      dispatch(getSession());
      setPerfilDialog(false);
      const userEcositema = encontrarUserEnEcosistemas(accountAutenticado.id);
      userEcositema.then(response => {
        setExiste(response.data);
      });
    }
  }, [updateSuccess]);

  useEffect(() => {
    if (successMessage) {
      toast.success(translate(successMessage));
      setPerfilDialog(false);
    }
  }, [successMessage]);

  const hidePerfilDialog = () => {
    setPerfilDialog(false);
  };
  const verPerfilDialog = () => {
    setPerfilDialog(true);
  };

  const verSolicitudEcosistema = () => {
    setEcosistemaDialogNew(true);
  };

  const handleValidSubmit = values => {
    dispatch(
      saveAccountSettings({
        ...accountAutenticado,
        ...values,
      })
    );
  };
  const handleFileUpload = event => {
    setFileupload(event.files[0]);
    const f = event.files[0];
    const formData = new FormData();
    formData.append('files', f);
    dispatch(createFile(formData));
  };

  const chooseOptions = {
    icon: 'pi pi-fw pi-images',
    iconOnly: true,
    className: 'custom-choose-btn p-button-rounded p-button-outlined',
  };
  const uploadOptions = {
    icon: 'pi pi-fw pi-cloud-upload',
    iconOnly: true,
    className: 'custom-upload-btn p-button-success p-button-rounded p-button-outlined',
  };
  const cancelOptions = {
    icon: 'pi pi-fw pi-times',
    iconOnly: true,
    className: 'custom-cancel-btn p-button-danger p-button-rounded p-button-outlined',
  };

  const onBasicUploadAuto = () => {
    toast.success('Subido');
  };
  const hideDialogNuevo = () => {
    setEcosistemaDialogNew(false);
  };

  // Para salvar el la peticiondel ecositema
  const [isNew, setNew] = useState(true);
  const updateSuccessEcossitema = useAppSelector(state => state.ecosistema.updateSuccess);
  const updating = useAppSelector(state => state.ecosistema.updating);

  useEffect(() => {
    if (updateSuccessEcossitema) {
      setEcosistemaDialogNew(false);
      if (users && !existe)
        users.map((usuario, i) => {
          const notificacion = {
            ...notiEntity,
            descripcion: 'El usuario ' + props.account1.login + ' ha solicitado la creación de un Ecosistema',
            visto: 0,
            fecha: dayjs().toDate(),
            user: usuario,
            usercreada: props.account1,
            tipoNotificacion: tipoNotificacion.find(it => it.tipoNotificacion === 'Solicitud Ecosistema'),
          };

          dispatch(createEntitySinMensaje(notificacion));
        });
      const userEcositema = encontrarUserEnEcosistemas(accountAutenticado.id);
      userEcositema.then(response => {
        setExiste(response.data);
      });
    }
  }, [updateSuccessEcossitema]);

  const emptyEcosistema = {
    id: null,
    nombre: '',
    tematica: '',
    activo: null,
    logoUrlContentType: '',
    logoUrl: null,
    ranking: null,
    usuariosCant: null,
    retosCant: null,
    ideasCant: null,
    retos: null,
    ecosistemaComponentes: null,
    users: null,
    ecosistemaRol: null,
    usurioecosistemas: null,
  };
  const ecosistemaRols = useAppSelector(state => state.ecosistemaRol.entities);
  const saveEntity = values => {
    const entity = {
      ...values,
      ecosistemaRol: ecosistemaRols.find(it => it.id.toString() === values.ecosistemaRol.toString()),
      user: props.account1,
    };

    dispatch(createEntitySolicitud(entity));
  };
  const defaultValues = () => (isNew ? {} : {});

  //

  return (
    <div className=" App ">
      <div className="flex" ref={menuRef}>
        <div
          className="menu-trigger  "
          onClick={() => {
            setOpen(true);
          }}
          onMouseEnter={() => {
            setOpen(true);
          }}
        >
          <Button icon="pi pi-align-justify" className="p-button-outlined p-button-help" />

          <p className="text-900 text-2xl text-blue-600 font-medium ml-10">{accountAutenticado.login}</p>
        </div>

        <div className={`pt-2 dropdown-menu1  ${open ? 'active' : 'inactive'} card `}>
          <div className="bg-white">
            <div className="relative">
              <Avatar image={`content/uploads/${accountAutenticado.imageUrl}`} shape="circle" className="" size="large"></Avatar>
              <div className="flex justify-content-start flex-wrap  absolute top-0 right-0  mr-2 ">
                <Button
                  icon="pi pi-pencil"
                  className="p-button-rounded p-button-text p-button-sm "
                  aria-label="Submit"
                  onClick={verPerfilDialog}
                />
              </div>
            </div>

            <h5 className="text-sm  text-blue-600 font-medium mt-1">{accountAutenticado.firstName + ' ' + accountAutenticado.lastName}</h5>
            <div className="linea"></div>

            {props.account1?.authorities?.find(rol => rol === 'ROLE_ADMIN') && (
              <div className="flex flex-column">
                <Link
                  to={`admin/user_crud`}
                  className="  flex w-full  align-items-center justify-content-center font-medium menuOver "
                  id="jh-tre1"
                  data-cy="menu1"
                >
                  <span className="p-2   ">Usuarios</span>
                </Link>
                <Link
                  to={`/entidad/ecosistema/crud`}
                  className="flex w-full  align-items-center justify-content-center font-medium menuOver "
                  id="jh-tre1"
                  data-cy="menu1"
                >
                  <span className="p-2">Ecosistemas</span>
                </Link>
                <Link
                  to={`/entidad/comunidad/comunidades`}
                  className="flex w-full  align-items-center justify-content-center font-medium menuOver "
                  id="jh-tre1"
                  data-cy="menu1"
                >
                  <span className="p-2">Comunidades</span>
                </Link>
                <Link
                  to={`/entidad/noticias/noticias-admin`}
                  className=" flex w-full  align-items-center justify-content-center font-medium menuOver"
                  id="jh-tre1"
                  data-cy="menu1"
                >
                  <span className="p-2">Noticias</span>
                </Link>
                <Link
                  to={`/entidad/proyectos/proyectos_admin`}
                  className=" flex w-full  align-items-center justify-content-center font-medium menuOver "
                  id="jh-tre1"
                  data-cy="menu1"
                >
                  <span className="p-2">Proyectos</span>
                </Link>
              </div>
            )}
            <div className="flex flex-column ">
              {(props.account1?.authorities?.find(rol => rol === 'ROLE_ADMINECOSISTEMA') ||
                props.account1?.authorities?.find(rol => rol === 'ROLE_GESTOR')) && (
                <div className="flex flex-column">
                  <Link
                    to={`/entidad/innovacion-racionalizacion/crud`}
                    className=" border-0 flex w-full  align-items-center justify-content-center  font-medium menuOver"
                    id="jh-tre2"
                    data-cy="menu2"
                  >
                    <span className="p-2">Innovaciones</span>
                  </Link>
                  <Link
                    to={`entidad/anirista/anirista-crud`}
                    className=" border-0 flex w-full  align-items-center justify-content-center  font-medium menuOver"
                    id="jh-tre2"
                    data-cy="menu2"
                  >
                    <span className="p-2">Aniristas</span>
                  </Link>
                </div>
              )}
              <Link
                to={`/entidad/ecosistema/card`}
                className=" border-0 flex w-full  align-items-center justify-content-center  font-medium menuOver"
                id="jh-tre3"
                data-cy="menu3"
              >
                <span className="p-2">Unirse a un Ecosistema</span>
              </Link>
              {!existe && props.account1?.authorities?.find(rol => rol === 'ROLE_USER') && (
                <div className="flex justify-content-start flex-wrap  mr-2 ">
                  <Button
                    label={'Solicitar Ecosistema'}
                    icon="pi pi-wallet"
                    className=" p-button-outlined text-xs"
                    aria-label="Submit"
                    onClick={verSolicitudEcosistema}
                  />
                </div>
              )}
            </div>
          </div>
        </div>
      </div>

      <Dialog visible={perfilDialog} style={{ width: '500px' }} header="Perfil" modal onHide={hidePerfilDialog}>
        <div className="grid">
          <div className="col-3">
            <div className="grig">
              <div className="col-12">
                <Avatar image={`content/uploads/${props.account1.imageUrl}`} shape="circle" className="" size="xlarge"></Avatar>
              </div>
              <div className="col-6">
                <FileUpload
                  name="demo"
                  url="./upload"
                  mode="basic"
                  auto
                  onUpload={onBasicUploadAuto}
                  customUpload
                  uploadHandler={handleFileUpload}
                  chooseOptions={chooseOptions}
                />
              </div>
            </div>
          </div>
          <div className="col-9 ">
            <ValidatedForm id="settings-form" onSubmit={handleValidSubmit} defaultValues={accountAutenticado}>
              <ValidatedField
                name="firstName"
                label={translate('settings.form.firstname')}
                id="firstName"
                placeholder={translate('settings.form.firstname.placeholder')}
                validate={{
                  required: { value: true, message: translate('settings.messages.validate.firstname.required') },
                  minLength: { value: 1, message: translate('settings.messages.validate.firstname.minlength') },
                  maxLength: { value: 50, message: translate('settings.messages.validate.firstname.maxlength') },
                }}
                data-cy="firstname"
              />
              <ValidatedField
                name="lastName"
                label={translate('settings.form.lastname')}
                id="lastName"
                placeholder={translate('settings.form.lastname.placeholder')}
                validate={{
                  required: { value: true, message: translate('settings.messages.validate.lastname.required') },
                  minLength: { value: 1, message: translate('settings.messages.validate.lastname.minlength') },
                  maxLength: { value: 50, message: translate('settings.messages.validate.lastname.maxlength') },
                }}
                data-cy="lastname"
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

              <Button
                className="w-full"
                color="primary"
                id="save-entity"
                data-cy="entityCreateSaveButton"
                type="submit"
                disabled={updatingFile}
              >
                <span className="m-auto">
                  <FontAwesomeIcon icon="save" />
                  &nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </span>
              </Button>
            </ValidatedForm>
          </div>
        </div>
      </Dialog>

      <Dialog
        visible={ecosistemaDialogNew}
        style={{ width: '450px' }}
        header="Solicitud de Ecosistema"
        modal
        className="p-fluid  "
        onHide={hideDialogNuevo}
      >
        <Row className="justify-content-center">
          <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
            {!isNew ? (
              <ValidatedField
                name="id"
                required
                readOnly
                hidden
                id="ecosistema-id"
                label={translate('global.field.id')}
                validate={{ required: true }}
              />
            ) : null}
            <ValidatedField
              label={translate('jhipsterApp.ecosistema.nombre')}
              id="ecosistema-nombre"
              name="nombre"
              data-cy="nombre"
              type="text"
              validate={{
                required: { value: true, message: translate('entity.validation.required') },
                maxLength: { value: 100, message: translate('entity.validation.maxlength', { max: 100 }) },
              }}
            />
            <ValidatedField
              label={translate('jhipsterApp.ecosistema.tematica')}
              id="ecosistema-tematica"
              name="tematica"
              data-cy="tematica"
              type="textarea"
              validate={{
                required: { value: true, message: translate('entity.validation.required') },
              }}
            />

            <ValidatedField
              name="logoUrlContentType"
              data-cy="logoUrlContentType"
              required
              readOnly
              hidden
              id="logoUrlContentType"
              type="text"
            />

            <ValidatedField
              id="ecosistema-ecosistemaRol"
              name="ecosistemaRol"
              data-cy="ecosistemaRol"
              label={translate('jhipsterApp.ecosistema.ecosistemaRol')}
              validate={{
                required: { value: true, message: 'Debe seleccionar un Rol' },
              }}
              type="select"
            >
              <option value="" key="0" />
              {ecosistemaRols
                ? ecosistemaRols.map(otherEntity => (
                    <option value={otherEntity.id} key={otherEntity.id}>
                      {otherEntity.ecosistemaRol}
                    </option>
                  ))
                : null}
            </ValidatedField>

            <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
              <span className="m-auto pl-2">
                &nbsp;
                <Translate contentKey="entity.action.save1">Save</Translate>
              </span>
            </Button>
          </ValidatedForm>
        </Row>
      </Dialog>
    </div>
  );
};

export default MenuUsuario;
