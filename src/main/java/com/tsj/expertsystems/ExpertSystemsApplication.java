package com.tsj.expertsystems;

import jakarta.annotation.PostConstruct;
import net.sf.clipsrules.jni.CLIPSException;
import net.sf.clipsrules.jni.Environment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExpertSystemsApplication {


    public static void main(String[] args) {
        SpringApplication.run(ExpertSystemsApplication.class, args);
    }

    @PostConstruct
    private void run() throws CLIPSException {
        Environment clips = new Environment();

        //clips.load("src\\main\\resources\\clips\\monkey-bananas\\run");

        //clips.run();

        clips.load("src\\main\\resources\\clips\\market\\templates.clp");

        clips.load("src\\main\\resources\\clips\\market\\facts.clp");

        clips.load("src\\main\\resources\\clips\\market\\rules.clp");

        clips.reset();
        clips.run();
        clips.clear();


    }

}
