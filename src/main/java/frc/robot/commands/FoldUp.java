package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.*;
import frc.robot.subsystems.*;

public class FoldUp extends ParallelCommandGroup {
  private final Elevator m_elevator;
  private final Elbow m_elbow;
  private final Intake m_intake;

  public FoldUp(Elevator subsystem_E, Elbow subsystem_el, Intake subsystem_I) {

    m_elevator = subsystem_E;
    m_elbow = subsystem_el;
    m_intake = subsystem_I;
    addCommands(new ElevMax(m_elevator), new ElbowUp(m_elbow));
  }

  @Override
  public boolean runsWhenDisabled() {
    return false;
  }
}
