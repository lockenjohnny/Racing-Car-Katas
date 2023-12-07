package tddmicroexercises.tirepressuremonitoringsystem;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AlarmTest {

    @Test
    public void alarmIsInitiallyOff() {
        Alarm alarm = new Alarm();
        assertThat(alarm.isAlarmOn()).isFalse();
    }
}
