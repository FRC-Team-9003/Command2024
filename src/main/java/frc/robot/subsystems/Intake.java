package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkAbsoluteEncoder.Type;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;

public class Intake extends SubsystemBase {

  private CANSparkMax intake;
  private CANSparkMax elbow;
  private CANSparkMax wrist;
  private AbsoluteEncoder wristEncoder;
  private AbsoluteEncoder elbowEncoder;


  public Intake() {
    intake = new CANSparkMax(IntakeConstants.Neo550Intake, MotorType.kBrushless);

    elbow = new CANSparkMax(IntakeConstants.NeoElbow, MotorType.kBrushless);

    wrist = new CANSparkMax(IntakeConstants.Neo550Wrist, MotorType.kBrushless);

    wristEncoder = wrist.getAbsoluteEncoder(Type.kDutyCycle);

    elbowEncoder = elbow.getAbsoluteEncoder(Type.kDutyCycle); 
  }

  @Override
  public void periodic() {}

  @Override
  public void simulationPeriodic() {}

  public void stopIntake(){
    intake.stopMotor();
    
  
  }
  public void stopWrist(){
    wrist.stopMotor();  
}
  public void stopElbow(){
    elbow.stopMotor();
}
  public void setSpeedIntake(double speed){
    intake.set(speed);
    
  }
  public void setSpeedWrist(double speed){
    wrist.set(speed);
  }
  public void setSpeedElbow(double speed){
    elbow.set(speed);
  }
}
