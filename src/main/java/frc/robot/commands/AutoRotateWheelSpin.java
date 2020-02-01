
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Constants;
import frc.robot.Robot;

public class AutoRotateWheelSpin extends Command {

    private double encoderSetValue;

    public AutoRotateWheelSpin() {
        requires(Robot.colorWheel);
        requires(Robot.intake);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        this.setInterruptible(true);

        Robot.colorWheel.zeroMotorPosition();
        encoderSetValue = (Constants.autoSpinCount * 8) * (Constants.colorWheelEncoderPulses * 4);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        Robot.colorWheel.setColorWheelPostion(encoderSetValue);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        if (Math.abs(encoderSetValue - Robot.colorWheel.getMotorPostion()) <= 50) {
            return true;
        } else {
            return false;
        }
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