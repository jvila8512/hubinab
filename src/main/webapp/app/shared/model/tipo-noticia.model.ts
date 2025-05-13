import { INoticias } from 'app/shared/model/noticias.model';

export interface ITipoNoticia {
  id?: number;
  tipoNoticia?: string;
  noticias?: INoticias[] | null;
}

export const defaultValue: Readonly<ITipoNoticia> = {};
