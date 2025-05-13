import axios from 'axios';
import { Storage } from 'react-jhipster';

const TIMEOUT = 2 * 60 * 1000;
axios.defaults.timeout = TIMEOUT;
axios.defaults.baseURL = SERVER_API_URL;

const setupAxiosInterceptors = onUnauthenticated => {
  const onRequestSuccess = config => {
    const token = Storage.local.get('jhi-authenticationToken') || Storage.session.get('jhi-authenticationToken');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  };

  const onResponseSuccess = response => response;

  const onResponseError = err => {
    const status = err.status || (err.response ? err.response.status : 0);

    if (status === 403) {
      const customErrorMessage = 'No autorizado. No tiene permisos para acceder a la página.';
      err.response.data.message = customErrorMessage;
      err.message = customErrorMessage;
    }

    if (status === 401) {
      const customErrorMessage = 'Su sesión ha caducado.';
      err.message = customErrorMessage;
      err.response.data.message = customErrorMessage;
      onUnauthenticated();
    }
    if (status === 500) {
      const customErrorMessage1 = err.response.data.detail;

      // Verificar si la cadena 'Error interno del servidor' está presente en customErrorMessage
      if (customErrorMessage1.includes('delete from')) {
        // eslint-disable-next-line no-console
        console.log('La cadena "Error interno del servidor" está presente en customErrorMessage');
        const customErrorMessage = 'No se puede eliminar. Contacte al Administrador.';
        err.response.data.message = customErrorMessage;
      } else {
        // eslint-disable-next-line no-console
        console.log('Error interno del servidor.............');
        const customErrorMessage = 'Su operación no se puede realizar. Contacte al Administrador.';
        err.response.data.message = customErrorMessage;
        err.message = customErrorMessage;
      }
    }
    if (status === 504) {
      const customErrorMessage = 'Error en el servidor...';
      err.response.data.message = 'Error interno del servidor..';
    }
    if (err.code === 'ECONNABORTED') {
      // Aquí puedes personalizar el mensaje de error cuando se excede el tiempo de espera
      const customErrorMessage = 'Conexión lenta. Se ha excedido el tiempo de espera de la solicitud';
      err.message = customErrorMessage;
      err.response.data.message = customErrorMessage;
    }

    return Promise.reject(err);
  };
  axios.interceptors.request.use(onRequestSuccess);
  axios.interceptors.response.use(onResponseSuccess, onResponseError);
};

export default setupAxiosInterceptors;
