import { CONST } from 'src/app/shared/constants';
import { Injectable, Inject } from '@angular/core';
import { SESSION_STORAGE, StorageService } from "angular-webstorage-service";

@Injectable({
  providedIn: 'root'
})
export class LocalSessionServiceService
{
  public static LOGGED_USER_KEY   = CONST.CODICE_APPLICAZIONE + "_logged_user_key";
  public static USER_GROUPS_KEY   = CONST.CODICE_APPLICAZIONE + "_user_groups_key";
  public static WORKING_GROUP_KEY = CONST.CODICE_APPLICAZIONE + "_gruppoScelto";
  public static RUP_KEY           = CONST.CODICE_APPLICAZIONE + "_rup_key";

  constructor(@Inject(SESSION_STORAGE) private storage: StorageService) { }
  public storeValue(chiave: string, valore: any)
  {
    this.storage.set(chiave, valore);
  }
  public getValue(chiave: string)
  {
    if (this.storage.get(chiave) == null)
    {
      throw new Error("Chiave " + chiave + " non presente nello storage");
    }
    return this.storage.get(chiave);
  }
  public removeValue(chiave: string)
  {
    this.storage.remove(chiave);
  }
  public containsValue(chiave: string)
  {
    return this.storage.get(chiave) != null;
  }
}
