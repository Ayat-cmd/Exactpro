package com.exactpro.surveillancesystem;

import java.io.File;
import java.nio.file.Paths;

import org.slf4j.Logger;
import static org.slf4j.LoggerFactory.getLogger;

public class ArgumentsReader {
	private final static Logger logger = getLogger(ArgumentsReader.class);
	private final String[] args;

	public ArgumentsReader(String[] args) {
		this.args = args;
		validate();
	}

	public File getPricesFile() {
		return getTransactionsPricesData(1);
	}

	public File getTransactionsFile() {
		return getTransactionsPricesData(0);
	}

	private void validate() {
		if (args == null || args.length == 0)
			logger.error("The file path is not specified");
	}

	private File getTransactionsPricesData(int index) {
		if(args.length < index)
			logger.error("not file!");
		File file = Paths.get(args[index]).toFile();
		if (!file.exists())
			logger.error("file {} not found!", file);
		return file;
	}
}
