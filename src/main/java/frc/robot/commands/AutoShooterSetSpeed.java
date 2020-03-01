
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.lib.vision.LimeLight.LedMode;
import frc.robot.Constants;
import frc.robot.Robot;

public class AutoShooterSetSpeed extends Command {

    private boolean mIsFar = false;

    public AutoShooterSetSpeed(boolean isFar) {
        mIsFar = isFar;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Robot.vision.limeLightManager.ledModeRequest(LedMode.On);

        if (mIsFar) {
            Robot.vision.limeLightCamera.setActivePipline(Constants.limelightShooterPipelineFar);
        } else {
            Robot.vision.limeLightCamera.setActivePipline(Constants.limelightShooterPipelineClose);
        }
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        if (Robot.vision.limeLightCamera.hasTarget()) {
            double distanceToTarget = Robot.vision.limeLightCamera.getDistanceToTarget(
                    Constants.cameraAngle + Robot.vision.limelightSetAngle, Constants.cameraHeight,
                    Constants.goalHeight);

            Robot.shooter.shooterSetSpeed = Robot.shooter.shooterSpeedList.getShooterSpeed(distanceToTarget);
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.vision.limeLightManager.ledModeRequest(LedMode.Off);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        Robot.vision.limeLightManager.ledModeRequest(LedMode.Off);
    }
}