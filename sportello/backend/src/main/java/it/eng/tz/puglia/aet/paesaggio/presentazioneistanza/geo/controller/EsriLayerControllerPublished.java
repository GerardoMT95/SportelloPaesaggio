package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.geo.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.ApplicationProperties;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.geo.repository.EnumNomeVista;
import it.eng.tz.puglia.error.exception.CustomOperationException;


@RestController
@RequestMapping(value = "/public/esriPublished")
public class EsriLayerControllerPublished extends GenericEsriLayerController {
	
	private String layerID="0";
	private String layerName;
	private String layerDescription;
	private String layerDescrizioneEstesa;
	private String coloreArea;
	
	@Autowired
	private ApplicationProperties props;
	
	
	@PostConstruct
	public void initLayerInfo() {
		this.layerName="PresentazioneIstanza"+"Published";
		this.layerDescription=props.getLayerPublishedDes();
		this.layerDescrizioneEstesa=props.getLayerPublishedDesEst();
		this.coloreArea=props.getLayerPublishedCol();
	}


	@Override
	protected void canEdit() throws CustomOperationException {
		throw new CustomOperationException("Editing non disponibile!");
	}

	@Override
	protected EnumNomeVista getVista() {
		return EnumNomeVista.PUBLISHED;
	}

	@Override
	protected String getLayerId() {
		return this.layerID;
	}

	@Override
	protected String getLayerName() {
		return this.layerName;
	}

	@Override
	protected String getLayerDescription() {
		return this.layerDescription;
	}

	@Override
	protected String getLayerDescriptionEstesa() {
		return this.layerDescrizioneEstesa;
	}

	@Override
	protected String getColoreArea() {
		return this.coloreArea;
	}

}