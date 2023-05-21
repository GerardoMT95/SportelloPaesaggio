package it.eng.tz.puglia.autpae.enumeratori;

import java.util.List;
import java.util.stream.Collectors;

import it.eng.tz.puglia.util.string.StringUtil;

public enum TipoAllegato {
	
	PROVVEDIMENTO_FINALE("Provvedimento Finale (Pubblico)","Provvedimento","Provvedimento"),
	PROVVEDIMENTO_FINALE_PRIVATO("Provvedimento Finale (Privato)","Provvedimento","Provvedimento"),
	PROVVEDIMENTO_FINALE_ESITO("Provvedimento Finale Esito Verifica","Provvedimento","Provvedimento"),
	PROVVEDIMENTO_FINALE_ESITO_LETTERA("Lettera Provvedimento Finale Esito Verifica","Campionamento","Campionamento"),
	PARERE_MIBAC("Parere MIBAC (Pubblico)","Provvedimento","Provvedimento"),
	PARERE_MIBAC_PRIVATO("Parere MIBAC (Privato)","Provvedimento","Provvedimento"),
	
	ISTANZA("Istanza di autorizzazione","Allegati","Allegati"),
	VERBALE("Verbale Conferenza Servizi","Allegati","Allegati"),
	PARERE("Parere/Verbale commissioni passaggio","Allegati","Allegati"),
	PREAVVISO("Preavviso di diniego autorizzazione","Allegati","Allegati"),
	ALTRI_PARERI("Altri pareri acquisiti dall'Ente","Allegati","Allegati"),
	ELABORATI("Elaborati di progetto","Allegati","Allegati"),
	PARERI_SCHEDA("Scheda conoscitiva del manufatto e del contesto rurale (rif.Capitolo 2 dell'elaborato del PPTR 4.4.6 - Linee guida per il recupero, la manutenzione acquisiti dall'Ente e il riuso dell'Edilizia e dei Beni Rurali)","Allegati","Allegati"),
	SCHEDA_PROGETTO("Scheda di progetto (rif. Capitolo 3 dell'elaborato del PPTR 4.4.6 - Linee guida per il recupero, la manutenzione e il riuso dell'Edilizia e dei Beni Rurali","Allegati","Allegati"),
	// usato in pareri (tab allegati)
	INTEGRAZIONI("Integrazioni documentali","Allegati","Allegati"),
	// usati in putt (tab allegati)
	ATTESTAZIONE_CONFORMITA("Attestazione di conformità urbanistica o relativa asseverazione","Allegati","Allegati"),
	RICHIESTA_SOPRINTENDENZA("Richiesta parere alla Soprintendenza","Allegati","Allegati"),
	PROPOSTA_PROVVEDIMENTO("Relazione tecnica illustrativa e proposta di provvedimento (DLGS 42/04 ART.146 C7 e DPR 139/10 ART.4 C6)","Allegati","Allegati"),
	DOCUMENTAZIONE_FOTOGRAFICA("Documentazione fotografica (se prodotta a parte da ''Elaborati di progetto'')","Allegati","Allegati"),
	RETTIFICA_AUTORIZZAZIONE("Rettifica autorizzazione paesaggistica","Allegati","Allegati"),
	ALTRO("Altro","Allegati","Allegati"),
	
	DOCUMENTO_RICONOSCIMENTO("Documento di riconoscimento del responsabile","DocRiconoscimento","DocRiconoscimento"), // usato in pareri (tab richiedente)
	LOCALIZZAZIONE("Area intervento SHAPE FILE - UTM33","ShapeFile","ShapeFile"),
	ULTERIORE_DOCUMENTAZIONE("Ulteriore Documentazione","UlterioreDocumentazione","UlterioreDocumentazione"),
	COMUNICAZIONE("Comunicazione","Corrispondenza",null),
	ESITO_CAMPIONAMENTO("Esito Campionamento","Campionamento","Campionamento"),
	ANTEPRIMA_TRASMISSIONE("Anteprima della ricevuta di Trasmissione","Trasmissione",null),		// ovvero è la  "RICEVUTA_TRASMISSIONE" ma NON 			 protocollata (nel .pdf NON è stampato il "numero_di_protocollo")
	RICEVUTA_TRASMISSIONE("Ricevuta di Trasmissione","Trasmissione","Trasmissione"),// ovvero è la "ANTEPRIMA_TRASMISSIONE" dopo che è stata protocollata (nel .pdf     è stampato il "numero_di_protocollo")
	
	CORRISPONDENZA_EML("EML della Corrispondenza inviata","Corrispondenza",null),
	RICEVUTA_EML("EML della Ricevuta","Corrispondenza/Ricevute",null),
	RICEVUTA_DATI_CERT("Dati Certificati della Ricevuta","Corrispondenza/Ricevute",null),
	RICEVUTA_SMIME("Firma digitale S/MIME della Ricevuta","Corrispondenza/Ricevute",null),
	
	IMAGE_TEMPLATE_DOC("Immagine o logo per template PDF","Configurazione",null), //utilizzata per la personalizzazione dei PDF generati da sistema NON presente a DB
	PDF_PRE_TIMBRATURA("Documento inviato a protocollazione","DocPreTimbratura",null),
	SCHEDA_CONOSCITIVA_MANUFATTO("Scheda conoscitiva del manufatto e del contensto rurale","Allegati","Allegati"), //rinvenuta solo su pratiche migrate
	SCHEDA_PROGETTO_MANUFATTO_RURALE("Scheda di progetto del manufatto rurale","Allegati","Allegati"),//rinvenuta solo su pratiche migrate
	COMUNICAZIONE_SOPRINTENDENZA_ESITO_NON_COMPLETATO("Comunicazione Soprintendenza Esito Non Completato","Allegati","Allegati"); //presente in putt migrate
	
	private String value;
	/**
	 * subpath relativo all'interno del folder del cms
	 */
	private String subPathCms;
	/**
	 * Attenzione!!!!! subPathDiogene è unico token perche finisce in un sub Fascicolo Reale
	 * null per indicare che questa tipologia non va in diogene
	 */
	private String subPathDiogene;
	
	private TipoAllegato(String text,String subPath,String subPathDiogene) {
		this.value = text;
		this.subPathCms =subPath;
		this.subPathDiogene =subPathDiogene;
	}
	
	public String getTextValue() {
		return value;
	}

	public String getSubPathCms() {
		return subPathCms;
	}
	
	public String getSubPathDiogene() {
		return subPathDiogene;
	}
	
/**
		 * 
	AUD05	AREA INTERVENTO SHAPE FILE - UTM33
	AUD0A	Altro
	SPD02	Comunicazione Soprintendenza Esito Non Completato
	AUTPAEM	DETERMINA AUTORIZZAZIONE PAESAGGISTICA (obbligatorio)
	AUD06	ELABORATI PROGETTUALI
	AUD07	Istanza di autorizzazione paesaggistica
	AUD18	Motivata proposta di accoglimento della domanda ai sensi del comma 6 art. 4 del Dpr 139/10
	PARSOP	PARERE DEFINITIVO MIBAC (D.Lgs. 42/04 art. 146 c.8 e DPR 139/10 art.4, c.6)
	AUD13	PARERE DEFINITIVO MIBAC (D.Lgs. 42/04 art. 146 c.8 e DPR 139/10 art.4, c.6)
	AUD04	PARERE SOPRINTENDENZA (se ex art. 146 - facoltativo)
	AUD02	PARERE/VERBALE COMMISSIONE PAESAGGIO
	AUD15	PROVVEDIMENTO FINALE DI AUTORIZZAZIONE PAESAGGISTICA
	AUD03	RELAZIONE ILLUSTRATIVA PER SOPRINTENDENZA (se ex art. 146- facoltativo)
	AUD01	VERBALE CONFERENZA SERVIZI
	AUD10	altri pareri
	AUD09	attestazione di conformità urbanistica o relativa asseverazione
	AUD08	documentazione fotografica (se prodotta a parte da elaborati progettuali)
	AUD12	preavviso di diniego Autorizzazione Paesaggistica
	AUD14	rettifica autorizzazione paesaggistica
	AUD11	"richiesta parere alla Soprintendenza, relazione tecnica illustrativa e proposta di provvedimento
	(D.Lgs. 42/04 art. 146 c.7 e DPR 139/10 art. 4 c.6)"
		 * @autor Adriano Colaianni
		 * @date 15 set 2021
		 * @param codice
		 * @return
		 */
		public static TipoAllegato fromCiviliaPuttCodiceTipoAllegato(String codice)
		{
			if(codice == null) return ALTRO;
			switch(codice.toLowerCase())
			{
			case "aud05" : return LOCALIZZAZIONE;
			case "spd02" : return COMUNICAZIONE_SOPRINTENDENZA_ESITO_NON_COMPLETATO;
			case "aud0a" : return ALTRO;
			case "autpaem" :
			case "aud15" : return PROVVEDIMENTO_FINALE;
			case "aud06" : return ELABORATI;
			case "aud07" : return ISTANZA;
			case "aud02" : return PARERE;
			case "aud13" :
			case "aud04" :
			case "parsop": return PARERE_MIBAC;
			
			case "aud08" : return DOCUMENTAZIONE_FOTOGRAFICA;
			case "aud09" : return ATTESTAZIONE_CONFORMITA;
			case "aud10" : return ALTRI_PARERI;
			case "aud03" :
			case "aud11" : return RICHIESTA_SOPRINTENDENZA;
			case "aud01" : return VERBALE;
			case "aud12" : return PREAVVISO;
			
			case "aud14" : return RETTIFICA_AUTORIZZAZIONE;
			
			default: return ALTRO;
			}
		}

/**
 * estrazione da dump:
 * 
 * --TIPI ALLEGATO PPTR
SELECT 
va.PPTR_TIPOALLEGATO_CODICE ,
TRANSLATE (va.PPTR_TIPOALLEGATO_DESCRIZIONE, 'x'||CHR(10)||CHR(13), 'x ') descrizione,
count(*) FROM 
VTNO_ALLEGATI_PPTR va,
INFO_AUT_PAES_PPTR_ALFA ii,
TNO_PPTR_PRATICA tp 
WHERE 
tp.CODICE_PRATICA_ENTEDELEGATO =ii.CODICE_PRATICA_ENTEDELEGATO 
AND va.CODICE =tp.CODICE_PRATICA_APPPTR 
AND NOT va.NOMEFILE IS NULL
AND tp.solotrasmissione=1
GROUP BY va.PPTR_TIPOALLEGATO_CODICE ,va.PPTR_TIPOALLEGATO_DESCRIZIONE
ORDER BY va.PPTR_TIPOALLEGATO_DESCRIZIONE ;
risultato (in alcuni casi c'è ASCII.LF in mezzo alla descrizione e quindi si va per codice):

70	Altri pareri acquisiti dall'ente
50	Altri pareri acquisiti dall'ente
80	Altri pareri acquisiti dall'ente
60	Altri pareri acquisiti dall'ente
500060	Altri pareri acquisiti dall'ente
600080	Altri pareri acquisiti dall'ente
46	Area Intervento Shp
76	Area Intervento Shp
56	Area Intervento Shp
66	Area Intervento Shp
500056	Area Intervento Shp
600076	Area Intervento Shp
82	Elaborati di progetto
84	Elaborati di progetto
83	Elaborati di progetto
81	Elaborati di progetto
85	Elaborati di progetto
500082	Elaborati di progetto
45	Istanza
600075	Istanza
75	Istanza
500055	Istanza
65	Istanza
55	Istanza
42	Parere Mibac
40	Parere Mibac
500040	Parere Mibac
38	Parere Mibac
58	Parere/Verbale commissioni paesaggio
78	Parere/Verbale commissioni paesaggio
68	Parere/Verbale commissioni paesaggio
48	Parere/Verbale commissioni paesaggio
600078	Parere/Verbale commissioni paesaggio
500058	Parere/Verbale commissioni paesaggio
69	Preavviso di diniego
49	Preavviso di diniego
79	Preavviso di diniego
600079	Preavviso di diniego
500059	Preavviso di diniego
41	Provvedimento finale
37	Provvedimento finale
43	Provvedimento finale
39	Provvedimento finale
600043	Provvedimento finale
500039	Provvedimento finale
142	Scheda conoscitiva del manufatto e del contensto rurale
122	Scheda conoscitiva del manufatto e del contensto rurale
112	Scheda conoscitiva del manufatto e del contensto rurale
89	Scheda conoscitiva del manufatto e del contesto rurale [rif. Capitolo 2 dell’ elaborato del PPTR 4.4.6 - Linee guida per il recupero, la manutenzione e il riuso dell’Edilizia e dei Beni Rurali] **
87	Scheda conoscitiva del manufatto e del contesto rurale [rif. Capitolo 2 dell’ elaborato del PPTR 4.4.6 - Linee guida per il recupero, la manutenzione e il riuso dell’Edilizia e dei Beni Rurali] **
86	Scheda conoscitiva del manufatto e del contesto rurale [rif. Capitolo 2 dell’ elaborato del PPTR 4.4.6 - Linee guida per il recupero, la manutenzione e il riuso dell’Edilizia e dei Beni Rurali] **
111	Scheda di progetto del manufatto rurale
121	Scheda di progetto del manufatto rurale
141	Scheda di progetto del manufatto rurale
100	Scheda di progetto [rif. Capitolo 3 dell’ elaborato del PPTR 4.4.6 - Linee guida per il recupero, la manutenzione e il riuso dell’Edilizia e dei Beni Rurali] **
101	Scheda di progetto [rif. Capitolo 3 dell’ elaborato del PPTR 4.4.6 - Linee guida per il recupero, la manutenzione e il riuso dell’Edilizia e dei Beni Rurali] **
102	Scheda di progetto [rif. Capitolo 3 dell’ elaborato del PPTR 4.4.6 - Linee guida per il recupero, la manutenzione e il riuso dell’Edilizia e dei Beni Rurali] **
103	Scheda di progetto [rif. Capitolo 3 dell’ elaborato del PPTR 4.4.6 - Linee guida per il recupero, la manutenzione e il riuso dell’Edilizia e dei Beni Rurali] **
600103	Scheda di progetto [rif. Capitolo 3 dell’ elaborato del PPTR 4.4.6 - Linee guida per il recupero, la manutenzione e il riuso dell’Edilizia e dei Beni Rurali] **
500101	Scheda di progetto [rif. Capitolo 3 dell’ elaborato del PPTR 4.4.6 - Linee guida per il recupero, la manutenzione e il riuso dell’Edilizia e dei Beni Rurali] **
600089	Scheda conoscitiva del manufatto e del contesto rurale [rif. Capitolo 2 dell’ elaborato del PPTR 4.4.6 - Linee guida per il recupero, la manutenzione e il riuso dell’Edilizia e dei Beni Rurali] **
500087	Scheda conoscitiva del manufatto e del contesto rurale [rif. Capitolo 2 dell’ elaborato del PPTR 4.4.6 - Linee guida per il recupero, la manutenzione e il riuso dell’Edilizia e dei Beni Rurali] **
47	Verbale Conferenza Servizi
77	Verbale Conferenza Servizi
67	Verbale Conferenza Servizi
500057	Verbale Conferenza Servizi
600077	Verbale Conferenza Servizi * 
 * 
 * 
 * @autor Adriano Colaianni
 * @date 27 apr 2021
 * @param textValue (codice t_tipodoc) 
 * @return
 */
	public static TipoAllegato fromCiviliaValue(String textValue)
	{
		switch(textValue)
		{
		case "70":
		case "50":
		case "80":
		case "60":
		case "500060":
		case "600080":
			return ALTRI_PARERI;
		case "46":
		case "76":
		case "56":
		case "66":
		case "500056":
		case "600076":
			return LOCALIZZAZIONE;
		case "82":
		case "84":
		case "83":
		case "81":
		case "85":
		case "500082":
			return ELABORATI;
		case "45":
		case "600075":
		case "75":
		case "500055":
		case "65":
		case "55":
			return ISTANZA;
		case "42":
		case "40":
		case "500040":
		case "38":	
			return PARERE_MIBAC;
		case "58":
		case "78":
		case "68":
		case "48":
		case "600078":
		case "500058":
			return PARERE;
		case "69":
		case "49":
		case "79":
		case "600079":
		case "500059":
			return PREAVVISO;
		case "41":
		case "37":
		case "43":
		case "39":
		case "600043":
		case "500039":
			return PROVVEDIMENTO_FINALE;
		case "142":
		case "122":
		case "112":
		case "89":
		case "87":
		case "86":
			return PARERI_SCHEDA;
		case "111":
		case "121":
		case "141":	
			return SCHEDA_PROGETTO_MANUFATTO_RURALE;
		case "100":
		case "101":
		case "102":
		case "103":
		case "600103":
		case "500101":
			return SCHEDA_PROGETTO;
		case "600089":
		case "500087":
			return PARERI_SCHEDA;
		case "47":
		case "77":
		case "67":
		case "500057":
		case "600077":
			return VERBALE;
		default:
			return null;
		}
	}
	
	public static TipoAllegato[] tipiProvvedimento() {
		return new TipoAllegato[] {
				TipoAllegato.PROVVEDIMENTO_FINALE, TipoAllegato.PARERE_MIBAC,
				TipoAllegato.PROVVEDIMENTO_FINALE_PRIVATO,
				TipoAllegato.PARERE_MIBAC_PRIVATO
		};
		 
	}

	/**
	 * restituisce la lista dei tipi supportati da diogene (che hanno un subPathDiogene non nullo)
	 * @autor Adriano Colaianni
	 * @date 19 ott 2021
	 * @return
	 */
	public static List<TipoAllegato> tipiAmmessiDiogene() {
		return 
				List.of(TipoAllegato.values())
				.stream()
				.filter(el->StringUtil.isNotEmpty(el.getSubPathDiogene())).collect(Collectors.toList());	
		 
	}
}
