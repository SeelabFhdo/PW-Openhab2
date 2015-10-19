package org.openhab.binding.samsungtv.internal.discovery;

import static org.openhab.binding.samsungtv.SamsungTvBindingConstants.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.smarthome.config.discovery.DiscoveryResult;
import org.eclipse.smarthome.config.discovery.DiscoveryResultBuilder;
import org.eclipse.smarthome.config.discovery.UpnpDiscoveryParticipant;
import org.eclipse.smarthome.core.thing.ThingTypeUID;
import org.eclipse.smarthome.core.thing.ThingUID;
import org.jupnp.model.meta.DeviceDetails;
import org.jupnp.model.meta.ManufacturerDetails;
import org.jupnp.model.meta.ModelDetails;
import org.jupnp.model.meta.RemoteDevice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SamsungTvDiscoveryParticipant implements UpnpDiscoveryParticipant {
    private Logger logger = LoggerFactory.getLogger(SamsungTvDiscoveryParticipant.class);

    @Override
    public Set<ThingTypeUID> getSupportedThingTypeUIDs() {
        return Collections.singleton(SAMSUNG_TV);
    }

    @Override
    public DiscoveryResult createResult(RemoteDevice device) {
        ThingUID uid = getThingUID(device);
        if (uid != null) {
            logger.debug("UID von Samsung: " + uid.getAsString());
            Map<String, Object> properties = new HashMap<>(2);
            properties.put(HOSTIP, device.getIdentity().getDescriptorURL().getHost());
            properties.put(HOSTPORT, device.getIdentity().getDescriptorURL().getPort());
            properties.put(SERIAL_NUMBER, device.getDetails().getSerialNumber());
            DiscoveryResult result = DiscoveryResultBuilder.create(uid).withProperties(properties)
                    .withLabel(device.getDetails().getFriendlyName()).withRepresentationProperty(SERIAL_NUMBER).build();
            return result;
        }
        return null;
    }

    @Override
    public ThingUID getThingUID(RemoteDevice device) {
        if (device != null) {
            DeviceDetails details = device.getDetails();
            if (details != null) {
                ManufacturerDetails manuDet = details.getManufacturerDetails();
                ModelDetails modelDet = details.getModelDetails();
                if (manuDet != null && modelDet != null) {
                    if (manuDet.getManufacturer().toUpperCase().equals("SAMSUNG ELECTRONICS")
                            && modelDet.getModelDescription().toUpperCase().equals("SAMSUNG TV RCR")) {
                        if (device.getIdentity().getDescriptorURL().getHost() != null) {
                            logger.debug("TV gefunden: " + device.getDisplayString() + " IP: "
                                    + device.getIdentity().getDescriptorURL().getHost());
                            return new ThingUID(SAMSUNG_TV, details.getSerialNumber());
                        }

                    }
                }
            }
        }
        return null;
    }
}
