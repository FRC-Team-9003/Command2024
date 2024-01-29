package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;

public class Shooter extends SubsystemBase{
   
    private CANSparkMax shoota;
    private CANSparkMax shootb;

    public Shooter(){
        shoota = new CANSparkMax(ShooterConstants.NeoBottom, MotorType.kBrushless);
        shootb = new CANSparkMax(ShooterConstants.NeoTop, MotorType.kBrushless);

    }
   
    @Override
    public void periodic(){

    }


    @Override public void simulationPeriodic(){
        
    }
}
