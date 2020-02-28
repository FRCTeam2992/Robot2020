
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class AutoTopLiftLoad extends Command {

    private double m_topLiftSpeed;

    public AutoTopLiftLoad(double topLiftSpeed) {
        requires(Robot.topLift);

        m_topLiftSpeed = topLiftSpeed;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        this.setInterruptible(true);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        if (Robot.bottomLift.liftBallSensor.get()) {
            Robot.topLift.stopTopLift();
        } else {
            Robot.topLift.setTopLiftSpeed(m_topLiftSpeed);
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.topLift.stopTopLift();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        Robot.topLift.stopTopLift();
    }
}