
package frc.robot.commands.autos;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.robot.commands.*;
import frc.robot.commands.groups.*;

public class ShootMoveNoEncoder extends CommandGroup {

  public ShootMoveNoEncoder() {
    addParallel(new SetShooterSpeed(5300));
    addParallel(new StartShooter());
    addSequential(new AutoTurretAim(true, false, 3));
    addSequential(new ShooterAtSetpoint(3));
    addSequential(new WaitCommand(1));
    addParallel(new AutoShoot(0.6, 0.8, 0.3, 0.3));
    addSequential(new WaitCommand(5.0));
    addParallel(new StopShooter());
    addParallel(new StopAutoShoot());
    addSequential(new MoveOffLine());
  }
}