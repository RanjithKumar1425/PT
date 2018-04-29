package org.crystalface.pt;

import akka.actor.ActorRef;
import akka.actor.Props;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        SchedulerExample test = new SchedulerExample();
        ActorRef once = test.getActor("Schedule_Once", ScheduledActor.props());
        
        ActorRef repeat = test.getActor("Schedule_Repeat1", ScheduledActor.props());
        test.getActor("Schedule_Repeat2", ScheduledActor.props());
        
        test.schedule(10000l, 10000l, "Schedule_Repeat1", "Not Triggered.... ");
        test.schedule(10000l, 10000l, "Schedule_Repeat2", "In Progress... ");
        test.runOnce(5000L, "Schedule_Once", "Once...");
    }
}