
package frc.lib.encoder;

import com.ctre.phoenix.motorcontrol.SensorCollection;

public class MagEncoderAbsolute {

    private final SensorCollection m_sensors;

    private boolean m_discontinuityPresent = false;

    private int m_bookEnd_0 = 0;
    private int m_bookEnd_1 = 4096;

    private int m_timeoutMs = 30;

    public MagEncoderAbsolute(SensorCollection sensors) {
        m_sensors = sensors;
    }

    public MagEncoderAbsolute(SensorCollection sensors, boolean discontinuityPresent, int bookEnd_0, int bookEnd_1,
            int timeoutMs) {
        m_sensors = sensors;

        m_discontinuityPresent = discontinuityPresent;

        m_bookEnd_0 = bookEnd_0;
        m_bookEnd_1 = bookEnd_1;

        m_timeoutMs = timeoutMs;
    }

    public void zeroEncoder() {
        int pulseWidth = m_sensors.getPulseWidthPosition();

        if (m_discontinuityPresent) {
            int newCenter = (m_bookEnd_0 + m_bookEnd_1) / 2;
            newCenter &= 0xFFF;

            pulseWidth -= newCenter;
        }

        pulseWidth &= 0xFFF;

        m_sensors.setQuadraturePosition(pulseWidth, m_timeoutMs);
    }

    public int getSensorPosition() {
        return m_sensors.getPulseWidthPosition() & 0xFFF;
    }

    public double getSensorDegrees() {
        double degrees = getSensorPosition() * 360.0 / 4096.0;

        // Truncate to 0.1 Resolution
        degrees *= 10;
        degrees = (int) degrees;
        degrees /= 10;

        return degrees;
    }
}