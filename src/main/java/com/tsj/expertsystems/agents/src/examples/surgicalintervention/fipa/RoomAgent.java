/**
 * ***************************************************************
 * JADE - Java Agent DEvelopment Framework is a framework to develop
 * multi-agent systems in compliance with the FIPA specifications.
 * Copyright (C) 2000 CSELT S.p.A.
 * <p>
 * GNU Lesser General Public License
 * <p>
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation,
 * version 2.1 of the License.
 * <p>
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA  02111-1307, USA.
 * **************************************************************
 */
package com.tsj.expertsystems.agents.src.examples.surgicalintervention.fipa;

import jade.core.Agent;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.ContractNetResponder;
import net.sf.clipsrules.jni.*;

import java.util.List;

/**
 * This example shows how to implement the responder role in
 * a FIPA-contract-net interaction protocol. In this case in particular
 * we use a <code>ContractNetResponder</code>
 * to participate into a negotiation where an initiator needs to assign
 * a task to an agent among a set of candidates.
 *
 * @author Giovanni Caire - TILAB
 */
public class RoomAgent extends Agent {

    private Environment clips;

    private CaptureRouter theRouter;

    protected void setup() {

        clips = new Environment();

        // https://stackoverflow.com/questions/58895760/how-to-re-direct-clips-jni-java-output-to-a-string
        theRouter = new CaptureRouter(clips, new String[]{Router.STDOUT,
                Router.STDERR,
                Router.STDWRN});

        try {

            clips.build("""
                    (defrule surgeon2-informs-chief-surgeon
                        (surgeon2-ready) (patient-ready) (assistant-nurse-ready) (anesthesiologist-ready) (chief-surgeon-ready)
                      =>
                      (printout t "{'to':'chief-surgeon-agent-1','knowledge':'operation-started', 'legend':'Room: Cirujano Jefe, la intervención puede comenzar.'}" crlf)
                    )""");


        } catch (CLIPSException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Agent " + getLocalName() + " waiting for CFP...");
        MessageTemplate template = MessageTemplate.and(
                MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET),
                MessageTemplate.MatchPerformative(ACLMessage.CFP));

        // Comportamiento para responder a los CFPs
        addBehaviour(new ContractNetResponder(this, template) {
            protected ACLMessage handleCfp(ACLMessage cfp) {
                // Procesar el CFP recibido
                System.out.println("Agente1: CFP recibido de " + cfp.getSender().getName());
                System.out.println("Contenido del CFP: " + cfp.getContent());

                // Generar la propuesta
                ACLMessage propose = cfp.createReply();
                propose.setPerformative(ACLMessage.PROPOSE);
                propose.setContent("Propuesta desde Agente1");
                return propose;
            }

            protected ACLMessage handleAcceptProposal(ACLMessage cfp, ACLMessage propose, ACLMessage accept) {
                // Procesar la propuesta aceptada
                System.out.println("Agente1: Propuesta aceptada por " + accept.getSender().getName());

                ACLMessage inform = cfp.createReply();
                inform.setPerformative(ACLMessage.INFORM);

                try {

                    List<String> factsBefore = clips.getFactList().stream().map(FactInstance::getRelationName).toList();

                    System.out.println("Before:");
                    System.out.println(factsBefore);

                    if (!factsBefore.contains(cfp.getContent())) {

                        MsgContent object = MsgContent.toObject(cfp.getContent());
                        clips.assertString("(%s)".formatted(object.getKnowledge()));
                        inform.setContent("operating-room");

                    }

                    clips.run();

                    List<String> factsAfter = clips.getFactList().stream().map(FactInstance::getRelationName).toList();

                    System.out.println("After:");
                    System.out.println(factsAfter);

                    //clips.eval("(facts)");

                    if (!theRouter.getOutput().isEmpty()) {
                        System.out.println(theRouter.getOutput());
                        MsgContent object = MsgContent.toObject(theRouter.getOutput().replace("'", "\""));
                        inform.clearAllReceiver();
                        if (!object.getTo().isEmpty()) {
                            inform.addReceiver(getAID(object.getTo()));
                            inform.setContent(object.getKnowledge());
                        }
                    }

                    theRouter.clear();

                } catch (CLIPSException e) {
                    throw new RuntimeException(e);
                }

                return inform;
            }

            protected void handleRejectProposal(ACLMessage cfp, ACLMessage propose, ACLMessage reject) {
                // Procesar la propuesta rechazada
                System.out.println("Agente1: Propuesta rechazada por " + reject.getSender().getName());
            }

        });


    }

}

