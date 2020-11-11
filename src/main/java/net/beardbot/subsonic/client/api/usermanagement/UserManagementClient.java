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
package net.beardbot.subsonic.client.api.usermanagement;

import feign.Param;
import feign.QueryMap;
import feign.RequestLine;
import org.subsonic.restapi.SubsonicResponse;

import java.util.List;
import java.util.Map;

public interface UserManagementClient {
    @RequestLine("GET /rest/getUser?username={username}")
    SubsonicResponse getUser(@Param("username") String userName);

    @RequestLine("GET /rest/getUsers")
    SubsonicResponse getUsers();

    @RequestLine("GET /rest/createUser?username={username}&password={password}&email={email}")
    SubsonicResponse createUser(@Param("username") String userName, @Param("password") String password, @Param("email") String email, @QueryMap Map<String,List<String>> userParams);

    @RequestLine("GET /rest/updateUser?username={username}")
    SubsonicResponse updateUser(@Param("username") String userName, @QueryMap Map<String,List<String>> userParams);

    @RequestLine("GET /rest/deleteUser?username={username}")
    SubsonicResponse deleteUser(@Param("username") String userName);

    @RequestLine("GET /rest/changePassword?username={username}&password={password}")
    SubsonicResponse changePassword(@Param("username") String userName,@Param("password") String password);
}
