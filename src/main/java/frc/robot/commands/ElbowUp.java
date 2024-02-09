package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.*;
import frc.robot.Constants.*;
import frc.robot.subsystems.Intake;

public class ElbowUp extends Command {

  private final Intake m_intake;

  public ElbowUp(Intake subsystem) {
    m_intake = subsystem;

    addRequirements(m_intake);
  }

  @Override
  public void initialize() {
    m_intake.setSpeedElbow(-IntakeConstants.defaultSpeedElbow);
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return m_intake.getElbowRev();
  }

  @Override
  public boolean runsWhenDisabled() {
    return false;
  }
}
