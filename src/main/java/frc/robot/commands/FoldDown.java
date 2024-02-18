package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.*;
import frc.robot.Constants.IntakeConstants;
import frc.robot.subsystems.*;

public class FoldDown extends SequentialCommandGroup {
  private final Elevator m_elevator;
  private final Elbow m_elbow;
  private final Intake m_intake;

  public FoldDown(Elevator subsystem_E, Elbow subsystem_el, Intake subsystem_I) {

    m_elevator = subsystem_E;
    m_elbow = subsystem_el;
    m_intake = subsystem_I;

    addCommands(
        new ElbowPosition(0.05, m_elbow),
        new PickUp(m_elevator, m_intake),
        new ElbowDown(m_elbow),
        new InstantCommand(() -> m_intake.setSpeedIntake(IntakeConstants.defaultSpeedIntake)));
  }

  @Override
  public boolean runsWhenDisabled() {
    return false;
  }
}
