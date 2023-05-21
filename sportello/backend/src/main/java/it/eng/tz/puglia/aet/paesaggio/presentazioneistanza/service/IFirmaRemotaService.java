package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

public interface IFirmaRemotaService {


	/**
	 * chiama servizio firma-remota e utilizza coordinate dummy per firmare il file
	 * utilizzato per scopi di test
	 * @author acolaianni
	 *
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public File firmaDocumentoMock(MultipartFile file) throws Exception;
	
}
