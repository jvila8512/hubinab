import { IEcosistema } from 'app/shared/model/ecosistema.model';

export interface IEcosistemaRol {
  id?: number;
  ecosistemaRol?: string;
  descripcion?: string;
  ecosistemas?: IEcosistema[] | null;
}

export const defaultValue: Readonly<IEcosistemaRol> = {};
