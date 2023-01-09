package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import java.math.MathContext;
import java.lang.annotation.Target;
import com.qualcomm.robotcore.hardware.DcMotor.RunMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.LED;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@Autonomous(name="Blue Carousel Pipe Auto", group="Linear Opmode")

public class BlueCarPipeAuto extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor mFL = null;
    private DcMotor mFR = null;
    private DcMotor mBL = null;
    private DcMotor mBR = null;
    private DcMotor mSpinner = null;
    private Boolean isAutoActive = true;
    // private double clawPosition = 0;
    
    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize the hardware variables.
        mFL  = hardwareMap.get(DcMotor.class, "motorFL");
        mFR = hardwareMap.get(DcMotor.class, "motorFR");
        mBL = hardwareMap.get(DcMotor.class, "motorBL");
        mBR = hardwareMap.get(DcMotor.class, "motorBR");
        mSpinner = hardwareMap.get(DcMotor.class, "spinner");
        

        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        mFL.setDirection(DcMotor.Direction.FORWARD);
        mFR.setDirection(DcMotor.Direction.FORWARD);
        mBL.setDirection(DcMotor.Direction.FORWARD);
        mBR.setDirection(DcMotor.Direction.REVERSE);
        mSpinner.setDirection(DcMotor.Direction.FORWARD);
        
        mFL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        mFR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        mBL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        mBR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            
            if(isAutoActive){
                //FL, FR, BL, BR
                mFL.setPower(-0.4);
                mFR.setPower(-0.4);
                mBL.setPower(-0.4);
                mBR.setPower(-0.4);
                sleep(500);
                StopMotors();
                mFL.setPower(-0.3);
                mFR.setPower(0.3);
                mBL.setPower(0.3);
                mBR.setPower(-0.3);
                sleep(1000);
                mFL.setPower(0.4);
                mFR.setPower(0.4);
                mBL.setPower(0.4);
                mBR.setPower(0.4);
                sleep(250);
                StopMotors();
                Spin(0.03);
                mFL.setPower(-0.4);
                mFR.setPower(-0.4);
                mBL.setPower(-0.4);
                mBR.setPower(-0.4);
                sleep(500);
                mFL.setPower(0.2);
                mFR.setPower(-0.2);
                mBL.setPower(0.2);
                mBR.setPower(-0.2);
                sleep(2500);
                mFL.setPower(-0.3);
                mFR.setPower(0.3);
                mBL.setPower(0.3);
                mBR.setPower(-0.3);
                sleep(3000);
                mFL.setPower(0.3);
                mFR.setPower(-0.3);
                mBL.setPower(-0.3);
                mBR.setPower(0.3);
                sleep(1500);
                mFL.setPower(0.8);
                mFR.setPower(0.8);
                mBL.setPower(0.8);
                mBR.setPower(0.8);
                sleep(3000);
                StopMotors();
                
                isAutoActive = false;
            }
            
            
        }
    }
        
    public void Spin(double acceleration){
            double power = 0;
            while(Math.abs(power) < 8){
                power += acceleration;
                telemetry.addData("Power", power);
                mSpinner.setPower(power);
                sleep(8L);
            }
            //sleep(100L);
            mSpinner.setPower(0);
        }
        
    public void StopMotors(){
        mFL.setPower(0);
        mFR.setPower(0);
        mBL.setPower(0);
        mBR.setPower(0);
    }
}
