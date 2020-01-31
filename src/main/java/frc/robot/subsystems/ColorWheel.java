// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package frc.robot.subsystems;

import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.commands.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.util.Color;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.command.Subsystem;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

/**
 *
 */
public class ColorWheel extends Subsystem {

    // Color Wheel Motors (Shared with Intake)
    private TalonSRX colorWheelMotor = Robot.intake.intakeTalon;

    // Color Sensor
    private final ColorSensorV3 colorSensor;
    private final I2C.Port sensor = I2C.Port.kOnboard;
    private final ColorMatch colorMatcher = new ColorMatch();

    public ColorWheel() {
        // Color Sensor
        colorSensor = new ColorSensorV3(sensor);

        // Color Sensor Add Colors to Matcher
        colorMatcher.addColorMatch(Constants.kBlueTarget);
        colorMatcher.addColorMatch(Constants.kGreenTarget);
        colorMatcher.addColorMatch(Constants.kRedTarget);
        colorMatcher.addColorMatch(Constants.kYellowTarget);
    }

    @Override
    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop

    }

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void setColorWheelSpeed(double speed) {
        colorWheelMotor.set(ControlMode.PercentOutput, speed);
    }

    public void stopColorWheel() {
        colorWheelMotor.set(ControlMode.PercentOutput, 0);
    }

    public void setColorWheelPostion(double position) {
        colorWheelMotor.set(ControlMode.Position, 0);
    }

    public int getMotorPosition() {
        return colorWheelMotor.getSelectedSensorPosition();
    }

    public String getDetectedColor() {
        Color detectedColor = colorSensor.getColor();

        ColorMatchResult match = colorMatcher.matchClosestColor(detectedColor);

        if (match.color == Constants.kBlueTarget) {
            return "Blue";
        } else if (match.color == Constants.kRedTarget) {
            return "Red";
        } else if (match.color == Constants.kGreenTarget) {
            return "Green";
        } else if (match.color == Constants.kYellowTarget) {
            return "Yellow";
        } else {
            return "Unknown";
        }
    }

}
