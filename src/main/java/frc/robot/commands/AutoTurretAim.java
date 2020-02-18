
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.lib.vision.LimeLight.LedMode;
import frc.robot.Constants;
import frc.robot.Robot;

public class AutoTurretAim extends Command {

    private int updateCounter = 8;

    private double turretSetAngle = 0;

    private boolean mFinishWhenAligned = false;

    public AutoTurretAim(boolean finishWhenAligned) {
        requires(Robot.turret);

        mFinishWhenAligned = finishWhenAligned;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        this.setInterruptible(true);

        Robot.vision.limeLightManager.ledModeRequest(LedMode.On);
        Robot.vision.limeLightCamera.setActivePipline(Constants.limelightShooterPipeline);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        updateCounter++;

        if (updateCounter > 8) {
            if (Robot.vision.limeLightCamera.hasTarget()) {
                turretSetAngle = Robot.turret.getTurretAngle() + Robot.vision.limeLightCamera.getTargetXOffset();
            }

            updateCounter = 0;
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