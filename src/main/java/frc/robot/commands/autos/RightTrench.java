
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

    addParallel(new SetShooterSpeed(5300));
    addParallel(new StartShooter());
    addSequential(new AutoTurretAim(true, false, 3));
    addParallel(new AutoTurretAim(false, false));
    addSequential(new ShooterAtSetpoint(3));
    addParallel(new AutoShoot(0.6, 0.8, 0.55, 0.3));
    addSequential(new WaitCommand(2.0));
    addParallel(new SetShooterSpeed(6150));
    addParallel(new IntakeDeploy(true));
    addParallel(new AutoIntakeBall(1, 0.4, 0.6, 1.0, 0.45));
    addSequential(new AutoFollowPath(Robot.driveTrain.RightTrench, true));
    addParallel(new AutoShoot(0.6, 0.8, 1.0, 0.8));
    addSequential(new WaitCommand(5.0));
  }
}