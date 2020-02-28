
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ChangeShooterSpeed extends Command {

    private int mChangeSpeed;

    public ChangeShooterSpeed(int changeSpeed) {
        mChangeSpeed = changeSpeed;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        int shooterSpeed = Robot.shooter.shooterSetSpeed + mChangeSpeed;

        shooterSpeed = Math.max(0, shooterSpeed);

        Robot.shooter.shooterSetSpeed = shooterSpeed;
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