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

import net.beardbot.subsonic.client.Subsonic;
import net.beardbot.subsonic.client.SubsonicPreferences;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.subsonic.restapi.User;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static net.beardbot.subsonic.client.api.TestUtil.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserManagementServiceTest {
    @Mock
    private UserManagementClient userManagementClient;

    @Mock
    private Subsonic subsonic;

    @Mock
    private SubsonicPreferences subsonicPreferences;

    private UserManagementService userManagementService;

    @BeforeEach
    void setUp() {
        userManagementService = new UserManagementService(userManagementClient, subsonic);
    }

    @Test
    void getUser() {
        var usersResponse = userResponse();

        when(userManagementClient.getUser("testUser")).thenReturn(usersResponse);

        var user = userManagementService.getUser("testUser");
        assertThat(user).isEqualTo(usersResponse.getUser());
    }

    @Test
    void getUsers() {
        var usersResponse = usersResponse();

        when(userManagementClient.getUsers()).thenReturn(usersResponse);

        List<User> users = userManagementService.getAllUsers();
        assertThat(users).isEqualTo(usersResponse.getUsers().getUsers());
    }

    @Test
    void createUser_noParams() {
        when(userManagementClient.createUser("testUser", "testPassword","test@test.com", Collections.emptyMap())).thenReturn(subsonicResponse());
        userManagementService.createUser("testUser", "testPassword","test@test.com");
    }

    @Test
    void createUser() {
        var params = new HashMap<String,List<String>>();
        params.put("ldapAuthenticated", Collections.singletonList("true"));
        params.put("adminRole", Collections.singletonList("true"));
        params.put("settingsRole", Collections.singletonList("true"));
        params.put("streamRole", Collections.singletonList("true"));
        params.put("jukeboxRole", Collections.singletonList("true"));
        params.put("downloadRole", Collections.singletonList("true"));
        params.put("uploadRole", Collections.singletonList("true"));
        params.put("playlistRole", Collections.singletonList("true"));
        params.put("coverArtRole", Collections.singletonList("true"));
        params.put("commentRole",Collections.singletonList("true"));
        params.put("podcastRole",Collections.singletonList("true"));
        params.put("shareRole",Collections.singletonList("true"));
        params.put("videoConversionRole",Collections.singletonList("true"));
        params.put("musicFolderId",Arrays.asList("15","16"));

        when(userManagementClient.createUser("testUser", "testPassword","test@test.com", params)).thenReturn(subsonicResponse());

        userManagementService.createUser("testUser", "testPassword","test@test.com",
                CreateUserParams.create().ldapAuthenticated().musicFolder("15","16")
                .roles(UserRole.ADMIN, UserRole.SETTINGS, UserRole.STREAM, UserRole.JUKEBOX,
                        UserRole.DOWNLOAD, UserRole.UPLOAD, UserRole.PLAYLISTS, UserRole.COVER_ART,
                        UserRole.COMMENT, UserRole.PODCAST, UserRole.SHARE, UserRole.VIDEO_CONVERSION));
    }

    @Test
    void deleteUser() {
        when(userManagementClient.deleteUser("testUser")).thenReturn(subsonicResponse());
        userManagementService.deleteUser("testUser");
    }

    @Test
    void changePassword() {
        when(subsonicPreferences.getUsername()).thenReturn("testUser");
        when(subsonic.getPreferences()).thenReturn(subsonicPreferences);
        when(userManagementClient.changePassword("testUser", "testPassword")).thenReturn(subsonicResponse());

        userManagementService.changePassword("testUser","testPassword");

        verify(subsonicPreferences).setPassword("testPassword");
    }

    @Test
    void changePassword_differentUser_doesNotUpdatePassword() {
        when(subsonicPreferences.getUsername()).thenReturn("admin");
        when(subsonic.getPreferences()).thenReturn(subsonicPreferences);
        when(userManagementClient.changePassword("testUser", "testPassword")).thenReturn(subsonicResponse());

        userManagementService.changePassword("testUser","testPassword");

        verify(subsonicPreferences, never()).setPassword(anyString());
    }
}