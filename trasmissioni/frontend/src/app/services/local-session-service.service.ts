import { Injectable, Inject } from '@angular/core';
import { SESSION_STORAGE, StorageService } from "angular-webstorage-service";
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class LocalSessionServiceService {

public static LOGGED_USER_KEY                 = environment.appName+"_logged_user_key";
public static USER_GROUPS_KEY                 = environment.appName+"_user_groups_key";
public static WORKING_GROUP_KEY               = environment.appName+"_gruppoScelto";
public static VALID_GROUP_KEY                 = environment.appName+"_group.validity";
public static PUBLIC_SEARCH_FILTER_DATA_KEY   = environment.appName+"_public.search.filter.fascicolo";
public static PRIVATE_SEARCH_FILTER_DATA_KEY  = environment.appName+"_private.search.paging.fascicolo";

constructor(@Inject(SESSION_STORAGE) private storage: StorageService) { }
  public storeValue(chiave:string, valore:any){
    this.storage.set(chiave, valore);
  }
  public getValue(chiave:string){
    if( this.storage.get(chiave) == null )
    {
      throw new Error("Chiave "+chiave+" non presente nello storage");
    }
    return this.storage.get(chiave);
  }
  public removeValue(chiave:string){
    this.storage.remove(chiave);
  }
  public containsValue(chiave:string){
    return this.storage.get(chiave) != null;
  }
  public getValueOrNull(chiave:string){
    return this.storage.get(chiave);
  }
}
