import dayjs from 'dayjs';
import { IIdea } from 'app/shared/model/idea.model';
import { IUser } from 'app/shared/model/user.model';
import { IEcosistema } from 'app/shared/model/ecosistema.model';

export interface IReto {
  id?: number;
  reto?: string;
  descripcion?: string;
  motivacion?: string;
  fechaInicio?: string;
  fechaFin?: string;
  activo?: boolean | null;
  validado?: boolean | null;
  urlFotoContentType?: string | null;
  urlFoto?: string | null;
  visto?: number | null;
  ideas?: IIdea[] | null;
  user?: IUser | null;
  ecosistema?: IEcosistema | null;
}

export const defaultValue: Readonly<IReto> = {
  activo: false,
  validado: false,
};
