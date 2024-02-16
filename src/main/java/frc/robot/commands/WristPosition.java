package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.*;
import frc.robot.subsystems.Intake;

public class WristPosition extends Command {

  private Intake intake;
  private double target;

  public WristPosition(double value, Intake subsystem_I) {
    intake = subsystem_I;
    target = value;
  }

  @Override
  public void initialize() {
    intake.setSpeedWrist(-0.15);
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {
    intake.stopWrist();
  }

  @Override
  public boolean isFinished() {
    return intake.getWristEncoder() > target;
  }

  @Override
  public boolean runsWhenDisabled() {
    return false;
  }
}
