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
package net.beardbot.subsonic.client.api.usermanagement;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.lanwen.wiremock.ext.WiremockResolver;
import ru.lanwen.wiremock.ext.WiremockUriResolver;
import ru.lanwen.wiremock.ext.WiremockUriResolver.WiremockUri;

import static net.beardbot.subsonic.client.api.TestUtil.subsonic;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith({
        WiremockResolver.class,
        WiremockUriResolver.class
})
public class UserManagementApiTest {

    @Test
    void getUser(@WiremockResolver.Wiremock WireMockServer server, @WiremockUri String uri) {
        var user = subsonic(uri).users().getUser("testUser");
        assertThat(user.getUsername()).isEqualTo("testUser");
        assertThat(user.getEmail()).isEqualTo("test@test.com");
        assertThat(user.isAdminRole()).isFalse();
        assertThat(user.isSettingsRole()).isFalse();
        assertThat(user.isDownloadRole()).isTrue();
        assertThat(user.isUploadRole()).isFalse();
        assertThat(user.isPlaylistRole()).isTrue();
        assertThat(user.isCoverArtRole()).isFalse();
        assertThat(user.isCommentRole()).isFalse();
        assertThat(user.isPodcastRole()).isFalse();
        assertThat(user.isStreamRole()).isTrue();
        assertThat(user.isJukeboxRole()).isFalse();
        assertThat(user.isShareRole()).isFalse();
        assertThat(user.isVideoConversionRole()).isFalse();
        assertThat(user.isScrobblingEnabled()).isTrue();
        assertThat(user.getFolders()).containsExactly(2,5);
    }

    @Test
    void getUsers(@WiremockResolver.Wiremock WireMockServer server, @WiremockUri String uri) {
        var users = subsonic(uri).users().getAllUsers();
        assertThat(users).hasSize(2);

        var firstUser = users.get(0);
        assertThat(firstUser.getUsername()).isEqualTo("admin");
        assertThat(firstUser.getEmail()).isNullOrEmpty();
        assertThat(firstUser.isAdminRole()).isTrue();
        assertThat(firstUser.isSettingsRole()).isTrue();
        assertThat(firstUser.isDownloadRole()).isTrue();
        assertThat(firstUser.isUploadRole()).isTrue();
        assertThat(firstUser.isPlaylistRole()).isTrue();
        assertThat(firstUser.isCoverArtRole()).isTrue();
        assertThat(firstUser.isCommentRole()).isTrue();
        assertThat(firstUser.isPodcastRole()).isTrue();
        assertThat(firstUser.isStreamRole()).isTrue();
        assertThat(firstUser.isJukeboxRole()).isTrue();
        assertThat(firstUser.isShareRole()).isTrue();
        assertThat(firstUser.isVideoConversionRole()).isFalse();
        assertThat(firstUser.isScrobblingEnabled()).isTrue();
        assertThat(firstUser.getFolders()).containsExactly(2,3,4,5);
    }

    @Test
    void createUser(@WiremockResolver.Wiremock WireMockServer server, @WiremockUri String uri) {
        var createUserParams = CreateUserParams.create().ldapAuthenticated().musicFolder("15", "16")
                .roles(UserRole.ADMIN, UserRole.SETTINGS, UserRole.STREAM, UserRole.JUKEBOX, UserRole.DOWNLOAD, UserRole.UPLOAD,
                        UserRole.PLAYLISTS, UserRole.COVER_ART, UserRole.COMMENT, UserRole.PODCAST, UserRole.SHARE, UserRole.VIDEO_CONVERSION);

        subsonic(uri).users().createUser("testUser2", "testPassword2","test@test.com", createUserParams);
    }

    @Test
    void updateUser(@WiremockResolver.Wiremock WireMockServer server, @WiremockUri String uri) {
        var updateUserParams = UpdateUserParams.create().password("testPassword2").email("test@test.com")
                .ldapAuthentication().musicFolder("15", "16").maxBitRate(320)
                .roles(UserRole.ADMIN, UserRole.SETTINGS, UserRole.STREAM, UserRole.JUKEBOX, UserRole.DOWNLOAD, UserRole.UPLOAD,
                        UserRole.PLAYLISTS, UserRole.COVER_ART, UserRole.COMMENT, UserRole.PODCAST, UserRole.SHARE, UserRole.VIDEO_CONVERSION);

        subsonic(uri).users().updateUser("testUser2", updateUserParams);
    }

    @Test
    void deleteUser(@WiremockResolver.Wiremock WireMockServer server, @WiremockUri String uri) {
        subsonic(uri).users().deleteUser("testUser");
    }

    @Test
    void changePassword(@WiremockResolver.Wiremock WireMockServer server, @WiremockUri String uri) {
        subsonic(uri).users().changePassword("testUser", "testPassword");
    }
}
