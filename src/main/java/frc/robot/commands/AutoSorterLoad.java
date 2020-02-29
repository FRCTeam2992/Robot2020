
package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Constants;
import frc.robot.Robot;

public class AutoSorterLoad extends Command {

    // Min Sorter Speeds
    private double mMinLeftSorterSpeed = 0;
    private double mMinRightSorterSpeed = 0;

    // Max Sorters Speeds
    private double mMaxLeftSorterSpeed = 0;
    private double mMaxRightSorterSpeed = 0;

    // Change Sorter Speeds Toggle
    private boolean autoSpeedChangeToggle = false;

    // Change Sorter Speeds Timer
    private Timer speedChangeTimer;

    public AutoSorterLoad(double leftSorterSpeed, double rightSorterSpeed) {
        requires(Robot.sorter);

        // Set Min Speeds
        mMinLeftSorterSpeed = leftSorterSpeed;
        mMinRightSorterSpeed = rightSorterSpeed;
    }

    public AutoSorterLoad(double minLeftSorterSpeed, double maxLeftSorterSpeed, double minRightSorterSpeed,
            double maxRightSorterSpeed) {
        requires(Robot.sorter);

        // Set Min Speeds
        mMinLeftSorterSpeed = minLeftSorterSpeed;
        mMinRightSorterSpeed = minRightSorterSpeed;

        // Set Max Speeds
        mMaxLeftSorterSpeed = maxLeftSorterSpeed;
        mMaxRightSorterSpeed = maxRightSorterSpeed;

        speedChangeTimer = new Timer();

        autoSpeedChangeToggle = true;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        this.setInterruptible(true);

        if (autoSpeedChangeToggle) {
            speedChangeTimer.reset();
            speedChangeTimer.start();
        }
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        if (Robot.sorter.sorterBallSensor.get() && Robot.sorter.sorterBallSensor2.get()) {
            Robot.sorter.stopSorter();
        } else {
            double left = 0;
            double right = 0;

            if (autoSpeedChangeToggle) {
                if (speedChangeTimer.get() >= Constants.autoSorterSpeedChangeDelay) {
                    left = mMinLeftSorterSpeed;
                    right = mMaxRightSorterSpeed;

                    if (speedChangeTimer.get() >= (2 * Constants.autoSorterSpeedChangeDelay)) {
                        speedChangeTimer.reset();
                    }
                } else {
                    left = mMaxLeftSorterSpeed;
                    right = mMinRightSorterSpeed;
                }
            } else {
                left = mMinLeftSorterSpeed;
                right = mMinRightSorterSpeed;
            }

            if (Robot.oi.sorterSwitch.get()) {
                Robot.sorter.setSorterSpeed(left, -right);
            } else {
                Robot.sorter.setSorterSpeed(left, right);
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
        Robot.sorter.stopSorter();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        Robot.sorter.stopSorter();
    }
}