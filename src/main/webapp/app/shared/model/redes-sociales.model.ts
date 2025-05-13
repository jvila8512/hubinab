import { IEcosistema } from 'app/shared/model/ecosistema.model';

export interface IRedesSociales {
  id?: number;
  redesUrl?: string;
  logoUrlContentType?: string;
  logoUrl?: string;
  ecosistema?: IEcosistema | null;
}

export const defaultValue: Readonly<IRedesSociales> = {};
