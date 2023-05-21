package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto;

import java.io.Serializable;



/**
 * Cambio ownership response bean
 * @author Antonio La Gatta
 * @date 27 lug 2021
 */
public class CambioOwnershipResponseBean implements Serializable{

	/**
	 * @author Antonio La Gatta
	 * @date 27 lug 2021
	 */
	private static final long serialVersionUID = 1065709405416064452L;

	private boolean esito;
	private boolean sollevamentoIncarico;
	private SubentroDto subentro;
	/**
	 * @return the esito
	 */
	public boolean isEsito() {
		return this.esito;
	}
	/**
	 * @param esito the esito to set
	 */
	public void setEsito(final boolean esito) {
		this.esito = esito;
	}
	/**
	 * @return the sollevamentoIncarico
	 */
	public boolean isSollevamentoIncarico() {
		return this.sollevamentoIncarico;
	}
	/**
	 * @param sollevamentoIncarico the sollevamentoIncarico to set
	 */
	public void setSollevamentoIncarico(final boolean sollevamentoIncarico) {
		this.sollevamentoIncarico = sollevamentoIncarico;
	}
	/**
	 * @return the subentro
	 */
	public SubentroDto getSubentro() {
		return this.subentro;
	}
	/**
	 * @param subentro the subentro to set
	 */
	public void setSubentro(final SubentroDto subentro) {
		this.subentro = subentro;
	}
}
