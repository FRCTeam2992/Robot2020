
package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;

import frc.lib.util.RollingAverage;
import frc.robot.Constants;
import frc.robot.Robot;

public class LimelightLoadStation extends Command {

    private double m_driveCmd = 0.0;
    private double m_turnCmd = 0.0;

    private double left = 0;
    private double rightY = 0;
    private double rightX = 0;

    private RollingAverage rollingAverage;

    public LimelightLoadStation() {
        requires(Robot.driveTrain);

        rollingAverage = new RollingAverage(3);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        this.setInterruptible(true);

        Robot.driveTrain.setDriveGear(false);

        Robot.vision.limelightSetAngle = Constants.limelightLoadStationAngle;
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        Robot.vision.setLimelightVisionMode(true);

        if (Robot.isJoystick) {
            left = Robot.oi.leftJoystick.smoothGetY();
            rightY = Robot.oi.rightJoystick.smoothGetY();
            rightX = Robot.oi.rightJoystick.smoothGetX();

            Robot.isLoadMode = Robot.oi.leftJoystick.getTrigger();
        } else {
            if (Robot.isTriggers) {
                left = Robot.oi.controller.smoothGetTrigger(Hand.kLeft);
                rightY = Robot.oi.controller.smoothGetTrigger(Hand.kRight);
                rightX = Robot.oi.controller.smoothGetX(Hand.kLeft);
            } else {
                left = Robot.oi.controller.smoothGetY(Hand.kLeft);
                rightY = Robot.oi.controller.smoothGetY(Hand.kRight);
                rightX = Robot.oi.controller.smoothGetX(Hand.kRight);
            }

            Robot.isLoadMode = Robot.oi.controller.getBumper(Hand.kRight);
        }

        if (Robot.isLoadMode) {
            if (Robot.vision.limeLightCamera.hasTarget()) {
                updateLimelightTracking();

                Robot.driveTrain.arcadeDrive(m_driveCmd, m_turnCmd);
            } else {
                if (Robot.isTankDrive) {
                    Robot.driveTrain.tankDrive(-rightY, -left);
                } else {
                    Robot.driveTrain.arcadeDrive(-left, rightX);
                }
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
        Robot.vision.setLimelightVisionMode(false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        Robot.vision.setLimelightVisionMode(false);
    }

    public void updateLimelightTracking() {
        // Drive Speed
        m_driveCmd = left;

        // Proportional Steering
        double steerCmd = Robot.vision.limeLightCamera.getTargetXOffset() * Constants.visionSteerP;

        rollingAverage.add(steerCmd);

        m_turnCmd = rollingAverage.getAverage();

        if (m_driveCmd < 0) {
            m_turnCmd *= -1;
        }
    }
}