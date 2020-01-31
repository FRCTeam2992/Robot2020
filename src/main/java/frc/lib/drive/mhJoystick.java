
package frc.lib.drive;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Handles input from joysticks connected to the Driver Station.
 * <p>
 * This class extends the Joystick class. It adds the functionality to smooth
 * and apply a deadzone to the joystick values.
 */
public class mhJoystick extends Joystick {

	/**
	 * @param port the port on the Driver Station that the joystick is plugged in
	 *             to.
	 */
	public mhJoystick(int port) {
		super(port);
	};

	/**
	 * @return the deadzoned and smoothed value of the joystick's X axis.
	 */
	public double smoothGetX() {
		double val = super.getX();

		return smoothPowerCurve(deadzone(val, .1));
	}

	/**
	 * @return the deadzoned and smoothed value of the joystick's Y axis.
	 */
	public double smoothGetY() {
		double val = super.getY();

		return smoothPowerCurve(deadzone(val, .1));
	}

	// This does the cubic smoothing equation on the input value. Assumes you have
	// already done any deadzone processing.
	protected double smoothPowerCurve(double x) {
		if (x > 0 || x < 0) {
			return (x * x * x);
		} else {
			return 0;
		}
	}

	// Applies a Deadzone to the Input
	protected double deadzone(double input, double threshold) {
		// Force Limit from -1.0 to 1.0
		input = Math.max(-1, Math.min(1, input));

		// Check If Value is Inside the Deadzone
		if (input >= 0.0) {
			if (Math.abs(input) >= threshold) {
				return (input - threshold) / (1 - threshold);
			} else {
				return 0.0;
			}
		} else {
			if (Math.abs(input) >= threshold) {
				return (input + threshold) / (1 - threshold);
			} else {
				return 0.0;
			}
		}
	}
}