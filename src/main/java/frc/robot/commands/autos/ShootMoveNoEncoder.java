
package frc.robot.commands.autos;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.AutoTurretAim;
import frc.robot.commands.StartShooter;

public class ShootMoveNoEncoder extends CommandGroup {

  public ShootMoveNoEncoder() {
    addParallel(new StartShooter());
    addSequential(new AutoTurretAim(true, false, 5));
  }
}
