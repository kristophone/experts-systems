package com.tsj.expertsystems.agents.src.examples.behaviours;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;

public class GenericAgent extends Agent {

    protected void setup() {
        System.out.println("Agent " + getLocalName() + " started.");
        addBehaviour(new MyGenericBehaviour());
    }

    private class MyGenericBehaviour extends Behaviour {

        int cont = 0;

        public void action() {
            System.out.println("Agent's action method is executed");
            cont += 1;
        }

        public boolean done() {
            return cont == 20;
        }

        public int onEnd() {
            myAgent.doDelete();
            return super.onEnd();
        }
    }    // END of inner class ...Behaviour
}
