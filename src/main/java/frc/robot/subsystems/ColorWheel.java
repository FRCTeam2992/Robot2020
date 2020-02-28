
package frc.robot.subsystems;

import frc.robot.Constants;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.util.Color;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ColorWheel extends Subsystem {

    // Color Wheel Motors (Shared with Top Lift)
    private TalonSRX colorWheelMotor = Robot.topLift.topLiftMotor;

    // Color Sensor Servo
    private Servo colorSensorServo;

    // Color Sensor
    private final ColorSensorV3 colorSensor;
    private final I2C.Port sensor = I2C.Port.kOnboard;
    private final ColorMatch colorMatcher = new ColorMatch();

    public enum TargetColor {
        Blue, Green, Red, Yellow, Unknown, Corrupt
    }

    public ColorWheel() {
        // Color Sensor Servo
        colorSensorServo = new Servo(1);

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
        // Default command set in top lift subsystem
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
        colorWheelMotor.set(ControlMode.Position, position);
    }

    public int getMotorPostion() {
        return colorWheelMotor.getSelectedSensorPosition();
    }

    public void zeroMotorPosition() {
        colorWheelMotor.setSelectedSensorPosition(0);
    }

    public void setSensorServoAngle(double angle) {
        colorSensorServo.setAngle(angle);
    }

    public TargetColor getDetectedColor() {
        Color detectedColor = colorSensor.getColor();

        ColorMatchResult match = colorMatcher.matchClosestColor(detectedColor);

        if (match.color == Constants.kBlueTarget) {
            return TargetColor.Blue;
        } else if (match.color == Constants.kRedTarget) {
            return TargetColor.Red;
        } else if (match.color == Constants.kGreenTarget) {
            return TargetColor.Green;
        } else if (match.color == Constants.kYellowTarget) {
            return TargetColor.Yellow;
        } else {
            return TargetColor.Unknown;
        }
    }

    public TargetColor getFMSColorData() {
        String gameData = DriverStation.getInstance().getGameSpecificMessage();

        if (gameData.length() > 0) {
            switch (gameData.charAt(0)) {
            case 'B':
                return TargetColor.Blue;
            case 'G':
                return TargetColor.Green;
            case 'R':
                return TargetColor.Red;
            case 'Y':
                return TargetColor.Yellow;
            default:
                return TargetColor.Corrupt;
            }
        } else {
            return TargetColor.Unknown;
        }
    }
}