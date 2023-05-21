package it.eng.tz.puglia.autpae.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.eng.tz.puglia.autpae.dto.TipologicaDestinatarioDTO;
import it.eng.tz.puglia.autpae.enumeratori.StatoCorrispondenza;
import it.eng.tz.puglia.autpae.enumeratori.TipoDestinatario;

/**
 * DTO for table public.destinatario
 */
public class DestinatarioDTO extends TipologicaDestinatarioDTO
{

	private static final long serialVersionUID = 6693642738L;

	private Long id;
	private Long idCorrispondenza;
//	@JsonProperty("tipo")
//	private TipoDestinatario type;
//	private String email;
//	@JsonProperty("nome")
//	private String denominazione;
	private StatoCorrispondenza stato;
//	private Boolean pec;
	private Date dataRicezione; // ???????? ora sta in RicevutaDTO --> data
	private List<RicevutaDTO> ricevute;

	public DestinatarioDTO()
	{
		this.ricevute = new ArrayList<RicevutaDTO>();
	}

	public DestinatarioDTO(String email, StatoCorrispondenza stato)
	{
//		this.email = email;
		setEmail(email);
		this.stato = stato;
	}

	public DestinatarioDTO(TipoDestinatario type, String email, StatoCorrispondenza stato, Long idCorrispondenza, String denominazione)
	{
//		this.type = type;
//		this.email = email;
		setTipo(type);
		setEmail(email);
		this.stato = stato;
		this.idCorrispondenza = idCorrispondenza;
		// this.denominazione = denominazione;
		setNome(denominazione);
	}

	public Date getDataRicezione()
	{
		return dataRicezione;
	}
	public void setDataRicezione(Date dataRicezione)
	{
		this.dataRicezione = dataRicezione;
	}

//	public String getDenominazione() {
//		return getNome();
//	}
//	public void setDenominazione(String denominazione) {
//		this.denominazione = denominazione;
//	}
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}

//	public TipoDestinatario getType() {
//		return type;
//	}
//	public void setType(TipoDestinatario type) {
//		this.type = type;
//	}
//	public String getEmail() {
//		return email;
//	}
//	public void setEmail(String email) {
//		this.email = email;
//	}
	
	public Long getIdCorrispondenza()
	{
		return idCorrispondenza;
	}
	public void setIdCorrispondenza(Long idCorrispondenza)
	{
		this.idCorrispondenza = idCorrispondenza;
	}

	public StatoCorrispondenza getStato()
	{
		return stato;
	}
	public void setStato(StatoCorrispondenza stato)
	{
		this.stato = stato;
	}

//	public Boolean getPec() {
//		return pec;
//	}
//	public void setPec(Boolean pec) {
//		this.pec = pec;
//	}
	public List<RicevutaDTO> getRicevute()
	{
		return ricevute;
	}
	public void setRicevute(List<RicevutaDTO> ricevute)
	{
		this.ricevute = ricevute;
	}

}
