
package frc.robot.commands.autos;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.robot.Robot;
import frc.robot.commands.*;
import frc.robot.commands.groups.*;

public class RightTrench extends CommandGroup {

  public RightTrench() {
    addParallel(new SetShooterSpeed(5100));
    addParallel(new StartShooter());
    addSequential(new AutoTurretAim(true, false, 3));
    addSequential(new ShooterAtSetpoint(3));
    addParallel(new AutoShoot(0.6, 0.8, 0.45, 0.45));
    addSequential(new WaitCommand(5.0));
    addParallel(new StopAutoShoot());
    addParallel(new AutoIntakeBall(0.8, 0.6, 0.8, 0.45, 0.45));
    addParallel(new AutoTurretAim(false, true));
    addSequential(new AutoFollowPath(Robot.driveTrain.RightTrench));
    addSequential(new AutoTurretAim(true, true, 3));
    addParallel(new AutoShoot(0.6, 0.8, 0.45, 0.45));
    addSequential(new WaitCommand(5000));
    addParallel(new StopAutoShoot());
  }
}
