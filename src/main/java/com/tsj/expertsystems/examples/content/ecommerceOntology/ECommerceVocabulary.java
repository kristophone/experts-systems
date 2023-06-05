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

/**
 * Vocabulary containing constants used by the ECommerceOntology.
 * @author Giovanni Caire - TILAB
 */
public interface ECommerceVocabulary {
    String ITEM = "ITEM";
    String ITEM_SERIALID = "serialID";

    String PRICE = "PRICE";
    String PRICE_VALUE = "value";
    String PRICE_CURRENCY = "currency";

    String CREDIT_CARD = "CREDITCARD";
    String CREDIT_CARD_TYPE = "type";
    String CREDIT_CARD_NUMBER = "number";
    String CREDIT_CARD_EXPIRATION_DATE = "expirationdate";

    String OWNS = "OWNS";
    String OWNS_OWNER = "Owner";
    String OWNS_ITEM = "item";

    String SELL = "SELL";
    String SELL_BUYER = "buyer";
    String SELL_ITEM = "item";
    String SELL_CREDIT_CARD = "creditcard";

    String COSTS = "COSTS";
    String COSTS_ITEM = "item";
    String COSTS_PRICE = "price";
}  
