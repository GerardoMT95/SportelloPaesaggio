import { environment } from '../../../../environments/environment';
import { AuthConfig } from 'angular-oauth2-oidc';
 
export const authConfig: AuthConfig = {
 
  issuer: environment.wso2.serverUrl.concat(environment.wso2.issuer), 
  redirectUri: environment.wso2.redirectUri,
  postLogoutRedirectUri: environment.wso2.postLogoutRedirectUri,
  clientId: environment.wso2.clientId,
  scope: environment.wso2.scope,
  tokenEndpoint: environment.wso2.serverUrl.concat(environment.wso2.tokenEndpoint),
  userinfoEndpoint:  environment.wso2.serverUrl.concat(environment.wso2.userinfoEndpoint),
  showDebugInformation: environment.wso2.showDebugInformation,
  loginUrl:  environment.wso2.serverUrl.concat(environment.wso2.authorizationEndpoint),
  logoutUrl: environment.wso2.serverUrl.concat(environment.wso2.logoutUrl),
  silentRefreshRedirectUri:environment.wso2.silentRefresh,
  requireHttps: environment.wso2.requireHttps,
  disableAtHashCheck: false,
  responseType: environment.wso2.responseType
  ,skipIssuerCheck:false
};
