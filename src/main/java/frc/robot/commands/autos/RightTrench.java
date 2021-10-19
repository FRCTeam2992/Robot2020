
package frc.robot.commands.autos;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.robot.Robot;
import frc.robot.commands.*;
import frc.robot.commands.groups.*;

public class RightTrench extends CommandGroup {

  public RightTrench() {
    // addParallel(new SetShooterSpeed(5300));
    // addParallel(new StartShooter());
    // addSequential(new AutoTurretAim(true, false, 3));
    // addParallel(new AutoTurretAim(false, false));
    // addSequential(new ShooterAtSetpoint(3));
    // addParallel(new AutoShoot(0.6, 0.8, 0.55, 0.3));
    // addSequential(new WaitCommand(2.0));
    // addParallel(new AutoShoot(0.6, 0.8, 1.0, 0.8));
    // addParallel(new SetShooterSpeed(5950));
    // addParallel(new IntakeDeploy(true));
    // addParallel(new IntakeFeed(1));
    // addSequential(new AutoFollowPath(Robot.driveTrain.RightTrench, true));

    addParallel(new SetShooterSpeed(6000));
    addParallel(new StartShooter());
    addSequential(new AutoTurretAim(true, false, 1));
    addParallel(new AutoTurretAim(false, false));
    addParallel(new IntakeDeploy(true));
    addSequential(new ShooterAtSetpoint(3));
    addParallel(new AutoShoot(0.6, 0.8, 0.7, 0.5));
    addSequential(new WaitCommand(1.5));
    addParallel(new SetShooterSpeed(6250));
    // addParallel(new IntakeDeploy(true));
    addParallel(new AutoIntakeBall(1, 0.4, 0.6, 1.0, 0.45));
    addSequential(new AutoFollowPath(Robot.driveTrain.RightTrench, true));
    addParallel(new DriveStraight(0.6));
    addParallel(new AutoTurretAim(false, false));
    addSequential(new WaitCommand(0.8));
    addParallel(new DriveStraight(0.4));
    addSequential(new WaitCommand(0.5));
    addParallel(new DriveStraight(0));
    addSequential(new WaitCommand(3.0));
    addParallel(new AutoShoot(0.6, 0.8, 1.0, 0.8));
    addSequential(new WaitCommand(8.0));
  }
}