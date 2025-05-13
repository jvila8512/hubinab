import { IProyectos } from 'app/shared/model/proyectos.model';

export interface IParticipantes {
  id?: number;
  nombre?: string;
  telefono?: string | null;
  correo?: string;
  proyectos?: IProyectos | null;
}

export const defaultValue: Readonly<IParticipantes> = {};
