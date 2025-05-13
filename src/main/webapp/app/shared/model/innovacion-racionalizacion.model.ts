import dayjs from 'dayjs';
import { ITipoIdea } from 'app/shared/model/tipo-idea.model';
import { ISector } from './sector.model';
import { ILineaInvestigacion } from './linea-investigacion.model';
import { IOds } from './ods.model';

export interface IInnovacionRacionalizacion {
  id?: number;
  tematica?: string;
  fecha?: string;
  vp?: number;
  autores?: string;
  numeroIdentidad?: number;
  observacion?: string | null;
  aprobada?: boolean | null;
  publico?: boolean;
  tipoIdea?: ITipoIdea | null;
  sectors?: ISector[] | null;
  lineaInvestigacions?: ILineaInvestigacion[] | null;
  ods?: IOds[] | null;
}

export const defaultValue: Readonly<IInnovacionRacionalizacion> = {
  aprobada: false,
  publico: false,
};
