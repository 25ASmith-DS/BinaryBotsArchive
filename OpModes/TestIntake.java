package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="TestIntake", group="Linear Opmode")

public class TestIntake extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor mFL = null;
    private DcMotor mFR = null;
    private DcMotor mBL = null;
    private DcMotor mBR = null;
    private DcMotor mSpinner = null;
    private DcMotor mIntake = null;
    private DcMotor mTurret = null;
    private Servo sCarriage = null;
    private Servo sFlap = null;
    boolean sCarriageDown = true;

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
        mIntake = hardwareMap.get(DcMotor.class, "intake");
        mTurret = hardwareMap.get(DcMotor.class, "turret");
        sCarriage = hardwareMap.get(Servo.class, "carriage");
        sFlap = hardwareMap.get(Servo.class, "flap");
        
        mFL.setDirection(DcMotor.Direction.FORWARD);
        mFR.setDirection(DcMotor.Direction.FORWARD);
        mBL.setDirection(DcMotor.Direction.FORWARD);
        mBR.setDirection(DcMotor.Direction.REVERSE);
        mIntake.setDirection(DcMotor.Direction.FORWARD);
        mTurret.setDirection(DcMotor.Direction.FORWARD);
        sCarriage.setDirection(Servo.Direction.FORWARD);
        sFlap.setDirection(Servo.Direction.FORWARD);
        
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            
            //Getting Controller Input
            double forwardPower = gamepad1.left_stick_y;
            double strafePower = gamepad1.left_stick_x;
            double turnPower = gamepad1.right_stick_x;
            double intakePower = 0.7 * (gamepad1.right_trigger - gamepad1.left_trigger);
            //int flapPosition = gamepad1.a ? 1 : 0;
            
            //intake triggers stop
            //bumpers lift and turn 90 degrees
            
            //Setting Motor Powers and Servo Positions
            mFL.setPower(forwardPower - turnPower - strafePower);
            mFR.setPower(forwardPower + turnPower + strafePower); 
            mBL.setPower(forwardPower - turnPower + strafePower);
            mBR.setPower(forwardPower + turnPower - strafePower);
            mIntake.setPower(intakePower);
            
            //if (gamepad1.y) {
            //    sCarriage.setPosition(0.25);
            //}
            
            if (gamepad1.right_bumper) {
                mTurret.setPower(0.15);
                sleep(100);
                mTurret.setPower(0);
            }
            
            if (gamepad1.left_bumper) {
                mTurret.setPower(-0.15);
                sleep(100);
                mTurret.setPower(0);
            }
            
            if(gamepad1.y){
                sFlap.setPosition(0.05);
                sleep(1000);
                sFlap.setPosition(1);
            }
        
        if (gamepad1.a && sCarriageDown) {
            sCarriage.setPosition(0.25);
            sCarriageDown = false;
        
            
        }
        
        if (gamepad1.a && !sCarriageDown) {
            sCarriage.setPosition(1.0);
            sCarriageDown = true;
        
            
        }

            
        }
    }
}

