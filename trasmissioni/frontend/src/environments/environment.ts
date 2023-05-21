import { APP_NAME } from './autpae';
import { projectVal } from "./autpae";
export const environment = {
  production: true,
  multiLang: false,
  useWso2: true,
  //Coincide con l'URL da richiamare per dettagli utentu
  baseUrl:"http://localhost:8081/"+projectVal, 
  //baseUrl:"https://puglialab.eng.it/"+projectVal, 
  // su puglialab per evitare problemi di cors partire con /login e autenticarsi
  //Coincide con la base-href in sviluppo è /
  pathName: "/",
  //Coincide con il nome della web app da richiamare lato BE
  projectName: "",
  //Dimensione massima dei file
  maxSizeUpload: 50000000,
  developer: true,
  // Assertion ID di JOSSO
  assertionId: 'assertion_id',
  //URL di JOSSO e nome parametro da inviare in BE
  josso: {
    jossoUrl: "/josso/",
    jossoAssertionId: 'josso_assertion_id'
  },
  //Configurazione WS2
  wso2: {
    clientId: 'XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX',
    serverUrl: 'https://puglialab.eng.it',
    issuer: ':443/oauth2/token',
    redirectUri: window.location.origin + "/login",
    postLogoutRedirectUri: window.location.origin + "/",
    scope: 'openid profile',
    tokenEndpoint: '/oauth2/token',
    userinfoEndpoint: '/oauth2/userinfo',
    authorizationEndpoint: '/oauth2/authorize',
    jwksEndpoint: '/oauth2/jwks',
    showDebugInformation: true,
    requireHttps: false,
    responseType: 'id_token token',
    logoutUrl: '/oidc/logout',
    silentRefresh: 'https://puglialab.eng.it/silent-refresh.html',
    jwks: {
      "keys": [
        {
          "kty": "RSA",
          "e": "AQAB",
          "use": "sig",
          "kid": "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX",
          "alg": "RS256",
          "n": "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
        }
      ]
    }
  },
  webgis:{"urlOrtofoto": "http://webapps.sit.puglia.it"
         ,"urlProxy"   : "http://webapps.sit.puglia.it/EsriJsViewer/ProxyUtil/proxy.ashx"
         ,"urlLocale"  : "http://localhost:8081/"+projectVal
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
  basepath_istruttoria_be:'http://localhost:8080/paesaggio-presentazione-istanza'
  ,basepath_istruttoria_fe:'https://puglialab.eng.it/paesaggio-istruttoria-fe'
  ,linkFunzioniTrasverali:"https://puglialab.eng.it/funzioni-trasversali/public/ente"
  ,appName:APP_NAME
};
