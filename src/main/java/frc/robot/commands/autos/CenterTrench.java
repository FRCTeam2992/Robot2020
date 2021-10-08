
package frc.robot.commands.autos;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.robot.Robot;
import frc.robot.commands.*;
import frc.robot.commands.groups.*;

public class CenterTrench extends CommandGroup {

  public CenterTrench() {
    addParallel(new SetShooterSpeed(6000));
    addParallel(new StartShooter());
    addSequential(new AutoTurretAim(true, false, 1));
    addParallel(new AutoTurretAim(false, false));
    addParallel(new IntakeDeploy(true));
    addSequential(new ShooterAtSetpoint(3));
    addParallel(new AutoShoot(0.6, 0.8, 0.7, 0.5));
    addSequential(new WaitCommand(1.5));
    //addParallel(new AutoShoot(0.6, 0.8, 1.0, 0.8));
    addParallel(new SetShooterSpeed(6250));
    // addParallel(new IntakeDeploy(true));
    addParallel(new AutoIntakeBall(1, 0.4, 0.6, 1.0, 0.45));
    addSequential(new AutoFollowPath(Robot.driveTrain.CenterTrench, true));
    addParallel(new DriveStraight(0.6));
    addParallel(new AutoTurretAim(false, false));
    addSequential(new WaitCommand(0.8));
    addParallel(new DriveStraight(0.4));
    addSequential(new WaitCommand(0.5));
    addParallel(new DriveStraight(0));
    addSequential(new WaitCommand(2.2));
    addParallel(new AutoShoot(0.6, 0.8, 1.0, 0.8));
    addSequential(new WaitCommand(8.0));
  }
}