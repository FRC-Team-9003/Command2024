package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.*;

public class OneMotionIntake extends SequentialCommandGroup {

  private Intake m_intake;
  private Elbow m_elbow;
  private Elevator m_elev;

  public OneMotionIntake(Elevator subsystem_e, Elbow subsystem_el, Intake subsystem_i) {
    m_elev = subsystem_e;
    m_elbow = subsystem_el;
    m_intake = subsystem_i;

    addCommands(new FoldDown(m_elev, m_elbow, m_intake), new FoldUp(m_elev, m_elbow, m_intake));
  }
}
