package com.iplleagueanalysisproblem;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class IPLAnalysisTest {
	private static String RUNS_FILE_PATH = "C:\\Users\\abc\\eclipse-workspace\\com.iplleagueanalysisproblem\\WP DP Data_01 IPL2019FactsheetMostRuns.csv";
	private static String WICKET_FILE_PATH = "C:\\Users\\abc\\eclipse-workspace\\com.iplleagueanalysisproblem\\WP DP Data_02 IPL2019FactsheetMostWkts.csv";

	@Test
	void givenRunsFilePath_shouldReturn_NumberOfRecords() {
		IPLAnalysis iplAnalysis = new IPLAnalysis();
		int numOfRecords = 0;
		try {
			numOfRecords = iplAnalysis.loadRunsCSV(RUNS_FILE_PATH);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		assertEquals(101, numOfRecords);
	}

	@Test
	void givenWicketsFilePath_shouldReturn_NumberOfRecords() {
		IPLAnalysis iplAnalysis = new IPLAnalysis();
		int numOfRecords = 0;
		try {
			numOfRecords = iplAnalysis.loadWicketsCSV(WICKET_FILE_PATH);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		assertEquals(99, numOfRecords);
	}
}
