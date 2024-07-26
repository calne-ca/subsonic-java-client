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

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class VersionTest {
    @Test
    void oneDigitVersion() {
        assertThat(Version.of("1").isLowerThan(Version.of("1"))).isFalse();
        assertThat(Version.of("2").isLowerThan(Version.of("1"))).isFalse();
        assertThat(Version.of("1").isLowerThan(Version.of("2"))).isTrue();
    }

    @Test
    void twoDigitVersion() {
        assertThat(Version.of("1.0").isLowerThan(Version.of("1.0"))).isFalse();
        assertThat(Version.of("1.1").isLowerThan(Version.of("1.0"))).isFalse();
        assertThat(Version.of("2.0").isLowerThan(Version.of("1.0"))).isFalse();
        assertThat(Version.of("1.1").isLowerThan(Version.of("2.0"))).isTrue();
        assertThat(Version.of("1.0").isLowerThan(Version.of("1.1"))).isTrue();
    }

    @Test
    void threeDigitVersion() {
        assertThat(Version.of("1.0.0").isLowerThan(Version.of("0.9.9"))).isFalse();
        assertThat(Version.of("1.1.0").isLowerThan(Version.of("1.0.0"))).isFalse();
        assertThat(Version.of("1.1.0").isLowerThan(Version.of("1.0.1"))).isFalse();
        assertThat(Version.of("1.1.0").isLowerThan(Version.of("1.1.1"))).isTrue();
        assertThat(Version.of("1.1.0").isLowerThan(Version.of("2.0.0"))).isTrue();
    }

    @Test
    void mixedDigitVersion() {
        assertThat(Version.of("1.0").isLowerThan(Version.of("1.0.0"))).isFalse();
        assertThat(Version.of("2.0").isLowerThan(Version.of("1.9.9"))).isFalse();
        assertThat(Version.of("3").isLowerThan(Version.of("2.9.9"))).isFalse();
        assertThat(Version.of("1.0").isLowerThan(Version.of("1.0.1"))).isTrue();
        assertThat(Version.of("1").isLowerThan(Version.of("1.1"))).isTrue();
    }

    @Test
    void invalidVersion() {
        assertThatCode(()->Version.of("1-2-3")).isExactlyInstanceOf(IllegalArgumentException.class);
        assertThatCode(()->Version.of("3.2.0.M")).isExactlyInstanceOf(IllegalArgumentException.class);
        assertThatCode(()->Version.of("")).isExactlyInstanceOf(IllegalArgumentException.class);
        assertThatCode(()->Version.of(null)).isExactlyInstanceOf(IllegalArgumentException.class);
    }
}