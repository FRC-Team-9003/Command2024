package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.*;
import frc.robot.Constants.*;
import frc.robot.subsystems.Intake;

public class WristCurl extends Command {

  private Intake m_intake;

  public WristCurl(Intake subsystem_I) {

    m_intake = subsystem_I;
    addRequirements(m_intake);
  }

  @Override
  public void initialize() {
    m_intake.setSpeedElbow(IntakeConstants.defaultSpeedWrist);
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return m_intake.getWristFwd();
  }

  @Override
  public boolean runsWhenDisabled() {
    return false;
  }
}
