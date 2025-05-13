import { Skeleton } from 'primereact/skeleton';
import React from 'react';

const SkaletonChangeMacker = () => {
  return (
    <div>
      <div className="surface-section px-4 py-8 md:px-6 lg:px-8 text-center">
        <div className="mb-3 font-bold text-900 text-5xl  ">
          <span className="text-blue-600">Qué dicen los ChangeMacker</span>
        </div>
        <div className="flex align-items-center justify-content-center grid">
          <div className=" col-12 md:col-4 mb-4 px-5 ">
            <div className="flex justify-content-center">
              <Skeleton width="80%" height="190px"></Skeleton>
            </div>

            <div className="flex justify-content-center">
              <Skeleton width="12rem" height="2rem" className=" flex mt-2 mb-2"></Skeleton>
            </div>
            <div className="flex   justify-content-center ">
              <div className="flex  ">
                <Skeleton width="400px" className=" mb-2"></Skeleton>
              </div>
            </div>
            <div className="flex   justify-content-center ">
              <div className="flex  ">
                <Skeleton width="400px" className=" mb-2"></Skeleton>
              </div>
            </div>

            <div className="flex justify-content-center">
              <div className="flex ">
                <Skeleton width="10rem" height="3rem" borderRadius="16px" className="mb-2"></Skeleton>
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
            <div className="flex   justify-content-center ">
              <div className="flex  ">
                <Skeleton width="400px" className=" mb-2"></Skeleton>
              </div>
            </div>
            <div className="flex   justify-content-center ">
              <div className="flex  ">
                <Skeleton width="400px" className=" mb-2"></Skeleton>
              </div>
            </div>

            <div className="flex justify-content-center">
              <div className="flex ">
                <Skeleton width="10rem" height="3rem" borderRadius="16px" className="mb-2"></Skeleton>
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
            <div className="flex   justify-content-center ">
              <div className="flex  ">
                <Skeleton width="400px" className=" mb-2"></Skeleton>
              </div>
            </div>
            <div className="flex   justify-content-center ">
              <div className="flex  ">
                <Skeleton width="400px" className=" mb-2"></Skeleton>
              </div>
            </div>

            <div className="flex justify-content-center">
              <div className="flex ">
                <Skeleton width="10rem" height="3rem" borderRadius="16px" className="mb-2"></Skeleton>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default SkaletonChangeMacker;
