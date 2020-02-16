
package frc.robot;

import frc.lib.drive.mhController;
import frc.lib.drive.mhJoystick;
import edu.wpi.first.wpilibj.Joystick;

public class OI {

    // Joysticks
    public mhJoystick rightJoystick;
    public mhJoystick leftJoystick;

    // Controllers
    public mhController controller;

    // Button Boxes
    public Joystick buttonBox;
    public Joystick buttonBox2;

    public OI() {
        // Joystick and Controller Init
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

        buttonBox2 = new Joystick(3);
        initButtonBox2Btns();
    }

    public void initJoystickBtns() {

    }

    public void initControllerBtns() {

    }

    public void initButtonBoxBtns() {

    }

    public void initButtonBox2Btns() {

    }
}