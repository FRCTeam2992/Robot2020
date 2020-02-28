
package frc.robot.commands.groups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.*;

public class AutoOverride extends CommandGroup {

  public AutoOverride() {
    addParallel(new IntakeDeploy(false));
    addParallel(new StopIntake());
    addParallel(new StopSorter());
    addParallel(new StopBottomLift());
  }
}