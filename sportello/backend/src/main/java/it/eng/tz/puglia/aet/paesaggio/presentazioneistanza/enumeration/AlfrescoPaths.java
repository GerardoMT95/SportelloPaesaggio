package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration;

import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.detect.DefaultDetector;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MimeTypes;

public enum AlfrescoPaths {

	ISTANZA("Istanza"),
	ALLEGATI("documentazione allegata"),
	DOC_RICONOSCIMENTO("documenti riconoscimento"),
	RICHIEDENTE("richiedente"),
	ALTRI_TITOLARI("altri titolari"),
	TECNICO_REDATTORE("tecnico redattore"),
	ALLEGATI_COMUNICAZIONE("allegati comunicazioni"),
	PATH_SEPARATOR("/");

	public static final String UNDERSCORE = "_";
	private String value = null;

	private AlfrescoPaths(String path) {
		this.value = path;
	}

	public String getTextValue() {
		return value;
	}
	
	public static String getPathDocRiconoscimento(TipoReferente tipo) {
		StringBuilder sb=new StringBuilder();
		sb
		.append(ISTANZA.getTextValue())
		.append(PATH_SEPARATOR.getTextValue())
		.append(DOC_RICONOSCIMENTO.getTextValue())
		.append(PATH_SEPARATOR.getTextValue());
		switch (tipo) {
		case SD :
			sb
			.append(RICHIEDENTE.getTextValue())
			.append(PATH_SEPARATOR.getTextValue());
			break;
		case AT :
			sb
			.append(ALTRI_TITOLARI.getTextValue())
			.append(PATH_SEPARATOR.getTextValue());
			break;
		case TR :
			sb
			.append(TECNICO_REDATTORE.getTextValue())
			.append(PATH_SEPARATOR.getTextValue());
			break;
		default:
			break;
		}
		return sb.toString();
		
	}
	
	public static String getPathDocAllegato(SezioneAllegati sezione) {
		StringBuilder sb=new StringBuilder();
		sb
		.append(ISTANZA.getTextValue())
		.append(PATH_SEPARATOR.getTextValue())
		.append(ALLEGATI.getTextValue())
		.append(PATH_SEPARATOR.getTextValue())
		.append(sezione.toString())
		.append(PATH_SEPARATOR.getTextValue());
		return sb.toString();
	}
	
	public static String detectMimeType(final InputStream data) throws IOException {
		try (TikaInputStream tikaIS = TikaInputStream.get(data)) {
			final Metadata metadata = new Metadata();
			return new DefaultDetector(MimeTypes.getDefaultMimeTypes()).detect(tikaIS, metadata).toString();
		} finally {
		}
	}
}
