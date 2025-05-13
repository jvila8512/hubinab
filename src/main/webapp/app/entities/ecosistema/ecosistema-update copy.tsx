import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText, Toast } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import './ecosistemas.scss';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IEcosistemaRol } from 'app/shared/model/ecosistema-rol.model';
import { getEntities as getEcosistemaRols } from 'app/entities/ecosistema-rol/ecosistema-rol.reducer';
import { IUsuarioEcosistema } from 'app/shared/model/usuario-ecosistema.model';
import { getEntities as getUsuarioEcosistemas } from 'app/entities/usuario-ecosistema/usuario-ecosistema.reducer';
import { IEcosistema } from 'app/shared/model/ecosistema.model';
import { getEntity, updateEntity, createEntity, reset } from './ecosistema.reducer';

import {
  getEntity as getFile,
  getEntities as getFiles,
  createEntity as createFile,
  deleteEntity as deleFile,
  reset as resetFile,
} from 'app/entities/Files/files.reducer';

import { FileUpload } from 'primereact/fileupload';
import { Cargando } from 'app/entities/loader/cargando';

export const EcosistemaUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const users = useAppSelector(state => state.userManagement.users);
  const ecosistemaRols = useAppSelector(state => state.ecosistemaRol.entities);
  const usuarioEcosistemas = useAppSelector(state => state.usuarioEcosistema.entities);
  const ecosistemaEntity = useAppSelector(state => state.ecosistema.entity);
  const loading = useAppSelector(state => state.ecosistema.loading);
  const updating = useAppSelector(state => state.ecosistema.updating);
  const updateSuccess = useAppSelector(state => state.ecosistema.updateSuccess);

  const loadingFile = useAppSelector(state => state.files.loading);
  const file = useAppSelector(state => state.files.entity);
  const updatingFile = useAppSelector(state => state.files.updating);
  const updateSuccessFile = useAppSelector(state => state.files.updateSuccess);

  const account = useAppSelector(state => state.authentication.account);

  const [postImage, setPostImage] = useState(false);

  const handleClose = () => {
    props.history.push('/ecosistema');
  };

  const [selectedFile, setSelectedFile] = useState(null);

  const [enviando, setEnviando] = useState(false);

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
      dispatch(resetFile());
      setPostImage(false);
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getUsers({}));
    dispatch(getEcosistemaRols({}));
  }, []);

  useEffect(() => {
    file && !isNew ? setPostImage(true) : setPostImage(false);
  }, [file]);

  useEffect(() => {
    ecosistemaEntity?.logoUrl ? dispatch(getFile(ecosistemaEntity.logoUrl)) : null;
  }, [ecosistemaEntity]);

  useEffect(() => {
    if (selectedFile ? true : false) {
      if (updateSuccess && updateSuccessFile) {
        setEnviando(false);
        handleClose();
      }
    } else {
      if (updateSuccess) {
        setEnviando(false);
        handleClose();
      }
    }
  }, [updateSuccess, updateSuccessFile]);

  useEffect(() => {
    setEnviando(false);
  }, [enviando]);

  const saveEntity = values => {
    const entity = {
      ...ecosistemaEntity,
      ...values,
      logoUrl: selectedFile ? selectedFile.name : values.logoUrl,
      user: account,
      ecosistemaRol: ecosistemaRols.find(it => it.id.toString() === values.ecosistemaRol.toString()),
    };

    const formData = new FormData();
    formData.append('files', selectedFile);
    setEnviando(true);

    if (isNew) {
      selectedFile ? dispatch(createFile(formData)) : null;
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const onUpload = e => {};

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...ecosistemaEntity,
          user: ecosistemaEntity?.user?.id,
          ecosistemaRol: ecosistemaEntity?.ecosistemaRol?.id,
        };

  const chooseOptions = {
    icon: 'pi pi-fw pi-images',
    iconOnly: true,
    className: 'custom-choose-btn p-button-rounded p-button-outlined',
  };

  const uploadOptions = {};

  const onTemplateSelect = e => {
    setSelectedFile(e.files[0]);
    dispatch(resetFile());
    setPostImage(false);
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhipsterApp.ecosistema.home.createOrEditLabel" data-cy="EcosistemaCreateUpdateHeading">
            <Translate contentKey="jhipsterApp.ecosistema.home.createOrEditLabel">Create or edit a Ecosistema</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <Cargando />
          ) : (
            <div className="pb-8">
              <Row>
                <Col md="4">
                  {selectedFile ? <img alt="Imagenok" height="200" src={URL.createObjectURL(selectedFile)} /> : ''}

                  {postImage && <img height="200" src={`data:image;base64,${file}`} />}
                </Col>
                <Col md="3">
                  <FileUpload
                    mode="basic"
                    name="demo[]"
                    accept="image/*"
                    maxFileSize={10000}
                    chooseLabel="Suba la Imagen"
                    invalidFileSizeMessageSummary="El tamaño de la foto excede el permitido"
                    chooseOptions={chooseOptions}
                    onSelect={onTemplateSelect}
                    onBeforeUpload={onUpload}
                  />
                </Col>
              </Row>
              <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
                {!isNew ? (
                  <ValidatedField
                    name="id"
                    required
                    readOnly
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
                  label={translate('jhipsterApp.ecosistema.activo')}
                  id="ecosistema-activo"
                  name="activo"
                  data-cy="activo"
                  check
                  type="checkbox"
                />
                <ValidatedField
                  id="ecosistema-ecosistemaRol"
                  name="ecosistemaRol"
                  data-cy="ecosistemaRol"
                  label={translate('jhipsterApp.ecosistema.ecosistemaRol')}
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
                <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/ecosistema" replace color="info">
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
            </div>
          )}
        </Col>
      </Row>
      <Row>{enviando ? <Cargando /> : null}</Row>
    </div>
  );
};
export default EcosistemaUpdate;
function resolve(result: string | ArrayBuffer) {
  throw new Error('Function not implemented.');
}

function reject(error: ProgressEvent<FileReader>) {
  throw new Error('Function not implemented.');
}
