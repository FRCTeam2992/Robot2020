
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.lib.vision.LimeLight;
import frc.lib.vision.LimeLightManager;
import frc.lib.vision.LimeLight.CameraMode;
import frc.lib.vision.LimeLight.LedMode;
import frc.robot.Constants;
import frc.robot.commands.*;

public class Vision extends Subsystem {

    // LimeLight Camera
    public LimeLight limeLightCamera;

    // LimeLight Manager
    public LimeLightManager limeLightManager;

    // LimeLight Tilt Servo
    private Servo limelightServo;

    // Limelight Set Angle
    public double limelightSetAngle = Constants.limelightShooterAngle;

    public Vision() {
        // LimeLight Camera
        limeLightCamera = new LimeLight();

        // LimeLight Manager
        limeLightManager = new LimeLightManager(limeLightCamera);

        // LimeLight Tilt Servo
        limelightServo = new Servo(0);
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new LimelightServoMove());
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

    public void setLimelightServoAngle(double angle) {
        limelightServo.setAngle(angle);
    }
}