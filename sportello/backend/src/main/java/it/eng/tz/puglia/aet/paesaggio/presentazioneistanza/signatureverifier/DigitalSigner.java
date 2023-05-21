/**
* DigitalSigner class which exposes methods for digital signing via hardware token.
*
*/

package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.signatureverifier;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.Security;
import java.security.cert.CertStore;
import java.security.cert.CertStoreException;
import java.security.cert.Certificate;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;

import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.ldap.LdapName;

import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.Store;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DigitalSigner
{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DigitalSigner.class);
	public static final String VERSION = "1.0";
	
	private static final String PKCS11_KEYSTORE_TYPE = "PKCS11";
	private static final String SUN_PKCS11_PROVIDER_CLASS = "sun.security.pkcs11.SunPKCS11";

	private static DigitalSigner instance;
	
	private String sigProvider;
	private String slot, password, libPath;
	private boolean debugEnabled;
	private int lastError;

	/**
	 * Singleton getter
	 */
	public static DigitalSigner getSingleton()
	{
		if (instance == null)
			instance = new DigitalSigner();
		return instance;
	}
	
	private DigitalSigner()
	{
		// Add provider
		Provider bc = new BouncyCastleProvider();
		sigProvider = bc.getName();
		Security.addProvider(bc);
		
		// Initialize last error variable to default value
		lastError = 0;
		slot = "0";
	}

	/**
	 * Gets the slot unit. By default it's "0"
	 *
	 * @return	the slot unit.
	 */
	public String getSlot()
	{
		return slot;
	}

	/**
	 * Sets the slot unit. By default it's "0"
	 *
	 * @param url an absolute URL giving the base location of the image
	 */
	public void setSlot(String slot)
	{
		this.slot = slot;
	}

	/**
	 * Gets the password for the token acquiring.
	 *
	 * @return the string represeting the password.
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * Sets the password for the token acquiring.
	 *
	 * @param password the string represeting the password.
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}

	/**
	 * Gets the library path setted previously.
	 *
	 * @return	the string represeting the library path.
	 */
	public String getLibPath()
	{
		return libPath;
	}

	/**
	 * Sets the library path.
	 *
	 * @param libPath the absolute file location on host machine where implementation library is located.
	 */
	public void setLibPath(String libPath)
	{
		this.libPath = libPath;
	}

	/**
	 * Check if debug is enabled.
	 *
	 * @return	boolean value denoting if debug was enabled.
	 */
	public boolean isDebugEnabled()
	{
		return debugEnabled;
	}

	/**
	 * Enable or disable debug logging.
	 *
	 * @param debugEnabled boolean value to enable or disable debug logging.
	 */
	public void setDebugEnabled(boolean debugEnabled)
	{
		this.debugEnabled = debugEnabled;
	}

	/**
	 * Returns the original content from a signed data, which can be
	 * read from a file or whatever function. It must be CMS signed. 
	 *
	 * @param signedData byte array denoting the full signed data.
	 * @return file
	 */
	public byte[] extractSignedContent(byte[] signedData) throws IOException, CMSException
	{
		// Process signed data
        CMSSignedData signature = new CMSSignedData(signedData);
        return (byte[]) signature.getSignedContent().getContent();
	}

	/**
	 * Sign the provided data, using the specified library path, slot
	 * and password for token acquiring. Data is signed via hardware.
	 * If data could not be signed, @null is returned.
	 *
	 * @param data byte array denoting the data to be signed.
	 * @return the full signed data with available certificate.
	 */
//	public byte[] signData(byte[] data)
//	{
//		byte[] signedData = null;
//		try
//		{
//			PrivateKeyAndCertChain privateKeyAndCertChain = extractCertificate();
//			signedData = sign(data, privateKeyAndCertChain.mPrivateKey, privateKeyAndCertChain.mCert, privateKeyAndCertChain.mCertificationChain);
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//
//		removeProviderPKCS11();
//		return signedData;
//	}

	/**
	 * Gets the last error message encountered from a previously called procedure.
	 * Error is taken from PKCS#11 Provider Log, so it can be different from hardware
	 * to hardware and from the choosen library.
	 *
	 * @return string representing a description of the last error message encountered.
	 */
	public String getLastErrorMessage()
	{
		if (lastError == 1)
			return "La Smart-Card è bloccata";
		else if (lastError == 2)
			return "Il PIN non è corretto";
		else if (lastError == 3)
			return "- La Smart-Card non è connessa\n- É già in uso da un altro programma\n- Non hai selezionato il driver corretto del costruttore della Smart-Card\n\nSe sei sicuro che sia tutto al suo posto, prova a riavviare il programma. Lo trovi nel percorso:\n" + System.getProperty("java.class.path").replaceAll("\\;\\.$", "");
		else if (lastError == 4)
			return "KeyStore PKCS11 non trovato. Controllare che la Firma Digitale\nnon sia danneggiata con il programma ufficiale del costruttore.\n\nSe sei sicuro che sia tutto al suo posto, prova a riavviare il programma. Lo trovi nel percorso:\n" + System.getProperty("java.class.path").replaceAll("\\;\\.$", "");
		else if (lastError == 5)
			return "La Smart-Card non è stata rilevata nello slot specificato.";
		return "Errore sconosciuto";
	}

	/**
	 * Loads the entire KeyStore from available hardware, using specified library path,
	 * slot and password. Certificate and certification chain should be always available,
	 * while private key can be available or not. It depends if it's obtainable. If not,
	 * @null is assigned to the private key field or returned KeyStore. 
	 *
	 * @return KeyStore containing certificate, certification chain and private key.
	 */
	public KeyStore loadKeyStorePKCS11() throws GeneralSecurityException, IOException
	{
		lastError = 0;
		removeProviderPKCS11();
		ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();

    	if (debugEnabled)
    	{
    		System.out.println("libPath: " + libPath);
    		System.out.println("slot: " + slot);
    	}
		
		PrintStream printstream = new PrintStream(bytearrayoutputstream);
		printstream.println("name = FonzTechSC" + System.currentTimeMillis());
		printstream.println("library = " + libPath);
		printstream.println("slot = " + slot);
		printstream.println("showInfo = " + (debugEnabled ? "true" : "false"));
		printstream.close();
		ByteArrayInputStream confStream = new ByteArrayInputStream(bytearrayoutputstream.toByteArray());

		// Add new provider
		Provider pkcs11Provider = null;
		try
		{
			Class<?> sunPkcs11Class = Class.forName(SUN_PKCS11_PROVIDER_CLASS);
			Constructor<?> pkcs11Constr = sunPkcs11Class.getConstructor(InputStream.class);
			pkcs11Provider = (Provider) pkcs11Constr.newInstance(confStream);
			sigProvider = pkcs11Provider.getName();
			Security.addProvider(pkcs11Provider);
		}
		catch (Exception e)
		{
			lastError = stackTraceToString(e).indexOf("CKR_SLOT_ID_INVALID") != -1 ? 5 : 3;
			throw new KeyStoreException("Can not initialize Sun PKCS#11 security provider.\nReason: " + e.toString());
		}

		char[] pin = password.toCharArray();
		KeyStore keyStore = null;
		try
		{
			keyStore = KeyStore.getInstance(PKCS11_KEYSTORE_TYPE, pkcs11Provider);
			keyStore.load(null, pin);
			// keyStore = KeyStore.Builder.newInstance(PKCS11_KEYSTORE_TYPE,
			// pkcs11Provider, new
			// KeyStore.PasswordProtection(pin)).getKeyStore();
		}
		catch (Exception e)
		{
			LOGGER.error("Error in loadKeyStorePKCS11",e);
			String stack = stackTraceToString(e);
			if (stack.indexOf("CKR_PIN_LOCKED") != -1)
				lastError = 1;
			else if (stack.indexOf("CKR_PIN_INCORRECT") != -1)
				lastError = 2;
			else if (stack.indexOf("PKCS11 not found") != -1)
				lastError = 4;
			else if (stack.indexOf("CKR_SLOT_ID_INVALID") != -1)
				lastError = 5;
			return null;
		}
		return keyStore;
	}

	/**
	 * This method can be used to verify CMS sign on data. It returns
	 * all the sginer info available on the provided data, plus the flag
	 * to check if sign is valid or not. Check VerifySignature class for
	 * further informations.
	 *
	 * @param data byte array denoting signed data.
	 * @return container of .
	 */
	public VerifySignature verify(InputStream is) throws CMSException, NoSuchAlgorithmException, NoSuchProviderException, CertStoreException, Exception
	{
		
		Security.addProvider(new BouncyCastleProvider());
		VerifySignature               vs      = new VerifySignature();
        CMSSignedData                 cms     = new CMSSignedData(is);
        Store<X509CertificateHolder>  store   = cms.getCertificates(); 
        SignerInformationStore        signers = cms.getSignerInfos(); 
        Collection<SignerInformation> c       = signers.getSigners(); 
        Iterator<SignerInformation>   it      = c.iterator();
        int verified = 0;
        while (it.hasNext()) { 
            SignerInformation signer = it.next(); 
            Collection<X509CertificateHolder> certCollection = store.getMatches(signer.getSID()); 
            Iterator<X509CertificateHolder>   certIt         = certCollection.iterator();
            X509CertificateHolder             certHolder     = certIt.next();
            X509Certificate                   cert           = new JcaX509CertificateConverter().setProvider("BC").getCertificate(certHolder);

            vs.addSubject(cert.getSubjectDN().getName());
            vs.addIssuer (cert.getIssuerDN ().getName());
            
            if(cert.getNotAfter().before(new java.util.Date())) {
            	LOGGER.info("Certificato scaduto");
        	}else if (signer.verify(new JcaSimpleSignerInfoVerifierBuilder().setProvider("BC").build(cert))) {
            	verified++;
            }
        }
        if (verified == c.size())
        	vs.setVerified(true);

        return vs;
	}

	/**
	 * Get alias for accessing Carta Nazionale Dei Servizi (CNS) on provided KeyStore.
	 * which should be obtained from hardware via the apposite function available in
	 * this library. @null is returned if no correct alias was found.
	 *
	 * @param keyStore KeyStore containing requried certificates.
	 * @return the string representing the alias.
	 */
    public String getAliasForCNS(KeyStore keyStore) throws GeneralSecurityException
    {
    	if (debugEnabled)
    		System.out.println("getPrivateKeyAndCertChain(): aKeyStore.size(): = " + keyStore.size());
        
        for (Enumeration<String> aliasesEnum = keyStore.aliases(); aliasesEnum.hasMoreElements();)
        {
            String alias = (String)aliasesEnum.nextElement();
            Certificate cert = keyStore.getCertificate(alias);
 
            String certName = null;
            try
            {
                certName = extractStrictCommonName(((X509Certificate) cert).getSubjectDN().getName());
            }
            catch (Exception e)
            {
                certName = null;
            }
            
            if (debugEnabled)
            	System.out.println("getPrivateKeyAndCertChain(): Enumera keystore - alias: " + alias + ", certName: " + certName);
 
            if (keyStore.size() == 1 || (certName != null && certName.contains("/")))
            	return alias;
        }
        return null;
    }

	private CertStore getCertStore(Certificate[] certificationChain) throws GeneralSecurityException
	{
		ArrayList<Certificate> list = new ArrayList<Certificate>();
		Certificate[] certificates = certificationChain;
		for (int i = 0, length = certificates == null ? 0 : certificates.length; i < length; i++)
			list.add(certificates[i]);
		return CertStore.getInstance("Collection", new CollectionCertStoreParameters(list), "BC");
	}

//	@SuppressWarnings("deprecation")
//	private byte[] sign(byte[] data, PrivateKey privateKey, Certificate cert, Certificate[] certificationChain) throws Exception
//	{
////		CMSSignedData signedDataOrig = null;
////		CMSProcessable content = null;
////
////		String digestAlg = CMSSignedGenerator.DIGEST_SHA256;
////		String encryptionAlg = CMSSignedGenerator.ENCRYPTION_RSA;
////		if ((privateKey instanceof RSAPrivateKey) || "RSA".equalsIgnoreCase(privateKey.getAlgorithm()))
////		{
////			digestAlg = CMSSignedGenerator.DIGEST_SHA256;
////			encryptionAlg = CMSSignedGenerator.ENCRYPTION_RSA;
////		}
////		else
////		{
////			if ((privateKey instanceof DSAPrivateKey) || "DSA".equalsIgnoreCase(privateKey.getAlgorithm()))
////			{
////				digestAlg = CMSSignedGenerator.DIGEST_SHA1;
////				encryptionAlg = CMSSignedGenerator.ENCRYPTION_DSA;
////			}
////		}
////
////		CMSSignedDataGenerator generator = new CMSSignedDataGenerator();
////
////		if (digestAlg.equals(CMSSignedGenerator.DIGEST_SHA1))
////			generator.addSigner(privateKey, (X509Certificate) cert, digestAlg);
////		else
////		{
////			AttributeTable sAttr = null;
////			AttributeTable unsAttr = null;
////
////			ASN1Set signedAttr = null;
////
////			DERObjectIdentifier derobjectidentifier = PKCSObjectIdentifiers.data;
////			CMSProcessable cmsprocessable = signedDataOrig != null ? signedDataOrig.getSignedContent()
////					: new CMSProcessableByteArray(data);
////
////			MessageDigest messagedigest = MessageDigest.getInstance(digestAlg, "BC");
////			cmsprocessable.write(new DigOutputStream(messagedigest));
////			byte abyte0[] = messagedigest.digest();
////
////			DEREncodableVector derencodablevector1 = new DEREncodableVector();
////			derencodablevector1.add(new Attribute(CMSAttributes.contentType, new DERSet(derobjectidentifier)));
////			derencodablevector1.add(new Attribute(CMSAttributes.signingTime, new DERSet(new DERUTCTime(new Date()))));
////			derencodablevector1.add(new Attribute(CMSAttributes.messageDigest, new DERSet(new DEROctetString(abyte0))));
////			byte[] encodedCert = cert.getEncoded();
////			MessageDigest md = MessageDigest.getInstance("SHA-256");
////			md.update(encodedCert);
////			byte[] certDigest = md.digest();
////			ASN1InputStream ais = new ASN1InputStream(encodedCert);
////			DERObject derObj = ais.readObject();
////			ASN1Sequence asn1Seq = (ASN1Sequence) derObj;
////			ais.close();
////			X509CertificateStructure x509CStructure = new X509CertificateStructure(asn1Seq);
////			X509Name x509Name = x509CStructure.getIssuer();
////			GeneralName generalName = new GeneralName(x509Name);
////			GeneralNames generalNames = new GeneralNames(generalName);
////			DERInteger serialNum = x509CStructure.getSerialNumber();
////			IssuerSerial issuerserial = new IssuerSerial(generalNames, serialNum);
////			AlgorithmIdentifier aiSha256 = new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha256);
////			ESSCertIDv2 essCert1 = new ESSCertIDv2(aiSha256, certDigest, issuerserial);
////			SigningCertificateV2 scv2 = new SigningCertificateV2(new ESSCertIDv2[] { essCert1 });
////			derencodablevector1.add(new Attribute(org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_aa_signingCertificateV2, new DERSet(scv2)));
////
////			signedAttr = new DERSet(derencodablevector1);
////
////			sAttr = new AttributeTable(signedAttr);
////
////			generator.addSigner(privateKey, (X509Certificate) cert, encryptionAlg, digestAlg, sAttr, unsAttr);
////		}
////
////		generator.addCertificatesAndCRLs(getCertStore(certificationChain));
////
////		content = new CMSProcessableByteArray(data);
////		CMSSignedData signedData = generator.generate(content, true, sigProvider);
////		return signedData.getEncoded();
//		//TODO
//		return null;
//	}

	private PrivateKeyAndCertChain extractCertificate() throws GeneralSecurityException, IOException
	{
		KeyStore keyStore = loadKeyStorePKCS11();
		return getPrivateKeyAndCertChain(keyStore);
	}

	private void removeProviderPKCS11()
	{
		if (sigProvider != null && sigProvider.startsWith("SunPKCS11-"))
			Security.removeProvider(sigProvider);
	}

    private PrivateKeyAndCertChain getPrivateKeyAndCertChain(KeyStore aKeyStore) throws GeneralSecurityException
    {
    	if (debugEnabled)
    		System.out.println("getPrivateKeyAndCertChain(): aKeyStore.size(): = " + aKeyStore.size());
    	
        for (Enumeration<String> aliasesEnum = aKeyStore.aliases(); aliasesEnum.hasMoreElements();)
        {
            String alias = (String)aliasesEnum.nextElement();
            Certificate cert = aKeyStore.getCertificate(alias);
 
            String certName = null;
            try
            {
                certName = extractStrictCommonName(((X509Certificate)cert).getSubjectDN().getName());
            }
            catch (Exception e)
            {
                certName = null;
            }

        	if (debugEnabled)
        		System.out.println("getPrivateKeyAndCertChain(): Enumera keystore - alias: " + alias + ", certName: " + certName);
 
            if ((certName != null && !certName.contains("/")) || (aKeyStore.size() == 1))
            {
                Certificate[] certificationChain = aKeyStore.getCertificateChain(alias);
                PrivateKey privateKey = (PrivateKey) aKeyStore.getKey(alias, password.toCharArray());
                PrivateKeyAndCertChain result = new PrivateKeyAndCertChain(privateKey, cert, certificationChain);
                return result;
                 
            }
        }
 
        throw new KeyStoreException("Nessuna chiave di firma valida trovata.");
    }
 
    private String extractStrictCommonName(String subjectName) throws NamingException
    {
        Properties nameComps = extractPropsFromSubjectName(subjectName);
        return(nameComps.getProperty("CN"));
    }
 
    private Properties extractPropsFromSubjectName(String subjectName) throws NamingException
    {
        Name name = new LdapName(subjectName.toUpperCase());
        Properties nameComps = new Properties();
        String comp;
        int pos;
        for (Enumeration<String> cenum = name.getAll(); cenum.hasMoreElements(); nameComps.setProperty(comp.substring(0, pos), comp.substring(pos + 1)))
        {
            comp = (String)cenum.nextElement();
            pos = comp.indexOf('=');
        }
        return(nameComps);
    }

    // Entire eexception stack trace to string
    private String stackTraceToString(Exception e)
    {
    	StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	LOGGER.error("convert stacktrace to string ",e);
    	return sw.toString();
    }

	// Wrapper class for message digest stream
	private class DigOutputStream extends OutputStream
	{
		private MessageDigest dig;

		private DigOutputStream(MessageDigest messagedigest)
		{
			dig = messagedigest;
		}

		@Override
		public void write(byte data[], int i, int j) throws IOException
		{
			dig.update(data, i, j);
		}

		@Override
		public void write(int i) throws IOException
		{
			dig.update((byte) i);
		}
	}
}
