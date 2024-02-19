package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Intake;

public class PickUp extends ParallelCommandGroup {

  private final Elevator m_elevator;
  private final Intake m_intake;

  public PickUp(Elevator subsystem_e, Intake subsystem_i) {
    m_elevator = subsystem_e;
    m_intake = subsystem_i;

    addCommands(new ElevMin(m_elevator), new WristPosition(0.1, m_intake));
  }
}
