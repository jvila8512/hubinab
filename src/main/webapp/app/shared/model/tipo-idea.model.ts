import { IIdea } from 'app/shared/model/idea.model';
import { IInnovacionRacionalizacion } from 'app/shared/model/innovacion-racionalizacion.model';

export interface ITipoIdea {
  id?: number;
  tipoIdea?: string;
  ideas?: IIdea[] | null;
  innovacionRacionalizacions?: IInnovacionRacionalizacion[] | null;
}

export const defaultValue: Readonly<ITipoIdea> = {};
