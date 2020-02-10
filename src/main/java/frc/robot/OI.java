
package frc.robot;

import frc.lib.drive.mhController;
import frc.lib.drive.mhJoystick;
import frc.robot.commands.*;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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

        // SmartDashboard.putData("Color Wheel Auto Spin", new AutoRotateWheelSpin());
        SmartDashboard.putData("Turret Stop", new RotateTurretStop());
        SmartDashboard.putData("Turret 0", new RotateTurretToAngle(0));
        SmartDashboard.putData("Turret 30", new RotateTurretToAngle(30));
        SmartDashboard.putData("Turret 330", new RotateTurretToAngle(330));
        SmartDashboard.putData("Turret 45", new RotateTurretToAngle(45));
        SmartDashboard.putData("Turret 90", new RotateTurretToAngle(90));
        SmartDashboard.putData("Turret 135", new RotateTurretToAngle(135));
        SmartDashboard.putData("Turret 180", new RotateTurretToAngle(180));
        SmartDashboard.putData("Turret 225", new RotateTurretToAngle(225));
        SmartDashboard.putData("Turret 270", new RotateTurretToAngle(270));
        SmartDashboard.putData("Turret 315", new RotateTurretToAngle(315));
        SmartDashboard.putData("Turret 360", new RotateTurretToAngle(360));

        // SmartDashboard.putData("Increase Angle", new ChangeTurretAngle(1));
        // SmartDashboard.putData("Decrease Angle", new ChangeTurretAngle(-1));
        // SmartDashboard.putData("Go To Saved Angle", new
        // RotateTurretToAngle(Robot.turretRotate.angle));

        /*
        SmartDashboard.putData("40% Turret", new RotateTurretMove(.4));
        SmartDashboard.putData("-40%", new RotateTurretMove(-.4));

        SmartDashboard.putData("Turret Rotate with Limelight", new AutoTurretAim());
        */

    }

    public void initJoystickBtns() {

    }

    public void initControllerBtns() {

    }

    public void initButtonBoxBtns() {

    }
}