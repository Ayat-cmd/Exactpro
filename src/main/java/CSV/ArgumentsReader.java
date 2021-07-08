package CSV;

import java.io.File;
import java.nio.file.Paths;
import java.util.Scanner;

// TODO: implement ArgumentsReader calss
public class ArgumentsReader {
	private final String[] path = new String[2];


	public ArgumentsReader() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter path transactions_current_datetime");
		this.path[0] = scanner.nextLine();
		System.out.println("price_file_datestamp");
		this.path[1] = scanner.nextLine();
		validate();
	}


	private void validate() {
		// TODO: arguments validation logic
		if (path == null || path.length == 0)
			throw new IllegalArgumentException();
	}

	public File getInputDataTransaction() {
		File file = Paths.get(path[0]).toFile();
		if (!file.exists())
			throw new IllegalStateException();

		return file;
	}
	public File getInputDataPrice() {
		File file = Paths.get(path[1]).toFile();
		if (!file.exists())
			throw new IllegalStateException();

		return file;
	}
}
