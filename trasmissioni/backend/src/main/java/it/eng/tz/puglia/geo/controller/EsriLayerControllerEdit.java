package it.eng.tz.puglia.geo.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.eng.tz.puglia.autpae.config.ApplicationProperties;
import it.eng.tz.puglia.autpae.controller.exception.CustomOperationToAdviceException;
import it.eng.tz.puglia.geo.repository.EnumNomeVista;

@RestController
@RequestMapping(value = "/esriEditing/")
public class EsriLayerControllerEdit  extends GenericEsriLayerController {

	private String layerID="0";
	private String layerName;
	private String layerDescription;
	private String layerDescrizioneEstesa;
	private String coloreArea;
	
	@Autowired
	private ApplicationProperties props;
	
	
	@PostConstruct
	public void initLayerInfo() {
		this.layerName=props.getCodiceApplicazione()+"Editing";
		this.layerDescription=props.getLayerEditingDes();
		this.layerDescrizioneEstesa=props.getLayerEditingDesEst();
		this.coloreArea=props.getLayerEditingCol();
	}

	
	@Override
	protected EnumNomeVista getVista() {
		return EnumNomeVista.EDITING;
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