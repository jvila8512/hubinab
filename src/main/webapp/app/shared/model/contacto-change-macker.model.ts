import dayjs from 'dayjs';
import { IChangeMacker } from 'app/shared/model/change-macker.model';

export interface IContactoChangeMacker {
  id?: number;
  nombre?: string;
  telefono?: string;
  correo?: string;
  mensaje?: string;
  fechaContacto?: string | null;
  changeMacker?: IChangeMacker | null;
}

export const defaultValue: Readonly<IContactoChangeMacker> = {};
