/**
 * 
 */
package it.eng.tz.puglia.autape.xml;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;

import org.junit.Test;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import it.eng.tz.regione.puglia.webmail.be.mail.ricevuta.daticert.Postacert;

/**
 * @author Adriano Colaianni
 * @date 8 set 2021
 */
public class RicevutaParse {
	
	@Test
	public void parseRicevutaConDTD() throws JAXBException, ParserConfigurationException, SAXException {
		final String xmlRicevuta="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
				"<?xml-stylesheet type=\"text/xsl\" href=\"https://www.telecompost.it/dtd/postacert.xsl\"?>\r\n" + 
				"<!DOCTYPE postacert PUBLIC \"posta certificata\" \"https://www.telecompost.it/dtd/postacert.dtd\">\r\n" + 
				"\r\n" + 
				"<postacert tipo=\"avvenuta-consegna\" errore=\"nessuno\">\r\n" + 
				"  <intestazione>\r\n" + 
				"    <mittente>sit.innovapuglia@pec.rupar.puglia.it</mittente>\r\n" + 
				"    <destinatari tipo=\"certificato\">provincia@pec.provincia.brindisi.it</destinatari>\r\n" + 
				"    <destinatari tipo=\"certificato\">servizio.assettoterritorio@pec.rupar.puglia.it</destinatari>\r\n" + 
				"    <destinatari tipo=\"certificato\">ufficioprotocollo@pec.comune.brindisi.it</destinatari>\r\n" + 
				"    <risposte>sit.innovapuglia@pec.rupar.puglia.it</risposte>\r\n" + 
				"    <oggetto>Autorizzazione Paesaggistica AP74001-66-2012 -  BRINDISI - ABBRACCIAVENTO Angelo</oggetto>\r\n" + 
				"  </intestazione>\r\n" + 
				"  <dati>\r\n" + 
				"    <gestore-emittente>I.T. Telecom S.R.L.</gestore-emittente>\r\n" + 
				"    <data zona=\"+0100\">\r\n" + 
				"      <giorno>07/01/2013</giorno>\r\n" + 
				"      <ora>17:41:49</ora>\r\n" + 
				"    </data>\r\n" + 
				"    <identificativo>201301071741240100.0787.pech@pec.rupar.puglia.it</identificativo>\r\n" + 
				"    <msgid>&lt;29358849.801357576884181.JavaMail.sit.innovapuglia@pec.rupar.puglia.it&gt;</msgid>\r\n" + 
				"    <ricevuta tipo=\"completa\" />\r\n" + 
				"    <consegna>provincia@pec.provincia.brindisi.it</consegna>\r\n" + 
				"  </dati>\r\n" + 
				"</postacert>";
		
		JAXBContext jaxbContext;
		jaxbContext = JAXBContext.newInstance(Postacert.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Postacert ricevutaXmlObj = null;
		ricevutaXmlObj = (Postacert) jaxbUnmarshaller.unmarshal(new ByteArrayInputStream(xmlRicevuta.getBytes(StandardCharsets.UTF_8)));
		
//		JAXBContext jaxbContext;
//		jaxbContext = JAXBContext.newInstance(Postacert.class);
//		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
//		Postacert ricevutaXmlObj = null;
//		
//		 SAXParserFactory spf = SAXParserFactory.newInstance();
//		 	spf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
////	        spf.setFeature("apache.org/xml/features/nonvalidating/load-external-dtd", false); 
////	        spf.setFeature("xml.org/sax/features/validation", false);
//	        XMLReader xmlReader = spf.newSAXParser().getXMLReader();
//	        InputSource inputSource = new InputSource(new ByteArrayInputStream(xmlRicevuta.getBytes(StandardCharsets.UTF_8)));
//	        SAXSource source = new SAXSource(xmlReader, inputSource);
//	     ricevutaXmlObj = (Postacert) jaxbUnmarshaller.unmarshal(source);
		System.out.println("ricevuta "+ricevutaXmlObj);
	}
	
	@Test
	public void testSB() {
		StringBuilder sb=new StringBuilder("testmail.ente.ee@aue.it");
		int idx = sb.lastIndexOf(".");
		if(idx>0) {
			sb.delete(idx, sb.length());
		}
		sb.append(".iot");
		System.out.print(sb.toString());
	
	}
	
}
