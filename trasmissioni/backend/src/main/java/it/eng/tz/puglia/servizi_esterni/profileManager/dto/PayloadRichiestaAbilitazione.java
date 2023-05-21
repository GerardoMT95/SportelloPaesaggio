package it.eng.tz.puglia.servizi_esterni.profileManager.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Payload della risposta alla requests/save del profile manager es. risposta:
 * "payload": { "idUtente": "d3421fc3-039d-4540-851a-4020349b2b69",
 * "listaGruppi": [ "d3d52c5d-69d3-41f5-8a35-35fe559b2e64" ], "dataRichiesta":
 * "2019-12-12T16:30:38.000+0000", "dataAccettazione": null, "statoRichiesta":
 * "IN_ATTESA", "stato": null, "idGruppo": null, "idApplicazione": null,
 * "codiceApplicazione": "PEV", "username": "pevreg", "codiceGruppo": [ "A662"
 * ], "note": "", "urlDatiRichiesta": "", "existDetails": false }
 * 
 * @author acolaianni
 *
 */
public class PayloadRichiestaAbilitazione implements Serializable {

	/**
	 *
	 * 
	 */
	private static final long serialVersionUID = -3457085996554091129L;

	private String idUtente;
	private List<String> listaGruppi;
	
	public String getIdUtente() {
		return idUtente;
	}
	public void setIdUtente(String idUtente) {
		this.idUtente = idUtente;
	}
	public List<String> getListaGruppi() {
		return listaGruppi;
	}
	public void setListaGruppi(List<String> listaGruppi) {
		this.listaGruppi = listaGruppi;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idUtente == null) ? 0 : idUtente.hashCode());
		result = prime * result + ((listaGruppi == null) ? 0 : listaGruppi.hashCode());
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
		PayloadRichiestaAbilitazione other = (PayloadRichiestaAbilitazione) obj;
		if (idUtente == null) {
			if (other.idUtente != null)
				return false;
		} else if (!idUtente.equals(other.idUtente))
			return false;
		if (listaGruppi == null) {
			if (other.listaGruppi != null)
				return false;
		} else if (!listaGruppi.equals(other.listaGruppi))
			return false;
		return true;
	}
	

}
