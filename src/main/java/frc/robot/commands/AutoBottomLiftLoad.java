
package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class AutoBottomLiftLoad extends Command {

    private double m_bottomLiftSpeed;

    private Timer delayTimer;

    private boolean sensorStarted = false;

    public AutoBottomLiftLoad(double bottomLiftSpeed) {
        requires(Robot.bottomLift);

        m_bottomLiftSpeed = bottomLiftSpeed;

        delayTimer = new Timer();
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        this.setInterruptible(true);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        if (Robot.bottomLift.liftBallSensor.get() && Robot.bottomLift.liftBallSensor2.get()) {
            if (sensorStarted) {
                delayTimer.reset();
                delayTimer.start();

                sensorStarted = true;
            }

            Robot.bottomLift.stopBottomLift();
        } else {
            Robot.bottomLift.setBottomLiftSpeed(m_bottomLiftSpeed);
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
        Robot.bottomLift.stopBottomLift();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        Robot.bottomLift.stopBottomLift();
    }
}