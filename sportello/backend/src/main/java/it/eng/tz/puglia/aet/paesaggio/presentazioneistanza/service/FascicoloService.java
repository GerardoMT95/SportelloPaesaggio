package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.FascicoloDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.IpaEnteDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PresentaIstanzaDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.ValidInfoDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.allegati.DocumentoDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.istanza.AltroTitolareDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.istruttoria.ProtocolloDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.AttivitaDaEspletare;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions.CustomOperationToAdviceException;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.DestinatarioDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.IntegrazioneDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PagamentiMypayDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDelegatoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.PagamentiMypaySearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.PraticaSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.scheduler.bean.FascicoloStatoBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_email.SezioneIstruttoria;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.error.exception.CustomCmisException;
import it.eng.tz.puglia.service.registroimprese.bean.RegistroImpreseBean;


public interface FascicoloService extends IPraticaDataAwareService{

	FascicoloDto creaNuovaPratica(PraticaDTO pratica) throws Exception;
	FascicoloDto creaNuovaPratica(PraticaDTO pratica, PraticaDelegatoDTO delegato, MultipartFile delega) throws Exception;

	/**
	 * effettua check di competenza sull pratica in caso di RICHIEDENTE
	 * @author acolaianni
	 *
	 * @param fascicolo
	 * @return
	 * @throws Exception
	 */
	FascicoloDto find(FascicoloDto fascicolo) throws Exception;

	/**
	 * effettua un check di competenza sullo user se appartenente al gruppo RICHIEDENTI
	 * @author acolaianni
	 *
	 * @param filters
	 * @return
	 * @throws Exception
	 */
	FascicoloDto find(PraticaSearch filters) throws Exception;

	/**
	 * effettua check di competenza sullo user se appartenente al gruppo RICHIEDENTI
	 * @author acolaianni
	 *
	 * @param pk
	 * @return
	 * @throws Exception
	 */
	FascicoloDto update(FascicoloDto pk) throws Exception;
	
	FascicoloDto updateRichiedente(FascicoloDto pk) throws Exception;

	/**
	 * effettua check di competenza sullo user se appartenente al gruppo RICHIEDENTI
	 * @author acolaianni
	 *
	 * @param pk
	 * @return
	 * @throws Exception
	 */
	int delete(FascicoloDto pk) throws Exception;

	/**
	 * effettua check di competenza
	 * @author acolaianni
	 *
	 * @param pk
	 * @return
	 * @throws CustomOperationToAdviceException
	 */
	AltroTitolareDto creaTitolare(FascicoloDto pk) throws CustomOperationToAdviceException;

	/**
	 * restituice il numero di record cancellati ed effettua check di compentenza
	 * 
	 * @param pk
	 * @return
	 * @throws CustomCmisException
	 * @throws CustomOperationToAdviceException
	 */
	int eliminaTitolare(Long pk) throws CustomOperationToAdviceException, CustomCmisException;

	/**
	 * effettua check di competenza
	 * @author acolaianni
	 *
	 * @param codicePratica
	 * @return
	 * @throws Exception
	 */
	Boolean validaIstanza(String codicePratica) throws Exception;
	
	/**
	 * 
	 * @param codicePratica
	 * @return
	 * @throws Exception
	 */
	void validaRichiedente(String codicePratica) throws Exception;

	Integer updateValidation(UUID id, Boolean istanza, Boolean schedaTecnica, Boolean allegati)
			throws CustomOperationToAdviceException, Exception;

	List<DocumentoDto> getDichiarazioni(UUID idPratica) throws Exception;
	
	List<DocumentoDto> getDichiarazioni(UUID idPratica, boolean batch) throws Exception;

	void cambiaStato(UUID idPratica, AttivitaDaEspletare stato) throws Exception;
	
	void passaTrasmissione(UUID idPratica) throws Exception;

	AttivitaDaEspletare getStato(UUID idPratica) throws Exception;

	Map<String, Long> getCounterForIstruttoria(PraticaSearch filters) throws Exception;

	FascicoloDto findById(UUID praticaId) throws Exception;

	void setDataPresentazione(UUID idPratica) throws Exception;

	String protocollaFascicolo(UUID idPratica, boolean batch) throws Exception;

	PraticaDTO updateProtocolloFascicolo(ProtocolloDto protocollo) throws Exception;

	/**
	 *
	 * invio mail come da specifiche in analisi...... leggo template con sezione
	 * RIC_ISTANZA risolve placeholders e invia mail.... destinatari: indirizzo
	 * pec richiedente e indirizzi pec eventuali altri titolari
	 * 
	 * @author acolaianni
	 *
	 * @param praticaId
	 * @throws Exception
	 */
	void inviaNotificaRicezioneIstanza(UUID pratica) throws Exception;
	void inviaNotificaRicezioneIstanza(UUID pratica, String username, String ruolo, int enteDelegato) throws Exception;
	void inviaNotificaRicezioneIstanza(PraticaDTO pratica) throws Exception;
	void inviaNotificaRicezioneIstanza(PraticaDTO pratica, String username, String ruolo, int enteDelegato) throws Exception;

	public void inviaMailRichiedente(PraticaDTO pratica,
			SezioneIstruttoria sezioneTemplate,
			IntegrazioneDTO integrazioneDto,
			boolean toAltriTitolari,
			boolean toTecnico) throws Exception;
	
	public void inviaMailRichiedente(PraticaDTO pratica,
			SezioneIstruttoria sezioneTemplate,
			IntegrazioneDTO integrazioneDto,
			boolean toAltriTitolari,
			boolean toTecnico,
			String username,
			String ruolo,
			int enteDelegato) throws Exception;

	void addDestinatariPratica(List<DestinatarioDTO> destinatari, PraticaDTO pratica, boolean toAltriTitolari,
			boolean toTecnico) throws Exception;
	
	
	/**
	 * find della pratica pubblicata... controlla che esista nella vista pubblica...
	 * @author acolaianni
	 *
	 * @param codice
	 * @return
	 * @throws CustomOperationToAdviceException
	 */
	PraticaDTO findPraticaPubblica(String codice) throws CustomOperationToAdviceException;

	void controllaVincoli(UUID idPratica, ValidInfoDto dto, boolean applicaModifiche) throws Exception;

	/**
	 * effettua check di competenza
	 * @author acolaianni
	 *
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	FascicoloDto cambioEnteDelegatoPratica(FascicoloDto dto) throws Exception;

	/**
	 * effettua check di competenza
	 * @author acolaianni
	 *
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	FascicoloDto cambioTipoProcedimentoPratica(FascicoloDto dto) throws Exception;
	
	PraticaDTO findPraticaFromCorrispondenza(Long idCorrispondenza);

	/**
	 * rimuove eventuali istanza e scheda tecnica generati 
	 * @author acolaianni
	 *
	 * @param pratica
	 * @throws Exception
	 */
	void rimuoviEventualiDocumentiGenerati(PraticaDTO pratica) throws Exception;

	/**
	 * 
	 * @author acolaianni
	 *
	 * @param pratica
	 * @param importo in centesimi di euro
	 * @param causale
	 * @param retUrl pagina del front end dove c'è il componente che mostra i pagamenti della pratica
	 * @return
	 * @throws Exception
	 */
	PagamentiMypayDTO effettuaPagamento(PraticaDTO pratica, Double importo, String causale, String retUrl)
			throws Exception;
	
	/**
	 * se ci sono pagamenti a stato in corso, viene effettuata una call per effettuare il retreival dello stato
	 * @author acolaianni
	 *
	 * @param search
	 * @throws Exception
	 */
	void aggiornaEventualiStatiPagamento(PagamentiMypaySearch search) throws Exception;
	
	PaginatedList<PagamentiMypayDTO> searchPagamenti(PagamentiMypaySearch search) throws Exception;

	/**
	 * restituisce una mappa con stato e importo
	 * @author acolaianni
	 *
	 * @param pratica
	 * @return
	 * @throws Exception
	 */
	Map<String, BigDecimal> totPagato(PraticaDTO pratica) throws Exception;

	


	/**
	 * solo per le migrate.. non viene generato il codice_appptr etc...
	 * @author acolaianni
	 *
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	FascicoloDto creaNuovaPraticaDaMigrazione(PraticaDTO dto) throws Exception;

	/**
	 * utilizzata per la migrazione non effetua check sul proprietario della pratica.
	 * @author acolaianni
	 *
	 * @param praticaId
	 * @return
	 * @throws Exception
	 */
	FascicoloDto findByIdTxWriteWithoutCheck(UUID praticaId) throws Exception;

	/**
	 * usato per l'update solo in fase di migrazione NON effettua controlli
	 * @author acolaianni
	 *
	 * @param pratica
	 * @return
	 * @throws Exception
	 */
	int updatePraticaPerMigrazione(PraticaDTO pratica) throws Exception;

	/**
	 * non effettua i check di competenza sulla pratica, serve solo per la migrazione
	 * @author acolaianni
	 *
	 * @param pk
	 * @return
	 * @throws Exception 
	 */
	FascicoloDto updateWithoutCkeck(FascicoloDto pk) throws Exception;

	/**
	 * effettua check e se protocollazione automatica, protocolla...
	 * @author acolaianni
	 *
	 * @param idPratica
	 * @param codicePratica
	 * @return
	 * @throws Exception
	 */
	PresentaIstanzaDto presentaIstanza(UUID idPratica, String codicePratica) throws Exception;

	/**
	 * invia i documenti consolidati alla tabella coda diogene
	 * @author acolaianni
	 *
	 * @param fascicoloStatoBean
	 */
	void allineaDiogene(FascicoloStatoBean fascicoloStatoBean) throws Exception;
	
	/**
	 * Valida azienda e recupera eventualmente registro imprese
	 * @author Antonio La Gatta
	 * @date 9 giu 2022
	 * @param idPratica
	 * @param id
	 * @param codiceFiscale
	 * @return {@link RegistroImpreseBean} instance
	 * @throws Exception
	 */
	RegistroImpreseBean validaAzienda(String idPratica, Long id, String codiceFiscale) throws Exception;
	/**
	 * Valida ente
	 * @author Antonio La Gatta
	 * @date 9 giu 2022
	 * @param idPratica
	 * @param id
	 * @param codiceFiscale
	 * @return {@link IpaEnteDto}
	 * @throws Exception
	 */
	IpaEnteDto validaEnte(String idPratica, Long id, String codiceFiscale) throws Exception;

	/**
	 * aggiornamento flag esoneri bollo e oneri
	 * effettua solo check stato pratica
	 * sflagga l'ok su validazioneAllegati 
	 * @author acolaianni
	 *
	 * @param idPratica
	 * @param esoneroOneri
	 * @param esoneroBollo
	 * @return rows updated
	 * @throws Exception 
	 */
	int updateEsoneri(PraticaDTO idPratica, Boolean esoneroOneri, Boolean esoneroBollo,String userUpdated) throws Exception;
	
	/**
	 * validazione scheda allegati, 
	 * esoneroOneri=true oppure (se ente con pagamento attivo => deve essere stato chiuso il pagamento oppure esiste allegato con checkPagamento=RO) 
	 * in and con 
	 * esoneroBolli=true oppure esiste allegato con tipo_contenuto check_pagamento
	 * non effettua check su pratica di competenza ma solo su stato
	 * @author acolaianni
	 *
	 * @param praticaDTO
	 * @return true in caso di successo altrimento eccezione CustomOperationToAdviceException con messaggio di errore validazione
	 * @throws CustomOperationToAdviceException 
	 */
	Boolean validaAllegati(PraticaDTO praticaDTO) throws CustomOperationToAdviceException;

	/**
	 * spegne alcune date che vengono mostrate nella linea temporale nel caso di ETI
	 * @author acolaianni
	 *
	 * @param response
	 */
	void pulisciLineaTemporale(FascicoloDto response);
	
	
	/**
	 * non effettua check su utente loggato nel retrieve dei vari pezzi del fascicolo
	 * @author acolaianni
	 *
	 * @param codice
	 * @param withoutCheck
	 * @return
	 * @throws Exception
	 */
	FascicoloDto findByCodicePraticaAppptr(String codice, boolean withoutCheck) throws Exception;
	
	/**
	 * inserisce in scheduler invio degli allegati a diogene per tutte le sezioni, senza alcun controllo sulla pratica.
	 * @author acolaianni
	 * utilizzato su controller @see()
	 * @param idPratica
	 * @throws SQLException 
	 * @throws JsonProcessingException 
	 */
	void allineaDiogeneAllSezioni(UUID idPratica) throws JsonProcessingException, SQLException;
	
}
