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

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class LocalDateTimeAdapterTest {
    private final LocalDateTimeAdapter localDateTimeAdapter = new LocalDateTimeAdapter();

    @Test
    void unmarshall() {
        assertUnmarshall("2024-07-18T22:20:25", 2024, 7, 18, 22, 20, 25);
        assertUnmarshall("2024-07-18T22:20:25.220976486Z", 2024, 7, 18, 22, 20, 25);
        assertUnmarshall("2024-07-18T22:20:25.220976486+02:00", 2024, 7, 18, 22, 20, 25);
    }

    @Test
    void marshall() {
        assertMarshall("2024-07-18T22:20:25", 2024, 7, 18, 22, 20, 25);
    }

    private void assertUnmarshall(String dateTimeString, int year, int month, int day, int hour, int minute, int second) {
        var localDateTime = localDateTimeAdapter.unmarshal(dateTimeString);
        assertThat(localDateTime).isNotNull();
        assertThat(localDateTime).hasYear(year);
        assertThat(localDateTime).hasMonthValue(month);
        assertThat(localDateTime).hasDayOfMonth(day);
        assertThat(localDateTime).hasHour(hour);
        assertThat(localDateTime).hasMinute(minute);
        assertThat(localDateTime).hasSecond(second);
    }

    private void assertMarshall(String dateTimeString, int year, int month, int day, int hour, int minute, int second) {
        var localDateTimeString = localDateTimeAdapter.marshal(LocalDateTime.of(year, month, day, hour, minute, second));
        assertThat(localDateTimeString).isEqualTo(dateTimeString);
    }
}