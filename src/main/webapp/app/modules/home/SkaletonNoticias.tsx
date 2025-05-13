import { Skeleton } from 'primereact/skeleton';
import React from 'react';

const SkaletonNoticias = () => {
  return (
    <div>
      <div className="surface-section px-1 py-4 md:px-6 text-center">
        <div className="mb-3 font-bold text-900 text-2xl sm:text-5xl  ">
          <span className="text-blue-600">Actualidad</span>
        </div>
        <div className="flex align-items-center justify-content-center grid">
          <div className=" col-12 md:col-4 mb-4 px-5 ">
            <div className="flex justify-content-center">
              <Skeleton width="80%" height="190px"></Skeleton>
            </div>

            <div className="flex justify-content-center">
              <Skeleton width="12rem" height="2rem" className=" flex mt-2 mb-2"></Skeleton>
            </div>
            <div className="flex  justify-content-center">
              <div className="flex  flex-column justify-content-center">
                <Skeleton width="300px" className=" mb-2"></Skeleton>
                <Skeleton width="300px" className=" mb-2"></Skeleton>
                <Skeleton width="300px" className=" mb-2"></Skeleton>
              </div>
            </div>

            <div className="flex justify-content-start">
              <div className="flex ">
                <Skeleton size="2rem" className="mr-2"></Skeleton>
                <div className="mt-2" style={{ flex: '1' }}>
                  <Skeleton width="80px"></Skeleton>
                </div>
              </div>
            </div>
          </div>
          <div className=" col-12 md:col-4 mb-4 px-5 ">
            <div className="flex justify-content-center">
              <Skeleton width="80%" height="190px"></Skeleton>
            </div>

            <div className="flex justify-content-center">
              <Skeleton width="12rem" height="2rem" className=" flex mt-2 mb-2"></Skeleton>
            </div>
            <div className="flex  justify-content-center">
              <div className="flex  flex-column justify-content-center">
                <Skeleton width="300px" className=" mb-2"></Skeleton>
                <Skeleton width="300px" className=" mb-2"></Skeleton>
              </div>
            </div>

            <div className="flex justify-content-start">
              <div className="flex ">
                <Skeleton size="2rem" className="mr-2"></Skeleton>
                <div className="mt-2" style={{ flex: '1' }}>
                  <Skeleton width="80px"></Skeleton>
                </div>
              </div>
            </div>
          </div>
          <div className=" col-12 md:col-4 mb-4 px-5 ">
            <div className="flex justify-content-center">
              <Skeleton width="80%" height="190px"></Skeleton>
            </div>

            <div className="flex justify-content-center">
              <Skeleton width="12rem" height="2rem" className=" flex mt-2 mb-2"></Skeleton>
            </div>
            <div className="flex  justify-content-center">
              <div className="flex  flex-column justify-content-center">
                <Skeleton width="300px" className=" mb-2"></Skeleton>
                <Skeleton width="300px" className=" mb-2"></Skeleton>
              </div>
            </div>

            <div className="flex justify-content-start">
              <div className="flex ">
                <Skeleton size="2rem" className="mr-2"></Skeleton>
                <div className="mt-2" style={{ flex: '1' }}>
                  <Skeleton width="80px"></Skeleton>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default SkaletonNoticias;
