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
package com.tsj.expertsystems.examples.content.mso;

import com.tsj.expertsystems.examples.content.eco.ECommerceOntology;
import com.tsj.expertsystems.examples.content.mso.elements.Single;
import com.tsj.expertsystems.examples.content.mso.elements.Track;
import com.tsj.expertsystems.examples.content.musicShopOntology.CD;
import jade.content.onto.BeanOntology;
import jade.content.onto.BeanOntologyException;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyUtils;

/**
 * Ontology containing music related concepts.
 *
 * @author Giovanni Caire - TILAB
 */
public class MusicShopOntology extends BeanOntology {
    // NAME
    public static final String ONTOLOGY_NAME = "Music-shop-ontology";
    private static final long serialVersionUID = 1L;
    // The singleton instance of this ontology
    private static Ontology INSTANCE;

    /**
     * Constructor
     *
     * @throws BeanOntologyException
     */
    private MusicShopOntology() throws BeanOntologyException {
        super(ONTOLOGY_NAME, ECommerceOntology.getInstance());

        add(Track.class);
        add(CD.class);
        add(Single.class);
    }

    public synchronized final static Ontology getInstance() throws BeanOntologyException {
        if (INSTANCE == null) {
            INSTANCE = new MusicShopOntology();
        }
        return INSTANCE;
    }

    public static void main(String[] args) throws Exception {
        Ontology onto = getInstance();
        OntologyUtils.exploreOntology(onto);
        onto = ECommerceOntology.getInstance();
        OntologyUtils.exploreOntology(onto);
    }
}
