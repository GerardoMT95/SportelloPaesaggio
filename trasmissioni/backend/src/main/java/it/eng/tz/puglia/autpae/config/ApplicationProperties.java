package it.eng.tz.puglia.autpae.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import it.eng.tz.puglia.util.crypt.CryptUtil;

@Component
@ConfigurationProperties
public class ApplicationProperties {

//	@Value("${email.registration.sender}")
//	private String registrationSender;
//	
//	@Value("${email.registration.password}")
//	private String registrationEmailPassword;
	
//	@Value("${email.sampling.notification.sender}")
//	@Value("${email.registration.sender}")
//	private String samplingNotificationSender;
	
//	@Value("${email.sampling.notification.password}")
//	@Value("${email.registration.password}")
//	private String samplingNotificationPassword;
	
//	@Value("${email.sampling.sender}")
//	@Value("${email.registration.sender}")
//	private String samplingSender;
	
//	@Value("${email.sampling.password}")
//	@Value("${email.registration.password}")
//	private String samplingPassword;
	
//	@Value("${email.verification.notification.sender}")
//	@Value("${email.registration.sender}")
//	private String verificationNotificationSender;

//	@Value("${email.verification.notification.password}")
//	@Value("${email.registration.password}")
//	private String verificationNotificationPassword;

//	@Value("${email.verification.sender}")
//	@Value("${email.registration.sender}")
//	private String verificationSender;

//	@Value("${email.verification.password}")
//	@Value("${email.registration.password}")
//	private String verificationPassword;

//	@Value("${email.hostname}")
//	private String emailHostName;
//	
//	@Value("${email.smtp.port}")
//	private int emailSmtpPort;
//	
//	@Value("${email.regional.operator}")
//	private String regionalOperatorEmail;
//	
//	@Value("${email.administrator}")
//	private String administratorEmail;
	
	@Value("${spring.application.name}")
	private String codiceApplicazione;
	
	@Value("${pm.codice.applicazione}")
	private String codiceApplicazioneProfile;
	
	@Value("${wso2.username.batchusr}")
	private String batchUsr;
	
	@Value("${autpae.vistaPubblica.integrataInIstruttoria.enable}")
	private String vistaPubblicaIntegrata;
	
	
	public boolean getVistaPubblicaIntegrata() {
		return vistaPubblicaIntegrata!=null && vistaPubblicaIntegrata.equalsIgnoreCase("S");
	}
	
	public String getBatchUsr() {
		return CryptUtil.decrypt(batchUsr);
	}

	public String getCodiceApplicazioneProfile() {
		return codiceApplicazioneProfile;
	}

	public String getCodiceApplicazione() {
		return codiceApplicazione;
	}
	
	@Value("${geo.layer.autpae.editing.description}") 
	private String layerEdAupDes;
	@Value("${geo.layer.autpae.editing.descrizioneEstesa}")
	private String layerEdAupDesEst;
	@Value("${geo.layer.autpae.editing.coloreArea}")
	private String layerEdAupCol;
	@Value("${geo.layer.autpae.publishing.description}")
	private String layerPubAupDes;
	@Value("${geo.layer.autpae.publishing.descrizioneEstesa}")
	private String layerPubAupDesEst;
	@Value("${geo.layer.autpae.publishing.coloreArea}")
	private String layerPubAupCol;
	@Value("${geo.layer.pareri.editing.description}")
	private String layerEdParDes;
	@Value("${geo.layer.pareri.editing.descrizioneEstesa}")
	private String layerEdParDesEst;
	@Value("${geo.layer.pareri.editing.coloreArea}")
	private String layerEdParCol;
	@Value("${geo.layer.pareri.publishing.description}")
	private String layerPubParDes;
	@Value("${geo.layer.pareri.publishing.descrizioneEstesa}")
	private String layerPubParDesEst;
	@Value("${geo.layer.pareri.publishing.coloreArea}")
	private String layerPubParCol;
	@Value("${geo.layer.putt.editing.description}")
	private String layerEdPutDes;
	@Value("${geo.layer.putt.editing.descrizioneEstesa}")
	private String layerEdPutDesEst;
	@Value("${geo.layer.putt.editing.coloreArea}")
	private String layerEdPutCol;
	@Value("${geo.layer.putt.publishing.description}")
	private String layerPubPutDes;
	@Value("${geo.layer.putt.publishing.descrizioneEstesa}")
	private String layerPubPutDesEst;
	@Value("${geo.layer.putt.publishing.coloreArea}")
	private String layerPubPutCol;
	

	public String getLayerEditingDes() {
		if(isPareri()) {
			return layerEdParDes;	
		}else if(isPutt()) {
			return layerEdPutDes;
		}else {
			return layerEdAupDes;
		}
	}
	
	public String getLayerEditingDesEst() {
		if(isPareri()) {
			return layerEdParDesEst;	
		}else if(isPutt()) {
			return layerEdPutDesEst;
		}else {
			return layerEdAupDesEst;
		}
	}
	
	public String getLayerEditingCol() {
		if(isPareri()) {
			return layerEdParCol;	
		}else if(isPutt()) {
			return layerEdPutCol;
		}else {
			return layerEdAupCol;
		}
	}
	
	public String getLayerPublishedDes() {
		if(isPareri()) {
			return layerPubParDes;	
		}else if(isPutt()) {
			return layerPubPutDes;
		}else {
			return layerPubAupDes;
		}
	}
	
	
	public String getLayerPublishedCol() {
		if(isPareri()) {
			return layerPubParCol;	
		}else if(isPutt()) {
			return layerPubPutCol;
		}else {
			return layerPubAupCol;
		}
	}
	
	public String getLayerPublishedDesEst() {
		if(isPareri()) {
			return layerPubParDesEst;	
		}else if(isPutt()) {
			return layerPubPutDesEst;
		}else {
			return layerPubAupDesEst;
		}
	}
	
	
	public boolean isAutPae() {
		return codiceApplicazione.equals("autpae");
	}
	
	public boolean isPareri() {
		return codiceApplicazione.equals("pareri");
	}

	public boolean isPutt() {
		return codiceApplicazione.equals("putt");
	}
	
	
//	public int getSamplingChangeLimit() {
//		return samplingChangeLimit;
//	}
//
//	public void setSamplingChangeLimit(int samplingChangeLimit) {
//		this.samplingChangeLimit = samplingChangeLimit;
//	}
//
//	public short getMaxPercentage() {
//		return maxPercentage;
//	}
//
//	public void setMaxPercentage(short maxPercentage) {
//		this.maxPercentage = maxPercentage;
//	}
//
//	public short getMinPercentage() {
//		return minPercentage;
//	}
//
//	public void setMinPercentage(short minPercentage) {
//		this.minPercentage = minPercentage;
//	}
//
//	public int getDaysOfSamplingProccess() {
//		return daysOfSamplingProccess;
//	}
//
//	public void setDaysOfSamplingProccess(int daysOfSamplingProccess) {
//		this.daysOfSamplingProccess = daysOfSamplingProccess;
//	}
//
//	public List<Integer> getDaysOfPresamplingNotification() {
//		return daysOfPresamplingNotification;
//	}
//
//	public void setDaysOfPresamplingNotification(List<Integer> daysOfPresamplingNotification) {
//		this.daysOfPresamplingNotification = daysOfPresamplingNotification;
//	}
//
//	public int getDaysOfManualVerification() {
//		return daysOfManualVerification;
//	}
//
//	public void setDaysOfManualVerification(int daysOfManualVerification) {
//		this.daysOfManualVerification = daysOfManualVerification;
//	}
//
//	public List<Integer> getDaysOfVerificationNotification() {
//		return daysOfVerificationNotification;
//	}
//
//	public void setDaysOfVerificationNotification(List<Integer> daysOfVerificationNotification) {
//		this.daysOfVerificationNotification = daysOfVerificationNotification;
//	}

//	public String getRegistrationSender() {
//		return registrationSender;
//	}
//
//	public void setRegistrationSender(String registrationSender) {
//		this.registrationSender = registrationSender;
//	}
//
//	public String getRegistrationEmailPassword() {
//		return registrationEmailPassword;
//	}
//
//	public void setRegistrationEmailPassword(String registrationEmailPassword) {
//		this.registrationEmailPassword = registrationEmailPassword;
//	}
//
//	public String getSamplingNotificationSender() {
//		return samplingNotificationSender;
//	}
//
//	public void setSamplingNotificationSender(String samplingNotificationSender) {
//		this.samplingNotificationSender = samplingNotificationSender;
//	}
//
//	public String getSamplingNotificationPassword() {
//		return samplingNotificationPassword;
//	}
//
//	public void setSamplingNotificationPassword(String samplingNotificationPassword) {
//		this.samplingNotificationPassword = samplingNotificationPassword;
//	}
//
//	public String getSamplingSender() {
//		return samplingSender;
//	}
//
//	public void setSamplingSender(String samplingSender) {
//		this.samplingSender = samplingSender;
//	}
//
//	public String getSamplingPassword() {
//		return samplingPassword;
//	}
//
//	public void setSamplingPassword(String samplingPassword) {
//		this.samplingPassword = samplingPassword;
//	}
//
//	public String getVerificationNotificationSender() {
//		return verificationNotificationSender;
//	}
//
//	public void setVerificationNotificationSender(String verificationNotificationSender) {
//		this.verificationNotificationSender = verificationNotificationSender;
//	}
//
//	public String getVerificationNotificationPassword() {
//		return verificationNotificationPassword;
//	}
//
//	public void setVerificationNotificationPassword(String verificationNotificationPassword) {
//		this.verificationNotificationPassword = verificationNotificationPassword;
//	}
//
//	public String getVerificationSender() {
//		return verificationSender;
//	}
//
//	public void setVerificationSender(String verificationSender) {
//		this.verificationSender = verificationSender;
//	}
//
//	public String getVerificationPassword() {
//		return verificationPassword;
//	}
//
//	public void setVerificationPassword(String verificationPassword) {
//		this.verificationPassword = verificationPassword;
//	}
//
//	public String getEmailHostName() {
//		return emailHostName;
//	}
//
//	public void setEmailHostName(String emailHostName) {
//		this.emailHostName = emailHostName;
//	}
//
//	public int getEmailSmtpPort() {
//		return emailSmtpPort;
//	}
//
//	public void setEmailSmtpPort(int emailSmtpPort) {
//		this.emailSmtpPort = emailSmtpPort;
//	}
//
//	public String getRegionalOperatorEmail() {
//		return regionalOperatorEmail;
//	}
//
//	public void setRegionalOperatorEmail(String regionalOperatorEmail) {
//		this.regionalOperatorEmail = regionalOperatorEmail;
//	}
//
//	public String getAdministratorEmail() {
//		return administratorEmail;
//	}
//
//	public void setAdministratorEmail(String administratorEmail) {
//		this.administratorEmail = administratorEmail;
//	}
	
	public String schemaName() throws Exception {
		if (this.getCodiceApplicazione().equals("autpae") || this.getCodiceApplicazione().equals("pareri") || this.getCodiceApplicazione().equals("putt")) {
			return this.getCodiceApplicazione();
		} else {
			throw new Exception("Il nome applicazione ("+this.getCodiceApplicazione()+") non è compreso nei valori ammessi: autpae, pareri, putt");
		}
	}

}