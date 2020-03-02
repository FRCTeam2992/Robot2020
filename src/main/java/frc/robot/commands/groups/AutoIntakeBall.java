
package frc.robot.commands.groups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.robot.commands.*;

public class AutoIntakeBall extends CommandGroup {

    public AutoIntakeBall(double intakeSpeed, double sorterSpeed, double bottomLiftSpeed, double topLiftSpeed) {
        //addParallel(new IntakeDeploy(true));
        //addSequential(new WaitCommand(1));
        addParallel(new IntakeFeed(intakeSpeed));
        addParallel(new AutoSorterLoad(sorterSpeed, sorterSpeed));
        addParallel(new AutoBottomLiftLoad(bottomLiftSpeed));
        addSequential(new AutoTopLiftLoad(topLiftSpeed));
    }

    public AutoIntakeBall(double intakeSpeed, double minSorterSpeed, double maxSorterSpeed, double bottomLiftSpeed, double topLiftSpeed) {
        //addParallel(new IntakeDeploy(true));
        //addSequential(new WaitCommand(1));
        addParallel(new IntakeFeed(intakeSpeed));
        addParallel(new AutoSorterLoad(minSorterSpeed, maxSorterSpeed, minSorterSpeed, maxSorterSpeed));
        addParallel(new AutoBottomLiftLoad(bottomLiftSpeed));
        addSequential(new AutoTopLiftLoad(topLiftSpeed));
    }
}