package it.cfCalculator.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * Oggetto di input per il calcolo del codice fiscale.
 * </p>
 * @author csciandrone
 *
 */
public class CfInputRequest {
	private String nome;
	private String cognome;
	private Sesso sesso;
	private Date dataNascita;
	private String comune;

	public CfInputRequest(String nome, String cognome, Sesso sesso, Date dataNascita, String comune) {
		super();
		this.nome = nome;
		this.cognome = cognome;
		this.sesso = sesso;
		this.dataNascita = dataNascita;
		this.comune = comune;
	}

	public String getNome() {
		return nome;
	}

	public String getCognome() {
		return cognome;
	}

	public Sesso getSesso() {
		return sesso;
	}

	public Date getDataNascita() {
		return dataNascita;
	}

	public String getComune() {
		if(comune!=null){
			return comune.replaceAll(" ", "").trim();
		}
		return comune;
	}
	public boolean isValid(){
		if(isEmpty(this.cognome) || isEmpty(this.nome) || isEmpty(this.comune) || this.sesso==null || this.dataNascita==null){
			return false;
		}
		else{
			return true;
		}
	}
	private boolean isEmpty(String s) {
		if (s == null || s.trim().equals("")) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[nome=");
		builder.append(nome);
		builder.append(", cognome=");
		builder.append(cognome);
		builder.append(", sesso=");
		builder.append(sesso);
		builder.append(", dataNascita=");
		builder.append(new SimpleDateFormat("dd/MM/yyyy").format(dataNascita));
		builder.append(", comune=");
		builder.append(comune);
		builder.append("]");
		return builder.toString();
	}
	
}
