import React, { useEffect, useState } from 'react';

import { DataView, DataViewLayoutOptions } from 'primereact/dataview';
import { Rating } from 'primereact/rating';
import { Tag } from 'primereact/tag';
import { Skeleton } from 'primereact/skeleton';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { Link, RouteComponentProps } from 'react-router-dom';
import { TextFormat, Translate } from 'react-jhipster';

import { APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Alert } from 'reactstrap';
import { getEntitiesbyReto } from './idea.reducer';
import { ToggleButton } from 'primereact/togglebutton';
import { Button } from 'primereact/button';

const vistaIdeasReto = props => {
  const dispatch = useAppDispatch();
  const [forma, setLayout] = useState(null);

  const ideasList = useAppSelector(state => state.idea.entities);
  const loading = useAppSelector(state => state.idea.loading);

  const [ideas, setIdeas] = useState([]);

  useEffect(() => {
    dispatch(getEntitiesbyReto(props.id));
    setLayout(props.layout);
    setIdeas(ideasList);
  }, []);

  useEffect(() => {
    if (!loading) setIdeas(ideasList);
  }, [loading]);

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

  const listItem = reto => {
    return (
      <div className="col-12 border-round-xl shadow-4 mb-2">
        <div className="flex flex-column xl:flex-row xl:align-items-start p-4 gap-4">
          <img
            className="w-9 sm:w-10rem xl:w-8rem shadow-2 block xl:block mx-auto border-round"
            src={`data:${reto.urlFotoContentType};base64,${reto.urlFoto}`}
            alt={reto.titulo}
          />
          <div className="flex flex-column sm:flex-row justify-content-between align-items-center xl:align-items-start flex-1 gap-4">
            <div className="flex flex-column align-content-end align-items-center sm:align-items-start gap-3">
              <div className="text-base font-bold ">{reto.titulo}</div>

              <div className=" surface-overlay w-full h-3rem mb-2  overflow-hidden text-overflow-ellipsis">{reto.descripcion}</div>

              <div className="flex flex-column sm:flex-row justify-content-between align-items-center gap-3">
                <div className="flex align-items-center gap-3">
                  <span className="flex align-items-center gap-2">
                    <i className="pi pi-calendar"></i>
                    {reto.fechaCreada ? <TextFormat type="date" value={reto.fechaCreada} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </span>
                </div>
              </div>
            </div>
            <div className="flex sm:flex-column align-items-center sm:align-items-end gap-3 sm:gap-2"></div>
          </div>
        </div>
      </div>
    );
  };
  const gridItem = idea => {
    return (
      <div className="col-12  sm:col-6 lg:col-12 xl:col-4 p-2 ">
        <div className="p-4 border-1 surface-border surface-card border-round">
          <div className="flex flex-column align-items-center gap-3 py-5">
            <Link to={`/idea/ver_Idea/${idea.id}/${props.id}/${props.idecosistema}`} id="jh-tre" data-cy="ideasReto">
              <img
                className="w-9 sm:w-16rem xl:w-20rem shadow-2 block xl:block mx-auto border-round"
                src={`data:${idea.fotoContentType};base64,${idea.foto}`}
                alt={idea.titulo}
              />
            </Link>
          </div>
          <div className="text-2xl  text-800">{idea.titulo}</div>
          <div className="text-1xl  text-600">Autor: {idea.titulo}</div>
          <div className="text-1xl  text-600">Tipo Idea: {idea.titulo}</div>

          <div className="flex flex-column sm:flex-row justify-content-between align-items-center gap-3 mb-2">
            <div className="flex align-items-center gap-3">
              <div className="flex align-items-center mt-2 gap-3">
                <span className="flex align-items-center gap-2">
                  <i className="pi pi-calendar"></i>
                  {idea.fechaInscripcion ? <TextFormat type="date" value={idea.fechaInscripcion} format={APP_LOCAL_DATE_FORMAT} /> : null}
                </span>
              </div>
            </div>
          </div>
          <Tag value={getActivo(idea)} severity={getSeverity(idea)}></Tag>
          <div className="flex justify-content-end flex-wrap  ">
            <div className="flex align-items-center justify-content-center  m-2">
              <Button icon="pi  pi-thumbs-up-fill" className="p-button-rounded p-button-secondary" aria-label="Bookmark" />
            </div>
          </div>

          <div className="flex justify-content-start flex-wrap  ">
            <div className="flex align-items-center mt-2 gap-3">
              <span className="flex align-items-center gap-2">
                <i className="pi pi-thumbs-up-fill"></i>
                Tu y 30 personas mas.
              </span>
            </div>
          </div>
        </div>
      </div>
    );
  };

  const itemTemplate = (reto, layout1) => {
    if (!reto) {
      return;
    }

    if (layout1 === 'list') return listItem(reto);
    else if (layout1 === 'grid') return gridItem(reto);
  };

  const header = () => {
    return <></>;
  };

  return (
    <>
      {ideasList && ideasList.length > 0 ? (
        <DataView value={ideas} layout={forma} itemTemplate={itemTemplate} emptyMessage="No hay Ideas" lazy />
      ) : (
        !loading && <div className="alert alert-warning mt-4">No hay Ideas.</div>
      )}
    </>
  );
};

export default vistaIdeasReto;
