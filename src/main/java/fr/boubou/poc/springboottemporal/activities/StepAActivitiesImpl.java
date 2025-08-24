package fr.boubou.poc.springboottemporal.activities;

import fr.boubou.poc.springboottemporal.workflow.StepStatus;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import java.util.Map; import java.util.concurrent.ConcurrentHashMap;

public class StepAActivitiesImpl implements StepAActivities {

  private static final Logger log = LoggerFactory.getLogger(StepAActivitiesImpl.class);
  private final Map<String, Integer> ticks = new ConcurrentHashMap<>();

  @Override
  public String startStepA(String entryData) {
    log.info("start step A entryData={}",  entryData);
    ticks.put(entryData, 0);
    return entryData;
  }
}
