package com.iplleagueanalysisproblem;

import com.opencsv.bean.CsvBindByName;

public class CSVRuns {

	@CsvBindByName(column = "POS", required = true)
	public int position;

	@CsvBindByName(column = "PLAYER", required = true)
	public String player;

	@CsvBindByName(column = "Mat", required = true)
	public int matches;

	@CsvBindByName(column = "Inns", required = true)
	public int innings;

	@CsvBindByName(column = "NO", required = true)
	public int notOut;

	@CsvBindByName(column = "Runs", required = true)
	public int runs;

	@CsvBindByName(column = "HS", required = true)
	public String highScore;

	@CsvBindByName(column = "Avg", required = true)
	public double average;

	@CsvBindByName(column = "BF", required = true)
	public int ballsFaced;

	@CsvBindByName(column = "SR", required = true)
	public double strikeRate;

	@CsvBindByName(column = "100", required = true)
	public int hundreds;

	@CsvBindByName(column = "50", required = true)
	public int fiftys;

	@CsvBindByName(column = "4s", required = true)
	public int fours;

	@CsvBindByName(column = "6s", required = true)
	public int sixes;

	@Override
	public String toString() {
		return "IPLRunsCSV{" + "Position='" + position + '\'' + ", Player='" + player + '\'' + ", Matches='" + matches
				+ '\'' + ", Innings='" + innings + '\'' + ", NO='" + notOut + '\'' + ", Runs='" + runs + '\''
				+ ", High Score='" + highScore + '\'' + ", Average='" + average + ", BF='" + ballsFaced + '\''
				+ ", Strike Rate='" + strikeRate + '\'' + ", 100='" + hundreds + '\'' + ", 50='" + fiftys + ", 4s='"
				+ fours + '\'' + ", 6s='" + sixes + '}';
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj == null || !(obj instanceof CSVRuns))
			return false;
		CSVRuns object = (CSVRuns) obj;
		return object.player.compareTo(this.player) == 0;
	}
	
	public CSVRuns() {
		
	}

	public CSVRuns(int position, String player, int matches, int innings, int notOut, int runs, String highScore,
			double average, int ballsFaced, double strikeRate, int hundreds, int fiftys, int fours, int sixes) {
		this.position = position;
		this.player = player;
		this.matches = matches;
		this.innings = innings;
		this.notOut = notOut;
		this.runs = runs;
		this.highScore = highScore;
		this.average = average;
		this.ballsFaced = ballsFaced;
		this.strikeRate = strikeRate;
		this.hundreds = hundreds;
		this.fiftys = fiftys;
		this.fours = fours;
		this.sixes = sixes;
	}

}
