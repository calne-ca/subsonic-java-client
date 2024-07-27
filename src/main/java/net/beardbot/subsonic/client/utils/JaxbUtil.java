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
package net.beardbot.subsonic.client.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.subsonic.restapi.ObjectFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JaxbUtil {

    private static final XMLInputFactory inputFactory = createInputFactory();
    private static final Unmarshaller unmarshaller = createUnmarshaller();

    @SneakyThrows
    public static <T> T unmarshall(InputStream xmlStream, Class<T> clazz){
        var reader = inputFactory.createXMLStreamReader(xmlStream);
        return (T) unmarshaller.unmarshal(reader);
    }

    private static XMLInputFactory createInputFactory() {
        var xif = XMLInputFactory.newFactory();
        xif.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
        xif.setProperty(XMLInputFactory.SUPPORT_DTD, false);
        return xif;
    }

    @SneakyThrows
    private static Unmarshaller createUnmarshaller() {
        return JAXBContext.newInstance(ObjectFactory.class).createUnmarshaller();
    }
}
