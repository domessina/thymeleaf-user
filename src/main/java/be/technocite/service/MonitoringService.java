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
            // dans grafana quand on veut faire des opérations ENTRE métriques il faut avoir le meme metric name et les memes label
            // pcq grafana n'autorise les opérations qu'entre la meme métrique. Du coup il faut donner un nom différent ET (voir plus bas)
            // faire ignoringState(true) pour lui dire t'inquiète pas si les tags sont différents. 
            meterRegistry.counter(LOGIN_SUCCESS_RATIO, "state", "success").increment(1);
        } else {
            meterRegistry.counter(LOGIN_SUCCESS_RATIO, "state", "failed").increment(1);
        }
    }
    
    /*6 – Dans notre dashboard grafana, on choisit de démarrer une nouvelle query et là il nous reste à écrire celle(s)-ci.
Une seule métrique est disponible « metric_pet_total » mais en vrai il en existe 3 et celles-ci peuvent être différenciées par leurs labels

metric_pet_total{state="total"}
metric_pet_total{state="found"}
metric_pet_total{state="lost"}

Et le plus important, le ratio d’animaux perdus sur le total (en %)

(metric_pet_total{state="lost"} / ignoring(state) metric_pet_total{state="total"})*100

Idem pour les trouvailles

(metric_pet_total{state="found"} / ignoring(state) metric_pet_total{state="total"})*100
*/
}
