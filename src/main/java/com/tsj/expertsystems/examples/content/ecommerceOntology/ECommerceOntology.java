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
package com.tsj.expertsystems.examples.content.ecommerceOntology;

import jade.content.onto.BasicOntology;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.schema.*;

/**
 * Ontology containing concepts related to buying/selling items.
 * @author Giovanni Caire - TILAB
 */
public class ECommerceOntology extends Ontology implements ECommerceVocabulary {
    // NAME
    public static final String ONTOLOGY_NAME = "E-Commerce-ontology";

    // The singleton instance of this ontology
    private static final Ontology theInstance = new ECommerceOntology(BasicOntology.getInstance());

    /**
     * Constructor
     */
    private ECommerceOntology(Ontology base) {
        super(ONTOLOGY_NAME, base);

        try {
            add(new ConceptSchema(ITEM), Item.class);
            add(new ConceptSchema(CREDIT_CARD), CreditCard.class);
            add(new ConceptSchema(PRICE), Price.class);
            add(new AgentActionSchema(SELL), Sell.class);
            add(new PredicateSchema(OWNS), Owns.class);
            add(new PredicateSchema(COSTS), Costs.class);


            ConceptSchema cs = (ConceptSchema) getSchema(ITEM);
            cs.add(ITEM_SERIALID, (PrimitiveSchema) getSchema(BasicOntology.INTEGER), ObjectSchema.OPTIONAL);

            cs = (ConceptSchema) getSchema(PRICE);
            cs.add(PRICE_VALUE, (PrimitiveSchema) getSchema(BasicOntology.FLOAT));
            cs.add(PRICE_CURRENCY, (PrimitiveSchema) getSchema(BasicOntology.STRING));

            cs = (ConceptSchema) getSchema(CREDIT_CARD);
            cs.add(CREDIT_CARD_TYPE, (PrimitiveSchema) getSchema(BasicOntology.STRING));
            cs.add(CREDIT_CARD_NUMBER, (PrimitiveSchema) getSchema(BasicOntology.INTEGER));
            cs.add(CREDIT_CARD_EXPIRATION_DATE, (PrimitiveSchema) getSchema(BasicOntology.DATE), ObjectSchema.OPTIONAL);

            AgentActionSchema as = (AgentActionSchema) getSchema(SELL);
            as.add(SELL_BUYER, (ConceptSchema) getSchema(BasicOntology.AID));
            as.add(SELL_ITEM, (ConceptSchema) getSchema(ITEM));
            as.add(SELL_CREDIT_CARD, (ConceptSchema) getSchema(CREDIT_CARD));

            PredicateSchema ps = (PredicateSchema) getSchema(OWNS);
            ps.add(OWNS_OWNER, getSchema(BasicOntology.AID));
            ps.add(OWNS_ITEM, getSchema(ITEM));

            ps = (PredicateSchema) getSchema(COSTS);
            ps.add(COSTS_ITEM, getSchema(ITEM));
            ps.add(COSTS_PRICE, getSchema(PRICE));

            useConceptSlotsAsFunctions();
        } catch (OntologyException oe) {
            oe.printStackTrace();
        }
    }

    public static Ontology getInstance() {
        return theInstance;
    }

}
