package com.iplleagueanalysisproblem;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.CSVBuilder.CSVBuilderException;
import com.google.gson.Gson;

class IPLAnalysisTest {
	private static IPLAnalysis iplAnalysis;
	private static String RUNS_FILE_PATH = "C:\\Users\\abc\\eclipse-workspace\\com.iplleagueanalysisproblem\\WP DP Data_01 IPL2019FactsheetMostRuns.csv";
	private static String WICKET_FILE_PATH = "C:\\Users\\abc\\eclipse-workspace\\com.iplleagueanalysisproblem\\WP DP Data_02 IPL2019FactsheetMostWkts.csv";

	@BeforeAll
	static void setUp() throws CSVBuilderException, IOException {
		iplAnalysis = new IPLAnalysis();
		iplAnalysis.loadRunsCSV(RUNS_FILE_PATH);
		iplAnalysis.loadWicketsCSV(WICKET_FILE_PATH);
	}

	/**
	 * checking number of records of runs CSV file
	 */
	@Test
	void givenRunsFilePath_shouldReturn_NumberOfRecords() {
		int numOfRecords = 0;
		try {
			numOfRecords = iplAnalysis.loadRunsCSV(RUNS_FILE_PATH);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		assertEquals(101, numOfRecords);
	}

	/**
	 * checking number of records of wickets CSV file
	 */
	@Test
	void givenWicketsFilePath_shouldReturn_NumberOfRecords() {
		int numOfRecords = 0;
		try {
			numOfRecords = iplAnalysis.loadWicketsCSV(WICKET_FILE_PATH);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		assertEquals(99, numOfRecords);
	}

	/**
	 * UC 1 : checking top batting average
	 */
	@Test
	void givenRunsFilePath_shouldReturn_topBattingAvg() {
		List<CSVRuns> battingAvgSortedList = iplAnalysis.getTopBattingAvg();
		assertEquals(83.2, battingAvgSortedList.get(0).average);
	}

	/**
	 * UC 2 : checking top striking rate
	 */
	@Test
	void givenRunsFilePath_shouldReturn_topStrikiingRate() {
		double maxBattingAvg = iplAnalysis.getTopStrikingRate();
		assertEquals(333.33, maxBattingAvg);
	}

	/**
	 * UC 3 : checking player with maximum fours
	 */
	@Test
	void givenRunsFilePath_shouldReturn_cricketerWithMaxFours() {
		CSVRuns actualMaxFoursPlayer = iplAnalysis.getPlayerMaxFours();
		assertEquals("Shikhar Dhawan", actualMaxFoursPlayer.playerName);
	}

	/**
	 * UC 3 : checking player with maximum sixes
	 */
	@Test
	void givenRunsFilePath_shouldReturn_cricketerWithMaxSixes() {
		CSVRuns actualMaxSixesPlayer = iplAnalysis.getPlayerMaxSixes();
		assertEquals("Andre Russell", actualMaxSixesPlayer.playerName);
	}

	/**
	 * UC 4 : checking player having max strike rate with 4s and 6s
	 */
	@Test
	void givenRunsFilePath_shouldReturn_cricketerWithMaxStrikeRateWithFoursAndSixes() {
		CSVRuns actualPlayer = iplAnalysis.getPlayerMaxStrikeRateWithFoursSixes();
		assertEquals("Ishant Sharma", actualPlayer.playerName);
	}

	/**
	 * UC 5 : checking player having max average with max strike rate
	 */
	@Test
	void givenRunsFilePath_shouldReturn_playerHavingGoodAvgWithBestStrikeRate() {
		String jsonSortedPLayers = iplAnalysis.getSortedJsonMaxAvgAndStrikeRate();
		CSVRuns[] actualSortedPlayers = new Gson().fromJson(jsonSortedPLayers, CSVRuns[].class);
		assertEquals("MS Dhoni", actualSortedPlayers[0].playerName);
	}

	/**
	 * UC 6 : checking player hitting max runs with max average
	 */
	@Test
	void givenRunsFilePath_shouldReturn_playerHittingMaxRunsWithBestAveragee() {
		String jsonSortedPLayers = iplAnalysis.getSortedJsonMaxRunsAndAverage();
		CSVRuns[] actualSortedPlayers = new Gson().fromJson(jsonSortedPLayers, CSVRuns[].class);
		assertEquals("David Warner", actualSortedPlayers[0].playerName);
	}

	/**
	 * UC 7 : checking top bowling average
	 */
	@Test
	void givenWicketsFilePath_shouldReturn_topBowlingAvg() {
		List<CSVWickets> bowlingAvgSortedList = iplAnalysis.getTopBowlingAvg();
		assertEquals(11.0, bowlingAvgSortedList.get(0).average);
		assertEquals(14.0, bowlingAvgSortedList.get(1).average);
	}

	/**
	 * UC 8 : checking top strike rate
	 */
	@Test
	void givenWicketsFilePath_shouldReturn_topStrikeRate() {
		double topStrikeRate = iplAnalysis.getTopStrikeRate();
		assertEquals(8.66, topStrikeRate);
	}

	/**
	 * UC 9 : checking top economy
	 */
	@Test
	void givenWicketsFilePath_shouldReturn_topEconomy() {
		double topEconomy = iplAnalysis.getTopEconomy();
		assertEquals(4.8, topEconomy);
	}

	/**
	 * UC 10 : checking player having best strike rate with 4 wickets and 5 wickets
	 */
	@Test
	void givenWicketsFilePath_shouldReturn_topStrikeRateWith4w5w() {
		String topStrikeRateWith4w5wPlayer = iplAnalysis.getPlayerWithBestStrikeRateWith4w5w();
		assertEquals("Alzarri Joseph", topStrikeRateWith4w5wPlayer);
	}

	/**
	 * UC 11 : checking player having best bowling average with best strike rate
	 */
	@Test
	void givenWicketsFilePath_shouldReturn_playerHavingGoodAvgWithBestStrikeRate() {
		String jsonSortedPLayers = iplAnalysis.getSortedJsonBestBowlingAvgAndStrikeRate();
		CSVWickets[] actualSortedPlayers = new Gson().fromJson(jsonSortedPLayers, CSVWickets[].class);
		assertEquals("Anukul Roy", actualSortedPlayers[0].playerName);
	}

	/**
	 * UC 12 : checking player taking max wickets with best bowling average
	 */
	@Test
	void givenWicketsFilePath_shouldReturn_playertakingMaxWicketsWithBestAvg() {
		String jsonSortedPLayers = iplAnalysis.getSortedJsonMaxWicketsWithBestBowlingAvg();
		CSVWickets[] actualSortedPlayers = new Gson().fromJson(jsonSortedPLayers, CSVWickets[].class);
		assertEquals("Imran Tahir", actualSortedPlayers[0].playerName);
		assertEquals("Kagiso Rabada", actualSortedPlayers[1].playerName);
	}

	/**
	 * UC 13 : checking player having best batting and bowling average
	 */
	@Test
	void givenWicketsFilePath_shouldReturn_playerhavingBestBattingAndBowlingAvg() {
		List<String> sortedPlayers = iplAnalysis.getPlayerWithBestBattingAndBowlingAvg();
		assertEquals("Andre Russell", sortedPlayers.get(0));
	}

	/**
	 * UC 14 : checking best all rounder player having most runs and wickets
	 */
	@Test
	void givenWicketsFilePath_shouldReturn_bestAllRounderPlayerhavingMostRunsAndWickets() {
		List<String> sortedPlayers = iplAnalysis.getBestAllRounder();
		assertEquals("Andre Russell", sortedPlayers.get(0));
	}
}
