import { IProyectos } from 'app/shared/model/proyectos.model';

export interface ILineaInvestigacion {
  id?: number;
  linea?: string;
  proyectos?: IProyectos[] | null;
}

export const defaultValue: Readonly<ILineaInvestigacion> = {};
