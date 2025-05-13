export interface IServicios {
  id?: number;
  servicio?: string;
  descripcion?: string;
  publicado?: boolean | null;
  fotoContentType?: string | null;
  foto?: string | null;
}

export const defaultValue: Readonly<IServicios> = {
  publicado: false,
};
