export interface IChangeMacker {
  id?: number;
  fotoContentType?: string;
  foto?: string;
  nombre?: string;
  tema?: string;
  descripcion?: string;
}

export const defaultValue: Readonly<IChangeMacker> = {};
