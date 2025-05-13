import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';
import { IEcosistema } from 'app/shared/model/ecosistema.model';

export interface IEcosistemaPeticiones {
  id?: number;
  motivo?: string;
  fechaPeticion?: string;
  aprobada?: boolean | null;
  user?: IUser | null;
  ecosistema?: IEcosistema | null;
}

export const defaultValue: Readonly<IEcosistemaPeticiones> = {
  aprobada: false,
};
