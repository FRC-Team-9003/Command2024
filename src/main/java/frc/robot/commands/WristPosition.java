package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.*;
import frc.robot.Constants.IntakeConstants;
import frc.robot.subsystems.Intake;

public class WristPosition extends Command {

  private final double bottomWrist = 0.57;

  private Intake intake;
  private double target;

  public WristPosition(double value, Intake subsystem_I) {
    intake = subsystem_I;
    target = value;
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    intake.setSpeedWrist(-IntakeConstants.defaultSpeedWrist);
  }

  @Override
  public void end(boolean interrupted) {
    intake.stopWrist();
  }

  @Override
  public boolean isFinished() {
    return intake.getWristEncoder() > bottomWrist + target;
  }

  @Override
  public boolean runsWhenDisabled() {
    return false;
  }
}
