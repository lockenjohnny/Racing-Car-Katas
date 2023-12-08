package tddmicroexercises.tirepressuremonitoringsystem;

public class Alarm {
    private static final double LOW_PRESSURE_THRESHOLD = 17;
    private static final double HIGH_PRESSURE_THRESHOLD = 21;

    protected Sensor sensor;

    protected boolean alarmOn = false;

    public Alarm(Sensor sensor) {
        this.sensor = sensor;
    }

    private static boolean pressureExceedingThreshold(double psiPressureValue) {
        return psiPressureValue < LOW_PRESSURE_THRESHOLD || HIGH_PRESSURE_THRESHOLD < psiPressureValue;
    }

    public void check() {
        double psiPressureValue = sensor.popNextPressurePsiValue();

        if (pressureExceedingThreshold(psiPressureValue)) {
            alarmOn = true;
        }
    }

    public boolean isAlarmOn() {
        return alarmOn;
    }
}
