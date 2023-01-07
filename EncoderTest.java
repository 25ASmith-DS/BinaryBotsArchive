package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous

public class EncoderTest extends LinearOpMode {


    private DcMotor mFL = null;
    private DcMotor mFR = null;
    private DcMotor mBL = null;
    private DcMotor mBR = null;
    private DcMotor mSpinner = null;
    
    private final double WHEEL_DIAMETER = 9.6;
    private final double TICKS_PER_REV = 1120;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        mFL  = hardwareMap.get(DcMotor.class, "motorFL");
        mFR = hardwareMap.get(DcMotor.class, "motorFR");
        mBL = hardwareMap.get(DcMotor.class, "motorBL");
        mBR = hardwareMap.get(DcMotor.class, "motorBR");
        mSpinner = hardwareMap.get(DcMotor.class, "spinner");
        
        mFL.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
        mFR.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
        mBL.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
        mBR.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);


        mFL.setDirection(DcMotor.Direction.REVERSE);
        mBL.setDirection(DcMotor.Direction.REVERSE);
        mFR.setDirection(DcMotor.Direction.FORWARD);
        mBR.setDirection(DcMotor.Direction.FORWARD);
        
        mFL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        mFR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        mBL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        mBR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    

        

        waitForStart();

        while (opModeIsActive()) {
            

        MoveCM(60.96);
        
            telemetry.addData("Target Distance: ", mSpinner.getTargetPosition());
            telemetry.update();
        }
    }
    
    public void MoveCM (double distance) {
        

        int tickValue = (int)((distance * TICKS_PER_REV) / (Math.PI * WHEEL_DIAMETER));
        
        //mSpinner.setTargetPosition(tickValue);
        mFL.setTargetPosition(1000);
        mFR.setTargetPosition(1000);
        mBL.setTargetPosition(1000);
        mBR.setTargetPosition(1000);
        
        mFL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        mFR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        mBL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        mBR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        
        telemetry.addData("Test:","Reached");
        telemetry.update();
        
        
        //mFL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //mFR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //mBL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //mBR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //mSpinner.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        
    }
}



