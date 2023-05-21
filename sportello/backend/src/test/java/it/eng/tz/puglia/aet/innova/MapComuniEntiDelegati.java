package it.eng.tz.puglia.aet.innova;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;





/**
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
	
	
	/**
	 * esportazione dalla TNO_PORTAL_INTEGRATION del DB ORACLE
	 * SELECT COD_COM,COD_ISTAT,DENOMINAZIONE FROM TNO_PORTAL_INTEGRATION_PROD.SIT_COMUNI sc ;
	 */
	private final static String comuniStr="C514|071020|CERIGNOLA#C633|071021|CHIEUTI#D269|071022|DELICETO#D459|071023|FAETO#D643|071024|FOGGIA#E332|071025|ISCHITELLA#E363|071026|ISOLE TREMITI#E549|071027|LESINA#E716|071028|LUCERA#E885|071029|MANFREDONIA#E946|110005|MARGHERITA DI SAVOIA#F059|071031|MATTINATA#F538|071032|MONTELEONE DI PUGLIA#F631|071033|MONTE SANT`ANGELO#F777|071034|MOTTA MONTECORVINO#G125|071035|ORSARA DI PUGLIA#G131|071036|ORTA NOVA#G312|071037|PANNI#G487|071038|PESCHICI#G604|071039|PIETRAMONTECORVINO#G761|071040|POGGIO IMPERIALE#H287|071041|RIGNANO GARGANICO#H467|071042|ROCCHETTA SANT`ANTONIO#H480|071043|RODI GARGANICO#H568|071044|ROSETO VALFORTORE#H839|071045|SAN FERDINANDO DI PUGLIA#H926|071046|SAN GIOVANNI ROTONDO#H985|071047|SAN MARCO IN LAMIS#H986|071048|SAN MARCO LA CATOLA#I054|071049|SAN NICANDRO GARGANICO#I072|071050|SAN PAOLO DI CIVITATE#I158|071051|SAN SEVERO#I193|071052|SANT`AGATA DI PUGLIA#I641|071053|SERRACAPRIOLA#I962|071054|STORNARA#I963|071055|STORNARELLA#L273|071056|TORREMAGGIORE#B915|110010|TRINITAPOLI#L447|071058|TROIA#L842|071059|VICO DEL GARGANO#L858|071060|VIESTE#M131|071061|VOLTURARA APPULA#M132|071062|VOLTURINO#M266|071063|ORDONA#M267|071064|ZAPPONETA#B180|074001|BRINDISI#B809|074002|CAROVIGNO#C424|074003|CEGLIE MESSAPICA#C448|074004|CELLINO SAN MARCO#C741|074005|CISTERNINO#D422|074006|ERCHIE#D508|074007|FASANO#D761|074008|FRANCAVILLA FONTANA#E471|074009|LATIANO#F152|074010|MESAGNE#G098|074011|ORIA#G187|074012|OSTUNI#H822|074013|SAN DONACI#I045|074014|SAN MICHELE SALENTINO#I066|074015|SAN PANCRAZIO SALENTINO#I119|074016|SAN PIETRO VERNOTICO#I396|074017|SAN VITO DEI NORMANNI#L213|074018|TORCHIAROLO#L280|074019|TORRE SANTA SUSANNA#L920|074020|VILLA CASTELLI#A662|072006|BARI#L049|073027|TARANTO.#F531|073016|MONTEIASI#H882|073024|SAN GIORGIO IONICO#A042|075001|ACQUARICA DEL CAPO#H047|075062|PRESICCE#M428|075098|PRESICCE-ACQUARICA#A048|072001|ACQUAVIVA DELLE FONTI#A055|072002|ADELFIA#A149|072003|ALBEROBELLO#A225|072004|ALTAMURA#A285|110001|ANDRIA#A669|110002|BARLETTA#A874|072008|BINETTO#A883|110003|BISCEGLIE#A892|072010|BITETTO#A893|072011|BITONTO#A894|072012|BITRITTO#B619|110004|CANOSA DI PUGLIA#B716|072014|CAPURSO#B923|072015|CASAMASSIMA#B998|072016|CASSANO DELLE MURGE#C134|072017|CASTELLANA GROTTE#C436|072018|CELLAMARE#C975|072019|CONVERSANO#C983|072020|CORATO#E038|072021|GIOIA DEL COLLE#E047|072022|GIOVINAZZO#E155|072023|GRAVINA IN PUGLIA#E223|072024|GRUMO APPULA#E645|072025|LOCOROTONDO#F220|110006|MINERVINO MURGE#F262|072027|MODUGNO#F280|072028|MOLA DI BARI#F284|072029|MOLFETTA#F376|072030|MONOPOLI#F915|072031|NOCI#F923|072032|NOICATTARO#G291|072033|PALO DEL COLLE#G769|072034|POGGIORSINI#G787|072035|POLIGNANO A MARE#H096|072036|PUTIGNANO#H643|072037|RUTIGLIANO#H645|072038|RUVO DI PUGLIA#H749|072039|SAMMICHELE DI BARI#I053|072040|SANNICANDRO DI BARI#I330|072041|SANTERAMO IN COLLE#I907|110008|SPINAZZOLA#L109|072043|TERLIZZI#L220|072044|TORITTO#L328|110009|TRANI#L425|072046|TRIGGIANO#L472|072047|TURI#L571|072048|VALENZANO#A514|073001|AVETRANA#B808|073002|CAROSINO#C136|073003|CASTELLANETA#D171|073004|CRISPIANO#D463|073005|FAGGIANO#D754|073006|FRAGAGNANO#E036|073007|GINOSA#E205|073008|GROTTAGLIE#E469|073009|LATERZA#E537|073010|LEPORANO#E630|073011|LIZZANO#E882|073012|MANDURIA#E986|073013|MARTINA FRANCA#E995|073014|MARUGGIO#F027|073015|MASSAFRA#F531|073016|MONTEIASI#F563|073017|MONTEMESOLA#F587|073018|MONTEPARANO#F784|073019|MOTTOLA#G251|073020|PALAGIANELLO#G252|073021|PALAGIANO#H090|073022|PULSANO#H409|073023|ROCCAFORZATA#H882|073024|SAN GIORGIO IONICO#I018|073025|SAN MARZANO DI SAN GIUSEPPE#I467|073026|SAVA#L049|073027|TARANTO#L294|073028|TORRICELLA#M298|073029|STATTE#A184|075002|ALESSANO#A185|075003|ALEZIO#A208|075004|ALLISTE#A281|075005|ANDRANO#A350|075006|ARADEO#A425|075007|ARNESANO#A572|075008|BAGNOLO DEL SALENTO#B086|075009|BOTRUGNO#B413|075010|CALIMERA#B506|075011|CAMPI SALENTINA#B616|075012|CANNOLE#B690|075013|CAPRARICA DI LECCE#B792|075014|CARMIANO#B822|075015|CARPIGNANO SALENTINO#B936|075016|CASARANO#C334|075017|CASTRI DI LECCE#C335|075018|CASTRIGNANO DE` GRECI#C336|075019|CASTRIGNANO DEL CAPO#C377|075020|CAVALLINO#C865|075021|COLLEPASSO#C978|075022|COPERTINO#D006|075023|CORIGLIANO D`OTRANTO#D044|075024|CORSANO#D223|075025|CURSI#D237|075026|CUTROFIANO#D305|075027|DISO#D851|075028|GAGLIANO DEL CAPO#D862|075029|GALATINA#D863|075030|GALATONE#D883|075031|GALLIPOLI#E053|075032|GIUGGIANELLO#E061|075033|GIURDIGNANO#E227|075034|GUAGNANO#E506|075035|LECCE#E538|075036|LEQUILE#E563|075037|LEVERANO#E629|075038|LIZZANELLO#E815|075039|MAGLIE#E979|075040|MARTANO#E984|075041|MARTIGNANO#F054|075042|MATINO#F101|075043|MELENDUGNO#F109|075044|MELISSANO#F117|075045|MELPIGNANO#F194|075046|MIGGIANO#F221|075047|MINERVINO DI LECCE#F604|075048|MONTERONI DI LECCE#F623|075049|MONTESANO SALENTINO#F716|075050|MORCIANO DI LEUCA#F816|075051|MURO LECCESE#F842|075052|NARDO#F881|075053|NEVIANO#F916|075054|NOCIGLIA#F970|075055|NOVOLI#G136|075056|ORTELLE#G188|075057|OTRANTO#G285|075058|PALMARIGGI#G325|075059|PARABITA#G378|075060|PATU#G751|075061|POGGIARDO#H147|075063|RACALE#H632|075064|RUFFANO#H708|075065|SALICE SALENTINO#H729|075066|SALVE#H757|075067|SANARICA#H793|075068|SAN CESARIO DI LECCE#H826|075069|SAN DONATO DI LECCE#I059|075070|SANNICOLA#I115|075071|SAN PIETRO IN LAMA#I172|075072|SANTA CESAREA TERME#I549|075073|SCORRANO#I559|075074|SECLI#I780|075075|SOGLIANO CAVOUR#I800|075076|SOLETO#I887|075077|SPECCHIA#I923|075078|SPONGANO#I930|075079|SQUINZANO#I950|075080|STERNATIA#L008|075081|SUPERSANO#L010|075082|SURANO#L011|075083|SURBO#L064|075084|TAURISANO#L074|075085|TAVIANO#L166|075086|TIGGIANO#L383|075087|TREPUZZI#L419|075088|TRICASE#L462|075089|TUGLIE#L484|075090|UGENTO#L485|075091|UGGIANO LA CHIESA#L711|075092|VEGLIE#L776|075093|VERNOLE#M187|075094|ZOLLINO#M264|075095|SAN CASSIANO#M261|075096|CASTRO#M263|075097|PORTO CESAREO#A015|071001|ACCADIA#A150|071002|ALBERONA#A320|071003|ANZANO DI PUGLIA#A339|071004|APRICENA#A463|071005|ASCOLI SATRIANO#A854|071006|BICCARI#B104|071007|BOVINO#B357|071008|CAGNANO VARANO#B584|071009|CANDELA#B724|071010|CARAPELLE#B784|071011|CARLANTINO#B829|071012|CARPINO#B904|071013|CASALNUOVO MONTEROTARO#B917|071014|CASALVECCHIO DI PUGLIA#C198|071015|CASTELLUCCIO DEI SAURI#C202|071016|CASTELLUCCIO VALMAGGIORE#C222|071017|CASTELNUOVO DELLA DAUNIA#C429|071018|CELENZA VALFORTORE#C442|071019|CELLE DI SAN VITO#";
	
	public static List<Comuni_completo_cod_istat> getListaComuni(){
		List<Comuni_completo_cod_istat> list=new ArrayList<>();
		String[] comArray = comuniStr.split("#");
		for( String comuneStr:comArray) {
			String[] campiComune = comuneStr.split("\\|");
			Comuni_completo_cod_istat comObj = new Comuni_completo_cod_istat();
			comObj.setCodiceCatastale(campiComune[0]);
			comObj.setIstat6province(campiComune[1]);
			comObj.setComune(campiComune[2]);
			list.add(comObj);
		}
		return list;
	}
	
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
		terradileuca.put( "75046" , tmpComune ); // in data 29/09/2020 miggiano passa da terradileucabis a terradileuca
		// terradileucabis.put( "75046" , tmpComune );
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
		terradileuca.put( "75049" , tmpComune );  // in data 29/09/2020 montesano passa da terradileucabis a terradileuca
		// terradileucabis.put( "75049" , tmpComune );
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
		comuni.put( "75098" , getComune_completo_cod_istat( listaComuni , "PRESICCE-ACQUARICA" ) );				
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
		// terradileucabis.put( "75064" , tmpComune ); adesso ruffano si trova in associazione con ALEZIO e CASARANO
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
	
	public static void main(String[] args) throws Exception {
		List<Comuni_completo_cod_istat> listaComuni = getListaComuni();
		MapComuniEntiDelegati th = new MapComuniEntiDelegati(listaComuni);
		String[] entiDelegati= {"20001","20002","20003","20004","20005","20006",
				"20007","20008","20010","20011","071","110"};
		for(String ed:entiDelegati) {
			List<Comuni_completo_cod_istat> list = th.getListaComuni_ByCodiceEnteDelegato(ed);
			for(Comuni_completo_cod_istat com:list) {
				String query="INSERT INTO common.paesaggio_organizzazione_competenze " +
						"( paesaggio_organizzazione_id, ente_id, data_inizio_delega, data_fine_delega, codice_civilia) " +
						"select po.id,e.id,'1900-01-01', '2050-01-01', NULL from common.paesaggio_organizzazione po,common.ente e " + 
						"	where po.codice_civilia='"+ed+"' and e.tipo='CO' and e.codice='"+com.getCodiceCatastale() +"';";
						System.out.println(query);	
			}
			
			//select * from paesaggio_organizzazione po,common.ente e where po.codice_civilia=20001 and e.tipo='CO' and e.codice='A662'
		}
		
	}
	
}
