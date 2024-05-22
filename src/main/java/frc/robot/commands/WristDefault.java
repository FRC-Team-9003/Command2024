package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.*;
import frc.robot.subsystems.Intake;

public class WristDefault extends Command {
  private Intake intake;

  public WristDefault(Intake subsystem_I) {
    intake = subsystem_I;
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    intake.setPosition(Math.PI / 2);
  }

  @Override
  public void end(boolean interrupted) {
    intake.stopWrist();
  }

  @Override
  public boolean runsWhenDisabled() {
    return false;
  }
}
