
package frc.robot.commands.groups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.*;

public class AutoVisionLoadStation extends CommandGroup {

    public AutoVisionLoadStation() {
        addSequential(new TurretToAngle(315, 5));
        addParallel(new LimelightLoadStation());
    }
}