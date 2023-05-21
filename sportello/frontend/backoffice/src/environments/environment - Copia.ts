let  nomeBe="paesaggio-presentazione-istanza"; //è il path per il be
let  nomeFe="paesaggio-istruttoria-fe"; //è il path del fe
export const environment = {
  production: true,
  multiLang:false,
  useWso2: true,
  //Coincide con l'URL da richiamare per dettagli utentu
  //baseUrl:"https://puglialab.eng.it/",
  baseUrl:"http://localhost:8080/",
  //Coincide con la base-href in sviluppo è /
  pathName:"/",
  //Coincide con il nome della web app da richiamare lato BE
  projectName:nomeBe,
  //Dimensione massima dei file
  maxSizeUpload:50000000,
  developer:true,
  // Assertion ID di JOSSO
  assertionId: 'assertion_id',
  //URL di JOSSO e nome parametro da inviare in BE
  josso:{
    jossoUrl:"/josso/",
    jossoAssertionId: 'josso_assertion_id'
  },
  //Configurazione WS2
  wso2: {
    checkSession: false,
    clientId : 'cjUGmHmyu49f6zzTklsBbtyayJwa',
    serverUrl: 'https://puglialab.eng.it',
    issuer :  ':443/oauth2/token',
    redirectUri : window.location.origin+"/login",
    postLogoutRedirectUri: window.location.origin + "/",
    scope: 'openid profile',
    tokenEndpoint:  '/oauth2/token',
    userinfoEndpoint:  '/oauth2/userinfo',
    authorizationEndpoint:  '/oauth2/authorize',
    jwksEndpoint: '/oauth2/jwks',
    showDebugInformation: true,
    requireHttps: false,
    responseType: 'id_token token',
    logoutUrl: '/oidc/logout',
    silentRefresh: 'https://puglialab.eng.it/silent-refresh.html',
    jwks : {
      "keys":[
          {
          "kty":"RSA",
          "e":"AQAB",
          "use":"sig",
          "kid":"NTAxZmMxNDMyZDg3MTU1ZGM0MzEzODJhZWI4NDNlZDU1OGFkNjFiMQ",
          "alg":"RS256",
          "n":"luZFdW1ynitztkWLC6xKegbRWxky-5P0p4ShYEOkHs30QI2VCuR6Qo4Bz5rTgLBrky03W1GAVrZxuvKRGj9V9-PmjdGtau4CTXu9pLLcqnruaczoSdvBYA3lS9a7zgFU0-s6kMl2EhB-rk7gXluEep7lIOenzfl2f6IoTKa2fVgVd3YKiSGsyL4tztS70vmmX121qm0sTJdKWP4HxXyqK9neolXI9fYyHOYILVNZ69z_73OOVhkh_mvTmWZLM7GM6sApmyLX6OXUp8z0pkY-vT_9-zRxxQs7GurC4_C1nK3rI_0ySUgGEafO1atNjYmlFN-M3tZX6nEcA6g94IavyQ"
          }
      ]
    }
  },
  webgis: {
    "urlOrtofoto": "http://webapps.sit.puglia.it"
    , "urlProxy": "http://webapps.sit.puglia.it/EsriJsViewer/ProxyUtil/proxy.ashx"
    , "urlLocale": "http://localhost:8080/"+nomeBe
    ,"urlServices": window.location.protocol + "//webapps.sit.puglia.it/arcgis/rest/services/Background/Catasto/MapServer"
    , "hasEditing": true
    , "hasEditLayer": true //disattiva l'editing layer (false fino a quando ci mettono a disposizione ESRI....)
  }
  ,linkFunzioniTrasverali:"https://puglialab.eng.it/funzioni-trasversali/public/ente"
  ,linkCds:"https://puglialab.eng.it/meetpad/conference/${id}"
};
