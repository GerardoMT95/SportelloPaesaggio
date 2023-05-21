package it.eng.tz.puglia.servizi_esterni.protocollo;

public interface IProtocolloService {
	
	//String INOUT="I";
	enum TipoDocumento { PRIMARIO, ALLEGATO};
    String PROTOCOLLO_ENDPOINT               = "PROTOCOLLO_ENDPOINT";
    String PROTOCOLLO_CODICE_AMMINISTRAZIONE = "PROTOCOLLO_CODICE_AMMINISTRAZIONE";
    String PROTOCOLLO_DENOMINAZIONE_MITTENTE = "PROTOCOLLO_DENOMINAZIONE_MITTENTE";
    String PROTOCOLLO_CODICE_AOO             = "PROTOCOLLO_CODICE_AOO";
    String PROTOCOLLO_CODICE_REGISTRO        = "PROTOCOLLO_CODICE_REGISTRO";
    String PROTOCOLLO_ID_UTENTE              = "PROTOCOLLO_ID_UTENTE";
    String PROTOCOLLO_PASSWORD               = "PROTOCOLLO_PASSWORD";
    String PROTOCOLLO_ALGORITMO_IMPRONTA     = "PROTOCOLLO_ALGORITMO_IMPRONTA";
    String CODIFICA                          = "base64";
    String DATA_REGISTRAZIONE                = "1900-01-01";
	String NUMERO_REGISTRAZIONE              = "0000000";   
	String TIPO_INDIRIZZO_TELEMATICO         = "NMTOKEN";   
	String VALORE_TELEMATICO                 = "sconosciuto";   
	String CONFERMA_RICEZIONE                = "no";
	String TIPO_RIFERIMENTO                  = "MIME";
	String TIPO_MIME                         = "application/pdf";
	String TIPO_DOCUMENTO                    = "pdf";
	String COD_OK_PROTOCOLLO                 = "0";
	String TIPO_INDIRIZZO_SMTP         		= "smtp";

    /**
     * Call protocollo
     * @author Antonio La Gatta
     * @date 3 apr 2020
     * @param allegato
     * @return String protocollo
     * @throws Exception
     */
//    SegnaturaProtocollo generateSegnatura(GeneratedFileBean pdfWithoutProtocolNumber,
//			ProtocolNumberType protocolNumberType, String denominazioneMittente) throws Exception;
//
//    
//    
//	SegnaturaProtocollo generateSegnatura(GeneratedFileBean pdfWithoutProtocolNumber,
//			ProtocolNumberType protocolNumberType, String denominazioneMittente, IProtocolloUscitaBean protoBean) throws Exception;
    
}