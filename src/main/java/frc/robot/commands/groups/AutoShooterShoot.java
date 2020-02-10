
package frc.robot.commands.groups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.*;

public class AutoShooterShoot extends CommandGroup {

    public AutoShooterShoot() {
        addParallel(new AutoLimelightServo());
        addParallel(new AutoShooterSetSpeed());
        addParallel(new AutoTurretAim());
    }
}