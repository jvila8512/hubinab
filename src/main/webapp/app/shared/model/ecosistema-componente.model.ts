import { IComponentes } from 'app/shared/model/componentes.model';
import { IEcosistema } from 'app/shared/model/ecosistema.model';

export interface IEcosistemaComponente {
  id?: number;
  componentehijo?: string | null;
  link?: string | null;
  documentoUrlContentType?: string | null;
  descripcion?: string | null;
  documentoUrl?: string | null;
  componente?: IComponentes | null;
  ecosistema?: IEcosistema | null;
}

export const defaultValue: Readonly<IEcosistemaComponente> = {};
