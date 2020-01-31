
package frc.lib.drive;

import edu.wpi.first.wpilibj.XboxController;

/**
 * Handles input from Xbox controllers connected to the Driver Station.
 * <p>
 * This class extends the XboxController class. It adds the functionality to
 * smooth joystick and trigger values.
 */
public class mhController extends XboxController {

	/**
	 * @param port the port on the Driver Station that the controller is plugged in
	 *             to.
	 */
	public mhController(int port) {
		super(port);
	}

	/**
	 * @param hand the left or right hand joystick.
	 * @return the deadzoned and smoothed value of the controller's X axis for the
	 *         selected hand.
	 */
	public double smoothGetX(Hand hand) {
		double val = super.getX(hand);

		return smoothPowerCurve(deadzone(val, .05));
	}

	/**
	 * @param hand the left or right hand joystick.
	 * @return the deadzoned and smoothed value of the controller's Y axis for the
	 *         selected hand.
	 */
	public double smoothGetY(Hand hand) {
		double val = super.getY(hand);

		return smoothPowerCurve(deadzone(val, .05));
	}

	/**
	 * @param hand the left or right hand trigger.
	 * @return the smoothed value of the controller's trigger for the selected hand.
	 */
	public double smoothGetTrigger(Hand hand) {
		double val = super.getTriggerAxis(hand);

		return smoothPowerCurve(val);
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