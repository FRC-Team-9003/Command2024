package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Elevator;

public class ElevMin extends Command {
  private final Elevator m_Elevator;

  public ElevMin(Elevator subsystem) {
    m_Elevator = subsystem;
    addRequirements(m_Elevator);
  }

  @Override
  public void initialize() {
    // set elevator to go down at 1/2 speed
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {
    m_Elevator.stop();
  }

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
