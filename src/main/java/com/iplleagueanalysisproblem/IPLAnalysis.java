package com.iplleagueanalysisproblem;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;

import com.CSVBuilder.CSVBuilderException;
import com.CSVBuilder.CSVBuilderFactory;
import com.CSVBuilder.ICSVBuilder;
import com.google.gson.Gson;

public class IPLAnalysis {
	List<CSVRuns> runsCSVList = null;
	List<CSVWickets> wicketsCSVList = null;

	/**
	 * Loads csv file data and returns list of POJO objects
	 * 
	 * @param <k>
	 * @param filePath
	 * @param csvClass
	 * @return
	 * @throws CSVBuilderException
	 * @throws IOException
	 */
	private static <k> List<k> loadCSVData(String filePath, Class<k> csvClass) throws CSVBuilderException, IOException {
		Reader reader = Files.newBufferedReader(Paths.get(filePath)); // no such file exception
		ICSVBuilder<k> csvBuilder = CSVBuilderFactory.createCSVBuilder();
		return csvBuilder.getCSVFileList(reader, csvClass);
	}

	/**
	 * Loads csv file data and returns size of list of POJO objects
	 * 
	 * @param filePath
	 * @return
	 * @throws CSVBuilderException
	 * @throws IOException
	 */
	public int loadRunsCSV(String filePath) throws CSVBuilderException, IOException {
		runsCSVList = loadCSVData(filePath, CSVRuns.class);
		return runsCSVList.size();
	}

	/**
	 * Loads csv file data and returns size of list of POJO objects
	 * 
	 * @param filePath
	 * @return
	 * @throws CSVBuilderException
	 * @throws IOException
	 */
	public int loadWicketsCSV(String filePath) throws CSVBuilderException, IOException {
		wicketsCSVList = loadCSVData(filePath, CSVWickets.class);
		return wicketsCSVList.size();
	}

	/**
	 * UC 1 : returns top batting average
	 * 
	 * @param filePath
	 * @return
	 * @throws CSVBuilderException
	 * @throws IOException
	 */
	public double getTopBattingAvg() {
		double maxBattingAvg = runsCSVList.stream().map(entry -> entry.average).max(Double::compare).get();
		return maxBattingAvg;
	}

	/**
	 * UC 2 : returns top striking rate
	 * 
	 * @param filePath
	 * @return
	 * @throws CSVBuilderException
	 * @throws IOException
	 */
	public double getTopStrikingRate() {
		double maxStrikingRate = runsCSVList.stream().map(entry -> entry.strikeRate).max(Double::compare).get();
		return maxStrikingRate;
	}

	/**
	 * UC 3 : returns player scoring maximum number of fours
	 * 
	 * @param filePath
	 * @return
	 * @throws CSVBuilderException
	 * @throws IOException
	 */
	public CSVRuns getPlayerMaxFours() {
		CSVRuns maxFoursPlayer = runsCSVList.stream().max((x, y) -> Integer.compare(x.fours, y.fours)).get();
		return maxFoursPlayer;
	}

	/**
	 * UC 3 : returns player scoring maximum number of sixes
	 * 
	 * @param filePath
	 * @return
	 * @throws CSVBuilderException
	 * @throws IOException
	 */
	public CSVRuns getPlayerMaxSixes() {
		CSVRuns maxFoursPlayer = runsCSVList.stream().max((x, y) -> Integer.compare(x.sixes, y.sixes)).get();
		return maxFoursPlayer;
	}

	/**
	 * UC 4 : returns player having max strike rate with 4s and 6s
	 * 
	 * @param filePath
	 * @return
	 * @throws CSVBuilderException
	 * @throws IOException
	 */
	public CSVRuns getPlayerMaxStrikeRateWithFoursSixes() {
		CSVRuns player = runsCSVList.stream()
				.max((x, y) -> Double.compare(calculateStrikeRateFoursSixes(x), calculateStrikeRateFoursSixes(y)))
				.get();
		return player;
	}

	private static double calculateStrikeRateFoursSixes(CSVRuns player) {
		return (player.fours * 4 + player.sixes * 6) * 100 / player.ballsFaced;
	}
	
	/**
	 * UC 5 : returns player having max average with max strike rate
	 * @return
	 */
	public String getSortedOnMaxRunsAndStrikeRate() {
		Comparator<CSVRuns> iplCSVComparator = Comparator.comparing(entry -> entry.average);
		this.sort(runsCSVList, iplCSVComparator.thenComparing(entry -> entry.strikeRate));
		String jsonSortedPLayers = new Gson().toJson(runsCSVList);
		return jsonSortedPLayers;
	}
	
	private <E> void sort(List<E> csvList, Comparator<E> iplCSVComparator) {
		for (int i = 0; i < csvList.size(); i++) {
			for (int j = 0; j < csvList.size() - i - 1; j++) {
				E player1 = csvList.get(j);
				E player2 = csvList.get(j + 1);
				if (iplCSVComparator.compare(player1, player2) < 0) {
					csvList.set(j, player2);
					csvList.set(j + 1, player1);
				}
			}
		}
	}
	
	public static void main(String[] args) {
		System.out.println("Welcome to IPL League analysis Problem");
	}
}
