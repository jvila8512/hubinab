import { Skeleton } from 'primereact/skeleton';
import React from 'react';

const SkeletonEcositemas = () => {
  return (
    <div className=" grid grid-nogutter surface-0 mt-2">
      <div className="flex flex-column sm:flex-row col-12 sm:col-6">
        <div className="card mt-4 border-round-3xl col-12 sm:h-auto ">
          <div className="flex justify-content-start ">
            <div className="text-900 text-2xl text-blue-600 font-medium ">
              <Skeleton width="10rem" className="mb-2"></Skeleton>{' '}
            </div>
          </div>

          <div className="flex justify-content-start">
            <div className="text-400  text-blue-800 font-medium ">
              <Skeleton width="6rem" className="mb-2"></Skeleton>
            </div>
          </div>

          <div className="flex ">
            <Skeleton width="100px" height="100px" className="mr-2"></Skeleton>

            <div style={{ flex: '1' }}>
              <Skeleton width="95%" className="mb-1"></Skeleton>
              <Skeleton width="95%" className="mb-1"></Skeleton>
              <Skeleton width="95%" className="mb-4"></Skeleton>
              <div className="flex flex-row">
                <Skeleton width="10%" className="mr-4"></Skeleton>
                <Skeleton width="10%"></Skeleton>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div className="flex flex-column sm:flex-row col-12 sm:col-6">
        <div className="card mt-4 border-round-3xl col-12 sm:h-auto ">
          <div className="flex justify-content-start ">
            <div className="text-900 text-2xl text-blue-600 font-medium ">
              <Skeleton width="10rem" className="mb-2"></Skeleton>{' '}
            </div>
          </div>

          <div className="flex justify-content-start">
            <div className="text-400  text-blue-800 font-medium ">
              <Skeleton width="6rem" className="mb-2"></Skeleton>
            </div>
          </div>

          <div className="flex ">
            <Skeleton width="100px" height="100px" className="mr-2"></Skeleton>

            <div style={{ flex: '1' }}>
              <Skeleton width="95%" className="mb-1"></Skeleton>
              <Skeleton width="95%" className="mb-1"></Skeleton>
              <Skeleton width="95%" className="mb-4"></Skeleton>
              <div className="flex flex-row">
                <Skeleton width="10%" className="mr-4"></Skeleton>
                <Skeleton width="10%"></Skeleton>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div className="flex flex-column sm:flex-row col-12 sm:col-6">
        <div className="card mt-4 border-round-3xl col-12 sm:h-auto ">
          <div className="flex justify-content-start ">
            <div className="text-900 text-2xl text-blue-600 font-medium ">
              <Skeleton width="10rem" className="mb-2"></Skeleton>{' '}
            </div>
          </div>

          <div className="flex justify-content-start">
            <div className="text-400  text-blue-800 font-medium ">
              <Skeleton width="6rem" className="mb-2"></Skeleton>
            </div>
          </div>

          <div className="flex ">
            <Skeleton width="100px" height="100px" className="mr-2"></Skeleton>

            <div style={{ flex: '1' }}>
              <Skeleton width="95%" className="mb-1"></Skeleton>
              <Skeleton width="95%" className="mb-1"></Skeleton>
              <Skeleton width="95%" className="mb-4"></Skeleton>
              <div className="flex flex-row">
                <Skeleton width="10%" className="mr-4"></Skeleton>
                <Skeleton width="10%"></Skeleton>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div className="flex flex-column sm:flex-row col-12 sm:col-6">
        <div className="card mt-4 border-round-3xl col-12 sm:h-auto ">
          <div className="flex justify-content-start ">
            <div className="text-900 text-2xl text-blue-600 font-medium ">
              <Skeleton width="10rem" className="mb-2"></Skeleton>{' '}
            </div>
          </div>

          <div className="flex justify-content-start">
            <div className="text-400  text-blue-800 font-medium ">
              <Skeleton width="6rem" className="mb-2"></Skeleton>
            </div>
          </div>

          <div className="flex ">
            <Skeleton width="100px" height="100px" className="mr-2"></Skeleton>

            <div style={{ flex: '1' }}>
              <Skeleton width="95%" className="mb-1"></Skeleton>
              <Skeleton width="95%" className="mb-1"></Skeleton>
              <Skeleton width="95%" className="mb-4"></Skeleton>
              <div className="flex flex-row">
                <Skeleton width="10%" className="mr-4"></Skeleton>
                <Skeleton width="10%"></Skeleton>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default SkeletonEcositemas;
