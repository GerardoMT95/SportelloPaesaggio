package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto;

import java.io.Serializable;
import java.sql.*;
import java.math.*;
import java.util.*;
import java.util.Date;
import it.eng.tz.puglia.util.json.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * DTO for table presentazione_istanza.protocollo
 */
public class ProtocolloDTO implements Serializable{

    private static final long serialVersionUID = 2072899156L;
    //COLUMN id_protocollo
    private UUID idProtocollo;
    //COLUMN codiceAmministrazione
    private String codiceamministrazione;
    //COLUMN codiceAOO
    private String codiceaoo;
    //COLUMN codiceRegistro
    private String codiceregistro;
    //COLUMN dataRegistrazione
    private String dataregistrazione;
    //COLUMN numeroProtocollo
    private String numeroprotocollo;
    //COLUMN hostProxy
    private String hostproxy;
    //COLUMN portProxy
    private String portproxy;
    //COLUMN usernameProxy
    private String usernameproxy;
    //COLUMN passwordProxy
    private String passwordproxy;
    //COLUMN urlNotProxy
    private String urlnotproxy;
    //COLUMN clientProtocolloEndpoint
    private String clientprotocolloendpoint;
    //COLUMN clientProtocolloAdministration
    private String clientprotocolloadministration;
    //COLUMN clientProtocolloAOO
    private String clientprotocolloaoo;
    //COLUMN clientProtocolloRegister
    private String clientprotocolloregister;
    //COLUMN clientProtocolloUser
    private String clientprotocollouser;
    //COLUMN clientProtocolloPassword
    private String clientprotocollopassword;
    //COLUMN clientProtocolloHashAlgorihtm
    private String clientprotocollohashalgorihtm;
    //COLUMN protocolObject
    private String protocolobject;
    //COLUMN request
    private String request;
    //COLUMN response
    private String response;
    //COLUMN protocollo
    private String protocollo;
    //COLUMN verso
    private String verso;
    //COLUMN data_esecuzione
    private Timestamp dataEsecuzione;
    //COLUMN id_allegato
    private UUID idAllegato;
    //COLUMN id_pratica
    private UUID idPratica;

    
    public UUID getIdPratica() {
		return idPratica;
	}

	public void setIdPratica(UUID idPratica) {
		this.idPratica = idPratica;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getProtocollo() {
		return protocollo;
	}

	public void setProtocollo(String protocollo) {
		this.protocollo = protocollo;
	}

	public String getVerso() {
		return verso;
	}

	public void setVerso(String verso) {
		this.verso = verso;
	}

	
	public Timestamp getDataEsecuzione() {
		return dataEsecuzione;
	}

	public void setDataEsecuzione(Timestamp dataEsecuzione) {
		this.dataEsecuzione = dataEsecuzione;
	}

	public UUID getIdAllegato() {
		return idAllegato;
	}

	public void setIdAllegato(UUID idAllegato) {
		this.idAllegato = idAllegato;
	}

	public UUID getIdProtocollo(){
        return this.idProtocollo;
    }

    public void setIdProtocollo(UUID idProtocollo){
        this.idProtocollo = idProtocollo;
    }

    public String getCodiceamministrazione(){
        return this.codiceamministrazione;
    }

    public void setCodiceamministrazione(String codiceamministrazione){
        this.codiceamministrazione = codiceamministrazione;
    }

    public String getCodiceaoo(){
        return this.codiceaoo;
    }

    public void setCodiceaoo(String codiceaoo){
        this.codiceaoo = codiceaoo;
    }

    public String getCodiceregistro(){
        return this.codiceregistro;
    }

    public void setCodiceregistro(String codiceregistro){
        this.codiceregistro = codiceregistro;
    }

    public String getDataregistrazione(){
        return this.dataregistrazione;
    }

    public void setDataregistrazione(String dataregistrazione){
        this.dataregistrazione = dataregistrazione;
    }

    public String getNumeroprotocollo(){
        return this.numeroprotocollo;
    }

    public void setNumeroprotocollo(String numeroprotocollo){
        this.numeroprotocollo = numeroprotocollo;
    }

    public String getHostproxy(){
        return this.hostproxy;
    }

    public void setHostproxy(String hostproxy){
        this.hostproxy = hostproxy;
    }

    public String getPortproxy(){
        return this.portproxy;
    }

    public void setPortproxy(String portproxy){
        this.portproxy = portproxy;
    }

    public String getUsernameproxy(){
        return this.usernameproxy;
    }

    public void setUsernameproxy(String usernameproxy){
        this.usernameproxy = usernameproxy;
    }

    public String getPasswordproxy(){
        return this.passwordproxy;
    }

    public void setPasswordproxy(String passwordproxy){
        this.passwordproxy = passwordproxy;
    }

    public String getUrlnotproxy(){
        return this.urlnotproxy;
    }

    public void setUrlnotproxy(String urlnotproxy){
        this.urlnotproxy = urlnotproxy;
    }

    public String getClientprotocolloendpoint(){
        return this.clientprotocolloendpoint;
    }

    public void setClientprotocolloendpoint(String clientprotocolloendpoint){
        this.clientprotocolloendpoint = clientprotocolloendpoint;
    }

    public String getClientprotocolloadministration(){
        return this.clientprotocolloadministration;
    }

    public void setClientprotocolloadministration(String clientprotocolloadministration){
        this.clientprotocolloadministration = clientprotocolloadministration;
    }

    public String getClientprotocolloaoo(){
        return this.clientprotocolloaoo;
    }

    public void setClientprotocolloaoo(String clientprotocolloaoo){
        this.clientprotocolloaoo = clientprotocolloaoo;
    }

    public String getClientprotocolloregister(){
        return this.clientprotocolloregister;
    }

    public void setClientprotocolloregister(String clientprotocolloregister){
        this.clientprotocolloregister = clientprotocolloregister;
    }

    public String getClientprotocollouser(){
        return this.clientprotocollouser;
    }

    public void setClientprotocollouser(String clientprotocollouser){
        this.clientprotocollouser = clientprotocollouser;
    }

    public String getClientprotocollopassword(){
        return this.clientprotocollopassword;
    }

    public void setClientprotocollopassword(String clientprotocollopassword){
        this.clientprotocollopassword = clientprotocollopassword;
    }

    public String getClientprotocollohashalgorihtm(){
        return this.clientprotocollohashalgorihtm;
    }

    public void setClientprotocollohashalgorihtm(String clientprotocollohashalgorihtm){
        this.clientprotocollohashalgorihtm = clientprotocollohashalgorihtm;
    }

    public String getProtocolobject(){
        return this.protocolobject;
    }

    public void setProtocolobject(String protocolobject){
        this.protocolobject = protocolobject;
    }


}
