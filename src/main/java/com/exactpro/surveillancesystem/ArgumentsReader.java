package com.exactpro.surveillancesystem;

import java.io.File;
import java.nio.file.Paths;

// TODO: implement ArgumentsReader calss
public class ArgumentsReader {
	private final String[] args;


	public ArgumentsReader(String[] args) {
		this.args = args;
		validate();
	}


	private void validate() {
		// TODO: arguments validation logic
		if (args == null || args.length == 0)
			throw new IllegalArgumentException();
	}

	public File getTransactionsData() {
		File file = Paths.get(args[0]).toFile();
		if (!file.exists())
			throw new IllegalStateException();

		return file;
	}

	public File getPriceData() {
		File file = Paths.get(args[1]).toFile();
		if (!file.exists())
			throw new IllegalStateException();

		return file;
	}
}
