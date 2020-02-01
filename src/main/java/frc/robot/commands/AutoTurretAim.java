
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.lib.vision.LimeLight.CameraMode;
import frc.lib.vision.LimeLight.LedMode;
import frc.robot.Robot;

public class AutoTurretAim extends Command {

    private int counter = 5;

    private double angle = 0;

    private double lastValue = 0;

    public AutoTurretAim() {
        requires(Robot.turretRotate);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        this.setInterruptible(true);

        Robot.vision.getLimeLight().setLedMode(LedMode.On);
        Robot.vision.getLimeLight().setCameraMode(CameraMode.Vision);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        if (Robot.vision.getLimeLight().hasTarget()) {
            counter++;

            if (counter >= 5) {
                double currentValue = Robot.vision.getLimeLight().getTargetXOffset();

                double turretAngle = Robot.turretRotate.getTurretAngle();

                if (Math.abs(currentValue - lastValue) < 15) {
                    angle = turretAngle + currentValue;
                } else {
                    angle = turretAngle + lastValue;
                }

                lastValue = currentValue;

                counter = 0;
            }

            Robot.turretRotate.goToAngle(angle);
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
        Robot.turretRotate.stopTurret();

        Robot.vision.getLimeLight().setLedMode(LedMode.Off);
        Robot.vision.getLimeLight().setCameraMode(CameraMode.Driver);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        Robot.turretRotate.stopTurret();

        Robot.vision.getLimeLight().setLedMode(LedMode.Off);
        Robot.vision.getLimeLight().setCameraMode(CameraMode.Driver);
    }
}