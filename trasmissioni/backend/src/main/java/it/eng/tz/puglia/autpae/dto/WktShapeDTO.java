/**
 * 
 */
package it.eng.tz.puglia.autpae.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;

/**
 * @author Adriano Colaianni
 * @date 11 mag 2021
 */
@ApiModel(description = "oggetto per inserire la geometria in mappa legata ad un fascicolo")
public class WktShapeDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotNull
	@ApiParam(required = true,value = "id del fascicolo")
	private Long idFascicolo;
	
	@NotBlank
	@ApiParam(required = true,value = "geometria in formato Well Know Text, supportato POLYGON e MULTIPOLYGON con terza dimensione fissa a 0 es: MULTIPOLYGON(((654501.3766687501 4553731.232469026 0,654506.4037621376 4553708.213672988 0,654518.3100359502 4553721.707449975 0,654512.2246071126 4553730.1741335755 0,654501.3766687501 4553731.232469026 0))) ")
	private String wkt;

	public Long getIdFascicolo() {
		return idFascicolo;
	}

	public void setIdFascicolo(Long idFascicolo) {
		this.idFascicolo = idFascicolo;
	}

	public String getWkt() {
		return wkt;
	}

	public void setWkt(String wkt) {
		this.wkt = wkt;
	}
	

}
