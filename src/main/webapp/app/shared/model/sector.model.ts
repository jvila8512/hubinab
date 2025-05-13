import { IProyectos } from 'app/shared/model/proyectos.model';

export interface ISector {
  id?: number;
  sector?: string;
  proyectos?: IProyectos[] | null;
}

export const defaultValue: Readonly<ISector> = {};
