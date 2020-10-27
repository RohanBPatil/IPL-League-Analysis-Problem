package com.iplleagueanalysisproblem;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.CSVBuilder.CSVBuilderException;

class IPLAnalysisTest {
	private static IPLAnalysis iplAnalysis;
	private static String RUNS_FILE_PATH = "C:\\Users\\abc\\eclipse-workspace\\com.iplleagueanalysisproblem\\WP DP Data_01 IPL2019FactsheetMostRuns.csv";
	private static String WICKET_FILE_PATH = "C:\\Users\\abc\\eclipse-workspace\\com.iplleagueanalysisproblem\\WP DP Data_02 IPL2019FactsheetMostWkts.csv";

	@BeforeAll
	static void setUp() {
		iplAnalysis = new IPLAnalysis();
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
	void givenRunsFilePath_shouldReturn_topBattingAvg() throws CSVBuilderException, IOException {
		double maxBattingAvg = iplAnalysis.getTopBattingAvg(RUNS_FILE_PATH);
		assertEquals(83.2, maxBattingAvg);
	}

	/**
	 * UC 2 : checking top striking rate
	 * 
	 * @throws CSVBuilderException
	 * @throws IOException
	 */
	@Test
	void givenRunsFilePath_shouldReturn_topStrikiingRate() throws CSVBuilderException, IOException {
		double maxBattingAvg = iplAnalysis.getTopStrikingRate(RUNS_FILE_PATH);
		assertEquals(333.33, maxBattingAvg);
	}

	/**
	 * UC 3 : checking player with maximum fours
	 * 
	 * @throws CSVBuilderException
	 * @throws IOException
	 */
	@Test
	void givenRunsFilePath_shouldReturn_cricketerWithMaxFours() throws CSVBuilderException, IOException {
		CSVRuns expectedMaxFoursPlayer = new CSVRuns(4, "Shikhar Dhawan", 16, 16, 1, 521, "97*", 34.73, 384, 135.67, 0,
				5, 64, 11);
		CSVRuns actualMaxFoursPlayer = iplAnalysis.getPlayerMaxFours(RUNS_FILE_PATH);
		assertEquals(expectedMaxFoursPlayer, actualMaxFoursPlayer);
	}

	/**
	 * UC 3 : checking player with maximum sixes
	 * 
	 * @throws CSVBuilderException
	 * @throws IOException
	 */
	@Test
	void givenRunsFilePath_shouldReturn_cricketerWithMaxSixes() throws CSVBuilderException, IOException {
		CSVRuns expectedMaxSixesPlayer = new CSVRuns(5, "Andre Russell", 14, 13, 4, 510, "80*", 56.66, 249, 204.81, 0,
				4, 31, 52);
		CSVRuns actualMaxSixesPlayer = iplAnalysis.getPlayerMaxSixes(RUNS_FILE_PATH);
		assertEquals(expectedMaxSixesPlayer, actualMaxSixesPlayer);
	}

	/**
	 * UC 4 : checking player having max strike rate with 4s and 6s
	 * 
	 * @throws CSVBuilderException
	 * @throws IOException
	 */
	@Test
	void givenRunsFilePath_shouldReturn_cricketerWithMaxStrikeRateWithFoursAndSixes()
			throws CSVBuilderException, IOException {
		CSVRuns expectedPlayer = new CSVRuns(97, "Ishant Sharma", 13, 3, 3, 10, "10*", 0, 3, 333.33, 0, 0, 1, 1);
		CSVRuns actualPlayer = iplAnalysis.getPlayerMaxStrikeRateWithFoursSixes(RUNS_FILE_PATH);
		assertEquals(expectedPlayer, actualPlayer);
	}
}
