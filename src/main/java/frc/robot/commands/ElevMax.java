package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.*;

import frc.robot.subsystems.Elevator;
import frc.robot.Constants.ElevatorConstants;

public class ElevMax extends Command {
  private final Elevator m_Elevator;

  public ElevMax(Elevator subsystem) {
    m_Elevator = subsystem;
    addRequirements(m_Elevator);
  }

  @Override
  public void initialize() {
    m_Elevator.setSpeed(-ElevatorConstants.defaultSpeed);
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {
    m_Elevator.stop();
  }

  @Override
  public boolean isFinished() {
    return m_Elevator.getElevRev();
  }

  @Override
  public boolean runsWhenDisabled() {
    return false;
  }
}
