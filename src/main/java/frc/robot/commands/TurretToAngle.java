
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class TurretToAngle extends Command {

    private double m_angle = 0;

    public TurretToAngle(double angle) {
        requires(Robot.turret);

        m_angle = angle;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        this.setInterruptible(true);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        Robot.turret.goToAngle(m_angle);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return Robot.turret.turretRotate.atSetpoint();
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