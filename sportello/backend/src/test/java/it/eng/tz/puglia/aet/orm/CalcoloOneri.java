package it.eng.tz.puglia.aet.orm;

import java.text.NumberFormat;
import java.util.Locale;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.PresentazioneIstanzaApplication;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.IPagamentiService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PresentazioneIstanzaApplication.class)
public class CalcoloOneri {
	@Autowired
	IPagamentiService pagSvc;

	@Test
	public void calcolaOneri() {
		double[] importi=new double[] {1000,200_000,300_000,5_000_000,10_000_000,25_000_000};
		NumberFormat nf=NumberFormat.getCurrencyInstance(Locale.ITALIAN);
		for(double importo:importi) {
			System.out.println("progetto di importo: "+nf.format(importo)+
					"   oneri calcolati:"+nf.format(pagSvc.calcolaOneri(0, 1, importo)));
		}
	}
}
