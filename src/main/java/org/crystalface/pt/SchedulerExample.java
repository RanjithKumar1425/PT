package org.crystalface.pt;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import scala.concurrent.duration.FiniteDuration;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Cancellable;
import akka.actor.Props;
import akka.actor.Scheduler;

public class SchedulerExample {


    private ActorSystem actorSystem = ActorSystem.create("helloakka");;

    private Map<String, ActorRef> actorMap = new HashMap<>();


    public ActorRef getActor(String name) {
        if (actorMap.containsKey(name)) {
            return actorMap.get(name);
        }
        return null;
    }

   
    public ActorRef getActor(String name, Props props) {
        if (actorMap.containsKey(name)) {
            return actorMap.get(name);
        }
        System.out.println("Creating new actor|Name=" +  name+ " Props =" + props);
        ActorRef actor = actorSystem.actorOf(props, name);
        actorMap.put(name, actor);
        return actor;
    }

   
    public void removeActor(String name) {
        if (!actorMap.containsKey(name)) {
            return;
        }
        System.out.println("Removing actor|Name= " + name);
        ActorRef ref = actorMap.get(name);
        actorSystem.stop(ref);
        actorMap.remove(name);
    }

   
    public Cancellable schedule(long initialDelay, long interval,
                                String actorName, Object message) {

        ActorRef actor = getActor(actorName);
        if (actor == null) {
            return null;
        }
        FiniteDuration initial = new FiniteDuration(initialDelay, TimeUnit.MILLISECONDS);
        FiniteDuration gap = new FiniteDuration(interval, TimeUnit.MILLISECONDS);
        Scheduler scheduler = actorSystem.scheduler();
        return scheduler.schedule(initial, gap, actor, message, actorSystem.dispatcher(), ActorRef.noSender());
    }

    
    public Cancellable runOnce(long initialDelay, String actorName,
                               Object message) {

        ActorRef actor = getActor(actorName);
        if (actor == null) {
            return null;
        }
        FiniteDuration initial = new FiniteDuration(initialDelay, TimeUnit.MILLISECONDS);
        Scheduler scheduler = actorSystem.scheduler();
        return scheduler.scheduleOnce(initial, actor, message, actorSystem.dispatcher(), ActorRef.noSender());
    }
}
