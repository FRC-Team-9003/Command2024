// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.*;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import java.util.function.BooleanSupplier;

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
  CommandXboxController m_debugController =
      new CommandXboxController(OIConstants.kDebugControllerPort);

  CommandJoystick m_redButton = new CommandJoystick(OIConstants.kMechanismBoxRedPort);
  CommandJoystick m_blueButton = new CommandJoystick(OIConstants.kMechanismBoxBluePort);

  // A chooser for autonomous commands
  // SendableChooser<Command> m_autoChooser = new SendableChooser<>();

  private TrajectoryConfig config;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    // configureDebugBindings();
    configureButtonBindings();

    // Register Named Commands
    NamedCommands.registerCommand("Wrist Tuck", new WristTuck(m_robotIntake));
    NamedCommands.registerCommand(
        "Intake Eject",
        new InstantCommand(() -> m_robotIntake.setSpeedIntake(0.8))
            .andThen(new WaitCommand(0.1))
            .finallyDo(() -> m_robotIntake.stopIntake()));
    NamedCommands.registerCommand(
        "Wrist Down",
        new WristPosition(0.25, m_robotIntake).finallyDo(() -> m_robotIntake.stopWrist()));

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

    /* // The code for testing elbow and wrist movement.

    m_robotIntake.setDefaultCommand(
        new RunCommand(
            () -> {
              m_robotIntake.setSpeedWrist(m_debugController.getLeftY() / 4);
            },
            m_robotIntake));

    m_robotElbow.setDefaultCommand(
        new RunCommand(
            () -> m_robotElbow.setSpeedElbow(m_debugController.getRightY() / 2), m_robotElbow)); */

  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link edu.wpi.first.wpilibj.GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then calling passing it to a
   * {@link JoystickButton}.
   */
  private void configureButtonBindings() {

    final Trigger shoot = m_blueButton.button(1);
    shoot.onTrue(new ShootNote(m_robotShoot));

    final Trigger shootIn = m_redButton.button(1);
    shootIn.onTrue(new ShootIn(m_robotShoot));

    BooleanSupplier wristActive = () -> m_robotElevator.getElevRev() && m_robotElbow.getElbowRev();

    final Trigger wrist = m_redButton.button(3);
    wrist.onTrue(
        new InstantCommand(() -> m_robotIntake.setSpeedWrist(-0.1), m_robotIntake)
            .onlyIf(wristActive));
    wrist.onFalse(new InstantCommand(() -> m_robotIntake.stopWrist(), m_robotIntake));

    final Trigger eject = m_redButton.button(4);
    eject.onTrue(
        new InstantCommand(
            () -> m_robotIntake.setSpeedIntake(IntakeConstants.defaultSpeedIntake), m_robotIntake));
    eject.onFalse(new InstantCommand(() -> m_robotIntake.stopIntake(), m_robotIntake));

    final Trigger foldDown = m_blueButton.button(7);
    foldDown.onTrue(new FoldDown(m_robotElevator, m_robotElbow, m_robotIntake));

    final Trigger foldUp = m_blueButton.button(3);
    foldUp.onTrue(new FoldUp(m_robotElevator, m_robotElbow, m_robotIntake));

    final Trigger midElbow = m_blueButton.button(5);
    midElbow.onTrue(new ElbowPosition(0.25, m_robotElbow));

    final Trigger oneMotion = m_redButton.button(5);
    oneMotion.onTrue(new OneMotionIntake(m_robotElevator, m_robotElbow, m_robotIntake));

    BooleanSupplier elevatorSlip =
        () ->
            m_robotElevator.getState().equals(ElevatorConstants.ElevState.Up)
                && !m_robotElevator.getElevRev();

    final Trigger returnToTop = new Trigger(elevatorSlip);
    returnToTop.onTrue(new ElevMax(m_robotElevator));
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

    config =
        new TrajectoryConfig(
            AutoConstants.kMaxSpeedMetersPerSecond,
            AutoConstants.kMaxAccelerationMetersPerSecondSquared);

    if (!m_robotIntake.isNote()) {
      return new PathPlannerAuto("Score Note");
    } else if (!m_robotShoot.hasNote()) {
      return new AutoDefault(config, m_robotDrive, m_robotShoot)
          .andThen(() -> m_robotDrive.drive(0, 0, 0, false, false));
    }

    return new LeaveZone(config, m_robotDrive)
        .andThen(() -> m_robotDrive.drive(0, 0, 0, false, false));
  }
}
