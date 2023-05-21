let  nomeBe="paesaggio-presentazione-istanza"; //è il path per il be
let  nomeFe="paesaggio-istruttoria-fe"; //è il path del fe
export const environment = {
  production: true,
  multiLang:false,
  useWso2: true,
  //Coincide con l'URL da richiamare per dettagli utentu
  baseUrl:"/",
  //Coincide con la base-href in sviluppo è /
  pathName:"/",
  //Coincide con il nome della web app da richiamare lato BE
  projectName:nomeBe,
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
    checkSession: false,
    clientId : 'rD6djNpSSy4cBViNAcTkNdvkcNUa',
    serverUrl: window.location.origin,
    issuer :  ':443/oauth2/token',
    redirectUri : window.location.origin+"/"+nomeFe+"/login",
    postLogoutRedirectUri: window.location.origin + "/"+nomeFe+"/",
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
    , "urlLocale":  window.location.origin+"/"+nomeBe //corrisponde al back-end se voglio usare il geocontroller interno
    ,"urlServices": window.location.protocol + "//webapps.sit.puglia.it/arcgis/rest/services/Background/Catasto/MapServer"
    , "hasEditing": true
    , "hasEditLayer": true //disattiva l'editing layer (false fino a quando ci mettono a disposizione ESRI....)
  }
  ,linkFunzioniTrasverali:window.location.origin + "/funzioni-trasversali/public/ente"
  ,linkCds:window.location.origin +"/meetpad/conference/${id}"
};
