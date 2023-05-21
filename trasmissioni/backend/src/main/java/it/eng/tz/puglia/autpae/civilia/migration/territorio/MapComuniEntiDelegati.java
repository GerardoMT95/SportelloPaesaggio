package it.eng.tz.puglia.autpae.civilia.migration.territorio;


import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.tz.puglia.autpae.civilia.migration.dto.Comuni_completo_cod_istat;



/**
 * COPIATA DA sorgente innova
 * The Class MapComuniEntiDelegati. Questa classe gestisce le unione dei comuni
 * per Ente Delegato . Durante il suo costruttore MapComuniEntiDelegati( List
 * <Comuni_completo_cod_istat> listaComuni ) riporta le varie associazioni .
 * metodo getCodiceProcedimento > restituisce il codice istat estratto dal
 * codicePratica metodo getlistaComuni_ByCodicePratica > restituisce la lista
 * della unione in considerazione al controllo del codice istat.
 * 
 */
public class MapComuniEntiDelegati
{
	
	/** _logger. */
	private static Logger _logger = LoggerFactory.getLogger( MapComuniEntiDelegati.class );
	
	/** comuni. */
	private Map<String , Comuni_completo_cod_istat> comuni;
	
	/** terradileucabis. */
	private Map<String , Comuni_completo_cod_istat> terradileucabis; // 20001
	
	/** entroterraidruntino. */
	private Map<String , Comuni_completo_cod_istat> entroterraidruntino; // 20002
	
	/** terremaresole. */
	private Map<String , Comuni_completo_cod_istat> terremaresole; // 20003
	
	/** jonicasalentina. */
	private Map<String , Comuni_completo_cod_istat> jonicasalentina; // 20004
	
	/** greciasal. */
	private Map<String , Comuni_completo_cod_istat> greciasal; // 20005
	
	/** terradileuca. */
	private Map<String , Comuni_completo_cod_istat> terradileuca; // 20006
	
	/** talassa. */
	private Map<String , Comuni_completo_cod_istat> talassa; // 20007
	
	/** nordsalento. */
	private Map<String , Comuni_completo_cod_istat> nordsalento; // 20008
	
	/** terra gravine. */
	private Map<String , Comuni_completo_cod_istat> terraGravine; // 20009
	
	/** prov fg. */
	private Map<String , Comuni_completo_cod_istat> provFg;
	
	/** prov bt. */
	private Map<String , Comuni_completo_cod_istat> provBt;
	
	/** comune di lecce , gestione particelle di squinzano e trepuzzi acquisite da lecce */
	private Map<String , Comuni_completo_cod_istat> comuneLecce; // 75035
	
	/** costa orientale. */
	private Map<String , Comuni_completo_cod_istat> costaOrientale; // 20010
	
	/** terre di mezzo */
	private Map<String , Comuni_completo_cod_istat> terreMezzo; // 20011
	
	/**
	 * getComune_completo_cod_istat.
	 *
	 * @param listaComuni
	 * @param nomeComune
	 * @return Comuni_completo_cod_istat
	 * @throws Exception
	 */
	private Comuni_completo_cod_istat getComune_completo_cod_istat( List<Comuni_completo_cod_istat> listaComuni, String nomeComune ) throws Exception
	{
		Comuni_completo_cod_istat comuni_completo_cod_istat = null;
		try
		{
			for( Comuni_completo_cod_istat comCodIstat : listaComuni )
			{
				if( nomeComune.compareToIgnoreCase( comCodIstat.getComune() ) == 0 )
				{
					comuni_completo_cod_istat = comCodIstat;
				}
			}
			if( comuni_completo_cod_istat == null )
			{
				throw new Exception( "getComune_completo_cod_istat Errore NULL :: NON SI TROVA COMUNE CON NOME " + nomeComune );
			}
		}
		catch( Exception e )
		{
			_logger.error( "MapComuniEntiDelegati.getComune_completo_cod_istat Errore " + e.getMessage() );
		}
		return comuni_completo_cod_istat;
	}
	
	/**
	 * Instanzia l`oggetto MapComuniEntiDelegati di tipo LinkedHashMap<String ,
	 * Comuni_completo_cod_istat> che contiene i seguenti associazioni : comuni
	 * terradileucabis entroterraidruntino terremaresole jonicasalentina
	 * greciasal terradileuca talassa nordsalento terraGravine provFg provBt .
	 *
	 * @param listaComuni
	 *            the lista comuni
	 * @throws Exception
	 *             the exception
	 */
	public MapComuniEntiDelegati( List<Comuni_completo_cod_istat> listaComuni ) throws Exception
	{
		comuni = new LinkedHashMap<String , Comuni_completo_cod_istat>();
		terradileucabis = new LinkedHashMap<String , Comuni_completo_cod_istat>();
		entroterraidruntino = new LinkedHashMap<String , Comuni_completo_cod_istat>();
		terremaresole = new LinkedHashMap<String , Comuni_completo_cod_istat>();
		jonicasalentina = new LinkedHashMap<String , Comuni_completo_cod_istat>();
		greciasal = new LinkedHashMap<String , Comuni_completo_cod_istat>();
		terradileuca = new LinkedHashMap<String , Comuni_completo_cod_istat>();
		talassa = new LinkedHashMap<String , Comuni_completo_cod_istat>();
		nordsalento = new LinkedHashMap<String , Comuni_completo_cod_istat>();
		terraGravine = new LinkedHashMap<String , Comuni_completo_cod_istat>();
		provFg = new LinkedHashMap<String , Comuni_completo_cod_istat>();
		provBt = new LinkedHashMap<String , Comuni_completo_cod_istat>();
		comuneLecce = new LinkedHashMap<String , Comuni_completo_cod_istat>();
		costaOrientale = new LinkedHashMap<String , Comuni_completo_cod_istat>();
		terreMezzo = new LinkedHashMap<String , Comuni_completo_cod_istat>();
			
		Comuni_completo_cod_istat tmpComune;
		tmpComune = getComune_completo_cod_istat( listaComuni , "ACCADIA" );
		comuni.put( "71001" , tmpComune );
		provFg.put( "71001" , tmpComune );
		comuni.put( "75001" , getComune_completo_cod_istat( listaComuni , "ACQUARICA DEL CAPO" ) );
		comuni.put( "72001" , getComune_completo_cod_istat( listaComuni , "ACQUAVIVA DELLE FONTI" ) );
		comuni.put( "72002" , getComune_completo_cod_istat( listaComuni , "ADELFIA" ) );
		comuni.put( "72003" , getComune_completo_cod_istat( listaComuni , "ALBEROBELLO" ) );
		tmpComune = getComune_completo_cod_istat( listaComuni , "ALBERONA" );
		comuni.put( "71002" , tmpComune );
		provFg.put( "71002" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "ALESSANO" );
		comuni.put( "75002" , tmpComune );
		terradileuca.put( "75002" , tmpComune );
		comuni.put( "75003" , getComune_completo_cod_istat( listaComuni , "ALEZIO" ) );
		tmpComune = getComune_completo_cod_istat( listaComuni , "ALLISTE" );
		comuni.put( "75004" , tmpComune );
		jonicasalentina.put( "75004" , tmpComune );
		comuni.put( "72004" , getComune_completo_cod_istat( listaComuni , "ALTAMURA" ) );
		comuni.put( "75005" , getComune_completo_cod_istat( listaComuni , "ANDRANO" ) );
		tmpComune = getComune_completo_cod_istat( listaComuni , "ANDRIA" );
		comuni.put( "10001" , tmpComune );
		provBt.put( "10001" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "ANZANO DI PUGLIA" );
		comuni.put( "71003" , tmpComune );
		provFg.put( "71003" , tmpComune );
		comuni.put( "71004" , getComune_completo_cod_istat( listaComuni , "APRICENA" ) );
		comuni.put( "75006" , getComune_completo_cod_istat( listaComuni , "ARADEO" ) );
		comuni.put( "75007" , getComune_completo_cod_istat( listaComuni , "ARNESANO" ) );
		tmpComune = getComune_completo_cod_istat( listaComuni , "ASCOLI SATRIANO" );
		comuni.put( "71005" , tmpComune );
		provFg.put( "71005" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "AVETRANA" );
		comuni.put( "73001" , tmpComune );
		terremaresole.put( "73001" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "BAGNOLO DEL SALENTO" );
		comuni.put( "75008" , tmpComune );
		entroterraidruntino.put( "75008" , tmpComune );
		comuni.put( "72006" , getComune_completo_cod_istat( listaComuni , "BARI" ) );
		tmpComune = getComune_completo_cod_istat( listaComuni , "BARLETTA" );
		comuni.put( "10002" , tmpComune );
		provBt.put( "10002" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "BICCARI" );
		comuni.put( "71006" , tmpComune );
		provFg.put( "71006" , tmpComune );
		comuni.put( "72008" , getComune_completo_cod_istat( listaComuni , "BINETTO" ) );
		comuni.put( "10003" , getComune_completo_cod_istat( listaComuni , "BISCEGLIE" ) );
		comuni.put( "72010" , getComune_completo_cod_istat( listaComuni , "BITETTO" ) );
		comuni.put( "72011" , getComune_completo_cod_istat( listaComuni , "BITONTO" ) );
		comuni.put( "72012" , getComune_completo_cod_istat( listaComuni , "BITRITTO" ) );		
		tmpComune = getComune_completo_cod_istat( listaComuni , "BOTRUGNO" );
		terreMezzo.put( "75009" , tmpComune );
		comuni.put( "75009" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "BOVINO" );
		comuni.put( "71007" , tmpComune );
		provFg.put( "71007" , tmpComune );
		comuni.put( "74001" , getComune_completo_cod_istat( listaComuni , "BRINDISI" ) );
		tmpComune = getComune_completo_cod_istat( listaComuni , "CAGNANO VARANO" );
		comuni.put( "71008" , tmpComune );
		provFg.put( "71008" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "CALIMERA" );
		comuni.put( "75010" , tmpComune );
		greciasal.put( "75010" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "CAMPI SALENTINA" );
		comuni.put( "75011" , tmpComune );
		nordsalento.put( "75011" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "CANDELA" );
		comuni.put( "71009" , tmpComune );
		provFg.put( "71009" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "CANNOLE" );
		comuni.put( "75012" , tmpComune );
		entroterraidruntino.put( "75012" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "CANOSA DI PUGLIA" );
		comuni.put( "10004" , tmpComune );
		provBt.put( "10004" , tmpComune );
		comuni.put( "75013" , getComune_completo_cod_istat( listaComuni , "CAPRARICA DI LECCE" ) );
		comuni.put( "72014" , getComune_completo_cod_istat( listaComuni , "CAPURSO" ) );
		tmpComune = getComune_completo_cod_istat( listaComuni , "CARAPELLE" );
		comuni.put( "71010" , tmpComune );
		provFg.put( "71010" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "CARLANTINO" );
		comuni.put( "71011" , tmpComune );
		provFg.put( "71011" , tmpComune );
		comuni.put( "75014" , getComune_completo_cod_istat( listaComuni , "CARMIANO" ) );
		comuni.put( "73002" , getComune_completo_cod_istat( listaComuni , "CAROSINO" ) );
		comuni.put( "74002" , getComune_completo_cod_istat( listaComuni , "CAROVIGNO" ) );
		tmpComune = getComune_completo_cod_istat( listaComuni , "CARPIGNANO SALENTINO" );
		comuni.put( "75015" , tmpComune );
		greciasal.put( "75015" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "CARPINO" );
		comuni.put( "71012" , tmpComune );
		provFg.put( "71012" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "CASALNUOVO MONTEROTARO" );
		comuni.put( "71013" , tmpComune );
		provFg.put( "71013" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "CASALVECCHIO DI PUGLIA" );
		comuni.put( "71014" , tmpComune );
		provFg.put( "71014" , tmpComune );
		comuni.put( "72015" , getComune_completo_cod_istat( listaComuni , "CASAMASSIMA" ) );
		comuni.put( "75016" , getComune_completo_cod_istat( listaComuni , "CASARANO" ) );
		comuni.put( "72016" , getComune_completo_cod_istat( listaComuni , "CASSANO DELLE MURGE" ) );
		comuni.put( "72017" , getComune_completo_cod_istat( listaComuni , "CASTELLANA GROTTE" ) );
		comuni.put( "73003" , getComune_completo_cod_istat( listaComuni , "CASTELLANETA" ) );
		tmpComune = getComune_completo_cod_istat( listaComuni , "CASTELLUCCIO DEI SAURI" );
		comuni.put( "71015" , tmpComune );
		provFg.put( "71015" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "CASTELLUCCIO VALMAGGIORE" );
		comuni.put( "71016" , tmpComune );
		provFg.put( "71016" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "CASTELNUOVO DELLA DAUNIA" );
		comuni.put( "71017" , tmpComune );
		provFg.put( "71017" , tmpComune );
		comuni.put( "75017" , getComune_completo_cod_istat( listaComuni , "CASTRI DI LECCE" ) );
		tmpComune = getComune_completo_cod_istat( listaComuni , "CASTRIGNANO DE` GRECI" );
		comuni.put( "75018" , tmpComune );
		greciasal.put( "75018" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "CASTRIGNANO DEL CAPO" );
		comuni.put( "75019" , tmpComune );
		// rimosso talassa.put( "75019" , tmpComune );
		terradileuca.put( "75019" , tmpComune ); 
		tmpComune = getComune_completo_cod_istat( listaComuni , "CASTRO" );
		comuni.put( "75096" , tmpComune );
		costaOrientale.put( "75096" , tmpComune );
		comuni.put( "75020" , getComune_completo_cod_istat( listaComuni , "CAVALLINO" ) );
		comuni.put( "74003" , getComune_completo_cod_istat( listaComuni , "CEGLIE MESSAPICA" ) );
		tmpComune = getComune_completo_cod_istat( listaComuni , "CELENZA VALFORTORE" );
		comuni.put( "71018" , tmpComune );
		provFg.put( "71018" , tmpComune );
		comuni.put( "72018" , getComune_completo_cod_istat( listaComuni , "CELLAMARE" ) );
		tmpComune = getComune_completo_cod_istat( listaComuni , "CELLE DI SAN VITO" );
		comuni.put( "71019" , tmpComune );
		provFg.put( "71019" , tmpComune );
		comuni.put( "74004" , getComune_completo_cod_istat( listaComuni , "CELLINO SAN MARCO" ) );
		tmpComune = getComune_completo_cod_istat( listaComuni , "CERIGNOLA" );
		comuni.put( "71020" , tmpComune );
		provFg.put( "71020" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "CHIEUTI" );
		comuni.put( "71021" , tmpComune );
		provFg.put( "71021" , tmpComune );
		comuni.put( "74005" , getComune_completo_cod_istat( listaComuni , "CISTERNINO" ) );
		comuni.put( "75021" , getComune_completo_cod_istat( listaComuni , "COLLEPASSO" ) );
		comuni.put( "72019" , getComune_completo_cod_istat( listaComuni , "CONVERSANO" ) );
		comuni.put( "75022" , getComune_completo_cod_istat( listaComuni , "COPERTINO" ) );
		comuni.put( "72020" , getComune_completo_cod_istat( listaComuni , "CORATO" ) );
		tmpComune = getComune_completo_cod_istat( listaComuni , "CORIGLIANO D`OTRANTO" );
		comuni.put( "75023" , tmpComune );
		greciasal.put( "75023" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "CORSANO" );
		comuni.put( "75024" , tmpComune );
		terradileuca.put( "75024" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "CRISPIANO" );
		comuni.put( "73004" , tmpComune );
		terraGravine.put( "73004" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "CURSI" );
		comuni.put( "75025" , tmpComune );
		entroterraidruntino.put( "75025" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "CUTROFIANO" );
		comuni.put( "75026" , tmpComune );
		greciasal.put( "75026" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "DELICETO" );
		comuni.put( "71022" , tmpComune );
		provFg.put( "71022" , tmpComune );
		comuni.put( "75027" , getComune_completo_cod_istat( listaComuni , "DISO" ) );
		comuni.put( "74006" , getComune_completo_cod_istat( listaComuni , "ERCHIE" ) );
		tmpComune = getComune_completo_cod_istat( listaComuni , "FAETO" );
		comuni.put( "71023" , tmpComune );
		provFg.put( "71023" , tmpComune );
		comuni.put( "73005" , getComune_completo_cod_istat( listaComuni , "FAGGIANO" ) );
		comuni.put( "74007" , getComune_completo_cod_istat( listaComuni , "FASANO" ) );
		tmpComune = getComune_completo_cod_istat( listaComuni , "FOGGIA" );
		comuni.put( "71024" , tmpComune );
		provFg.put( "71024" , tmpComune );
		comuni.put( "73006" , getComune_completo_cod_istat( listaComuni , "FRAGAGNANO" ) );
		comuni.put( "74008" , getComune_completo_cod_istat( listaComuni , "FRANCAVILLA FONTANA" ) );
		tmpComune = getComune_completo_cod_istat( listaComuni , "GAGLIANO DEL CAPO" );
		comuni.put( "75028" , tmpComune );
		terradileuca.put( "75028" , tmpComune );
		comuni.put( "75029" , getComune_completo_cod_istat( listaComuni , "GALATINA" ) );
		comuni.put( "75030" , getComune_completo_cod_istat( listaComuni , "GALATONE" ) );
		comuni.put( "75031" , getComune_completo_cod_istat( listaComuni , "GALLIPOLI" ) );
		comuni.put( "73007" , getComune_completo_cod_istat( listaComuni , "GINOSA" ) );
		comuni.put( "72021" , getComune_completo_cod_istat( listaComuni , "GIOIA DEL COLLE" ) );
		comuni.put( "72022" , getComune_completo_cod_istat( listaComuni , "GIOVINAZZO" ) );		
		tmpComune = getComune_completo_cod_istat( listaComuni , "GIUGGIANELLO" );
		comuni.put( "75032" , tmpComune );
		terreMezzo.put( "75032" , tmpComune );				
		comuni.put( "75033" , getComune_completo_cod_istat( listaComuni , "GIURDIGNANO" ) );
		comuni.put( "72023" , getComune_completo_cod_istat( listaComuni , "GRAVINA IN PUGLIA" ) );
		comuni.put( "73008" , getComune_completo_cod_istat( listaComuni , "GROTTAGLIE" ) );
		comuni.put( "72024" , getComune_completo_cod_istat( listaComuni , "GRUMO APPULA" ) );
		tmpComune = getComune_completo_cod_istat( listaComuni , "GUAGNANO" );
		comuni.put( "75034" , tmpComune );
		nordsalento.put( "75034" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "ISCHITELLA" );
		comuni.put( "71025" , tmpComune );
		provFg.put( "71025" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "ISOLE TREMITI" );
		comuni.put( "71026" , tmpComune );
		provFg.put( "71026" , tmpComune );
		comuni.put( "73009" , getComune_completo_cod_istat( listaComuni , "LATERZA" ) );
		comuni.put( "74009" , getComune_completo_cod_istat( listaComuni , "LATIANO" ) );
		tmpComune = getComune_completo_cod_istat( listaComuni , "LECCE" );		
		comuni.put( "75035" , tmpComune );									
		nordsalento.put( "75035" , tmpComune );
		comuneLecce.put( "75035" , tmpComune );		
		tmpComune = getComune_completo_cod_istat( listaComuni , "LEPORANO" );
		comuni.put( "73010" , tmpComune );
		terremaresole.put( "73010" , tmpComune );
		comuni.put( "75036" , getComune_completo_cod_istat( listaComuni , "LEQUILE" ) );
		tmpComune = getComune_completo_cod_istat( listaComuni , "LESINA" );
		comuni.put( "71027" , tmpComune );
		provFg.put( "71027" , tmpComune );
		comuni.put( "75037" , getComune_completo_cod_istat( listaComuni , "LEVERANO" ) );
		comuni.put( "75038" , getComune_completo_cod_istat( listaComuni , "LIZZANELLO" ) );
		tmpComune = getComune_completo_cod_istat( listaComuni , "LIZZANO" );
		comuni.put( "73011" , tmpComune );
		terremaresole.put( "73011" , tmpComune );
		comuni.put( "72025" , getComune_completo_cod_istat( listaComuni , "LOCOROTONDO" ) );
		tmpComune = getComune_completo_cod_istat( listaComuni , "LUCERA" );
		comuni.put( "71028" , tmpComune );
		provFg.put( "71028" , tmpComune );		
		tmpComune = getComune_completo_cod_istat( listaComuni , "MAGLIE" );
		comuni.put( "75039" , tmpComune  );		
		entroterraidruntino.put( "75039" , tmpComune  );				
		comuni.put( "73012" , getComune_completo_cod_istat( listaComuni , "MANDURIA" ) );
		tmpComune = getComune_completo_cod_istat( listaComuni , "MANFREDONIA" );
		comuni.put( "71029" , tmpComune );
		provFg.put( "71029" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "MARGHERITA DI SAVOIA" );
		comuni.put( "10005" , tmpComune );
		provBt.put( "10005" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "MARTANO" );
		comuni.put( "75040" , tmpComune );
		greciasal.put( "75040" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "MARTIGNANO" );
		comuni.put( "75041" , tmpComune );
		greciasal.put( "75041" , tmpComune );
		comuni.put( "73013" , getComune_completo_cod_istat( listaComuni , "MARTINA FRANCA" ) );
		tmpComune = getComune_completo_cod_istat( listaComuni , "MARUGGIO" );
		comuni.put( "73014" , tmpComune );
		terremaresole.put( "73014" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "MASSAFRA" );
		comuni.put( "73015" , tmpComune );
		terraGravine.put( "73015" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "MATINO" );
		comuni.put( "75042" , tmpComune );
		jonicasalentina.put( "75042" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "MATTINATA" );
		comuni.put( "71031" , tmpComune );
		provFg.put( "71031" , tmpComune );
		comuni.put( "75043" , getComune_completo_cod_istat( listaComuni , "MELENDUGNO" ) );
		tmpComune = getComune_completo_cod_istat( listaComuni , "MELISSANO" );
		comuni.put( "75044" , tmpComune );
		jonicasalentina.put( "75044" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "MELPIGNANO" );
		comuni.put( "75045" , tmpComune );
		greciasal.put( "75045" , tmpComune );
		comuni.put( "74010" , getComune_completo_cod_istat( listaComuni , "MESAGNE" ) );
		tmpComune = getComune_completo_cod_istat( listaComuni , "MIGGIANO" );
		comuni.put( "75046" , tmpComune );
		terradileucabis.put( "75046" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "MINERVINO DI LECCE" );
		comuni.put( "75047" , tmpComune );
		costaOrientale.put( "75047" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "MINERVINO MURGE" );
		comuni.put( "10006" , tmpComune );
		provBt.put( "10006" , tmpComune );
		comuni.put( "72027" , getComune_completo_cod_istat( listaComuni , "MODUGNO" ) );
		comuni.put( "72028" , getComune_completo_cod_istat( listaComuni , "MOLA DI BARI" ) );
		comuni.put( "72029" , getComune_completo_cod_istat( listaComuni , "MOLFETTA" ) );
		comuni.put( "72030" , getComune_completo_cod_istat( listaComuni , "MONOPOLI" ) );
		tmpComune = getComune_completo_cod_istat( listaComuni , "MONTE SANT`ANGELO" );
		comuni.put( "71033" , tmpComune );
		provFg.put( "71033" , tmpComune );
		comuni.put( "73016" , getComune_completo_cod_istat( listaComuni , "MONTEIASI" ) );
		tmpComune = getComune_completo_cod_istat( listaComuni , "MONTELEONE DI PUGLIA" );
		comuni.put( "71032" , tmpComune );
		provFg.put( "71032" , tmpComune );
		comuni.put( "73017" , getComune_completo_cod_istat( listaComuni , "MONTEMESOLA" ) );
		comuni.put( "73018" , getComune_completo_cod_istat( listaComuni , "MONTEPARANO" ) );
		comuni.put( "75048" , getComune_completo_cod_istat( listaComuni , "MONTERONI DI LECCE" ) );
		tmpComune = getComune_completo_cod_istat( listaComuni , "MONTESANO SALENTINO" );
		comuni.put( "75049" , tmpComune );
		terradileucabis.put( "75049" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "MORCIANO DI LEUCA" );
		comuni.put( "75050" , tmpComune );
		terradileuca.put( "75050" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "MOTTA MONTECORVINO" );
		comuni.put( "71034" , tmpComune );
		provFg.put( "71034" , tmpComune );
		comuni.put( "73019" , getComune_completo_cod_istat( listaComuni , "MOTTOLA" ) );				
		comuni.put( "75051" , getComune_completo_cod_istat( listaComuni , "MURO LECCESE" ) );		
		comuni.put( "75052" , getComune_completo_cod_istat( listaComuni , "NARDO" ) );
		comuni.put( "75053" , getComune_completo_cod_istat( listaComuni , "NEVIANO" ) );
		comuni.put( "72031" , getComune_completo_cod_istat( listaComuni , "NOCI" ) );
		tmpComune = getComune_completo_cod_istat( listaComuni , "NOCIGLIA" );
		comuni.put( "75054" , tmpComune );
		terreMezzo.put( "75054" , tmpComune );
		comuni.put( "72032" , getComune_completo_cod_istat( listaComuni , "NOICATTARO" ) );
		tmpComune = getComune_completo_cod_istat( listaComuni , "NOVOLI" );
		comuni.put( "75055" , tmpComune );
		nordsalento.put( "75055" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "ORDONA" );
		comuni.put( "71063" , tmpComune );
		provFg.put( "71063" , tmpComune );
		comuni.put( "74011" , getComune_completo_cod_istat( listaComuni , "ORIA" ) );
		tmpComune = getComune_completo_cod_istat( listaComuni , "ORSARA DI PUGLIA" );
		comuni.put( "71035" , tmpComune );
		provFg.put( "71035" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "ORTA NOVA" );
		comuni.put( "71036" , tmpComune );
		provFg.put( "71036" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "ORTELLE" );
		comuni.put( "75056" ,tmpComune );
		costaOrientale.put( "75056" , tmpComune );
		comuni.put( "74012" , getComune_completo_cod_istat( listaComuni , "OSTUNI" ) );
		comuni.put( "75057" , getComune_completo_cod_istat( listaComuni , "OTRANTO" ) );
		comuni.put( "73020" , getComune_completo_cod_istat( listaComuni , "PALAGIANELLO" ) );
		comuni.put( "73021" , getComune_completo_cod_istat( listaComuni , "PALAGIANO" ) );
		tmpComune = getComune_completo_cod_istat( listaComuni , "PALMARIGGI" );
		comuni.put( "75058" , tmpComune );
		entroterraidruntino.put( "75058" , tmpComune );
		comuni.put( "72033" , getComune_completo_cod_istat( listaComuni , "PALO DEL COLLE" ) );
		tmpComune = getComune_completo_cod_istat( listaComuni , "PANNI" );
		comuni.put( "71037" , tmpComune );
		provFg.put( "71037" , tmpComune );
		comuni.put( "75059" , getComune_completo_cod_istat( listaComuni , "PARABITA" ) );
		tmpComune = getComune_completo_cod_istat( listaComuni , "PATU" );
		comuni.put( "75060" , tmpComune );
		terradileuca.put( "75060" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "PESCHICI" );
		comuni.put( "71038" , tmpComune );
		provFg.put( "71038" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "PIETRAMONTECORVINO" );
		comuni.put( "71039" , tmpComune );
		provFg.put( "71039" , tmpComune );		
		comuni.put( "75061" , getComune_completo_cod_istat( listaComuni , "POGGIARDO" ) );
		tmpComune = getComune_completo_cod_istat( listaComuni , "POGGIO IMPERIALE" );
		comuni.put( "71040" , tmpComune );
		provFg.put( "71040" , tmpComune );
		comuni.put( "72034" , getComune_completo_cod_istat( listaComuni , "POGGIORSINI" ) );
		comuni.put( "72035" , getComune_completo_cod_istat( listaComuni , "POLIGNANO A MARE" ) );
		comuni.put( "75097" , getComune_completo_cod_istat( listaComuni , "PORTO CESAREO" ) );
		comuni.put( "75062" , getComune_completo_cod_istat( listaComuni , "PRESICCE" ) );
		tmpComune = getComune_completo_cod_istat( listaComuni , "PULSANO" );
		comuni.put( "73022" , tmpComune );
		terremaresole.put( "73022" , tmpComune );
		comuni.put( "72036" , getComune_completo_cod_istat( listaComuni , "PUTIGNANO" ) );
		tmpComune = getComune_completo_cod_istat( listaComuni , "RACALE" );
		comuni.put( "75063" , tmpComune );
		jonicasalentina.put( "75063" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "RIGNANO GARGANICO" );
		comuni.put( "71041" , tmpComune );
		provFg.put( "71041" , tmpComune );
		comuni.put( "73023" , getComune_completo_cod_istat( listaComuni , "ROCCAFORZATA" ) );
		tmpComune = getComune_completo_cod_istat( listaComuni , "ROCCHETTA SANT`ANTONIO" );
		comuni.put( "71042" , tmpComune );
		provFg.put( "71042" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "RODI GARGANICO" );
		comuni.put( "71043" , tmpComune );
		provFg.put( "71043" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "ROSETO VALFORTORE" );
		comuni.put( "71044" , tmpComune );
		provFg.put( "71044" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "RUFFANO" );
		comuni.put( "75064" , tmpComune );
		terradileucabis.put( "75064" , tmpComune );
		comuni.put( "72037" , getComune_completo_cod_istat( listaComuni , "RUTIGLIANO" ) );
		comuni.put( "72038" , getComune_completo_cod_istat( listaComuni , "RUVO DI PUGLIA" ) );
		tmpComune = getComune_completo_cod_istat( listaComuni , "SALICE SALENTINO" );
		comuni.put( "75065" , tmpComune );
		nordsalento.put( "75065" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "SALVE" );
		comuni.put( "75066" , tmpComune );
		terradileuca.put( "75066" , tmpComune );
		comuni.put( "72039" , getComune_completo_cod_istat( listaComuni , "SAMMICHELE DI BARI" ) );
		tmpComune = getComune_completo_cod_istat( listaComuni , "SAN CASSIANO" );
		comuni.put( "75095" , tmpComune );
		terreMezzo.put( "75095" , tmpComune );
		comuni.put( "75068" , getComune_completo_cod_istat( listaComuni , "SAN CESARIO DI LECCE" ) );
		comuni.put( "74013" , getComune_completo_cod_istat( listaComuni , "SAN DONACI" ) );
		comuni.put( "75069" , getComune_completo_cod_istat( listaComuni , "SAN DONATO DI LECCE" ) );
		tmpComune = getComune_completo_cod_istat( listaComuni , "SAN FERDINANDO DI PUGLIA" );
		comuni.put( "10007" , tmpComune );
		provBt.put( "10007" , tmpComune );
		comuni.put( "73024" , getComune_completo_cod_istat( listaComuni , "SAN GIORGIO IONICO" ) );
		tmpComune = getComune_completo_cod_istat( listaComuni , "SAN GIOVANNI ROTONDO" );
		comuni.put( "71046" , tmpComune );
		provFg.put( "71046" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "SAN MARCO IN LAMIS" );
		comuni.put( "71047" , tmpComune );
		provFg.put( "71047" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "SAN MARCO LA CATOLA" );
		comuni.put( "71048" , tmpComune );
		provFg.put( "71048" , tmpComune );
		comuni.put( "73025" , getComune_completo_cod_istat( listaComuni , "SAN MARZANO DI SAN GIUSEPPE" ) );
		comuni.put( "74014" , getComune_completo_cod_istat( listaComuni , "SAN MICHELE SALENTINO" ) );
		tmpComune = getComune_completo_cod_istat( listaComuni , "SAN NICANDRO GARGANICO" );
		comuni.put( "71049" , tmpComune );
		provFg.put( "71049" , tmpComune );
		comuni.put( "74015" , getComune_completo_cod_istat( listaComuni , "SAN PANCRAZIO SALENTINO" ) );
		tmpComune = getComune_completo_cod_istat( listaComuni , "SAN PAOLO DI CIVITATE" );
		comuni.put( "71050" , tmpComune );
		provFg.put( "71050" , tmpComune );
		comuni.put( "75071" , getComune_completo_cod_istat( listaComuni , "SAN PIETRO IN LAMA" ) );
		comuni.put( "74016" , getComune_completo_cod_istat( listaComuni , "SAN PIETRO VERNOTICO" ) );
		tmpComune = getComune_completo_cod_istat( listaComuni , "SAN SEVERO" );
		comuni.put( "71051" , tmpComune );
		provFg.put( "71051" , tmpComune );
		comuni.put( "74017" , getComune_completo_cod_istat( listaComuni , "SAN VITO DEI NORMANNI" ) );
		tmpComune = getComune_completo_cod_istat( listaComuni , "SANARICA" );
		comuni.put( "75067" , tmpComune );
		terreMezzo.put( "75067" , tmpComune );		
		comuni.put( "72040" , getComune_completo_cod_istat( listaComuni , "SANNICANDRO DI BARI" ) );
		comuni.put( "75070" , getComune_completo_cod_istat( listaComuni , "SANNICOLA" ) );
		tmpComune = getComune_completo_cod_istat( listaComuni , "SANT`AGATA DI PUGLIA" );
		comuni.put( "71052" , tmpComune );
		provFg.put( "71052" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "SANTA CESAREA TERME" );
		comuni.put( "75072" , tmpComune );
		costaOrientale.put( "75072" , tmpComune );
		comuni.put( "72041" , getComune_completo_cod_istat( listaComuni , "SANTERAMO IN COLLE" ) );
		comuni.put( "73026" , getComune_completo_cod_istat( listaComuni , "SAVA" ) );
		comuni.put( "75073" , getComune_completo_cod_istat( listaComuni , "SCORRANO" ) );
		comuni.put( "75074" , getComune_completo_cod_istat( listaComuni , "SECLI" ) );
		tmpComune = getComune_completo_cod_istat( listaComuni , "SERRACAPRIOLA" );
		comuni.put( "71053" , tmpComune );
		provFg.put( "71053" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "SOGLIANO CAVOUR" );
		comuni.put( "75075" , tmpComune );
		greciasal.put( "75075" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "SOLETO" );
		comuni.put( "75076" , tmpComune );
		greciasal.put( "75076" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "SPECCHIA" );
		comuni.put( "75077" , tmpComune );
		/// rimosso terradileucabis.put( "75077" , tmpComune );
		terradileuca.put( "75077" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "SPINAZZOLA" );
		comuni.put( "10008" , tmpComune );
		provBt.put( "10008" , tmpComune );
		comuni.put( "75078" , getComune_completo_cod_istat( listaComuni , "SPONGANO" ) );		
		tmpComune = getComune_completo_cod_istat( listaComuni , "SQUINZANO" );
		comuni.put( "75079" , tmpComune );		
		comuneLecce.put( "75079" , tmpComune );		
		nordsalento.put( "75079" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "STATTE" );
		comuni.put( "73029" , tmpComune );
		terraGravine.put( "73029" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "STERNATIA" );
		comuni.put( "75080" , tmpComune );
		greciasal.put( "75080" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "STORNARA" );
		comuni.put( "71054" , tmpComune );
		provFg.put( "71054" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "STORNARELLA" );
		comuni.put( "71055" , tmpComune );
		provFg.put( "71055" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "SUPERSANO" );
		comuni.put( "75081" , tmpComune );
		terreMezzo.put( "75081" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "SURANO" );
		comuni.put( "75082" , tmpComune );
		terreMezzo.put( "75082" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "SURBO" );
		comuni.put( "75083" , tmpComune );
		nordsalento.put( "75083" , tmpComune );
		comuni.put( "73027" , getComune_completo_cod_istat( listaComuni , "TARANTO" ) );
		comuni.put( "75084" , getComune_completo_cod_istat( listaComuni , "TAURISANO" ) );
		tmpComune = getComune_completo_cod_istat( listaComuni , "TAVIANO" );
		comuni.put( "75085" , tmpComune );
		jonicasalentina.put( "75085" , tmpComune );
		comuni.put( "72043" , getComune_completo_cod_istat( listaComuni , "TERLIZZI" ) );
		tmpComune = getComune_completo_cod_istat( listaComuni , "TIGGIANO" );
		comuni.put( "75086" , tmpComune );
		terradileuca.put( "75086" , tmpComune );
		comuni.put( "74018" , getComune_completo_cod_istat( listaComuni , "TORCHIAROLO" ) );
		comuni.put( "72044" , getComune_completo_cod_istat( listaComuni , "TORITTO" ) );
		comuni.put( "74019" , getComune_completo_cod_istat( listaComuni , "TORRE SANTA SUSANNA" ) );
		tmpComune = getComune_completo_cod_istat( listaComuni , "TORREMAGGIORE" );
		comuni.put( "71056" , tmpComune );
		provFg.put( "71056" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "TORRICELLA" );
		comuni.put( "73028" , tmpComune );
		terremaresole.put( "73028" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "TRANI" );
		comuni.put( "10009" , tmpComune );
		provBt.put( "10009" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "TREPUZZI" );
		comuni.put( "75087" , tmpComune );
		comuneLecce.put( "75087" , tmpComune );		
		nordsalento.put( "75087" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "TRICASE" );
		comuni.put( "75088" , tmpComune );
		talassa.put( "75088" , tmpComune );
		comuni.put( "72046" , getComune_completo_cod_istat( listaComuni , "TRIGGIANO" ) );
		tmpComune = getComune_completo_cod_istat( listaComuni , "TRINITAPOLI" );
		comuni.put( "10010" , tmpComune );
		provBt.put( "10010" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "TROIA" );
		comuni.put( "71058" , tmpComune );
		provFg.put( "71058" , tmpComune );
		comuni.put( "75089" , getComune_completo_cod_istat( listaComuni , "TUGLIE" ) );
		comuni.put( "72047" , getComune_completo_cod_istat( listaComuni , "TURI" ) );
		comuni.put( "75090" , getComune_completo_cod_istat( listaComuni , "UGENTO" ) );
		comuni.put( "75091" , getComune_completo_cod_istat( listaComuni , "UGGIANO LA CHIESA" ) );
		comuni.put( "72048" , getComune_completo_cod_istat( listaComuni , "VALENZANO" ) );
		comuni.put( "75092" , getComune_completo_cod_istat( listaComuni , "VEGLIE" ) );
		comuni.put( "75093" , getComune_completo_cod_istat( listaComuni , "VERNOLE" ) );
		tmpComune = getComune_completo_cod_istat( listaComuni , "VICO DEL GARGANO" );
		comuni.put( "71059" , tmpComune );
		provFg.put( "71059" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "VIESTE" );
		comuni.put( "71060" , tmpComune );
		provFg.put( "71060" , tmpComune );
		comuni.put( "74020" , getComune_completo_cod_istat( listaComuni , "VILLA CASTELLI" ) );
		tmpComune = getComune_completo_cod_istat( listaComuni , "VOLTURARA APPULA" );
		comuni.put( "71061" , tmpComune );
		provFg.put( "71061" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "VOLTURINO" );
		comuni.put( "71062" , tmpComune );
		provFg.put( "71062" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "ZAPPONETA" );
		comuni.put( "71064" , tmpComune );
		provFg.put( "71064" , tmpComune );
		tmpComune = getComune_completo_cod_istat( listaComuni , "ZOLLINO" );
		comuni.put( "75094" , tmpComune );
		greciasal.put( "75094" , tmpComune );
		
	}
	
	/**
	 * getCodiceProcedimento restituisce la descrizione del tipo di procedimento
	 * associata alla pratica .
	 *
	 * @param codicePratica -- arriva il codice fornito da civilia "AUTPAE-numero-anno"
	 * @return the codice procedimento
	 */
	public String getCodiceProcedimento( String codicePratica )
	{
		_logger.debug( "MapComuniEntiDelegati.getCodiceProcedimento codicePratica " + codicePratica );
		
		String return_ = "";
		try
		{
			int index = codicePratica.indexOf( "-" );
			if( index > 0 )
			{
				String tmp = codicePratica.substring( 0 , index );
				CharSequence s = new String( "AP" );
				boolean found = tmp.contains( s );
				if( found == true ) // si tratta di una AP dello schema CS
				{
					tmp = tmp.substring( 2 );
					return_ = tmp;
				}
				// else
				// altrimenti non so di che pratica si tratti
				// potrebbe trattarsi di una pratica dello schema 016 Regionale
				// de devo far vedere tutto
			}
		}
		catch( Exception e )
		{
			_logger.error( "getCodiceProcedimento Errore " + e.getMessage() );
		}
		
		_logger.debug( "MapComuniEntiDelegati.getCodiceProcedimento return_ " + return_ );		
		return return_;
	}
	
	/**
	 * Convert map to list.
	 *
	 * @param unioneMap the unione map
	 * @return List<Comuni_completo_cod_istat>
	 */
	private List<Comuni_completo_cod_istat> convertMapToList( Map<String , Comuni_completo_cod_istat> unioneMap )
	{
		List<Comuni_completo_cod_istat> listaComuni = new ArrayList<Comuni_completo_cod_istat>();
		try
		{
			// Converting HashMap Values into ArrayList
			Collection<Comuni_completo_cod_istat> comuni_completo_cod_istat_values = unioneMap.values();
			listaComuni = new ArrayList<Comuni_completo_cod_istat>( comuni_completo_cod_istat_values );
		}
		catch( Exception e )
		{
			_logger.error( "MapComuniEntiDelegati.convertMapToList Errore " + e.getMessage() );
		}
		return listaComuni;
	}
	
	/**
	 * in base al codice della pratica, mi dice se si tratta di una unione di
	 * comuni oppure no .
	 *
	 * @param codicePratica - formato civilia AP72002-3-2013
	 * @return true, if is unico comune
	 */
	public boolean isUnicoComune( String codicePratica )
	{
		_logger.debug( "MapComuniEntiDelegati.isUnicoComune codicePratica " + codicePratica );
		
		boolean isUnicoComune = false;
		
		String codiceProcedimento = getCodiceProcedimento( codicePratica );
		
		if( codiceProcedimento.length() == 0 ) // si tratta di Regione Puglia
		{
			isUnicoComune = false;
		}
		// si tratta di Provincia di foggia
		else if( codiceProcedimento.compareTo( "071" ) == 0 ) 
		{
			isUnicoComune = false;
		}
		// si tratta di provincia di BAT
		else if( codiceProcedimento.compareTo( "110" ) == 0 ) 
		{
			isUnicoComune = false;
		}
		// si tratta di terradileucabis
		else if( codiceProcedimento.compareTo( "20001" ) == 0 ) 
		{
			isUnicoComune = false;
		}
		// si tratta di entroterraidruntino
		else if( codiceProcedimento.compareTo( "20002" ) == 0 ) 
		{
			isUnicoComune = false;
		}
		// si tratta di terremaresole
		else if( codiceProcedimento.compareTo( "20003" ) == 0 ) 
		{
			isUnicoComune = false;
		}
		// si tratta di jonicasalentina
		else if( codiceProcedimento.compareTo( "20004" ) == 0 ) 
		{
			isUnicoComune = false;
		}
		// si tratta di greciasal
		else if( codiceProcedimento.compareTo( "20005" ) == 0 ) 
		{
			isUnicoComune = false;
		}
		// si tratta di terradileuca
		else if( codiceProcedimento.compareTo( "20006" ) == 0 ) 
		{
			isUnicoComune = false;
		}
		// si tratta di talassa
		else if( codiceProcedimento.compareTo( "20007" ) == 0 ) 
		{
			isUnicoComune = false;
		}
		// si tratta di nord salento
		else if( codiceProcedimento.compareTo( "20008" ) == 0 ) 
		{
			isUnicoComune = false;
		}
		// si tratta di terra delle gravine
		else if( codiceProcedimento.compareTo( "20009" ) == 0 ) 
		{
			isUnicoComune = false;
		}
		// si tratta di costa orientale
		else if( codiceProcedimento.compareTo( "20010" ) == 0 ) 
		{
			isUnicoComune = false;
		}		
		// si tratta di Comune di lecce , con particelle di squinzano e trepuzi
		else if( codiceProcedimento.compareTo( "75035" ) == 0 ) 
		{
			isUnicoComune = false;
		}		
		else
		{
			isUnicoComune = true;
		}
		
		_logger.debug( "MapComuniEntiDelegati.isUnicoComune isUnicoComune " + isUnicoComune );
		return isUnicoComune;
	}
		
	/**
	 * in base al codice della pratica, mi dice se si tratta di una unione di
	 * comuni oppure no . e restituisce la lista di comuni che appartengono alla
	 * unione.
	 * 
	 * @param codicePratica
	 *            - formato civilia AP72002/3/2013
	 * @return List<Comuni_completo_cod_istat>
	 */
	public List<Comuni_completo_cod_istat> getListaComuni_ByCodicePratica( String codicePratica, List<Comuni_completo_cod_istat> listaComuniCompleto )
	{
		_logger.debug( "MapComuniEntiDelegati.getListaComuni_ByCodicePratica codicePratica " + codicePratica );		
		List<Comuni_completo_cod_istat> listaComuni = new ArrayList<Comuni_completo_cod_istat>();;
		try
		{
			String codiceProcedimento = getCodiceProcedimento( codicePratica );
			// si tratta di Regione Puglia
			if( codiceProcedimento.length() == 0 )   
			{
				listaComuni = convertMapToList( this.comuni );
			}
			// si tratta di  Provincia di foggia
			else if( codiceProcedimento.compareTo( "071" ) == 0 ) 
			{
				listaComuni = convertMapToList( this.provFg );
			}
			// si tratta di Provincia di BAT
			else if( codiceProcedimento.compareTo( "110" ) == 0 ) 
			{
				listaComuni = convertMapToList( this.provBt );
			}
			// si tratta di terradileucabis
			else if( codiceProcedimento.compareTo( "20001" ) == 0 ) 
			{
				listaComuni = convertMapToList( this.terradileucabis );
			}
			// si tratta di entroterraidruntino
			else if( codiceProcedimento.compareTo( "20002" ) == 0 ) 
			{
				listaComuni = convertMapToList( this.entroterraidruntino );
			}
			// si tratta di terremaresole
			else if( codiceProcedimento.compareTo( "20003" ) == 0 )  
			{
				listaComuni = convertMapToList( this.terremaresole );
			}
			// si tratta di  jonicasalentina
			else if( codiceProcedimento.compareTo( "20004" ) == 0 )  
			{
				listaComuni = convertMapToList( this.jonicasalentina );
			}
			// si tratta di greciasal
			else if( codiceProcedimento.compareTo( "20005" ) == 0 ) 
			{
				listaComuni = convertMapToList( this.greciasal );
			}
			// si tratta di terradileuca
			else if( codiceProcedimento.compareTo( "20006" ) == 0 ) 														// 
			{
				listaComuni = convertMapToList( this.terradileuca );
			}
			// si tratta di talassa
			else if( codiceProcedimento.compareTo( "20007" ) == 0 ) 
			{
				listaComuni = convertMapToList( this.talassa );
			}
			// si tratta di nord salento 
			else if( codiceProcedimento.compareTo( "20008" ) == 0 ) 
			{
				listaComuni = convertMapToList( this.nordsalento );
			}
			// si tratta di terra delle gravine
			else if( codiceProcedimento.compareTo( "20009" ) == 0 ) 
			{
				listaComuni = convertMapToList( this.terraGravine );
			}
			// si tratta di costa orientale
			else if( codiceProcedimento.compareTo( "20010" ) == 0 ) 
			{
			    listaComuni = convertMapToList( this.costaOrientale );
			}
			// si tratta di terre di mezzo
			else if( codiceProcedimento.compareTo( "20011" ) == 0 ) 
			{
			    listaComuni = convertMapToList( this.terreMezzo );
			}
			// si tratta del comune di lecce, con trepuzzi e squinzano
			else if( codiceProcedimento.compareTo( "75035" ) == 0 ) 
			{
				listaComuni = convertMapToList( this.comuneLecce );
			}
			else
			{				
				Comuni_completo_cod_istat tmpComune = comuni.get( codiceProcedimento );
				
				// si tratta di un comune singolo
				listaComuni.add( tmpComune );
			}
			
		}
		catch( Exception e )
		{
			_logger.error( "MapComuniEntiDelegati.getlistaComuni Errore" + e.getMessage() );
		}
		
		return listaComuni;
	}
	
	/*
	 * restituisce la lista dei comuni di competenza dell'ente delegato di cui viene passato il codice
	 * Es: 00016 per regione Puglia
	 */
	public List<Comuni_completo_cod_istat> getListaComuni_ByCodiceEnteDelegato( String codiceEnte )
	{
		//Prendo solo la prima parte del codice ente 
		List<Comuni_completo_cod_istat> listaComuni = new ArrayList<Comuni_completo_cod_istat>();
		try
		{
			// si tratta di Regione Puglia
			if( codiceEnte.compareTo("00016") == 0 )   
			{
				listaComuni = convertMapToList( this.comuni );
			}
			// si tratta di  Provincia di foggia
			else if( codiceEnte.compareTo( "071" ) == 0 ) 
			{
				listaComuni = convertMapToList( this.provFg );
			}
			// si tratta di Provincia di BAT
			else if( codiceEnte.compareTo( "110" ) == 0 ) 
			{
				listaComuni = convertMapToList( this.provBt );
			}
			// si tratta di terradileucabis
			else if( codiceEnte.compareTo( "20001" ) == 0 ) 
			{
				listaComuni = convertMapToList( this.terradileucabis );
			}
			// si tratta di entroterraidruntino
			else if( codiceEnte.compareTo( "20002" ) == 0 ) 
			{
				listaComuni = convertMapToList( this.entroterraidruntino );
			}
			// si tratta di terremaresole
			else if( codiceEnte.compareTo( "20003" ) == 0 )  
			{
				listaComuni = convertMapToList( this.terremaresole );
			}
			// si tratta di  jonicasalentina
			else if( codiceEnte.compareTo( "20004" ) == 0 )  
			{
				listaComuni = convertMapToList( this.jonicasalentina );
			}
			// si tratta di greciasal
			else if( codiceEnte.compareTo( "20005" ) == 0 ) 
			{
				listaComuni = convertMapToList( this.greciasal );
			}
			// si tratta di terradileuca
			else if( codiceEnte.compareTo( "20006" ) == 0 )
			{
				listaComuni = convertMapToList( this.terradileuca );
			}
			// si tratta di talassa
			else if( codiceEnte.compareTo( "20007" ) == 0 ) 
			{
				listaComuni = convertMapToList( this.talassa );
			}
			// si tratta di nord salento 
			else if( codiceEnte.compareTo( "20008" ) == 0 ) 
			{
				listaComuni = convertMapToList( this.nordsalento );
			}
			// si tratta di terra delle gravine
			else if( codiceEnte.compareTo( "20009" ) == 0 ) 
			{
				listaComuni = convertMapToList( this.terraGravine );
			}
			// si tratta di costa orientale
			else if( codiceEnte.compareTo( "20010" ) == 0 ) 
			{
				listaComuni = convertMapToList( this.costaOrientale );
			}			
			// si tratta di terre di mezzo
			else if( codiceEnte.compareTo( "20011" ) == 0 ) 
			{
				listaComuni = convertMapToList( this.terreMezzo );
			}			
			// si tratta del comune di lecce, con trepuzzi e squinzano
			else if( codiceEnte.compareTo( "75035" ) == 0 ) 
			{
				listaComuni = convertMapToList( this.comuneLecce );
			}
			else
			{				
				Comuni_completo_cod_istat tmpComune = comuni.get( codiceEnte );
				
				// si tratta di un comune singolo
				listaComuni.add( tmpComune );
			}
			
		}
		catch( Exception e )
		{
			_logger.error( "MapComuniEntiDelegati.getlistaComuni Errore" + e.getMessage() );
		}
		
		return listaComuni;
	}
}
