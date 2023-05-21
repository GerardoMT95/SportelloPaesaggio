package it.eng.tz.puglia.servizi_esterni.jasper.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import it.eng.tz.puglia.autpae.dto.AllegatoCustomDTO;
import it.eng.tz.puglia.autpae.dto.TipologicaDTO;
import it.eng.tz.puglia.autpae.entity.ParticelleCatastaliDTO;
import it.eng.tz.puglia.autpae.entity.ResponsabileDTO;
import it.eng.tz.puglia.autpae.entity.RichiedenteDTO;
import it.eng.tz.puglia.autpae.enumeratori.TipoDocRiconoscimento;
import it.eng.tz.puglia.autpae.utility.StringWrapper;

public class JasperDTO_Factory {
	
	@SuppressWarnings("serial")
	public static Collection<JasperDTO> dettaglioFascicolo() {
		
		JasperDTO dto = new JasperDTO();
		
		dto.setRichiedenteInfo(new ArrayList<>(richiedente()));
		dto.setResponsabileInfo(new ArrayList<>(responsabile()));
		
	//	dto.setProtocollo("CIAO");
		
		dto.getListaDestinatari().add(new TipologicaDTO("indirizzo_1@email_1.it", "nome cognome 1"));
		dto.getListaDestinatari().add(new TipologicaDTO("indirizzo_2@email_2.it", "nome cognome 2"));
		dto.getListaDestinatari().add(new TipologicaDTO("indirizzo_3@email_3.it", "nome cognome 3"));
		
		dto.setCodicePratica("codicePratica");
		dto.setUfficio("ufficio");
		dto.setRichiedente("richiedente");
		dto.setTipoProcedimento("tipoProcedimento");
		dto.setOggettoIntervento("oggettoIntervento");
		
		dto.setProvvedimentoNumero("provvedimentoNumero");
		dto.setProvvedimentoDataAutorizzazione(new Date());
		dto.setProvvedimentoEsito("provvedimentoEsito");
		dto.setProvvedimentoRup("provvedimentoRup");
		
		dto.setInterventoTipologia("PIANI PARTICOLAREGGIATI DI ESECUZIONE, (L. 1150/1942 ESS. MM. E II);");
		dto.setInterventoTempo("interventoTempo");
		dto.setCodiceTipoProcedimento("codiceTipoProcedimento");
		
		dto.getInterventoCaratterizzazione().add(new StringWrapper("interventoCaratterizzazione_1"));
		dto.getInterventoCaratterizzazione().add(new StringWrapper("interventoCaratterizzazione_2"));
		dto.getInterventoCaratterizzazione().add(new StringWrapper("interventoCaratterizzazione_3"));
		dto.getInterventoCaratterizzazione().add(new StringWrapper("interventoCaratterizzazione_4"));
		dto.getInterventoCaratterizzazione().add(new StringWrapper("interventoCaratterizzazione_5"));
		
		dto.getInterventoQualificazioneA().add(new StringWrapper("interventoQualificazioneA_1"));
		dto.getInterventoQualificazioneA().add(new StringWrapper("interventoQualificazioneA_2"));
		dto.getInterventoQualificazioneA().add(new StringWrapper("interventoQualificazioneA_3"));
		dto.getInterventoQualificazioneA().add(new StringWrapper("interventoQualificazioneA_4"));
		dto.getInterventoQualificazioneA().add(new StringWrapper("interventoQualificazioneA_5"));
		
		dto.getInterventoQualificazioneB().add(new StringWrapper("interventoQualificazioneB_1"));
		dto.getInterventoQualificazioneB().add(new StringWrapper("interventoQualificazioneB_2"));
		dto.getInterventoQualificazioneB().add(new StringWrapper("interventoQualificazioneB_3"));
		dto.getInterventoQualificazioneB().add(new StringWrapper("interventoQualificazioneB_4"));
		dto.getInterventoQualificazioneB().add(new StringWrapper("interventoQualificazioneB_5"));
		
		dto.getListaAllegati().add(new JasperAllegatoDTO(new Date(), "nome allegato 1    molto molto molto molto molto molto lungo 1", new ArrayList<String>(){{add("Tipo_1"); add("Tipo_2"); add("Tipo_3");}},""));
		dto.getListaAllegati().add(new JasperAllegatoDTO(new Date(), "nome allegato 2 \n molto molto molto molto molto molto lungo 2", new ArrayList<String>(){{add("Tipo_4"); add("Tipo_5"); add("Tipo_6");}},""));
		dto.getListaAllegati().add(new JasperAllegatoDTO(new Date(), "nome allegato 3    molto molto molto molto molto molto lungo 3", new ArrayList<String>(){{add("Tipo_1");								}},""));
		dto.getListaAllegati().add(new JasperAllegatoDTO(new Date(), "nome allegato 4    molto molto molto molto molto molto lungo 4", new ArrayList<String>(){{add("Tipo_1"); add("Tipo_2"); add("Tipo_3");add("Tipo_4");add("Tipo_5");			 	}},""));
		dto.getListaAllegati().add(new JasperAllegatoDTO(new Date(), "nome allegato 5    molto molto molto molto molto molto lungo 5", new ArrayList<String>(){{add("Tipo_0"); add("Tipo_2"); add("Tipo_4");}},""));
		
		dto.getListaAllegatiProvvedimento ().add(new JasperAllegatoDTO(new Date(), "nome allegato provvedimento 1 molto molto molto molto molto molto molto lungo 1", new ArrayList<String>(){{add("Tipo_Provv_1");}},""));
		dto.getListaAllegatiProvvedimento ().add(new JasperAllegatoDTO(new Date(), "nome allegato provvedimento 2 molto molto molto molto molto molto molto lungo 2", new ArrayList<String>(){{add("Tipo_Provv_2");}},""));
		
		dto.getListaAllegatiLocalizzazione().add(new JasperAllegatoDTO(new Date(), "nome allegato localizzazione 1 molto molto molto molto molto molto moltolungo 1", new ArrayList<String>(){{add("Tipo_Localizz_1");}},""));
		dto.getListaAllegatiLocalizzazione().add(new JasperAllegatoDTO(new Date(), "nome allegato localizzazione 2 molto molto molto molto molto molto moltolungo 2", new ArrayList<String>(){{add("Tipo_Localizz_2");}},""));
		dto.getListaAllegatiLocalizzazione().add(new JasperAllegatoDTO(new Date(), "nome allegato localizzazione 3 molto molto molto molto molto molto moltolungo 3", new ArrayList<String>(){{add("Tipo_Localizz_3");}},""));
	
		dto.setListaLocalizzazioni(new ArrayList<>(localizzazione()));
		
		dto.setCodiceApp("PARERI");
		
		dto.setDataDelibera(new Date());
		dto.setDeliberaNum("numero delibera 12345");
		dto.setOggettoDelibera("OGGETTO! - Lorem ipsum dolor sit amet, consectetur adipisci elit, sed do eiusmod tempor incidunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrum exercitationem ullamco laboriosam, nisi ut aliquid ex ea commodi consequatur. Duis aute irure reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint obcaecat cupiditat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
		dto.setInfoDeliberePrecedenti("INFO DELIBERE! - Lorem ipsum dolor sit amet, consectetur adipisci elit, sed do eiusmod tempor incidunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrum exercitationem ullamco laboriosam, nisi ut aliquid ex ea commodi consequatur. Duis aute irure reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint obcaecat cupiditat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
		dto.setDescrizioneIntervento("DESCRUZIONE! - Lorem ipsum dolor sit amet, consectetur adipisci elit, sed do eiusmod tempor incidunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrum exercitationem ullamco laboriosam, nisi ut aliquid ex ea commodi consequatur. Duis aute irure reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint obcaecat cupiditat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
		
		return Collections.singletonList(dto);
	}
	
	
	public static Collection<JasperLocalizzazioneInterventoDTO> localizzazione() {

		JasperLocalizzazioneInterventoDTO dto = new JasperLocalizzazioneInterventoDTO();
		
		dto.setIndirizzo("indirizzo");
		dto.setNumCivico("numCivico");
		dto.setPiano("piano");
		dto.setInterno("interno");
		dto.setDestUsoAtt("destUsoAtt");
		dto.setDestUsoProg("destUsoProg");
		dto.setComune("comune");
		dto.setSiglaProvincia("siglaProvincia");
		dto.setDataRiferimentoCatastale(new Date());
		dto.setStrade(true);
		
		dto.getListaImmobili().add(new StringWrapper("Codice AAAAAA - Immobile numero 1"));
		dto.getListaImmobili().add(new StringWrapper("Codice AAAAAA AAAAAAAAAA AAAAAAAAA AAAAAAAAAAAAA AAAAAAAAAAA AAAAAAAAAAA AAAAAAAAAAA AAAAAAAAA - Immobile numero 2"));
		dto.getListaImmobili().add(new StringWrapper("Codice AAAAAA - Immobile numero 3"));
		
		dto.getListaParchi()  .add(new StringWrapper("Codice AAAAAA - Parco numero 1"));
		dto.getListaParchi()  .add(new StringWrapper("Codice AAAAAA - Parco numero 2"));
		dto.getListaParchi()  .add(new StringWrapper("Codice AAAAAA - Parco numero 3"));
		
		dto.getListaPaesaggi().add(new StringWrapper("Codice AAAAAA - Paesaggio numero 1"));
		dto.getListaPaesaggi().add(new StringWrapper("Codice AAAAAA - Paesaggio numero 2"));
		dto.getListaPaesaggi().add(new StringWrapper("Codice AAAAAA - Paesaggio numero 3"));
		
		ParticelleCatastaliDTO partic1 = new ParticelleCatastaliDTO();
		partic1.setId(1);
		partic1.setLivello("livello 1");
		partic1.setFoglio("foglio 1");
		partic1.setSezione("sezione 1");
		partic1.setParticella("particella 1");
		partic1.setSub("subalterno 1");
		
		ParticelleCatastaliDTO partic2 = new ParticelleCatastaliDTO();
		partic2.setId(2);
		partic2.setLivello("livello 2");
		partic2.setFoglio("foglio 2");
		partic2.setSezione("sezione 2");
		partic2.setParticella("particella 2");
		partic2.setSub("subalterno 2");
		
		ParticelleCatastaliDTO partic3 = new ParticelleCatastaliDTO();
		partic3.setId(3);
		partic3.setLivello("livello 3");
		partic3.setFoglio("foglio 3");
		partic3.setSezione("sezione 3");
		partic3.setParticella("particella 3");
		partic3.setSub("subalterno 3");
		
		ParticelleCatastaliDTO partic4 = new ParticelleCatastaliDTO();
		partic4.setId(4);
		partic4.setLivello("livello 4");
		partic4.setFoglio("foglio 4");
		partic4.setSezione("sezione 4");
		partic4.setParticella("particella 4");
		partic4.setSub("subalterno 4");
		
		dto.getParticelle().add(partic1);
		dto.getParticelle().add(partic2);
		dto.getParticelle().add(partic3);
		dto.getParticelle().add(partic4);
		
		return Collections.singletonList(dto);
	}

	
	public static Collection<JasperRicercaFascicoliDTO> ricercaFascicoli() {

		JasperRicercaFascicoliDTO dto = new JasperRicercaFascicoliDTO();
		
		dto.setDipartimentoRegione("DIPARTIMENTO MOBILIT\u00c0, QUALIT\u00c0 URBANA, OPERE PUBBLICHE, ECOLOGIA E PAESAGGIO");
		dto.setIndirizzoRegione("Via Gentile, 52\n70126 Bari");
		dto.setPecRegione("PEC: servizio.ecologia@pec.rupar.puglia.it");
		dto.setSezioneRegione("SEZIONE AUTORIZZAZIONI AMBIENTALI");
		dto.setTelRegione("Tel. 080 5404726");
		
		JasperFascicoloDTO f1 = new JasperFascicoloDTO();
		f1.setCodiceFascicolo("AP-072006-888-2020");
		f1.setComune("- comune 1\n- comune con nome lungo 2\n-comune 3");
		f1.setDataProvvedimento("17/05/1985");
		f1.setDescrizione("descrizione completa e molto lunga dell'intervento svolto generalmente male!");
		f1.setEsito("esito dell'esito");
		f1.setEsitoVerifica("esito Verifica non");
		f1.setNumeroProvvedimento("numeroProvvedimento");
		f1.setResponsabileProcedimento("responsabile del Procedimento");
		f1.setStatoRegistrazione("stato della Registrazione");
		f1.setTipologiaIntervento("tipologia dell'Intervento molto molto lunga lunghissima");
		
		dto.getFascicoloDtoList().add(f1);
		dto.getFascicoloDtoList().add(f1);
		dto.getFascicoloDtoList().add(f1);
		
		return Collections.singletonList(dto);
	}
	
	
	public static Collection<RichiedenteDTO> richiedente() {

		RichiedenteDTO rich = new RichiedenteDTO();
		rich.setNome("@@@@@");
		rich.setCognome("@@@@@");
		rich.setCodiceFiscale("@@@@@");
		rich.setSesso("@");
		rich.setDataNascita(new Date());
		rich.setStatoNascita("Bosnia ed Erzegovina Tobagao");
		rich.setProvinciaNascita("Barletta-Andria-Trani");
		rich.setComuneNascita("Barletta##");
		rich.setStatoResidenza("Bosnia ed Erzegovina Tobagao");
		rich.setProvinciaResidenza("Barletta-Andria-Trani");
		rich.setComuneResidenza("San Valentino in Abruzzo Citeriore");
		rich.setViaResidenza("via celso ulpiani delle logge");
		rich.setNumeroResidenza("numeroResidenza");
		rich.setCap("70825");
		rich.setPec("@@@@@");
		rich.setEmail("@@@@@");
		rich.setTelefono("@@@@@");
		rich.setDitta(true);
		rich.setDittaSocieta("Carter&Carter association ditta...");
		rich.setDittaInQualitaDi("Rappresentante Legale");
		rich.setDittaCodiceFiscale("GGGGGGGGGGGGGGGG_");
		rich.setDittaPartitaIva("GGGGGGGGGGGGGGGG_");
		
		return Collections.singletonList(rich);
	}
	
	
	public static Collection<ResponsabileDTO> responsabile() {

		ResponsabileDTO respons = new ResponsabileDTO();
		respons.setNome("nome");
		respons.setCognome("cognome");
		respons.setInQualitaDi("inQualitaDi");
		respons.setServizioSettoreUfficio("servizioSettoreUfficio");
		respons.setPec("pec");
		respons.setMail("mail");
		respons.setTelefono("telefono");
		respons.setRiconoscimentoTipo(TipoDocRiconoscimento.CARTA_DI_IDENTITA);
		respons.setRiconoscimentoNumero("riconoscimentoNumero");
		respons.setRiconoscimentoDataRilascio(new Date());
		respons.setRiconoscimentoDataScadenza(new Date());
		respons.setRiconoscimentoRilasciatoDa("riconoscimentoRilasciatoDa");

		AllegatoCustomDTO allegato = new AllegatoCustomDTO();
		allegato.setNome("nome __");
		allegato.setDataCaricamento(new Date());
		allegato.setMimeType("Carta d'identità");
		respons.setListaAllegati(Collections.singletonList(allegato));
		
		return Collections.singletonList(respons);
	}

	
	public static Collection<JasperCampionamentoDTO> campionamento() {
		JasperCampionamentoDTO campionamento = new JasperCampionamentoDTO();
		return Collections.singletonList(campionamento);
	}
	
}