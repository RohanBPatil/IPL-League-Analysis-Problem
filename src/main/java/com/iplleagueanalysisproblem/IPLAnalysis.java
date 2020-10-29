package com.iplleagueanalysisproblem;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
	private static <k> List<k> loadCSVData(String filePath, Class<k> csvClass) throws IPLLeagueAnalyserException {
		try {
			Reader reader = Files.newBufferedReader(Paths.get(filePath));
			ICSVBuilder<k> csvBuilder = CSVBuilderFactory.createCSVBuilder();
			return csvBuilder.getCSVFileList(reader, csvClass);
		}
		catch (CSVBuilderException exception) {
			throw new IPLLeagueAnalyserException(exception.getMessage(), IPLLeagueAnalyserException.ExceptionType.UNABLE_TO_PARSE);
		} catch (IOException exception) {
			throw new IPLLeagueAnalyserException(exception.getMessage(), IPLLeagueAnalyserException.ExceptionType.INCORRECT_FILE);
		}
	}

	/**
	 * Loads csv file data and returns size of list of POJO objects
	 * 
	 * @param filePath
	 * @return
	 * @throws IPLLeagueAnalyserException 
	 */
	public int loadRunsCSV(String filePath) throws IPLLeagueAnalyserException {
		runsCSVList = loadCSVData(filePath, CSVRuns.class);
		return runsCSVList.size();
	}

	/**
	 * Loads csv file data and returns size of list of POJO objects
	 * 
	 * @param filePath
	 * @return
	 * @throws IPLLeagueAnalyserException 
	 */
	public int loadWicketsCSV(String filePath) throws IPLLeagueAnalyserException {
		wicketsCSVList = loadCSVData(filePath, CSVWickets.class);
		return wicketsCSVList.size();
	}

	/**
	 * UC 1 : returns top batting average
	 * 
	 * @return
	 */
	public List<CSVRuns> getTopBattingAvg() {
		Comparator<CSVRuns> battingComparator = Comparator.comparing(entry -> entry.average);
		this.sort(runsCSVList, battingComparator);
		return runsCSVList;
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
		this.sort(runsCSVList, battingComparator.thenComparing(entry -> entry.strikeRate));
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
		this.sort(runsCSVList, battingComparator.thenComparing(entry -> entry.average));
		String jsonSortedPlayers = new Gson().toJson(runsCSVList);
		return jsonSortedPlayers;
	}

	private <k> void sort(List<k> csvList, Comparator<k> battingComparator) {
		for (int i = 0; i < csvList.size(); i++) {
			for (int j = 0; j < csvList.size() - i - 1; j++) {
				k player1 = csvList.get(j);
				k player2 = csvList.get(j + 1);
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
	public List<CSVWickets> getTopBowlingAvg() {
		Comparator<CSVWickets> BowlingComparator = Comparator.comparing(entry -> entry.average);
		List<CSVWickets> tempList = wicketsCSVList.stream().filter(entry -> entry.wickets != 0)
				.filter(entry -> entry.average != 0).collect(Collectors.toList());
		this.sort(tempList, BowlingComparator.reversed());
		return tempList;
	}

	/**
	 * UC 8 : returns top strike rate
	 * 
	 * @return
	 */
	public double getTopStrikeRate() {
		Comparator<CSVWickets> BowlingComparator = Comparator.comparing(entry -> entry.strikeRate);
		List<CSVWickets> tempList = wicketsCSVList.stream().filter(entry -> entry.wickets != 0)
				.collect(Collectors.toList());
		this.sort(tempList, BowlingComparator.reversed());
		return tempList.get(0).strikeRate;
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
		List<CSVWickets> tempList = wicketsCSVList.stream().filter(entry -> entry.wickets != 0)
				.filter(entry -> entry.average != 0).collect(Collectors.toList());
		Comparator<CSVWickets> bowlingComparator = Comparator.comparing(entry -> entry.average);
		this.sort(tempList, bowlingComparator.thenComparing(entry -> entry.strikeRate).reversed());
		String jsonSortedPlayers = new Gson().toJson(tempList);
		return jsonSortedPlayers;
	}

	/**
	 * UC 12 : returns player taking max wickets with best bowling average
	 * 
	 * @return
	 */
	public String getSortedJsonMaxWicketsWithBestBowlingAvg() {
		List<CSVWickets> tempList = wicketsCSVList.stream().filter(entry -> entry.wickets != 0)
				.filter(entry -> entry.average != 0).collect(Collectors.toList());
		Comparator<CSVWickets> bowlingComparator = Comparator.comparing(entry -> -entry.wickets);
		this.sort(tempList, bowlingComparator.thenComparing(entry -> entry.average).reversed());
		String jsonSortedPlayers = new Gson().toJson(tempList);
		return jsonSortedPlayers;
	}

	/**
	 * UC 13 : returns player having best batting and bowling average
	 * 
	 * @return
	 */
	public List<String> getPlayerWithBestBattingAndBowlingAvg() {
		List<String> battingAvgSortList = this.getTopBattingAvg().stream().map(entry -> entry.playerName).limit(40)
				.collect(Collectors.toList());
		List<String> bowlingAvgSortList = this.getTopBowlingAvg().stream().map(entry -> entry.playerName).limit(40)
				.collect(Collectors.toList());
		List<String> result = battingAvgSortList.stream().distinct().filter(bowlingAvgSortList::contains)
				.collect(Collectors.toList());
		return result;
	}

	/**
	 * UC 14 : returns best all rounder player having most runs and wickets
	 * 
	 * @return
	 */
	public List<String> getBestAllRounder() {
		Comparator<CSVRuns> battingComparator = Comparator.comparing(entry -> entry.runs);
		Comparator<CSVWickets> bowlingComparator = Comparator.comparing(entry -> entry.wickets);
		this.sort(runsCSVList, battingComparator);
		this.sort(wicketsCSVList, bowlingComparator);
		List<String> mostRunsPlayerList = runsCSVList.stream().limit(40).map(entry -> entry.playerName)
				.collect(Collectors.toList());
		List<String> mostWicketsPlayerList = wicketsCSVList.stream().limit(40).map(entry -> entry.playerName)
				.collect(Collectors.toList());
		return mostRunsPlayerList.stream().distinct().filter(mostWicketsPlayerList::contains)
				.collect(Collectors.toList());
	}

	/**
	 * UC 15 : returns players scoring max hundreds with best batting averages
	 * 
	 * @return
	 */
	public List<CSVRuns> getPlayersWithMaxHundredsBestBattingAverage() {
		Comparator<CSVRuns> battingComparator = Comparator.comparing(entry -> entry.hundreds);
		this.sort(runsCSVList, battingComparator.thenComparing(entry -> entry.average));
		return runsCSVList;
	}

	public static void main(String[] args) {
		System.out.println("Welcome to IPL League analysis Problem");
	}
}
