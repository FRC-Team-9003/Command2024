package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.*;

public class Fold extends ParallelCommandGroup {
  private final Intake m_intake;
  private final Elevator m_elevator;

  public Fold(Elevator subsystem_E, Intake subsystem_I) {
    m_intake = subsystem_I;
    m_elevator = subsystem_E;

    addRequirements(m_intake, m_elevator);

    addCommands(new ElevMax(m_elevator)); // Add Intake operations
  }

  @Override
  public boolean runsWhenDisabled() {
    return false;
  }
}
