package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.SparkAbsoluteEncoder.Type;
import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkAbsoluteEncoder;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ElevatorConstants;

public class Elevator extends SubsystemBase {

  private CANSparkMax elev;
  private AbsoluteEncoder elevEncoder;
  public Elevator() {
    elev = new CANSparkMax(ElevatorConstants.kElevatorCanID, MotorType.kBrushless);
    elevEncoder = elev.getAbsoluteEncoder(Type.kDutyCycle);
  }

  @Override
  public void periodic() {}

  @Override
  public void simulationPeriodic() {}

  public void stop(){
    elev.stopMotor();
  }

  public void setSpeed(double speed){
    elev.set(speed);
  }
}
