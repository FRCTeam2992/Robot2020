// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.StopClimbSlide;

public class ClimbSlide extends Subsystem {

  // Climb Slide Motors
  private VictorSPX climbSlideMtr;

  public ClimbSlide() {
    // Climb Slide Motors
    climbSlideMtr = new VictorSPX(14);
    climbSlideMtr.setInverted(true);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new StopClimbSlide());
  }

  @Override
  public void periodic() {
    // Put code here to be run every loop
  }

  public void stopClimbSlide() {
    climbSlideMtr.set(ControlMode.PercentOutput, 0);
  }

  public void setClimbSlideSpeed(double speed) {
    climbSlideMtr.set(ControlMode.PercentOutput, speed);
  }
}
