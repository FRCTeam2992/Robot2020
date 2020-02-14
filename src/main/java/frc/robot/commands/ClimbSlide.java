
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ClimbSlide extends Command {

    private double m_climbSlideSpeed = 0;

    public ClimbSlide(double climbSlideSpeed) {
        requires(Robot.climb);

        m_climbSlideSpeed = climbSlideSpeed;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        this.setInterruptible(true);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        Robot.climb.setClimbSlideSpeed(m_climbSlideSpeed);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.climb.stopClimbMotors();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        Robot.climb.stopClimbMotors();
    }
}