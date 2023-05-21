package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.istruttoria;

import java.util.UUID;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.protocollo.GeneratedFileBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.protocollo.ProtocolNumberType;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.protocollo.model.SegnaturaProtocollo;
import it.eng.tz.puglia.service.http.bean.ProtocolloResponseBean;

public interface IProtocolloCallService {

	/**
	 * protocollazione pratica trasmessa dal richiedente
	 * @author acolaianni
	 *
	 * @param idAllegato
	 * @param idPratica
	 * @param pt
	 * @return
	 * @throws Exception 
	 */
//	@Deprecated
//	SegnaturaProtocollo getNumeroProtocollo(UUID idAllegato,UUID idPratica,ProtocolNumberType pt);
	/**
	 * protocollazione istranza e scheda tecnica come da dettagli consultati sulla web app rupar
	 * vengono allegati sia l'istanza che la scheda tecnica
	 * @author acolaianni
	 *
	 * @param idIstanza
	 * @param idSchedaTecnica
	 * @param idPratica
	 * @param pt
	 * @return
	 * @throws Exception
	 */
	ProtocolloResponseBean protocollaIstanza(UUID idIstanza,UUID idSchedaTecnica,UUID idPratica,ProtocolNumberType pt) throws Exception;

	/**
	 * 
	 * @author acolaianni
	 *
	 * @param generatedFileBean
	 * @param idPratica
	 * @param pt
	 * @param usaregione
	 * @return
	 * @throws Exception
	 */
	ProtocolloResponseBean getNumeroProtocollo(GeneratedFileBean generatedFileBean, UUID idPratica, ProtocolNumberType pt, boolean usaregione,String[] mailDestinatari,String[] denominazioneDestinatari,String oggetto)throws Exception;
}
