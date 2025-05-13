import { IProyectos } from 'app/shared/model/proyectos.model';

export interface IOds {
  id?: number;
  ods?: string;
  proyectos?: IProyectos[] | null;
}

export const defaultValue: Readonly<IOds> = {};
