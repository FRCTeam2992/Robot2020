
package frc.robot;

import frc.lib.drive.mhController;
import frc.lib.drive.mhJoystick;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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

    // Button Box 1 Buttons
    private JoystickButton sorterManualForward;
    private JoystickButton sorterManualReverse;
    public JoystickButton sorterSwitch;
    private JoystickButton intakeDeploy;
    private JoystickButton intakeManualForward;
    private JoystickButton intakeManualReverse;
    private JoystickButton autoColorWheelColor;
    private JoystickButton autoColorWheelSpin;
    private JoystickButton climbLiftUp;
    private JoystickButton climbLiftDown;
    private JoystickButton climbSlideLeft;
    private JoystickButton climbSlideRight;

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

    }

    public void initControllerBtns() {

    }

    public void initButtonBoxBtns() {
        sorterManualForward = new JoystickButton(buttonBox, 1);
        sorterManualForward.whenPressed(new SorterFeed(0.3, 0.3));
        sorterManualForward.whenReleased(new StopSorter());

        sorterManualReverse = new JoystickButton(buttonBox, 2);
        sorterManualReverse.whenPressed(new SorterFeed(-0.3, -0.3));
        sorterManualReverse.whenReleased(new StopSorter());

        intakeDeploy = new JoystickButton(buttonBox, 4);
        intakeDeploy.whenPressed(new IntakeDeploy(true));
        intakeDeploy.whenReleased(new IntakeDeploy(false));

        intakeManualForward = new JoystickButton(buttonBox, 5);
        intakeManualForward.whenPressed(new IntakeFeed(0.3));
        intakeManualForward.whenReleased(new StopIntakeAndWheel());

        intakeManualReverse = new JoystickButton(buttonBox, 6);
        intakeManualReverse.whenPressed(new IntakeFeed(-0.3));
        intakeManualReverse.whenReleased(new StopIntakeAndWheel());
    }

    public void initButtonBox2Btns() {
        topLiftManualUp = new JoystickButton(buttonBox2, 1);
        topLiftManualUp.whenPressed(new TopLiftMove(0.7));
        topLiftManualUp.whenReleased(new StopTopLift());

        topLiftManualDown = new JoystickButton(buttonBox2, 2);
        topLiftManualDown.whenPressed(new TopLiftMove(-0.7));
        topLiftManualDown.whenReleased(new StopTopLift());

        bottomLiftManualUp = new JoystickButton(buttonBox2, 3);
        bottomLiftManualUp.whenPressed(new BottomLiftMove(0.7));
        bottomLiftManualUp.whenReleased(new StopBottomLift());

        bottomLiftManualDown = new JoystickButton(buttonBox2, 4);
        bottomLiftManualDown.whenPressed(new BottomLiftMove(-0.7));
        bottomLiftManualDown.whenReleased(new StopBottomLift());

        autoIntakePowerCell = new JoystickButton(buttonBox2, 5);
        autoIntakePowerCell.whenPressed(new AutoIntakeBall(0.3, .30, .70));

        autoOverride = new JoystickButton(buttonBox2, 6);
        autoOverride.whenPressed(new AutoOverride());

        shooterToggle = new JoystickButton(buttonBox2, 7);
        shooterToggle.whenPressed(new StartShooter());
        shooterToggle.whenReleased(new StopShooter());

        turretManualLeft = new JoystickButton(buttonBox2, 8);
        turretManualLeft.whenPressed(new TurretMove(-0.4));
        turretManualLeft.whenReleased(new StopTurret());

        turretManualRight = new JoystickButton(buttonBox2, 9);
        turretManualRight.whenPressed(new TurretMove(0.4));
        turretManualRight.whenReleased(new StopTurret());
    }
}