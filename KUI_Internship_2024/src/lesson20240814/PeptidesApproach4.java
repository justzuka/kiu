package lesson20240814;

import java.util.*;

public class PeptidesApproach4 implements IPeptides<Long> {

	public static final int DEFAULT_PEPTIDE_SIZE = 8;

	private String protein;
	private int peptideSize;

	// Array to store the long integer representation of peptides
	private long[] peptideArray;

	// List to store positions of found k-mers
	private Map<Long, List<Integer>> kmerPositions = new LinkedHashMap<>();

	private List<String> library;

	public PeptidesApproach4(int peptideSize, String protein, List<String> library) {
		this.peptideSize = peptideSize;
		this.protein = protein;
		this.library = library;
		populatePeptideArray();
		Arrays.sort(peptideArray);  // Sort the array of peptide longs
	}

	// Converts a peptide string into a unique long integer
	long convertToLong(String peptide) {
		long value = 0;
		for (char c : peptide.toCharArray()) {
			value = value * 31 + c;  // Prime number used for hash-like uniqueness
		}
		return value;
	}

	// Populate the array with peptides converted to long integers
	void populatePeptideArray() {
		peptideArray = new long[library.size()];
		for (int i = 0; i < library.size(); i++) {
			peptideArray[i] = convertToLong(library.get(i));
		}
	}

	// Binary search for a k-mer in the sorted array of peptide longs
	boolean searchPeptideInArray(long kmerAsLong) {
		return Arrays.binarySearch(peptideArray, kmerAsLong) >= 0;
	}

	// Sliding through the protein to find k-mers in the sorted peptide array
	void searchProteinForPeptides() {
		for (int i = 0; i < protein.length() - peptideSize + 1; i++) {
			String kmer = protein.substring(i, i + peptideSize);
			long kmerAsLong = convertToLong(kmer);
			if (searchPeptideInArray(kmerAsLong)) {
				kmerPositions.computeIfAbsent(kmerAsLong, k -> new ArrayList<>()).add(i);
			}
		}
	}

	// Get the positions of k-mers found in the protein
	public Map<Long, List<Integer>> searchLibrary() {
		searchProteinForPeptides();
		return kmerPositions;
	}
}
