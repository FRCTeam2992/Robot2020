
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class AutoSorterLoad extends Command {

    public double m_leftSorterMotorSpeed;
    public double m_rightSorterMotorSpeed;

    public AutoSorterLoad(double leftSorterMotorSpeed, double rightSorterMotorSpeed) {
        requires(Robot.sorter);

        m_leftSorterMotorSpeed = leftSorterMotorSpeed;
        m_rightSorterMotorSpeed = rightSorterMotorSpeed;

    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        this.setInterruptible(true);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        Robot.sorter.setSorterSpeed(m_leftSorterMotorSpeed, m_rightSorterMotorSpeed);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return Robot.sorter.sorterBallSensor.get();
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.sorter.stopSorter();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        Robot.sorter.stopSorter();
    }
}