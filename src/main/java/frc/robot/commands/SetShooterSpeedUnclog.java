
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.Constants;
import frc.robot.Robot;

public class SetShooterSpeedUnclog extends Command {

    private double mShooterSpeed;
    private JoystickButton mButton;

    private boolean wasRunning;

    public SetShooterSpeedUnclog(int shooterSpeed, JoystickButton stopButton) {
        mShooterSpeed = shooterSpeed;
        mButton = stopButton;
        requires(Robot.shooter);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        this.setInterruptible(true);

        // Keep track of prior state before we try to unclog shooter
        wasRunning = Robot.shooter.isRunning();    
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() { 
        if (mButton.get() && Robot.shooter.getShooterRPM() < 400) {
            // Unclog button is still pressed and either shooter not running or its jammed so try to unclog
            double velocity = (mShooterSpeed / 600.0) * (Constants.shooterEncoderPulses * 4.0);
            Robot.shooter.setShooterVelocity(velocity);
        } else if (wasRunning) {
            // It was already running and its spinning forward  or we have released unclog button so let it spin!
            double velocity = (Robot.shooter.shooterSetSpeed / 600.0) * (Constants.shooterEncoderPulses * 4.0);
            Robot.shooter.setShooterVelocity(velocity);     // This will let it spin at prior set volocity
        } else {
            // We have released button and it wasn't running before, so stop it
            Robot.shooter.stopShooter();
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