let  nomewebapp="paesaggio-presentazione-istanza";
export const environment = {
  production: true,
  multiLang:false,
  useWso2: true,
  baseUrl:"/",
  //Coincide con la base-href in sviluppo è /
  pathName:"/",
  //Coincide con il nome della web app da richiamare lato BE Okkio che è fuori standard... lo / finale è inserito in tutti gli endpoint e non va inserito qui
  projectName:nomewebapp,
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
    clientId : 'XXXXXXXXXXXXXXXXXXXX',
    serverUrl: 'https://puglialab.eng.it',
    issuer :  ':443/oauth2/token',
    redirectUri : window.location.origin+"/"+nomewebapp+"-fe/login",
    postLogoutRedirectUri: window.location.origin + "/"+nomewebapp+"-fe/",
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
          "kid":"XXXXXXXXXXXXXXXXXXXX",
          "alg":"RS256",
          "n":"XXXXXXXXXXXXXXXXXXXX"
          }
      ]
  }
  },  
  webgis:
  {"urlOrtofoto": "http://webapps.sit.puglia.it"
  ,"urlProxy"   : "http://webapps.sit.puglia.it" + "/EsriJsViewer/ProxyUtil/proxy.ashx"
  ,"urlLocale"  : window.location.origin+"/"+nomewebapp //corrisponde al back-end se voglio usare il geocontroller interno
  ,"urlServices": window.location.protocol + "//webapps.sit.puglia.it/arcgis/rest/services/Background/Catasto/MapServer"
  ,"hasEditing" : true
  ,"hasEditLayer" :true //disattiva l'editing layer (false fino a quando ci mettono a disposizione ESRI....)
  },
  roles:{
    RICHIEDENTE:'RICHIEDENTE'
  }
  ,linkFunzioniTrasverali:window.location.origin + "/funzioni-trasversali/public/ente"
};
