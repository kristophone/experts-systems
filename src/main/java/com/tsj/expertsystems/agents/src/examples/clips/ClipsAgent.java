package com.tsj.expertsystems.agents.src.examples.clips;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import net.sf.clipsrules.jni.CLIPSException;
import net.sf.clipsrules.jni.Environment;

public class ClipsAgent extends Agent {
    private Environment clips;

    protected void setup() {
        clips = new Environment();
        addBehaviour(new TellBehaviour());
        addBehaviour(new AskBehaviour());
    }

    private class TellBehaviour extends Behaviour {
        boolean tellDone = false;

        public void action() {

            try {

                System.out.println("Invoking Tell");
                clips.eval("(clear)");
                clips.build("(defrule r1 (sintoma ?s) => (printout t ?s crlf))");
                clips.eval("(reset)");

                clips.eval("(assert (sintoma a))");

                tellDone = true;

            } catch (CLIPSException e) {
                throw new RuntimeException(e);
            }

        }

        public boolean done() {
            return tellDone;
        }
    }    // END of inner class ...Behaviour

    private class AskBehaviour extends Behaviour {
        boolean askDone = false;

        public void action() {

            try {
                clips.eval("(facts)");
                //clips.run();

                clips.clear();
                System.out.println("Invoking Ask");

                askDone = true;
            } catch (CLIPSException e) {
                throw new RuntimeException(e);
            }
        }

        public boolean done() {
            return askDone;
        }

        public int onEnd() {
            myAgent.doDelete();
            return super.onEnd();
        }
    }
}
