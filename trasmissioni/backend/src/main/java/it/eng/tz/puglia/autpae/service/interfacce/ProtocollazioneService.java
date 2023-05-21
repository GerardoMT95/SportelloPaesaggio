package it.eng.tz.puglia.autpae.service.interfacce;

import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.puglia.autpae.entity.AllegatoDTO;
import it.eng.tz.puglia.service.http.bean.ProtocolloRequestBean;
import it.eng.tz.puglia.servizi_esterni.protocollo.ProtocolNumberType;
import it.eng.tz.puglia.servizi_esterni.protocollo.model.SegnaturaProtocollo;


public interface ProtocollazioneService {

//		SegnaturaProtocollo ottieniNumeroProtocollo(MultipartFile file, AllegatoDTO informazioni, ProtocolNumberType tipo,
//			String denominazioneMittente) throws Exception;
		/**
		 * 
		 * @param file
		 * @param informazioni
		 * @param tipo
		 * @param denominazioneMittente
		 * @param beanProto nullable (come il metodo senza questo parametro) 
		 * 
		 * altrimenti usa i dati per la protocollazione in uscita (compresi i destinatari e loro denominazione)
		 * @return
		 * @throws Exception
		 */
		SegnaturaProtocollo ottieniNumeroProtocollo(MultipartFile file, AllegatoDTO informazioni, ProtocolNumberType tipo,
				String denominazioneMittente,ProtocolloRequestBean beanProto) throws Exception;
}