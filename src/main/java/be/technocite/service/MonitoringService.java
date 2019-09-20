package be.technocite.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.search.MeterNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MonitoringService {

    @Autowired
    private MeterRegistry meterRegistry;

    private Logger logger = LoggerFactory.getLogger(MonitoringService.class);

    private static final String LOGIN_SUCCESS = "login_success";
    private static final String LOGIN_FAILED = "login_failed";
    private static final String LOGIN_SUCCESS_RATIO = "login_success_ratio";
    private static Double successRatio = 0.0;

    void trackLogin(String email, boolean success) {
        if (success) {
            meterRegistry.counter(LOGIN_SUCCESS, "toto", "toto").increment(1);
        } else {
            meterRegistry.counter(LOGIN_FAILED, "toto", "toto").increment(1);
        }
        try {
            Counter loginSuccess = meterRegistry.get(LOGIN_SUCCESS).counter();
            Counter loginFailed = meterRegistry.get(LOGIN_FAILED).counter();
            //FIXME Idk why, but the decimals are wrong eg: (2+3) / 3 = 2 !!
            successRatio = loginSuccess.count() + loginFailed.count() / loginSuccess.count();
//            meterRegistry.(LOGIN_SUCCESS_RATIO, successRatio);
        } catch (MeterNotFoundException e) {
            logger.debug("meter not found ");
        }
    }
}
