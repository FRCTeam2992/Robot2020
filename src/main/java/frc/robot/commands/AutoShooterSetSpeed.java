
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Constants;
import frc.robot.Robot;

public class AutoShooterSetSpeed extends Command {

    public AutoShooterSetSpeed() {

    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        double distanceToTarget = Robot.vision.limeLightCamera.getDistanceToTarget(Constants.cameraAngle,
                Constants.cameraHeight, Constants.goalHeight);

        Robot.shooter.setShooterSetSpeed(Robot.shooter.shooterSpeedList.getShooterSpeed(distanceToTarget));
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    }
}