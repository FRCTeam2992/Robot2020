
package frc.robot.commands.autos;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.robot.Robot;
import frc.robot.commands.*;
import frc.robot.commands.groups.*;

public class RightTrench extends CommandGroup {

  public RightTrench() {
    addParallel(new SetShooterSpeed(5150));
    addParallel(new StartShooter());
    addSequential(new AutoTurretAim(true, false, 3));
    addParallel(new AutoTurretAim(false, false));
    addSequential(new ShooterAtSetpoint(3));
    addParallel(new AutoShoot(0.6, 0.8, 0.60, 0.45));   // changed from 0.35 and 0.35 lift
    addSequential(new WaitCommand(2.0));
    addParallel(new AutoShoot(0.6, 0.8, 0.8, 0.8));
    addParallel(new SetShooterSpeed(5500));
    addParallel(new IntakeDeploy(true));
    addParallel(new IntakeFeed(0.8));
    addSequential(new AutoFollowPath(Robot.driveTrain.RightTrench, true));
  }
}
