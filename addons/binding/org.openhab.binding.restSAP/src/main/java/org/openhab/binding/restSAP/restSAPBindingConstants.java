/**
 * Copyright (c) 2014 openHAB UG (haftungsbeschraenkt) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.restSAP;

import org.eclipse.smarthome.core.thing.ThingTypeUID;

/**
 * The {@link restSAPBinding} class defines common constants, which are
 * used across the whole binding.
 *
 * @author Philip Wizenty - Initial contribution
 */
public class restSAPBindingConstants {

    public static final String BINDING_ID = "restSAP";

    // List of all Thing Type UIDs
    public final static ThingTypeUID THING_TYPE_SAMPLE = new ThingTypeUID(BINDING_ID, "restSAP");

    // List of all Channel ids
    public final static String SEND = "Send";
    public final static String VOLUME = "Volume";
    public final static String CHANNEL = "Channel";
    public final static String TEMPERSTURE = "Temperature";

}
