package com.iplleagueanalysisproblem;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.CSVBuilder.CSVBuilderException;
import com.CSVBuilder.CSVBuilderFactory;
import com.CSVBuilder.ICSVBuilder;

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
	public double getTopBattingAvg(String filePath) throws CSVBuilderException, IOException {
		loadRunsCSV(filePath);
		double max = runsCSVList.stream().map(entry -> entry.average).max(Double::compare).get();
		return max;
	}

	public static void main(String[] args) {
		System.out.println("Welcome to IPL League analysis Problem");
	}
}
