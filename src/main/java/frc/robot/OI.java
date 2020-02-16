
package frc.robot;

import frc.lib.drive.mhController;
import frc.lib.drive.mhJoystick;
import frc.lib.drive.mhJoystickButton;
import frc.robot.commands.*;
import frc.robot.commands.groups.AutoShooterShoot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class OI {

    public mhJoystick rightJoystick;
    public mhJoystick leftJoystick;
    public mhController controller;
    public Joystick buttonBox;

    public mhJoystickButton test;

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

        test = new mhJoystickButton(leftJoystick, 5);
        test.setToggle(false);
        test.whenPressed(new LimelightLoadStation(), new AutoShooterShoot());

        SmartDashboard.putData("AutoShooterShoot", new AutoShooterShoot());
        SmartDashboard.putData("LimelightLoadStation", new LimelightLoadStation());
    }

    public void initJoystickBtns() {

    }

    public void initControllerBtns() {

    }

    public void initButtonBoxBtns() {

    }
}