/**
 * Copyright (c) 2014 openHAB UG (haftungsbeschraenkt) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.samsungtv.handler;

import static org.openhab.binding.samsungtv.SamsungTvBindingConstants.*;

import org.eclipse.smarthome.config.core.Configuration;
import org.eclipse.smarthome.core.library.types.IncreaseDecreaseType;
import org.eclipse.smarthome.core.library.types.OnOffType;
import org.eclipse.smarthome.core.library.types.StopMoveType;
import org.eclipse.smarthome.core.library.types.UpDownType;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandler;
import org.eclipse.smarthome.core.types.Command;
import org.openhab.binding.samsungtv.SamsungTvBindingConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link SamsungTvHandler} is responsible for handling commands, which are
 * sent to one of the channels.
 *
 * @author Philip Wizenty - Initial contribution
 */
public class SamsungTvHandler extends BaseThingHandler {

    private Logger logger = LoggerFactory.getLogger(SamsungTvHandler.class);
    private SamsungConnectionHelper tv;
    private String ip;
    private Integer port;

    public SamsungTvHandler(Thing thing) {
        super(thing);
    }

    @Override
    public void initialize() {
        super.initialize();
        tv = new SamsungConnectionHelper();
        Configuration config = thing.getConfiguration();
        ip = (String) config.get(SamsungTvBindingConstants.HOSTIP);
        // port = ((BigDecimal) config.get(SamsungTvBindingConstants.HOSTPORT)).intValue();
        port = 55000;
        tv.connectToTv(ip, port);

        tv.sendAuth();
        updateStatus(ThingStatus.ONLINE);
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {

        if (channelUID.getId().equals(VOLUME)) {
            if (command instanceof UpDownType) {
                if (command == UpDownType.UP) {
                    tv.sendKey("KEY_VOLUP");
                } else {
                    tv.sendKey("KEY_VOLDOWN");
                }

            } else if (command instanceof StopMoveType) {
                if (command == StopMoveType.STOP) {
                    tv.sendKey("KEY_MUTE");
                }
            } else if (channelUID.getId().equals(VOLUME)) {
                if (command instanceof IncreaseDecreaseType) {
                    if (command == IncreaseDecreaseType.INCREASE) {
                        tv.sendKey("KEY_VOLUP");
                    } else {
                        tv.sendKey("KEY_VOLDOWN");
                    }
                }
            } else {
                logger.info("unknown command type");
            }
        }

        if (channelUID.getId().equals(CHANNEL)) {
            if (command instanceof UpDownType) {
                if (command == UpDownType.UP) {
                    tv.sendKey("KEY_CHUP");
                } else {
                    tv.sendKey("KEY_CHDOWN");
                }

            } else if (command instanceof StopMoveType) {
                if (command == StopMoveType.STOP) {
                    tv.sendKey("KEY_7");
                }
            } else if (channelUID.getId().equals(CHANNEL)) {
                if (command instanceof IncreaseDecreaseType) {
                    if (command == IncreaseDecreaseType.INCREASE) {
                        tv.sendKey("KEY_CHUP");
                    } else {
                        tv.sendKey("KEY_CHDOWN");
                    }
                }
            } else {
                logger.info("unknown command type");
            }
        }

        if (channelUID.getId().equals(CONNECT)) {
            if (command instanceof OnOffType) {
                if (command == OnOffType.ON) {
                    ///
                } else {
                    tv.end();
                }
            } else {
                logger.info("unknown command type");
            }
        }

        if (channelUID.getId().equals(MUTE)) {
            if (command instanceof OnOffType) {
                if (command == OnOffType.ON) {
                    tv.sendKey("KEY_MUTE");
                } else {
                    tv.sendKey("KEY_MUTE");
                }
            } else {
                logger.info("unknown command type");
            }
        }
    }

}
