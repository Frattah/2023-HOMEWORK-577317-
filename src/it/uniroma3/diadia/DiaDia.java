package it.uniroma3.diadia;
import java.util.Scanner;

import it.uniroma3.diadia.comandi.Comando;
import it.uniroma3.diadia.comandi.FabbricaDiComandi;
import it.uniroma3.diadia.comandi.FabbricaDiComandiRiflessiva;

/**
 * Classe principale di diadia, un semplice gioco di ruolo ambientato al dia.
 * Per giocare crea un'istanza di questa classe e invoca il letodo gioca
 *
 * Questa e' la classe principale crea e istanzia tutte le altre
 *
 * @author  docente di POO 
 *         (da un'idea di Michael Kolling and David J. Barnes) 
 *          
 * @version base
 */

public class DiaDia {
	

	static final private String MESSAGGIO_BENVENUTO = ""+
			"Ti trovi nell'Universita', ma oggi e' diversa dal solito...\n" +
			"Meglio andare al piu' presto in biblioteca a studiare. Ma dov'e'?\n"+
			"I locali sono popolati da strani personaggi, " +
			"alcuni amici, altri... chissa!\n"+
			"Ci sono attrezzi che potrebbero servirti nell'impresa:\n"+
			"puoi raccoglierli, usarli, posarli quando ti sembrano inutili\n" +
			"o regalarli se pensi che possano ingraziarti qualcuno.\n\n"+
			"Per conoscere le istruzioni usa il comando 'aiuto'.\n";

	private Partita partita;

	private IO inputOutputManager;
	
	public DiaDia(IO io) {
		this.partita = new Partita("files/lab.txt");
		this.inputOutputManager = io;
	}

	public void gioca() {
		String istruzione; 
		Scanner scanner = new Scanner(System.in);

		this.inputOutputManager.mostraMessaggio(MESSAGGIO_BENVENUTO);
		do {
			istruzione = this.inputOutputManager.leggiRiga(scanner);
		}
		while (!processaIstruzione(istruzione) && !partita.isFinita());
		scanner.close();
		if (partita.getGiocatore().getCfu() <= 0)
			this.inputOutputManager.mostraMessaggio("Hai esaurito i cfu...\n");
	}   


	/**
	 * Processa una istruzione 
	 *
	 * @return true se l'istruzione e' eseguita e il gioco continua, false altrimenti
	 * @throws Exception 
	 * 
	 */
	private boolean processaIstruzione(String istruzione)  {
		Comando comandoDaEseguire;
		FabbricaDiComandi factory = new FabbricaDiComandiRiflessiva();
		
		comandoDaEseguire = factory.costruisciComando(istruzione, this.inputOutputManager);
		comandoDaEseguire.esegui(this.partita);
		if (this.partita.vinta()) {
			this.inputOutputManager.mostraMessaggio("Hai vinto!\n");
			return true;
		} else
			return false;
	}   

	public static void main(String[] argc) {
		IO io = new IOConsole();
		DiaDia gioco = new DiaDia(io);
		gioco.gioca();
	}
}