package year2023;

import lombok.extern.log4j.Log4j2;

import java.util.logging.Logger;

@Log4j2
public class Log4JTest {
    static Logger logger = Logger.getLogger(Log4JTest.class.getCanonicalName());

    public static void main(String[] args) {
        logger.info("hello form java.util.logger");
        log.info("hello from log4j2 of lombok");
    }
}
