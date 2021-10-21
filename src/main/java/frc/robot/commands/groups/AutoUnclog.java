// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.groups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.BottomLiftMove;
import frc.robot.commands.IntakeFeed;
import frc.robot.commands.SetShooterSpeedUnclog;
import frc.robot.commands.SorterFeed;
import frc.robot.commands.TopLiftMove;

public class AutoUnclog extends CommandGroup {
  /** Add your docs here. */
  public AutoUnclog() {
    
    addParallel(new SorterFeed(-0.75, -0.75));
    addParallel(new BottomLiftMove(-0.9));
    addParallel(new TopLiftMove(-0.4));
    addParallel(new IntakeFeed(-0.75));
    //addParallel(new SetShooterSpeedUnclog(-400));


    // To run multiple commands at the same time,
    // use addParallel()
    // e.g. addParallel(new Command1());
    // addSequential(new Command2());
    // Command1 and Command2 will run in parallel.

    // A command group will require all of the subsystems that each member
    // would require.
    // e.g. if Command1 requires chassis, and Command2 requires arm,
    // a CommandGroup containing them would require both the chassis and the
    // arm.
  }
}
