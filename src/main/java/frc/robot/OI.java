// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package frc.robot;

import frc.robot.commands.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.subsystems.*;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);

    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.

    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:

    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());

    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());

    // Start the command when the button is released and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public mhJoystick rightJoystick;
    public mhJoystick leftJoystick;
    public mhController controller;
    public Joystick buttonBox;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    public OI() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

        // Can't Switch Between Joystick and Controller After Init
        if (Robot.isJoystick) {
            leftJoystick = new mhJoystick(1);
            rightJoystick = new mhJoystick(0);
            initJoystick();
        } else {
            controller = new mhController(0);
            initController();
        }

        buttonBox = new Joystick(2);

        // // SmartDashboard Buttons
        // SmartDashboard.putData("Autonomous Command", new AutonomousCommand());
        // SmartDashboard.putData("StartShooterr", new StartShooterr());
        // SmartDashboard.putData("StopShooter", new StopShooter());
        // SmartDashboard.putData("UpShooterSpeed", new UpShooterSpeed());
        // SmartDashboard.putData("DownShooterSpeed", new DownShooterSpeed());
        // SmartDashboard.putData("RotateTurretLeft", new RotateTurretLeft());
        // SmartDashboard.putData("RotateTurretRIght", new RotateTurretRIght());
        // SmartDashboard.putData("AutoTurretAim", new AutoTurretAim());
        // SmartDashboard.putData("RotateTurretStop", new RotateTurretStop());
        // SmartDashboard.putData("StartLift", new StartLift());
        // SmartDashboard.putData("StopLift", new StopLift());
        // SmartDashboard.putData("StartIndexer", new StartIndexer());
        // SmartDashboard.putData("StopIndexer", new StopIndexer());
        // SmartDashboard.putData("IntakeDeploySol", new IntakeDeploySol());
        // SmartDashboard.putData("IntakeFeedStart", new IntakeFeedStart());
        // SmartDashboard.putData("IntakeFeedStop", new IntakeFeedStop());
        // SmartDashboard.putData("AutoRotateWheelColor", new AutoRotateWheelColor());
        // SmartDashboard.putData("AutoRotateWheelSpin", new AutoRotateWheelSpin());
        // SmartDashboard.putData("RotateWheelRight", new RotateWheelRight());
        // SmartDashboard.putData("RotateWheelStop", new RotateWheelStop());
        // SmartDashboard.putData("WheelDeploySol", new WheelDeploySol());
        // SmartDashboard.putData("AutoLimelightTilt", new AutoLimelightTilt());
        // SmartDashboard.putData("StopLimelightTilt", new StopLimelightTilt());

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
    }

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
    public Joystick getRightJoystick() {
        return rightJoystick;
    }

    public Joystick getLeftJoystick() {
        return leftJoystick;
    }

    public Joystick getButtonBox() {
        return buttonBox;
    }

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS

    public void initJoystick() {

    }

    public void initController() {

    }
}
