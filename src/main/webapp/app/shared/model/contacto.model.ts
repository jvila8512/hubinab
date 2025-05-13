import dayjs from 'dayjs';
import { ITipoContacto } from 'app/shared/model/tipo-contacto.model';

export interface IContacto {
  id?: number;
  nombre?: string;
  telefono?: string;
  correo?: string;
  mensaje?: string;
  fechaContacto?: string | null;
  tipoContacto?: ITipoContacto | null;
}

export const defaultValue: Readonly<IContacto> = {};
