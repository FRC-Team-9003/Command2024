package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.*;
import frc.robot.Constants.ElbowConstants;
import frc.robot.subsystems.Elbow;

public class ElbowPosition extends Command {

  private Elbow elbow;
  private double target;

  public ElbowPosition(double value, Elbow subsystem_el) {
    elbow = subsystem_el;
    target = value;
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    elbow.setSpeedElbow(ElbowConstants.defaultSpeedElbow);
  }

  @Override
  public void end(boolean interrupted) {
    elbow.stopElbow();
  }

  @Override
  public boolean isFinished() {
    return elbow.getElbowEncoder() < target;
  }

  @Override
  public boolean runsWhenDisabled() {
    return false;
  }
}
