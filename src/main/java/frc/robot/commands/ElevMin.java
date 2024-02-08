package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Elevator;
import frc.robot.Constants.ElevatorConstants;

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
