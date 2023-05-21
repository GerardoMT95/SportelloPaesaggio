/**
 * 
 */
package it.eng.tz.aet.paesaggio.civilia.migrazione.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Repository;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.SessionFactory;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.client.util.FileUtils;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import it.eng.tz.aet.paesaggio.civilia.migrazione.service.IProxyClientAlfresco;
import it.eng.tz.puglia.util.crypt.CryptUtil;

/**
 * @author Adriano Colaianni
 * @date 21 lug 2021
 */
@Service
@ConditionalOnProperty("datasource.civ.enableMigration")
public class ProxyClientAlfresco implements IProxyClientAlfresco {
	
	@Value("${migration.alfresco.pptr.username}")
	private String username;
	@Value("${migration.alfresco.pptr.password}")
	private String password;
	@Value("${migration.alfresco.pptr.url}")
	private String url;
	
	private Session sessione=null;
	

	private Session getReadConnection() {
		final Map<String, String> connectionParameters = new HashMap<String, String>();
        connectionParameters.put(SessionParameter.USER             , CryptUtil.decrypt(username));
        connectionParameters.put(SessionParameter.PASSWORD         , CryptUtil.decrypt(password));
        connectionParameters.put(SessionParameter.ATOMPUB_URL      , url);
        connectionParameters.put(SessionParameter.BINDING_TYPE     , BindingType.ATOMPUB.value());
        connectionParameters.put(SessionParameter.COMPRESSION      , "true");
        connectionParameters.put(SessionParameter.CACHE_TTL_OBJECTS, "0"); // Caching is turned offR
        connectionParameters.put(SessionParameter.CONNECT_TIMEOUT  , "10000"); // Caching is turned offR
        connectionParameters.put(SessionParameter.READ_TIMEOUT     , "120000"); // Caching is turned offR


        final SessionFactory sessionFactory = SessionFactoryImpl.newInstance();
        final List<Repository> repositories = sessionFactory.getRepositories(connectionParameters);
		return repositories.get(0).createSession();
	}
	
	
	@PostConstruct
	private void initConnection() {
		//stabilisco la connessione con alfresco di produzione
		this.sessione=getReadConnection();
	}
	
	@Override
	public File getDocumentIntoLocalFile(String idCmis, String localPath) throws IOException {
		 Document document = (Document)this.sessione.getObject(idCmis);
		 if(localPath==null) {
			 File file = File.createTempFile("Alfresco", "");
			 FileUtils.download(document, file.getPath());
			 return file;
		 }else {
			 FileUtils.download(document, localPath);
			 return new File(localPath);
		 }
		 
		 
	}

}
