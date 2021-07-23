package com.exactpro.surveillancesystem.utils;

import java.io.File;
import java.io.IOException;

import static com.exactpro.surveillancesystem.utils.DateTimeUtils.nowDate;
import org.slf4j.Logger;
import static org.slf4j.LoggerFactory.getLogger;

public class IOUtils {
    private static Logger logger = getLogger(IOUtils.class);
    public static File createFile() throws IOException {
        File file = new File("alerts_"+nowDate()+".csv");

        if (file.createNewFile()){
            logger.info("File is created!");
        }
        else{
            logger.error("File already exists.");
        }
        return file;
    }
}
