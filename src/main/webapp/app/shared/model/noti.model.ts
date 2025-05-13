import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';

export interface INoti {
  id?: number;
  descripcion?: string;
  visto?: boolean;
  fecha?: string;
  user?: IUser | null;
  usercreada?: IUser | null;
}

export const defaultValue: Readonly<INoti> = {
  visto: false,
};
