package it.eng.tz.puglia.servizi_esterni.jasper.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JasperCampionamentoDTO implements SezioniDinamiche{
	
	private List<JasperCampionamentoListaDestinatariDTO> destinatariTO;
	private List<JasperCampionamentoListaDestinatariDTO> destinatariCC;
	private List<JasperCampionamentoListaFascicoliDTO> listAll;
	private List<JasperCampionamentoListaFascicoliDTO> listSelected;
	private List<JasperCampionamentoListaFascicoliDTO> listNotSelected;
	private List<JasperCampionamentoFooterDTO> footerFields;
	private List<JasperCampionamentoFirmaDTO> firmaFields;
	private String numeroProtocolo;
	private String dataProtocolo;					// (today)
	private String dataRegistrazionePrimoPiano;		// data inizio campionamento
	private String dataRegistrazionePrimoPiano28;	// data fine   campionamento (today)
	private String dipartimento;
	private String sezione;
	private String dirigente;
	private String sezionaleProtocollo;
	private Map<String,Object> sezioniDinamiche;
	
	
	public JasperCampionamentoDTO() {
		this.destinatariTO 	 = new ArrayList<>();
		this.destinatariCC 	 = new ArrayList<>();
		this.listAll 		 = new ArrayList<>();
		this.listSelected 	 = new ArrayList<>();
		this.listNotSelected = new ArrayList<>();
		this.footerFields 	 = new ArrayList<>();
		this.firmaFields 	 = new ArrayList<>();
	}
	
	
	public List<JasperCampionamentoListaDestinatariDTO> getDestinatariTO() {
		return destinatariTO;
	}
	public void setDestinatariTO(List<JasperCampionamentoListaDestinatariDTO> destinatariTO) {
		this.destinatariTO = destinatariTO;
	}
	public List<JasperCampionamentoListaDestinatariDTO> getDestinatariCC() {
		return destinatariCC;
	}
	public void setDestinatariCC(List<JasperCampionamentoListaDestinatariDTO> destinatariCC) {
		this.destinatariCC = destinatariCC;
	}
	public List<JasperCampionamentoListaFascicoliDTO> getListAll() {
		return listAll;
	}
	public void setListAll(List<JasperCampionamentoListaFascicoliDTO> listAll) {
		this.listAll = listAll;
	}
	public List<JasperCampionamentoListaFascicoliDTO> getListSelected() {
		return listSelected;
	}
	public void setListSelected(List<JasperCampionamentoListaFascicoliDTO> listSelected) {
		this.listSelected = listSelected;
	}
	public List<JasperCampionamentoListaFascicoliDTO> getListNotSelected() {
		return listNotSelected;
	}
	public void setListNotSelected(List<JasperCampionamentoListaFascicoliDTO> listNotSelected) {
		this.listNotSelected = listNotSelected;
	}
	public List<JasperCampionamentoFooterDTO> getFooterFields() {
		return footerFields;
	}
	public void setFooterFields(List<JasperCampionamentoFooterDTO> footerFields) {
		this.footerFields = footerFields;
	}
	public List<JasperCampionamentoFirmaDTO> getFirmaFields() {
		return firmaFields;
	}
	public void setFirmaFields(List<JasperCampionamentoFirmaDTO> firmaFields) {
		this.firmaFields = firmaFields;
	}
	public String getNumeroProtocolo() {
		return numeroProtocolo;
	}
	public void setNumeroProtocolo(String numeroProtocolo) {
		this.numeroProtocolo = numeroProtocolo;
	}
	public String getDataRegistrazionePrimoPiano() {
		return dataRegistrazionePrimoPiano;
	}
	public void setDataRegistrazionePrimoPiano(String dataRegistrazionePrimoPiano) {
		this.dataRegistrazionePrimoPiano = dataRegistrazionePrimoPiano;
	}
	public String getDataRegistrazionePrimoPiano28() {
		return dataRegistrazionePrimoPiano28;
	}
	public void setDataRegistrazionePrimoPiano28(String dataRegistrazionePrimoPiano28) {
		this.dataRegistrazionePrimoPiano28 = dataRegistrazionePrimoPiano28;
	}
	public String getDataProtocolo() {
		return dataProtocolo;
	}
	public void setDataProtocolo(String dataProtocolo) {
		this.dataProtocolo = dataProtocolo;
	}
	public String getDipartimento() {
		return dipartimento;
	}
	public void setDipartimento(String dipartimento) {
		this.dipartimento = dipartimento;
	}
	public String getSezione() {
		return sezione;
	}
	public void setSezione(String sezione) {
		this.sezione = sezione;
	}
	public String getDirigente() {
		return dirigente;
	}
	public void setDirigente(String dirigente) {
		this.dirigente = dirigente;
	}
	public String getSezionaleProtocollo() {
		return sezionaleProtocollo;
	}
	public void setSezionaleProtocollo(String sezionaleProtocollo) {
		this.sezionaleProtocollo = sezionaleProtocollo;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataProtocolo == null) ? 0 : dataProtocolo.hashCode());
		result = prime * result + ((dataRegistrazionePrimoPiano == null) ? 0 : dataRegistrazionePrimoPiano.hashCode());
		result = prime * result	+ ((dataRegistrazionePrimoPiano28 == null) ? 0 : dataRegistrazionePrimoPiano28.hashCode());
		result = prime * result + ((destinatariCC == null) ? 0 : destinatariCC.hashCode());
		result = prime * result + ((destinatariTO == null) ? 0 : destinatariTO.hashCode());
		result = prime * result + ((dipartimento == null) ? 0 : dipartimento.hashCode());
		result = prime * result + ((dirigente == null) ? 0 : dirigente.hashCode());
		result = prime * result + ((firmaFields == null) ? 0 : firmaFields.hashCode());
		result = prime * result + ((footerFields == null) ? 0 : footerFields.hashCode());
		result = prime * result + ((listAll == null) ? 0 : listAll.hashCode());
		result = prime * result + ((listNotSelected == null) ? 0 : listNotSelected.hashCode());
		result = prime * result + ((listSelected == null) ? 0 : listSelected.hashCode());
		result = prime * result + ((numeroProtocolo == null) ? 0 : numeroProtocolo.hashCode());
		result = prime * result + ((sezionaleProtocollo == null) ? 0 : sezionaleProtocollo.hashCode());
		result = prime * result + ((sezione == null) ? 0 : sezione.hashCode());
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
		JasperCampionamentoDTO other = (JasperCampionamentoDTO) obj;
		if (dataProtocolo == null) {
			if (other.dataProtocolo != null)
				return false;
		} else if (!dataProtocolo.equals(other.dataProtocolo))
			return false;
		if (dataRegistrazionePrimoPiano == null) {
			if (other.dataRegistrazionePrimoPiano != null)
				return false;
		} else if (!dataRegistrazionePrimoPiano.equals(other.dataRegistrazionePrimoPiano))
			return false;
		if (dataRegistrazionePrimoPiano28 == null) {
			if (other.dataRegistrazionePrimoPiano28 != null)
				return false;
		} else if (!dataRegistrazionePrimoPiano28.equals(other.dataRegistrazionePrimoPiano28))
			return false;
		if (destinatariCC == null) {
			if (other.destinatariCC != null)
				return false;
		} else if (!destinatariCC.equals(other.destinatariCC))
			return false;
		if (destinatariTO == null) {
			if (other.destinatariTO != null)
				return false;
		} else if (!destinatariTO.equals(other.destinatariTO))
			return false;
		if (dipartimento == null) {
			if (other.dipartimento != null)
				return false;
		} else if (!dipartimento.equals(other.dipartimento))
			return false;
		if (dirigente == null) {
			if (other.dirigente != null)
				return false;
		} else if (!dirigente.equals(other.dirigente))
			return false;
		if (firmaFields == null) {
			if (other.firmaFields != null)
				return false;
		} else if (!firmaFields.equals(other.firmaFields))
			return false;
		if (footerFields == null) {
			if (other.footerFields != null)
				return false;
		} else if (!footerFields.equals(other.footerFields))
			return false;
		if (listAll == null) {
			if (other.listAll != null)
				return false;
		} else if (!listAll.equals(other.listAll))
			return false;
		if (listNotSelected == null) {
			if (other.listNotSelected != null)
				return false;
		} else if (!listNotSelected.equals(other.listNotSelected))
			return false;
		if (listSelected == null) {
			if (other.listSelected != null)
				return false;
		} else if (!listSelected.equals(other.listSelected))
			return false;
		if (numeroProtocolo == null) {
			if (other.numeroProtocolo != null)
				return false;
		} else if (!numeroProtocolo.equals(other.numeroProtocolo))
			return false;
		if (sezionaleProtocollo == null) {
			if (other.sezionaleProtocollo != null)
				return false;
		} else if (!sezionaleProtocollo.equals(other.sezionaleProtocollo))
			return false;
		if (sezione == null) {
			if (other.sezione != null)
				return false;
		} else if (!sezione.equals(other.sezione))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "JasperCampionamentoDTO [destinatariTO=" + destinatariTO + ", destinatariCC=" + destinatariCC
				+ ", listAll=" + listAll + ", listSelected=" + listSelected + ", listNotSelected=" + listNotSelected
				+ ", footerFields=" + footerFields + ", firmaFields=" + firmaFields + ", numeroProtocolo="
				+ numeroProtocolo + ", dataProtocolo=" + dataProtocolo + ", dataRegistrazionePrimoPiano="
				+ dataRegistrazionePrimoPiano + ", dataRegistrazionePrimoPiano28=" + dataRegistrazionePrimoPiano28
				+ ", dipartimento=" + dipartimento + ", sezione=" + sezione + ", dirigente=" + dirigente
				+ ", sezionaleProtocollo=" + sezionaleProtocollo + "]";
	}


	public Map<String,Object> getSezioniDinamiche() {
		return sezioniDinamiche;
	}


	public void setSezioniDinamiche(Map<String,Object> sezioniDinamiche) {
		this.sezioniDinamiche = sezioniDinamiche;
	}
	
}