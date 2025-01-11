package logger;

import logger.appender.Appender;

import java.util.List;

public class LoggerConfig {
    private LogLevel level;
    private List<Appender> appenderList;

    public LogLevel getLevel() {
        return level;
    }

    public List<Appender> getAppenderList() {
        return appenderList;
    }

    public void setLevel(LogLevel level) {
        this.level = level;
    }

    public void setAppenderList(List<Appender> appenderList) {
        this.appenderList = appenderList;
    }
}
