
package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.subsystems.ColorWheel.TargetColor;

public class AutoRotateWheelColor extends Command {

    private boolean isDone = false;

    private int detectedPosition = 0;
    private int targetPosition = 0;

    private int rotations = 0;
    private double encoderSetValue = 0;

    private double mTimeout = 0;
    private Timer timeoutTimer;

    public AutoRotateWheelColor(double timeout) {
        requires(Robot.colorWheel);

        mTimeout = timeout;
        timeoutTimer = new Timer();
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        detectedPosition = getColorID(Robot.colorWheel.getDetectedColor());
        targetPosition = getColorID(Robot.colorWheel.getFMSColorData());

        if (detectedPosition == 0 || targetPosition == 0) {
            isDone = true;
        }

        rotations = targetPosition - detectedPosition;

        if (Math.abs(rotations) > 2) {
            rotations += detectedPosition;
        }

        Robot.colorWheel.zeroMotorPosition();
        encoderSetValue = ((rotations / 8) * Constants.colorWheelSpinRatio) * (Constants.colorWheelEncoderPulses * 4);

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
        return isDone || Math.abs(encoderSetValue - Robot.colorWheel.getMotorPostion()) <= 50
                || timeoutTimer.get() >= mTimeout;
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

    private int getColorID(TargetColor targetColor) {
        switch (targetColor) {
        case Green:
            return 1;
        case Blue:
            return 2;
        case Yellow:
            return 3;
        case Red:
            return 4;
        default:
            return 0;
        }
    }
}