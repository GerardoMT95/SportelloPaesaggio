package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import it.eng.tz.puglia.util.string.StringUtil;

public enum Gruppi {
	
	ADMIN 	    ("SUPER Amministratore di Applicazione",null),
	RICHIEDENTI ("Richiedenti",null),
	USER_CL		("Utenti commissione locale",null), //in è un gruppo di transito ( non e' ammesso nell'applicazione) conterrà gli utenti da migrare nel corretto CL_XXX_F
	REG_  		("Regione","REG"),
	ETI_  		("Ente Territorialmente Interessato","ETI"),
	SBAP_ 		("Soprintendenza","SBAP"),
	ED_   		("Ente Delegato","ED"),
    CL_   		("Commissione Locale","CL");//riferita ad un ente delegato
	
	
	private String value;
	private String tipoOrganizzazione;
	
	
	private Gruppi(String text,String tipoOrganizzazione) {
		this.value = text;
		this.tipoOrganizzazione = tipoOrganizzazione;
	}
	
	
	public String getTextValue() {
		return value;
	}
	
	public String getTipoOrganizzazione() {
		return tipoOrganizzazione;
	}
	
	
	public static Gruppi[] tipoOrganizzazioniAbilitazione() {
		Gruppi[] ret = {Gruppi.REG_, Gruppi.ETI_, Gruppi.SBAP_, Gruppi.ED_, Gruppi.CL_};
		return ret;
	}
	
	public static boolean hasRup(Gruppi gruppo) {
		if(gruppo!=null && 
				(gruppo.equals(REG_)||gruppo.equals(ED_))) {
			return true;
		}
		return false;
	}
	
	/**
	 * check sintax group name
	 * @author acolaianni
	 *
	 * @param gruppo
	 * @return
	 */
	public static boolean isGruppoSenzaOrganizzazione(String gruppo) {
		return StringUtil.isNotEmpty(gruppo)
				&& 
				Arrays.asList(new Gruppi[] { Gruppi.ADMIN, Gruppi.RICHIEDENTI, Gruppi.USER_CL })
				.stream()
				.filter(gr -> gr.name().equals(gruppo))
				.findAny().isPresent();
	}
	
	/**
	 * check sintax group name  GGGG_NNN_R     (R  => A, D, F)   NNN=intero idOrg  GGGG in REG_
	 * @author acolaianni
	 *
	 * @param gruppo
	 * @return
	 */
	public static boolean isGruppoConOrganizzazione(String gruppo) {
		if(StringUtil.isEmpty(gruppo)) 
			return false;
		String[] parts = gruppo.split("_");
		if(parts.length!=3)
			return false;
		try {
			int idOrg = Integer.valueOf(parts[1]); //numero
		}catch(NumberFormatException e) {
			return false;
		}
		return  Arrays.asList(new Gruppi[] { Gruppi.REG_, Gruppi.ED_, Gruppi.CL_ , Gruppi.ETI_,Gruppi.SBAP_})
				.stream()
				.filter(gr -> gruppo.startsWith(gr.name()))
				.findAny().isPresent()
				&&
				Arrays.asList("A","D","F").contains(parts[2]);
	}
	
	/**
	 * da ED_109_F restituisce ED_109 se gruppo con organizzazione, altrimenti restituisce se stesso
	 * @author acolaianni
	 *
	 * @param codiceGruppoRuolo
	 * @return
	 */
	public static String gruppoEsclusoRuolo(String codiceGruppoRuolo) {
		if (isGruppoConOrganizzazione(codiceGruppoRuolo)) {
			String[] parts = codiceGruppoRuolo.split("_");
			return parts[0] + "_" + parts[1];
		} else {
			return codiceGruppoRuolo;
		}
	}	
	
	public static boolean isValidSyntax(String gruppo) {
		return isGruppoSenzaOrganizzazione(gruppo) || isGruppoConOrganizzazione(gruppo);
	}
	
	/**
	 * 
	 * @param gruppo1
	 * @param gruppo2
	 * @return
	 */
	public static boolean stessoGruppoEsclusoRuolo(String gruppo1,String gruppo2) {
		if(gruppo1==null || gruppo2==null) return false;
		String[] parts1=gruppo1.split("_");
		String[] parts2=gruppo2.split("_");
		return gruppo1.equals(gruppo2) ||
				(isGruppoConOrganizzazione(gruppo1) && 
						parts1.length>=3 && parts2.length>=3 && 
						StringUtil.concateneString(parts1[0],parts1[1]).equals(
								StringUtil.concateneString(parts2[0],parts2[1]))
						);
	}
	
	/**
	 * se ED_109_F restituisce set ED_109_F ED_109_A ED_109_D
	 * se ADMIN restituisce ADMIN
	 * @author acolaianni
	 *
	 * @param codiceGruppoRuolo
	 * @return
	 */
	public static Set<String> getAllMyGruppi(String codiceGruppoRuolo){
		Set<String> ret=new HashSet<>();
		ret.add(codiceGruppoRuolo);
		if(isGruppoConOrganizzazione(codiceGruppoRuolo)) {
			//aggiungo eventuali _X
			ret.add(gruppoEsclusoRuolo(codiceGruppoRuolo)+"_A");
			ret.add(gruppoEsclusoRuolo(codiceGruppoRuolo)+"_D");
			ret.add(gruppoEsclusoRuolo(codiceGruppoRuolo)+"_F");
		}
		return ret;
	}
	
	/**
	 * ET=ETI_
	 * SOP=SBAP_
	 * ED=ED_
	 * RI=RICHIEDENTI
	 * @author acolaianni
	 *
	 * @param ruolo
	 * @return
	 */
	public static Gruppi fromRuoloCivilia(String ruolo) {
		switch (ruolo) {
		case "ET":
			return Gruppi.ETI_;
		case "SOP":
			return Gruppi.SBAP_;
		case "ED":
			return Gruppi.ED_;
		case "RI":
			return Gruppi.RICHIEDENTI;
		default:
			return null;
		}
		
	}
}