import { GroupType, GroupRole } from './../../shared/models/models';
/* import { Role } from 'src/app/shared/models/models'; */

export interface PrimeNgMenu {
  label?: string;
  items?: PrimeNgMenuItems[];
}

export interface PrimeNgMenuItems {
  label?: string;
  icon?: string;
  visible?: boolean;
  routerLink?: string;
  allowedRoles?: string[];
  items?: PrimeNgMenuItems[];
  grant?: AllowedGroup | AllowedGroup[] | '*' | 'ALL' | 'all';
  subMenu?: PrimeNgMenuItems[];
  show?: boolean;
}

export interface MenuItem {
  label?: string;
  icon?: string;
  command?: (event?: any) => void;
  url?: string;
  routerLink?: any;
  queryParams?: {
      [k: string]: any;
  };
  items?: MenuItem[];
  expanded?: boolean;
  disabled?: boolean;
  visible?: boolean;
  target?: string;
  routerLinkActiveOptions?: any;
  separator?: boolean;
  badge?: string;
  badgeStyleClass?: string;
  style?: any;
  styleClass?: string;
  title?: string;
  id?: string;
  automationId?: any;
  tabindex?: string;
  /* roles?: Role[]; */
  permission?: {groupType: GroupType, roles?: GroupRole[]}[];
}

export interface AllowedGroup
{
  group: GroupType;
  roles?: GroupRole[];
}
