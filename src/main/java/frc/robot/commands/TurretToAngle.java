
package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class TurretToAngle extends Command {

    private double mAngle = 0;

    private double mTimeout = 0;
    private Timer timeoutTimer;

    public TurretToAngle(double angle, double timeout) {
        requires(Robot.turret);

        mAngle = angle;
        
        mTimeout = timeout;
        timeoutTimer = new Timer();
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        this.setInterruptible(true);

        timeoutTimer.reset();
        timeoutTimer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        Robot.turret.goToAngle(mAngle);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return Robot.turret.turretRotate.atSetpoint() || timeoutTimer.get() >= mTimeout;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.turret.stopTurret();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        Robot.turret.stopTurret();
    }
}