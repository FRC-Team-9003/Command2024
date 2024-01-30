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

  public void stop(){
    intake.stopMotor();
    wrist.stopMotor();
    elbow.stopMotor();
  }

  public void setSpeed(double speed){
    intake.set(speed);
    wrist.set(speed);
    elbow.set(speed);
  }
}
