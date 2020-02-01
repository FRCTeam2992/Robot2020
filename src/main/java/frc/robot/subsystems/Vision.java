
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.lib.vision.LimeLight;
import frc.lib.vision.LimeLight.*;
import frc.robot.commands.StopLimelightTilt;

public class Vision extends Subsystem {

    private LimeLight limeLightCamera;

    public Vision() {
        limeLightCamera = new LimeLight();
        limeLightCamera.setCameraMode(CameraMode.Driver);
        limeLightCamera.setActivePipline(0);
        limeLightCamera.setTakeSnapshots(false);
    }

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new StopLimelightTilt());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public LimeLight getLimeLight() {
        return limeLightCamera;
    }
}