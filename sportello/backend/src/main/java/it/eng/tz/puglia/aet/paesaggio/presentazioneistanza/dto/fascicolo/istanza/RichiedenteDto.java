package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.istanza;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.DestinatarioDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ReferentiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.validation.RequiredDependsOn;

/**
 * String message() default "Valori inseriti non validi"; String field(); String
 * dependesOn(); String dependeOnValue() default "";
 * 
 * @author msantoro
 *
 */

@RequiredDependsOn.List(
{ 
	@RequiredDependsOn(field = "inQualitaDi", dependsOn = "nelCaso", dependsOnValue = "true", message = "Il campo 'societa' non può essere vuoto"),
//	@RequiredDependsOn(field = "societa", dependsOn = "nelCaso", dependsOnValue = "true", message = "Il campo 'societa' non può essere vuoto"),
//	@RequiredDependsOn(field = "partitaIva", dependsOn = "nelCaso", dependsOnValue = "true", message = "Il campo 'partitaIva' non può essere vuoto"),
	@RequiredDependsOn(field = "societaCodiceFiscale", dependsOn = "nelCaso", dependsOnValue = "true", message = "Il campo 'societaCodiceFiscale' non può essere vuoto"),
	@RequiredDependsOn(field = "descAltroDitta", dependsOn = "inQualitaDi", dependsOnValue = "3", message = "Campo 'descAltroDitta' obbligatorio"), 
	@RequiredDependsOn(field = "idTipoAzienda", dependsOn = "nelCaso", dependsOnValue = "true", message = "Il campo 'Tipo Azzienda' è onnligatorio")
})
public class RichiedenteDto extends InformazionePersonaleDto
{

	private static final long serialVersionUID = -4423376757887201700L;

	@NotNull
	@Valid
	private IndirizzoCompletoDto residenteIn;
	@NotBlank(message = "Il campo 'Recapito telefonico' non può essere vuoto")
	//@Pattern(regexp = "(^[0][1-9]+)|([1-9]\\d*)", message = "Il campo 'Recapito telefonico' può contenere solamente numeri")
	@Pattern(regexp = "^[0-9 \\+\\-\\.]*$", message = "Il campo 'Recapito telefonico' può contenere solamente numeri")
	private String recapitoTelefonico;
	@NotBlank(message = "Il campo 'Indirizzo email' non può essere vuoto",groups = {RichiedenteInfo.class})
	@Email(message = "Il contenuto del campo 'Indirizzo email' non è valido")
	private String indirizzoEmail;
	@NotBlank(message = "Il campo 'pec' non può essere vuoto")
	@Email(message = "Il contenuto del campo 'pec' non è valido")
	private String pec;
	private Boolean nelCaso;
	@Min(1)
	@Max(4)
	private Integer inQualitaDi;
	private String descAltroDitta;
	private String societa;
	private String partitaIva;
	private String societaCodiceFiscale;
	private String idTipoAzienda;
	private String tipoAzienda;
	private String codiceIpa;
	private String cUo;

	public IndirizzoCompletoDto getResidenteIn()
	{
		return this.residenteIn;
	}

	public void setResidenteIn(final IndirizzoCompletoDto residenteIn)
	{
		this.residenteIn = residenteIn;
	}

	public String getRecapitoTelefonico()
	{
		return this.recapitoTelefonico;
	}

	public void setRecapitoTelefonico(final String recapitoTelefonico)
	{
		this.recapitoTelefonico = recapitoTelefonico;
	}

	public String getIndirizzoEmail()
	{
		return this.indirizzoEmail;
	}

	public void setIndirizzoEmail(final String indirizzoEmail)
	{
		this.indirizzoEmail = indirizzoEmail;
	}

	public String getPec()
	{
		return this.pec;
	}

	public void setPec(final String pec)
	{
		this.pec = pec;
	}

	public Boolean isNelCaso()
	{
		return this.nelCaso;
	}

	public void setNelCaso(final Boolean nelCaso)
	{
		this.nelCaso = nelCaso;
	}

	public Integer getInQualitaDi()
	{
		return this.inQualitaDi;
	}

	public void setInQualitaDi(final Integer inQualitaDi)
	{
		this.inQualitaDi = inQualitaDi;
	}

	public String getDescAltroDitta()
	{
		return this.descAltroDitta;
	}

	public void setDescAltroDitta(final String descAltroDitta)
	{
		this.descAltroDitta = descAltroDitta;
	}

	public String getSocieta()
	{
		return this.societa;
	}

	public void setSocieta(final String societa)
	{
		this.societa = societa;
	}

	public String getPartitaIva()
	{
		return this.partitaIva;
	}

	public void setPartitaIva(final String partitaIva)
	{
		this.partitaIva = partitaIva;
	}

	public String getSocietaCodiceFiscale()
	{
		return this.societaCodiceFiscale;
	}

	public void setSocietaCodiceFiscale(final String societaCodiceFiscale)
	{
		this.societaCodiceFiscale = societaCodiceFiscale;
	}

	@Override
	public void fromEntity(final ReferentiDTO referenteDTO)
	{
		super.fromEntity(referenteDTO);
		this.setResidenteIn(new IndirizzoCompletoDto(referenteDTO.getNazionalitaResidenza(),
				referenteDTO.getNazionalitaResidenzaName(), referenteDTO.getProvinciaResidenza(),
				referenteDTO.getProvinciaResidenzaName(), referenteDTO.getComuneResidenza(),
				referenteDTO.getComuneResidenzaName(), referenteDTO.getComuneResidenzaEstero(),
				referenteDTO.getIndirizzoResidenza(), referenteDTO.getCivicoResidenza(),
				referenteDTO.getCapResidenza()));
		this.setRecapitoTelefonico(referenteDTO.getTelefono());
		this.setIndirizzoEmail(referenteDTO.getMail());
		this.setPec(referenteDTO.getPec());
		this.setNelCaso(referenteDTO.getDitta());
		this.setInQualitaDi(referenteDTO.getDittaQualitaDi());
		this.setSocieta(referenteDTO.getDittaEnte());
		this.setPartitaIva(referenteDTO.getDittaPartitaIva());
		this.setSocietaCodiceFiscale(referenteDTO.getDittaCf());
		this.setDescAltroDitta(referenteDTO.getDittaQualitaAltro());
		this.setCodiceIpa(referenteDTO.getCodiceIpa());
		this.setIdTipoAzienda(referenteDTO.getIdTipoAzienda());
		this.setTipoAzienda(referenteDTO.getTipoAzienda());
		this.setcUo(referenteDTO.getDittaCodiceUO());
	}

	@Override
	public void toEntity(final ReferentiDTO referenteDTO, final String tipoReferente)
	{
		super.toEntity(referenteDTO, tipoReferente);
		if (this.getResidenteIn() != null)
		{
			this.getResidenteIn().toSetter(val -> referenteDTO.setNazionalitaResidenza(val),
					label -> referenteDTO.setNazionalitaResidenzaName(label),
					val -> referenteDTO.setProvinciaResidenza(val),
					label -> referenteDTO.setProvinciaResidenzaName(label), val -> referenteDTO.setComuneResidenza(val),
					label -> referenteDTO.setComuneResidenzaName(label),
					val -> referenteDTO.setComuneResidenzaEstero(val), val -> referenteDTO.setIndirizzoResidenza(val),
					val -> referenteDTO.setCivicoResidenza(val), val -> referenteDTO.setCapResidenza(val));
		}
		referenteDTO.setTelefono(this.recapitoTelefonico);
		referenteDTO.setMail(this.indirizzoEmail);
		referenteDTO.setPec(this.pec);
		referenteDTO.setDitta(this.isNelCaso());
		referenteDTO.setDittaQualitaDi(this.getInQualitaDi());
		referenteDTO.setDittaEnte(this.getSocieta());
		referenteDTO.setDittaPartitaIva(this.getPartitaIva());
		referenteDTO.setDittaCf(this.getSocietaCodiceFiscale());
		referenteDTO.setDittaQualitaAltro(this.getDescAltroDitta());
		referenteDTO.setCodiceIpa(this.getCodiceIpa());
		referenteDTO.setIdTipoAzienda(this.getIdTipoAzienda());
		referenteDTO.setTipoAzienda(this.getTipoAzienda());
		referenteDTO.setDittaCodiceUO(this.cUo);
	}

//	@JsonIgnore
//	public void setDocData(ReferentiDocumentoDTO docReferente) {
//		this.setTipo(docReferente.getIdTipo());
//		this.setNumero(docReferente.getNumero());
//		this.setDataScadenza(docReferente.getDataScadenza());
//		this.setRilascitoIl(docReferente.getDataRilascio());
//		this.setRilascitoDa(docReferente.getEnteRilascio());
//	}
//	
//	@JsonIgnore
//	public ReferentiDocumentoDTO getDocData() {
//		ReferentiDocumentoDTO ret=new ReferentiDocumentoDTO();
//		ret.setIdTipo(this.getTipo());
//		ret.setNumero(this.getNumero());
//		ret.setDataScadenza(this.getDataScadenza());
//		ret.setDataRilascio(this.getRilascitoIl());
//		ret.setEnteRilascio(this.getRilascitoDa());
//		return ret;
//	}

	/**
	 * 
	 * @author acolaianni
	 *
	 * @param tipo TO o CC
	 * @return
	 * @throws Exception
	 */
	public DestinatarioDTO buildDestinatarioPec(final String tipo) throws Exception {
		return new DestinatarioDTO(this.getCognome() + "  " + this.getNome(), this.getPec(), tipo);
	}

	/**
	 * @return the tipoAzienda
	 */
	public String getTipoAzienda() {
		return this.tipoAzienda;
	}

	/**
	 * @param tipoAzienda the tipoAzienda to set
	 */
	public void setTipoAzienda(final String tipoAzienda) {
		this.tipoAzienda = tipoAzienda;
	}

	/**
	 * @return the codiceIpa
	 */
	public String getCodiceIpa() {
		return this.codiceIpa;
	}

	/**
	 * @param codiceIpa the codiceIpa to set
	 */
	public void setCodiceIpa(final String codiceIpa) {
		this.codiceIpa = codiceIpa;
	}

	/**
	 * @return the idTipoAzienda
	 */
	public String getIdTipoAzienda() {
		return this.idTipoAzienda;
	}

	/**
	 * @param idTipoAzienda the idTipoAzienda to set
	 */
	public void setIdTipoAzienda(final String idTipoAzienda) {
		this.idTipoAzienda = idTipoAzienda;
	}

	public String getcUo()
	{
	    return cUo;
	}

	public void setcUo(String cUo)
	{
	    this.cUo = cUo;
	}
	
}
