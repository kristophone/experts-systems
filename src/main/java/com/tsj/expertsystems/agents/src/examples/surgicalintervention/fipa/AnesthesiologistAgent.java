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
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.ContractNetInitiator;
import net.sf.clipsrules.jni.CLIPSException;
import net.sf.clipsrules.jni.CaptureRouter;
import net.sf.clipsrules.jni.Environment;
import net.sf.clipsrules.jni.Router;

import java.util.Vector;

/**
 * This example shows how to implement the initiator role in
 * a FIPA-contract-net interaction protocol. In this case in particular
 * we use a <code>ContractNetInitiator</code>
 * to assign a dummy task to the agent that provides the best offer
 * among a set of agents (whose local
 * names must be specified as arguments).
 *
 * @author Giovanni Caire - TILAB
 */
public class AnesthesiologistAgent extends Agent {

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
                    (defrule anesthesiologist-operate
                        (operating-room)
                      =>
                      (printout t "{'to':'','knowledge':null,'legend':'Estoy en el cuarto de operaciones.'}" crlf)
                    )""");

            clips.build("""
                    (defrule anesthesiologist-confirms-sedation
                       (anesthetic-calculated)
                       =>
                       (printout t "{'to':'chief-surgeon-agent-1','knowledge':'patient-sedated', 'legend':'Anestesiólogo: Cirujano Jefe, el paciente se encuentra sedado.'}" crlf)
                     )""");


        } catch (CLIPSException e) {
            throw new RuntimeException(e);
        }

        // Comportamiento para enviar CFPs
        ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
        cfp.setProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET);
        cfp.addReceiver(getAID("room-agent-1"));

        cfp.setContent(
                new MsgContent(null, "anesthesiologist-ready", "Soy el Anestesiólogo y estoy listo").toJsonString()
        );

        addBehaviour(new ContractNetInitiator(this, cfp) {
            protected void handlePropose(ACLMessage propose, Vector acceptances) {
                // Procesar la propuesta recibida
                System.out.println("Anestesiólogo: Propuesta recibida de " + propose.getSender().getName());
                System.out.println("Contenido de la propuesta: " + propose.getContent());
                ACLMessage reply = propose.createReply();
                reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
                send(reply);
            }

            protected void handleRefuse(ACLMessage refuse) {
                // Procesar la propuesta rechazada
                System.out.println("Agente2: Propuesta rechazada por " + refuse.getSender().getName());
            }

            protected void handleInform(ACLMessage inform) {
                // Procesar el resultado
                System.out.println("Agente2: Resultado recibido de " + inform.getSender().getName());
                System.out.println("Contenido del resultado: " + inform.getContent());
            }

        });

        addBehaviour(new CyclicBehaviour() {
            public void action() {
                // Realizar otras acciones del agente
                // ...

                // Escuchar y procesar mensajes de inform
                ACLMessage msg = receive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
                if (msg != null) {
                    // Procesar el mensaje de inform recibido
                    System.out.println("Mensaje de inform recibido de " + msg.getSender().getName());
                    try {

                        clips.assertString("(%s)".formatted(msg.getContent()));

                        clips.run();

                        if (!theRouter.getOutput().isEmpty()) {
                            System.out.println(theRouter.getOutput());
                            MsgContent object1 = MsgContent.toObject(theRouter.getOutput().replace("'", "\""));
                            if (object1 != null && !object1.getTo().isEmpty()) {
                                msg.addReceiver(getAID(object1.getTo()));
                                msg.setContent(object1.getKnowledge());
                                send(msg);
                            }
                        }

                        theRouter.clear();
                    } catch (CLIPSException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    block();
                }
            }
        });

    }
}

