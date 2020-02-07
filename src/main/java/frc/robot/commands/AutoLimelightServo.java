
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class AutoLimelightServo extends Command {

    private int counter = 0;

    public AutoLimelightServo() {
        requires(Robot.vision);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        this.setInterruptible(true);

        Robot.vision.setLimelightVisionMode(true);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        counter++;

        if (counter > 8) {
            if (Robot.vision.limelightHasTarget()) {
                double limelightYOffset = Robot.vision.getLimelightYOffset();

                if (Math.abs(limelightYOffset) > 1) {
                    Robot.vision.setLimelightSetTilt(Robot.vision.getLimelightServoAngle() + limelightYOffset);
                }
            }

            Robot.vision.setLimelightServoAngle(Robot.vision.getLimelightSetTilt());

            counter = 0;
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
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    }
}