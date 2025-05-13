import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';
import { IEcosistema } from 'app/shared/model/ecosistema.model';
import { ITipoNoticia } from 'app/shared/model/tipo-noticia.model';

export interface INoticias {
  id?: number;
  titulo?: string;
  descripcion?: string;
  urlFotoContentType?: string | null;
  urlFoto?: string | null;
  publica?: boolean | null;
  publicar?: boolean | null;
  fechaCreada?: string | null;
  user?: IUser | null;
  ecosistema?: IEcosistema | null;
  tipoNoticia?: ITipoNoticia | null;
}

export const defaultValue: Readonly<INoticias> = {
  publica: false,
  publicar: false,
};
