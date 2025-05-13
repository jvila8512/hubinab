import './aceptada.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { size } from 'lodash';
import { Button } from 'primereact/button';
import { Tag } from 'primereact/tag';
import React, { useEffect, useRef, useState } from 'react';
import { TextFormat, Translate, ValidatedBlobField, ValidatedField, ValidatedForm, isNumber, translate } from 'react-jhipster';
import { Link } from 'react-router-dom';
import { ILikeIdea } from 'app/shared/model/like-idea.model';
import { INotificacion } from 'app/shared/model/notificacion.model';
import { getEntitiesByIdea, createEntity as createLike, deleteEntity as deletelike, reset } from 'app/entities/like-idea/like-idea.reducer';
import {
  createEntitySinMensaje1 as crearNotificacion,
  deleteEntitySinMensaje as deleteNotificacion,
} from '../../entities/notificacion/notificacion.reducer';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntitiesbyReto, getEntity, updateEntity as actualizarIdea, deleteEntity as deleteIdea } from './idea.reducer';
import { Dialog } from 'primereact/dialog';
import { Row } from 'reactstrap';
import { Skeleton } from 'primereact/skeleton';
import dayjs from 'dayjs';
import { Avatar } from 'primereact/avatar';
import {
  getEntity as getFile,
  getEntities as getFiles,
  createEntity as createFile,
  reset as resetFile,
  getArchivo,
  deletefile,
} from 'app/entities/Files/files.reducer';

import { FileUpload } from 'primereact/fileupload';

const ComponenteIdea1 = props => {
  const dispatch = useAppDispatch();
  const idea = props.idea;
  const idea1 = props.idea;
  const [gusta, setGusta] = useState(false);
  const [isNewIdea, setNewIdea] = useState(true);
  const [ideaDialog, setIdeaDialog] = useState(false);

  const emptyLike = {};

  const [likeMio, setLikeMio] = useState(null);

  const [Ngusta, setNGusta] = useState(0);

  const [insertar_Borrar, setIB] = useState(true);

  const likeIdeaEntity = useAppSelector(state => state.likeIdea.entity);

  const [likeIdeaEntityCopia, setLICopia] = useState(null);

  const [texto, setTexto] = useState('');

  const updating = useAppSelector(state => state.likeIdea.updating);

  const updateSuccess = useAppSelector(state => state.likeIdea.updateSuccess);

  const ideasEntity = useAppSelector(state => state.idea.entity);
  const ideasEntities = useAppSelector(state => state.idea.entities);
  const loading = useAppSelector(state => state.idea.loading);

  const loadingIdea = useAppSelector(state => state.idea.loading);
  const updatingIdea = useAppSelector(state => state.idea.updating);
  const updateSuccessIdea = useAppSelector(state => state.idea.updateSuccess);

  const tipoNotificacion = useAppSelector(state => state.tipoNotificacion.entities);

  const notificacion = useAppSelector(state => state.notificacion.entity);

  const [respuestaNotificacion, setRespuestaNotificacion] = useState(null);

  const [open, setOpen] = useState(false);

  const [id, setID] = useState(0);

  const emptyNotificacion = {
    id: null,
    descripcion: '',
    visto: null,
    fecha: '',
    user: null,
    usercreada: null,
    tipoNotificacion: null,
  };
  const fileUploadRef = useRef(null);
  const file = useAppSelector(state => state.files.entity);
  const updatingFile = useAppSelector(state => state.files.updating);
  const updateSuccessFile = useAppSelector(state => state.files.updateSuccess);
  const loadingFile = useAppSelector(state => state.files.loading);

  const [selectedFile, setSelectedFile] = useState(null);
  const [fileModificar, setfileModificar] = useState(null);

  const borrar = icono => {
    const consulta = deletefile(icono);
    consulta.then(response => {
      setSelectedFile(null);
    });
  };

  const handleFileUpload = event => {
    const fileupload = event.files[0];
    const formData = new FormData();
    formData.append('files', selectedFile);
    dispatch(createFile(formData));
  };

  const chooseOptions = {
    icon: 'pi pi-fw pi-images',
    className: 'custom-choose-btn p-button-rounded p-button-outlined',
  };
  const uploadOptions = {
    icon: 'pi pi-fw pi-cloud-upload',

    className: 'custom-upload-btn p-button-success p-button-rounded p-button-outlined p-3',
  };

  const cancelOptions = { label: 'Cancel', icon: 'pi pi-times', className: 'p-button-danger' };

  const onUpload = e => {};

  const onTemplateSelect = e => {
    setSelectedFile(e.files[0]);
  };

  const iconoTemplate = rowData => {
    return (
      <>
        <Avatar image={`content/uploads/${rowData.icono}`} shape="circle" className="p-overlay-badge " size="xlarge" />
      </>
    );
  };

  const headerTemplate = options => {
    const { className, chooseButton, uploadButton, cancelButton } = options;

    return (
      <div className={className + ' relative'} style={{ backgroundColor: 'transparent', display: 'flex', alignItems: 'center' }}>
        {chooseButton}
      </div>
    );
  };
  const onTemplateRemove = (file1, callback) => {
    setSelectedFile(null);
    callback();
  };
  const itemTemplate = (file1, props1) => {
    return (
      <div className="flex flex-wrap align-items-center">
        <div className="flex  align-items-center gap-3" style={{ width: '60%' }}>
          <img alt={file1.name} role="presentation" src={file1.objectURL} width={100} />
          <span className="flex flex-column text-left ml-3">
            {file1.name}
            <small>{new Date().toLocaleDateString()}</small>
          </span>
        </div>
        <Tag value={props1.formatSize} severity="warning" className="px-4 py-3" />

        <Button
          type="button"
          icon="pi pi-times"
          className=" p-button-outlined p-button-rounded p-button-danger ml-3 p-3"
          onClick={() => onTemplateRemove(file1, props1.onRemove)}
        />
      </div>
    );
  };

  const emptyTemplate = () => {
    return (
      <div className="flex align-items-center flex-column">
        <span style={{ fontSize: '1.2em', color: 'var(--text-color-secondary)' }} className="my-5">
          Puede arrastrar y soltar el icono para Modificar
        </span>
      </div>
    );
  };

  useEffect(() => {
    const numero = idea.likes?.length;

    idea.likes?.length && setNGusta(idea.likes?.length);
    idea.likes?.length === 1 && setTexto('1 persona');
    idea.likes?.length === 0 && setTexto('0 persona');
    idea.likes?.length >= 2 && setTexto(idea.likes?.length + ' personas');

    const likes = idea.likes?.find(it => it.user?.id === props.usuarioo?.id);

    if (likes) {
      idea.likes?.length === 1 && setTexto('Tu gusta solo a ti');
      idea.likes?.length === 2 && setTexto('Tu y ' + (idea.likes?.length - 1) + ' personas');
      idea.likes?.length > 2 && setTexto('Tu y ' + (idea.likes?.length - 1) + ' personas');

      setGusta(true);
      setLikeMio(likes);
    } else {
      setGusta(false);
      setLikeMio(null);
    }
  }, []);

  useEffect(() => {
    if (updateSuccess)
      if (idea.id === id) {
        dispatch(getEntitiesbyReto(props.idReto));
        setGusta(!gusta);
      }
  }, [updateSuccess]);

  useEffect(() => {
    if (updateSuccessIdea)
      if (idea.id === id && !props.reto.publico) {
        borrar(idea.fotoContentType);
        dispatch(getEntitiesbyReto(props.idReto));
      }
    setIdeaDialog(false);
  }, [updateSuccessIdea]);

  useEffect(() => {
    if (!loading) {
      const likes = idea.likes?.find(it => it.user?.id === props.usuarioo?.id);
      setLikeMio(likes);

      if (likes) {
        idea.likes?.length === 1 && setTexto('Te gusta solo a ti');
        idea.likes?.length === 2 && setTexto('Tú y ' + (idea.likes?.length - 1) + ' persona');
        idea.likes?.length > 2 && setTexto('Tú y ' + (idea.likes?.length - 1) + ' personas');
      } else {
        idea.likes?.length === 1 && setTexto('1 persona');
        idea.likes?.length === 0 && setTexto('0 persona');
        idea.likes?.length >= 2 && setTexto(idea.likes?.length + ' personas');
      }
    }

    setID(0);
  }, [loading]);

  const defaultValuesIdeas = () =>
    !isNewIdea
      ? {}
      : {
          ...idea,
          user: props.usuarioo,
          reto: props.idreto,
          tipoIdea: idea?.tipoIdea?.id,
          entidad: idea?.entidad?.id,
        };

  const hideIdeaDialog = () => {
    setIdeaDialog(false);
  };

  const saveIdea = values => {
    const entity = {
      ...values,
      user: props.usuarioo,
      reto: props.reto,
      tipoIdea: props.tipoIdeass.find(it => it.id.toString() === values.tipoIdea.toString()),
      entidad: props.entidades.find(it => it.id.toString() === values.entidad.toString()),
      fotoContentType: selectedFile ? selectedFile.name : values.fotoContentType,
    };
    selectedFile && fileUploadRef.current.upload();
    dispatch(actualizarIdea(entity));
    setID(idea.id);
  };

  const getSeverity = reto => {
    switch (reto.publica) {
      case true:
        return 'success';

      case false:
        return 'danger';

      default:
        return null;
    }
  };

  const getActivo = reto => {
    switch (reto.publica) {
      case true:
        return 'Publica';

      case false:
        return 'No Publica';

      default:
        return null;
    }
  };

  const actualizarTexto = () => {
    setTexto('Holaaaaaa');
  };

  const actualizarIdeas = () => {
    setIdeaDialog(true);
  };

  const saveEntity = () => {
    const entityNotificacion = {
      ...emptyNotificacion,
      descripcion: 'Ha ' + props.usuarioo.firstName + ' ' + props.usuarioo.lastName + ' le gusta tu Idea con el título ' + idea1.titulo,
      visto: 0,
      fecha: dayjs().toDate(),
      user: idea1.user,
      usercreada: props.usuarioo,
      tipoNotificacion: props.tipoNotificacion.find(it => it.tipoNotificacion === 'Me Gusta'),
    };

    const likeIdea = {
      ...likeMio,
      like: 0,
      fechaInscripcion: new Date(),
      user: props.usuarioo,
      idea: idea1,
    };

    if (likeMio) {
      dispatch(deletelike(likeMio.id));
      if (respuestaNotificacion) dispatch(deleteNotificacion(respuestaNotificacion.id));
      setRespuestaNotificacion(null);
    } else {
      dispatch(createLike(likeIdea));

      const retos = crearNotificacion(entityNotificacion);
      retos.then(response => {
        setRespuestaNotificacion(response.data);
      });
    }
  };

  const megusta = () => {
    saveEntity();
    setID(idea.id);
  };

  const hacerPublicaIdea = () => {
    if (props.reto.user?.id === props.usuarioo?.id) {
      const entity = {
        ...idea,
        publica: !idea.publica,
      };

      if (props.reto.publico) {
        dispatch(actualizarIdea(entity));
        setID(idea.id);
      }
    }
  };

  const borrarIdea = () => {
    setDeleteIdeaDialog(true);
  };
  const borrarIdeaD = () => {
    borrar(idea.fotoContentType);
    dispatch(deleteIdea(idea.id));
    setDeleteIdeaDialog(false);
  };

  const hideDeleteIdeaDialog = () => {
    setDeleteIdeaDialog(false);
  };

  const [deleteIdeaDialog, setDeleteIdeaDialog] = useState(false);
  const deleteIdeaDialogFooter = (
    <>
      <Button label="No" icon="pi pi-times" className="p-button-text" onClick={hideDeleteIdeaDialog} />

      <Button label="Sí" icon="pi pi-check" className="p-button-text" onClick={borrarIdeaD} />
    </>
  );

  const renderDevRibbon = () => (props.idea.aceptada === true ? <div className="curso1"></div> : null);

  return (
    <div key={idea.id} className="p-2 ">
      <div className=" w-18rem h-30rem sm:w-24rem max-h-30rem  p-4 border-round-xl shadow-4 mb-2 relative">
        <div className="flex flex-column align-items-center gap-3 py-5">
          {renderDevRibbon()}

          <img className="h-13rem w-full  border-round" src={`content/uploads/${idea.fotoContentType}`} alt={idea.titulo} />
        </div>
        <div className="text-xl font-bold text-900">{idea.titulo}</div>
        {idea.user.id === props.usuarioo.id && <div className="text-1xl  text-600 ">Autor: {idea.autor}</div>}
        <div className="text-1xl  text-600 ">Tipo Idea: {idea.tipoIdea?.tipoIdea}</div>

        <div className="flex absolute bottom-0 left-0 mb-8 ml-5">
          <div className="flex align-items-center gap-3">
            <span className="flex align-items-center text-sm gap-2 mt-2">
              <i className="pi pi-calendar"></i>
              {idea.fechaInscripcion ? <TextFormat type="date" value={idea.fechaInscripcion} format={APP_LOCAL_DATE_FORMAT} /> : null}
            </span>
          </div>
        </div>
        <div className="flex absolute bottom-0 left-0 mb-6 ml-5">
          <Tag className="manito" onClick={hacerPublicaIdea} value={getActivo(idea)} severity={getSeverity(idea)}></Tag>
        </div>
        {idea.user?.id === props.usuarioo?.id && !props.reto.publico && (
          <div className="flex justify-content-start flex-wrap  absolute top-0 right-0 mt-2 mr-4">
            <Button onClick={actualizarIdeas} label="Editar" className="p-button-link" icon="pi pi-pencil" />
          </div>
        )}

        {(idea.user?.id === props.usuarioo?.id || props.reto.user?.id === props.usuarioo?.id) && (
          <div className="flex align-items-center justify-content-end  absolute top-0 left-0 ml-2 mt-3 ">
            <Button icon="pi pi-trash" className="p-button-danger" style={{ width: '25px', height: '25px' }} onClick={borrarIdea} />
          </div>
        )}

        <div
          onMouseLeave={() => {
            setOpen(!open);
          }}
          onMouseEnter={() => {
            setOpen(!open);
          }}
          className="flex justify-content-start flex-wrap  absolute bottom-0 mb-4"
        >
          <div className="flex align-items-start mt-2 gap-3">
            <span className="flex align-items-center gap-2 text-sm text-blue-500">
              <i className="pi pi-thumbs-up-fill"></i>
              {texto}
            </span>
          </div>
          <div className={`dropdown-menu111  ${open ? 'active' : 'inactive'}`}>
            {idea.likes && idea.likes?.length > 0 ? (
              <div className="flex flex-column  justify-content-center  mt-2">
                {idea.likes.map((likeIdea, i) => (
                  <span key={likeIdea.id} className="flex align-items-center gap-2 text-sm text-white">
                    {likeIdea.user.firstName + ' ' + likeIdea.user.lastName}
                  </span>
                ))}
              </div>
            ) : (
              <span className="flex align-items-center gap-2 text-sm text-white">No hay Likes.</span>
            )}
          </div>
        </div>

        <div className="flex justify-content-end absolute bottom-0 right-0 mb-4 mr-4">
          <div className="flex align-items-center">
            <Button
              icon="pi  pi-thumbs-up-fill"
              onClick={megusta}
              className={!gusta ? 'p-button-sm p-button-rounded p-button-secondary ' : 'p-button-sm p-button-rounded p-button-info '}
              aria-label="Bookmark"
              disabled={!props.reto.publico || props.usuarioo?.id === props.reto.user?.id}
            />
          </div>
        </div>
        <div className="flex justify-content-end absolute bottom-0 right-0 mb-8 mr-5">
          <Link
            hidden={idea.publica || idea.user?.id === props.usuarioo?.id || props.usuarioo?.id === props.reto.user?.id ? false : true}
            to={`/entidad/idea/ver_Ideas/${idea.id}/${props.idReto}/${props.index}/${props.idecosistema}`}
          >
            <div className="flex justify-content-start">
              <FontAwesomeIcon icon="eye" size="lg" /> <span className="d-none d-md-inline sm"></span>
            </div>
          </Link>
        </div>
      </div>
      <Dialog visible={ideaDialog} style={{ width: '500px' }} header="Idea" modal onHide={hideIdeaDialog}>
        <Row className="justify-content-center">
          {loadingIdea ? (
            <p>Cargando...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValuesIdeas()} onSubmit={saveIdea}>
              <ValidatedField name="fotoContentType" data-cy="fotoContentType" required readOnly hidden id="fotoContentType" type="text" />
              <FileUpload
                ref={fileUploadRef}
                name="demo[1]"
                accept="image/*"
                maxFileSize={1000000}
                chooseLabel="Suba la imagen nueva"
                uploadLabel="Modificar"
                onSelect={onTemplateSelect}
                onUpload={onUpload}
                customUpload
                uploadHandler={handleFileUpload}
                headerTemplate={headerTemplate}
                itemTemplate={itemTemplate}
                invalidFileSizeMessageSummary="Tamaño del archivo no válido"
                invalidFileSizeMessageDetail="El tamaño máximo de carga es de 1MB"
                emptyTemplate={emptyTemplate}
                chooseOptions={chooseOptions}
                uploadOptions={uploadOptions}
                cancelOptions={cancelOptions}
              />
              <ValidatedField
                label={translate('jhipsterApp.idea.numeroRegistro')}
                id="idea-numeroRegistro"
                name="numeroRegistro"
                data-cy="numeroRegistro"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('jhipsterApp.idea.titulo')}
                id="idea-titulo"
                name="titulo"
                data-cy="titulo"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterApp.idea.descripcion')}
                id="idea-descripcion"
                name="descripcion"
                data-cy="descripcion"
                type="textarea"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterApp.idea.autor')}
                id="idea-autor"
                name="autor"
                data-cy="autor"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterApp.idea.fechaInscripcion')}
                id="idea-fechaInscripcion"
                name="fechaInscripcion"
                data-cy="fechaInscripcion"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              {props.reto?.user?.id === props.usuarioo?.id && (
                <ValidatedField
                  label={translate('jhipsterApp.idea.publica')}
                  id="idea-publica"
                  name="publica"
                  data-cy="publica"
                  check
                  type="checkbox"
                />
              )}
              <ValidatedField
                id="idea-tipoIdea"
                name="tipoIdea"
                data-cy="tipoIdea"
                label={translate('jhipsterApp.idea.tipoIdea')}
                type="select"
              >
                <option value="" key="0" />
                {props.tipoIdeass
                  ? props.tipoIdeass.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.tipoIdea}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="idea-entidad"
                name="entidad"
                data-cy="entidad"
                label={translate('jhipsterApp.idea.entidad')}
                type="select"
              >
                <option value="" key="0" />
                {props.entidades
                  ? props.entidades.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.entidad}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              &nbsp;
              <Button
                className="w-full"
                color="primary"
                id="save-entity"
                data-cy="entityCreateSaveButton"
                type="submit"
                disabled={updatingIdea}
              >
                <span className="m-auto">
                  <FontAwesomeIcon icon="save" />
                  &nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </span>
              </Button>
            </ValidatedForm>
          )}
        </Row>
      </Dialog>

      <Dialog
        visible={deleteIdeaDialog}
        style={{ width: '450px' }}
        header="Confirmar"
        modal
        footer={deleteIdeaDialogFooter}
        onHide={hideDeleteIdeaDialog}
      >
        <div className="flex align-items-center justify-content-center">
          <i className="pi pi-exclamation-triangle mr-3" style={{ fontSize: '2rem' }} />
          {idea && (
            <span>
              ¿Seguro que quiere eliminar la idea: <b>{idea.titulo}</b>?
            </span>
          )}
        </div>
      </Dialog>
    </div>
  );
};

export default ComponenteIdea1;
