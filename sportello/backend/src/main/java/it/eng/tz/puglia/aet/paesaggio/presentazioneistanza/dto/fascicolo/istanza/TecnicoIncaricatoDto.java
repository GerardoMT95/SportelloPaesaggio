package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.istanza;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.DestinatarioDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ReferentiDTO;

public class TecnicoIncaricatoDto extends InformazionePersonaleDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3463898595201965419L;
	private IndirizzoCompletoDto residenteIn;
	private IndirizzoCompletoDto conStudioIn;
	private String iscritoAllOrdine;
	private String di;
	private String n;
	private String recapitoTelefonico;
	private String fax;
	private String cellulare;
	private String pec;

	public IndirizzoCompletoDto getResidenteIn() {
		return residenteIn;
	}
	public void setResidenteIn(IndirizzoCompletoDto residenteIn) {
		this.residenteIn = residenteIn;
	}
	public IndirizzoCompletoDto getConStudioIn() {
		return conStudioIn;
	}
	public void setConStudioIn(IndirizzoCompletoDto conStudioIn) {
		this.conStudioIn = conStudioIn;
	}
	public String getIscritoAllOrdine() {
		return iscritoAllOrdine;
	}
	public void setIscritoAllOrdine(String iscritoAllOrdine) {
		this.iscritoAllOrdine = iscritoAllOrdine;
	}
	public String getDi() {
		return di;
	}
	public void setDi(String di) {
		this.di = di;
	}
	public String getN() {
		return n;
	}
	public void setN(String n) {
		this.n = n;
	}
	public String getRecapitoTelefonico() {
		return recapitoTelefonico;
	}
	public void setRecapitoTelefonico(String recapitoTelefonico) {
		this.recapitoTelefonico = recapitoTelefonico;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getCellulare() {
		return cellulare;
	}
	public void setCellulare(String cellulare) {
		this.cellulare = cellulare;
	}
	public String getPec() {
		return pec;
	}
	public void setPec(String pec) {
		this.pec = pec;
	}

	public void fromEntity(ReferentiDTO referenteDTO){
		super.fromEntity(referenteDTO);
		this.setResidenteIn(new IndirizzoCompletoDto(
				referenteDTO.getNazionalitaResidenza(),
				referenteDTO.getNazionalitaResidenzaName(),
				referenteDTO.getProvinciaResidenza(),referenteDTO.getProvinciaResidenzaName(),
				referenteDTO.getComuneResidenza(),referenteDTO.getComuneResidenzaName(),
				referenteDTO.getComuneResidenzaEstero(),
				referenteDTO.getIndirizzoResidenza(),
				referenteDTO.getCivicoResidenza(),
				referenteDTO.getCapResidenza()
				));
		this.setConStudioIn(new IndirizzoCompletoDto(
				referenteDTO.getTecnicoStudioNazionalita(),
				referenteDTO.getTecnicoStudioNazionalitaName(),
				referenteDTO.getTecnicoStudioProvincia(),referenteDTO.getTecnicoStudioProvinciaName(),
				referenteDTO.getTecnicoStudioComune(),referenteDTO.getTecnicoStudioComuneName(),
				referenteDTO.getTecnicoStudioComuneEstero(),
				referenteDTO.getTecnicoStudioIndirizzo(),
				referenteDTO.getTecnicoStudioCivico(),
				referenteDTO.getTecnicoStudioCap()
				));
		this.setIscritoAllOrdine(referenteDTO.getTecnicoOrdCollegio());
		this.setDi(referenteDTO.getTecnicoCollegioSede());
		this.setN(referenteDTO.getTecnicoCollegioNIscr());
		this.setRecapitoTelefonico(referenteDTO.getTelefono());
		this.setFax(referenteDTO.getFax());
		this.setPec(referenteDTO.getPec());
		this.setCellulare(referenteDTO.getCellulare());
	}
	
	public void toEntity(ReferentiDTO referenteDTO,String tipoReferente) {
		super.toEntity(referenteDTO,tipoReferente);
		if(this.getResidenteIn()!=null) {
			this.getResidenteIn().toSetter(
					val->referenteDTO.setNazionalitaResidenza(val),label->referenteDTO.setNazionalitaResidenzaName(label), 
					val->referenteDTO.setProvinciaResidenza(val),label->referenteDTO.setProvinciaResidenzaName(label), 
					val->referenteDTO.setComuneResidenza(val),label->referenteDTO.setComuneResidenzaName(label), 
					val->referenteDTO.setComuneResidenzaEstero(val), 
					val->referenteDTO.setIndirizzoResidenza(val),
					val->referenteDTO.setCivicoResidenza(val),
					val->referenteDTO.setCapResidenza(val));
		}
		if(this.getConStudioIn()!=null) {
			this.getConStudioIn().toSetter(
					val->referenteDTO.setTecnicoStudioNazionalita(val),label->referenteDTO.setTecnicoStudioNazionalitaName(label), 
					val->referenteDTO.setTecnicoStudioProvincia(val),label->referenteDTO.setTecnicoStudioProvinciaName(label), 
					val->referenteDTO.setTecnicoStudioComune(val),label->referenteDTO.setTecnicoStudioComuneName(label), 
					val->referenteDTO.setTecnicoStudioComuneEstero(val), 
					val->referenteDTO.setTecnicoStudioIndirizzo(val),
					val->referenteDTO.setTecnicoStudioCivico(val),
					val->referenteDTO.setTecnicoStudioCap(val));
		}
		referenteDTO.setTecnicoOrdCollegio(this.getIscritoAllOrdine());
		referenteDTO.setTecnicoCollegioSede(this.getDi());
		referenteDTO.setTecnicoCollegioNIscr(this.getN());
		referenteDTO.setTelefono(this.getRecapitoTelefonico());
		referenteDTO.setFax(this.getFax());
		referenteDTO.setPec(this.getPec());
		referenteDTO.setCellulare(this.getCellulare());
	}
	
	/**
	 * 
	 * @author acolaianni
	 *
	 * @param tipo TO o CC
	 * @return
	 * @throws Exception
	 */
	public DestinatarioDTO buildDestinatarioPec(String tipo) throws Exception {
		return new DestinatarioDTO(this.getCognome() + "  " + this.getNome(), this.getPec(), tipo);
	}
}
