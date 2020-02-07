
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class AutoTurretAim extends Command {

    private int counter = 5;

    private double turretSetAngle = 0;

    public AutoTurretAim() {
        requires(Robot.turret);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        this.setInterruptible(true);

        Robot.vision.setLimelightVisionMode(true);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        counter++;

        if (counter > 8) {
            if (Robot.vision.limelightHasTarget()) {
                turretSetAngle = Robot.turret.getTurretAngle() + Robot.vision.getLimelightXOffset();

                counter = 0;
            }
        }

        Robot.turret.goToAngle(turretSetAngle);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
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