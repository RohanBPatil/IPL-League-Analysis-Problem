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
}
