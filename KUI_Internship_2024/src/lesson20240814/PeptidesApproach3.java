package lesson20240814;

import java.util.*;

public class PeptidesApproach3 implements IPeptides<Long> {

	public static final int DEFAULT_PEPTIDE_SIZE = 8;

	private String protein;
	private int peptideSize;

	// Now the peptideMap will store the peptide's long integer representation and their positions
	public HashMap<Long, List<Integer>> peptideMap = new LinkedHashMap<>();

	private List<String> library;

	public PeptidesApproach3(int peptideSize, String protein, List<String> library) {
		this.peptideSize = peptideSize;
		this.protein = protein;
		this.library = library;
		populatePeptideMap();
	}

	// Converts a peptide string into a unique long integer
	long convertToLong(String peptide) {
		long value = 0;
		for (char c : peptide.toCharArray()) {
			value = value * 31 + c; // Prime number used for hash-like uniqueness
		}
		return value;
	}

	// Populate the map with peptides converted to long integers
	void populatePeptideMap() {
		for (String peptide : library) {
			long peptideAsLong = convertToLong(peptide);
			peptideMap.put(peptideAsLong, new ArrayList<>());
		}
	}

	// Sliding through the protein to find k-mers in the peptideMap, but k-mers are now long integers
	void searchProteinForPeptides() {
		for (int i = 0; i < protein.length() - peptideSize + 1; i++) {
			String kmer = protein.substring(i, i + peptideSize);
			long kmerAsLong = convertToLong(kmer);
			if (peptideMap.containsKey(kmerAsLong)) {
				peptideMap.get(kmerAsLong).add(i);
			}
		}
	}

//	public List<Integer> search(String peptide) {
//		searchProteinForPeptides();
//		long peptideAsLong = convertToLong(peptide);
//		return peptideMap.getOrDefault(peptideAsLong, List.of());
//	}

	public Map<Long, List<Integer>> searchLibrary() {
		searchProteinForPeptides();
		return peptideMap;
	}
}
