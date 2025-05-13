import React from 'react';

const Crowdsourcing = () => {
  return (
    <div>
      <div
        className="col-12 mt-8 mb-8 p-2 md:p-8"
        style={{
          borderRadius: '20px',
          background:
            'linear-gradient(0deg, rgba(255, 255, 255, 0.6), rgba(255, 255, 255, 0.6)), radial-gradient(77.36% 256.97% at 77.36% 57.52%, #EFE1AF 0%, #C3DCFA 100%)',
        }}
      >
        <div className="flex flex-column justify-content-center align-items-center text-center px-3 py-3 md:py-0">
          <h1 className="text-2xl text-gray-900 mb-2">Página de Crowdsourcing</h1>
        </div>
      </div>
    </div>
  );
};

export default Crowdsourcing;
