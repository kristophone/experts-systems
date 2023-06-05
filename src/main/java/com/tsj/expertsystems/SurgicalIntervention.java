package com.tsj.expertsystems;

import net.sf.clipsrules.jni.CLIPSException;
import net.sf.clipsrules.jni.Environment;

public class SurgicalIntervention {

    public static void main(String[] args) throws CLIPSException {

        Environment clips = new Environment();

        clips.load("src\\main\\resources\\clips\\surgical-intervention\\run.clp");
        //clips.reset();
        clips.assertString("(patient-ready)");
        clips.assertString("(assistant-nurse-ready)");
        clips.assertString("(anesthesiologist-ready)");
        clips.assertString("(chief-surgeon-ready)");
        clips.assertString("(surgeon2-ready)");

        clips.run();

    }


}
