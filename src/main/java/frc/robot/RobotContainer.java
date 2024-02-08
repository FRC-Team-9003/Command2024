// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.*;
import frc.robot.commands.*;
import frc.robot.subsystems.*;

/*
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems
  private final DriveSubsystem m_robotDrive = new DriveSubsystem();
  protected final Elevator m_robotElevator = new Elevator();
  protected final Intake m_robotIntake = new Intake();
  private final Shooter m_robotShoot = new Shooter();
  private final Climbers m_robotClimb = new Climbers();

  // The driver's controller
  CommandJoystick m_stickDrive = new CommandJoystick(OIConstants.kDriverControllerPort);
  CommandXboxController m_debugController =
      new CommandXboxController(OIConstants.kDebugControllerPort);

  // A chooser for autonomous commands
  SendableChooser<Command> m_autoChooser = new SendableChooser<>();

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureDebugBindings();

    // Sendable Chooser for autonomous
    // add items to chooser
    m_autoChooser.setDefaultOption("Fold", new Fold(m_robotElevator, m_robotIntake));

    SmartDashboard.putData("Auto Mode", m_autoChooser);

    // Configure default commands
    m_robotDrive.setDefaultCommand(
        // The left stick controls translation of the robot.
        // Turning is controlled by the X axis of the right stick.
        new RunCommand(
            () ->
                m_robotDrive.drive(
                    -MathUtil.applyDeadband(
                        Math.pow(m_stickDrive.getY(), 3), OIConstants.kDriveDeadband),
                    -MathUtil.applyDeadband(
                        Math.pow(m_stickDrive.getX(), 3), OIConstants.kDriveDeadband),
                    -MathUtil.applyDeadband(m_stickDrive.getZ(), OIConstants.kDriveDeadband),
                    true,
                    true),
            m_robotDrive));

    m_robotIntake.setDefaultCommand(
        new RunCommand(
            () -> {
              m_robotIntake.setSpeedElbow(m_debugController.getLeftY());
              m_robotIntake.setSpeedWrist(m_debugController.getRightY());
            },
            m_robotIntake));
  }

  // Set Default command for climbers. The sticks should be associated to each climber so they work
  // independently.

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link edu.wpi.first.wpilibj.GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then calling passing it to a
   * {@link JoystickButton}.
   */
  private void configureButtonBindings() {}

  private void configureDebugBindings() {

    /*
     * X - Wheels in X configuration
     * Y -
     * A - Intake In
     * B - Intake Out
     * DPad - Elevator
     * Left Bumper - Shoot note
     * Right Bumper - Take-In note
     * Left Y - Elbow
     * Right Y - Wrist
     */

    final Trigger x = m_debugController.x();
    x.onTrue(new RunCommand(() -> m_robotDrive.setX(), m_robotDrive));
    x.onFalse(new RunCommand(() -> m_robotDrive.setNormal(), m_robotDrive));

    final Trigger povUp = m_debugController.povUp();
    povUp.onTrue(
        new RunCommand(
            () -> m_robotElevator.setSpeed(-ElevatorConstants.defaultSpeed), m_robotElevator));
    povUp.onFalse(new RunCommand(() -> m_robotElevator.setSpeed(0), m_robotElevator));

    final Trigger povDown = m_debugController.povDown();
    povDown.onTrue(
        new RunCommand(
            () -> m_robotElevator.setSpeed(ElevatorConstants.defaultSpeed), m_robotElevator));
    povDown.onFalse(new RunCommand(() -> m_robotElevator.setSpeed(0), m_robotElevator));

    final Trigger a = m_debugController.a();
    a.onTrue(
        new RunCommand(
            () -> m_robotIntake.setSpeedIntake(-IntakeConstants.defaultSpeedIntake),
            m_robotIntake));
    a.onFalse(new RunCommand(() -> m_robotIntake.setSpeedIntake(0.0), m_robotIntake));

    final Trigger b = m_debugController.b();
    b.onTrue(
        new RunCommand(
            () -> m_robotIntake.setSpeedIntake(IntakeConstants.defaultSpeedIntake), m_robotIntake));
    b.onFalse(new RunCommand(() -> m_robotIntake.setSpeedIntake(0.0), m_robotIntake));

    final Trigger LeftBumper = m_debugController.leftBumper();
    LeftBumper.onTrue(
        new RunCommand(
            () -> {
              m_robotShoot.setSpeedShootA(ShooterConstants.defaultSpeedTop);
              m_robotShoot.setSpeedShootB(ShooterConstants.defaultSpeedBottom);
            },
            m_robotShoot));
    LeftBumper.onFalse(
        new RunCommand(
            () -> {
              m_robotShoot.setSpeedShootA(0.0);
              m_robotShoot.setSpeedShootB(0.0);
            },
            m_robotShoot));

    final Trigger RightBumper = m_debugController.rightBumper();
    RightBumper.onTrue(
        new RunCommand(
            () -> {
              m_robotShoot.setSpeedShootA(-ShooterConstants.defaultSpeedTop);
              m_robotShoot.setSpeedShootB(-ShooterConstants.defaultSpeedBottom);
            },
            m_robotShoot));
    RightBumper.onFalse(
        new RunCommand(
            () -> {
              m_robotShoot.setSpeedShootA(0);
              m_robotShoot.setSpeedShootB(0);
            },
            m_robotShoot));

    final Trigger noteTrigger = new Trigger(m_robotShoot::isNote);
    noteTrigger.onTrue(new RunCommand(() -> new Fold(m_robotElevator, m_robotIntake)));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return m_autoChooser.getSelected();
  }
}
