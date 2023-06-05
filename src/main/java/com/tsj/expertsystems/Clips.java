package com.tsj.expertsystems;


import net.sf.clipsrules.jni.CLIPSException;
import net.sf.clipsrules.jni.Environment;

public class Clips {

    public static void main(String[] args) throws CLIPSException {

        Environment clips = new Environment();

        /*
        clips.load("src\\main\\resources\\clips\\persons\\load-persons.clp");
        clips.load("src\\main\\resources\\clips\\persons\\load-persons-rules.clp");

        clips.reset();
        clips.getFactList().forEach(System.out::println);
        clips.run();
        clips.clear();


        clips.load("src\\main\\resources\\clips\\prodcust\\load-prod-cust.clp");
        clips.load("src\\main\\resources\\clips\\prodcust\\load-prodcust-rules.clp");

        clips.getFactList().forEach(System.out::println);
        clips.run();
        clips.clear();


        clips.load("src\\main\\resources\\clips\\market\\templates.clp");

        clips.load("src\\main\\resources\\clips\\market\\facts.clp");

        clips.load("src\\main\\resources\\clips\\market\\rules.clp");

        clips.reset();
        clips.run();
        clips.clear();
        */

        clips.load("src\\main\\resources\\clips\\monkey-bananas\\run");
        //clips.reset();
        clips.assertString("(estado inicial)");
        clips.assertString("(mono en puerta)");
        clips.assertString("(mesa cerca de ventana)");
        clips.assertString("(bananas colgando)");

        clips.run();

    }


}
