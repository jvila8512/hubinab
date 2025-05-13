import { useAppDispatch, useAppSelector } from 'app/config/store';

import React, { useEffect, useState } from 'react';
import { Translate } from 'react-jhipster';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Row } from 'reactstrap';
import { getEntities } from './ecosistema.reducer';
import EcositemasCard from './ecositemasCard';
import { getEntities as getUsuarioEcosistema, getEntityByUserId } from 'app/entities/usuario-ecosistema/usuario-ecosistema.reducer';

import { Toolbar } from 'primereact/toolbar';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Button } from 'primereact/button';
import { Skeleton } from 'primereact/skeleton';
import EcositemasCard1 from './ecositemasCard1';

const TodosCard = props => {
  const dispatch = useAppDispatch();

  const ecosistemaList = useAppSelector(state => state.ecosistema.entities);
  const loading = useAppSelector(state => state.ecosistema.loading);

  const usuarioEcosistemaList = useAppSelector(state => state.usuarioEcosistema.entity);

  const account = useAppSelector(state => state.authentication.account);
  const [mostrar, setMostrar] = useState(false);

  useEffect(() => {
    dispatch(getEntities({}));
    dispatch(getEntityByUserId(account.id));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };
  const atras = () => {
    props.history.push('/usuario-panel');
  };
  const leftToolbarTemplate = () => {
    return (
      <React.Fragment>
        <div className="my-2">
          <Button label="Atrás" icon="pi pi-arrow-left" className="p-button-secondary mr-2" onClick={atras} />
        </div>
        <div className="text-2xl font-bold text-900 ml-8 text-center text-blue-600">Ecosistemas </div>
      </React.Fragment>
    );
  };

  return (
    <>
      {loading ? (
        <div className="flex flex-row  justify-content-center gap-3 grid mt-4">
          <div className="flex flex-column  h-28rem w-18rem max-w-30rem max-h-30rem  p-4 border-round-xl shadow-4 mb-2 relative">
            <div className="flex flex-column align-items-center gap-3 py-1 mb-2">
              <Skeleton width="100%" height="150px"></Skeleton>
            </div>

            <Skeleton className="mb-2"></Skeleton>
            <Skeleton width="8rem" className="mb-2"></Skeleton>
            <Skeleton width="6rem" className="mb-2"></Skeleton>
            <Skeleton width="4rem" className="mb-2"></Skeleton>

            <div className="flex absolute bottom-0 left-0 mb-8 ml-4">
              <Skeleton width="4rem" height="2rem"></Skeleton>
            </div>
            <div className="flex justify-content-end absolute bottom-0 right-0 mb-8 mr-2">
              <Skeleton shape="circle" size="1.5rem" className="mr-2"></Skeleton>
            </div>

            <div className="flex justify-content-end absolute bottom-0 right-0 mb-4 mr-2">
              <Skeleton shape="circle" size="2rem" className="mr-2"></Skeleton>
            </div>

            <div className="flex justify-content-start flex-wrap  absolute bottom-0 mb-4">
              <Skeleton width="4rem" className="mb-2"></Skeleton>
            </div>
          </div>
          <div className="flex flex-column  h-28rem w-18rem max-w-30rem max-h-30rem  p-4 border-round-xl shadow-4 mb-2 relative">
            <div className="flex flex-column align-items-center gap-3 py-1 mb-2">
              <Skeleton width="100%" height="150px"></Skeleton>
            </div>

            <Skeleton className="mb-2"></Skeleton>
            <Skeleton width="8rem" className="mb-2"></Skeleton>
            <Skeleton width="6rem" className="mb-2"></Skeleton>
            <Skeleton width="4rem" className="mb-2"></Skeleton>

            <div className="flex absolute bottom-0 left-0 mb-8 ml-4">
              <Skeleton width="4rem" height="2rem"></Skeleton>
            </div>

            <div className="flex justify-content-start flex-wrap  absolute bottom-0 mb-4">
              <Skeleton width="4rem" className="mb-2"></Skeleton>
            </div>
          </div>
          <div className="flex flex-column  h-28rem w-18rem max-w-30rem max-h-30rem  p-4 border-round-xl shadow-4 mb-2 relative">
            <div className="flex flex-column align-items-center gap-3 py-1 mb-2">
              <Skeleton width="100%" height="150px"></Skeleton>
            </div>

            <Skeleton className="mb-2"></Skeleton>
            <Skeleton width="8rem" className="mb-2"></Skeleton>
            <Skeleton width="6rem" className="mb-2"></Skeleton>
            <Skeleton width="4rem" className="mb-2"></Skeleton>

            <div className="flex absolute bottom-0 left-0 mb-8 ml-4">
              <Skeleton width="4rem" height="2rem"></Skeleton>
            </div>
            <div className="flex justify-content-end absolute bottom-0 right-0 mb-8 mr-2">
              <Skeleton shape="circle" size="1.5rem" className="mr-2"></Skeleton>
            </div>

            <div className="flex justify-content-end absolute bottom-0 right-0 mb-4 mr-2">
              <Skeleton shape="circle" size="2rem" className="mr-2"></Skeleton>
            </div>

            <div className="flex justify-content-start flex-wrap  absolute bottom-0 mb-4">
              <Skeleton width="4rem" className="mb-2"></Skeleton>
            </div>
          </div>
        </div>
      ) : (
        <div className="card mt-4">
          <Toolbar className="mb-2" left={leftToolbarTemplate}></Toolbar>

          <div className="col-12">
            <div className="flex flex-column md:flex-row align-items-center justify-content-center grid gap-3">
              {ecosistemaList.length > 0
                ? ecosistemaList.map(
                    (eco, i) =>
                      eco.activo && (
                        <div key={`card-${i}`} className="flex flex-column sm:flex-row  justify-content-center card-container  mt-2 ">
                          <EcositemasCard1
                            ecosistema={eco}
                            userEcosistema={usuarioEcosistemaList}
                            lista={...usuarioEcosistemaList?.ecosistemas}
                            history={props.history}
                          />
                        </div>
                      )
                  )
                : ''}
            </div>
          </div>
        </div>
      )}
    </>
  );
};

export default TodosCard;
