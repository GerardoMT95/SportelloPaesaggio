export interface PrimeNgMenu {
  label?: string;
  items?: PrimeNgMenuItems[];
}

export interface PrimeNgMenuItems {
  label?: string;
  icon?: string;
  routerLink?: string;
  allowedRoles?: string[];
}
