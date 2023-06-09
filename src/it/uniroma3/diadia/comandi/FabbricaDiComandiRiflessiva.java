package it.uniroma3.diadia.comandi;

import java.util.Scanner;

import it.uniroma3.diadia.IO;

public class FabbricaDiComandiRiflessiva implements FabbricaDiComandi {

	@SuppressWarnings("deprecation")
	@Override
	public Comando costruisciComando(String istruzione, IO io)  {
		Comando comando = null;
		if (istruzione == null)
			return new ComandoNonValido();
		Scanner scannerDiParole = new Scanner(istruzione);
		String nomeComando = null;
		String parametro = null;


		// Gestione casi limite
		if (istruzione.equals("")) {
			comando = new ComandoNonValido();
			comando.setIO(io);
			scannerDiParole.close();
			return comando;
		}


		// Verifica che il comando abbia una lunghezza di 1/2 parole
		if (scannerDiParole.hasNext())
			nomeComando = scannerDiParole.next();
		if (scannerDiParole.hasNext())
			parametro = scannerDiParole.next();
		if (scannerDiParole.hasNext()) {
			scannerDiParole.close();
			return new ComandoNonValido();
		}
		scannerDiParole.close();
		String nomeClasse = new StringBuilder("it.uniroma3.diadia.comandi.Comando")
				.append(Character.toUpperCase(nomeComando.charAt(0)))
				.append(nomeComando.substring(1))
				.toString();
		try {
			comando = (Comando) Class.forName(nomeClasse).newInstance();
		} catch(Exception e) {
			comando = new ComandoNonValido();
		}
		comando.setParametro(parametro);
		comando.setIO(io);
		return comando;
	}
}
