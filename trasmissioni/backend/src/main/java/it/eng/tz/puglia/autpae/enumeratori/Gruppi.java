package it.eng.tz.puglia.autpae.enumeratori;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import it.eng.tz.puglia.util.string.StringUtil;

public enum Gruppi {
	
	ADMIN ("SUPER Amministratore di Applicazione",null),
	REG_  ("Regione","REG"),
	ETI_  ("Ente Territorialmente Interessato","ETI"),
	SBAP_ ("Soprintendenza","SBAP"),
	ED_   ("Ente Delegato","ED");
  //CL_   ("Commissione Locale"); //da gestire in istruttoria...
	
	
	private String value; //valore human readable
	private String tipoOrganizzazione;  //valore nel db common.paesaggio_organizzazione.tipo
	
	
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
	
	public static Gruppi[] tipoOrganizzazioniAbilitazione(boolean isPareri) {
		Gruppi[] ret= {Gruppi.REG_,Gruppi.ETI_,Gruppi.SBAP_,Gruppi.ED_};
		if(isPareri) {
			//si esclude SBAP per pareri
			return new Gruppi[] {Gruppi.REG_,Gruppi.ETI_,Gruppi.ED_}; 
		}
		return ret;
	}
	
	/**
	 * 
	 * @author acolaianni
	 *
	 * @param gruppo
	 * @return
	 */
	public static boolean isGruppoSenzaOrganizzazione(String gruppo) {
		return StringUtil.isNotEmpty(gruppo)
				&& 
				Arrays.asList(new Gruppi[] { Gruppi.ADMIN})
				.stream()
				.filter(gr -> gr.name().equals(gruppo))
				.findAny().isPresent();
	}
	
	/**
	 * check syntax group name  GGGG_NNN_R     (R  => A, D, F)   NNN=intero idOrg  GGGG in REG_
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
		return  Arrays.asList(new Gruppi[] { Gruppi.REG_, Gruppi.ED_, Gruppi.ETI_,Gruppi.SBAP_})
				.stream()
				.filter(gr -> gruppo.startsWith(gr.name()))
				.findAny().isPresent()
				&&
				Arrays.asList("A","D","F").contains(parts[2]);
	}
	
	/**
	 * 
	 * @autor Adriano Colaianni
	 * @date 7 giu 2021
	 * @param gruppo
	 * @param isPareri se pareri vengono escluse le SBAP
	 * @return
	 */
	public static boolean isValidSyntax(String gruppo,boolean isPareri) {
		return isGruppoSenzaOrganizzazione(gruppo) || 
				(isGruppoConOrganizzazione(gruppo) && 
						(!isPareri || !isSbap(gruppo)) 
						);
	}
	
	public static boolean isSbap(String gruppo) {
		return StringUtil.isNotEmpty(gruppo)&& gruppo.startsWith(Gruppi.SBAP_.name());
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
	 * si aspetta gruppo TIPO_NUMGRUPPO_R in caso di errore restituisce 0
	 * @autor Adriano Colaianni
	 * @date 4 mag 2021
	 * @param gruppo
	 * @return
	 */
	public static int getIdOrganizzazione(String codiceGruppo) {
		if(StringUtil.isEmpty(codiceGruppo)) 
			return 0;
		String[] parts = codiceGruppo.split("_");
		if(parts.length!=3)
			return 0;
		try {
			int idOrg = Integer.valueOf(parts[1]); //numero
			return idOrg;
		}catch(NumberFormatException e) {
			return 0;
		}
	}
	
	/**
	 * null, A D F
	 * @autor Adriano Colaianni
	 * @date 4 mag 2021
	 * @param codiceGruppo
	 * @return
	 */
	public static String getCharRuolo(String codiceGruppo) {
		if(StringUtil.isEmpty(codiceGruppo)) 
			return null;
		String[] parts = codiceGruppo.split("_");
		if(parts.length!=3)
			return null;
		try {
			return parts[2];
		}catch(NumberFormatException e) {
			return null;
		}
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
	 * da ED_109_F  restituisce Gruppi.ED_ , null se non riconosciuto
	 * @autor Adriano Colaianni
	 * @date 25 giu 2021
	 * @param gruppo_id_ruolo
	 * @return
	 */
	public static Gruppi fromCodiceGruppoPM(String gruppo_id_ruolo) {
		if (StringUtil.isEmpty(gruppo_id_ruolo)) return null;
			 if (gruppo_id_ruolo.toUpperCase().startsWith(Gruppi.REG_ .name()))	return Gruppi.REG_;
		else if (gruppo_id_ruolo.toUpperCase().startsWith(Gruppi.ETI_ .name())) return Gruppi.ETI_;
		else if (gruppo_id_ruolo.toUpperCase().startsWith(Gruppi.SBAP_.name())) return Gruppi.SBAP_;
		else if (gruppo_id_ruolo.toUpperCase().startsWith(Gruppi.ED_  .name())) return Gruppi.ED_;
		else if (gruppo_id_ruolo.toUpperCase().equals	 (Gruppi.ADMIN.name())) return Gruppi.ADMIN;
		else 								  					  	 			return null;
	}
}