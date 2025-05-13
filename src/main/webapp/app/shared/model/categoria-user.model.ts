import { IUsuarioEcosistema } from 'app/shared/model/usuario-ecosistema.model';

export interface ICategoriaUser {
  id?: number;
  categoriaUser?: string;
  usurioecosistemas?: IUsuarioEcosistema[] | null;
}

export const defaultValue: Readonly<ICategoriaUser> = {};
