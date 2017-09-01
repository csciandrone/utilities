package it.cfCalculator.model;
/**
 * <p>
 * Raccordo tra identificativo del mese e lettera utilizzata 
 * nel calcolo del codice fiscale.
 * </p>
 * @author csciandrone
 *
 */
public enum CalendarForCf {
	GENNAIO(1,"A"),
	FEBBRAIO(2,"B"),
	MARZO(3,"C"),
	APRILE(4,"D"),
	MAGGIO(5,"E"),
	GIUGNO(6,"H"),
	LUGLIO(7,"L"),
	AGOSTO(8,"M"),
	SETTEMBRE(9,"P"),
	OTTOBRE(10,"R"),
	NOVEMBRE(11,"S"),
	DICEMBRE(12,"T");
	private CalendarForCf(int numero,String valore){
		this.numero=numero;
		this.valore=valore;
	}
	private String valore;
	private int numero;
	public String getValore() {
		return valore;
	}
	public int getNumero() {
		return numero;
	}
    
}
