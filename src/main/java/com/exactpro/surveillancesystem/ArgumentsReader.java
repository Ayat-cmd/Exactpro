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

	public File getInputData(int i) {
		File file = Paths.get(args[i]).toFile();
		if (!file.exists())
			throw new IllegalStateException();

		return file;
	}
}
