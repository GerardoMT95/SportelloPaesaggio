package it.eng.tz.puglia.autpae.dto.BE_to_FE;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import it.eng.tz.puglia.autpae.dto.TipologicaDTO;
import it.eng.tz.puglia.autpae.enumeratori.Gruppi;

public class GruppiRuoliDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String gruppo;
	private List<PlainTypeStringIdDTO> ruoli;
	private String codiceGruppo;

	
	public GruppiRuoliDTO() { 
		this.ruoli = new ArrayList<PlainTypeStringIdDTO>();
	}

		
	public String getGruppo() {
		return gruppo;
	}

	public void setGruppo(String gruppo) {
		this.gruppo = gruppo;
	}

	public List<PlainTypeStringIdDTO> getRuoli() {
		return ruoli;
	}

	public void setRuoli(List<PlainTypeStringIdDTO> ruoli) {
		this.ruoli = ruoli;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((gruppo == null) ? 0 : gruppo.hashCode());
		result = prime * result + ((ruoli == null) ? 0 : ruoli.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GruppiRuoliDTO other = (GruppiRuoliDTO) obj;
		if (gruppo == null) {
			if (other.gruppo != null)
				return false;
		} else if (!gruppo.equals(other.gruppo))
			return false;
		if (ruoli == null) {
			if (other.ruoli != null)
				return false;
		} else if (!ruoli.equals(other.ruoli))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "GruppiRuoliDTO [gruppo=" + gruppo + ", ruoli=" + ruoli + "]";
	}

	
	public static List<GruppiRuoliDTO> creaDaTipologicaDTO(Set<TipologicaDTO> setTipologicaDTO) {
		
		List<GruppiRuoliDTO> listaGruppiRuoliDTO = new ArrayList<>();
		
		setTipologicaDTO.forEach(tipologica->{
			
			boolean giaPresente = false;
			
			for (GruppiRuoliDTO gruppoRuoli : listaGruppiRuoliDTO) {
				//if (gruppoRuoli.getGruppo().equalsIgnoreCase(tipologica.getLabel())) {
				if (Gruppi.stessoGruppoEsclusoRuolo(gruppoRuoli.getCodiceGruppo(), tipologica.getCodice())) {
					gruppoRuoli.getRuoli().add(new PlainTypeStringIdDTO(tipologica.getCodice(), GruppiRuoliDTO.creaNomeRuolo(tipologica.getCodice())));
					giaPresente = true;
				}
			}
			if (giaPresente==false) {
				GruppiRuoliDTO nuovoGR = new GruppiRuoliDTO();
				//aggiungo il tipoOrganizzazione in caso di ED_ ETI_
				nuovoGR.setGruppo(tipologica.getLabel());
				nuovoGR.setCodiceGruppo(tipologica.getCodice());
				if(tipologica.getCodice().startsWith(Gruppi.ED_.name())) {
					nuovoGR.setGruppo(nuovoGR.getGruppo()+" (Ente Delegato)");
				}else if(tipologica.getCodice().startsWith(Gruppi.ETI_.name())) {
					nuovoGR.setGruppo(nuovoGR.getGruppo()+" (Ente Terr.Int.)");
				}
				nuovoGR.getRuoli().add(new PlainTypeStringIdDTO(tipologica.getCodice(), GruppiRuoliDTO.creaNomeRuolo(tipologica.getCodice())));
				listaGruppiRuoliDTO.add(nuovoGR);
			}
		});
		return listaGruppiRuoliDTO;
	}


	private static String creaNomeRuolo(String gruppo_id_ruolo) {
			 if (gruppo_id_ruolo.endsWith	 ("_A")) 	   	  return "Amministratore";
		else if (gruppo_id_ruolo.endsWith	 ("_D")) 	   	  return "Dirigente";
		else if (gruppo_id_ruolo.endsWith	 ("_F")) 	   	  return "Funzionario";
		else if (gruppo_id_ruolo.equals(Gruppi.ADMIN.name())) return "Amministratore di applicazione";
		else 										   		  return "ERRORE";
	}


	public String getCodiceGruppo() {
		return codiceGruppo;
	}


	public void setCodiceGruppo(String codiceGruppo) {
		this.codiceGruppo = codiceGruppo;
	}
	
}