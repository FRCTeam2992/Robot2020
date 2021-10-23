
package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Constants;
import frc.robot.Robot;

public class AutoRotateWheelSpin extends Command {

    private double encoderSetValue;

    private double mRotations = 0;

    private double mTimeout = 0;
    private Timer timeoutTimer;

    public AutoRotateWheelSpin(double rotations, double timeout) {
        requires(Robot.colorWheel);
        requires(Robot.topLift);

        mRotations = rotations;
        mTimeout = timeout;
        
        
        timeoutTimer = new Timer();
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        this.setInterruptible(true);

        Robot.colorWheel.zeroMotorPosition();
        encoderSetValue = (mRotations * Constants.colorWheelSpinRatio) * (Constants.colorWheelEncoderPulses * 4);

        timeoutTimer.reset();
        timeoutTimer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        Robot.colorWheel.setColorWheelPostion(encoderSetValue);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return Math.abs(encoderSetValue - Robot.colorWheel.getMotorPostion()) <= 50 || timeoutTimer.get() >= mTimeout;
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