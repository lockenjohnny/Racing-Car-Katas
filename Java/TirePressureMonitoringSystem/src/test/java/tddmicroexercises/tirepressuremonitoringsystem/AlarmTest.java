package tddmicroexercises.tirepressuremonitoringsystem;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AlarmTest {
    @Mock
    Sensor sensor;
    @InjectMocks
    Alarm alarm;

    @Nested
    class SingleChecks {
        @Test
        public void alarmIsInitiallyOff() {
            assertThat(alarm.isAlarmOn()).isFalse();
        }

        @Test
        void checkActivatesAlarmWhenPressureIsTooLow() {
            when(sensor.popNextPressurePsiValue()).thenReturn(16.0);
            alarm.check();
            assertThat(alarm.isAlarmOn()).isTrue();
        }

        @Test
        void checkActivatesAlarmWhenPressureIsTooHigh() {
            when(sensor.popNextPressurePsiValue()).thenReturn(21.1);
            alarm.check();
            assertThat(alarm.isAlarmOn()).isTrue();
        }

        @ParameterizedTest
        @ValueSource(doubles = {17, 18, 19, 20, 21})
        void checkDoesNotActivateForPressureWithinThreshold(double pressure) {
            when(sensor.popNextPressurePsiValue()).thenReturn(pressure);
            alarm.check();
            assertThat(alarm.isAlarmOn()).isFalse();
        }
    }

    @Nested
    class MultipleChecks {
        @Test
        void checkTwiceActivatesAlarmForPressureTooHighAfterPressureWithinThreshold() {
            when(sensor.popNextPressurePsiValue()).thenReturn(21.0, 21.1);
            alarm.check();
            alarm.check();
            assertThat(alarm.isAlarmOn()).isTrue();
        }

        @Test
        void checkKeepsAlarmOnForPressureWithinThresholdAfterPressureTooLow() {
            when(sensor.popNextPressurePsiValue()).thenReturn(16.0, 17.0);
            alarm.check();
            alarm.check();
            assertThat(alarm.isAlarmOn()).isTrue();
        }

        @Test
        void checkKeepsAlarmOnForPressureWithinThresholdAfterPressureTooHigh() {
            when(sensor.popNextPressurePsiValue()).thenReturn(22.0, 21.0);
            alarm.check();
            alarm.check();
            assertThat(alarm.isAlarmOn()).isTrue();
        }
    }
}
