
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.lib.vision.LimeLight;
import frc.lib.vision.LimeLight.CameraMode;
import frc.lib.vision.LimeLight.LedMode;
import frc.robot.commands.*;

public class Vision extends Subsystem {

    // LimeLight Camera
    private LimeLight limeLightCamera;

    // LimeLight Tilt Servo
    private Servo limelightServo;

    private double limelightTilt = 20;

    public Vision() {
        // LimeLight Camera
        limeLightCamera = new LimeLight();

        // LimeLight Tilt Servo
        limelightServo = new Servo(0);
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new StopLimelightServo());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void setLimelightVisionMode(boolean toggle) {
        if (toggle) {
            limeLightCamera.setLedMode(LedMode.On);
            limeLightCamera.setCameraMode(CameraMode.Vision);
        } else {
            limeLightCamera.setLedMode(LedMode.Off);
            limeLightCamera.setCameraMode(CameraMode.Driver);
        }
    }

    public double getLimelightXOffset() {
        return limeLightCamera.getTargetXOffset();
    }

    public double getLimelightYOffset() {
        return limeLightCamera.getTargetYOffset();
    }

    public boolean limelightHasTarget() {
        return limeLightCamera.hasTarget();
    }

    public void stopLimelightServo() {
        limelightServo.setDisabled();
    }

    public double getLimelightSetTilt() {
        return limelightTilt;
    }

    public void setLimelightSetTilt(double angle) {
        limelightTilt = angle;
    }

    public double getLimelightServoAngle() {
        return limelightServo.getAngle();
    }

    public void setLimelightServoAngle(double angle) {
        limelightServo.setAngle(angle);
    }
}