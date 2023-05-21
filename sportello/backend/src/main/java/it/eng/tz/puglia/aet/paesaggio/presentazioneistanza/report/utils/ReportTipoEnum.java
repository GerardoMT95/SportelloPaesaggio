package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.utils;

import java.util.Arrays;
import java.util.List;

public enum ReportTipoEnum {

//     FONDI_POSR                          (IReportTypeConstant.REPORT_FONDI_POSR                   , "Fondi POSR"                         , false, Arrays.asList("pdf", "xls", "rtf"))
//    ,FONDI_PSR                           (IReportTypeConstant.REPORT_FONDI_PSR                    , "Fondi PSR"                          , false, Arrays.asList("pdf", "xls", "rtf"))
       LOCALIZZAZIONE                      (IReportTypeConstant.REPORT_LOCALIZZAZIONE               , "Localizzazione"                     , false, Arrays.asList("pdf", "xls", "rtf"))
//    ,ISTRUTTORI                          (IReportTypeConstant.REPORT_ISTRUTTORI                   , "Istruttori"                         , true , Arrays.asList("pdf", "xls", "rtf"))
	  ,ONERI_ISTRUTTORI                    (IReportTypeConstant.REPORT_ONERI_ISTRUTTORI             , "Oneri Istruttori"                   , false, Arrays.asList("pdf", "xls", "rtf"))
//    ,TEMPI_MEDI                          (IReportTypeConstant.REPORT_TEMPI_MEDI                   , "Tempi Medi"                         , false, Arrays.asList("pdf", "xls", "rtf"))
//    ,ATTIVITA_ANNUALI                    (IReportTypeConstant.REPORT_ATTIVITA_ANNUALI             , "Attività Annauli"                   , false, Arrays.asList("pdf", "xls", "rtf"))
//    ,REPORT_ENTI_COINVOLTI               (IReportTypeConstant.REPORT_ENTI_COINVOLTI               , "Soggetti Competenti Coinvolti"      , false, Arrays.asList("pdf", "xls", "rtf"))
//    ,REPORT_MEDIA_ENTI_COINVOLTI         (IReportTypeConstant.REPORT_MEDIA_ENTI_COINVOLTI         , "Media Soggetti Competenti Coinvolti", false, Arrays.asList("pdf", "xls", "rtf"))
//    ,REPORT_DOCUMENTAZIONE_PRODOTTA      (IReportTypeConstant.REPORT_DOCUMENTAZIONE_PRODOTTA      , "Documentazione Prodotta"            , false, Arrays.asList("pdf", "xls", "rtf"))
//    ,REPORT_DOCUMENTAZIONE_MEDIA_PRODOTTA(IReportTypeConstant.REPORT_DOCUMENTAZIONE_MEDIA_PRODOTTA, "Documentazione Media Prodotta"      , false, Arrays.asList("pdf", "xls", "rtf"))
//    ,REPORT_ANDAMENTO_MENSILE            (IReportTypeConstant.REPORT_ANDAMENTO_MENSILE            , "Andamento Mensile"                  , false, Arrays.asList("pdf", "xls", "rtf"))
//    ,REPORT_ELENCO_ISTANZE_XLS           (IReportTypeConstant.REPORT_ELENCO_ISTANZE               , "Elenco Istanze"                     , false, Arrays.asList("xls"))
	;
	
	private final String codice;
	private final String titolo;
	private final boolean onlyDirigente;
	private final List<String> estensioni;

	/**
	 * @return the codice
	 */
	public String getCodice() {
		return this.codice;
	}

	private ReportTipoEnum(final String codice, final String titolo, final boolean onlyDirigente, final List<String> estensioni) {
		this.codice = codice;
		this.titolo = titolo;
		this.onlyDirigente = onlyDirigente;
		this.estensioni = estensioni;
	}	

	/**
	 * @return the onlyDirigente
	 */
	public boolean isOnlyDirigente() {
		return this.onlyDirigente;
	}

	/**
	 * @return the titolo
	 */
	public String getTitolo() {
		return this.titolo;
	}
	
	public List<String> getEstensioni() {
		return this.estensioni;
	}
	
	public static String getCodice(final String name) {
		for(final ReportTipoEnum item:ReportTipoEnum.values()) {
			if(name.equals(item.name())) {
				return item.codice;
			}
		}
		return null;
	}
	
	public static String getTitolo(final String codice) {
		for(final ReportTipoEnum item:ReportTipoEnum.values()) {
			if(codice.equals(item.codice)) {
				return item.titolo;
			}
		}
		return null;
	}

	public static String getKeyLabel(final String name) {
		for(final ReportTipoEnum item:ReportTipoEnum.values()) {
			if(item.getCodice().equals(name)) {
				return item.name().replace("_XLS", "").replace("", "").replace("_RTF", "");
			}
		}
		return "report.unknow";
	}
	
}
