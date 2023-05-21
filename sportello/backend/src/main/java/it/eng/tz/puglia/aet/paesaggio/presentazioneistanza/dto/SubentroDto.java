package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDelegatoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.SubentroDTO;
import it.eng.tz.puglia.util.date.DateUtil;

public class SubentroDto extends PraticaDelegatoDTO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonIgnore
	private String cmisIdModulo;
	private String fileNameModulo;
	private String hashModulo;
	@JsonIgnore
	private String cmisIdSollevamento;
	private String fileNameSollevamento;
	private String hashSollevamento;
	/**
	 * @return the cmisIdModulo
	 */
	public String getCmisIdModulo() {
		return cmisIdModulo;
	}
	/**
	 * @param cmisIdModulo the cmisIdModulo to set
	 */
	public void setCmisIdModulo(String cmisIdModulo) {
		this.cmisIdModulo = cmisIdModulo;
	}
	/**
	 * @return the fileNameModulo
	 */
	public String getFileNameModulo() {
		return fileNameModulo;
	}
	/**
	 * @param fileNameModulo the fileNameModulo to set
	 */
	public void setFileNameModulo(String fileNameModulo) {
		this.fileNameModulo = fileNameModulo;
	}
	/**
	 * @return the hashModulo
	 */
	public String getHashModulo() {
		return hashModulo;
	}
	/**
	 * @param hashModulo the hashModulo to set
	 */
	public void setHashModulo(String hashModulo) {
		this.hashModulo = hashModulo;
	}
	/**
	 * @return the cmisIdSollevamento
	 */
	public String getCmisIdSollevamento() {
		return cmisIdSollevamento;
	}
	/**
	 * @param cmisIdSollevamento the cmisIdSollevamento to set
	 */
	public void setCmisIdSollevamento(String cmisIdSollevamento) {
		this.cmisIdSollevamento = cmisIdSollevamento;
	}
	/**
	 * @return the fileNameSollevamento
	 */
	public String getFileNameSollevamento() {
		return fileNameSollevamento;
	}
	/**
	 * @param fileNameSollevamento the fileNameSollevamento to set
	 */
	public void setFileNameSollevamento(String fileNameSollevamento) {
		this.fileNameSollevamento = fileNameSollevamento;
	}
	/**
	 * @return the hashSollevamento
	 */
	public String getHashSollevamento() {
		return hashSollevamento;
	}
	/**
	 * @param hashSollevamento the hashSollevamento to set
	 */
	public void setHashSollevamento(String hashSollevamento) {
		this.hashSollevamento = hashSollevamento;
	}

	public SubentroDto() {
		
	}
	
	public SubentroDto(SubentroDTO entity) {
		this.setId(entity.getIdPratica());
		this.setCognome(entity.getCognome());
		this.setNome(entity.getNome());
		this.setCodiceFiscale(entity.getCodiceFiscale());
		this.setSesso(entity.getSesso());
		if(entity.getDataNascita()!=null) {
			this.setDataNascita(new Timestamp(entity.getDataNascita().getTime()));	
		}
		this.setIdNazioneNascita(entity.getIdNazioneNascita());
		this.setNazioneNascita(entity.getNazioneNascita());
		this.setIdComuneNascita(entity.getIdComuneNascita());
		this.setComuneNascita(entity.getComuneNascita());
		this.setComuneNascitaEstero(entity.getComuneNascitaEstero());
		this.setIdNazioneResidenza(entity.getIdNazioneResidenza());
		this.setNazioneResidenza(entity.getNazioneResidenza());
		this.setIdComuneResidenza(entity.getIdComuneResidenza());
		this.setComuneResidenza(entity.getComuneResidenza());
		this.setComuneResidenzaEstero(entity.getComuneResidenzaEstero());
		this.setIndirizzoResidenza(entity.getIndirizzoResidenza());
		this.setCivicoResidenza(entity.getCivicoResidenza());
		this.setCapResidenza(entity.getCapResidenza());
		this.setPec(entity.getPec());
		this.setMail(entity.getMail());
		this.setDateCreate(entity.getDateCreate());
		this.setCmisIdModulo(entity.getCmisIdModulo());
		this.setFileNameModulo(entity.getFileNameModulo());
		this.setHashModulo(entity.getHashModulo());
		this.setCmisIdSollevamento(entity.getCmisIdSollevamento());
		this.setFileNameSollevamento(entity.getFileNameSollevamento());
		this.setHashSollevamento(entity.getHashSollevamento());
		this.setProvinciaNascita(entity.getProvinciaNascita());
		this.setIdProvinciaNascita(entity.getIdProvinciaNascita());
		this.setProvinciaResidenza(entity.getProvinciaResidenza());
		this.setIdProvinciaResidenza(entity.getIdProvinciaResidenza());
	}
	
}
