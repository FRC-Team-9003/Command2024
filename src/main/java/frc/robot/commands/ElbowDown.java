package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.*;
import frc.robot.Constants.ElbowConstants;
import frc.robot.subsystems.Elbow;

public class ElbowDown extends Command {

  private final Elbow elbow;

  public ElbowDown(Elbow subsystem) {
    elbow = subsystem;

    addRequirements(elbow);
  }

  @Override
  public void initialize() {
    elbow.setSpeedElbow(ElbowConstants.defaultSpeedElbow);
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return elbow.getElbowFwd();
  }

  @Override
  public boolean runsWhenDisabled() {
    return false;
  }
}
