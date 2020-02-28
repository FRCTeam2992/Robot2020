
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ColorWheelMove extends Command {

    private double m_rotateSpeed;

    public ColorWheelMove(double rotateSpeed) {
        requires(Robot.colorWheel);
        requires(Robot.topLift);

        m_rotateSpeed = rotateSpeed;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        this.setInterruptible(true);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        Robot.colorWheel.setColorWheelSpeed(m_rotateSpeed);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.colorWheel.stopColorWheel();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        Robot.colorWheel.stopColorWheel();
    }
}