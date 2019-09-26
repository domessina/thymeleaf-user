package be.technocite.service;

import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MonitoringService {

    @Autowired
    private MeterRegistry meterRegistry;

    private Logger logger = LoggerFactory.getLogger(MonitoringService.class);

    private static final String LOGIN_SUCCESS_RATIO = "login_success_ratio";

    void trackLogin(String email, boolean success) {
        if (success) {
            // dans grafana quand on veut faire des opérations ENTRE métriques il faut avoir le meme metric name
            meterRegistry.counter(LOGIN_SUCCESS_RATIO, "state", "success").increment(1);
        } else {
            meterRegistry.counter(LOGIN_SUCCESS_RATIO, "state", "failed").increment(1);
        }
    }
}
