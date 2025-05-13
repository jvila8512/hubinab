import dayjs from 'dayjs';
import { IServicios } from 'app/shared/model/servicios.model';

export interface IContactoServicio {
  id?: number;
  nombre?: string;
  telefono?: string;
  correo?: string;
  mensaje?: string;
  fechaContacto?: string | null;
  servicios?: IServicios | null;
}

export const defaultValue: Readonly<IContactoServicio> = {};
