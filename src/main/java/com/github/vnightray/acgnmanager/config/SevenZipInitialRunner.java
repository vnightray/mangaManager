package com.github.vnightray.acgnmanager.config;

import net.sf.sevenzipjbinding.SevenZip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * initial sevenZip, otherwise it will throw an exception (SevenZipJBinding wasn’t initialized successfully last time)
 */
@Component
public class SevenZipInitialRunner implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(SevenZipInitialRunner.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        SevenZip.initSevenZipFromPlatformJAR();
        logger.info("sevenZipInitialization complete");
    }
}