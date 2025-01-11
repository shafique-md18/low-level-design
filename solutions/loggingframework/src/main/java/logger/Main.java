package logger;

import logger.appender.*;

import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        Layout layout = new DefaultPatternLayout();
        Appender consoleAppender = new ConsoleAppender(layout);
        Appender fileAppender = new FileAppender(layout, "/Users/shafique/IdeaProjects/low-level-design/application.log");

        LoggerConfig config = new LoggerConfig();
        config.setLevel(LogLevel.INFO);
        config.setAppenderList(Arrays.asList(consoleAppender, fileAppender));

        Logger logger = new Logger(config);

        logger.info("Starting logs");
        logger.debug("Some debug message");
        logger.warn("Some warn message");
        logger.error("Some error message");
    }
}
