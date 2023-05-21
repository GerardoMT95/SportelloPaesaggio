package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.jasper;

import java.io.File;
import java.util.List;
import java.util.UUID;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.TipologicaDTO;
import net.sf.jasperreports.engine.JasperPrint;

public interface CreaReportService {

	public JasperPrint creaPdf_DemoTemplate(int idOrganizzazione,String nomeTemplate);

	public File creaPdf_DocumentoTrasmissione(UUID idPratica, String protocollo, List<TipologicaDTO> listaDestinatari) throws Exception;

	public static String convertiATagJasper(String htmlString) {
		if(htmlString==null) return htmlString;
		if(htmlString.contains("<p>")) {
			htmlString = htmlString.replace("<p>", ""); //alla chiusura metto br
		}
		if(htmlString.contains("</p>")) {
			htmlString = htmlString.replace("</p>", "<br>");
		}
		 if(htmlString.contains("<strong>")){
			htmlString = htmlString.replace("<strong>", "<b>");
		}
		 if(htmlString.contains("</strong>")) {
			htmlString = htmlString.replace("</strong>", "</b>");
		}
		 if(htmlString.contains("<em>")) {
			 htmlString = htmlString.replace("<em>","<i>");
		 }
		 if(htmlString.contains("</em>")) {
			 htmlString = htmlString.replace("</em>","</i>");
		 }
		
		return htmlString;
	}

}
