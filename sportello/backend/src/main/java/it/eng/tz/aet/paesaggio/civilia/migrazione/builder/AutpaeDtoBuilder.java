package it.eng.tz.aet.paesaggio.civilia.migrazione.builder;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.AutPaesPptrLocalizInterv;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.EnteParcoAss;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.InteressePubblicoAss;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PaesaggioRuraleAss;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrCaratterizzazioneIntervento;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrInquadramentoIntervento;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrParticelleCatastali;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrQualificIntervento;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrStrutAntroStorCult;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrStrutEcosistemica;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrStrutIdrogeomorf;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrTipoIntervento;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.VtnoAllegatiPptr;
import it.eng.tz.aet.paesaggio.civilia.migrazione.exception.MigrationException;
import it.eng.tz.aet.paesaggio.civilia.migrazione.service.IMigratorService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.SelectOptionDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.istanza.UlterioriInformazioniDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica.CaratterizzazioneInterventoDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica.ConfigOptionValue;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Inquadramento;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.enums.CaratterizzazioneIntervento;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.enums.QualificazioneIntervento;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.enums.TipologiaIntervento;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.InquadramentoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.LocalizzazioneInterventoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ParticelleCatastaliDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PptrSelezioniDTO;
import it.eng.tz.puglia.util.list.ListUtil;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Classe di utility per popolare i bean richiesti dai repository di autpae 
 * partenendo dai bean popolati dai dati del db da migrare
 * 
 * @author Michele Santoro
 *
 */
public class AutpaeDtoBuilder
{

	private static final Logger log = LoggerFactory.getLogger(AutpaeDtoBuilder.class);
	
	final static String MIGRAZIONE_SUBPATH="MIGRAZIONE_AUTPAE";
	
	public static String localBasePath;
	
	/**
	 * Metodo che crea un {@link FascicoloDTO} e lo popola partendo da un oggetto 
	 * di tipo {@link InfoAutPaesPptrAlfa}
	 * @param bean
	 * @return oggetto popolato di tipo {@link FascicoloDTO}
	 */
//	public static FascicoloDTO buildFascicolo(InfoAutPaesPptrAlfa bean)
//	{
//		FascicoloDTO myBean = new FascicoloDTO();
//		buildFascicolo(myBean, bean);
//		return myBean;
//	}
//	/**
//	 * Metodo che popola un {@link FascicoloDTO} passato in input partendo da un oggetto 
//	 * di tipo {@link InfoAutPaesPptrAlfa}
//	 * @param myBean
//	 * @param extBean
//	 * @return oggetto popolato di tipo {@link FascicoloDTO}
//	 */
//	public static FascicoloDTO buildFascicolo(FascicoloDTO myBean, InfoAutPaesPptrAlfa extBean) 
//	{
//		myBean.setCodice(extBean.getCodicePraticaEnteDelegato());
//		myBean.setOggettoIntervento(extBean.getOggetto());
//		myBean.setRup(extBean.getResponsabileProvvedimento());
//		myBean.setNumeroProvvedimento(extBean.getNumeroProvvedimento());
//		myBean.setDataProtocollo(extBean.getDataProvvedimento());
//		myBean.setEsito(EsitoProvvedimento.fromString(extBean.getEsitoProvvedimento()));
//		myBean.setDataTrasmissione(extBean.getDataTrasmissione());
//		//myBean.setNote(extBean.getNote());
//		//...
//		return myBean;
//	}
//	
//	/**
//	 * Metodo che crea un {@link FascicoloDTO} e lo popola partendo da un oggetto 
//	 * di tipo {@link AutPaesPptrPratica}
//	 * @param bean
//	 * @return oggetto popolato di tipo {@link FascicoloDTO}
//	 */
//	public static FascicoloDTO buildFascicolo(AutPaesPptrPratica bean)
//	{
//		FascicoloDTO myBean = new FascicoloDTO();
//		buildFascicolo(myBean, bean);
//		return myBean;
//	}
//	/**
//	 * Metodo che popola un {@link FascicoloDTO} passato in input partendo da un oggetto
//	 * setta tipoProcedimento,DescrizioneIntervento,Note,sanatoria 
//	 * di tipo {@link AutPaesPptrPratica}
//	 * @param myBean
//	 * @param extBean
//	 * @return oggetto popolato di tipo {@link FascicoloDTO}
//	 */
//	public static FascicoloDTO buildFascicolo(FascicoloDTO myBean, AutPaesPptrPratica extBean)
//	{
//		myBean.setTipoProcedimento(fromLongToTipoProcediento(extBean.getTipoProcedimentoId()));
//		myBean.setDescrizioneIntervento(extBean.gettPraticaDescrizione());
//		myBean.setNote(extBean.getNote());
//		myBean.setSanatoria(extBean.getProvvedimentoInSanatoria().equals("1"));
//		return myBean;
//	}
//	
	/**
	 * Metodo che crea un {@link LocalizzazioneInterventoDTO} e lo popola partendo da un oggetto 
	 * di tipo {@link AutPaesPptrLocalizInterv}
	 * @param bean
	 * @return oggetto popolato di tipo {@link LocalizzazioneInterventoDTO}
	 */
	public static LocalizzazioneInterventoDTO buildLocalizzazione(AutPaesPptrLocalizInterv bean)
	{
		LocalizzazioneInterventoDTO myBean = new LocalizzazioneInterventoDTO();
		buildLocalizzazione(myBean, bean);
		return myBean;
	}
	/**
	 * Metodo che popola un {@link LocalizzazioneInterventoDTO} passato in input partendo da un oggetto 
	 * di tipo {@link AutPaesPptrLocalizInterv}
	 * @param myBean
	 * @param extBean
	 * @return oggetto popolato di tipo {@link LocalizzazioneInterventoDTO}
	 */
	public static LocalizzazioneInterventoDTO buildLocalizzazione(LocalizzazioneInterventoDTO myBean, AutPaesPptrLocalizInterv extBean)
	{
		//myBean.setComuneId(extBean.getLocalizIntervComuneId());//Da vedere
		myBean.setSiglaProvincia(extBean.getLocalizIntervProvincia());
		myBean.setComune(extBean.getComune());
		myBean.setDestUsoAtt(extBean.getLocalizIntervDestUsoAtt());
		myBean.setDestUsoProg(extBean.getLocalizIntervDestUsoProg());
		myBean.setIndirizzo(extBean.getLocalizIntervIndirizzo());
		myBean.setInterno(extBean.getLocalizIntervInterno());
		myBean.setNumCivico(extBean.getLocalizIntervNciv());
		myBean.setPiano(extBean.getLocalizIntervPiano());
		myBean.setDataRiferimentoCatastale(handleStringDate(extBean.getDataRiferimentoCatastale(), "dd/MM/yyyy"));
		myBean.setStrade(extBean.getStrade() != null && extBean.getStrade().equals("S"));
		return myBean;
	}
//	
//	/**
//	 * Metodo che crea un oggetto di tipo {@link ParticelleCatastaliDTO} e lo popola partendo da un oggetto 
//	 * di tipo {@link ParticelleCatastaliBase}
//	 * 
//	 * @param bean
//	 * @param idFascicolo
//	 * @param idComune
//	 * @return
//	 */
//	public static ParticelleCatastaliDTO buildParticelleCatastali(ParticelleCatastaliBase bean, Long idFascicolo, Long idComune)
//	{
//		ParticelleCatastaliDTO myBean = new ParticelleCatastaliDTO();
//		myBean.setFoglio(bean.getFoglio());
//		myBean.setParticella(bean.getParticella());
//		myBean.setLivello(bean.getLivello());
//		myBean.setSezione(bean.getSezione());
//		myBean.setSub(bean.getSub());
//		myBean.setCodCat(bean.getCodCat());
//		myBean.setPraticaId(idFascicolo);
//		myBean.setComuneId(idComune);
//		return myBean;
//	}
//	
//	/**
//	 * Metodo che crea una lista di {@link FascicoloInterventoDTO} e lo popola partendo da un oggetto 
//	 * di tipo {@link PptrQualificIntervento}
//	 * 
//	 * @param bean
//	 * @param tp
//	 * @param idFascicolo
//	 * @return lista di {@link FascicoloInterventoDTO}
//	 */
//	public static java.util.List<FascicoloInterventoDTO> buildQualificazioneIntervento(PptrQualificIntervento bean, TipoProcedimento tp, long idFascicolo)
//	{
//		java.util.List<FascicoloInterventoDTO> list = new java.util.ArrayList<>();
//		java.util.List<Field> fields = Arrays.asList(PptrQualificIntervento.class.getDeclaredFields());
//		long idTp = fromTipoProcedientoToLong(tp);
//		if(bean.getdLgs42146Rb01() != null && idTp == 1l)
//		{
//			switch(bean.getdLgs42146Rb01())
//			{
//			case "1": list.add(new FascicoloInterventoDTO(idFascicolo, 20l)); break;
//			case "2": list.add(new FascicoloInterventoDTO(idFascicolo, 21l)); break;
//			case "3": list.add(new FascicoloInterventoDTO(idFascicolo, 22l)); break;
//			}
//		}
//		
//		if(bean.getdLgs4291RbLieveEntita() != null)
//		{
//			if(idTp == 4)
//			{
//				switch(bean.getdLgs4291RbLieveEntita())
//				{
//				case "1": list.add(new FascicoloInterventoDTO(idFascicolo, 25l)); break;
//				case "2": list.add(new FascicoloInterventoDTO(idFascicolo, 26l)); break;
//				}
//			}
//			else if(idTp == 6)
//			{
//				switch(bean.getdLgs4291RbLieveEntita())
//				{
//				case "1": list.add(new FascicoloInterventoDTO(idFascicolo, 23l)); break;
//				case "2": list.add(new FascicoloInterventoDTO(idFascicolo, 24l)); break;
//				}
//			}
//		}
//		
//		if(idTp == 2)
//		{
//			for(Field f: fields)
//			{
//				f.setAccessible(true);
//				
//				if(f.getName().startsWith("dpr13991Chk") && check(f, bean, "1"))
//				{
//					try 
//					{ 
//						int n = Integer.parseInt(f.getName().subSequence(11, f.getName().length()).toString());
//						list.add(new FascicoloInterventoDTO(idFascicolo, 26l + n));
//					} catch(NumberFormatException e) {}
//				}
//			}
//		}
//		
//		if(idTp == 3)
//		{
//			if(bean.getdLgs42167ChkA() != null && bean.getdLgs42167ChkA().equals("1"))
//				list.add(new FascicoloInterventoDTO(idFascicolo, 110l));
//			if(bean.getdLgs42167ChkB() != null && bean.getdLgs42167ChkB().equals("1"))
//				list.add(new FascicoloInterventoDTO(idFascicolo, 111l));
//			if(bean.getdLgs42167ChkC() != null && bean.getdLgs42167ChkC().equals("1"))
//				list.add(new FascicoloInterventoDTO(idFascicolo, 112l));
//		}
//		
//		if(idTp == 4)
//		{
//			for(Field f: fields)
//			{
//				f.setAccessible(true);
//				
//				if(f.getName().startsWith("dpr13991Chk") && check(f, bean, "1"))
//				{
//					try 
//					{ 
//						int n = Integer.parseInt(f.getName().subSequence(11, f.getName().length()).toString());
//						list.add(new FascicoloInterventoDTO(idFascicolo, 126l + n));
//					} catch(NumberFormatException e) {}
//				}
//			}
//		}
//		
//		if(idTp == 5 || idTp == 6)
//		{
//			if(idTp == 5)
//			{
//				if(bean.getDpr3190Chk1() != null && bean.getDpr3190Chk1().equals("1"))
//					list.add(new FascicoloInterventoDTO(idFascicolo, 66l));
//				if(bean.getDpr3190Chk2() != null && bean.getDpr3190Chk2().equals("1"))
//					list.add(new FascicoloInterventoDTO(idFascicolo, 67l));
//			}
//			for(Field f: fields)
//			{
//				f.setAccessible(true);
//				
//				if(f.getName().startsWith("dpr3190Chk2_") && check(f, bean, "1"))
//				{
//					try 
//					{ 
//						int n = Integer.parseInt(f.getName().subSequence(12, f.getName().length()).toString());
//						list.add(new FascicoloInterventoDTO(idFascicolo, 67l + n));
//					} catch(NumberFormatException e) {}
//				}
//			}
//		}
//		
//		return list;
//	}
//	
//	/**
//	 * Metodo che popola un oggetto {@link FascicoloDTO} e lo popola partendo da un oggetto 
//	 * di tipo {@link PptrProvvedimento}
//	 * 
//	 * @param myBean
//	 * @param extBean
//	 * @return {@link FascicoloDTO}
//	 */
//	public static FascicoloDTO buildProvvedimento(FascicoloDTO myBean, PptrProvvedimento extBean)
//	{
//		myBean.setNumeroProvvedimento(extBean.getNumeroProvvedimento());
//		myBean.setEsito(fromStringToEsito(extBean.getFlagEsito()));
//		myBean.setDataRilascioAutorizzazione(extBean.getDataProvvedimento());
//		myBean.setRup(extBean.getRup());
//		return myBean;
//	}
//	
//	/**
//	 * Metodo che crea una lista di {@link ParchiPaesaggiImmobiliDTO} e lo popola partendo da lista di 
//	 * {@link EnteParcoAss}, {@link InteressePubblicoAss} e {@link PaesaggioRuraleAss}
//	 * 
//	 * @param idFascicolo
//	 * @param parchi
//	 * @param interessi
//	 * @param paesaggi
//	 * @return lista di {@link ParchiPaesaggiImmobiliDTO}
//	 */
//	public static List<ParchiPaesaggiImmobiliDTO> buildPrachiPaesaggiImmobili(Long idFascicolo,Long comuneId,
//																			  List<EnteParcoAss> parchi, 
//																			  List<InteressePubblicoAss> interessi, 
//																			  List<PaesaggioRuraleAss> paesaggi)
//	{
//		List<ParchiPaesaggiImmobiliDTO> list = new ArrayList<ParchiPaesaggiImmobiliDTO>();
//		//aggiungo parchi
//		if(parchi != null)
//		{
//			for(EnteParcoAss p: parchi)
//			{
//				ParchiPaesaggiImmobiliDTO entity = new ParchiPaesaggiImmobiliDTO();
//				entity.setPraticaId(idFascicolo);
//				entity.setDescrizione(p.getDescParco());
//				entity.setSelezionato(p.getEnteParcoTipoAssId() == 1 ? "S" : null);
//				entity.setCodice(p.getTnoEnteParcoMapId()+"");
//				entity.setTipoVincolo("P");
//				entity.setComuneId(comuneId);
//				list.add(entity);
//			}
//		}
//		//aggiungo immobili
//		if(interessi != null)
//		{
//			for(InteressePubblicoAss i: interessi)
//			{
//				ParchiPaesaggiImmobiliDTO entity = new ParchiPaesaggiImmobiliDTO();
//				entity.setPraticaId(idFascicolo);
//				entity.setDescrizione(i.getOggetto());
//				entity.setSelezionato(i.getInteressePubbTipoAssId() == 1 ? "S" : null);
//				entity.setCodice(i.getCodiceVincolo());
//				entity.setTipoVincolo("I");
//				entity.setComuneId(comuneId); 
//				list.add(entity);
//			}
//		}
//		//aggiungo paesaggi rurali
//		if(paesaggi != null)
//		{
//			for(PaesaggioRuraleAss r: paesaggi)
//			{
//				ParchiPaesaggiImmobiliDTO entity = new ParchiPaesaggiImmobiliDTO();
//				entity.setPraticaId(idFascicolo);
//				entity.setDescrizione(r.getDescPaesaggioRurale());
//				entity.setSelezionato(r.getPaesaggioRuraleTipoAssId() == 1 ? "S" : null);
//				entity.setTipoVincolo("R");
//				entity.setCodice(r.getTnoPaesaggiRuraliMapId()+"");
//				entity.setComuneId(comuneId); 
//				list.add(entity);
//			}
//		}
//		
//		return list;
//	}
//	
//	public static AllegatiWithMultipart buildAllegato(VtnoAllegatiPptr bean, TipoAllegato tipo) throws Exception
//	{
//		MultipartFile m = new MockMultipartFile(bean.getNomeFile(), bean.getBinContent().getBinaryStream());
//		AllegatiWithMultipart myBean = new AllegatiWithMultipart(m);
//		myBean.setNome(bean.getNomeAllegato());
//		myBean.setTipo(tipo);
//		myBean.setMimeType(bean.gettKeDocAttrValue());
//		myBean.setDimensione((int)m.getSize());
//		myBean.setDescrizione(bean.getDescrizione());
//		myBean.setDataProtocolloIn(bean.getDataProtocollo());//data protocollo out?
//		myBean.setTitolo(bean.getName());
//		myBean.setUsernameInserimento("MIGRAZIONE"); //Non è presente l'info...
//		myBean.setDataCaricamento(new java.util.Date()); //FIXME bean.getDataArrivo() type mismatch
//		return myBean;
//	}
//	
	private static java.util.Date handleStringDate(String strDate, String pattern)
	{
		java.util.Date d = null;
		if(StringUtil.isEmpty(strDate))
				return null;
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try
		{
			d = sdf.parse(strDate);
		}
		catch(ParseException e)
		{
			try 
			{
				Integer year = Integer.parseInt(strDate);
				if(year != null && year > 0)
				{
					sdf.applyPattern("dd/MM/yyyy");
					String strDate2 = "01/01/"+year;
					d = sdf.parse(strDate2);
				}
			} catch(NumberFormatException|ParseException ex) {}
		}
		return d;
	}
//	
//	private static TipoProcedimento fromLongToTipoProcediento(Long tpId)
//	{
//		switch(tpId.intValue())
//		{
//		case 1: return TipoProcedimento.AUT_PAES_ORD;
//		case 2: return TipoProcedimento.AUT_PAES_SEMPL_DPR_139_2010;
//		case 3: return TipoProcedimento.ACCERT_COMPAT_PAES_DLGS_42_2004;
//		case 4: return TipoProcedimento.ACCERT_COMPAT_PAES_DPR_139_2010;
//		case 5: return TipoProcedimento.AUT_PAES_SEMPL_DPR_31_2017;
//		case 6: return TipoProcedimento.ACCERT_COMPAT_PAES_DPR_31_2017;
//		default: return null;
//		}
//	}
//	
//	private static Long fromTipoProcedientoToLong(TipoProcedimento tp)
//	{
//		switch(tp)
//		{
//		case AUT_PAES_ORD: return 1l;
//		case AUT_PAES_SEMPL_DPR_139_2010: return 2l;
//		case ACCERT_COMPAT_PAES_DLGS_42_2004: return 3l;
//		case ACCERT_COMPAT_PAES_DPR_139_2010: return 4l;
//		case AUT_PAES_SEMPL_DPR_31_2017: return 5l;
//		case ACCERT_COMPAT_PAES_DPR_31_2017: return 6l;
//		default: return null;
//		}
//	}
//	
	private static boolean check(Field f, Object o, Object toCheck)
	{
		boolean checked = false;
		try { checked = f.get(o).equals(toCheck);}
		catch (Exception e) {}
		return checked;
	}
//	
//	private static EsitoProvvedimento fromStringToEsito(String esito)
//	{
//		if(esito == null) return null;
//		switch(esito.toUpperCase())
//		{
//		case "A": return EsitoProvvedimento.AUTORIZZATO;
//		case "N": return EsitoProvvedimento.NON_AUTORIZZATO;
//		case "P": return EsitoProvvedimento.AUT_CON_PRESCRIZ;
//		default: return null;
//		}
//	}
//	
//	public static void addProvvedimentoField(FascicoloDTO fascicolo,PptrProvvedimento extProvv,InfoAutPaesPptrAlfa infoPratica) throws ProvvedimentoException {
//		fascicolo.setNumeroProvvedimento(extProvv.getNumeroProvvedimento());
//		fascicolo.setDataRilascioAutorizzazione(extProvv.getDataProvvedimento());
//		fascicolo.setRup(extProvv.getRup());
//		fascicolo.setEsito(EsitoProvvedimento.fromCiviliaValue(extProvv.getFlagEsito()));
//		if(fascicolo.getEsito()==null) {
//			throw new ProvvedimentoException(infoPratica,"Esito provvedimento inatteso source:"+ extProvv.getFlagEsito(),null); 
//		}
//	}
//	
//	public static void addProvvedimentoAttachment(InfoAutPaesPptrAlfa infoPratica,
//			FascicoloDTO fascicolo,List<VtnoAllegatiPptr> allegatiProvvedimento,
//			AllegatoService allegatoService) 
//			throws ProvvedimentoException {
//		boolean hasProvv=false;
//		for(VtnoAllegatiPptr allegatoProvvedimento:allegatiProvvedimento) {
//			TipoAllegato tipo=null;
//			if(allegatoProvvedimento.getPptrTipoAllegatoDescrizione().equalsIgnoreCase("PARERE MIBAC")) {
//				tipo=TipoAllegato.PARERE_MIBAC;
//			}else if(allegatoProvvedimento.getPptrTipoAllegatoDescrizione().equalsIgnoreCase("PROVVEDIMENTO FINALE")) {
//				tipo=TipoAllegato.PROVVEDIMENTO_FINALE;
//				hasProvv=true;
//			} 
//			if(tipo!=null) {
//				try {
//					allegatoService.inserisciAllegatoDaMigrazione(fascicolo, Collections.singletonList(tipo), allegatoProvvedimento.getBinContent(),
//							allegatoProvvedimento.getNomeFile(), tipo.name(),allegatoProvvedimento.gettKeDocAttrValue(),
//							MIGRAZIONE_SUBPATH, localBasePath,
//							null, null, allegatoProvvedimento.getDataArrivo(),null);
//				} catch (Exception e) {
//					throw new ProvvedimentoException(infoPratica, "Errore durante l'elaborazione del file provvedimento " + tipo.name() + " " +allegatoProvvedimento.getNomeFile(), e);
//				}	
//			}
//		//scrivo nei repo tutte le info del file (compreso path su alfresco) e metto nel campo dell'idalfresco un placeholder
//		//nel frattempo scrivo i file in locale in una cartella che poi andro' ad uploadare su alfresco e tramite path
//		// del file su alfresco, sostituisco placeholder con id reale.
//		}
//		if(!hasProvv) {
//			throw new ProvvedimentoException(infoPratica, "Nessun file provvedimento ", null);
//		}
//	}
//	/**
//	 * popola codice_interno_ente ,	numero_interno_ente,protocollo ,data_protocollo 
//	 * @autor Adriano Colaianni
//	 * @date 23 apr 2021
//	 * @param fascicoloDTO
//	 * @param pptrEnte
//	 * @return
//	 */
//	public static FascicoloDTO buildFascicolo(FascicoloDTO fascicoloDTO, PptrCodiceInterno pptrEnte) {
//		fascicoloDTO.setCodiceInternoEnte(pptrEnte.getRifIntAlfanumerico());
//		fascicoloDTO.setNumeroInternoEnte(pptrEnte.getRifNumero());
//		fascicoloDTO.setProtocollo(pptrEnte.getRifProtocollo());
//		fascicoloDTO.setDataProtocollo(pptrEnte.getRifData());
//		return fascicoloDTO;
//	}
//	
//	//
//	
	public static String buildFascicoloTipoIntervento(PptrTipoIntervento bean) throws MigrationException
	{
		if(bean.getInterventiNoEdil()!=null && bean.getInterventiNoEdil().equals("1")) {
			return TipologiaIntervento.TIPO_INT_1.name();
		}
		if(bean.getManutenzione()!=null && bean.getManutenzione().equals("1")) {
			return TipologiaIntervento.TIPO_INT_2.name();
		}
		if(bean.getNuovaCostruzione()!=null && bean.getNuovaCostruzione().equals("1")) {
			return TipologiaIntervento.TIPO_INT_3.name();
		}
		if(bean.getRistEdilizia()!=null && bean.getRistEdilizia().equals("1")) {
			return TipologiaIntervento.TIPO_INT_4.name();
		}
		if(bean.getRistUrbanistica()!=null && bean.getRistUrbanistica().equals("1")) {
			return TipologiaIntervento.TIPO_INT_4.name();
		}
		return null;
	}
	
	public static CaratterizzazioneInterventoDto buildFascicoloCarattIntervento(PptrCaratterizzazioneIntervento bean) throws MigrationException
	{
		CaratterizzazioneInterventoDto ret=new CaratterizzazioneInterventoDto();
		ret.setCaratterizzazione(new ArrayList<>());
		/**
		 * CAR_INT_1("Rimessa in ripristino"),
	CAR_INT_2("Demolizione"),
	CAR_INT_3("Nuovi insediamenti in area urbana"),
	CAR_INT_4("Nuovi insediamenti rurali"),
	CAR_INT_5("Interventi su manufatti rurali in pietra a secco"),
	CAR_INT_6("Interventi su manufatti rurali non in pietra a secco"),
	CAR_INT_7("Nuovi insediamenti industriali e commerciali"),
	CAR_INT_8("Interventi su insediamenti industriali e commerciali"),
	CAR_INT_9("Recinzioni"),
	CAR_INT_10("Impianti per la produzione di energia rinnovabile"),
	CAR_INT_11("Linee telefoniche o elettriche"),
	CAR_INT_12("Infrastrutture primarie (viarie, acqua, gas, ecc.)"),
	CAR_INT_13("Miglioramenti fondiari"),
	CAR_INT_14("Altro:");
		 */
		if(bean.getRimessaInPristino() != null && bean.getRimessaInPristino().equals("1"))
			ret.getCaratterizzazione().add(new ConfigOptionValue(CaratterizzazioneIntervento.CAR_INT_1.name(),bean.getRimessaInPristinoDett()));
		if(bean.getDemolizione() != null && bean.getDemolizione().equals("1"))
			ret.getCaratterizzazione().add(new ConfigOptionValue(CaratterizzazioneIntervento.CAR_INT_2.name(),null));
		if(bean.getNuoviInsedAreaUrb() != null && bean.getNuoviInsedAreaUrb().equals("1"))
			ret.getCaratterizzazione().add(new ConfigOptionValue(CaratterizzazioneIntervento.CAR_INT_3.name(),null));
		if(bean.getNuoviInsedAreaRurali() != null && bean.getNuoviInsedAreaRurali().equals("1"))
			ret.getCaratterizzazione().add(new ConfigOptionValue(CaratterizzazioneIntervento.CAR_INT_4.name(),null));
		if(bean.getRistrManufRuraliSecco() != null && bean.getRistrManufRuraliSecco().equals("1"))
			ret.getCaratterizzazione().add(new ConfigOptionValue(CaratterizzazioneIntervento.CAR_INT_5.name(),null));
		if(bean.getRistrManufRuraliNoSecco() != null && bean.getRistrManufRuraliNoSecco().equals("1"))
			ret.getCaratterizzazione().add(new ConfigOptionValue(CaratterizzazioneIntervento.CAR_INT_6.name(),null));
		if(bean.getNuoviInsedIndCommerc() != null && bean.getNuoviInsedIndCommerc().equals("1"))
			ret.getCaratterizzazione().add(new ConfigOptionValue(CaratterizzazioneIntervento.CAR_INT_7.name(),null));
		if(bean.getRistrInsedIndCommerc() != null && bean.getRistrInsedIndCommerc().equals("1"))
			ret.getCaratterizzazione().add(new ConfigOptionValue(CaratterizzazioneIntervento.CAR_INT_8.name(),null));
		if(bean.getRecinzioni() != null && bean.getRecinzioni().equals("1"))
			ret.getCaratterizzazione().add(new ConfigOptionValue(CaratterizzazioneIntervento.CAR_INT_9.name(),null));
		if(bean.getImpiantiProduzioneEnergia() != null && bean.getImpiantiProduzioneEnergia().equals("1"))
			ret.getCaratterizzazione().add(new ConfigOptionValue(CaratterizzazioneIntervento.CAR_INT_10.name(),null));
		if(bean.getLineeTelefElettriche() != null && bean.getLineeTelefElettriche().equals("1"))
			ret.getCaratterizzazione().add(new ConfigOptionValue(CaratterizzazioneIntervento.CAR_INT_11.name(),null));
		if(bean.getInfrastrutturePrimarie() != null && bean.getInfrastrutturePrimarie().equals("1"))
			ret.getCaratterizzazione().add(new ConfigOptionValue(CaratterizzazioneIntervento.CAR_INT_12.name(),null));
		if(bean.getMiglioramentiFondiari() != null && bean.getMiglioramentiFondiari().equals("1"))
			ret.getCaratterizzazione().add(new ConfigOptionValue(CaratterizzazioneIntervento.CAR_INT_13.name(),null));
		if(bean.getAltro() != null && bean.getAltro().equals("1"))
			ret.getCaratterizzazione().add(new ConfigOptionValue(CaratterizzazioneIntervento.CAR_INT_14.name(),bean.getAltroSpecificare()));
		String tempPerm = bean.getTempPerm();
		if(StringUtil.isNotEmpty(tempPerm)) {
			switch (tempPerm) {
			case "T":
				ret.setDurata("T");
				break;
			case "R":
				ret.setDurata("F"); //Permanente rimovibile 
				break;	
			case "F":
				ret.setDurata("P");
				break;
			}	
		}
		return ret;
	}
	
	public static List<ConfigOptionValue> buildFascicoloQualifIntervento(PptrQualificIntervento bean,boolean isPresentata,int newTipoProcedimento) throws MigrationException
	{
		List<ConfigOptionValue> ret= new ArrayList<>();
		java.util.List<Field> fields = Arrays.asList(PptrQualificIntervento.class.getDeclaredFields());
		//ordinaria 1
		if(bean.getdLgs42146Rb01()!=null && bean.getdLgs42146Rb01().equalsIgnoreCase("1")){
			ret.add(new ConfigOptionValue(QualificazioneIntervento.DPCM_12_2005_1.name()));
		}
		if(bean.getdLgs42146Rb01()!=null && bean.getdLgs42146Rb01().equalsIgnoreCase("2")){
			ret.add(new ConfigOptionValue(QualificazioneIntervento.DPCM_12_2005_2.name()));
		}
		if(bean.getdLgs42146Rb01()!=null && bean.getdLgs42146Rb01().equalsIgnoreCase("3")){
			ret.add(new ConfigOptionValue(QualificazioneIntervento.DPCM_12_2005_3.name()));
		}
		//accertamento ordinaria tipo 3
		if(bean.getdLgs42167ChkA()!=null && bean.getdLgs42167ChkA().equalsIgnoreCase("1")){
			ret.add(new ConfigOptionValue(QualificazioneIntervento.DLGS_42_2004_1.name()));
		}
		if(bean.getdLgs42167ChkB()!=null && bean.getdLgs42167ChkB().equalsIgnoreCase("1")){
			ret.add(new ConfigOptionValue(QualificazioneIntervento.DLGS_42_2004_2.name()));
		}
		if(bean.getdLgs42167ChkC()!=null && bean.getdLgs42167ChkC().equalsIgnoreCase("1")){
			ret.add(new ConfigOptionValue(QualificazioneIntervento.DLGS_42_2004_3.name()));
		}
		//autorizzazione semplificata 31/2017
		if(bean.getDpr3190Chk1()!=null && bean.getDpr3190Chk1().equalsIgnoreCase("1")){
			//istanza di rinnovo
			ret.add(new ConfigOptionValue(QualificazioneIntervento.DPR_31_2017_1.name()));
		}
		if(bean.getDpr3190Chk2()!=null && bean.getDpr3190Chk2().equalsIgnoreCase("1")){
			//Le opere rientrano tra gli interventi di lieve entità di cui all'allegato B al d.P.R. 31/2017:
			ret.add(new ConfigOptionValue(QualificazioneIntervento.DPR_31_2017_2.name()));
		}
		if((newTipoProcedimento==5 || newTipoProcedimento==6) 
				&& bean.getdLgs4291RbLieveEntita()!=null &&
				bean.getdLgs4291RbLieveEntita().equalsIgnoreCase("1")){
			ret.add(new ConfigOptionValue("DPR_139_91_LIEVE_ENTITA_N"));
		}
		if((newTipoProcedimento==5 || newTipoProcedimento==6)
				&& bean.getdLgs4291RbLieveEntita()!=null 
				&& bean.getdLgs4291RbLieveEntita().equalsIgnoreCase("2")){
			ret.add(new ConfigOptionValue("DPR_139_91_LIEVE_ENTITA_S"));
		}
		if((newTipoProcedimento==4) 
				&& bean.getdLgs4291RbLieveEntita()!=null 
				&& bean.getdLgs4291RbLieveEntita().equalsIgnoreCase("1")){
			ret.add(new ConfigOptionValue(QualificazioneIntervento.QUAL_1.name()));
		}
		if((newTipoProcedimento==4) 
				&& bean.getdLgs4291RbLieveEntita()!=null 
				&& bean.getdLgs4291RbLieveEntita().equalsIgnoreCase("2")){
			ret.add(new ConfigOptionValue(QualificazioneIntervento.QUAL_2.name()));
		}
		//autorizz semplificata 2017 DPR_31_2017_XX shiftate di 2 posti rispetto alle loro 3..44
		for(Field f: fields)
		{
			f.setAccessible(true);
			if(f.getName().startsWith("dpr3190Chk2_") && check(f, bean, "1"))
			{
				try 
				{ 
					int n = Integer.parseInt(f.getName().subSequence(12, f.getName().length()).toString());
					ret.add(new ConfigOptionValue("DPR_31_2017_"+(n+2)));
				} catch(NumberFormatException e) {}
			}
		}
		//da capire se ci saranno da migrare pratiche con i procedimenti riferiti al 139/2010
		//if(!isPresentata) {
			for(Field f: fields)
			{
				f.setAccessible(true);
				if(f.getName().startsWith("dpr13991Chk") && check(f, bean, "1"))
				{
					try 
					{ 
						int n = Integer.parseInt(f.getName().subSequence(11, f.getName().length()).toString());
						ret.add(new ConfigOptionValue("DPR_139_91_"+(String.format("%02d", n))));
						//log.warn(IMigratorService.LOG_MIGRAZIONE_MARKER,"Errore, ritrovate opzioni di qualificazione intervento non ammesse nei tipi procedimento gestiti alla pratica con tPraticaApptrId "+bean.gettPraticaApptrId() + " bean "+bean);
					} catch(NumberFormatException e) {
						log.error(IMigratorService.LOG_MIGRAZIONE_MARKER,"Errore in conversione qualificazione , ritrovate opzioni di qualificazione intervento non ammesse nei tipi procedimento gestiti alla pratica con tPraticaApptrId "+bean.gettPraticaApptrId() + " campo:  "+f.getName());
					}
				}
			}	
		//}
		return ret;
	}
	
	
//	/**
//	 * @autor Adriano Colaianni
//	 * @date 26 apr 2021
//	 * @param referentiProgettoDto
//	 * @return
//	 */
//	public static RichiedenteDTO buildRichiedente(ReferentiProgettoDto referentiProgettoDto) {
//		RichiedenteDTO ret=new RichiedenteDTO();
//		ret.setNome(referentiProgettoDto.getRichiedenteNome());
//		ret.setCognome(referentiProgettoDto.getRichiedenteCognome());
//		ret.setCodiceFiscale(referentiProgettoDto.getRichiedenteCodiceFiscale());
//		//ret.setSesso(); 
//		ret.setStatoNascita(referentiProgettoDto.getStatoNascita());
//		ret.setProvinciaNascita(referentiProgettoDto.getProvNascita());
//		ret.setComuneNascita(referentiProgettoDto.getComuneNascita());
//		ret.setDataNascita(referentiProgettoDto.getDataNascita());
//		ret.setStatoResidenza(referentiProgettoDto.getStatoResidenza());
//		ret.setProvinciaResidenza(referentiProgettoDto.getProvResidenza());
//		ret.setComuneResidenza(referentiProgettoDto.getComuneResidenza());
//		ret.setViaResidenza(referentiProgettoDto.getIndirizzo());
//		ret.setNumeroResidenza(referentiProgettoDto.getNumCivico());
//		ret.setCap(referentiProgettoDto.getCap());
//		ret.setTelefono(referentiProgettoDto.getTelFisso());
//		if(StringUtil.isNotEmpty(referentiProgettoDto.getDittaRuoloRich())){
//			ret.setDitta(true);
//			ret.setDittaCodiceFiscale(referentiProgettoDto.getDittaCodiceFiscale());
//			ret.setDittaPartitaIva(referentiProgettoDto.getDittaPartitaIva());
//			ret.setDittaSocieta(referentiProgettoDto.getDittaRagioneSociale());
//			ret.setDittaInQualitaDi(Ditta.ALTRO.name());
//			ret.setDittaQualitaAltro(referentiProgettoDto.getDittaRuoloRich());
//		}
//		ret.setEmail(referentiProgettoDto.getIndirizzoMail());
//		ret.setPec(referentiProgettoDto.getIndirizzoPec());
//		return ret;
//	}
//	
	/**
	 * mapping particelle
	 * @autor Adriano Colaianni
	 * @date 27 apr 2021
	 * @param listaOld
	 * @param locIntNew
	 * @return
	 */
	public static List<ParticelleCatastaliDTO> buildLocalizzazioneParticelle(List<PptrParticelleCatastali> listaOld,LocalizzazioneInterventoDTO locIntNew)
	{
		//myBean.setComuneId(extBean.getLocalizIntervComuneId());//Da vedere
		List<ParticelleCatastaliDTO> listaNew=new ArrayList<>();
		if(ListUtil.isNotEmpty(listaOld)) {
			listaOld.forEach(particellaOld->{
				ParticelleCatastaliDTO particellaNew=new ParticelleCatastaliDTO();
				particellaNew.setComuneId(locIntNew.getComuneId());
				particellaNew.setPraticaId(locIntNew.getPraticaId());
				particellaNew.setSezione(particellaOld.getSezione());
				particellaNew.setLivello(particellaOld.getLivello());
				particellaNew.setFoglio(particellaOld.getFoglio());
				particellaNew.setParticella(particellaOld.getParticella());
				particellaNew.setSub(particellaOld.getSub());
				particellaNew.setDescrSezione(particellaOld.getNomeSezione());
				particellaNew.setCodCat(particellaOld.getCodCat());
				listaNew.add(particellaNew);
			});	
		}
		return listaNew;
	}
//	
//	
//	/**
//	 * 
//	 * @autor Adriano Colaianni
//	 * @date 27 apr 2021
//	 * @param infoPratica
//	 * @param fascicolo
//	 * @param allegati
//	 * @param allegatoService
//	 * @throws ProvvedimentoException
//	 */
//	public static void addAttachment(InfoAutPaesPptrAlfa infoPratica,
//			FascicoloDTO fascicolo,List<VtnoAllegatiPptr> allegati,
//			AllegatoService allegatoService) 
//			throws ProvvedimentoException {
//		for(VtnoAllegatiPptr allegato:allegati) {
//			Map<String, List<VtnoAllegatiPptr>> mappaContenuti = VtnoAllegatiPptr.getMapAllegatiFacoltativi_byKey_tnTKeDocStId(allegati);
//			List<TipoAllegato> tipi=new ArrayList<>();
//			//sono raggruppati per file
//			Set<String> files = mappaContenuti.keySet();
//			files.forEach(file->{
//				List<VtnoAllegatiPptr> listaContenuti = mappaContenuti.get(file);
//				listaContenuti.forEach(contenuto->{
//					TipoAllegato tipo = TipoAllegato.fromCiviliaValue(contenuto.getPptrTipoAllegatoDescrizione());
//					if(tipo==null) {
//						log.error("TipoAllegato non mappato: "+contenuto.getPptrTipoAllegatoDescrizione());
//					}else {
//						tipi.add(tipo);
//					}
//				});
//			});
//			if(ListUtil.isNotEmpty(tipi)) {
//				try {
//					allegatoService.inserisciAllegatoDaMigrazione(fascicolo, tipi, allegato.getBinContent(),
//							allegato.getNomeFile(), tipi.get(0).name(),allegato.gettKeDocAttrValue(),
//							MIGRAZIONE_SUBPATH, localBasePath,
//							null, null, allegato.getDataArrivo(),allegato.getnTKeDocStId());
//				} catch (Exception e) {
//					throw new ProvvedimentoException(infoPratica, "Errore durante l'elaborazione del'allegato " + tipi.get(0).name() + " " +allegato.getNomeFile(), e);
//				}	
//			}
//		}
//		
//	}
//	
//	public static void inserisciRicevutaTrasmissione(PptrProtocolloUscita ricevuta,AllegatoService allegatoSvc,FascicoloDTO fascicoloDTO) throws SQLException, Exception {
//		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
//		final StringBuilder sb=new StringBuilder();
//		sb
//		.append("/")
//		.append(ricevuta.getTitolarioProtocollo())
//		.append(sdf.format(ricevuta.getDataprotocollo()))
//		.append("/")
//		.append(ricevuta.getNumeroProtocollo());
//		allegatoSvc.inserisciAllegatoDaMigrazione(fascicoloDTO, Collections.singletonList(TipoAllegato.RICEVUTA_TRASMISSIONE),
//				ricevuta.getBinPdfContent().length()>ricevuta.getBinPdfProtContent().length()?
//						ricevuta.getBinPdfContent():ricevuta.getBinPdfProtContent(),
//						TipoAllegato.RICEVUTA_TRASMISSIONE.getTextValue().replaceAll(" ", "_").concat(".pdf"),
//						TipoAllegato.RICEVUTA_TRASMISSIONE.getTextValue(), 
//						"application/pdf",
//				MIGRAZIONE_SUBPATH, localBasePath,
//				sb.toString(), ricevuta.getDataprotocollo(), ricevuta.getDataprotocollo(),ricevuta.getTnoPptrTrasmissioneId()+"");
//		
//		allegatoSvc.inserisciAllegatoDaMigrazione(fascicoloDTO, Collections.singletonList(TipoAllegato.ANTEPRIMA_TRASMISSIONE),
//				ricevuta.getBinPdfContent(),
//						"Segnatura_xml_protocollo.xml",
//						TipoAllegato.ANTEPRIMA_TRASMISSIONE.getTextValue(), 
//						"text/xml",
//				MIGRAZIONE_SUBPATH, localBasePath,
//				null, null, ricevuta.getDataprotocollo(),ricevuta.getTnoPptrTrasmissioneId()+"");
//	}
//	
//	public static AllegatoDTO inserisciAllegatoMail(CiviliaMailAllegati allegato,AllegatoService allegatoSvc,FascicoloDTO fascicoloDTO,Date dataMail,TipoAllegato tipoAllegato) throws SQLException, Exception {
//		StringBuilder contentType=new StringBuilder();
//		if(ListUtil.isNotEmpty(allegato.getAttributi())) {
//			allegato.getAttributi().stream().filter(attr->attr.getName().equalsIgnoreCase("FORMATO")).findAny().ifPresent(attr->contentType.append(attr.getValue()));
//		}
//		if(contentType.length()==0) {
//			contentType.append("application/binary"); //default generico...
//		}
//		AllegatoDTO ret = allegatoSvc.inserisciAllegatoDaMigrazione(fascicoloDTO, Collections.singletonList(tipoAllegato),
//				allegato.getBinContent(),
//						allegato.getNomeFile(), 
//						tipoAllegato.getTextValue(),
//						contentType.toString(),
//				MIGRAZIONE_SUBPATH, localBasePath,
//				null, null, dataMail,allegato.gettKeDocId()+"");
//		return ret;
//	}
	public static UlterioriInformazioniDto buildUlterioriInformazioniDto(UUID id, Long idComuneNew,
			List<EnteParcoAss> parchi, List<InteressePubblicoAss> interessi,
			List<PaesaggioRuraleAss> paesaggi) {
		UlterioriInformazioniDto ret=new UlterioriInformazioniDto();
		ret.setBpImmobileAreePubblico(new ArrayList<>());
		ret.setBpImmobileAreePubblicoOptions(new ArrayList<>());
		ret.setBpParchiEReserve(new ArrayList<>());
		ret.setBpParchiEReserveOptions(new ArrayList<>());
		ret.setUcpPaesaggioRurale(new ArrayList<>());
		ret.setUcpPaesaggioRuraleOptions(new ArrayList<>());
		//aggiungo parchi
		if(parchi != null)
		{
			for(EnteParcoAss p: parchi)
			{
				if(p.getEnteParcoTipoAssId() == 1 ) {
					ret.getBpParchiEReserve().add(p.getTnoEnteParcoMapId()+"");
				}
				SelectOptionDto opzione = new SelectOptionDto();
				opzione.setValue(p.getTnoEnteParcoMapId()+"");
				opzione.setDescription(p.getDescParco());
				opzione.setLinked(p.getMail());
				ret.getBpParchiEReserveOptions().add(opzione);
			}
		}
		//aggiungo immobili
		if(interessi != null)
		{
			for(InteressePubblicoAss i: interessi)
			{
				if(i.getInteressePubbTipoAssId() == 1 ) {
					ret.getBpImmobileAreePubblico().add(i.getCodiceVincolo());
				}
				SelectOptionDto opzione = new SelectOptionDto();
				opzione.setValue(i.getCodiceVincolo());
				opzione.setDescription(i.getOggetto());
				opzione.setLinked(null);
				ret.getBpImmobileAreePubblicoOptions().add(opzione);
			}
		}
		//aggiungo paesaggi rurali
		if(paesaggi != null)
		{
			for(PaesaggioRuraleAss r: paesaggi)
			{
				if(r.getPaesaggioRuraleTipoAssId() == 1) {
					ret.getUcpPaesaggioRurale().add(r.getTnoPaesaggiRuraliMapId()+"");
				}
				SelectOptionDto opzione = new SelectOptionDto();
				opzione.setValue(r.getTnoPaesaggiRuraliMapId()+"");
				opzione.setDescription(r.getDescPaesaggioRurale());
				opzione.setLinked(null);
				ret.getUcpPaesaggioRuraleOptions().add(opzione);
			}
		}
		return ret;
	}
	
	public static void convertiInquadramento(PptrInquadramentoIntervento inqOld,InquadramentoDTO inqNew) {
		inqNew.setInqContestoPaesagAltro(inqOld.getInqContestoPaesagAltro());
		if(StringUtil.isNotEmpty(inqOld.getInqContestoPaesag())){
			Inquadramento newVal = Inquadramento.fromCivilia(inqOld.getInqContestoPaesag());
			if(newVal!=null) {
				inqNew.setInqContestoPaesag(newVal.name());	
			}else {
				inqNew.setInqContestoPaesag(Inquadramento.ALTRO.name());
				inqNew.setInqContestoPaesagAltro(inqOld.getInqContestoPaesag());
			}
		}
		inqNew.setInqDestUsoIntervAltro(inqOld.getInqDestUsoIntervAltro());
		if(StringUtil.isNotEmpty(inqOld.getInqDestUsoInterv())){
			Inquadramento newVal = Inquadramento.fromCivilia(inqOld.getInqDestUsoInterv());
			if(newVal!=null) {
				inqNew.setInqDestUsoInterv(newVal.name());	
			}else {
				inqNew.setInqDestUsoInterv(Inquadramento.ALTRO.name());
				inqNew.setInqDestUsoIntervAltro(inqOld.getInqDestUsoInterv());
			}
		}
		inqNew.setInqDestUsoIntervAltro(inqOld.getInqUsoSuoloAltro());
		if(StringUtil.isNotEmpty(inqOld.getInqUsoSuolo())){
			Inquadramento newVal = Inquadramento.fromCivilia(inqOld.getInqUsoSuolo());
			if(newVal!=null) {
				inqNew.setInqDestUsoSuolo(newVal.name());	
			}else {
				inqNew.setInqDestUsoSuolo(Inquadramento.ALTRO.name());
				inqNew.setInqDestUsoSuoloAltro(inqOld.getInqUsoSuolo());
			}
		}
		inqNew.setInqMorfologiaPaesagAltro(inqOld.getInqMorfologiaPaesagAltro());
		if(StringUtil.isNotEmpty(inqOld.getInqMorfologiaPaesag())){
			Inquadramento newVal = Inquadramento.fromCivilia(inqOld.getInqMorfologiaPaesag());
			if(newVal!=null) {
				inqNew.setInqMorfologiaPaesag(newVal.name());	
			}else {
				inqNew.setInqMorfologiaPaesag(Inquadramento.ALTRO.name());
				inqNew.setInqMorfologiaPaesagAltro(inqOld.getInqMorfologiaPaesag());
			}
		}
	}
	
	
	private static boolean pptrChecked(String campo) {
		return StringUtil.isNotEmpty(campo) && campo.equalsIgnoreCase("s");
	}
	
	private static PptrSelezioniDTO buildPptrSelezione(UUID idPratica,String codice,String sezione) {
		PptrSelezioniDTO ret=new PptrSelezioniDTO();
		ret.setIdPratica(idPratica);
		ret.setSezione(sezione);
		ret.setCodice(codice);
		return ret;
	}
	
	public static List<PptrSelezioniDTO> buildPptrSelezionis(List<PptrStrutAntroStorCult> pptrSats,
			List<PptrStrutEcosistemica> pptrEcos, List<PptrStrutIdrogeomorf> pptrIgms,UUID idPratica) {
		List<PptrSelezioniDTO> ret=new ArrayList<PptrSelezioniDTO>();
		if(ListUtil.isNotEmpty(pptrSats)) {
			PptrStrutAntroStorCult pptrSat = pptrSats.get(0);
			if(pptrChecked(pptrSat.getCultBpInteresseArcheo())){
				PptrSelezioniDTO dto = buildPptrSelezione(idPratica, "ZONE_INT_ARCH", "art_142");
				ret.add(dto);
			} 
			if(pptrChecked(pptrSat.getCultBpInteressePubb())){
				PptrSelezioniDTO dto = buildPptrSelezione(idPratica, "AREE_NOTEVOLE_INSTERESSE_PUBB", "art_136");
				ret.add(dto);
			}
			//pptrSat.getCultBpInteressePubbSpecif(); gestita dalla localizzazione
			if(pptrChecked(pptrSat.getCultBpUsiCivici())){
				PptrSelezioniDTO dto = buildPptrSelezione(idPratica, "ZONE_USI_CIVICI", "art_142");
				ret.add(dto);
			}
			if(pptrChecked(pptrSat.getCultUcpCittaConsolidata())) {
				PptrSelezioniDTO dto = buildPptrSelezione(idPratica, "CITTA_CONSOLIDATA", "art_143");
				ret.add(dto);
			}
			if(pptrChecked(pptrSat.getCultUcpConiVisuali())){
				PptrSelezioniDTO dto = buildPptrSelezione(idPratica, "CONI_VISUALI", "art_143");
				ret.add(dto);
			}
			if(pptrChecked(pptrSat.getCultUcpPaesagRurali())) {
				PptrSelezioniDTO dto = buildPptrSelezione(idPratica, "PAES_RUR", "art_143");
				ret.add(dto);
			}
			if(pptrChecked(pptrSat.getCultUcpPuntiPanoramici())) {
				PptrSelezioniDTO dto = buildPptrSelezione(idPratica, "LUOG_PAN", "art_143");
				ret.add(dto);
			}
			if(pptrChecked(pptrSat.getCultUcpRispComponInsediat())) {
				PptrSelezioniDTO dto = buildPptrSelezione(idPratica, "AREE_RISP_COMP_CULT", "art_143");
				ret.add(dto);
			}
			if(pptrChecked(pptrSat.getCultUcpStradePanoramiche())) {
				PptrSelezioniDTO dto = buildPptrSelezione(idPratica, "STRADE_PANORAMICHE", "art_143");
				ret.add(dto);
			}
			if(pptrChecked(pptrSat.getCultUcpStratInsedArcheo())) {
				PptrSelezioniDTO dto = buildPptrSelezione(idPratica, "TEST_STRAT_INS_1", "art_143");
				dto.setSpecifica(pptrSat.getCultUcpStratInsedArcSpec());
				ret.add(dto);
			}
			if(pptrChecked(pptrSat.getCultUcpStratInsedRiskArc())) {
				PptrSelezioniDTO dto = buildPptrSelezione(idPratica, "TEST_STRAT_INS_3", "art_143");
				dto.setSpecifica(pptrSat.getCultUcpStrInsRiskArcSpec());
				ret.add(dto);
			}
			if(pptrChecked(pptrSat.getCultUcpStratReteTratturi())){
				PptrSelezioniDTO dto = buildPptrSelezione(idPratica, "TEST_STRAT_INS_2", "art_143");
				dto.setSpecifica(pptrSat.getCultUcpStratTratturiSpecif());
				ret.add(dto);
			}
			if(pptrChecked(pptrSat.getPercUcpStradeValenzaPaesag())){
				PptrSelezioniDTO dto = buildPptrSelezione(idPratica, "STRADE_VAL_PAES", "art_143");
				ret.add(dto);
			};
		}
		if(ListUtil.isNotEmpty(pptrEcos)) {
			PptrStrutEcosistemica pptrEco = pptrEcos.get(0);
			if(pptrChecked(pptrEco.getBotBpBoschi())) {
				PptrSelezioniDTO dto = buildPptrSelezione(idPratica, "BOSCHI", "art_142");
				ret.add(dto);
			}
			if(pptrChecked(pptrEco.getBotBpZoneUmideRamsar())){
				PptrSelezioniDTO dto = buildPptrSelezione(idPratica, "ZONE_UMIDE_RAMSAR", "art_142");
				dto.setSpecifica(pptrEco.getBotBpZUmideRamsarSpecif());
				ret.add(dto);
				
			};
			if(pptrChecked(pptrEco.getBotUcpAreeRispBoschi())) {
				PptrSelezioniDTO dto = buildPptrSelezione(idPratica, "AREE_DI_RISPETTO_DEI_BOSCHI", "art_143");
				ret.add(dto);
			}
			if(pptrChecked(pptrEco.getBotUcpAreeUmide())) {
				PptrSelezioniDTO dto = buildPptrSelezione(idPratica, "AREE_UMIDE", "art_143");
				dto.setSpecifica(pptrEco.getBotUcpAreeUmideSpecif());
				ret.add(dto);
			}
			if(pptrChecked(pptrEco.getBotUcpFormArbustive())) {
				PptrSelezioniDTO dto = buildPptrSelezione(idPratica, "FORMAZIONI_ARBUSTIVE", "art_143");
				ret.add(dto);
			}
			if(pptrChecked(pptrEco.getBotUcpPratiPascoli())) {
				PptrSelezioniDTO dto = buildPptrSelezione(idPratica, "PRATI_E_PASCOLI", "art_143");
				ret.add(dto);
			}
			if(pptrChecked(pptrEco.getNatUcpAreaRispParchi())) {
				PptrSelezioniDTO dto = buildPptrSelezione(idPratica, "AREE_DI_RISPETTO:DEI_PARCHI", "art_143");
				ret.add(dto);
			}
			if(pptrChecked(pptrEco.getNatUcpSitiRilNatural())) {
				PptrSelezioniDTO dto = buildPptrSelezione(idPratica, "SITI_RIL_NAT", "art_143");
				dto.setSpecifica(pptrEco.getNatUcpSitiRilNaturSpecif());
				ret.add(dto);
			}
		}
		if(ListUtil.isNotEmpty(pptrIgms)) {
			 PptrStrutIdrogeomorf pptrIgm = pptrIgms.get(0);
			 if(pptrChecked(pptrIgm.getGeoUcpCordoniDunari())) {
				PptrSelezioniDTO dto = buildPptrSelezione(idPratica, "CORDONI_DUNARI", "art_143");
				ret.add(dto);
			 }
			 if(pptrChecked(pptrIgm.getGeoUcpDoline())) {
				PptrSelezioniDTO dto = buildPptrSelezione(idPratica, "DOLINE", "art_143");
				ret.add(dto);
			 }
			 if(pptrChecked(pptrIgm.getGeoUcpGeositi())) {
				 PptrSelezioniDTO dto = buildPptrSelezione(idPratica, "GEOSITI", "art_143");
				 ret.add(dto);
			 }
			 if(pptrChecked(pptrIgm.getGeoUcpGrotte())) {
				 PptrSelezioniDTO dto = buildPptrSelezione(idPratica, "GROTTE", "art_143");
				 ret.add(dto);
			 }
			 if(pptrChecked(pptrIgm.getGeoUcpInghiottitoi())) {
				 PptrSelezioniDTO dto = buildPptrSelezione(idPratica, "INGHIOTTITOI", "art_143");
				 ret.add(dto);
			 }
			 if(pptrChecked(pptrIgm.getGeoUcpLameGravine())) {
				 PptrSelezioniDTO dto = buildPptrSelezione(idPratica, "LAME_E_GRAVINE", "art_143");
				 ret.add(dto);
			 }
			 if(pptrChecked(pptrIgm.getGeoUcpVersanti())) {
				 PptrSelezioniDTO dto = buildPptrSelezione(idPratica, "VERSANTI", "art_143");
				 ret.add(dto);
			 }
			 if(pptrChecked(pptrIgm.getIdroBpCorsiAcqua())) {
				 PptrSelezioniDTO dto = buildPptrSelezione(idPratica, "ACQUE_PUBBLICHE", "art_142");
				 dto.setSpecifica(pptrIgm.getIdroBpCorsiAcquaSpecif());
				 ret.add(dto);
			 }
			 if(pptrChecked(pptrIgm.getIdroBpTerritContermLaghi())) {
				 PptrSelezioniDTO dto = buildPptrSelezione(idPratica, "TERRITORI_LAGHI", "art_142");
				 ret.add(dto);
			 }
			 if(pptrChecked(pptrIgm.getIdroBpTerritCostieri())) {
				 PptrSelezioniDTO dto = buildPptrSelezione(idPratica, "TERRITORI_COSTIERI", "art_142");
				 ret.add(dto);
			 }
			 if(pptrChecked(pptrIgm.getIdroUcpAreeSoggVinc())) {
				 PptrSelezioniDTO dto = buildPptrSelezione(idPratica, "AREE_VINCOLO", "art_143");
				 ret.add(dto);
			 }
			 if(pptrChecked(pptrIgm.getIdroUcpReticoloIdrografico())) {
				 PptrSelezioniDTO dto = buildPptrSelezione(idPratica, "RETICOLO_IDROGRAFICO", "art_143");
				 ret.add(dto);
			 }
			 if(pptrChecked(pptrIgm.getIdroUcpSorgenti())) {
				 PptrSelezioniDTO dto = buildPptrSelezione(idPratica, "SORGENTI", "art_143");
				 ret.add(dto);
			 }
		}
		return ret;
	}
	public static Integer getTipoDocAmm(String tipoDocumento) throws MigrationException {
		Integer ret=null;
		if(tipoDocumento.toLowerCase().contains("atti") && 
				tipoDocumento.toLowerCase().contains("relativi") && 
				tipoDocumento.toLowerCase().contains("procedimenti")&&
				tipoDocumento.toLowerCase().contains("contenzios")) {
			//Atti relativi a eventuali procedimenti di contenzioso in atto
			ret=6;
		}
		if(tipoDocumento.toLowerCase().contains("planimetria") && 
				tipoDocumento.toLowerCase().contains("individuaz") && 
				tipoDocumento.toLowerCase().contains("part")&&
				tipoDocumento.toLowerCase().contains("precedent")) {
			//Planimetria con individuazione delle parti realizzate con precedenti titoli edilizi
			ret=2;
		}
		if(tipoDocumento.toLowerCase().contains("ricevuta") && 
				tipoDocumento.toLowerCase().contains("pagamento") && 
				tipoDocumento.toLowerCase().contains("oneri")&&
				tipoDocumento.toLowerCase().contains("istruttor")) {
			//Ricevuta di pagamento oneri istruttori
			ret=1;
		}
		if(tipoDocumento.toLowerCase().contains("eventual") && 
				tipoDocumento.toLowerCase().contains("atti") && 
				tipoDocumento.toLowerCase().contains("assenso")&&
				tipoDocumento.toLowerCase().contains("acquisit")) {
			//Eventuali atti di assenso già acquisiti
			ret=5;
		}
		if(tipoDocumento.toLowerCase().contains("eventual") && 
				tipoDocumento.toLowerCase().contains("titoli") && 
				tipoDocumento.toLowerCase().contains("edilizi")&&
				tipoDocumento.toLowerCase().contains("rilasciat")) {
			//Eventuali titoli edilizi già rilasciati
			ret=3;
		}
		if(tipoDocumento.toLowerCase().contains("parere") && 
				tipoDocumento.toLowerCase().contains("conformit") && 
				tipoDocumento.toLowerCase().contains("ediliz")&&
				tipoDocumento.toLowerCase().contains("urbanis")) {
			//Parere di conformità edilizia-urbanistica, se già acquisito
			ret=8;
		}
		if(tipoDocumento.toLowerCase().contains("attestaz") && 
				tipoDocumento.toLowerCase().contains("conformit") && 
				tipoDocumento.toLowerCase().contains("prescriz")&&
				tipoDocumento.toLowerCase().contains("asseveraz") && 
				tipoDocumento.toLowerCase().contains("23")) {
			//ATTESTAZIONE CONFORMITA' A PRESCRIZIONI URB/EDIL RILASCIATE DA COMUNE - ASSEVERAZIONI ART23 DPR380/01
			ret=10;
		}
		if(tipoDocumento.toLowerCase().contains("attestaz") && 
				tipoDocumento.toLowerCase().contains("conformit") && 
				tipoDocumento.toLowerCase().contains("prescriz")&&
				tipoDocumento.toLowerCase().contains("36")) {
			//ATTESTAZIONE CONFORMITA' A PRESCRIZIONI URB/EDIL RILASCIATE DA COMUNE ART36 DPR380/01
			ret=9;
		}
		if(tipoDocumento.toLowerCase().contains("eventual") && 
				tipoDocumento.toLowerCase().contains("provvediment") && 
				tipoDocumento.toLowerCase().contains("paesaggist")&&
				tipoDocumento.toLowerCase().contains("rilasciat")) {
			//Eventuali provvedimenti paesaggistici già rilasciati
			ret=4;
		}
		if(tipoDocumento.toLowerCase().contains("eventual") && 
				tipoDocumento.toLowerCase().contains("pareri") && 
				tipoDocumento.toLowerCase().contains("natura") &&
				tipoDocumento.toLowerCase().contains("paesaggist")&&
				tipoDocumento.toLowerCase().contains("ambientale")) {
			//Eventuali pareri di natura paesaggistica ambientale
			ret=7;
		}
		
		//Eventuali pareri di natura paesaggistica ambientale
		if(ret==null) throw new MigrationException("Errore nel mapping del tipo documento amministrativo:"+tipoDocumento);
		return ret;
	}
	
	/**
	 * mappa i loro codici allegato (solo per la fase di presentazione istanza SEZIONE DOC_TECNICA)
	 * @author acolaianni
	 *
	 * @param metadatiAllegato
	 * @param tipoProcedimentoNew
	 * @return
	 * @throws MigrationException
	 */
	public static List<Integer> getTipiDocTecnica(List<VtnoAllegatiPptr> metadatiAllegato,Integer tipoProcedimentoNew) throws MigrationException {
		List<Integer> ret=new ArrayList<Integer>();
		if(ListUtil.isEmpty(metadatiAllegato))
			return ret;
		for(VtnoAllegatiPptr allegato:metadatiAllegato) {
			if(tipoProcedimentoNew==1) {
				int codiceCivilia=Integer.parseInt(allegato.getPptrTipoAllegatoCodice());
				if(codiceCivilia>1007 && codiceCivilia<1000) {
					throw new MigrationException("Errore nel mapping del tipo allegato scheda tecnica, codice:"+allegato.getCodice()+" descr:"+allegato.getDescrizione());
				}
				ret.add(100+(codiceCivilia % 1000));
			}
			if(tipoProcedimentoNew==2) {
				int codiceCivilia=Integer.parseInt(allegato.getPptrTipoAllegatoCodice());
				switch (codiceCivilia) {
				case 502000:
					ret.add(200);
					break;
				case 502001:
					ret.add(201);
					break;
				case 502002:
					ret.add(202);
					break;	
				case 502003:
					ret.add(203);
					break;
				case 502005:
					ret.add(204);
					break;
				case 502007:
					ret.add(205);
					break;	
				default:
					throw new MigrationException("Errore nel mapping del tipo allegato scheda tecnica, codice:"+allegato.getCodice()+" descr:"+allegato.getDescrizione());
				}
			}
			if(tipoProcedimentoNew==3) {
				int codiceCivilia=Integer.parseInt(allegato.getPptrTipoAllegatoCodice());
				if(codiceCivilia>3006 && codiceCivilia<3000) {
					throw new MigrationException("Errore nel mapping del tipo allegato scheda tecnica, codice:"+allegato.getCodice()+" descr:"+allegato.getDescrizione());
				}
				ret.add(300+(codiceCivilia % 3000));
			}
			if(tipoProcedimentoNew==4) {
				int codiceCivilia=Integer.parseInt(allegato.getPptrTipoAllegatoCodice());
				if(codiceCivilia>604006 && codiceCivilia<604000) {
					throw new MigrationException("Errore nel mapping del tipo allegato scheda tecnica, codice:"+allegato.getCodice()+" descr:"+allegato.getDescrizione());
				}
				ret.add(400+(codiceCivilia % 604000));
			}
			if(tipoProcedimentoNew==5) {
				int codiceCivilia=Integer.parseInt(allegato.getPptrTipoAllegatoCodice());
				if(codiceCivilia>4006 && codiceCivilia<4000) {
					throw new MigrationException("Errore nel mapping del tipo allegato scheda tecnica, codice:"+allegato.getCodice()+" descr:"+allegato.getDescrizione());
				}
				ret.add(150+(codiceCivilia % 4000));
			}
			if(tipoProcedimentoNew==6) {
				int codiceCivilia=Integer.parseInt(allegato.getPptrTipoAllegatoCodice());
				if(codiceCivilia>2008 && codiceCivilia<2000) {
					throw new MigrationException("Errore nel mapping del tipo allegato scheda tecnica, codice:"+allegato.getCodice()+" descr:"+allegato.getDescrizione());
				}
				ret.add(160+(codiceCivilia % 2000));
			}
			
		}
		if(ListUtil.isEmpty(ret)) {
			throw new MigrationException("Errore nel mapping dei tipi di contenuto da: "+
					metadatiAllegato.stream().map(el->el.gettTipodocDescrizione()).collect(Collectors.toList()));
		}
		return ret;
	}
	
}
