/*
 * Copyright (C) 2020 Joscha Düringer
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.beardbot.subsonic.client.base;

import feign.Feign;
import feign.Logger;
import feign.jaxb.JAXBContextFactory;
import feign.jaxb.JAXBDecoder;
import feign.slf4j.Slf4jLogger;
import lombok.SneakyThrows;
import net.beardbot.subsonic.client.Subsonic;
import org.subsonic.restapi.ObjectFactory;

import java.util.Collections;

public class SubsonicClient {
    public static <T> T create(Subsonic subsonic, Class<T> clazz){
        return Feign.builder()
                .requestInterceptor(new SubsonicBaseParamInterceptor(subsonic))
                .decoder(jaxbDecoder())
                .errorDecoder(new SubsonicErrorDecoder())
                .logger(new Slf4jLogger(clazz))
                .logLevel(Logger.Level.FULL)
                .target(clazz, subsonic.getPreferences().getServerUrl());
    }

    @SneakyThrows
    private static JAXBDecoder jaxbDecoder(){
        JAXBContextFactory jaxbContextFactory = new JAXBContextFactory.Builder().build(Collections.singletonList(ObjectFactory.class));
        return new JAXBDecoder.Builder().withJAXBContextFactory(jaxbContextFactory).build();
    }
}
