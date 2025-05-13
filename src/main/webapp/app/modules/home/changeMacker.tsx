import { Button } from 'primereact/button';
import { Card } from 'primereact/card';
import { Carousel } from 'primereact/carousel';
import React, { useEffect } from 'react';
import { Link } from 'react-router-dom';
import { Row } from 'reactstrap';
import { getEntities } from 'app/entities/change-macker/change-macker.reducer';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import SkaletonChangeMacker from './SkaletonChangeMacker';

const changeMacker = () => {
  const dispatch = useAppDispatch();
  const changeMackerList = useAppSelector(state => state.changeMacker.entities);
  const loading = useAppSelector(state => state.changeMacker.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  return (
    <>
      {loading ? (
        <SkaletonChangeMacker />
      ) : changeMackerList && changeMackerList.length > 0 ? (
        <div className="surface-section px-4 py-2 md:px-6 lg:px-8 text-center">
          <div className="mb-3 font-bold text-900 text-5xl  ">
            <span className="text-blue-600">Qué dicen los ChangeMacker</span>
          </div>

          <div className="flex align-items-center justify-content-center grid">
            {changeMackerList.map((changeMacker1, i) => (
              <div key={changeMacker1.id} className=" col-12 md:col-4 mb-4 px-5 ">
                <div className="mb-3">
                  <img
                    src={`data:${changeMacker1.fotoContentType};base64,${changeMacker1.foto}`}
                    style={{ height: '300px', width: '200px' }}
                  />
                </div>
                <div className="mb-3 font-bold text-2xl">
                  <span className="  text-blue-600">{changeMacker1.nombre}</span>
                </div>

                <div>
                  <h5 className="mb-3 mt-3">{changeMacker1.tema}</h5>
                </div>

                <div className="align-items-center">
                  <Link
                    to={`/change-macker/vista-changeMacker`}
                    className="btn btn-primary rounded-pill align-items-center"
                    id="jh-trehfgh"
                    data-cy="ecosistemasgfgf"
                  >
                    <span className="font-bold text-2xl p-2 font-white ">Enterate</span>
                  </Link>
                </div>
              </div>
            ))}
          </div>
        </div>
      ) : null}
    </>
  );
};

export default changeMacker;
