package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.StatoCorrispondenza;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.TipoDestinatario;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.profilemanager.dto.AssUtenteGruppo;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.profilemanager.dto.UtenteGruppo;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto.PaesaggioEmailDTO;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * DTO for table presentazione_istanza.destinatario
 */
public class DestinatarioDTO implements Serializable
{

	private static final long serialVersionUID = 1200039208L;
	// COLUMN id
	private long id;
	// COLUMN type
	private TipoDestinatario tipo;
	// COLUMN email
	private String email;
	// COLUMN stato
	private StatoCorrispondenza stato;
	// COLUMN pec
	private boolean pec;
	// COLUMN denominazione
	private String nome;
	// COLUMN data_ricezione
	private Timestamp dataRicezione;
	// COLUMN id_corrispondenza
	private long idCorrispondenza;
	
	//utilizzate per il FE per renderizzare lo stato di ricezione
	private List<RicevutaDTO> ricevute;
	
	
	public List<RicevutaDTO> getRicevute() {
		return ricevute;
	}

	public void setRicevute(List<RicevutaDTO> ricevute) {
		this.ricevute = ricevute;
	}

	public DestinatarioDTO()
	{
		super();
	}
	
	public DestinatarioDTO(String denominazione, String email, String tipo) throws Exception
	{
		super();
		if(!StringUtil.isEmail(email))
			throw new Exception("Email non valida per la creazione del destinatario "+email);
		this.setEmail(email);
		this.setNome(denominazione);
		if(tipo==null || !tipo.equals("TO"))
		{
			this.setTipo(TipoDestinatario.valueOf("CC"));
		}else {
			this.setTipo(TipoDestinatario.valueOf("TO"));
		}
	}
	
	public DestinatarioDTO(String denominazione, String email, Boolean isPec, TipoDestinatario tipo) throws Exception
	{
		super();
		if(!StringUtil.isEmail(email))
			throw new Exception("Email non valida per la creazione del destinatario "+email);
		this.setEmail(email);
		this.setNome(denominazione);
		this.pec = isPec;
		this.tipo = tipo;
	}
	
	public DestinatarioDTO(PaesaggioEmailDTO other)
	{
		super();
		this.email = other.getEmail();
		this.pec = other.getPec();
		this.tipo = TipoDestinatario.TO;
		this.nome = other.getDenominazione();
	}
	
	public DestinatarioDTO(ReferentiDTO other)
	{
		super();
		pec = StringUtil.isNotBlank(other.getPec());
		tipo = TipoDestinatario.TO;
		email = pec ? other.getPec() : other.getMail();
		nome = StringUtil.concateneString(other.getNome(), " ", other.getCognome());
		
	}
	
	public DestinatarioDTO(UtenteGruppo other)
	{
		super();
		pec = false;
		tipo = TipoDestinatario.TO;
		email = other.getEmail();
		nome = StringUtil.concateneString(other.getNomeUtente(), " ", other.getCognomeUtente());
		
	}
	
	public DestinatarioDTO(AssUtenteGruppo other)
	{
		super();
		pec = false;
		tipo = TipoDestinatario.TO;
		email = other.getMail();
		nome = StringUtil.concateneString(other.getNome(), " ", other.getCognome());
		
	}
	
	public DestinatarioDTO(DestinatarioDTO dto)
	{
		id = dto.getId();
		tipo = dto.getTipo();
		email = dto.getEmail();
		stato = dto.getStato();
		pec = dto.getPec();
		nome = dto.getNome();
		dataRicezione = dto.getDataRicezione();
		idCorrispondenza =dto.getIdCorrispondenza();
	}
	
	/**
	 * 
	 * @param dto
	 * @param prioritaPec se a true prende la pec... se non esiste passa alla
	 *                    mail... e lo stesso al contrario....
	 */
	public DestinatarioDTO(TemplateDestinatarioDTO dto, boolean prioritaPec) {
		id = 0;
		tipo = TipoDestinatario.valueOf(dto.getTipo());
		if ((prioritaPec && StringUtil.isEmail(dto.getPec())) || !StringUtil.isEmail(dto.getEmail())) {
			email = dto.getPec();
			pec = true;

		} else {
			email = dto.getEmail();
			pec = false;
		}
		stato = null;
		nome = dto.getDenominazione();
		dataRicezione = null;
		idCorrispondenza = 0;
	}
	
	public DestinatarioDTO(TemplateDestinatarioDTO dto) 
	{
		this(dto, true);
	}


	public long getId()
	{
		return this.id;
	}
	public void setId(long id)
	{
		this.id = id;
	}

	public TipoDestinatario getTipo()
	{
		return tipo;
	}
	public void setTipo(TipoDestinatario tipo)
	{
		this.tipo = tipo;
	}

	public String getEmail()
	{
		return this.email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}

	public StatoCorrispondenza getStato()
	{
		return this.stato;
	}
	public void setStato(StatoCorrispondenza stato)
	{
		this.stato = stato;
	}

	public boolean getPec()
	{
		return this.pec;
	}
	public void setPec(boolean pec)
	{
		this.pec = pec;
	}

	public String getNome()
	{
		return nome;
	}
	public void setNome(String nome)
	{
		this.nome = nome;
	}

	public Timestamp getDataRicezione()
	{
		return this.dataRicezione;
	}
	public void setDataRicezione(Timestamp dataRicezione)
	{
		this.dataRicezione = dataRicezione;
	}

	public long getIdCorrispondenza()
	{
		return this.idCorrispondenza;
	}
	public void setIdCorrispondenza(long idCorrispondenza)
	{
		this.idCorrispondenza = idCorrispondenza;
	}

}
