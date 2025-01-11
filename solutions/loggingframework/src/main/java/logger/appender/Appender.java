package logger.appender;

import logger.LogMessage;

import java.io.IOException;

public interface Appender {
    public void append(LogMessage logMessage) throws IOException;
    public void close() throws IOException;
}
