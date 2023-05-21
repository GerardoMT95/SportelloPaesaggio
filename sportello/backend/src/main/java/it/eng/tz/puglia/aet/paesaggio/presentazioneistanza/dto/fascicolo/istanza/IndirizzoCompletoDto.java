package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.istanza;

import java.io.Serializable;
import java.util.function.Consumer;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PlainNumberValueLabel;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.utils.PlainNumberValueLabelDeserializer;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.validation.RequiredDependsOn;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.validation.RequiredDependsOn.Condition;

@RequiredDependsOn.List({
	@RequiredDependsOn(field="provincia", dependsOn="stato", dependsOnSubField="value", dependsOnValue="1", message="Il campo 'provincia' è obbligatorio se settato lo stato Italia"),
	@RequiredDependsOn(field="comune", dependsOn="stato", dependsOnSubField="value", dependsOnValue="1", message="Il campo 'comune' è obbligatorio se settato lo stato Italia"),
	@RequiredDependsOn(field="citta", dependsOn="stato", dependsOnSubField="value", dependsOnValue="1", condition=Condition.notFound, message="In caso 'stato' non sia Italia il campo 'citta' non può essere lasciato vuoto"),
	@RequiredDependsOn(field="via", dependsOn="comune", message="Il campo 'via' è obbligatorio se settato un comune"),
	@RequiredDependsOn(field="n", dependsOn="comune", message="Il campo 'n' è obbligatorio se settato un comune"),
	@RequiredDependsOn(field="cap", dependsOn="comune", message="Il campo 'cap' è obbligatorio se settato un comune"),
})
public class IndirizzoCompletoDto implements Serializable {

	private static final long serialVersionUID = -2175191803185545624L;

	@NotNull(message="Il campo 'stato' non uò essere nullo")
	@JsonDeserialize(using=PlainNumberValueLabelDeserializer.class)
	private PlainNumberValueLabel stato;
	private String citta;// caso estero
	@JsonDeserialize(using=PlainNumberValueLabelDeserializer.class)
	private PlainNumberValueLabel comune;
	@JsonDeserialize(using=PlainNumberValueLabelDeserializer.class)
	private PlainNumberValueLabel provincia;
	private String via;
	private String n;
	private String cap;

	public PlainNumberValueLabel getStato() {
		return stato;
	}

	public void setStato(PlainNumberValueLabel stato) {
		this.stato = stato;
	}

	public String getCitta() {
		return citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}

	public PlainNumberValueLabel getComune() {
		return comune;
	}

	public void setComune(PlainNumberValueLabel comune) {
		this.comune = comune;
	}

	public PlainNumberValueLabel getProvincia() {
		return provincia;
	}

	public void setProvincia(PlainNumberValueLabel provincia) {
		this.provincia = provincia;
	}

	public String getVia() {
		return via;
	}

	public void setVia(String via) {
		this.via = via;
	}

	public String getN() {
		return n;
	}

	public void setN(String n) {
		this.n = n;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public IndirizzoCompletoDto() {
	}

	public IndirizzoCompletoDto(Integer statoId,String stato, Integer provinciaId, String provincia,
			Integer comuneId,String comune,
			 String citta, String via, String n, String cap) {
		this.setStato(PlainNumberValueLabel.toGetter(statoId, stato));
		this.setComune(PlainNumberValueLabel.toGetter(comuneId, comune));
		this.setProvincia(PlainNumberValueLabel.toGetter(provinciaId, provincia));
		this.setVia(via);this.setCap(cap);this.setN(n);this.setCitta(citta);
	}
	
	public void toSetter(Consumer<Integer> statoId,
						 Consumer<String> stato, 
						 Consumer<Integer> provinciaId, 
						 Consumer<String> provincia,
						Consumer<Integer> comuneId,
						Consumer<String> comune,
						Consumer<String> citta, Consumer<String> via, Consumer<String> n, Consumer<String> cap) {
		PlainNumberValueLabel.toSetter(this.getStato(), statoId, stato);
		PlainNumberValueLabel.toSetter(this.getComune(), comuneId, comune);
		PlainNumberValueLabel.toSetter(this.getProvincia(), provinciaId, provincia);
		citta.accept(this.getCitta());
		via.accept(this.getVia());
		n.accept(this.getN());
		cap.accept(this.getCap());
	}

}
