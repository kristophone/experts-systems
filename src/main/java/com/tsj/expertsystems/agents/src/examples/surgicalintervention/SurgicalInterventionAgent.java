package com.tsj.expertsystems.agents.src.examples.surgicalintervention;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import net.sf.clipsrules.jni.CLIPSException;
import net.sf.clipsrules.jni.CaptureRouter;
import net.sf.clipsrules.jni.Environment;
import net.sf.clipsrules.jni.Router;

public class SurgicalInterventionAgent extends Agent {
    private Environment clips;

    private CaptureRouter theRouter;

    protected void setup() {
        clips = new Environment();
        // https://stackoverflow.com/questions/58895760/how-to-re-direct-clips-jni-java-output-to-a-string
        theRouter = new CaptureRouter(clips, new String[]{Router.STDOUT,
                Router.STDERR,
                Router.STDWRN});
        addBehaviour(new TellBehaviour());
        addBehaviour(new AskBehaviour());
    }

    private class TellBehaviour extends Behaviour {
        boolean tellDone = false;

        public void action() {

            try {

                System.out.println("Invoking Tell");



                clips.load("src\\main\\resources\\clips\\surgical-intervention\\run.clp");

                clips.assertString("(patient-ready)");
                clips.assertString("(assistant-nurse-ready)");
                clips.assertString("(anesthesiologist-ready)");
                clips.assertString("(chief-surgeon-ready)");
                clips.assertString("(surgeon2-ready)");

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

                //clips.reset();

                clips.run();

                System.out.println("Output:");
                System.out.println(theRouter.getOutput());

                System.out.println("Invoking Ask");

                askDone = true;
            } catch (CLIPSException e) {
                throw new RuntimeException(e);
            }

            clips.deleteRouter(theRouter);
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