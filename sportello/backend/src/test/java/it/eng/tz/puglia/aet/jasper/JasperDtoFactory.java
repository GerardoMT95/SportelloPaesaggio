package it.eng.tz.puglia.aet.jasper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.ObjectBuffer;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica.ConfigOptionValue;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.IntegerWrapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperAllegatoDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperAltroTitolareDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica.TipologiaDettaglioDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperCaratterizzazioneInterventoDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperDescrizioneDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperDestinazioneUrbanisticaDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperDichiarazioniArticoliDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperDichiarazioniDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperDomandaDichiarazioneTecnicaDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperDomandaIstanzaDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperEffettiMitigazioneDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperEventualiProcedimentiDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperIndirizzoCompletoDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperInformazioniPersonaliDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperInquadramentoDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperLegittimitaDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperLocalizzazioneDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperPareriAttiDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperParticellaDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperPptrContenutoTabellaDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperPptrDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperPptrTabellaDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperProcedureEdilizieDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperQualificazioneInterventoDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperReferenteDocumentoDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperRichiedenteDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperTecnicoIncaricatoDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperTipologiaInterventoDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperVincolisticaDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.StringWrapper;

public class JasperDtoFactory {
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		creaDomandaIstanzaFromJson();
	}
	
	public static Collection<JasperDomandaIstanzaDto> creaDomandaIstanzaFromJson() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper om=new ObjectMapper();
		JasperDomandaIstanzaDto ret = om.readValue(new File("C:\\tmp\\objSerialized\\jasperDomanda.json"),JasperDomandaIstanzaDto.class );
		return Collections.singletonList(ret);
		
	}
	
	
	public static Collection<JasperDomandaIstanzaDto> creaDomandaIstanza() {
		JasperDomandaIstanzaDto entity = new JasperDomandaIstanzaDto();
		List<JasperLocalizzazioneDto> localizzazione = generateLocalizzazione();
		List<JasperRichiedenteDto> richiedente = Collections.singletonList(generateRichiedente());
		List<JasperTecnicoIncaricatoDto> tecnico = Collections.singletonList(generateTecnicoIncaricato());
		entity.setCodiceFascicolo("APPTR-X-2020");
		entity.setOggettoIntervento("XXX");
		entity.setTipoProcedimento(2);
		entity.setLocalizzazione(localizzazione);
		entity.setRichiedente(richiedente);
		entity.setTecnicoIncaricato(tecnico);
		entity.setDichiarazioni(Collections.singletonList(generateDichiarazione()));
		entity.setAltriTitolari(generateAltriTitolari());
		entity.setDichiarazioniArticoli(Collections.singletonList(generateDichiarazioniArticoli()));
		entity.setDocumentazioneAmministrativa(generateDocumentiAmministrativiIstanza());
		return Collections.singletonList(entity);
	}

	public static Collection<JasperDomandaDichiarazioneTecnicaDto> creaDomandaDichiarazioneTecnica() {
		JasperDomandaDichiarazioneTecnicaDto entity = new JasperDomandaDichiarazioneTecnicaDto();
		List<JasperLocalizzazioneDto> localizzazione = generateLocalizzazione();
		List<JasperRichiedenteDto> richiedente = Collections.singletonList(generateRichiedente());
		List<JasperTecnicoIncaricatoDto> tecnico = Collections.singletonList(generateTecnicoIncaricato());
		List<JasperInquadramentoDto> inquadramento = Collections.singletonList(generateInquadramento());
		List<JasperDescrizioneDto> descrizione = Collections.singletonList(generateDescrizione());
		List<JasperEventualiProcedimentiDto> eventualiProcedimenti = Collections
				.singletonList(generateEventualiProcedimenti());
		List<JasperDestinazioneUrbanisticaDto> destinazioneUrbanistica = generateDestinazioneUrbanistica();
		List<JasperProcedureEdilizieDto> procedureEdilizie = Collections.singletonList(generateProcedureEdilizie());
		List<JasperLegittimitaDto> legittimita = Collections.singletonList(generateLegittimita());
		List<JasperPareriAttiDto> pareriAtti = Collections.singletonList(generatePareriAtti());
		List<JasperPptrDto> pptr = Collections.singletonList(generatePptr());
		List<JasperVincolisticaDto> vincolistica = Collections.singletonList(generateVincolistica());
		List<JasperEffettiMitigazioneDto> effettiMitigazione = Collections.singletonList(generateEffettiMitigazione());
		List<JasperAllegatoDto> elencoAllegati = generateElencoAllegati();
		List<JasperAllegatoDto> documentazioneAmministrativa = generateElencoAllegati();
		List<JasperAllegatoDto> documentazioneTecnica = generateElencoAllegati();
		List<JasperPptrTabellaDto> pptrTabella = generatePptrTabella();
		entity.setCodiceFascicolo("APPTR-X-2020");
		entity.setOggettoIntervento("XXX");
		entity.setTipoProcedimento(2);
		entity.setLocalizzazione(localizzazione);
		entity.setRichiedente(richiedente);
		entity.setTecnicoIncaricato(tecnico);
		entity.setInquadramento(inquadramento);
		entity.setDescrizione(descrizione);
		entity.setEventualiProcedimenti(eventualiProcedimenti);
		entity.setDestinazioneUrbanistica(destinazioneUrbanistica);
		entity.setProcedureEdilizie(procedureEdilizie);
		entity.setLegittimita(legittimita);
		entity.setPareriAtti(pareriAtti);
		entity.setPptr(pptr);
		entity.setVincolistica(vincolistica);
		entity.setEffettiMitigazione(effettiMitigazione);
		entity.setElencoAllegati(elencoAllegati);
		entity.setDocumentazioneAmministrativa(documentazioneAmministrativa);
		entity.setDocumentazioneTecnica(documentazioneTecnica);
		entity.setPptrTabella(pptrTabella);
		return Collections.singletonList(entity);
	}

	private static Integer getRandomInt(Integer limite) {
		Random random = new Random();
		if (limite != null)
			return random.nextInt(limite);
		else
			return random.nextInt();
	}

	private static Boolean getRandomBoolean() {
		Random random = new Random();
		return random.nextBoolean();
	}

	private static List<JasperAllegatoDto> generateElencoAllegati() {
		List<JasperAllegatoDto> list = new ArrayList<JasperAllegatoDto>();
		for (int i = 0; i < 2; i++) {
			JasperAllegatoDto dto = new JasperAllegatoDto();
			dto.setNomeAllegato("Allegato_" + i);
			dto.setNomeArchivio("Allegato_" + i + ".pdf");
			dto.setTipoDocumento(StringGenerator.randomAlphaNumeric(50));
			list.add(dto);
		}
		return list;
	}

	private static List<JasperPptrTabellaDto> generatePptrTabella() {
		List<JasperPptrTabellaDto> tabella = new ArrayList<JasperPptrTabellaDto>();
		for (int i = 0; i < 2; i++) {
			JasperPptrTabellaDto dto = new JasperPptrTabellaDto();
			if (i == 0) {
				dto.setTitolo("6.1 - STRUTTURA IDRO-GEO-MORFOLOGICA");
				List<JasperPptrContenutoTabellaDto> contenuto = new ArrayList<JasperPptrContenutoTabellaDto>();
				JasperPptrContenutoTabellaDto subDto1 = new JasperPptrContenutoTabellaDto();
				subDto1.setNome("6.1.1 - Componenti geomorfologiche");
				subDto1.setDefinizione("art. 49");
				subDto1.setDisposizioniNormative("Indirizzi / Direttive");
				subDto1.setArt2("art. 51/art. 52");
				List<JasperPptrContenutoTabellaDto> figliSub1 = new ArrayList<JasperPptrContenutoTabellaDto>();
				JasperPptrContenutoTabellaDto figlioSub1 = new JasperPptrContenutoTabellaDto();
				figlioSub1.setNome("UCP - Versanti");
				figlioSub1.setArt1("art. 143, co. 1, lett. e)");
				figlioSub1.setDefinizione("art. 50 - 1)");
				figlioSub1.setDisposizioniNormative("Misure di salvaguardia e utilizzazione");
				figlioSub1.setArt2("art. 53");
				figliSub1.add(figlioSub1);
				subDto1.setFigli(figliSub1);
				JasperPptrContenutoTabellaDto subDto2 = new JasperPptrContenutoTabellaDto();
				subDto2.setNome("6.1.2 - Componenti idrologiche");
				subDto2.setDefinizione("art. 40");
				subDto2.setDisposizioniNormative("Indirizzi / Direttive");
				subDto2.setArt2("art. 43/art. 44");
				List<JasperPptrContenutoTabellaDto> figliSub2 = new ArrayList<JasperPptrContenutoTabellaDto>();
				JasperPptrContenutoTabellaDto figlioSub2 = new JasperPptrContenutoTabellaDto();
				figlioSub2.setNome("BP - Fiumi, torrenti, corsi d’acqua iscritti negli elenchi delle acque pubbliche (150m)");
				figlioSub2.setArt1("art. 142, co. 1, lett. c)");
				figlioSub2.setDefinizione("art. 41 - 3)");
				figlioSub2.setDisposizioniNormative("Prescrizioni");
				figlioSub2.setArt2("art. 46");
				figlioSub2.setSpecificare("fiume specifica");
				figliSub2.add(figlioSub2);
				subDto2.setFigli(figliSub2);
				contenuto.add(subDto1);
				contenuto.add(subDto2);
				dto.setContenuto(contenuto);
			} else {
				dto.setTitolo("6.2 - STRUTTURA ECOSISTEMICA - AMBIENTALE");
				List<JasperPptrContenutoTabellaDto> contenuto = new ArrayList<JasperPptrContenutoTabellaDto>();
				JasperPptrContenutoTabellaDto subDto1 = new JasperPptrContenutoTabellaDto();
				subDto1.setNome("6.2.1 - Componenti botanicovegetazionali");
				subDto1.setDefinizione("art. 57");
				subDto1.setDisposizioniNormative("Indirizzi / Direttive");
				subDto1.setArt2("art. 60/art. 61");
				List<JasperPptrContenutoTabellaDto> figliSub1 = new ArrayList<JasperPptrContenutoTabellaDto>();
				JasperPptrContenutoTabellaDto figlioSub1 = new JasperPptrContenutoTabellaDto();
				figlioSub1.setNome("BP – Boschi");
				figlioSub1.setArt1("art. 142, co. 1, lett. g)");
				figlioSub1.setDefinizione("art. 58 - 1)");
				figlioSub1.setDisposizioniNormative("Prescrizioni");
				figlioSub1.setArt2("art. 62");
				figliSub1.add(figlioSub1);
				subDto1.setFigli(figliSub1);
				JasperPptrContenutoTabellaDto subDto2 = new JasperPptrContenutoTabellaDto();
				subDto2.setNome("6.2.2 - Componenti delle aree protette e dei siti naturalistici");
				subDto2.setDefinizione("art. 67");
				subDto2.setDisposizioniNormative("Indirizzi / Direttive");
				subDto2.setArt2("art. 69/art. 70");
				List<JasperPptrContenutoTabellaDto> figliSub2 = new ArrayList<JasperPptrContenutoTabellaDto>();
				JasperPptrContenutoTabellaDto figlioSub2 = new JasperPptrContenutoTabellaDto();
				figlioSub2.setNome("UCP - Siti di rilevanza naturalistica");
				figlioSub2.setArt1("art. 143, co. 1, lett. e)");
				figlioSub2.setDefinizione("art. 68 - 2)");
				figlioSub2.setDisposizioniNormative("Misure di salvaguardia e utilizzazione");
				figlioSub2.setArt2("art. 73");
				figlioSub2.setSpecificare("ucp sito");
				figliSub2.add(figlioSub2);
				subDto2.setFigli(figliSub2);
				contenuto.add(subDto1);
				contenuto.add(subDto2);
				dto.setContenuto(contenuto);
			}
			tabella.add(dto);
		}
		return tabella;
	}

	private static JasperEffettiMitigazioneDto generateEffettiMitigazione() {
		JasperEffettiMitigazioneDto dto = new JasperEffettiMitigazioneDto();
		dto.setDescrizione(StringGenerator.randomAlphaNumeric(200));
		dto.setEffeti(StringGenerator.randomAlphaNumeric(200));
		dto.setMisure(StringGenerator.randomAlphaNumeric(200));
		dto.setContenutiPercettivi(StringGenerator.randomAlphaNumeric(200));
		return dto;
	}

	private static JasperVincolisticaDto generateVincolistica() {
		JasperVincolisticaDto dto = new JasperVincolisticaDto();
		dto.setSottopostoATutela(getRandomBoolean());
		if (dto.getSottopostoATutela()) {
			List<StringWrapper> list = new ArrayList<StringWrapper>();
			for (int i = 0; i < 4; i++) {
				StringWrapper specifica = new StringWrapper();
				specifica.setValue(StringGenerator.randomAlphaNumeric(24));
				list.add(specifica);
			}
			dto.setSpecificaVincolo(list);
		}
		dto.setAltriVincolo(StringGenerator.randomAlphaNumeric(100));
		return dto;
	}

	private static JasperPptrDto generatePptr() {
		JasperPptrDto dto = new JasperPptrDto();
		dto.setAmbitoPaesaggistico(StringGenerator.randomAlphaNumeric(24));
		dto.setFigura(StringGenerator.randomAlphaNumeric(24));
		dto.setArt103(getRandomBoolean());
		dto.setArt142(getRandomBoolean());
		dto.setDescrizione(StringGenerator.randomAlphaNumeric(100));
		return dto;
	}

	private static JasperPareriAttiDto generatePareriAtti() {
		JasperPareriAttiDto dto = new JasperPareriAttiDto();
		// genero 2 atti
		List<TipologiaDettaglioDto> atti = new ArrayList<TipologiaDettaglioDto>();
		for (int i = 0; i < 2; i++) {
			TipologiaDettaglioDto atto = new TipologiaDettaglioDto();
			atto.setTipologia(StringGenerator.randomAlphaNumeric(10));
			atto.setRialisciatoDa(StringGenerator.randomAlphaNumeric(10));
			atto.setNoProtocollo(StringGenerator.randomAlphaNumeric(5, "0123456789"));
			atto.setDataRilascio(new Date());
			atto.setIntestinario(StringGenerator.randomAlphaNumeric(10));
			atti.add(atto);
		}
		dto.setAttiInfo(atti);
		// genero 2 pareri
		List<TipologiaDettaglioDto> pareri = new ArrayList<TipologiaDettaglioDto>();
		for (int i = 0; i < 2; i++) {
			TipologiaDettaglioDto parere = new TipologiaDettaglioDto();
			parere.setTipologia(StringGenerator.randomAlphaNumeric(10));
			parere.setRialisciatoDa(StringGenerator.randomAlphaNumeric(10));
			parere.setNoProtocollo(StringGenerator.randomAlphaNumeric(5, "0123456789"));
			parere.setDataRilascio(new Date());
			parere.setIntestinario(StringGenerator.randomAlphaNumeric(10));
			pareri.add(parere);
		}
		dto.setParreriInfo(pareri);
		return dto;
	}

	private static JasperLegittimitaDto generateLegittimita() {
		JasperLegittimitaDto dto = new JasperLegittimitaDto();
		dto.setTipoLegittimitaUrbanistica(getRandomInt(2) + 1);
		if (dto.getTipoLegittimitaUrbanistica() == 1) {
			dto.setLegittimitaUrbanstica(StringGenerator.randomAlphaNumeric(50));
		} else {
			List<TipologiaDettaglioDto> listUrb = new ArrayList<TipologiaDettaglioDto>();
			for (int i = 0; i < 2; i++) {
				TipologiaDettaglioDto urb = new TipologiaDettaglioDto();
				urb.setTipologia(StringGenerator.randomAlphaNumeric(10));
				urb.setRialisciatoDa(StringGenerator.randomAlphaNumeric(10));
				urb.setNoProtocollo(StringGenerator.randomAlphaNumeric(5, "0123456789"));
				urb.setDataRilascio(new Date());
				urb.setIntestinario(StringGenerator.randomAlphaNumeric(10));
				listUrb.add(urb);
			}
			dto.setLegittimitaInfo(listUrb);
		}
		dto.setTipoLegittimitaPaesaggistica(getRandomInt(2) + 1);
		if (dto.getTipoLegittimitaPaesaggistica() == 1) {
			dto.setTipologiaVincolo(StringGenerator.randomAlphaNumeric(24));
			dto.setDataIntervento(new Date());
			dto.setDataImposizioneVincolo(new Date());
		} else {
			List<TipologiaDettaglioDto> listPae = new ArrayList<TipologiaDettaglioDto>();
			for (int i = 0; i < 2; i++) {
				TipologiaDettaglioDto pae = new TipologiaDettaglioDto();
				pae.setTipologia(StringGenerator.randomAlphaNumeric(10));
				pae.setRialisciatoDa(StringGenerator.randomAlphaNumeric(10));
				pae.setNoProtocollo(StringGenerator.randomAlphaNumeric(5, "0123456789"));
				pae.setDataRilascio(new Date());
				pae.setIntestinario(StringGenerator.randomAlphaNumeric(10));
				listPae.add(pae);
			}
			dto.setAutorizzatoPaesaggisticamenteInfo(listPae);
		}
		return dto;
	}

	private static JasperProcedureEdilizieDto generateProcedureEdilizie() {
		JasperProcedureEdilizieDto dto = new JasperProcedureEdilizieDto();
		dto.setTipoIntervento(getRandomInt(2) + 1);
		if (dto.getTipoIntervento() == 1) {
			dto.setMotivazione(StringGenerator.randomAlphaNumeric(50));
		} else {// suppongo siano checkate entrambe
			dto.setPrimaCheckbox(true);
			dto.setSecondaCheckbox(true);
			dto.setDetagglio(StringGenerator.randomAlphaNumeric(50));
			dto.setPressoData(new Date());
			dto.setEspressoData(new Date());
		}
		return dto;
	}

	private static List<JasperDestinazioneUrbanisticaDto> generateDestinazioneUrbanistica() {
		List<JasperDestinazioneUrbanisticaDto> list = new ArrayList<JasperDestinazioneUrbanisticaDto>();
		for (int i = 0; i < 2; i++) {
			JasperDestinazioneUrbanisticaDto dto = new JasperDestinazioneUrbanisticaDto();
			dto.setNomeComune(StringGenerator.randomAlphaNumeric(10));
			dto.setMostraCoerenza(getRandomBoolean());
			if (dto.isMostraCoerenza()) {
				dto.setCoerenzaAtto(StringGenerator.randomAlphaNumeric(5));
				dto.setCoerenzaData(new Date());
			}
			dto.setStrumentoUrbanistico(getRandomInt(3) + 1);
			dto.setApprovatoData(new Date());
			dto.setApprovatoCon(StringGenerator.randomAlphaNumeric(10));
			dto.setDestinazioneUrbanistica(StringGenerator.randomAlphaNumeric(100));
			dto.setUlterioriTutele(StringGenerator.randomAlphaNumeric(100));
			dto.setConfermaCoerenza(true);
			dto.setStrumentoInAdozione(getRandomInt(3) + 1);
			dto.setAdottatoData(new Date());
			dto.setAdottatoCon(StringGenerator.randomAlphaNumeric(10));
			dto.setDestinazioneUrbanisticaAdottato(StringGenerator.randomAlphaNumeric(100));
			dto.setUlterioriTuteleAdottato(StringGenerator.randomAlphaNumeric(100));
			dto.setConformitaStrumentoUrbanistico(true);
			list.add(dto);
		}
		return list;
	}

	private static JasperEventualiProcedimentiDto generateEventualiProcedimenti() {
		JasperEventualiProcedimentiDto dto = new JasperEventualiProcedimentiDto();
		dto.setPresenza(getRandomBoolean());
		if (dto.getPresenza()) {
			dto.setDescrizione(StringGenerator.randomAlphaNumeric(100));
		}
		return dto;
	}

	private static JasperDescrizioneDto generateDescrizione() {
		JasperDescrizioneDto dto = new JasperDescrizioneDto();
		List<JasperCaratterizzazioneInterventoDto> caratterizzazioneIntervento = Collections
				.singletonList(generateCaratterizzazioneIntervento());
		List<JasperQualificazioneInterventoDto> qualificazioneIntervento = generateQualificazioneIntervento();
		List<JasperTipologiaInterventoDto> tipologiaIntervento = Collections
				.singletonList(generateTipologiaIntervento());
		dto.setDescrizione(StringGenerator.randomAlphaNumeric(100));
		dto.setCaratterizzazioneIntervento(caratterizzazioneIntervento);
		dto.setQualificazioneIntervento(qualificazioneIntervento);
		dto.setTipologiaIntervento(tipologiaIntervento);
		return dto;
	}

	private static JasperTipologiaInterventoDto generateTipologiaIntervento() {
		JasperTipologiaInterventoDto dto = new JasperTipologiaInterventoDto();
		dto.setTipologiaDiIntervento(StringGenerator.randomAlphaNumeric(24));
		dto.setInParticolareAgliArtt(StringGenerator.randomAlphaNumeric(24));
		dto.setDataApprovazione(new Date());
		dto.setCon(StringGenerator.randomAlphaNumeric(5));
		return dto;
	}

	private static List<JasperQualificazioneInterventoDto> generateQualificazioneIntervento() {
		List<JasperQualificazioneInterventoDto> list = new ArrayList<JasperQualificazioneInterventoDto>();
		for (int i = 0; i < 2; i++) {
			JasperQualificazioneInterventoDto dto = new JasperQualificazioneInterventoDto();
			dto.setText(StringGenerator.randomAlphaNumeric(24));
			if (i == 1) {
				StringWrapper sub1 = new StringWrapper(StringGenerator.randomAlphaNumeric(24));
				StringWrapper sub2 = new StringWrapper(StringGenerator.randomAlphaNumeric(24));
				List<StringWrapper> listaSub = new ArrayList<StringWrapper>();
				listaSub.add(sub1);
				listaSub.add(sub2);
				dto.setSubtext(listaSub);
			}
			list.add(dto);
		}
		return list;
	}

	private static JasperCaratterizzazioneInterventoDto generateCaratterizzazioneIntervento() {
		JasperCaratterizzazioneInterventoDto dto = new JasperCaratterizzazioneInterventoDto();
		List<ConfigOptionValue> options = new ArrayList<ConfigOptionValue>();
		for (int i = 0; i < 5; i++) { // fingo di aver selezionato 5 checkbox di cui...
			ConfigOptionValue option = new ConfigOptionValue();
			option.setValue(StringGenerator.randomAlphaNumeric(10));
			if (getRandomBoolean()) { // ...solo ad alcune metto la specifica (per maggiori info leggere commenti in
										// JasperCaratterizzazioneInterventoDto)
				option.setText(StringGenerator.randomAlphaNumeric(50));
			}
			options.add(option);
		}
		dto.setCaratterizzazione(options);
		if (getRandomBoolean()) {
			dto.setDurata("P");
		} else {
			dto.setDurata("T");
		}
		return dto;
	}

	private static JasperInquadramentoDto generateInquadramento() {
		JasperInquadramentoDto inquadramento = new JasperInquadramentoDto();
		Random random = new Random();
		Integer value = random.nextInt(10) + 1;
		inquadramento.setContestoPaesaggInterv(value);
		if (value == 10) {
			inquadramento.setContestoPaesaggIntervSpecifica(StringGenerator.randomAlphaNumeric(24));
		}
		value = random.nextInt(6) + 1;
		inquadramento.setDestinazioneUso(value);
		if (value == 6) {
			inquadramento.setDestinazioneUsoSpecifica(StringGenerator.randomAlphaNumeric(24));
		}
		value = random.nextInt(7) + 1;
		inquadramento.setMorfologiaConPaesag(value);
		if (value == 7) {
			inquadramento.setMorfologiaConPaesagSpecifica(StringGenerator.randomAlphaNumeric(24));
		}
		return inquadramento;
	}

	private static List<JasperLocalizzazioneDto> generateLocalizzazione() {
		List<JasperLocalizzazioneDto> entities = new ArrayList<JasperLocalizzazioneDto>();
		Integer n = getRandomInt(3) + 1;
		for (int j = 0; j < n; j++) {
			JasperLocalizzazioneDto entity = new JasperLocalizzazioneDto();
			Integer x = getRandomInt(6);
			entity.setComune(StringGenerator.randomAlphaNumeric(16));
			entity.setDataRiferimentoCatastale(new Date());
			entity.setDestUsoAtt(StringGenerator.randomAlphaNumeric(12));
			entity.setDestUsoProg(StringGenerator.randomAlphaNumeric(12));
			entity.setIndirizzo("Via " + StringGenerator.randomAlphaNumeric(8));
			entity.setInterno(StringGenerator.randomAlphaNumeric(10));
			entity.setNumCivico(StringGenerator.randomAlphaNumeric(5));
			entity.setPiano(StringGenerator.randomAlphaNumeric(10));
			entity.setParticelle(generateParticelle(x));
			entity.setSigla(StringGenerator.randomAlphaNumeric(2, "ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
			entity.setUlterioriInformazioni(null);
			entities.add(entity);
		}
		return entities;
	}

	private static List<JasperParticellaDto> generateParticelle(Integer x) {
		List<JasperParticellaDto> particelle = new ArrayList<JasperParticellaDto>();
		for (int j = 0; j < x; j++) {
			JasperParticellaDto tmp = new JasperParticellaDto();
			tmp.setParticella(getRandomInt(100).toString());
			tmp.setFoglio(getRandomInt(100).toString());
			tmp.setSezione("XX");
			tmp.setCodCat("A662");
			tmp.setLivello("PARTICELLE");
			tmp.setSub(getRandomInt(100).toString());
			particelle.add(tmp);
		}
		return particelle;
	}

	private static JasperTecnicoIncaricatoDto generateTecnicoIncaricato() {
		JasperTecnicoIncaricatoDto tecnico = new JasperTecnicoIncaricatoDto();
		baseGenerateInfo(tecnico);
		tecnico.setCellulare(StringGenerator.randomAlphaNumeric(7, "1234567890"));
		tecnico.setDi(StringGenerator.randomAlphaNumeric(24));
		tecnico.setFax(StringGenerator.randomAlphaNumeric(24));
		tecnico.setPec(StringGenerator.randomAlphaNumeric(24));
		tecnico.setIscritoAllOrdine(StringGenerator.randomAlphaNumeric(24));
		tecnico.setN(StringGenerator.randomAlphaNumeric(24));
		tecnico.setConStudioIn(Collections.singletonList(generateIndirizzo()));
		tecnico.setResidenteIn(Collections.singletonList(generateIndirizzo()));
		tecnico.setRecapitoTelefonico(StringGenerator.randomAlphaNumeric(7, "1234567890"));
		return tecnico;
	}

	private static JasperRichiedenteDto generateRichiedente() {
		JasperRichiedenteDto richiedente = new JasperRichiedenteDto();
		baseGenerateInfo(richiedente);
		richiedente.setIndirizzoEmail(StringGenerator.randomAlphaNumeric(24));
		richiedente.setNelCaso(getRandomBoolean());
		richiedente.setResidenteIn(Collections.singletonList(generateIndirizzo()));
		if (richiedente.getNelCaso()) {
			richiedente.setInQualitaDi(getRandomInt(3));
			richiedente.setPartitaIva(StringGenerator.randomAlphaNumeric(24));
			richiedente.setSocieta(StringGenerator.randomAlphaNumeric(24));
			richiedente.setSocietaCodiceFiscale(StringGenerator.randomAlphaNumeric(16));
			richiedente.setDescAltroDitta(StringGenerator.randomAlphaNumeric(24));
		}
		return richiedente;
	}

	private static <T extends JasperInformazioniPersonaliDto> void baseGenerateInfo(T base) {
		base.setCodiceFiscale(StringGenerator.randomAlphaNumeric(16));
		base.setCognome(StringGenerator.randomAlphaNumeric(24));
		base.setNome(StringGenerator.randomAlphaNumeric(24));
		base.setNatoIl(new Date());
		if (getRandomBoolean()) {
			base.setNatoStato("Italia");
			base.setNatoProvincia(StringGenerator.randomAlphaNumeric(24));
			base.setNatoComune(StringGenerator.randomAlphaNumeric(24));
		} else {
			base.setNatoStato(StringGenerator.randomAlphaNumeric(24));
			base.setNatoCitta(StringGenerator.randomAlphaNumeric(24));
		}

		if (getRandomBoolean())
			base.setSesso("F");
		else
			base.setSesso("M");
		base.setDocumento(Collections.singletonList(generateDocumento()));
	}

	private static JasperReferenteDocumentoDto generateDocumento() {
		JasperReferenteDocumentoDto doc = new JasperReferenteDocumentoDto();
		doc.setDataRilascio(new Date());
		doc.setDataScadenza(new Date());
		doc.setEnteRilascio("Comune di " + StringGenerator.randomAlphaNumeric(10));
		doc.setNumero(StringGenerator.randomAlphaNumeric(5));
		doc.setTipoDocumento(getRandomInt(4) + 1);
		return doc;
	}

	private static JasperIndirizzoCompletoDto generateIndirizzo() {
		JasperIndirizzoCompletoDto indirizzo = new JasperIndirizzoCompletoDto();
		indirizzo.setCap(StringGenerator.randomAlphaNumeric(5));
		indirizzo.setCitta(StringGenerator.randomAlphaNumeric(24));
		indirizzo.setComune(StringGenerator.randomAlphaNumeric(24));
		indirizzo.setN(StringGenerator.randomAlphaNumeric(3));
		indirizzo.setProvincia(StringGenerator.randomAlphaNumeric(24));
		indirizzo.setStato("Italia");
		indirizzo.setVia("Via " + StringGenerator.randomAlphaNumeric(24));
		return indirizzo;
	}

	private static JasperDichiarazioniDto generateDichiarazione() {
		JasperDichiarazioniDto entity = new JasperDichiarazioniDto();
		
		entity.setTitolarita(getRandomInt(10));
		entity.setImmobile(getRandomBoolean() ? "S" : "N");
		entity.setTipoProcedimento(2);
		for(int j = 0; j < 4; j++)
		{
			StringWrapper iw = new StringWrapper("aaaaaaaaaaaaaaaaaaaaaaaaaaaaa "+j);
			entity.getDisclaimer().add(iw);
		}

		return entity;
	}

	private static JasperDichiarazioniArticoliDto generateDichiarazioniArticoli() {
		JasperDichiarazioniArticoliDto entity = new JasperDichiarazioniArticoliDto();
		entity.setArt136(getRandomBoolean());
		entity.setArt142(getRandomBoolean());
		entity.setArt134(getRandomBoolean());
		if (entity.getArt136()) {
			entity.setArt136a(getRandomBoolean());
			entity.setArt136b(getRandomBoolean());
			entity.setArt136c(getRandomBoolean());
			entity.setArt136d(getRandomBoolean());
		} else {
			entity.setArt136a(false);
			entity.setArt136b(false);
			entity.setArt136c(false);
			entity.setArt136d(false);
		}
		if (entity.getArt142()) {
			entity.setFiumiTorrenti(getRandomBoolean());
			entity.setZoneUmide(getRandomBoolean());
			entity.setUsiCivici(getRandomBoolean());
			entity.setTerritoriCostieri(getRandomBoolean());
			entity.setTerritoriContermini(getRandomBoolean());
			entity.setParchiRiserve(getRandomBoolean());
			entity.setInteresseArcheologico(getRandomBoolean());
			entity.setFiumiTorrenti(getRandomBoolean());
		} else {
			entity.setFiumiTorrenti(false);
			entity.setZoneUmide(false);
			entity.setUsiCivici(false);
			entity.setTerritoriCostieri(false);
			entity.setTerritoriContermini(false);
			entity.setParchiRiserve(false);
			entity.setInteresseArcheologico(false);
			entity.setFiumiTorrenti(false);
		}
		return entity;
	}

	private static List<JasperAltroTitolareDto> generateAltriTitolari() {
		List<JasperAltroTitolareDto> entities = new ArrayList<JasperAltroTitolareDto>();
		Integer n = getRandomInt(3) + 1;
		for (int j = 0; j < n; j++) {
			JasperAltroTitolareDto entity = new JasperAltroTitolareDto();
			baseGenerateInfo(entity);
			entity.setIndirizzoEmail(StringGenerator.randomAlphaNumeric(24));
			entity.setNelCaso(getRandomBoolean());
			entity.setResidenteIn(Collections.singletonList(generateIndirizzo()));
			if (entity.getNelCaso()) {
				entity.setInQualitaDi(getRandomInt(3));
				entity.setPartitaIva(StringGenerator.randomAlphaNumeric(24));
				entity.setSocieta(StringGenerator.randomAlphaNumeric(24));
				entity.setSocietaCodiceFiscale(StringGenerator.randomAlphaNumeric(16));
				entity.setDescAltroDitta(StringGenerator.randomAlphaNumeric(24));
			}
			entity.setTitolaritaInQualitaDi("");
			if (entity.getTitolaritaInQualitaDi().equals(9))
				entity.setDescrizioneAltro(StringGenerator.randomAlphaNumeric(30));
			entities.add(entity);
		}
		return entities;
	}

	private static List<StringWrapper> generateDocumentiAmministrativiIstanza() {
		List<StringWrapper> entities = new ArrayList<StringWrapper>();
		Integer n = getRandomInt(3) + 1;
		for (int j = 0; j < n; j++) {
			StringWrapper entity = new StringWrapper();
			entity.setValue(StringGenerator.randomAlphaNumeric(20));
			entities.add(entity);
		}
		return entities;
	}
}
