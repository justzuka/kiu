package lesson20240814;

import java.util.*;

public class PeptidesApproach2 implements IPeptides {

	public static final int DEFAULT_PEPTIDE_SIZE = 8;

	private String protein;
	private int peptideSize;

	// Now the kmers will store library peptides and their positions in the protein
	public HashMap<String, List<Integer>> peptideMap = new LinkedHashMap<>();

	private List<String> library;

	public PeptidesApproach2(int peptideSize, String protein, List<String> library) {
		this.peptideSize = peptideSize;
		this.protein = protein;
		this.library = library;
		populatePeptideMap();

	}

	// Populate the map with peptides from the library
	void populatePeptideMap() {
		for (String peptide : library) {
			peptideMap.put(peptide, new ArrayList<>());
		}
	}


	void searchProteinForPeptides() {
		for (int i = 0; i < protein.length() - peptideSize + 1; i++) {
			String kmer = protein.substring(i, i + peptideSize);
			if (peptideMap.containsKey(kmer)) {
				peptideMap.get(kmer).add(i);
			}
		}
	}

	public List<Integer> search(String peptide) {
		searchProteinForPeptides();
		return peptideMap.getOrDefault(peptide, List.of());
	}

	public Map<String, List<Integer>> searchLibrary() {
		return peptideMap;
	}
}
