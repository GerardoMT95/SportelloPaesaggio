import { APP_NAME } from './autpae';
import { projectVal } from "./autpae";

export const environment = {
  production: true,
  multiLang:false,
  useWso2: true,
  //Coincide con l'URL da richiamare per dettagli utentu
  baseUrl:"/"+projectVal,
  //Coincide con la base-href in sviluppo è /
  //pathName:"autpae-fe/",
  pathName:projectVal+"-fe/",
  //Coincide con il nome della web app da richiamare lato BE
  projectName:"",
  //Dimensione massima dei file
  maxSizeUpload:50000000,
  developer:false,
  // Assertion ID di JOSSO
  assertionId: 'assertion_id',
  //URL di JOSSO e nome parametro da inviare in BE
  josso:{
    jossoUrl:"/josso/",
    jossoAssertionId: 'josso_assertion_id'
  },
  //Configurazione WS2
  wso2: {
    clientId : 'XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX',
    serverUrl: window.location.origin,
    issuer :  '/oauth2/token',
    redirectUri : window.location.origin+"/"+projectVal+"-fe/login",
    postLogoutRedirectUri: window.location.origin+"/logout.html",
    scope: 'openid profile',
    tokenEndpoint:  '/oauth2/token',
    userinfoEndpoint:  '/oauth2/userinfo',
    authorizationEndpoint:  '/oauth2/authorize',
    jwksEndpoint: '/oauth2/jwks',
    showDebugInformation: false,
    requireHttps: false,
    responseType: 'id_token token',
    logoutUrl: '/oidc/logout',
    silentRefresh:  window.location.origin + '/silent-refresh.html',
    jwks : {  
      "keys":[  
          {  
          "kty":"RSA",
          "e":"AQAB",
          "use":"sig",
          "kid":"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX",
          "alg":"RS256",
          "n":"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
          }
      ]
    }
  }
  ,webgis:{
    //"urlOrtofoto": window.location.origin
    //,"urlProxy"   : window.location.origin + "/EsriJsViewer/ProxyUtil/proxy.ashx"
    "urlOrtofoto": window.location.protocol + "//webapps.sit.puglia.it"
    //,"urlProxy"   : window.location.origin + "/EsriJsViewer/ProxyUtil/proxy.ashx"
    ,"urlProxy"   : window.location.protocol + "//webapps.sit.puglia.it/EsriJsViewer/ProxyUtil/proxy.ashx"
    // "urlOrtofoto": "http://webapps.sit.puglia.it"
   // ,"urlProxy"   : "http://webapps.sit.puglia.it" + "/EsriJsViewer/ProxyUtil/proxy.ashx"
    //,"urlLocale"  : "https://webadaptor.tz.it:6443"
    ,"urlLocale"  : window.location.origin + "/"+projectVal
    ,"urlServices": window.location.protocol + "//webapps.sit.puglia.it/arcgis/rest/services/Background/Catasto/MapServer"
    ,"hasEditing" : true
    ,"hasEditLayer" :true //disattiva l'editing layer (false fino a quando ci mettono a disposizione ESRI....)
    },
  roles: {
      admin: 'admin',
      ap: 'ap',
      funz: 'funz',
      viewer: 'viewer',
  },
  project: projectVal,
  basepath_istruttoria_be:window.location.origin+'/paesaggio-presentazione-istanza'
  ,basepath_istruttoria_fe:window.location.origin+'/paesaggio-istruttoria-fe'
  ,linkFunzioniTrasverali:window.location.origin + "/funzioni-trasversali/public/ente"
  ,appName:APP_NAME
};
