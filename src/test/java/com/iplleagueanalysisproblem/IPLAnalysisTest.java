package com.iplleagueanalysisproblem;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

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
	 * 
	 * @throws CSVBuilderException
	 * @throws IOException
	 */
	@Test
	void givenRunsFilePath_shouldReturn_topBattingAvg() {
		double maxBattingAvg = iplAnalysis.getTopBattingAvg();
		assertEquals(83.2, maxBattingAvg);
	}

	/**
	 * UC 2 : checking top striking rate
	 * 
	 * @throws CSVBuilderException
	 * @throws IOException
	 */
	@Test
	void givenRunsFilePath_shouldReturn_topStrikiingRate() {
		double maxBattingAvg = iplAnalysis.getTopStrikingRate();
		assertEquals(333.33, maxBattingAvg);
	}

	/**
	 * UC 3 : checking player with maximum fours
	 * 
	 * @throws CSVBuilderException
	 * @throws IOException
	 */
	@Test
	void givenRunsFilePath_shouldReturn_cricketerWithMaxFours() {
		CSVRuns actualMaxFoursPlayer = iplAnalysis.getPlayerMaxFours();
		assertEquals("Shikhar Dhawan", actualMaxFoursPlayer.playerName);
	}

	/**
	 * UC 3 : checking player with maximum sixes
	 * 
	 * @throws CSVBuilderException
	 * @throws IOException
	 */
	@Test
	void givenRunsFilePath_shouldReturn_cricketerWithMaxSixes() {
		CSVRuns actualMaxSixesPlayer = iplAnalysis.getPlayerMaxSixes();
		assertEquals("Andre Russell", actualMaxSixesPlayer.playerName);
	}

	/**
	 * UC 4 : checking player having max strike rate with 4s and 6s
	 * 
	 * @throws CSVBuilderException
	 * @throws IOException
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
	
}
