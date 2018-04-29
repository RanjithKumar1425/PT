package org.crystalface.pt;

import java.util.Date;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;

public class ScheduledActor extends AbstractActor {
	
	  static public Props props() {
		    return Props.create(ScheduledActor.class, () -> new ScheduledActor());
		  }

	@Override
	public Receive createReceive() {
		return receiveBuilder().match(String.class, data -> {
			System.out.println(new Date());
			System.out.println(data + self().path().name());
		}).build();
	}

}
