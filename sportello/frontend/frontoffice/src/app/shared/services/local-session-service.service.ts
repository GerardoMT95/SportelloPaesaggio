import { Injectable, Inject } from '@angular/core';
import { SESSION_STORAGE, StorageService } from "angular-webstorage-service";

@Injectable({
  providedIn: 'root'
})
export class LocalSessionServiceService {

public static LOGGED_USER_KEY = "logged_user_key"
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
}
