
package frc.robot.commands.groups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.*;

public class AutoIntakeBall extends CommandGroup {

    public AutoIntakeBall(double intakeSpeed, double sorterSpeed, double bottomLiftSpeed, double topLiftSpeed) {
        addParallel(new IntakeFeed(intakeSpeed));
        addParallel(new IntakeDeploy(true));
        addParallel(new AutoSorterLoad(sorterSpeed, sorterSpeed));
        addParallel(new AutoBottomLiftLoad(bottomLiftSpeed));
        addSequential(new AutoTopLiftLoad(topLiftSpeed));
    }

    public AutoIntakeBall(double intakeSpeed, double minSorterSpeed, double maxSorterSpeed, double bottomLiftSpeed,
            double topLiftSpeed) {
        addParallel(new IntakeFeed(intakeSpeed));
        addParallel(new IntakeDeploy(true));
        addParallel(new AutoSorterLoad(minSorterSpeed, maxSorterSpeed, minSorterSpeed, maxSorterSpeed));
        addParallel(new AutoBottomLiftLoad(bottomLiftSpeed));
        addSequential(new AutoTopLiftLoad(topLiftSpeed));
    }
}