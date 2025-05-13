import { IProyectos } from 'app/shared/model/proyectos.model';

export interface ITipoProyecto {
  id?: number;
  tipoProyecto?: string;
  proyectos?: IProyectos[] | null;
}

export const defaultValue: Readonly<ITipoProyecto> = {};
