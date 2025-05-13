import { IReto } from 'app/shared/model/reto.model';
import { IProyectos } from 'app/shared/model/proyectos.model';
import { IEcosistemaPeticiones } from 'app/shared/model/ecosistema-peticiones.model';
import { INoticias } from 'app/shared/model/noticias.model';
import { IRedesSociales } from 'app/shared/model/redes-sociales.model';
import { IAnirista } from 'app/shared/model/anirista.model';
import { IEcosistemaComponente } from 'app/shared/model/ecosistema-componente.model';
import { IUser } from 'app/shared/model/user.model';
import { IEcosistemaRol } from 'app/shared/model/ecosistema-rol.model';
import { IUsuarioEcosistema } from 'app/shared/model/usuario-ecosistema.model';

export interface IEcosistema {
  id?: number;
  nombre?: string;
  tematica?: string;
  activo?: boolean | null;
  logoUrlContentType?: string | null;
  logoUrl?: string | null;
  ranking?: number | null;
  usuariosCant?: number | null;
  retosCant?: number | null;
  ideasCant?: number | null;
  retos?: IReto[] | null;
  proyectos?: IProyectos[] | null;
  ecosistemaPeticiones?: IEcosistemaPeticiones[] | null;
  noticias?: INoticias[] | null;
  redesUrls?: IRedesSociales[] | null;
  aniristas?: IAnirista[] | null;
  ecosistemaComponentes?: IEcosistemaComponente[] | null;
  users?: IUser[] | null;
  ecosistemaRol?: IEcosistemaRol | null;
  usurioecosistemas?: IUsuarioEcosistema[] | null;
}

export const defaultValue: Readonly<IEcosistema> = {
  activo: false,
};
