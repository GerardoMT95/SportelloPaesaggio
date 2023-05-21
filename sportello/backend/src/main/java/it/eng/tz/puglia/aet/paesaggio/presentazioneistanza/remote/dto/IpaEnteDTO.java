package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto;

import java.io.Serializable;

public class IpaEnteDTO implements Serializable
{
    private static final long serialVersionUID = -3943849552836061490L;
    
    private String codiceUo;
    private String nome;
    private String codiceFiscale;
    private String codiceIpa;
    
    public String getCodiceUo()
    {
        return codiceUo;
    }
    public void setCodiceUo(String codiceUo)
    {
        this.codiceUo = codiceUo;
    }
    
    public String getNome()
    {
        return nome;
    }
    public void setNome(String nome)
    {
        this.nome = nome;
    }
    
    public String getCodiceFiscale()
    {
        return codiceFiscale;
    }
    public void setCodiceFiscale(String codiceFiscale)
    {
        this.codiceFiscale = codiceFiscale;
    }
    
    public String getCodiceIpa()
    {
        return codiceIpa;
    }
    public void setCodiceIpa(String codiceIpa)
    {
        this.codiceIpa = codiceIpa;
    }
    
}
