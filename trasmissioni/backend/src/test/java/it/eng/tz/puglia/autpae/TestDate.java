package it.eng.tz.puglia.autpae;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import it.eng.tz.puglia.autpae.utility.CheckSumUtil;

public class TestDate {

	@Test
	public void fromDateToLocaldate() throws ParseException {
		SimpleDateFormat sm=new SimpleDateFormat("dd/MM/yyyy");
		Date d=sm.parse("17/12/2020");
		d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	  System.out.println(d);
	}
	
	@Test
	public void testSha() throws IOException {
		File f=new File("C:\\tmp\\autpae\\autpae\\MIGRAZIONE_AUTPAE\\AP72043-12-2017\\Allegati\\1750690\\F_1750688_Gadaleta_Domenico.zip");
		System.out.println(CheckSumUtil.getCheckSum(f));
	}
	

}
