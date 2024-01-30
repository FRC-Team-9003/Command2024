package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ClimbersConstants;

public class Climbers extends SubsystemBase {

  private CANSparkMax leftclimb;
  private CANSparkMax rightclimb;

  public Climbers() {
    leftclimb = new CANSparkMax(ClimbersConstants.NeoLeftClimber, MotorType.kBrushless);
    rightclimb = new CANSparkMax(ClimbersConstants.NeoRightClimber, MotorType.kBrushless);
  }

  @Override
  public void periodic() {}

  @Override
  public void simulationPeriodic() {}

  public void stopLeftClimb(){
    leftclimb.stopMotor();
  }

  public void stopRightClimb(){
    rightclimb.stopMotor();
  }

  public void setSpeedLeftClimb(double speed){
    leftclimb.set(speed);
  }

  public void setSpeedRightClimb(double speed){
    rightclimb.set(speed);
  }
}
