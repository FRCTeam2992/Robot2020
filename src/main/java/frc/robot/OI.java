
package frc.robot;

import frc.lib.drive.mhController;
import frc.lib.drive.mhJoystick;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.POVButton;
import frc.robot.commands.groups.*;
import frc.robot.commands.*;

public class OI {

    // Joysticks
    public mhJoystick rightJoystick;
    public mhJoystick leftJoystick;

    // Controllers
    public mhController controller;

    // Button Boxes
    private Joystick buttonBox;
    private Joystick buttonBox2;

    // Left Joystick Buttons
    private POVButton joystickShooterSpeedUp;
    private POVButton joystickShooterSpeedDown;
    private POVButton joystickTurretManualLeft;
    private POVButton joystickTurretManualRight;
    private JoystickButton joystickAutoShoot;

    // Right Joystick Buttons
    public JoystickButton autoLimelightClose;
    public JoystickButton autoLimelightFar;
    private POVButton limelightTiltUp;
    private POVButton limelightTiltDown;

    // Controller Buttons
    private JoystickButton controllerShooterSpeedUp;
    private JoystickButton controllerShooterSpeedDown;
    private JoystickButton controllerTurretManualLeft;
    private JoystickButton controllerTurretManualRight;
    private POVButton controllerAutoShoot;

    // Button Box 1 Buttons
    private JoystickButton sorterManualForward;
    private JoystickButton sorterManualReverse;
    public JoystickButton sorterSwitch;
    private JoystickButton intakeDeploy;
    private JoystickButton intakeManualForward;
    private JoystickButton intakeManualReverse;
    private JoystickButton autoColorWheelSpin;
    private JoystickButton autoColorWheelColor;
    private JoystickButton colorWheelManualLeft;
    private JoystickButton colorWheelManualRight;
    private JoystickButton climbLiftUp;
    private JoystickButton climbLiftDown;
    private JoystickButton climbSlideLeft;
    private JoystickButton climbSlideRight;
    public JoystickButton climbOverride;
    public JoystickButton joystickSelector1;
    public JoystickButton joystickSelector2;
    public JoystickButton autoSwitch1;

    // Button Box 2 Buttons
    private JoystickButton topLiftManualUp;
    private JoystickButton topLiftManualDown;
    private JoystickButton bottomLiftManualUp;
    private JoystickButton bottomLiftManualDown;
    private JoystickButton autoIntakePowerCell;
    private JoystickButton autoOverride;
    private JoystickButton shooterToggle;
    private JoystickButton turretManualLeft;
    private JoystickButton turretManualRight;
    private JoystickButton colorSensorDeploy;
    public JoystickButton autoSwitch2;
    public JoystickButton autoSwitch3;

    public OI() {
        // Joystick and Controller Init
        if (Robot.isJoystick) {
            rightJoystick = new mhJoystick(0);
            leftJoystick = new mhJoystick(1);
            initJoystickBtns();
        } else {
            controller = new mhController(0);
            initControllerBtns();
        }

        buttonBox = new Joystick(2);
        initButtonBoxBtns();

        buttonBox2 = new Joystick(3);
        initButtonBox2Btns();
    }

    public void initJoystickBtns() {
        // Left Joystick
        joystickShooterSpeedUp = new POVButton(leftJoystick, 0);
        joystickShooterSpeedUp.whenPressed(new ChangeShooterSpeed(100));

        joystickShooterSpeedDown = new POVButton(leftJoystick, 180);
        joystickShooterSpeedDown.whenPressed(new ChangeShooterSpeed(-100));

        joystickTurretManualLeft = new POVButton(leftJoystick, 270);
        joystickTurretManualLeft.whenPressed(new TurretMove(-0.4));
        joystickTurretManualLeft.whenReleased(new StopTurret());

        joystickTurretManualRight = new POVButton(leftJoystick, 90);
        joystickTurretManualRight.whenPressed(new TurretMove(0.4));
        joystickTurretManualRight.whenReleased(new StopTurret());

        joystickAutoShoot = new JoystickButton(leftJoystick, 4);
        joystickAutoShoot.whileHeld(new AutoShoot(0.4, 0.6, 1.0, 0.5));
        joystickAutoShoot.whenReleased(new StopAutoShoot());

        // Right Joystick
        autoLimelightClose = new JoystickButton(rightJoystick, 3);
        autoLimelightClose.whenPressed(new VisionProcessing(false, false));
        autoLimelightClose.whenReleased(new VisionProcessing(true, false));

        autoLimelightFar = new JoystickButton(rightJoystick, 5);
        autoLimelightFar.whenPressed(new VisionProcessing(false, true));
        autoLimelightFar.whenReleased(new VisionProcessing(true, true));

        limelightTiltUp = new POVButton(rightJoystick, 0);
        limelightTiltUp.whenPressed(new ChangeLimelightTilt(10));

        limelightTiltDown = new POVButton(rightJoystick, 180);
        limelightTiltDown.whenPressed(new ChangeLimelightTilt(-10));
    }

    public void initControllerBtns() {
        controllerShooterSpeedUp = new JoystickButton(controller, 4);
        controllerShooterSpeedUp.whenPressed(new ChangeShooterSpeed(100));

        controllerShooterSpeedDown = new JoystickButton(controller, 1);
        controllerShooterSpeedDown.whenPressed(new ChangeShooterSpeed(-100));

        controllerTurretManualLeft = new JoystickButton(controller, 3);
        controllerTurretManualLeft.whenPressed(new TurretMove(-0.4));
        controllerTurretManualLeft.whenReleased(new StopTurret());

        controllerTurretManualRight = new JoystickButton(controller, 2);
        controllerTurretManualRight.whenPressed(new TurretMove(0.4));
        controllerTurretManualRight.whenReleased(new StopTurret());

        autoLimelightClose = new JoystickButton(controller, 10);
        autoLimelightClose.whenPressed(new VisionProcessing(false, false));
        autoLimelightClose.whenReleased(new VisionProcessing(true, false));

        autoLimelightFar = new JoystickButton(controller, 9);
        autoLimelightFar.whenPressed(new VisionProcessing(false, true));
        autoLimelightFar.whenReleased(new VisionProcessing(true, true));

        controllerAutoShoot = new POVButton(controller, 90);
        controllerAutoShoot.whileHeld(new AutoShoot(0.4, 0.6, 0.8, 0.35));
        controllerAutoShoot.whenReleased(new StopAutoShoot());

        limelightTiltUp = new POVButton(controller, 0);
        limelightTiltUp.whenPressed(new ChangeLimelightTilt(10));

        limelightTiltDown = new POVButton(controller, 180);
        limelightTiltDown.whenPressed(new ChangeLimelightTilt(-10));
    }

    public void initButtonBoxBtns() {
        sorterManualForward = new JoystickButton(buttonBox, 1);
        sorterManualForward.whenPressed(new SorterFeed(0.6, 0.8));
        sorterManualForward.whenReleased(new StopSorter());

        sorterManualReverse = new JoystickButton(buttonBox, 2);
        sorterManualReverse.whenPressed(new SorterFeed(-0.6, -0.8));
        sorterManualReverse.whenReleased(new StopSorter());

        sorterSwitch = new JoystickButton(buttonBox, 3);

        intakeDeploy = new JoystickButton(buttonBox, 4);
        intakeDeploy.whenPressed(new IntakeDeploy(true));
        intakeDeploy.whenReleased(new IntakeDeploy(false));

        intakeManualForward = new JoystickButton(buttonBox, 5);
        intakeManualForward.whenPressed(new IntakeFeed(1));
        intakeManualForward.whenReleased(new StopIntake());

        intakeManualReverse = new JoystickButton(buttonBox, 6);
        intakeManualReverse.whenPressed(new IntakeFeed(-1));
        intakeManualReverse.whenReleased(new StopIntake());

        autoColorWheelSpin = new JoystickButton(buttonBox, 7);
        autoColorWheelSpin.whenPressed(new AutoRotateWheelSpin(Constants.colorWheelSpinRotations, 5));

        autoColorWheelColor = new JoystickButton(buttonBox, 8);
        autoColorWheelColor.whenPressed(new AutoRotateWheelColor(5));

        colorWheelManualLeft = new JoystickButton(buttonBox, 9);
        colorWheelManualLeft.whenPressed(new ColorWheelMove(-0.65));
        colorWheelManualLeft.whenReleased(new StopIntake());

        colorWheelManualRight = new JoystickButton(buttonBox, 10);
        colorWheelManualRight.whenPressed(new ColorWheelMove(0.65));
        colorWheelManualRight.whenReleased(new StopIntake());

        climbLiftUp = new JoystickButton(buttonBox, 11);
        climbLiftUp.whenPressed(new ClimbLift(0.5));
        climbLiftUp.whenPressed(new StopClimb());
        // climbLiftUp.whenReleased(new StopClimbUp(0.8, 0.25));

        climbLiftDown = new JoystickButton(buttonBox, 12);
        climbLiftDown.whenPressed(new ClimbLift(-0.8));
        climbLiftDown.whenReleased(new StopClimb());

        climbSlideLeft = new JoystickButton(buttonBox, 13);
        climbSlideLeft.whenPressed(new ClimbSlide(-1));
        climbSlideLeft.whenReleased(new StopClimb());

        climbSlideRight = new JoystickButton(buttonBox, 14);
        climbSlideRight.whenPressed(new ClimbSlide(1));
        climbSlideRight.whenReleased(new StopClimb());

        autoSwitch2 = new JoystickButton(buttonBox, 18);

        autoSwitch3 = new JoystickButton(buttonBox, 17);
    }

    public void initButtonBox2Btns() {
        topLiftManualUp = new JoystickButton(buttonBox2, 1);
        topLiftManualUp.whenPressed(new TopLiftMove(0.45));
        topLiftManualUp.whenReleased(new StopTopLiftAndWheel());

        topLiftManualDown = new JoystickButton(buttonBox2, 2);
        topLiftManualDown.whenPressed(new TopLiftMove(-0.45));
        topLiftManualDown.whenReleased(new StopTopLiftAndWheel());

        bottomLiftManualUp = new JoystickButton(buttonBox2, 3);
        bottomLiftManualUp.whenPressed(new BottomLiftMove(0.8)); // was .45
        bottomLiftManualUp.whenReleased(new StopBottomLift());

        bottomLiftManualDown = new JoystickButton(buttonBox2, 4);
        bottomLiftManualDown.whenPressed(new BottomLiftMove(-0.45));
        bottomLiftManualDown.whenReleased(new StopBottomLift());

        autoIntakePowerCell = new JoystickButton(buttonBox2, 5);
        autoIntakePowerCell.whenPressed(new AutoIntakeBall(1, 0.4, 0.6, 1.0, 0.45));

        autoOverride = new JoystickButton(buttonBox2, 6);
        autoOverride.whenPressed(new AutoOverride());

        shooterToggle = new JoystickButton(buttonBox2, 7);
        shooterToggle.whenPressed(new StartShooter());
        shooterToggle.whenReleased(new StopShooter());

        turretManualLeft = new JoystickButton(buttonBox2, 8);
        turretManualLeft.whenPressed(new TurretMove(-1));
        turretManualLeft.whenReleased(new StopTurret());

        turretManualRight = new JoystickButton(buttonBox2, 9);
        turretManualRight.whenPressed(new TurretMove(1));
        turretManualRight.whenReleased(new StopTurret());

        colorSensorDeploy = new JoystickButton(buttonBox2, 10);
        colorSensorDeploy.whenPressed(new ColorSensorDeploy(true));
        colorSensorDeploy.whenReleased(new ColorSensorDeploy(false));

        climbOverride = new JoystickButton(buttonBox2, 15);
        climbOverride.whenPressed(new ClimbLock(false));
        climbOverride.whenReleased(new ClimbLock(true));

        joystickSelector1 = new JoystickButton(buttonBox2, 16);

        joystickSelector2 = new JoystickButton(buttonBox2, 17);

        autoSwitch1 = new JoystickButton(buttonBox2, 18);
    }
}