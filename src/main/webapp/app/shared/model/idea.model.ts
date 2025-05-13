import dayjs from 'dayjs';
import { IComenetariosIdea } from 'app/shared/model/comenetarios-idea.model';
import { ILikeIdea } from 'app/shared/model/like-idea.model';
import { IUser } from 'app/shared/model/user.model';
import { IReto } from 'app/shared/model/reto.model';
import { ITipoIdea } from 'app/shared/model/tipo-idea.model';
import { IEntidad } from 'app/shared/model/entidad.model';

export interface IIdea {
  id?: number;
  numeroRegistro?: number;
  titulo?: string;
  descripcion?: string;
  autor?: string;
  fechaInscripcion?: string;
  visto?: number | null;
  fotoContentType?: string | null;
  foto?: string | null;
  aceptada?: boolean | null;
  publica?: boolean | null;
  comentarios?: IComenetariosIdea[] | null;
  likes?: ILikeIdea[] | null;
  user?: IUser | null;
  reto?: IReto | null;
  tipoIdea?: ITipoIdea | null;
  entidad?: IEntidad | null;
}

export const defaultValue: Readonly<IIdea> = {
  aceptada: false,
  publica: false,
};
