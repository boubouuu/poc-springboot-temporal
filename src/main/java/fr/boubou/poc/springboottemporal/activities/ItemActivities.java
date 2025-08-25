package fr.boubou.poc.springboottemporal.activities;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface ItemActivities {

    @ActivityMethod
    String process(String string);
}
