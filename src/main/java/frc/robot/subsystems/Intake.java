package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;

public class Intake extends SubsystemBase{

    private CANSparkMax take;
    private CANSparkMax elbow;
    private CANSparkMax wrist;

    public Intake(){
        take = new CANSparkMax(IntakeConstants.Neo550Intake, MotorType.kBrushless );

        elbow = new CANSparkMax(IntakeConstants.NeoElbow, MotorType.kBrushless);
    
        wrist = new CANSparkMax(IntakeConstants.Neo550Wrist, MotorType.kBrushless);
    
    }
   


    @Override
    public void periodic(){

    }


    @Override public void simulationPeriodic(){
        
    }
}
