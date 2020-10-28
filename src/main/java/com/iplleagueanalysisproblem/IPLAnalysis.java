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
	 * @return
	 */
	public double getTopBattingAvg() {
		double maxBattingAvg = runsCSVList.stream().map(entry -> entry.average).max(Double::compare).get();
		return maxBattingAvg;
	}

	/**
	 * UC 2 : returns top striking rate
	 * 
	 * @return
	 */
	public double getTopStrikingRate() {
		double maxStrikingRate = runsCSVList.stream().map(entry -> entry.strikeRate).max(Double::compare).get();
		return maxStrikingRate;
	}

	/**
	 * UC 3 : returns player scoring maximum number of fours
	 * 
	 * @return
	 */
	public CSVRuns getPlayerMaxFours() {
		CSVRuns maxFoursPlayer = runsCSVList.stream().max((x, y) -> Integer.compare(x.fours, y.fours)).get();
		return maxFoursPlayer;
	}

	/**
	 * UC 3 : returns player scoring maximum number of sixes
	 * 
	 * @return
	 */
	public CSVRuns getPlayerMaxSixes() {
		CSVRuns maxFoursPlayer = runsCSVList.stream().max((x, y) -> Integer.compare(x.sixes, y.sixes)).get();
		return maxFoursPlayer;
	}

	/**
	 * UC 4 : returns player having max strike rate with 4s and 6s
	 * 
	 * @return
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
	 * 
	 * @return
	 */
	public String getSortedJsonMaxAvgAndStrikeRate() {
		Comparator<CSVRuns> battingComparator = Comparator.comparing(entry -> entry.average);
		this.sortBattingList(runsCSVList, battingComparator.thenComparing(entry -> entry.strikeRate));
		String jsonSortedPlayers = new Gson().toJson(runsCSVList);
		return jsonSortedPlayers;
	}

	/**
	 * UC 6 : returns player having max runs with max average
	 * 
	 * @return
	 */
	public String getSortedJsonMaxRunsAndAverage() {
		Comparator<CSVRuns> battingComparator = Comparator.comparing(entry -> entry.runs);
		this.sortBattingList(runsCSVList, battingComparator.thenComparing(entry -> entry.average));
		String jsonSortedPlayers = new Gson().toJson(runsCSVList);
		return jsonSortedPlayers;
	}

	private void sortBattingList(List<CSVRuns> csvList, Comparator<CSVRuns> battingComparator) {
		for (int i = 0; i < csvList.size(); i++) {
			for (int j = 0; j < csvList.size() - i - 1; j++) {
				CSVRuns player1 = csvList.get(j);
				CSVRuns player2 = csvList.get(j + 1);
				if (battingComparator.compare(player1, player2) < 0) {
					csvList.set(j, player2);
					csvList.set(j + 1, player1);
				}
			}
		}
	}

	/**
	 * UC 7 : returns top bowling average
	 * 
	 * @return
	 */
	public double getTopBowlingAvg() {
		Comparator<CSVWickets> BowlingComparator = Comparator.comparing(entry -> entry.average);
		this.sortBowlingList(wicketsCSVList, BowlingComparator);
		return wicketsCSVList.get(0).average;
	}

	/**
	 * UC 8 : returns top strike rate
	 * 
	 * @return
	 */
	public double getTopStrikeRate() {
		Comparator<CSVWickets> BowlingComparator = Comparator.comparing(entry -> entry.strikeRate);
		this.sortBowlingList(wicketsCSVList, BowlingComparator);
		return wicketsCSVList.get(0).strikeRate;
	}

	/**
	 * UC 9 : returns top economy
	 * 
	 * @return
	 */
	public double getTopEconomy() {
		CSVWickets topEconomyPlayer = wicketsCSVList.stream().min((x, y) -> Double.compare(x.economy, y.economy)).get();
		return topEconomyPlayer.economy;
	}

	/**
	 * UC 10 : returns player having best strike rate with 4 wickets and 5 wickets
	 * 
	 * @return
	 */
	public String getPlayerWithBestStrikeRateWith4w5w() {
		CSVWickets topEconomyPlayer = wicketsCSVList.stream()
				.min((x, y) -> Double.compare(calculateStrikeRateWith4w5w(x), calculateStrikeRateWith4w5w(y))).get();
		return topEconomyPlayer.playerName;
	}

	private double calculateStrikeRateWith4w5w(CSVWickets player) {
		double numOfWicketsWith4w5w = player.fourWickets * 4 + player.fiveWickets * 5;
		if (numOfWicketsWith4w5w == 0)
			return Double.MAX_VALUE;
		int numOfBalls = (int) player.overs;
		numOfBalls = numOfBalls * 6 + (int) ((player.overs - numOfBalls) * 10);
		return numOfBalls / numOfWicketsWith4w5w;
	}

	/**
	 * UC 11 : returns player having best bowling average with best strike rate
	 * 
	 * @return
	 */
	public String getSortedJsonBestBowlingAvgAndStrikeRate() {
		Comparator<CSVWickets> bowlingComparator = Comparator.comparing(entry -> entry.average);
		this.sortBowlingList(wicketsCSVList, bowlingComparator.thenComparing(entry -> entry.strikeRate));
		String jsonSortedPlayers = new Gson().toJson(wicketsCSVList);
		return jsonSortedPlayers;
	}

	/**
	 * UC 12 : returns player taking max wickets with best bowling average
	 * 
	 * @return
	 */
	public String getSortedJsonMaxWicketsWithBestBowlingAvg() {
		Comparator<CSVWickets> bowlingComparator = Comparator.comparing(entry -> -entry.wickets);
		this.sortBowlingList(wicketsCSVList, bowlingComparator.thenComparing(entry -> entry.average));
		String jsonSortedPlayers = new Gson().toJson(wicketsCSVList);
		return jsonSortedPlayers;
	}

	private void sortBowlingList(List<CSVWickets> csvList, Comparator<CSVWickets> BowlingComparator) {
		for (int i = 0; i < csvList.size(); i++) {
			for (int j = 0; j < csvList.size() - i - 1; j++) {
				CSVWickets player1 = csvList.get(j);
				CSVWickets player2 = csvList.get(j + 1);
				if (BowlingComparator.compare(player1, player2) > 0 && (player1.wickets != 0 && player2.wickets != 0)) {
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
