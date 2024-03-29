
package frc.robot;

import edu.wpi.first.hal.FRCNetComm.tInstances;
import edu.wpi.first.hal.FRCNetComm.tResourceType;
import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSource.ConnectionStrategy;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.autos.*;
import frc.robot.subsystems.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {

    // There are 4496 lines of code!!!!!

    public static OI oi;
    public static DriveTrain driveTrain;
    public static Shooter shooter;
    public static Turret turret;
    public static TopLift topLift;
    public static BottomLift bottomLift;
    public static Sorter sorter;
    public static Climb climb;
    public static Intake intake;
    public static ColorWheel colorWheel;
    public static Vision vision;
    public static ClimbSlide climbSlide;

    // Autonomous Command and Selector
    private Command autonomousCommand;

    // Set Drive Controller Modes
    public static boolean isJoystick = true;
    public static boolean isTankDrive = false;
    public static boolean isTriggers = false;

    // Robot Variables
    public static boolean isLoadMode = false;

    // Drive Cameras
    private static MjpegServer virtualCamera;
    private static UsbCamera shooterCamera;
    private static UsbCamera loadCamera;

    /**
     * This function is run when the robot is first started up and should be used
     * for any initialization code.
     */
    @Override
    public void robotInit() {

        driveTrain = new DriveTrain();
        shooter = new Shooter();
        turret = new Turret();
        topLift = new TopLift();
        bottomLift = new BottomLift();
        sorter = new Sorter();
        climb = new Climb();
        intake = new Intake();
        colorWheel = new ColorWheel();
        vision = new Vision();
        climbSlide = new ClimbSlide();
        
        // OI must be constructed after subsystems. If the OI creates Commands
        // (which it very likely will), subsystems are not guaranteed to be
        // constructed yet. Thus, their requires() statements may grab null
        // pointers. Bad news. Don't move it.
        oi = new OI();

        HAL.report(tResourceType.kResourceType_Framework, tInstances.kFramework_RobotBuilder);

        // Initialize Drive Cameras
        shooterCamera = CameraServer.getInstance().startAutomaticCapture("Shooter Camera", 0);
        shooterCamera.setFPS(30);
        shooterCamera.setResolution(160,90);
        shooterCamera.setConnectionStrategy(ConnectionStrategy.kAutoManage);

        loadCamera = CameraServer.getInstance().startAutomaticCapture("Load Camera", 1);
        loadCamera.setFPS(30);
        loadCamera.setResolution(160,90);
        loadCamera.setConnectionStrategy(ConnectionStrategy.kAutoManage);

        virtualCamera = CameraServer.getInstance().addSwitchedCamera("Drive Cameras");

    }

    /**
     * This function is called when the disabled button is hit. You can use it to
     * reset subsystems before shutting down.
     */
    @Override
    public void disabledInit() {

    }

    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();

        if (oi.autoLimelightClose.get() || oi.autoLimelightFar.get()) {
            Robot.vision.setLimelightVisionMode(true);
        } else {
            Robot.vision.setLimelightVisionMode(false);
        }

        updateDriveMode();

        updateDriveCameras();

        setAutonomousCommand();
    }

    @Override
    public void autonomousInit() {
        // Reset Drive Sensors
        Robot.driveTrain.navx.zeroYaw();
        Robot.driveTrain.resetOdometry();

        // Set Autonomous Command
        setAutonomousCommand();

        // Start Autonomous Command
        if (autonomousCommand != null) {
            autonomousCommand.start();
        }
    }

    /**
     * This function is called periodically during autonomous
     */
    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {
        // Stop Autonomous Command
        if (autonomousCommand != null) {
            autonomousCommand.cancel();
        }

        // Reset Drive Sensors
        Robot.driveTrain.navx.zeroYaw();
        Robot.driveTrain.resetOdometry();

        // Reset Shooter Speed
        Robot.shooter.shooterSetSpeed = Constants.deafaultShooterSpeed;
    }

    /**
     * This function is called periodically during operator control
     */
    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();

        updateDriveMode();

        updateDriveCameras();
    }

    private void updateDriveMode() {
        if (isJoystick) {
            if (oi.leftJoystick.getTrigger()) {
                isLoadMode = true;
            } else {
                isLoadMode = false;
            }
        } else {
            if (oi.controller.getBumper(Hand.kLeft)) {
                isLoadMode = true;
            } else {
                isLoadMode = false;
            }
        }
    }

    private void updateDriveCameras() {
        if (isLoadMode) {
            virtualCamera.setSource(loadCamera);
        } else {
            virtualCamera.setSource(shooterCamera);
        }
    }

    public void setAutonomousCommand() {
        int autoNumber = 0;

        if (oi.autoSwitch1.get()) {
            autoNumber++;
        }

        if (oi.autoSwitch2.get()) {
            autoNumber += 2;
        }

        if (oi.autoSwitch3.get()) {
            autoNumber += 4;
        }

        switch(autoNumber) {
            case 0:
            autonomousCommand = null;
            break;
            case 1:
            autonomousCommand = new ShootMoveNoEncoder();
            break;
            case 2:
            autonomousCommand = new MoveOffLine();
            break;
            case 3:
            autonomousCommand = new RightTrench();
            break;
            case 4:
            autonomousCommand = new CenterTrench();
            break;
            default:
            autonomousCommand = null;
        }

        if(autonomousCommand != null) {
            SmartDashboard.putString("Auto Name", autonomousCommand.getName());
        } else {
            SmartDashboard.putString("Auto Name", "DoNothing");
        }
    }
}