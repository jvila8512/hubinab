export interface IComponentes {
  id?: number;
  componente?: string;
  descripcion?: string;
  activo?: boolean | null;
}

export const defaultValue: Readonly<IComponentes> = {
  activo: false,
};
