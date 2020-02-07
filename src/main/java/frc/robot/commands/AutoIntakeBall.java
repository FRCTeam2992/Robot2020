
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoIntakeBall extends CommandGroup {

    public AutoIntakeBall(double intakeSpeed, double sorterSpeed, double bottomLiftSpeed) {
        addParallel(new IntakeDeploySol(true));
        addParallel(new IntakeFeed(intakeSpeed));
        addParallel(new AutoSorterLoad(sorterSpeed, sorterSpeed));
        addParallel(new AutoBottomLiftLoad(bottomLiftSpeed));
    }
}