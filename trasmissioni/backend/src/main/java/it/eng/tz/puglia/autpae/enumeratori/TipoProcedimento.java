package it.eng.tz.puglia.autpae.enumeratori;

import io.swagger.annotations.ApiModelProperty;

public enum TipoProcedimento {
	
	@ApiModelProperty("AUTORIZZAZIONE PAESAGGISTICA art. 146, D. Lgs. 42/2004 – art. 90, NTA PPTR (ORDINARIA)")
	AUT_PAES_ORD("AUTORIZZAZIONE PAESAGGISTICA art. 146, D. Lgs. 42/2004 – art. 90, NTA PPTR (ORDINARIA)", true, false, false),
	AUT_PAES_SEMPL_DPR_139_2010("AUTORIZZAZIONE PAESAGGISTICA SEMPLIFICATA D.P.R. 139/2010 – art. 90, NTA PPTR (PER LE OPERE IL CUI IMPATTO PAESAGGISTICO E' VALUTATO MEDIANTE UNA DOCUMENTAZIONE SEMPLIFICATA)", true, false, false),
	AUT_PAES_SEMPL_DPR_31_2017 ("AUTORIZZAZIONE PAESAGGISTICA SEMPLIFICATA D.P.R.  31/2017 – art. 90, NTA PPTR (PER LE OPERE IL CUI IMPATTO PAESAGGISTICO E' VALUTATO MEDIANTE UNA DOCUMENTAZIONE SEMPLIFICATA)", true, false, false),
	ACCERT_COMPAT_PAES_DLGS_42_2004("ACCERTAMENTO DI COMPATIBILITÀ PAESAGGISTICA Art. 167 e 181, D. Lgs. 42/2004 (PER OPERE IN DIFFORMITA’ O ASSENZA DI AUTORIZZAZIONE PAESAGGISTICA)", true, false, false),
	ACCERT_COMPAT_PAES_DPR_31_2017 ("ACCERTAMENTO DI COMPATIBILITÀ PAESAGGISTICA art. 91, NTA PPTR [IN VIGENZA D.P.R.  31/2017] (PER INTERVENTI CHE COMPORTINO MODIFICA DELLO STATO DEI LUOGHI NEGLI ULTERIORI CONTESTI COME INDIVIDUATI NELL’ART. 38 C. 3.1 NTA PPTR)", true, false, false),
	ACCERT_COMPAT_PAES_DPR_139_2010("ACCERTAMENTO DI COMPATIBILITÀ PAESAGGISTICA art. 91, NTA PPTR [IN VIGENZA D.P.R. 139/2010] (PER INTERVENTI CHE COMPORTINO MODIFICA DELLO STATO DEI LUOGHI NEGLI ULTERIORI CONTESTI COME INDIVIDUATI NELL’ART. 38 C. 3.1 NTA PPTR)", true, false, false),
	PARERE_COMP_96D("PARERE DI COMPATIBILITÀ PAESAGGISTICA art. 96.1 lett.d NTA PPTR", false, true, false), // solo per pareri
	PUTT_X("Pratica paesaggistica presentata nell’ambito del art. 5.01 NTA del PUTT", false, false, true), // solo per putt
	PUTT_DLGS_42_2004("Pratica paesaggistica presentata nell’ambito del art. 5.01 NTA del PUTT e dell’art. 146 D.Lgs 42/04", false, false, true); // solo per putt
	
	
	private String value;
	private boolean forAutpae;
	private boolean forPareri;
	private boolean forPutt;
	
	
	private TipoProcedimento(String text, boolean forAutpae, boolean forPareri, boolean forPutt) {
		this.value = text;
		this.forAutpae = forAutpae;
		this.forPareri = forPareri;
		this.forPutt = forPutt;
	}

	public String getTextValue() {
		return value;
	}

	public boolean isForAutpae() {
		return forAutpae;
	}
	
	public boolean isForPareri() {
		return forPareri;
	}
	
	public boolean isForPutt() {
		return forPutt;
	}
	

//	static public TipoProcedimento getTipoProcedimento(String value) {
//		switch (value) {
//		case "AUTORIZZAZIONE PAESAGGISTICA art. 146, D. Lgs. 42/2004 – art. 90, NTA PPTR (ORDINARIA)":
//			return TipoProcedimento.AUT_PAES_ORD;
//		case "AUTORIZZAZIONE PAESAGGISTICA SEMPLIFICATA D.P.R. 139/2010– art. 90, NTA PPTR (PER LE OPERE IL CUI IMPATTO PAESAGGISTICO E' VALUTATO MEDIANTE UNA DOCUMENTAZIONE SEMPLIFICATA)":
//			return TipoProcedimento.ACCERT_COMPAT_PAES_DPR_139_2010;
//		case "AUTORIZZAZIONE PAESAGGISTICA SEMPLIFICATA D.P.R. 31/2017– art. 90, NTA PPTR (PER LE OPERE IL CUI IMPATTO PAESAGGISTICO E' VALUTATO MEDIANTE UNA DOCUMENTAZIONE SEMPLIFICATA)":
//			return TipoProcedimento.ACCERT_COMPAT_PAES_DPR_31_2017;
//		case "ACCERTAMENTO DI COMPATIBILITÀ PAESAGGISTICA Art. 167 e 181, D. Lgs. 42/2004 (PER OPERE IN DIFFORMITA’ O ASSENZA DI AUTORIZZAZIONE PAESAGGISTICA)":
//			return TipoProcedimento.ACCERT_COMPAT_PAES_DLGS_42_2004;
//		case "ACCERTAMENTO DI COMPATIBILITÀ PAESAGGISTICA art. 91, NTA PPTR [IN VIGENZA D.P.R.  31/2017] (PER INTERVENTI CHE COMPORTINO MODIFICA DELLO STATO DEI LUOGHI NEGLI ULTERIORI CONTESTI COME INDIVIDUATI NELL’ART. 38 C. 3.1 NTA PPTR)":
//			return TipoProcedimento.ACCERT_COMPAT_PAES_DPR_31_2017;
//		case "ACCERTAMENTO DI COMPATIBILITÀ PAESAGGISTICA art. 91, NTA PPTR [IN VIGENZA D.P.R. 139/2010] (PER INTERVENTI CHE COMPORTINO MODIFICA DELLO STATO DEI LUOGHI NEGLI ULTERIORI CONTESTI COME INDIVIDUATI NELL’ART. 38 C. 3.1 NTA PPTR)":
//			return TipoProcedimento.ACCERT_COMPAT_PAES_DPR_139_2010;
//		default:
//			return null;
//		}
//	}

	/**
	 * mapping dei codici civilia CIVILIA_CS.TNO_PPTR_TIPOPROCEDIMENTO
	 * @autor Adriano Colaianni
	 * @date 20 apr 2021
	 * @param idCivilia
	 * @return
	 */
	static public TipoProcedimento fromIdCivilia(String idCivilia) {
		switch (idCivilia) {
		case "1":
			return TipoProcedimento.AUT_PAES_ORD;
		case "2":
			return TipoProcedimento.AUT_PAES_SEMPL_DPR_139_2010;
		case "3":
			return TipoProcedimento.ACCERT_COMPAT_PAES_DLGS_42_2004;
		case "4":
			return TipoProcedimento.ACCERT_COMPAT_PAES_DPR_139_2010;
		case "5":
			return TipoProcedimento.AUT_PAES_SEMPL_DPR_31_2017;
		case "6":
			return TipoProcedimento.ACCERT_COMPAT_PAES_DPR_31_2017;
		}
		return null;
	}
	
}
