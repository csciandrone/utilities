package it.cfCalculator.main;

import java.io.File;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.cfCalculator.CfGenerator;
import it.cfCalculator.CfGeneratorImpl;
import it.cfCalculator.model.CfInputRequest;
import it.cfCalculator.model.Sesso;
import it.cfcalculator.randomGeneration.GenerazioneCodiciFiscali;
import it.cfcalculator.randomGeneration.GenerazioneCodiciFiscaliImpl;

public class ConsoleInvocation {

	private static final String DT_NASCITA = "d";
	private static final String PAESE = "p";
	private static final String SESSO = "s";
	private static final String COGNOME = "cog";
	private static final String NOME = "nom";
	private static final String NUMERO_CF = "n";
	private static final String MODALITA = "m";
	private static final String HELP = "h";
	private static final Logger LOGGER = LoggerFactory.getLogger(ConsoleInvocation.class);

	private ConsoleInvocation() {
	}

	public static void main(String[] args) throws Exception {
		LOGGER.debug("Start programma generazione codici fiscali");
		CommandLineParser clParser = new DefaultParser();
		Options options = options();
		CommandLine cl = clParser.parse(options, args, true);
		if (cl.hasOption(HELP)) {
			printHelp(options);
			return;
		}
		String optionValue = cl.getOptionValue(MODALITA);
		if ("1".equals(optionValue)) {
			boolean hasOptionN = cl.hasOption(NUMERO_CF);
			GenerazioneCodiciFiscali genCodFisc = new GenerazioneCodiciFiscaliImpl();
			String numeroCf = hasOptionN ? cl.getOptionValue(NUMERO_CF) : "10";
			Map<String, CfInputRequest> randomCf = genCodFisc.getRandomCf(Integer.parseInt(numeroCf));
			Set<String> keySet = randomCf.keySet();
			PrintWriter pw = new PrintWriter(new File("cfList.txt"));
			for (String cf : keySet) {
				pw.write(cf + " - " + randomCf.get(cf).toString());
				pw.write(System.getProperty("line.separator"));
			}
			pw.flush();
		} else if ("2".equals(optionValue)) {
			CfInputRequest cfInputRequst = getFromOptions(cl);
			if (cfInputRequst.isValid()) {
				CfGenerator cfGenerator = CfGeneratorImpl.getInstance();
				String cf = cfGenerator.generaCodiceFiscale(cfInputRequst);
				LOGGER.debug("Il codice fiscale calcolato e' {} ", cf);
			} else {
				LOGGER.error("Attenzione i valori forniti non sono corretti");
				printHelp(options);
			}

		} else {
			printHelp(options);
		}
	}

	private static void printHelp(Options options) {
		HelpFormatter formatter = new HelpFormatter();
		formatter.setOptionComparator(new Comparator<Option>() {
			
			@Override
			public int compare(Option o1, Option o2) {
				// insertion order
				return 0;
			}
		});
		formatter.printHelp(180,"java -jar calcolatoreCodiceFiscale.jar", "Istruzioni invocazione del tool", options,
				"Segnalare problemi a carlo.sciandrone@gmail.com", true);
	}

	private static CfInputRequest getFromOptions(CommandLine cl) throws ParseException {
		String nome = cl.getOptionValue(NOME);
		String cognome = cl.getOptionValue(COGNOME);
		String sessoValue = cl.getOptionValue(SESSO);
		String dataString = cl.getOptionValue(DT_NASCITA);
		String comune = cl.getOptionValue(PAESE);
		Sesso sesso = sessoValue!=null?Sesso.valueOf(sessoValue.toUpperCase()):null;
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
		Date dataNascita = dataString!=null?sdf.parse(dataString):null;
		CfInputRequest cfInputRequest = new CfInputRequest(nome, cognome, sesso, dataNascita, comune);
		return cfInputRequest;
	}

	public static Options options() {
		Options options = new Options();
		options.addOption(MODALITA, "modalita", true,
				"OBBLIGATORIO - Scrivere 1 se si vuole generare n codici fiscali arbitrari 2 se si vuole creare un codice fiscale");
		options.getOption(MODALITA).setRequired(true);
		options.addOption(NUMERO_CF, "numeroCf", true,
				"Il numero di cf da generare , da valorizzare solo se m=1, se non viene valorizzato vengono generati 10 codici fiscali. I codici fiscali sono visibili in console e in un file denominato cfList.txt creato allo stesso livello del jar.");
		options.addOption(NOME, "nome", true, "Il nome, da valorizzare solo se m=2");
		options.addOption(COGNOME, "cognome", true, "Il cognome, da valorizzare solo se m=2");
		options.addOption(SESSO, "sesso", true, "Il sesso, da valorizzare solo se m=2, valori ammessi M o F");
		options.addOption(PAESE, "paese", true, "Il paese di nascita, da valorizzare solo se m=2");
		options.addOption(DT_NASCITA, "data_nascita", true,
				"La data di nascita, da valorizzare solo se m=2, nel formato GGMMAAAA");
		options.addOption(HELP, "help", false, "Help online");
		return options;
	}

}
