/**
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
package net.beardbot.subsonic.client;

import lombok.*;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.UUID;

@Data
public class SubsonicPreferences {
    private String serverUrl;
    private String username;
    private String clientName = "SubsonicJavaClient";
    private int streamBitRate = 192;
    private String streamFormat = "mp3";

    private final SubsonicAuthentication authentication;

    public SubsonicPreferences(String serverUrl, String username, String password) {
        this.serverUrl = serverUrl;
        this.username = username;
        this.authentication = new SubsonicAuthentication(password);
    }

    public void setPassword(String password){
        authentication.update(password);
    }

    @Getter
    public static class SubsonicAuthentication {
        private String salt;
        private String token;

        public SubsonicAuthentication(String password) {
            update(password);
        }

        void update(String password){
            this.salt = UUID.randomUUID().toString();
            this.token = DigestUtils.md5Hex(password + salt);
        }
    }
}
