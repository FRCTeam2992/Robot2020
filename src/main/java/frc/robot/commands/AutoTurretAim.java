
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.lib.vision.LimeLight.LedMode;
import frc.robot.Constants;
import frc.robot.Robot;

public class AutoTurretAim extends Command {

    private int updateCounter = 0;

    private double turretSetAngle = Robot.turret.getTurretAngle();

    private boolean mFinishWhenAligned = false;
    private boolean mIsFar = false;

    public AutoTurretAim(boolean finishWhenAligned, boolean isFar) {
        requires(Robot.turret);

        mFinishWhenAligned = finishWhenAligned;
        mIsFar = isFar;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        this.setInterruptible(true);

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
        updateCounter++;

        if (updateCounter > 10) {
            if (Robot.vision.limeLightCamera.hasTarget()) {
                double xOffset = Robot.vision.limeLightCamera.getTargetXOffset();

                if (Math.abs(xOffset) > 1) {
                    turretSetAngle = Robot.turret.getTurretAngle() + xOffset;
                }
            }
        }

        Robot.turret.goToAngle(turretSetAngle);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return mFinishWhenAligned && Robot.vision.limeLightCamera.getTargetXOffset() <= Constants.turretTolerance;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.turret.stopTurret();

        Robot.vision.limeLightManager.ledModeRequest(LedMode.Off);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        Robot.turret.stopTurret();

        Robot.vision.limeLightManager.ledModeRequest(LedMode.Off);
    }
}