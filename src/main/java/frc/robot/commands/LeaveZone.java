package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;
import frc.robot.Constants.*;
import frc.robot.subsystems.*;
import java.util.List;

public class LeaveZone extends SequentialCommandGroup {

  private DriveSubsystem m_drive;

  private TrajectoryConfig config;
  private Trajectory trajectoryLeave;
  private ProfiledPIDController theta;
  private PIDController xCont;
  private PIDController yCont;

  public LeaveZone(TrajectoryConfig configure, DriveSubsystem drive) {
    m_drive = drive;
    config = configure;

    trajectoryLeave =
        TrajectoryGenerator.generateTrajectory(
            List.of(
                new Pose2d(0, 0, new Rotation2d(0)),
                new Pose2d(0.25, 0, new Rotation2d(0)),
                new Pose2d(1.25, 0, new Rotation2d(0))),
            config);

    theta =
        new ProfiledPIDController(
            AutoConstants.kPThetaController, 0, 0, AutoConstants.kThetaControllerConstraints);
    theta.enableContinuousInput(-Math.PI, Math.PI);

    xCont = new PIDController(AutoConstants.kPXController, 0, 0);
    yCont = new PIDController(AutoConstants.kPYController, 0, 0);

    addCommands(
        new SwerveControllerCommand(
            trajectoryLeave,
            m_drive::getPose,
            DriveConstants.kDriveKinematics,
            xCont,
            yCont,
            theta,
            m_drive::setModuleStates,
            m_drive));
  }

  @Override
  public boolean runsWhenDisabled() {
    return false;
  }
}
