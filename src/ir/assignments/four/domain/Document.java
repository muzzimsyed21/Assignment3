package ir.assignments.four.domain;

import java.io.File;

public class Document {
	private File file;
	private double score;
	
	public Document(File file, double score) {
		this.file = file;
		this.score = score;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}	
}
