
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
        requires(Robot.topLift);

        mTimeout = timeout;
        timeoutTimer = new Timer();
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        this.setInterruptible(true);
        detectedPosition = getColorID(Robot.colorWheel.getDetectedColor());
        targetPosition = getColorID(Robot.colorWheel.getFMSColorData());
        // System.out.println("Detected = " + detectedPosition);
        // System.out.println("Target = " + targetPosition);

        if (detectedPosition == 0 || targetPosition == 0) {
            isDone = true;
        }

        targetPosition = (targetPosition -1 +2)%4 + 1;
        // System.out.println("Calcultated target color = " + targetPosition);
       // rotations = -targetPosition + detectedPosition + 2;
       // while (rotations > 0) {
       //     rotations -= 4;  // Make sure only spin negative
       // }
       //  System.out.println("Rotations = " + rotations);

    
        
        // Robot.colorWheel.zeroMotorPosition();
        //encoderSetValue = (((rotations - 0.25) / 8.0) * Constants.colorWheelSpinRatio) * (Constants.colorWheelEncoderPulses * 4);
        // System.out.println("encoderSet = " + encoderSetValue);

        timeoutTimer.reset();
        timeoutTimer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        //Robot.colorWheel.setColorWheelPostion(encoderSetValue);
        Robot.colorWheel.setColorWheelSpeed(-0.3 );     // Spin it reverse 75% until we are on right color
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return isDone || getColorID(Robot.colorWheel.getDetectedColor()) == targetPosition
                || timeoutTimer.get() >= mTimeout;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        // System.out.println("Stopped at "+ timeoutTimer.get());
        // Robot.colorWheel.stopColorWheel();
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