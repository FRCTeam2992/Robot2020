
package frc.robot.commands.groups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.*;

public class AutoVisionShooter extends CommandGroup {

    public AutoVisionShooter(boolean isFar) {
        addParallel(new AutoLimelightServo(isFar));
        // addParallel(new AutoShooterSetSpeed(isFar));
        addSequential(new AutoTurretAim(false, isFar));
    }
}