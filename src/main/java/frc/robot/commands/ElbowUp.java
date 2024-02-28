package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.*;
import frc.robot.Constants.ElbowConstants;
import frc.robot.subsystems.Elbow;

public class ElbowUp extends Command {

  private final Elbow elbow;

  public ElbowUp(Elbow subsystem) {
    elbow = subsystem;

    addRequirements(elbow);
  }

  @Override
  public void initialize() {
    elbow.setSpeedElbow(-ElbowConstants.defaultSpeedElbow);
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {
    elbow.stopElbow();
  }

  @Override
  public boolean isFinished() {
    return elbow.getElbowRev();
  }

  @Override
  public boolean runsWhenDisabled() {
    return false;
  }
}
