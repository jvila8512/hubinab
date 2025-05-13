export interface IComunidad {
  id?: number;
  comunidad?: string;
  descripcion?: string;
  activo?: boolean | null;
  link?: string | null;
}

export const defaultValue: Readonly<IComunidad> = {
  activo: false,
};
