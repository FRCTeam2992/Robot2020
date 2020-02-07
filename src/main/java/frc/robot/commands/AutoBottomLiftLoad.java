
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class AutoBottomLiftLoad extends Command {

    private double m_bottomLiftSpeed;

    public AutoBottomLiftLoad(double bottomLiftSpeed) {
        requires(Robot.bottomLift);

        m_bottomLiftSpeed = bottomLiftSpeed;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        this.setInterruptible(true);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        Robot.bottomLift.setBottomLiftSpeed(m_bottomLiftSpeed);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return Robot.bottomLift.liftBallSensor.get();
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.bottomLift.stopBottomLift();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        Robot.bottomLift.stopBottomLift();
    }
}