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
package com.tsj.expertsystems.examples.content.musicShopOntology;

import com.tsj.expertsystems.examples.content.ecommerceOntology.ECommerceOntology;
import jade.content.onto.BasicOntology;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.schema.ConceptSchema;
import jade.content.schema.ObjectSchema;
import jade.content.schema.PrimitiveSchema;
import jade.content.schema.TermSchema;
import jade.content.schema.facets.CardinalityFacet;

/**
 * Ontology containing music related concepts.
 *
 * @author Giovanni Caire - TILAB
 */
public class MusicShopOntology extends Ontology implements MusicShopVocabulary {
    // NAME
    public static final String ONTOLOGY_NAME = "Music-shop-ontology";

    // The singleton instance of this ontology
    private static final Ontology theInstance = new MusicShopOntology(ECommerceOntology.getInstance());

    /**
     * Constructor
     */
    private MusicShopOntology(Ontology base) {
        super(ONTOLOGY_NAME, base);

        try {
            add(new ConceptSchema(CD), CD.class);
            add(new ConceptSchema(TRACK), Track.class);
            add(new ConceptSchema(SINGLE), Single.class);

            ConceptSchema cs = (ConceptSchema) getSchema(CD);
            cs.addSuperSchema((ConceptSchema) getSchema(ECommerceOntology.ITEM));
            cs.add(CD_TITLE, (PrimitiveSchema) getSchema(BasicOntology.STRING));
            cs.add(CD_TRACKS, (ConceptSchema) getSchema(TRACK), 1, ObjectSchema.UNLIMITED);

            cs = (ConceptSchema) getSchema(TRACK);
            cs.add(TRACK_NAME, (TermSchema) getSchema(BasicOntology.STRING));
            cs.add(TRACK_DURATION, (TermSchema) getSchema(BasicOntology.INTEGER), ObjectSchema.OPTIONAL);
            cs.add(TRACK_PCM, (TermSchema) getSchema(BasicOntology.BYTE_SEQUENCE), ObjectSchema.OPTIONAL);

            cs = (ConceptSchema) getSchema(SINGLE);
            cs.addSuperSchema((ConceptSchema) getSchema(CD));
            // A SINGLE only includes two tracks
            cs.addFacet(CD_TRACKS, new CardinalityFacet(2, 2));
        } catch (OntologyException oe) {
            oe.printStackTrace();
        }
    }

    public static Ontology getInstance() {
        return theInstance;
    }

}
