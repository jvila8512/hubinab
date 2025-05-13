import { IIdea } from 'app/shared/model/idea.model';

export interface IEntidad {
  id?: number;
  entidad?: string;
  ideas?: IIdea[] | null;
}

export const defaultValue: Readonly<IEntidad> = {};
