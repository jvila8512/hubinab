import dayjs from 'dayjs';

export interface ITramite {
  id?: number;
  inscripcion?: string | null;
  pruebaExperimental?: string | null;
  exmanenEvaluacion?: string | null;
  dictamen?: string | null;
  concesion?: boolean | null;
  denegado?: boolean | null;
  reclamacion?: boolean | null;
  anulacion?: boolean | null;
  fechaNotificacion?: string | null;
  fecaCertificado?: string | null;
  observacion?: string | null;
}

export const defaultValue: Readonly<ITramite> = {
  concesion: false,
  denegado: false,
  reclamacion: false,
  anulacion: false,
};
