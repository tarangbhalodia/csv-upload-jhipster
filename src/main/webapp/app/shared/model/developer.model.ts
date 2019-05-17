export interface IDeveloper {
  id?: number;
  firstName?: string;
  lastName?: string;
  topSkill?: string;
}

export const defaultValue: Readonly<IDeveloper> = {};
