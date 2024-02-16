package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.*;
import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.Shooter;

public class ShootIn extends Command {

  private final Shooter robotShooter;

  public ShootIn(Shooter subsystem) {
    robotShooter = subsystem;

    addRequirements(robotShooter);
  }

  @Override
  public void initialize() {
    robotShooter.setSpeedTop(-ShooterConstants.defaultIn);
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {
    robotShooter.stopTop();
  }

  @Override
  public boolean isFinished() {
    return robotShooter.hasNote();
  }

  @Override
  public boolean runsWhenDisabled() {
    return false;
  }
}
