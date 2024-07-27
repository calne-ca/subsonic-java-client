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