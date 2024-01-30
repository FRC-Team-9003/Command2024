package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.SparkAbsoluteEncoder.Type;
import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkAbsoluteEncoder;
import com.revrobotics.SparkLimitSwitch;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ElevatorConstants;

public class Elevator extends SubsystemBase {

  private CANSparkMax elev;
  private SparkAbsoluteEncoder elevEncoder;
  private SparkLimitSwitch elevFwd;
  private SparkLimitSwitch elevRev;

  public Elevator() {
    elev = new CANSparkMax(ElevatorConstants.kElevatorCanID, MotorType.kBrushless);
    elevEncoder = elev.getAbsoluteEncoder(Type.kDutyCycle);
    elevFwd = elev.getForwardLimitSwitch(SparkLimitSwitch.Type.kNormallyClosed);
    elevRev = elev.getReverseLimitSwitch(SparkLimitSwitch.Type.kNormallyClosed);
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
  
  public double getElevEncoder(){
    return elevEncoder.getPosition();
  }

  public boolean getElevFwd(){
    return elevFwd.isPressed();
  }

  public boolean getElevRev(){
    return elevRev.isPressed();
  }
}
