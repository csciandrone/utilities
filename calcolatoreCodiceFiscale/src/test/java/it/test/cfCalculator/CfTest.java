package it.test.cfCalculator;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Test;

import it.cfCalculator.CfGenerator;
import it.cfCalculator.CfGeneratorImpl;
import it.cfCalculator.model.CfInputRequest;
import it.cfCalculator.model.Sesso;
import it.cfCalculator.util.Util;



public class CfTest {

	public CfTest() {
	}
	@Test
	public void testRecuperoCodiciCatastali(){
		Assert.assertEquals("L780", Util.getCodiceCatastale("VEROLI"));
		Assert.assertEquals("A123", Util.getCodiceCatastale("ALATRI"));
		Assert.assertEquals("H501", Util.getCodiceCatastale("ROMA"));
		Assert.assertEquals("L774", Util.getCodiceCatastale("VERNAZZA"));
		Assert.assertEquals("A006", Util.getCodiceCatastale("ABBADIA SAN SALVATORE"));
		Assert.assertEquals("M376", Util.getCodiceCatastale("ABETONE CUTIGLIANO"));
		Assert.assertEquals("M103", Util.getCodiceCatastale("VO'"));
	}
	
	@Test
	public void testCarattereControllo(){
		Assert.assertEquals("V", Util.getCarattereDiControllo("SCNCRL78D20A123"));
	}
	@Test
	public void testCompleto(){
		CfGenerator c=CfGeneratorImpl.getInstance();
		Assert.assertEquals("RSSMRA80A01G224W", c.generaCodiceFiscale(new CfInputRequest("MARIO", "ROSSI", Sesso.M, Util.creaData(1980, 1, 1), "PADOVA")));
		Assert.assertEquals("GHRGNN45C14A001V", c.generaCodiceFiscale(new CfInputRequest("GIOVANNI", "GHERSI", Sesso.M, Util.creaData(1945, 3, 14), "ABANO TERME")));
		Assert.assertEquals("CNLCLD68C56H501V", c.generaCodiceFiscale(new CfInputRequest("CLAUDIA", "CANALE", Sesso.F, Util.creaData(1968, 3, 16), "ROMA")));
	}
	@Test
	public void testGeneraNome() throws Exception{
		
		Method[] metodi=CfGeneratorImpl.class.getDeclaredMethods();
		for(int i=0;i<metodi.length;i++){
			Method method = metodi[i];
			
			if("genera3Caratteri".equals(method.getName())){
				method.setAccessible(true);
				Constructor<?>[] declaredConstructors = CfGeneratorImpl.class.getDeclaredConstructors();
				Constructor<?> costruttoreVuoto = declaredConstructors[0];
				costruttoreVuoto.setAccessible(true);
				Object istanza = costruttoreVuoto.newInstance();
				Assert.assertEquals("CRL",(String)method.invoke(istanza, "CARLO",new Boolean(true)));
				Assert.assertEquals("MRN",(String)method.invoke(istanza, "MARINA",new Boolean(true)));
				Assert.assertEquals("GUO",(String)method.invoke(istanza, "UGO",new Boolean(true)));
				Assert.assertEquals("JOX",(String)method.invoke(istanza, "JO",new Boolean(true)));
				Assert.assertEquals("FPP",(String)method.invoke(istanza, "FILIPPO",new Boolean(true)));
			}
			
			
		}
		
		
	}
	
	
	@Test
	public void testGeneraCognome() throws Exception{
		
		Method[] metodi=CfGeneratorImpl.class.getDeclaredMethods();
		for(int i=0;i<metodi.length;i++){
			Method method = metodi[i];
			
			if("genera3Caratteri".equals(method.getName())){
				method.setAccessible(true);
				Constructor<?>[] declaredConstructors = CfGeneratorImpl.class.getDeclaredConstructors();
				Constructor<?> costruttoreVuoto = declaredConstructors[0];
				costruttoreVuoto.setAccessible(true);
				Object istanza = costruttoreVuoto.newInstance();
				Assert.assertEquals("SCN",(String)method.invoke(istanza, "SCIANDRONE",new Boolean(false)));
				Assert.assertEquals("RSS",(String)method.invoke(istanza, "ROSSI",new Boolean(false)));
				Assert.assertEquals("FOX",(String)method.invoke(istanza, "FO",new Boolean(false)));
				Assert.assertEquals("JOX",(String)method.invoke(istanza, "JO",new Boolean(false)));
				Assert.assertEquals("DLP",(String)method.invoke(istanza, "DEL PORTO",new Boolean(false)));
			}
			
			
		}
	}

}
