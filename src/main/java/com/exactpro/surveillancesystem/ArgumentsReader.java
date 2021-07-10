package com.exactpro.surveillancesystem;

import java.io.File;
import java.nio.file.Paths;

public class ArgumentsReader {
	private final String[] args;

	public ArgumentsReader(String[] args) {
		this.args = args;
		validate();
	}

	private void validate() {
		
		if (args == null || args.length == 0)
			System.err.println("The file path is not specified");
	}

	public File getTransactionsData() {
		File file = Paths.get(args[0]).toFile();
		if (!file.exists())
			System.err.println("file not found!");

		return file;
	}

	public File getPriceData() {
		File file = Paths.get(args[1]).toFile();
		if (!file.exists())
			System.err.println("file not found!");

		return file;
	}
}
