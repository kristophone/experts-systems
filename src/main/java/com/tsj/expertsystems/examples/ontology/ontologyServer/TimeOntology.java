package com.tsj.expertsystems.examples.ontology.ontologyServer;

import jade.content.onto.BeanOntology;
import jade.content.onto.Ontology;

public class TimeOntology extends BeanOntology {

    public static final String NAME = "Time-Ontology";

    // The singleton instance of the Time-Ontology
    private static final TimeOntology theInstance = new TimeOntology();

    private TimeOntology() {
        super(NAME);

        try {
            // Add all Concepts, Predicates and AgentActions in the local package
            add(getClass().getPackage().getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Ontology getInstance() {
        return theInstance;
    }

}
