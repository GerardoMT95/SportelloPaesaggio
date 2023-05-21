package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import it.eng.tz.puglia.cds.bean.ConferenzaApiDocumentazioneDto;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Bean per documentazione
 * @author Antonio La Gatta
 * @date 24 nov 2021
 */
public class ConferenzaApiDocumentazioneExtendedDto extends ConferenzaApiDocumentazioneDto{
	/**
	 * @author Antonio La Gatta
	 * @date 24 nov 2021
	 */
	private static final long serialVersionUID = -8346927940618803485L;
	
	
	public ConferenzaApiDocumentazioneExtendedDto(final String nomeFile
			                                     ,final String cmisId
			                                     ,final String numeroProtocollo
			                                     ,final String dataProtocollo
	) throws ParseException {
		super();
        super.setNomeFile        (nomeFile);
        super.setCmisId          (cmisId);
        super.setNumeroProtocollo(numeroProtocollo);
//        if(StringUtil.isNotBlank(dataProtocollo)) {
//        	if(dataProtocollo.contains("/"))
//        	super.setDataProtocollo(new SimpleDateFormat(AetConstants.DATE_FORMAT_PROTOCOLLO).parse(dataProtocollo));
//        }else {
//        	super.setDataProtocollo(new SimpleDateFormat(AetConstants.DATE_FORMAT_PROTOCOLLO_2).parse(dataProtocollo));
//        }
	}
	
}
