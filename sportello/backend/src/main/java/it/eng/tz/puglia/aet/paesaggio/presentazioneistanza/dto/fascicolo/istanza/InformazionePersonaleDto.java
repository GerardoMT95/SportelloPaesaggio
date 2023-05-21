package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.istanza;

import java.io.Serializable;
import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PlainNumberValueLabel;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ReferentiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ReferentiDocumentoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.utils.PlainNumberValueLabelDeserializer;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.validation.RequiredDependsOn;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.validation.RequiredDependsOn.Condition;
import it.eng.tz.puglia.util.json.DateSerializer;
import it.eng.tz.puglia.util.json.DateSqlDeserializer;

@RequiredDependsOn.List({
	@RequiredDependsOn(field="natoProvincia", dependsOn="natoStato", dependsOnSubField="value", dependsOnValue="1", message="Il campo 'provincia' è obbligatorio se settato lo stato Italia"),
	@RequiredDependsOn(field="natoComune", dependsOn="natoStato", dependsOnSubField="value", dependsOnValue="1", message="Il campo 'comune' è obbligatorio se settato lo stato Italia"),
	@RequiredDependsOn(field="natoCitta", dependsOn="natoStato", dependsOnSubField="value", dependsOnValue="1", condition=Condition.notFound, message="In caso 'stato' non sia Italia il campo 'citta' non può essere lasciato vuoto")
})
public class InformazionePersonaleDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1838294636137796086L;
	
	@NotNull
	private Long id;
	@NotBlank(message="Il campo 'cognome' non può essere vuoto")
	private String cognome;
	@NotBlank(message="Il campo 'nome' non può essere vuoto")
	private String nome;
	@NotBlank(message="Il campo 'codice fiscale' non può essere vuoto")
	@Pattern(regexp="[a-zA-Z]{6}[0-9]{2}[a-zA-Z][0-9]{2}[a-zA-Z][0-9]{3}[a-zA-Z]", message="codice fiscale non valido")
	private String codiceFiscale;
	@NotBlank(message="Il campo 'sesso' non può essere vuoto")
	private String sesso;

	@NotNull(message="Il campo 'natoIl' non può essere nullo")
	@JsonDeserialize(using = DateSqlDeserializer.class)
	@JsonSerialize(using = DateSerializer.class)
	private Date natoIl;
	@NotNull(message="Il campo 'natoStato' non può essere nullo")
	@Valid
	@JsonDeserialize(using=PlainNumberValueLabelDeserializer.class)
	private PlainNumberValueLabel natoStato;
	private String natoCitta; //caso estero
	@JsonDeserialize(using=PlainNumberValueLabelDeserializer.class)
	private PlainNumberValueLabel natoComune;
	@JsonDeserialize(using=PlainNumberValueLabelDeserializer.class)
	private PlainNumberValueLabel natoProvincia;
	
	@Valid
	private ReferentiDocumentoDTO documento;
	
	
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale != null ? codiceFiscale.toUpperCase(): codiceFiscale;
	}
	public String getSesso() {
		return sesso;
	}
	public void setSesso(String sesso) {
		this.sesso = sesso;
	}
	public Date getNatoIl() {
		return natoIl;
	}
	public void setNatoIl(Date natoIl) {
		this.natoIl = natoIl;
	}
	
	public PlainNumberValueLabel getNatoStato()
	{
		return natoStato;
	}
	public void setNatoStato(PlainNumberValueLabel natoStato)
	{
		this.natoStato = natoStato;
	}
	
	public String getNatoCitta()
	{
		return natoCitta;
	}
	public void setNatoCitta(String natoCitta)
	{
		this.natoCitta = natoCitta;
	}
	
	public PlainNumberValueLabel getNatoComune()
	{
		return natoComune;
	}
	public void setNatoComune(PlainNumberValueLabel natoComune)
	{
		this.natoComune = natoComune;
	}
	
	public PlainNumberValueLabel getNatoProvincia()
	{
		return natoProvincia;
	}
	public void setNatoProvincia(PlainNumberValueLabel natoProvincia)
	{
		this.natoProvincia = natoProvincia;
	}
	
//	public Integer getTipo() {
//		return tipo;
//	}
//	public void setTipo(Integer tipo) {
//		this.tipo = tipo;
//	}
//	public String getNumero() {
//		return numero;
//	}
//	public void setNumero(String numero) {
//		this.numero = numero;
//	}
//	public Date getRilascitoIl() {
//		return rilascitoIl;
//	}
//	public void setRilascitoIl(Date rilascitoIl) {
//		this.rilascitoIl = rilascitoIl;
//	}
//	public String getRilascitoDa() {
//		return rilascitoDa;
//	}
//	public void setRilascitoDa(String rilascitoDa) {
//		this.rilascitoDa = rilascitoDa;
//	}
//	public Date getDataScadenza() {
//		return dataScadenza;
//	}
//	public void setDataScadenza(Date dataScadenza) {
//		this.dataScadenza = dataScadenza;
//	}
    
	public ReferentiDocumentoDTO getDocumento()
	{
		return documento;
	}
	public void setDocumento(ReferentiDocumentoDTO documento)
	{
		this.documento = documento;
	}
	public void fromEntity(ReferentiDTO referenteDTO) {
		this.setNome(referenteDTO.getNome());
		this.setCognome(referenteDTO.getCognome());
		this.setCodiceFiscale(referenteDTO.getCodiceFiscale());
		this.setSesso(referenteDTO.getSesso());
		this.setNatoIl(referenteDTO.getDataNascita());
		this.setNatoCitta(referenteDTO.getComuneNascitaEstero());
		this.setNatoStato(PlainNumberValueLabel.toGetter(referenteDTO.getNazionalitaNascita(),referenteDTO.getNazionalitaNascitaName()));
		this.setNatoComune(PlainNumberValueLabel.toGetter(referenteDTO.getComuneNascita(),referenteDTO.getComuneNascitaName()));
		this.setNatoProvincia(PlainNumberValueLabel.toGetter(referenteDTO.getProvinciaNascita(),referenteDTO.getProvinciaNascitaName()));
		this.setId(referenteDTO.getId());	
	}
	
	public void toEntity(ReferentiDTO referenteDTO,String tipoReferente) {
		referenteDTO.setNome(this.getNome());
		referenteDTO.setCognome(this.getCognome());
		referenteDTO.setCodiceFiscale(this.getCodiceFiscale());
		referenteDTO.setSesso(this.getSesso()) ;
		referenteDTO.setDataNascita(this.getNatoIl());
		PlainNumberValueLabel.toSetter(this.getNatoStato(),val->referenteDTO.setNazionalitaNascita(val),label->referenteDTO.setNazionalitaNascitaName(label));
		PlainNumberValueLabel.toSetter(this.getNatoComune(),val->referenteDTO.setComuneNascita(val),label->referenteDTO.setComuneNascitaName(label));
		PlainNumberValueLabel.toSetter(this.getNatoProvincia(),val->referenteDTO.setProvinciaNascita(val),label->referenteDTO.setProvinciaNascitaName(label));
		referenteDTO.setComuneNascitaEstero(this.getNatoCitta());
		if(this.getId()!=null) {
			referenteDTO.setId(this.getId());	
		}
		referenteDTO.setTipoReferente(tipoReferente);
	}
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	
//	@JsonIgnore
//	public void fromEntityDoc(ReferentiDocumentoDTO docReferente) {
//		this.setTipo(docReferente.getIdTipo());
//		this.setNumero(docReferente.getNumero());
//		this.setDataScadenza(docReferente.getDataScadenza());
//		this.setRilascitoIl(docReferente.getDataRilascio());
//		this.setRilascitoDa(docReferente.getEnteRilascio());
//	}
	
//	@JsonIgnore
//	public void toEntityDocData(ReferentiDocumentoDTO ret) {
//		ret.setReferenteId(this.getId());
//		ret.setIdTipo(this.getTipo());
//		ret.setNumero(this.getNumero());
//		ret.setDataScadenza(this.getDataScadenza());
//		ret.setDataRilascio(this.getRilascitoIl());
//		ret.setEnteRilascio(this.getRilascitoDa());
//	}
}
