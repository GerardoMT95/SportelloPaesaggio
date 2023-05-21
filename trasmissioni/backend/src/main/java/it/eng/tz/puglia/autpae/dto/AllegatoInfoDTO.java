package it.eng.tz.puglia.autpae.dto;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.puglia.autpae.entity.AllegatoDTO;

public class AllegatoInfoDTO extends AllegatoDTO implements Serializable
{
	private static final long serialVersionUID = 7545873978918968471L;

	private MultipartFile file;
	/**
	 * indica se il file non verrà allegato alla mail e quindi verrà aggiunto un url nel corpo della mail
	 */
	private boolean isUrl;
	
	
	public AllegatoInfoDTO() { }
	
	public AllegatoInfoDTO(AllegatoDTO informazioni, MultipartFile file,boolean isUrl) {
		this(informazioni,file);
		this.isUrl=isUrl;
	}
	
	public AllegatoInfoDTO(AllegatoDTO informazioni, MultipartFile file) {
		this.id=informazioni.getId();
		this.nome=informazioni.getNome();
		this.titolo=informazioni.getTitolo();
		this.descrizione=informazioni.getDescrizione();
		this.mimeType=informazioni.getMimeType();
		this.dataCaricamento=informazioni.getDataCaricamento();
		this.contenuto=informazioni.getContenuto();
		this.checksum=informazioni.getChecksum();
		this.deleted=informazioni.getDeleted();
		this.dimensione=informazioni.getDimensione();
		this.utenteInserimento=informazioni.getUtenteInserimento();
		this.usernameInserimento=informazioni.getUsernameInserimento();
		this.numeroProtocolloIn=informazioni.getNumeroProtocolloIn();
		this.numeroProtocolloOut=informazioni.getNumeroProtocolloOut();
		this.dataProtocolloIn=informazioni.getDataProtocolloIn();
		this.dataProtocolloOut=informazioni.getDataProtocolloOut();
		this.tipo = informazioni.getTipo();
		this.file = file;
	}
	
	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	@Override
	public String toString() {
		return "AllegatoInfoDTO [file=" + file + "]";
	}

	public boolean getIsUrl() {
		return isUrl;
	}

	public void setIsUrl(boolean isUrl) {
		this.isUrl = isUrl;
	}
	
}
