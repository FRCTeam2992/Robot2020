
package frc.robot.commands.groups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.*;

public class StopAutoShoot extends CommandGroup {

  public StopAutoShoot() {
    addParallel(new StopSorter());
    addParallel(new StopBottomLift());
    addParallel(new StopTopLiftAndWheel());
  }
}
