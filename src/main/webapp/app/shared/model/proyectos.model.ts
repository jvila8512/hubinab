import dayjs from 'dayjs';
import { IParticipantes } from 'app/shared/model/participantes.model';
import { IUser } from 'app/shared/model/user.model';
import { ISector } from 'app/shared/model/sector.model';
import { ILineaInvestigacion } from 'app/shared/model/linea-investigacion.model';
import { IOds } from 'app/shared/model/ods.model';
import { IEcosistema } from 'app/shared/model/ecosistema.model';
import { ITipoProyecto } from 'app/shared/model/tipo-proyecto.model';

export interface IProyectos {
  id?: number;
  nombre?: string;
  descripcion?: string;
  autor?: string;
  necesidad?: string;
  fechaInicio?: string;
  fechaFin?: string;
  logoUrlContentType?: string | null;
  logoUrl?: string | null;
  partipantes?: IParticipantes[] | null;
  user?: IUser | null;
  sectors?: ISector[] | null;
  lineaInvestigacions?: ILineaInvestigacion[] | null;
  ods?: IOds[] | null;
  ecosistema?: IEcosistema | null;
  tipoProyecto?: ITipoProyecto | null;
}

export const defaultValue: Readonly<IProyectos> = {};
