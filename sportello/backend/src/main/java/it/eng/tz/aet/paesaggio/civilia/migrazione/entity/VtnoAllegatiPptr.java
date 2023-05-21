/**
 * 
 */
package it.eng.tz.aet.paesaggio.civilia.migrazione.entity;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.eng.tz.puglia.util.string.StringUtil;

/**
 * VISTA VTON_ALLEGATI_PPTR
 * @author Adriano Colaianni
 * @date 22 apr 2021
 */
public class VtnoAllegatiPptr {
	
	final static String NOFILEINALFRESCO="NoFileInAlfresco";

	
	//@Column(name="T_PRATICA_ID")
	private long tPraticaId;
	
	//@Column(name="CODICE")
	private String codice;
	
	//@Column(name="DESCRIZIONE")
	private String descrizione;
	
	//@Column(name="DATAARRIVO")
	private Date dataArrivo;

	//@Column(name="NOME")
	private String nome;
		
	//@Column(name="NOMEFILE") nome con prefisso: F_1263068_dichiarazione_tecnica.pdf
	private String nomeFile;	
	
	//@Column(name="TIPO")
	private String tipo;
	
	//@Column(name="T_TIPODOC_CODICE")
	private String tTipoCodice;
	
	//@Column(name="T_TIPODOC_DESCRIZIONE")
	private String tTipodocDescrizione;
	
	//@Column(name="T_TIPODOC_MODCOMPILAZIONE")
	private String tTipodocModcompilazione;
	
	//@Column(name="T_TIPODOC_ID")
	private  Long tTipoDocId; 
	
	//@Column(name="NAME")  nome del file es. dichiarazione_tecnica.pdf
	private String name;
	
	//@Column(name="BIN_CONTENT")
	Blob binContent;
	
	//@Column(name="VERSION_NOTES")
	private String versionNotes;
	
	//@Column(name="T_KE_DOC_ATTR_NAME")
	private String tKeDocAttrName; 
	
	//@Column(name="T_KE_DOC_ATTR_VALUE") //application/pdf  image/jpeg 
	private String tKeDocAttrValue; 
	
	//@Column(name="N_T_KE_DOC_ST_ID")
	private String nTKeDocStId;
	
	//@Column(name="NUMEROPROTOCOLLO")
	private String numeroProtocollo;
	
	//@Column(name="DATAPROTOCOLLO")
	//@javax.persistence.Temporal(TemporalType.TIMESTAMP)
	private Date dataProtocollo;
	
	//@Column(name="PPTR_TIPOALLEGATO_codice")
	private String pptrTipoAllegatoCodice;
	
	//@Column(name="PPTR_TIPOALLEGATO_DESCRIZIONE")  //Parere Mibac -  Provvedimento finale
	private String pptrTipoAllegatoDescrizione;
	
	//@Column(name="FASE_ID")
	private String faseId;
	
	//@Column(name="PPTR_TIPOALLEGATO_ORDINE")
	private String pptrTipoAllegatoOrdine;
	
	
	//@Lob
	//@Column(name="nomeAllegato") // es. Ubicazione dell'opera - Relazione generale è il nome dato all'allegato quando è multitipo 
	private String nomeAllegato;

	//@Column(name="PROG")
	private long prog;
	
	//@Column(name="PROVV")
	private String provv;
	
	//@Column(name="ID_ALFRESCO")
	// contiene il riferimento di alfresco del file  
	private String idAlfresco;

	public long gettPraticaId() {
		return tPraticaId;
	}

	public void settPraticaId(long tPraticaId) {
		this.tPraticaId = tPraticaId;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Date getDataArrivo() {
		return dataArrivo;
	}

	public void setDataArrivo(Date dataArrivo) {
		this.dataArrivo = dataArrivo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String gettTipoCodice() {
		return tTipoCodice;
	}

	public void settTipoCodice(String tTipoCodice) {
		this.tTipoCodice = tTipoCodice;
	}

	public String gettTipodocDescrizione() {
		return tTipodocDescrizione;
	}

	public void settTipodocDescrizione(String tTipodocDescrizione) {
		this.tTipodocDescrizione = tTipodocDescrizione;
	}

	public String gettTipodocModcompilazione() {
		return tTipodocModcompilazione;
	}

	public void settTipodocModcompilazione(String tTipodocModcompilazione) {
		this.tTipodocModcompilazione = tTipodocModcompilazione;
	}

	public Long gettTipoDocId() {
		return tTipoDocId;
	}

	public void settTipoDocId(Long tTipoDocId) {
		this.tTipoDocId = tTipoDocId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Blob getBinContent() {
		return binContent;
	}

	public void setBinContent(Blob binContent) {
		this.binContent = binContent;
	}

	public String getVersionNotes() {
		return versionNotes;
	}

	public void setVersionNotes(String versionNotes) {
		this.versionNotes = versionNotes;
	}

	public String gettKeDocAttrName() {
		return tKeDocAttrName;
	}

	public void settKeDocAttrName(String tKeDocAttrName) {
		this.tKeDocAttrName = tKeDocAttrName;
	}

	public String gettKeDocAttrValue() {
		return tKeDocAttrValue;
	}

	public void settKeDocAttrValue(String tKeDocAttrValue) {
		this.tKeDocAttrValue = tKeDocAttrValue;
	}

	public String getnTKeDocStId() {
		return nTKeDocStId;
	}

	public void setnTKeDocStId(String nTKeDocStId) {
		this.nTKeDocStId = nTKeDocStId;
	}

	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}

	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}

	public Date getDataProtocollo() {
		return dataProtocollo;
	}

	public void setDataProtocollo(Date dataProtocollo) {
		this.dataProtocollo = dataProtocollo;
	}

	public String getPptrTipoAllegatoCodice() {
		return pptrTipoAllegatoCodice;
	}

	public void setPptrTipoAllegatoCodice(String pptrTipoAllegatoCodice) {
		this.pptrTipoAllegatoCodice = pptrTipoAllegatoCodice;
	}

	public String getPptrTipoAllegatoDescrizione() {
		return pptrTipoAllegatoDescrizione;
	}

	public void setPptrTipoAllegatoDescrizione(String pptrTipoAllegatoDescrizione) {
		this.pptrTipoAllegatoDescrizione = pptrTipoAllegatoDescrizione;
	}

	public String getFaseId() {
		return faseId;
	}

	public void setFaseId(String faseId) {
		this.faseId = faseId;
	}

	public String getPptrTipoAllegatoOrdine() {
		return pptrTipoAllegatoOrdine;
	}

	public void setPptrTipoAllegatoOrdine(String pptrTipoAllegatoOrdine) {
		this.pptrTipoAllegatoOrdine = pptrTipoAllegatoOrdine;
	}

	public String getNomeAllegato() {
		return nomeAllegato;
	}

	public void setNomeAllegato(String nomeAllegato) {
		this.nomeAllegato = nomeAllegato;
	}

	public long getProg() {
		return prog;
	}

	public void setProg(long prog) {
		this.prog = prog;
	}

	public String getProvv() {
		return provv;
	}

	public void setProvv(String provv) {
		this.provv = provv;
	}

	public String getIdAlfresco() {
		return idAlfresco;
	}

	public void setIdAlfresco(String idAlfresco) {
		this.idAlfresco = idAlfresco;
	}

	/**
	 * getMapAllegatiFacoltativi_byKey_tnTKeDocStId
	 * Questo metodo raggruppa la lista listaVtnoAllegatiPutp in un map . 
	 * <br> Questo permette di avere per ogni distinto valore tnTKeDocStId (unico file) come map Key , la lista dei VtnoAllegatiPutp 
	 * <br>che contengono diversi valori del tipo allegato come map value
	 * @param listaVtnoAllegatiPutp the lista vtno allegati putp
	 * @return the map allegati facoltativi
	 */
	public static Map<String , List<VtnoAllegatiPptr>> getMapAllegatiFacoltativi_byKey_tnTKeDocStId( List<VtnoAllegatiPptr> listaVtnoAllegatiPptr )
	{
		Map<String , List<VtnoAllegatiPptr>> mapVtnoAllegatiPutp = new HashMap<String , List<VtnoAllegatiPptr>>();
		
		try
		{
			for( VtnoAllegatiPptr vtnoAllegatiPptr : listaVtnoAllegatiPptr )
			{
				if( mapVtnoAllegatiPutp.containsKey( vtnoAllegatiPptr.getnTKeDocStId() ) )
				{
					List<VtnoAllegatiPptr> lista = mapVtnoAllegatiPutp.get( vtnoAllegatiPptr.getnTKeDocStId() );
					lista.add( vtnoAllegatiPptr );
					mapVtnoAllegatiPutp.put( vtnoAllegatiPptr.getnTKeDocStId()  , lista );
				}
				else
				{
					List<VtnoAllegatiPptr> listaNuovoAllegato = new ArrayList<VtnoAllegatiPptr>();
					listaNuovoAllegato.add( vtnoAllegatiPptr );
					mapVtnoAllegatiPutp.put( vtnoAllegatiPptr.getnTKeDocStId()  , listaNuovoAllegato );
				}
			}
		}
		catch( Exception e )
		{
			
		}
		return mapVtnoAllegatiPutp;
	}

	@Override
	public String toString() {
		return "VtnoAllegatiPptr [tPraticaId=" + tPraticaId + ", codice=" + codice + ", descrizione=" + descrizione
				+ ", dataArrivo=" + dataArrivo + ", nome=" + nome + ", nomeFile=" + nomeFile + ", tipo=" + tipo
				+ ", tTipoCodice=" + tTipoCodice + ", tTipodocDescrizione=" + tTipodocDescrizione
				+ ", tTipodocModcompilazione=" + tTipodocModcompilazione + ", tTipoDocId=" + tTipoDocId + ", name="
				+ name + ", binContent=" + binContent + ", versionNotes=" + versionNotes + ", tKeDocAttrName="
				+ tKeDocAttrName + ", tKeDocAttrValue=" + tKeDocAttrValue + ", nTKeDocStId=" + nTKeDocStId
				+ ", numeroProtocollo=" + numeroProtocollo + ", dataProtocollo=" + dataProtocollo
				+ ", pptrTipoAllegatoCodice=" + pptrTipoAllegatoCodice + ", pptrTipoAllegatoDescrizione="
				+ pptrTipoAllegatoDescrizione + ", faseId=" + faseId + ", pptrTipoAllegatoOrdine="
				+ pptrTipoAllegatoOrdine + ", nomeAllegato=" + nomeAllegato + ", prog=" + prog + ", provv=" + provv
				+ ", idAlfresco=" + idAlfresco + "]";
	}

	
	public String getIdToDownloadFromAlfresco() {
		if( StringUtil.isNotEmpty(idAlfresco) && ( !idAlfresco.equalsIgnoreCase(NOFILEINALFRESCO) ) )  
		{
			return this.idAlfresco;
		}
		return null;
	}
	
}
