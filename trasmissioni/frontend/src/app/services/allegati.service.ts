import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
//import { BaseResponse } from 'src/app/shared/models';

//import * as fileSaver from 'file-saver';
import { BaseResponse } from '../components/model/base-response';
import { UploadType } from '../components/model/enum';
import { nextContext } from '@angular/core/src/render3';

@Injectable({
  providedIn: 'root'
})
export class AllegatiService 
{
  private baseUrl: string = environment.baseUrl + '/allegati';
  
  constructor(private http: HttpClient) { }

  uploadAttachment(pianoId: string, tipoDiFile: string, file: FormData,sezione:string): Observable<Object> {
    //li inserisco nella localstorage
    let objAttach={};
    let idAttach=((new Date()).getTime())+'';
    let key='ATTACH#'+pianoId+'#'+sezione+'#'+idAttach;
    objAttach['id']=idAttach;
    objAttach['descrizione']=tipoDiFile;
    let fileObj=<File>file.get('file');
    console.log("file {}:",fileObj);
    console.log("isostring date:" +(new Date(fileObj.lastModified)).toISOString());
    objAttach['data']=(new Date(fileObj.lastModified)).toISOString();
    objAttach['nome']=fileObj.name;
    objAttach['size']=fileObj.size;
    objAttach['type']=fileObj.type;
    localStorage.setItem(key,JSON.stringify(objAttach));
    return new Observable((observer)=>{
      observer.next(idAttach);
      observer.complete();
    })
    //return this.http.post<BaseResponse<Object>>(`${this.baseUrl}/${pianoId}/?tipoDiFile=${tipoDiFile}`, file);
  }

  downloadAttachment(allegatiId: number, uploadType: UploadType) {
    return this.http.get(`${this.baseUrl}/${allegatiId}/${uploadType}`,{
      observe: 'response'
    });
  }

  deleteAttachment(allegatiId: number) {
    let key:string;
    for (let index = 0; index < localStorage.length; index++) {
      key = localStorage.key(index);
      if(key.startsWith('ATTACH#')&& key.endsWith(allegatiId+'')){
        localStorage.removeItem(key);
      }    
    } 
    return new Observable(observer=>{observer.next(key);observer.complete();});
    //return this.http.patch(`${this.baseUrl}/${allegatiId}`, allegatiId);
  }

  saveFile(data: any, filename?: string) {
    //fileSaver.saveAs(this.dataURItoBlob(data), filename);
    alert("todo installare filesaver   npm --proxy http://user:password@eng.it:3128 install file-saver --save");
  }
  
  dataURItoBlob(dataURI) {
    
    const byteString = atob(dataURI);

    const ab = new ArrayBuffer(byteString.length);
    const ia = new Uint8Array(ab);

    for (let i = 0; i < byteString.length; i++) {
        ia[i] = byteString.charCodeAt(i);
    }

    const blob = new Blob([ab], { type: 'text/csv; charset=utf-8' });

    return blob;
}

downloadAnteprima(codiceFascicolo: string) {
  //return this.http.get(`${this.baseUrl}/${allegatiId}/${uploadType}`,{
    //observe: 'response'
  //});
  return new Observable((observer)=>{
    observer.next('');
    observer.complete();
  });
}

downloadAttestatoRicezione(codiceFascicolo: string) {
  return new Observable((observer)=>{
    observer.next('');
    observer.complete();
  });
}

/**
 * 
 * @param input calcola l'hash di applicazione sul file
 * @returns 
 */
public calcolaHash(input: FormData): Observable<BaseResponse<string>> {
  return this.http.post<BaseResponse<string>>(`${this.baseUrl}/calcola-hash`, input);
}

}
