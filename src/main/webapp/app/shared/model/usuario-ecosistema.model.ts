import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';
import { ICategoriaUser } from 'app/shared/model/categoria-user.model';
import { IEcosistema } from 'app/shared/model/ecosistema.model';

export interface IUsuarioEcosistema {
  id?: number;
  fechaIngreso?: string;
  bloqueado?: boolean | null;
  user?: IUser | null;
  categoriaUser?: ICategoriaUser | null;
  ecosistemas?: IEcosistema[] | null;
}

export const defaultValue: Readonly<IUsuarioEcosistema> = {
  bloqueado: false,
};
