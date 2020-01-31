
package frc.robot;

import com.revrobotics.ColorMatch;

import edu.wpi.first.wpilibj.util.Color;

public class Constants {

    // Drive Constants
    public static final double straightDriveP = 0.015; // Coefficient for Straight Drive Gyro Corrections
    public static final double driveTurnDamp = 0.2; // Drive Turn Speed Dampening

    // Color Wheel Target Colors
    public static Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
    public static Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
    public static Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
    public static Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);

    // Color Wheel
    public static double autoSpinCount = 4; // How many times should we spin the color wheel?

}