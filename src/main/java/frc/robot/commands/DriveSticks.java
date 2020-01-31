
package frc.robot.commands;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Constants;
import frc.robot.Robot;

public class DriveSticks extends Command {

    boolean straightDrive = false;
    double maxJoystickValue = 0;
    double minJoystickValue = 0;
    double straightDriveDeadzone = 0;
    double straightDriveHeading = 0;

    double left = 0;
    double rightY = 0;
    double rightX = 0;
    double rawLeft = 0;
    double rawRight = 0;

    boolean driveGear = false;
    boolean loadingMode = false;

    public DriveSticks() {
        requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        this.setInterruptible(true);

        Robot.driveTrain.setIdleMode(IdleMode.kCoast);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {

        // Read Joystick and Controller Values
        if (Robot.isJoystick) {
            left = Robot.oi.leftJoystick.smoothGetY();
            rightY = Robot.oi.rightJoystick.smoothGetY();
            rightX = Robot.oi.rightJoystick.smoothGetX();
            rawLeft = Robot.oi.leftJoystick.getY();
            rawRight = Robot.oi.rightJoystick.getY();

            loadingMode = Robot.oi.leftJoystick.getTrigger();
            driveGear = Robot.oi.rightJoystick.getTrigger();
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

            loadingMode = Robot.oi.controller.getBumper(Hand.kRight);
            driveGear = Robot.oi.controller.getBumper(Hand.kLeft);
        }

        if (Robot.isTankDrive) {
            // Turn Dampening for Tank Drive
            double speedDifference = Math.abs(rightY - left);
            rightY /= 1 + Constants.driveTurnDamp * speedDifference;
            left /= 1 + Constants.driveTurnDamp * speedDifference;

            // Straight Drive Assist
            maxJoystickValue = Math.max(Math.abs(rawRight), Math.abs(rawLeft));
            minJoystickValue = Math.min(Math.abs(rawRight), Math.abs(rawLeft));

            // Deadzone for Initiating Straight Drive
            straightDriveDeadzone = maxJoystickValue - (maxJoystickValue * Constants.straightDriveThreshold);

            if (minJoystickValue >= straightDriveDeadzone
                    && ((rawRight > 0 && rawLeft > 0) || (rawRight < 0 && rawLeft < 0))) {
                if (!straightDrive) {
                    // Record the Angle
                    straightDriveHeading = Robot.driveTrain.navx.getYaw();
                    straightDrive = true;
                } else {
                    rightX += Constants.straightDriveP * Robot.driveTrain.calcGyroError(straightDriveHeading);
                    left -= Constants.straightDriveP * Robot.driveTrain.calcGyroError(straightDriveHeading);
                }
            } else {
                straightDrive = false;
            }

            // Scale Motor Powers
            double powerMax = Math.max(1.0, Math.max(Math.abs(rightX), Math.abs(left)));
            rightX /= powerMax;
            left /= powerMax;
        } else {
            // Turn Dampening for Arcade Drive
            rightX /= 1 + Constants.driveTurnDamp;
        }

        // Set the Drive Gear
        Robot.driveTrain.setDriveGear(driveGear);

        // Call Tank Drive and Arcade Drive Functions
        if (loadingMode) {
            if (Robot.isTankDrive) {
                Robot.driveTrain.tankDrive(-rightY, -left);
            } else {
                Robot.driveTrain.arcadeDrive(left, -rightX);
            }
        } else {
            if (Robot.isTankDrive) {
                Robot.driveTrain.tankDrive(left, rightY);
            } else {
                Robot.driveTrain.arcadeDrive(left, -rightX);
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
        Robot.driveTrain.stopDriveTrain();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        Robot.driveTrain.stopDriveTrain();
    }
}