package lesson20240814;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Benchmark {
	
	private static final int PROTEIN_SIZE = 10_000;
	private static final int LIBRARY_SIZE = 1_000_000;

	static final byte[] ALPHABET = new byte[4];
	
	static {
		for (byte c = 'A'; c < 'A' + ALPHABET.length; c++) {
			ALPHABET[c-'A'] = c;
		}
	}

	public static void main(String[] args) {
		
		System.out.println("generating data...");
		
		String protein = generateProtein(PROTEIN_SIZE);
		List<String> library = generateLibrary(LIBRARY_SIZE);


		System.out.println("searching peptides...");
		long start = System.currentTimeMillis();
		IPeptides peptides1 = new PeptidesApproach1(PeptidesApproach1.DEFAULT_PEPTIDE_SIZE, protein, library);
		peptides1.searchLibrary();
		long stop = System.currentTimeMillis();

		System.out.println("Elapsed1: " + (stop - start));

		start = System.currentTimeMillis();
		IPeptides peptides2 = new PeptidesApproach2(PeptidesApproach2.DEFAULT_PEPTIDE_SIZE, protein, library);
		peptides2.searchLibrary();
		stop = System.currentTimeMillis();

		System.out.println("Elapsed2: " + (stop - start));

		start = System.currentTimeMillis();
		IPeptides peptides3 = new PeptidesApproach3(PeptidesApproach3.DEFAULT_PEPTIDE_SIZE, protein, library);
		peptides3.searchLibrary();
		stop = System.currentTimeMillis();

		System.out.println("Elapsed3: " + (stop - start));

		start = System.currentTimeMillis();
		IPeptides peptides4 = new PeptidesApproach4(PeptidesApproach3.DEFAULT_PEPTIDE_SIZE, protein, library);
		peptides4.searchLibrary();
		stop = System.currentTimeMillis();

		System.out.println("Elapsed3: " + (stop - start));
	}

	static String generateProtein(int proteinSize) {
		Random r = new Random();
		var data = new byte[proteinSize];
		for (int i = 0; i < proteinSize; i++) {
			data[i] = ALPHABET[r.nextInt(ALPHABET.length)];
		}
		return new String(data);
	}

	private static List<String> generateLibrary(int librarySize) {
		var library = new ArrayList<String>(librarySize);
		for (int i = 0; i < librarySize; i++) {
			var peptide = generateProtein(PeptidesApproach1.DEFAULT_PEPTIDE_SIZE);
			if (i == 0) System.out.println(peptide);
			library.add(peptide);
		}
		return library;
	}


}
