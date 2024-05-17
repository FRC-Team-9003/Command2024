package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.ElevatorConstants;
import frc.robot.subsystems.Elevator;

public class ElevMin extends Command {
  private final Elevator m_Elevator;

  public ElevMin(Elevator subsystem) {
    m_Elevator = subsystem;
    addRequirements(m_Elevator);
  }

  @Override
  public void initialize() {
    m_Elevator.setSpeed(ElevatorConstants.defaultSpeed);
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {
    m_Elevator.stop();
    m_Elevator.setState(ElevatorConstants.ElevState.Down);
  }

  @Override
  public boolean isFinished() {
    return m_Elevator.getElevFwd();
  }

  @Override
  public boolean runsWhenDisabled() {
    return false;
  }
}
