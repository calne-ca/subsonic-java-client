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

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Version implements Comparable<Version> {

    private static final String VERSION_PATTERN = "[0-9]+(\\.[0-9]+)*";
    private final String versionString;

    public static Version of(String versionString){
        return new Version(versionString);
    }

    private Version(String versionString) {
        if(versionString == null || !versionString.matches(VERSION_PATTERN)) {
            throw new IllegalArgumentException("Invalid version format");
        }
        this.versionString = versionString;
    }

    public boolean isLowerThan(Version version){
        return compareTo(version) < 0;
    }

    @Override
    public int compareTo(Version that) {
        if(that == null) {
            return 1;
        }

        String[] thisParts = this.getVersionString().split("\\.");
        String[] thatParts = that.getVersionString().split("\\.");

        int length = Math.max(thisParts.length, thatParts.length);

        for(int i = 0; i < length; i++) {
            int thisPart = i < thisParts.length ? Integer.parseInt(thisParts[i]) : 0;
            int thatPart = i < thatParts.length ? Integer.parseInt(thatParts[i]) : 0;

            if(thisPart < thatPart) {
                return -1;
            }
            if(thisPart > thatPart) {
                return 1;
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        return versionString;
    }
}