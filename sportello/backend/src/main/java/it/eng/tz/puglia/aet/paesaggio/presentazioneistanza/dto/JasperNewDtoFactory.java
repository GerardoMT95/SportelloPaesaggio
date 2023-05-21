package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.jasper.dto.JasperInfoEmailAllegatoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.jasper.dto.JasperInfoEmailDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.jasper.dto.JasperInfoEmailDestinatarioDTO;

public class JasperNewDtoFactory {
	
	public static Collection<JasperInfoEmailDTO> creaInformazioniEmail() {
		JasperInfoEmailDTO entity = new JasperInfoEmailDTO();
		entity.setData(new Date());
	//	entity.setProtocollo("r_puglia/ct_rupar_puglia/07/05/2020/0002403");
		entity.setMittenteEmail("asdsad@ss,ss");
	//	entity.setMittenteNome("Nome yyggg");
		entity.setOggetto("Oggetto test 123 prova Oggetto test 123 prova Oggetto test 123 prova Oggetto test 123 prova Oggetto test 123 prova Oggetto test 123 prova Oggetto test 123 prova Oggetto test 123 prova Oggetto test 123 prova Oggetto test 123 prova Oggetto test 123 prova Oggetto test 123 prova Oggetto test 123 prova Oggetto test 123 prova ");
		entity.setCorpo("<!DOCTYPE html>\r\n"
				+ "<html>\r\n"
				+ "<body>\r\n"
				+ "\r\n"
				+ "<h2>An Unordered HTML List</h2>\r\n"
				+ "\r\n"
				+ "<ul>\r\n"
				+ "  <li>Coffee</li>\r\n"
				+ "  <li>Tea</li>\r\n"
				+ "  <li>Milk</li>\r\n"
				+ "</ul>  \r\n"
				+ "\r\n"
				+ "<h2>An Ordered HTML List</h2>\r\n"
				+ "\r\n"
				+ "<ol>\r\n"
				+ "  <li>Coffee</li>\r\n"
				+ "  <li>Tea</li>\r\n"
				+ "  <li>Milk</li>\r\n"
				+ "</ol> \r\n"
				+ "\r\n"
				+ "</body>\r\n"
				+ "</html>");
		List<JasperInfoEmailAllegatoDTO> listaAllegati = new ArrayList<>();
		listaAllegati.add(new JasperInfoEmailAllegatoDTO("nome allegato 1", "gdfklgsdfbgsdfb", 123));
		listaAllegati.add(new JasperInfoEmailAllegatoDTO("nome allegato 2", "hdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfb", 943718410));
		listaAllegati.add(new JasperInfoEmailAllegatoDTO("nome allegato 2", "hdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfb", 943718410));
		listaAllegati.add(new JasperInfoEmailAllegatoDTO("nome allegato 2", "hdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfb", 943718410));
		listaAllegati.add(new JasperInfoEmailAllegatoDTO("nome allegato 2", "hdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfb", 943718410));
		listaAllegati.add(new JasperInfoEmailAllegatoDTO("nome allegato 2", "hdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfb", 943718410));
		listaAllegati.add(new JasperInfoEmailAllegatoDTO("nome allegato 2", "hdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfb", 943718410));
		listaAllegati.add(new JasperInfoEmailAllegatoDTO("nome allegato 2", "hdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfb", 943718410));
		listaAllegati.add(new JasperInfoEmailAllegatoDTO("nome allegato 2", "hdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfb", 943718410));
		listaAllegati.add(new JasperInfoEmailAllegatoDTO("nome allegato 2", "hdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfbhdSSDFfgdbgsdfb", 943718410));
		listaAllegati.add(new JasperInfoEmailAllegatoDTO("nome allegato 3", "gdfklgsdfSDfsfb", 12345));
		entity.setListaAllegati(listaAllegati);
		List<JasperInfoEmailDestinatarioDTO> listaDestinatari = new ArrayList<>();
		listaDestinatari.add(new JasperInfoEmailDestinatarioDTO("to","email1@email_gy.com","nome destinatario 1 nome destinatario 1 nome destinatario 1 nome destinatario 1 nome destinatario 1 nome destinatario 1 nome destinatario 1 nome destinatario 1"));
		listaDestinatari.add(new JasperInfoEmailDestinatarioDTO("email2@email_gy.com"));
		listaDestinatari.add(new JasperInfoEmailDestinatarioDTO("to","email3@email_gy.com","nome destinatario 3"));
		entity.setListaDestinatari(listaDestinatari);
		return Collections.singletonList(entity);
	}

}