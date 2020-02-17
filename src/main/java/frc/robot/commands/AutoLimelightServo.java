
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Constants;
import frc.robot.Robot;

public class AutoLimelightServo extends Command {

    private int updateCounter = 8;

    public AutoLimelightServo() {
        requires(Robot.vision);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        this.setInterruptible(true);

        Robot.vision.limelightSetAngle = Constants.limelightShooterAngle;
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        Robot.vision.setLimelightVisionMode(true);

        updateCounter++;

        if (updateCounter > 8) {
            if (Robot.vision.limeLightCamera.hasTarget()) {
                double limelightYOffset = Robot.vision.limeLightCamera.getTargetYOffset();

                if (Math.abs(limelightYOffset) > 1) {
                    Robot.vision.limelightSetAngle += limelightYOffset;
                }
            }

            updateCounter = 0;
        }

        Robot.vision.setLimelightServoAngle(Robot.vision.limelightSetAngle);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.vision.setLimelightVisionMode(false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        Robot.vision.setLimelightVisionMode(false);
    }
}