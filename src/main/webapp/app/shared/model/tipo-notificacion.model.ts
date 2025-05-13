import { INotificacion } from './notificacion.model';

export interface ITipoNotificacion {
  id?: number;
  tipoNotificacion?: string;
  icono?: string;
  notificacion?: INotificacion[] | null;
}

export const defaultValue: Readonly<ITipoNotificacion> = {};
