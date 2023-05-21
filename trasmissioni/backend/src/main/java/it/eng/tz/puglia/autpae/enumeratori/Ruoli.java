package it.eng.tz.puglia.autpae.enumeratori;

import org.hsqldb.lib.StringUtil;

public enum Ruoli {
	
	ADMIN		   ("SUPER Amministratore di Applicazione","Amministratore Applicazione","ADMIN"),	// in realtà non è un RUOLO ma è un GRUPPO
	AMMINISTRATORE ("Amministratore","Amm","A"),
	FUNZIONARIO	   ("Funzionario","Funz","F"),
	DIRIGENTE	   ("Dirigente","Dir","D");
	
	
	private String value;
	private String pmAbbr;
	private String pmCode;//codice su pm es. ADMIN o suffisso R in  TP_IDORG_R 
	
	
	private Ruoli(String text,String pmAbbreviazione,String pmCode) {
		this.value = text;
		this.pmAbbr = pmAbbreviazione;
		this.pmCode=pmCode;
	}
	
	
	public String getTextValue() {
		return value;
	}
	
	public String getPmAbbr() {
		return pmAbbr;
	}
	
	public String getPmCode() {
		return pmCode;
	}
	
	
	public static Ruoli getFromPmCode(String pmCode) {
		if(StringUtil.isEmpty(pmCode)) return null;
		switch(pmCode) {
		case "ADMIN":
			return ADMIN;
		case "A":
			return AMMINISTRATORE;
		case "D":
			return DIRIGENTE;
		case "F":
			return FUNZIONARIO;
		}
		return null;
		
	}
	
	public static String getRuoloPlurale(String pmCode) {
		if(StringUtil.isEmpty(pmCode)) return null;
		switch(pmCode) {
		case "A":
			return "Amministratori";
		case "D":
			return "Dirigenti";
		case "F":
			return "Funzionari";
		}
		return null;
		
	}
}