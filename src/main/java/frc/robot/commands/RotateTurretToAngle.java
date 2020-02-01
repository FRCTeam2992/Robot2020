
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Constants;
import frc.robot.Robot;

public class RotateTurretToAngle extends Command {

    private double m_angle = 0;

    public RotateTurretToAngle(double angle) {
        requires(Robot.turretRotate);

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
        Robot.turretRotate.goToAngle(m_angle);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return Robot.turretRotate.atTarget();
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.turretRotate.stopTurret();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        Robot.turretRotate.stopTurret();
    }
}