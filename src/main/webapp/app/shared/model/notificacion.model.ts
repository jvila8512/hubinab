import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';
import { ITipoNotificacion } from './tipo-notificacion.model';

export interface INotificacion {
  id?: number;
  descripcion?: string;
  visto?: boolean;
  fecha?: string;
  user?: IUser | null;
  usercreada?: IUser | null;
  tipoNotificacion?: ITipoNotificacion | null;
}

export const defaultValue: Readonly<INotificacion> = {
  visto: false,
};
