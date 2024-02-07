// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.OIConstants;
import frc.robot.subsystems.*;
import java.util.List;

/*
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems
  private final DriveSubsystem m_robotDrive = new DriveSubsystem();
  private final Elevator m_robotElevator = new Elevator();
  private final Intake m_robotIntake = new Intake();
  private final Shooter m_robotShoot = new Shooter();
  private final Climbers m_robotClimb = new Climbers();

  // The driver's controller
  CommandJoystick m_stickDrive = new CommandJoystick(OIConstants.kDriverControllerPort);
  CommandXboxController m_debugController =
      new CommandXboxController(OIConstants.kDebugControllerPort);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureDebugBindings();

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

    m_robotElevator.setDefaultCommand(
        new RunCommand(
            () -> m_robotElevator.setSpeed(m_debugController.getLeftY()), m_robotElevator));

    m_robotIntake.setDefaultCommand(
      new RunCommand(() -> 
        m_robotIntake.setSpeedElbow(m_debugController.getLeftY()), m_robotIntake)
      );
      

    m_robotIntake.setDefaultCommand(
     new RunCommand(() -> m_robotIntake.setSpeedWrist(m_debugController.getRightY()), m_robotIntake));

/*     m_robotClimb.setDefaultCommand(
      new RunCommand(() -> m_robotClimb.setSpeedLeftClimb(m_debugController.))
    );

    m_robotClimb.setDefaultCommand(
      new RunCommand(() -> m_robotClimb.setSpeedRightClimb(m_debugController.))); */
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
     * DPad - "Elbow" and "Wrist"
     * Left Bumper - Shoot
     * Right Bumper - Take-In note
     */

    final Trigger x = m_debugController.x();
    x.onTrue(new RunCommand(() -> m_robotDrive.setX(), m_robotDrive));
    x.onFalse(new RunCommand(() -> m_robotDrive.setNormal(), m_robotDrive));

    final Trigger povUp = m_debugController.povUp();
    povUp.onTrue(new RunCommand(() -> m_robotElevator.setSpeed(.25), m_robotElevator));
    povUp.onFalse(new RunCommand(() -> m_robotElevator.setSpeed(0), m_robotElevator));

    final Trigger povDown = m_debugController.povDown();
    povDown.onTrue(new RunCommand(() -> m_robotElevator.setSpeed(-.25), m_robotElevator));
    povDown.onFalse(new RunCommand(() -> m_robotElevator.setSpeed(0), m_robotElevator));

    final Trigger a = m_debugController.a();
    a.onTrue(new RunCommand(() -> m_robotIntake.setSpeedIntake(0.5), m_robotIntake));
    a.onFalse(new RunCommand(() -> m_robotIntake.setSpeedIntake(0.0), m_robotIntake));

    final Trigger b = m_debugController.b();
    b.onTrue(new RunCommand(() -> m_robotIntake.setSpeedIntake(-0.5), m_robotIntake));
    b.onFalse(new RunCommand(() -> m_robotIntake.setSpeedIntake(0.0), m_robotIntake));

    // Need to change so that both motors are running for both Left and Right bumper
    final Trigger LeftBumper = m_debugController.leftBumper();
    LeftBumper.onTrue(new RunCommand(() -> m_robotShoot.setSpeedShootA(0.9), m_robotShoot));
    LeftBumper.onFalse(new RunCommand(() -> m_robotShoot.setSpeedShootA(0), m_robotShoot));

    final Trigger RightBumper = m_debugController.rightBumper();
    RightBumper.onTrue(new RunCommand(() -> m_robotShoot.setSpeedShootB(-0.9), m_robotShoot));
    RightBumper.onFalse(new RunCommand(() -> m_robotShoot.setSpeedShootB(0.0), m_robotShoot));

    // Limit Switch Binding - Note in intake invokes fold-up command

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // Create config for trajectory
    TrajectoryConfig config =
        new TrajectoryConfig(
                AutoConstants.kMaxSpeedMetersPerSecond,
                AutoConstants.kMaxAccelerationMetersPerSecondSquared)
            // Add kinematics to ensure max speed is actually obeyed
            .setKinematics(DriveConstants.kDriveKinematics);

    // An example trajectory to follow. All units in meters.
    Trajectory exampleTrajectory =
        TrajectoryGenerator.generateTrajectory(
            // Start at the origin facing the +X direction
            new Pose2d(0, 0, new Rotation2d(0)),
            // Pass through these two interior waypoints, making an 's' curve path
            List.of(new Translation2d(1, 1), new Translation2d(2, -1)),
            // End 3 meters straight ahead of where we started, facing forward
            new Pose2d(3, 0, new Rotation2d(0)),
            config);

    var thetaController =
        new ProfiledPIDController(
            AutoConstants.kPThetaController, 0, 0, AutoConstants.kThetaControllerConstraints);
    thetaController.enableContinuousInput(-Math.PI, Math.PI);

    SwerveControllerCommand swerveControllerCommand =
        new SwerveControllerCommand(
            exampleTrajectory,
            m_robotDrive::getPose, // Functional interface to feed supplier
            DriveConstants.kDriveKinematics,

            // Position controllers
            new PIDController(AutoConstants.kPXController, 0, 0),
            new PIDController(AutoConstants.kPYController, 0, 0),
            thetaController,
            m_robotDrive::setModuleStates,
            m_robotDrive);

    // Reset odometry to the starting pose of the trajectory.
    m_robotDrive.resetOdometry(exampleTrajectory.getInitialPose());

    // Run path following command, then stop at the end.
    return swerveControllerCommand.andThen(() -> m_robotDrive.drive(0, 0, 0, false, false));
  }
}
