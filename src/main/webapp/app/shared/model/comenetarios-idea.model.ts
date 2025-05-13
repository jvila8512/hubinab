import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';
import { IIdea } from 'app/shared/model/idea.model';

export interface IComenetariosIdea {
  id?: number;
  comentario?: string;
  fechaInscripcion?: string;
  user?: IUser | null;
  idea?: IIdea | null;
}

export const defaultValue: Readonly<IComenetariosIdea> = {};
