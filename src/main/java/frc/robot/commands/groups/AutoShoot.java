
package frc.robot.commands.groups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.BottomLiftMove;
import frc.robot.commands.SorterFeed;
import frc.robot.commands.TopLiftMove;

public class AutoShoot extends CommandGroup {

  public AutoShoot(double leftSorterSpeed, double rightSorterSpeed, double bottomLiftSpeed, double topLiftSpeed) {
    addParallel(new TopLiftMove(topLiftSpeed));
    addParallel(new BottomLiftMove(bottomLiftSpeed));
    addSequential(new SorterFeed(leftSorterSpeed, rightSorterSpeed));
  }
}