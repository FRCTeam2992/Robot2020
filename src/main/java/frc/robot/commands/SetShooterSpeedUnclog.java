
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.Constants;
import frc.robot.Robot;

public class SetShooterSpeedUnclog extends Command {

    private double mShooterSpeed;
    private JoystickButton mStopButton;     // The button that when released we should stop the unclog action
    private JoystickButton mShootButton;    // The main shooter on/off switch

    private boolean wasRunning;
    private double oldShooterSpeed;
    

    public SetShooterSpeedUnclog(double shooterSpeed, JoystickButton stopButton, JoystickButton shooterButton) {
        mShooterSpeed = shooterSpeed;
        mStopButton = stopButton;
        mShootButton = shooterButton;
        requires(Robot.shooter);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        this.setInterruptible(true); 
        oldShooterSpeed = Robot.shooter.getShooterRPM();
        wasRunning = Robot.shooter.getShooterRPM() > 400;

         
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() { 
        if (mStopButton.get() && !wasRunning) {
            // Unclog button is still pressed and either shooter not running or its jammed so try to unclog
            double velocity = (-400 / 600.0) * (Constants.shooterEncoderPulses * 4.0);
            Robot.shooter.setShooterVelocity(velocity);
        } else if (mStopButton.get() && wasRunning) {
            // Either it's already running so keep it going or unclog finished so restart is needed
            double velocity = (Robot.shooter.shooterSetSpeed / 600.0) * (Constants.shooterEncoderPulses * 4.0);
            Robot.shooter.setShooterVelocity(velocity);     // This will let it spin at prior set volocity
        } else {
            // We have released unclog button and shooter switch is off so stop it
            if (mShootButton.get()) {
                double velocity = (Robot.shooter.shooterSetSpeed / 600.0) * (Constants.shooterEncoderPulses * 4.0);
                Robot.shooter.setShooterVelocity(velocity);
            } else {
                Robot.shooter.stopShooter();
            }
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
            Robot.shooter.stopShooter();
        }


    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        Robot.shooter.stopShooter();
    }
}