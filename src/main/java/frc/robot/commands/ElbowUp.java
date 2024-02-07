package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.*;
import frc.robot.subsystems.Intake;

public class ElbowUp extends Command {

  private final Intake m_intake;

  public ElbowUp(Intake subsystem) {
    m_intake = subsystem;

    addRequirements(m_intake);
  }

  @Override
  public void initialize() {
    // set elbow motor to go up at 1/4 speed
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    // return limit switch isPressed
    return true; // temp value
  }

  @Override
  public boolean runsWhenDisabled() {
    return false;
  }
}
