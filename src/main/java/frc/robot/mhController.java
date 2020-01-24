/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;

public class mhController extends XboxController{

	public mhController(int port) {

		super(port);

	};

/*
	public mhJoystick(int port, int numAxisTypes, int numButtonTypes){
		super(port, numAxisTypes, numButtonTypes);
	}
*/
	

	/*
	 * get x axis with smoothing
	 */
	
	public double smoothGetX(Hand hand){

		double val = super.getX(hand);

		return smoothPowerCurve(deadzone(val, .05));

	}

	

	/*
	 * get y axis with smoothing
	 */

	public double smoothGetY(Hand hand){

		double val = super.getY(hand);

		return smoothPowerCurve(deadzone(val, .05));

	}

	public double smoothGetTrigger(Hand hand){

		double val = super.getTriggerAxis(hand);

		return smoothPowerCurve(val);
	}

	

	

	/* This does the cubic smoothing equation on joystick value.
	 * Assumes you have already done any deadzone processing.
	 * 
	 * @param x  joystick input
	 * @return  smoothed value
	 */

	protected double smoothPowerCurve (double x) {

        

        if (x > 0 || x< 0){

            return (x*x*x);

        }else{

        	return 0;

        }

    }

	

	

	/*
	 * adds deadzone to joysticks
	 * 
	 * @param rawStick Raw value from joystick read -1.0 to 1.0
	 * @param dz	Deadzone value to use 0 to 0.999
     * @return		Value after deadzone processing
	 */

	 protected double deadzone(double rawStick, double dz) {

	        double stick;



	        // Force limit to -1.0 to 1.0

	        if (rawStick > 1.0) {

	            stick = 1.0;

	        } else if (rawStick < -1.0) {

	            stick = -1.0;

	        } else {

	            stick = rawStick;

	        }



	        // Check if value is inside the dead zone

	        if (stick >= 0.0){

	            if (Math.abs(stick) >= dz) 

	                return (stick - dz)/(1 -  dz);

	            else 

	                return 0.0;

	            

	        }

	        else {

	            if (Math.abs(stick) >= dz) 

	                return (stick + dz)/(1 - dz);

	            else 

	                return 0.0;

	            

	        }

	 }

}