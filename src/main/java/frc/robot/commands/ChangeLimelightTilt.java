
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ChangeLimelightTilt extends Command {

    private double mChangeAngle;

    public ChangeLimelightTilt(double changeAngle) {
        mChangeAngle = changeAngle;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        this.setInterruptible(true);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        double servoAngle = Robot.vision.limelightSetAngle + mChangeAngle;

        servoAngle = Math.max(0, Math.min(180, servoAngle));

        Robot.vision.limelightSetAngle = servoAngle;
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