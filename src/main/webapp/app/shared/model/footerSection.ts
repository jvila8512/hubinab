export interface IFooterSection {
  id?: number;
  title?: string;
  content?: string;
  section_order?: string;
  is_active?: boolean | null;
}

export const defaultValue: Readonly<IFooterSection> = {
  is_active: true,
};
