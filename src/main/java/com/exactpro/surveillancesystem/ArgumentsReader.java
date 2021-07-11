package com.exactpro.surveillancesystem;

import java.io.File;
import java.nio.file.Paths;

import org.slf4j.Logger;
import static org.slf4j.LoggerFactory.getLogger;

public class ArgumentsReader {
	private final String[] args;
	private File pricesFile;
	private File transactionsFile;
	private final static Logger loggerArgumentsReader = getLogger(ArgumentsReader.class);

	public ArgumentsReader(String[] args) {
		this.args = args;
		validate();
		setTransactionsPricesData();
	}

	public File getPricesFile() { return pricesFile; }

	public File getTransactionsFile() { return transactionsFile; }

	private void validate() {
		if (args == null || args.length == 0)
			loggerArgumentsReader.error("The file path is not specified");
	}

	public void setTransactionsPricesData() {
		transactionsFile = Paths.get(args[0]).toFile();
		pricesFile = Paths.get(args[1]).toFile();
		if (!transactionsFile.exists())
			loggerArgumentsReader.error("file " + transactionsFile + " not found!");
		if (!pricesFile.exists())
			loggerArgumentsReader.error("file " + pricesFile + " not found!");
	}
}
