// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.Trigger;
// import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
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
  protected final Elbow m_robotElbow = new Elbow();
  private final Shooter m_robotShoot = new Shooter();
  private final Climbers m_robotClimb = new Climbers();

  // The driver's controller
  CommandJoystick m_stickDrive = new CommandJoystick(OIConstants.kDriverControllerPort);
  // CommandXboxController m_debugController =
  //    new CommandXboxController(OIConstants.kDebugControllerPort);

  CommandJoystick m_redButton = new CommandJoystick(OIConstants.kMechanismBoxRedPort);
  CommandJoystick m_blueButton = new CommandJoystick(OIConstants.kMechanismBoxBluePort);

  // A chooser for autonomous commands
  SendableChooser<Command> m_autoChooser = new SendableChooser<>();

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    // configureDebugBindings();
    configureButtonBindings();

    // Sendable Chooser for autonomous
    // add items to chooser
    m_autoChooser.setDefaultOption("Placeholder", new ElevMax(m_robotElevator));

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

    m_robotClimb.setDefaultCommand(
        new RunCommand(
            () -> {
              m_robotClimb.setSpeedLeftClimb(m_blueButton.getY());
              m_robotClimb.setSpeedRightClimb(m_redButton.getY());
            },
            m_robotClimb));

    /*
     * The code for testing elbow and wrist movement.
     *
     *     m_robotIntake.setDefaultCommand(
        new RunCommand(
            () -> {
              m_robotIntake.setSpeedWrist(m_debugController.getLeftY() / 4);
            },
            m_robotIntake));

    m_robotElbow.setDefaultCommand(
        new RunCommand(
            () -> m_robotElbow.setSpeedElbow(m_debugController.getRightY() / 2), m_robotElbow));
     *
     */

    // Create default command for climbers.

  }

  // Set Default command for climbers. The sticks should be associated to each climber so they work
  // independently.

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link edu.wpi.first.wpilibj.GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then calling passing it to a
   * {@link JoystickButton}.
   */
  private void configureButtonBindings() {

    // put button bindings for red / blue box

    final Trigger Shoot = m_blueButton.button(1);
    Shoot.onTrue(
        new InstantCommand(
            () -> {
              m_robotShoot.setSpeedBottom(ShooterConstants.defaultSpeedBottom);
              m_robotShoot.setSpeedTop(ShooterConstants.defaultSpeedTop);
            },
            m_robotShoot));
    Shoot.onFalse(
        new InstantCommand(
            () -> {
              m_robotShoot.stopBottom();
              m_robotShoot.stopTop();
            },
            m_robotShoot));

    final Trigger ShootIn = m_redButton.button(1);
    ShootIn.onTrue(
        new InstantCommand(
            () -> m_robotShoot.setSpeedTop(-ShooterConstants.defaultIn), m_robotShoot));
    ShootIn.onFalse(new InstantCommand(() -> m_robotShoot.stopTop(), m_robotShoot));

    final Trigger Wrist = m_redButton.button(3);
    Wrist.onTrue(new InstantCommand(() -> m_robotIntake.setSpeedWrist(-0.1), m_robotIntake));
    Wrist.onFalse(new InstantCommand(() -> m_robotIntake.stopWrist(), m_robotIntake));

    final Trigger Eject = m_redButton.button(4);
    Eject.onTrue(
        new InstantCommand(
            () -> m_robotIntake.setSpeedIntake(IntakeConstants.defaultSpeedIntake), m_robotIntake));
    Eject.onFalse(new InstantCommand(() -> m_robotIntake.stopIntake(), m_robotIntake));

    final Trigger FoldDown = m_blueButton.button(7);
    FoldDown.onTrue(new FoldDown(m_robotElevator, m_robotElbow, m_robotIntake));

    final Trigger FoldUp = m_blueButton.button(3);
    FoldUp.onTrue(new FoldUp(m_robotElevator, m_robotElbow, m_robotIntake));
  }

  /** private void configureDebugBindings() { */
  /*
   * X - Wheels in X configuration
   * Y -
   * A - Intake In
   * B - Intake Out
   * DPad - Elevator
   * Left Bumper - Shoot note top motors
   * Left Trigger - Shoot note bottom motor
   * Right Bumper - Take-In note
   * Left Y - Elbow
   * Right Y - Wrist
   */
  /**
   * final Trigger x = m_debugController.x();
   *
   * <p>// x.onTrue(new InstantCommand(() -> m_robotDrive.setX(), m_robotDrive)); // x.onFalse(new
   * InstantCommand(() -> m_robotDrive.setNormal(), m_robotDrive));
   *
   * <p>x.whileTrue( new WristPosition(0.25, m_robotIntake)
   * .withInterruptBehavior(InterruptionBehavior.kCancelSelf));
   *
   * <p>final Trigger povUp = m_debugController.povUp(); povUp.onTrue( new InstantCommand( () ->
   * m_robotElevator.setSpeed(-ElevatorConstants.defaultSpeed), m_robotElevator)); povUp.onFalse(new
   * InstantCommand(() -> m_robotElevator.stop(), m_robotElevator));
   *
   * <p>final Trigger povDown = m_debugController.povDown(); povDown.onTrue( new InstantCommand( ()
   * -> m_robotElevator.setSpeed(ElevatorConstants.defaultSpeed), m_robotElevator));
   * povDown.onFalse(new InstantCommand(() -> m_robotElevator.stop(), m_robotElevator));
   *
   * <p>final Trigger a = m_debugController.a(); a.onTrue( new InstantCommand( () ->
   * m_robotIntake.setSpeedIntake(-IntakeConstants.defaultSpeedIntake), m_robotIntake));
   * a.onFalse(new InstantCommand(() -> m_robotIntake.setSpeedIntake(0.0), m_robotIntake));
   *
   * <p>final Trigger b = m_debugController.b(); b.onTrue( new InstantCommand( () ->
   * m_robotIntake.setSpeedIntake(IntakeConstants.defaultSpeedIntake), m_robotIntake));
   * b.onFalse(new InstantCommand(() -> m_robotIntake.stopIntake(), m_robotIntake));
   *
   * <p>final Trigger y = m_debugController.y(); y.onTrue( new InstantCommand( () -> {
   * m_robotShoot.setSpeedTop(1.0); m_robotShoot.setSpeedBottom(0.9); }, m_robotShoot)); y.onFalse(
   * new InstantCommand( () -> { m_robotShoot.stopBottom(); m_robotShoot.stopTop(); },
   * m_robotShoot));
   *
   * <p>final Trigger LeftBumper = m_debugController.leftBumper(); LeftBumper.whileTrue( new
   * InstantCommand( () -> m_robotShoot.setSpeedTop(ShooterConstants.defaultSpeedTop),
   * m_robotShoot)); LeftBumper.onFalse(new InstantCommand(() -> m_robotShoot.stopTop(),
   * m_robotShoot));
   *
   * <p>final Trigger lTrigger = m_debugController.leftTrigger(); lTrigger.whileTrue( new
   * InstantCommand(() -> m_robotShoot.setSpeedBottom(ShooterConstants.defaultSpeedBottom)));
   * lTrigger.onFalse(new InstantCommand(() -> m_robotShoot.stopBottom(), m_robotShoot));
   *
   * <p>final Trigger RightBumper = m_debugController.rightBumper(); RightBumper.whileTrue(new
   * InstantCommand(() -> m_robotShoot.setSpeedTop(-0.25), m_robotShoot)); RightBumper.onFalse(new
   * InstantCommand(() -> m_robotShoot.stopTop(), m_robotShoot)); }
   */

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return m_autoChooser.getSelected();
  }
}
