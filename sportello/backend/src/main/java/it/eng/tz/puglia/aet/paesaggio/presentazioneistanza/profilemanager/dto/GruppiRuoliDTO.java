package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.profilemanager.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PlainStringValueLabel;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Gruppi;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Ruoli;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.utils.CustomGroupsComparator;

public class GruppiRuoliDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String gruppo;
	@JsonIgnore
	private Gruppi tipoGruppo;
	@JsonIgnore
	private String codiceGruppo;
	private List<PlainStringValueLabel> ruoli;

	
	
	public String getCodiceGruppo() {
		return codiceGruppo;
	}
	public void setCodiceGruppo(String codiceGruppo) {
		this.codiceGruppo = codiceGruppo;
	}
	public GruppiRuoliDTO() { 
		this.ruoli = new ArrayList<PlainStringValueLabel>();
	}	
	public String getGruppo() {
		return gruppo;
	}
	
	public Gruppi getTipoGruppo()
	{
		return tipoGruppo;
	}
	public void setTipoGruppo(Gruppi tipoGruppo)
	{
		this.tipoGruppo = tipoGruppo;
	}
	
	public void setGruppo(String gruppo) {
		this.gruppo = gruppo;
	}

	public List<PlainStringValueLabel> getRuoli() {
		return ruoli;
	}
	public void setRuoli(List<PlainStringValueLabel> ruoli) {
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

	
	public static List<GruppiRuoliDTO> creaDaPlainStringValueLabel(Set<PlainStringValueLabel> setPlainStringValueLabel) {
		
		List<GruppiRuoliDTO> listaGruppiRuoliDTO = new ArrayList<>();
		
		setPlainStringValueLabel.forEach(tipologica->{
			
			boolean giaPresente = false;
			
			for (GruppiRuoliDTO gruppoRuoli : listaGruppiRuoliDTO) {
				if(tipologica.getDescription()!= null) {
					//if (gruppoRuoli.getGruppo().equalsIgnoreCase(tipologica.getDescription())) {
					if (Gruppi.stessoGruppoEsclusoRuolo(gruppoRuoli.getCodiceGruppo(), tipologica.getValue())) {
						gruppoRuoli.getRuoli().add(new PlainStringValueLabel(tipologica.getValue(), GruppiRuoliDTO.creaNomeRuolo(tipologica.getValue())));
						giaPresente = true;
					}
				}
			}
			if (giaPresente==false && tipologica.getDescription()!=null) {
				GruppiRuoliDTO nuovoGR = new GruppiRuoliDTO();
				nuovoGR.setGruppo(tipologica.getDescription());
				Gruppi g = null;
				if(tipologica.getValue().equals(Gruppi.ADMIN.name()))
					g = Gruppi.ADMIN;
				else
					g = Gruppi.valueOf(StringUtils.split(tipologica.getValue(), "_")[0]+"_");
				if(tipologica.getValue().startsWith(Gruppi.ED_.name())) {
					nuovoGR.setGruppo(tipologica.getDescription()+" (Ente Delegato)");
				}else if(tipologica.getValue().startsWith(Gruppi.ETI_.name())) {
					nuovoGR.setGruppo(tipologica.getDescription()+" (Ente Terr.Int.)");
				}
				nuovoGR.setTipoGruppo(g);
				nuovoGR.setCodiceGruppo(tipologica.getValue());
				nuovoGR.getRuoli().add(new PlainStringValueLabel(tipologica.getValue(), GruppiRuoliDTO.creaNomeRuolo(tipologica.getValue())));
				listaGruppiRuoliDTO.add(nuovoGR);
			}
		});
		listaGruppiRuoliDTO.sort(new CustomGroupsComparator());
		return listaGruppiRuoliDTO;
	}


	private static String creaNomeRuolo(String gruppo_id_ruolo) {
			 if (gruppo_id_ruolo.endsWith	 ("_A")) 	   	  		return Ruoli.AMMINISTRATORE.getTextValue();
		else if (gruppo_id_ruolo.endsWith	 ("_D")) 	   	  		return Ruoli.DIRIGENTE	   .getTextValue();
		else if (gruppo_id_ruolo.endsWith	 ("_F")) 	   	  		return Ruoli.FUNZIONARIO   .getTextValue();
		else if (gruppo_id_ruolo.equals(Gruppi.RICHIEDENTI.name())) return Ruoli.RICHIEDENTE   .getTextValue();
		else if (gruppo_id_ruolo.equals(Gruppi.ADMIN	  .name())) return Ruoli.ADMIN		   .getTextValue();
		else 										   		  		return "ERRORE";
	}
	
}