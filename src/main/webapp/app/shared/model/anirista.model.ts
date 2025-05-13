import dayjs from 'dayjs';
import { IEcosistema } from 'app/shared/model/ecosistema.model';

export interface IAnirista {
  id?: number;
  nombre?: string;
  fechaEntrada?: string;
  descripcion?: string;
  ecosistema?: IEcosistema | null;
}

export const defaultValue: Readonly<IAnirista> = {};
