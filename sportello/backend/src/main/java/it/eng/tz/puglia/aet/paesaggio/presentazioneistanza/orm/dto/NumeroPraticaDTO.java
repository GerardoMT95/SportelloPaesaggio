package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto;

import java.io.Serializable;
import java.sql.*;
import java.math.*;
import java.util.*;
import it.eng.tz.puglia.util.json.*;

/**
 * DTO for table presentazione_istanza.numero_pratica
 */
public class NumeroPraticaDTO implements Serializable{

    private static final long serialVersionUID = 8893679718L;
    //COLUMN id
    private int id;
    //COLUMN anno
    private Long anno;
    //COLUMN numero
    private Long numero;
    
    private Long nextNumero;
    private String numeroPratica;
    
    public Long getNextNumero() {
		return nextNumero;
	}

	public void setNextNumero(Long nextNumero) {
		this.nextNumero = nextNumero;
	}

	public String getNumeroPratica() {
		return numeroPratica;
	}

	public void setNumeroPratica(String numeroPratica) {
		this.numeroPratica = numeroPratica;
	}

	

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public Long getAnno(){
        return this.anno;
    }

    public void setAnno(Long anno){
        this.anno = anno;
    }

    public Long getNumero(){
        return this.numero;
    }

    public void setNumero(Long numero){
        this.numero = numero;
    }


}
