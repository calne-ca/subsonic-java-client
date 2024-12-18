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
package net.beardbot.subsonic.client.api.media;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.beardbot.subsonic.client.base.ApiParams;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StreamParams extends ApiParams {
    public static StreamParams create(){
        return new StreamParams();
    }

    public StreamParams format(String format) {
        setParam("format", format);
        return this;
    }

    public StreamParams maxBitRate(int maxBitRate) {
        setParam("maxBitRate", String.valueOf(maxBitRate));
        return this;
    }

    public StreamParams estimateContentLength(boolean estimateContentLength) {
        setParam("estimateContentLength", String.valueOf(estimateContentLength));
        return this;
    }
}
