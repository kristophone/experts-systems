package com.tsj.expertsystems.agents.src.examples.surgicalintervention.fipa;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serial;
import java.io.Serializable;

public class MsgContent implements Serializable {

    @Serial
    private static final long serialVersionUID = 1621322487465667602L;

    private MsgContent() {
    }

    public MsgContent(String to, String knowledge, String legend) {
        this.to = to;
        this.knowledge = knowledge;
        this.legend = legend;
    }

    private String to;

    private String knowledge;

    private String legend;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getKnowledge() {
        return knowledge;
    }

    public void setKnowledge(String knowledge) {
        this.knowledge = knowledge;
    }

    public String getLegend() {
        return legend;
    }

    public void setLegend(String legend) {
        this.legend = legend;
    }

    public String toJsonString() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "{}";
    }

    public static MsgContent toObject(String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, MsgContent.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new MsgContent();
    }

    @Override
    public String toString() {
        return "MsgContent{" +
                "to='" + to + '\'' +
                ", knowledge='" + knowledge + '\'' +
                ", legend='" + legend + '\'' +
                '}';
    }

}
