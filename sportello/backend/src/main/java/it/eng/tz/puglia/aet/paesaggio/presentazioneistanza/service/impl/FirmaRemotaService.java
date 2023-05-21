package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Base64;

import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.eng.tz.puglia.aet.firmaremota.bean.FirmaRemotaRequestBean;
import it.eng.tz.puglia.aet.firmaremota.bean.FirmaRemotaRequestFileBean;
import it.eng.tz.puglia.aet.firmaremota.bean.FirmaRemotaResponseBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.IFirmaRemotaService;
import it.eng.tz.puglia.service.http.IHttpFirmaRemotaClientService;

@Service
public class FirmaRemotaService implements IFirmaRemotaService {

	@Autowired
	IHttpFirmaRemotaClientService firmaRemotaSvc;
	
	@Override
	public File firmaDocumentoMock(MultipartFile file) throws Exception {
		FirmaRemotaRequestBean bean=new FirmaRemotaRequestBean();
		bean.setCodiceApplicazione("AUT-AMB");
		bean.setFirma("Tizio Caio");
		bean.setFlagVisible(false);
		bean.setReason("Per approvazione");
		bean.setRightX(700);
		bean.setRightY(180);
		bean.setLeftX(50);
		bean.setLeftY(765);
		bean.setPage(1);
		bean.setPassword("7676576576567");
		bean.setCodiceUtenteSc("575765675");
		bean.setOtp("765675765765765");
		bean.setFiles(new ArrayList<FirmaRemotaRequestFileBean>());
		FirmaRemotaRequestFileBean fileBean=new FirmaRemotaRequestFileBean();
		fileBean.setBase64(new String(Base64.getEncoder().encode(file.getBytes())));
		bean.getFiles().add(fileBean);
		FirmaRemotaResponseBean signedBean = firmaRemotaSvc.pades(bean);
		byte[] streamByte=Base64.getDecoder().decode(signedBean.getBase64Files().get(0));
		Path fileOut = Files.createTempFile(file.getOriginalFilename(), "signed");
		try(FileOutputStream os=new FileOutputStream(fileOut.toFile())){
			IOUtils.write(streamByte, os);	
		}
		return fileOut.toFile();
	}

}
