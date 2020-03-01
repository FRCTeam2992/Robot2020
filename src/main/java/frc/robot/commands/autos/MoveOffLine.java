
package frc.robot.commands.autos;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.robot.commands.DriveStraight;

public class MoveOffLine extends CommandGroup {

  public MoveOffLine() {
    addParallel(new DriveStraight(0.30));
    addSequential(new WaitCommand(2));
    addParallel(new DriveStraight(0));
  }
}