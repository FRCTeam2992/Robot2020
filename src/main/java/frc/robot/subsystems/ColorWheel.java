
package frc.robot.subsystems;

import frc.robot.Constants;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.util.Color;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.command.Subsystem;

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
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop

    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void stopColorWheel() {
        colorWheelMotor.set(ControlMode.PercentOutput, 0);
    }

    public void setColorWheelSpeed(double speed) {
        colorWheelMotor.set(ControlMode.PercentOutput, speed);
    }

    public void setColorWheelPostion(double position) {
        colorWheelMotor.set(ControlMode.Position, 0);
    }

    public int getMotorPostion() {
        return colorWheelMotor.getSelectedSensorPosition();
    }

    public void zeroMotorPosition() {
        colorWheelMotor.setSelectedSensorPosition(0);
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