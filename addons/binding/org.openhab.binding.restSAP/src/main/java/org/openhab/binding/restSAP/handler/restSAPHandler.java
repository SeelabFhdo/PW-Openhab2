/**
 * Copyright (c) 2014 openHAB UG (haftungsbeschraenkt) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.restSAP.handler;

import static org.openhab.binding.restSAP.restSAPBindingConstants.*;

import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandler;
import org.eclipse.smarthome.core.types.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link restSAPHandler} is responsible for handling commands, which are
 * sent to one of the channels.
 *
 * @author Philip Wizenty - Initial contribution
 */
public class restSAPHandler extends BaseThingHandler {

    private Logger logger = LoggerFactory.getLogger(restSAPHandler.class);
    private RestSapHelper rsh;

    public restSAPHandler(Thing thing) {
        super(thing);
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {

        rsh = new RestSapHelper();
        if (channelUID.getId().equals(CHANNEL)) {
            logger.info(channelUID.getId() + "PW");
            // rsh.printItems();
            org.openhab.core.items.Item item = rsh.getItem(channelUID.getId());
            // rsh.sendRestData(channelUID.getId());
            // logger.info("/rest/sap/items/" + item.getName());
            // logger.info("/rest/sap/items/" + item.getState());
        }
        if (channelUID.getId().equals(TEMPERSTURE)) {
            logger.info(channelUID.getId() + "PW");
            // rsh.printItems();
            org.openhab.core.items.Item item = rsh.getItem(channelUID.getId());
            // rsh.sendRestData(channelUID.getId());
            // logger.info("/rest/sap/items/" + item.getName());
            // logger.info("/rest/sap/items/" + item.getState());
        }
    }
}
