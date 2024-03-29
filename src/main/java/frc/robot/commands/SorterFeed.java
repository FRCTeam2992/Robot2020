
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class SorterFeed extends Command {

    double m_leftSideSpeed;
    double m_rightSideSpeed;

    public SorterFeed(double leftSideSpeed, double rightSideSpeed) {
        requires(Robot.sorter);

        m_leftSideSpeed = leftSideSpeed;
        m_rightSideSpeed = rightSideSpeed;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        this.setInterruptible(true);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        // if (Robot.oi.sorterSwitch.get()) {
        //     Robot.sorter.setSorterSpeed(m_leftSideSpeed, -m_rightSideSpeed);
        // } else {
            Robot.sorter.setSorterSpeed(m_leftSideSpeed, m_rightSideSpeed);
        
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
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