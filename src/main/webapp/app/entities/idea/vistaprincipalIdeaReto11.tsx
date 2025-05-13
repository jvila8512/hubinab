import './aceptadavista.scss';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import React, { useEffect, useState } from 'react';
import { RouteComponentProps } from 'react-router-dom';
import { Fieldset } from 'primereact/fieldset';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IReto } from 'app/shared/model/reto.model';
import { getEntity as getReto } from 'app/entities/reto/reto.reducer';
import { ITipoIdea } from 'app/shared/model/tipo-idea.model';
import { getEntities as getTipoIdeas } from 'app/entities/tipo-idea/tipo-idea.reducer';
import { IEntidad } from 'app/shared/model/entidad.model';
import { getEntities as getEntidads } from 'app/entities/entidad/entidad.reducer';
import { IIdea } from 'app/shared/model/idea.model';
import { getEntity, updateEntity, createEntity, reset, updateEntitySinRespuesta } from './idea.reducer';
import { Col, Row } from 'reactstrap';
import { Button } from 'primereact/button';
import { TextFormat, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import {
  getEntitiesByIdIdea,
  getEntities as getIdeasComentarios,
  createEntity as nuevoComentario,
  deleteEntity as deleteComentario,
  reset as resetIdeaComentario,
} from 'app/entities/comenetarios-idea/comenetarios-idea.reducer';
import { getEntity as getEcosistema } from 'app/entities/ecosistema/ecosistema.reducer';
import { Card } from 'primereact/card';
import { Toolbar } from 'primereact/toolbar';
import { useForm } from 'react-hook-form';
import { Accordion, AccordionTab } from 'primereact/accordion';
import { Skeleton } from 'primereact/skeleton';

const VistaprincipalIdeaReto11 = (
  props: RouteComponentProps<{
    id: string;
    idreto: string;
    index: string;
    idecosistema: string;
  }>
) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const users = useAppSelector(state => state.userManagement.users);
  const reto = useAppSelector(state => state.reto.entity);
  const tipoIdeas = useAppSelector(state => state.tipoIdea.entities);
  const entidads = useAppSelector(state => state.entidad.entities);
  const ideaEntity = useAppSelector(state => state.idea.entity);
  const loading = useAppSelector(state => state.idea.loading);
  const updating = useAppSelector(state => state.idea.updating);
  const updateSuccess = useAppSelector(state => state.idea.updateSuccess);
  const ecosistemaEntity = useAppSelector(state => state.ecosistema.entity);

  const comenetariosIdeaEntities = useAppSelector(state => state.comenetariosIdea.entities);
  const loadingCOmentario = useAppSelector(state => state.comenetariosIdea.loading);
  const updatingComentario = useAppSelector(state => state.comenetariosIdea.updating);
  const updateSuccessComentario = useAppSelector(state => state.comenetariosIdea.updateSuccess);

  const account = useAppSelector(state => state.authentication.account);
  const [isNewIdeaComentario, setNew] = useState(true);

  const [actualizarVistossoloUnavez, setVistos] = useState(parseInt(localStorage.getItem('vistos2'), 10) || 0);

  useEffect(() => {
    dispatch(getEntitiesByIdIdea(props.match.params.id));
    dispatch(getReto(props.match.params.idreto));

    if (actualizarVistossoloUnavez === 0) {
      if (ideaEntity.user?.login !== account.login) dispatch(updateEntitySinRespuesta(props.match.params.id));
    } else dispatch(getEntity(props.match.params.id));

    setVistos(parseInt(localStorage.getItem('vistos2'), 10));

    return () => {
      localStorage.setItem('vistos2', '0');
    };
  }, []);

  useEffect(() => {
    if (updateSuccessComentario) dispatch(getEntitiesByIdIdea(props.match.params.id));
  }, [updateSuccessComentario]);

  useEffect(() => {
    if (updateSuccess) dispatch(getEntity(props.match.params.id));
    localStorage.setItem('vistos2', '1');
    setVistos(parseInt(localStorage.getItem('vistos2'), 10));
  }, [updateSuccess]);

  const saveEntityComentariosIdea = values => {
    const entity = {
      ...values,
      user: account,
      fechaInscripcion: new Date(),
      idea: ideaEntity,
    };
    dispatch(nuevoComentario(entity));
  };
  const saveEntity = () => {
    const acept = ideaEntity.aceptada ? false : true;

    const entity = {
      ...ideaEntity,
      aceptada: acept,
    };

    dispatch(updateEntity(entity));
  };

  const atras = () => {
    props.history.push(
      '/entidad/reto/reto_ideas/' + props.match.params.idreto + '/' + props.match.params.idecosistema + '/' + props.match.params.index
    );
  };
  const defaultValuesIdeaComentarios = () => (isNewIdeaComentario ? {} : { user: account, idea: ideaEntity });
  const leftToolbarTemplate = () => {
    return (
      <React.Fragment>
        <div className="mt-2 my-2">
          <Button label="Atrás" icon="pi pi-arrow-left" className="p-button-secondary mr-2" onClick={atras} />
        </div>
      </React.Fragment>
    );
  };

  const renderDevRibbon = () =>
    ideaEntity.aceptada === true ? (
      <div className="ribbon11 dev">
        <a href="">Aceptada</a>
      </div>
    ) : null;

  const borrarComentario = comentario => {
    dispatch(deleteComentario(comentario.id));
  };
  return (
    <div className=" mt-3 mb-4">
      <Toolbar className="mb-4 " left={leftToolbarTemplate}></Toolbar>

      <div className="grid  grid-nogutter surface-0 text-800">
        {renderDevRibbon()}
        <div className="col-12 lg:col-6">
          <div className="flex p-2  md:text-left flex align-items-center ">
            <section className="flex flex-column">
              <h1 className="text-2xl sm:text-4xl font-bold text-gray-900 line-height-2">
                <span className="font-light block">{ideaEntity.titulo}</span>
              </h1>
              <p className="font-normal text-xl sm:text-2xl line-height-3 md:mt-3 text-gray-700">Autor: {ideaEntity.autor}</p>
              <p className="font-normal text-xl sm:text-2xl line-height-3 md:mt-3 text-gray-700">
                Fecha Inscripción:{' '}
                {ideaEntity.fechaInscripcion ? (
                  <TextFormat type="date" value={ideaEntity.fechaInscripcion} format={APP_LOCAL_DATE_FORMAT} />
                ) : null}
              </p>
              <p className="font-normal text-xl sm:text-2xl line-height-3 md:mt-3 text-gray-700">Entidad: {ideaEntity?.entidad?.entidad}</p>

              <span className="text-500">
                Visitas
                <span className="text-green-500 font-medium"> {ideaEntity.visto}</span>
              </span>

              <br></br>
              <span className="text-500">
                Comentarios
                <span className="text-green-500 font-medium"> {comenetariosIdeaEntities.length}</span>
              </span>
              {reto.user?.id === account.id && (
                <div className="flex justify-content-start">
                  <Button
                    label={ideaEntity.aceptada ? 'No Aceptar' : 'Aceptar'}
                    icon={ideaEntity.aceptada ? 'pi pi-thumbs-down ' : ' pi pi-thumbs-up-fill'}
                    className={ideaEntity.aceptada ? 'p-button-danger mr-2 mt-2' : 'p-button-success mr-2 mt-2'}
                    onClick={saveEntity}
                    disabled={updating}
                  />
                </div>
              )}
            </section>
          </div>
        </div>

        <div className="col-12 lg:col-6">
          <div className="flex  sm:justify-content-center justify-content-end overflow-hidden">
            {loading ? (
              <Skeleton width="35rem" height="30rem"></Skeleton>
            ) : (
              <img
                src={`content/uploads/${ideaEntity.fotoContentType}`}
                style={{ maxHeight: '500px' }}
                className=" flex w-9 sm:w-8 sm:justify-content-center md:w-10 xl:w-10 shadow-2 block xl:block mt-4 border-round"
              />
            )}
          </div>
        </div>
        <div className="xl:col-12 sm:col-12 md:col-12"></div>
        <Accordion
          activeIndex={0}
          expandIcon="pi pi-chevron-down"
          collapseIcon="pi pi-chevron-up"
          className="mt-3 xl:col-12 sm:col-12 md:col-12 lg:col-12"
        >
          <AccordionTab className="text-100 " header="Descripción">
            <div className="justify-content-start">
              <div className="flex flex-wrap align-items-center justify-content-center card-container">
                <div className="flex surface-overlay  w-full my-3 p-3">
                  <p className="text-xl" style={{ whiteSpace: 'pre-line' }}>
                    {ideaEntity.descripcion}
                  </p>
                </div>
              </div>
            </div>
          </AccordionTab>
        </Accordion>

        <div>
          <Col md="12" className="card ">
            {!updateSuccessComentario && (
              <ValidatedForm defaultValues={defaultValuesIdeaComentarios()} onSubmit={saveEntityComentariosIdea}>
                <div className="flex align-items-center text-xs sm:text-sm mb-3">
                  <i className="pi pi-check-circle text-aling-justify  text-green-500 mr-2"></i>
                  <span>
                    Este sitio se reserva el derecho de la publicación de los comentarios. No se harán visibles aquellos que sean
                    denigrantes, ofensivos, difamatorios, que estén fuera de contexto o atenten contra la dignidad de una persona o grupo
                    social. Recomendamos brevedad en sus planteamientos.
                  </span>
                </div>

                <ValidatedField
                  id="comenetarios-idea-comentario"
                  name="comentario"
                  placeholder="Haga su comentario"
                  data-cy="comentario"
                  type="textarea"
                  validate={{
                    required: { value: true, message: translate('entity.validation.required') },
                  }}
                />
                <Button
                  className="mb-2"
                  color="primary"
                  id="save-entity"
                  data-cy="entityCreateSaveButton"
                  type="submit"
                  disabled={updatingComentario || !reto.publico || ideaEntity.user?.login === account.login}
                >
                  Enviar Comentario
                </Button>
              </ValidatedForm>
            )}
          </Col>
        </div>

        <div className="col-12 flex flex-column ">
          {comenetariosIdeaEntities && comenetariosIdeaEntities.length > 0
            ? comenetariosIdeaEntities.map((comenetariosIdea, i) => (
                <Card key={comenetariosIdea.id} className=" relative shadow-6 mt-2">
                  <div className="text-900 font-medium text-lg mt-2">
                    {comenetariosIdea.user?.firstName + ' ' + comenetariosIdea.user?.lastName}
                  </div>
                  <p className="mt-2">{comenetariosIdea.comentario}</p>
                  <div className="flex align-items-center mt-3">
                    <i className="pi pi-calendar mr-2"></i>
                    <span>
                      <TextFormat type="date" value={comenetariosIdea.fechaInscripcion} format={APP_LOCAL_DATE_FORMAT} />
                    </span>
                  </div>
                  {reto.user?.login === account.login && (
                    <div className="flex align-items-center justify-content-end  absolute top-0 right-0 mr-2 mt-2 ">
                      <Button
                        icon="pi pi-trash"
                        className="p-button-danger"
                        style={{ width: '25px', height: '25px' }}
                        onClick={() => borrarComentario(comenetariosIdea)}
                        disabled={updatingComentario}
                      />
                    </div>
                  )}
                </Card>
              ))
            : !loadingCOmentario && <div className="alert alert-warning">No hay Comentarios...</div>}
        </div>
      </div>
    </div>
  );
};

export default VistaprincipalIdeaReto11;
