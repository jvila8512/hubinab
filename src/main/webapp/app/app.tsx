import 'react-toastify/dist/ReactToastify.css';
import './app.scss';
import React, { useEffect, useState } from 'react';
import { Card } from 'reactstrap';
import { BrowserRouter as Router, useHistory, Redirect } from 'react-router-dom';
import { ToastContainer, toast } from 'react-toastify';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getSession } from 'app/shared/reducers/authentication';
import { getProfile } from 'app/shared/reducers/application-profile';
import { setLocale } from 'app/shared/reducers/locale';
import Header from 'app/shared/layout/header/header';
import Footer from 'app/shared/layout/footer/footer';
import { hasAnyAuthority } from 'app/shared/auth/private-route';
import ErrorBoundary from 'app/shared/error/error-boundary';
import { AUTHORITIES } from 'app/config/constants';
import AppRoutes from 'app/routes';
import { logout } from 'app/shared/reducers/authentication';
import { useSelector } from 'react-redux';
import { getEntitiesActivas } from 'app/entities/comunidad/comunidad.reducer';

const baseHref = document.querySelector('base').getAttribute('href').replace(/\/$/, '');
const TIMEOUT = 900000; // 15 minutos en milisegundos 600000
export const App = () => {
  const dispatch = useAppDispatch();
  const [isLoggedIn, setIsLoggedIn] = useState(true);
  const [isAutenticate, setIsAutenticate] = useState(true);
  const logoutUrl = useAppSelector(state => state.authentication.logoutUrl);
  const history = useHistory();
  const logoutSubmitHandler = () => {
    // Aquí puedes llamar a una función para cerrar la sesión isAuthenticated
    if (!isAutenticate) {
      toast.error('Cerrada la  Sesion por inactividad');
      dispatch(logout());
      window.location.href = '/login';
    }
  };
  const currentLocale = useAppSelector(state => state.locale.currentLocale);
  const isAuthenticated1 = useAppSelector(state => state.authentication.isAuthenticated);

  const isAdmin = useAppSelector(state => hasAnyAuthority(state.authentication.account.authorities, [AUTHORITIES.ADMIN]));
  const ribbonEnv = useAppSelector(state => state.applicationProfile.ribbonEnv);
  const isInProduction = useAppSelector(state => state.applicationProfile.inProduction);
  const isOpenAPIEnabled = useAppSelector(state => state.applicationProfile.isOpenAPIEnabled);
  const account = useAppSelector(state => state.authentication.account);

  const comunidadList = useAppSelector(state => state.comunidad.entities);
  useEffect(() => {
    dispatch(getSession());
    dispatch(getProfile());
    dispatch(getEntitiesActivas());
  }, []);

  useEffect(() => {
    if (isAutenticate) setIsAutenticate(false);
    let timer;

    const resetTimer = () => {
      if (timer) {
        clearTimeout(timer);
      }

      timer = setTimeout(() => {
        logoutSubmitHandler();
      }, TIMEOUT);
    };

    const handleUserActivity = () => {
      resetTimer();
    };

    resetTimer();

    window.addEventListener('mousemove', handleUserActivity);
    window.addEventListener('keydown', handleUserActivity);

    return () => {
      window.removeEventListener('mousemove', handleUserActivity);
      window.removeEventListener('keydown', handleUserActivity);
    };
  }, [isAuthenticated1]);

  const paddingTop = '38px';
  return (
    <Router basename={baseHref}>
      <div className="app-container" style={{ paddingTop }}>
        <ToastContainer position={toast.POSITION.TOP_RIGHT} className="toastify-container" toastClassName="toastify-toast" />
        <ErrorBoundary>
          <Header
            isAuthenticated={isAuthenticated1}
            isAdmin={isAdmin}
            currentLocale={currentLocale}
            ribbonEnv={ribbonEnv}
            isInProduction={isInProduction}
            isOpenAPIEnabled={isOpenAPIEnabled}
            comunidades={comunidadList}
          />
        </ErrorBoundary>
        <div className="container-fluid view-container" id="app-view-container">
          <ErrorBoundary>
            <AppRoutes />
          </ErrorBoundary>

          <Footer />
        </div>
      </div>
    </Router>
  );
};

export default App;
