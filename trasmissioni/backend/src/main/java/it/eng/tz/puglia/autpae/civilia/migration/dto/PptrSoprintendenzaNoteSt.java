/**
 * 
 */
package it.eng.tz.puglia.autpae.civilia.migration.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Adriano Colaianni
 * @date 8 set 2021
 */
public class PptrSoprintendenzaNoteSt implements Serializable{

private static final long serialVersionUID = 1L;
	
	//@Column(name="TNO_PPTR_SOPNOTE_ST_ID")
	private long id;
	
	//@Column(name="CODICE_PRATICA_APPPTR")
	private String codicePraticaApptr;
	
	//@Column(name="SPUNTA")
	private long spunta;

	//@Column(name="NOTE")
	private String note;
	
	//@Column(name="T_KE_UTENTE_ID")
	private long tKeUtenteId;

	//@Column(name="USERNAME")
	private String username;
	
	//@Column(name="LAST_MODIFIED")
	//@Temporal(TemporalType.DATE)
	private Date lastModified;
		
	//@Column(name="BIN_CONTENT_NEGATIVO")
	private byte[] binContentNegativo; 
			
	//@Column(name="BIN_CONTENT_NON_COMPLETATO")
	private byte[] binContentNonCompletato;
	
	//@Column(name="NOME_FILE_NEGATIVO")
	private String nomeFileNegativo;

	//@Column(name="NOME_FILE_NCOMPLETATO")
	private String nomeFileNonCompletato;
	
	//@Column(name="PROG")
	private long prog;
	
	public long getProg() {
		return prog;
	}

	public void setProg(long prog) {
		this.prog = prog;
	}
	
	public String getNomeFileNegativo() {
		return nomeFileNegativo;
	}

	public void setNomeFileNegativo(String nomeFileNegativo) {
		this.nomeFileNegativo = nomeFileNegativo;
	}

	public String getNomeFileNonCompletato() {
		return nomeFileNonCompletato;
	}

	public void setNomeFileNonCompletato(String nomeFileNonCompletato) {
		this.nomeFileNonCompletato = nomeFileNonCompletato;
	}

	public byte[] getBinContentNegativo() {
		return binContentNegativo;
	}

	public void setBinContentNegativo(byte[] binContentNegativo) {
		this.binContentNegativo = binContentNegativo;
	}

	public byte[] getBinContentNonCompletato() {
		return binContentNonCompletato;
	}

	public void setBinContentNonCompletato(byte[] binContentNonCompletato) {
		this.binContentNonCompletato = binContentNonCompletato;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCodicePraticaApptr() {
		return codicePraticaApptr;
	}

	public void setCodicePraticaApptr(String codicePraticaApptr) {
		this.codicePraticaApptr = codicePraticaApptr;
	}

	public long getSpunta() {
		return spunta;
	}

	public void setSpunta(long spunta) {
		this.spunta = spunta;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public long gettKeUtenteId() {
		return tKeUtenteId;
	}

	public void settKeUtenteId(long tKeUtenteId) {
		this.tKeUtenteId = tKeUtenteId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModificed(Date lastModified) {
		this.lastModified = lastModified;
	}
	
//	public void set_Dati_FormDataMultipartParameters( Map<String , String> mapDati , Map<String , FileItem> files )
//	{
//		if( mapDati != null && !mapDati.isEmpty() )
//		{
//			/*Vengono settati i valori che provengono dai input della form 
//			 * 1- SPUNTA
//			 * 2- note_sop
//			 */
//			setDati( mapDati );
//		}
//		
//		
//		if( files != null && !files.isEmpty() )
//		{
//			/*
//			 * Set del file e dei metadati del file DOC 
//			 * 7 -  NOME_FILE
//			 * 8 - FORMATO_FILE
//			 * 9 - FILE (BYTE) 
//			 * Viene accettato di non caricare il file subito e quindi nessuna exception in caso che il file sia NULL, 
//			 * per questo tipo di operazione.
//			 */ 			
//			setFile( files );
//			
//		}
//	}
//	
//	private void setFile( Map<String , FileItem > files )
//	{
//		try
//		{
//			//Set del file e dei metadati del file 
//			for ( Map.Entry<String , FileItem> entry: files.entrySet() )
//			{
//				FileItem file = entry.getValue();
//				if( file != null)
//				{
//					long FileSize = file.getSize();
//					if( FileSize > 0 )
//					{
//						String fieldName = file.getFieldName();
//						//Se il file proviene dal Form Input "CaricamentofileNegativo" 
//						if( fieldName.compareTo("CaricamentofileNegativo") == 0)
//						{
//							String file_name = file.getName();
//							if( file_name != null )
//							{
//								this.setNomeFileNegativo( file_name );
//							}
//							
//							byte[] contenutoFile = file.get();
//							if( contenutoFile != null )
//							{
//								this.setBinContentNegativo( contenutoFile );
//							}
//						}
//						
//						//Se il file proviene dal Form Input "CaricamentofileNegativo" 
//						if( fieldName.compareTo("CaricamentofileNonCompleto") == 0)
//						{
//							String file_name = file.getName();
//							if( file_name != null )
//							{
//								this.setNomeFileNonCompletato( file_name );
//							}
//							
//							byte[] contenutoFile = file.get();
//							if( contenutoFile != null )
//							{
//								this.setBinContentNonCompletato( contenutoFile );
//							}
//						}
//						
//					}
//				}
//			}
//		}
//		catch ( Exception e )
//		{
//			e.printStackTrace();
//			return;	
//		}
//		
//	}	
//		
//	private void setDati( Map<String , String> mapDati )
//	{
//		try
//		{						
//			//Set di dati provenienti dai form inputs 
//			for( Map.Entry<String, String> entry : mapDati.entrySet() )
//			{
//				/*Vengono settati i valori che provengono dai input della form 
//				 * 3- SPUNTA
//				 * 4- note_sop
//				 */
//				
//				//TIT_RUOLO_ID
//				if ( entry.getKey().compareTo("SPUNTA") == 0 )
//				{
//					String spunta = entry.getValue();
//					if( spunta != null && !spunta.isEmpty() )
//					{
//						Long lspunta = Long.valueOf( spunta ).longValue();
//						this.setSpunta( lspunta );
//					}
//				}
//				
//				//TIT_RUOLO_ALTRO
//				if( entry.getKey().compareTo("note_sop") == 0 )
//				{
//					this.setNote( entry.getValue() );
//				}				
//			}
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//			return;			
//		}
//	}		

	
}
