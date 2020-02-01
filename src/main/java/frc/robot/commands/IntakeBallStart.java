
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class IntakeBallStart extends CommandGroup {

    public IntakeBallStart() {
        addParallel(new IntakeFeed(0.5));
        addParallel(new SorterMove(0.5, 0.5));
        addParallel(new BottomLiftMove(0.5));
    }
}