
package frc.robot;

import frc.lib.drive.mhController;
import frc.lib.drive.mhJoystick;
import edu.wpi.first.wpilibj.Joystick;

public class OI {

    public mhJoystick rightJoystick;
    public mhJoystick leftJoystick;
    public mhController controller;
    public Joystick buttonBox;

    public OI() {
        // Can't Switch Between Joystick and Controller After Init
        if (Robot.isJoystick) {
            rightJoystick = new mhJoystick(0);
            leftJoystick = new mhJoystick(1);
            initJoystickBtns();
        } else {
            controller = new mhController(0);
            initControllerBtns();
        }

        buttonBox = new Joystick(2);
        initButtonBoxBtns();
    }

    public void initJoystickBtns() {

    }

    public void initControllerBtns() {

    }

    public void initButtonBoxBtns() {

    }
}
