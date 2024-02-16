package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.*;

public class IntakeNote extends ParallelCommandGroup {

  private Elevator elev;
  private Elbow el;
  private Intake intake;

  public IntakeNote(Elevator subsystem_E, Elbow subsystem_el, Intake subsystem_I) {

    elev = subsystem_E;
    el = subsystem_el;
    intake = subsystem_I;

    addCommands(new ElevMin(elev), new ElbowDown(el));
  }
}
