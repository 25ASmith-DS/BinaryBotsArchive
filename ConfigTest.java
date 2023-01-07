package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@Autonomous(name="Configuration Test", group="Linear Opmode")

public class ConfigTest extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor mFL = null;
    private DcMotor mFR = null;
    private DcMotor mBL = null;
    private DcMotor mBR = null;
    private DcMotor mSpinner = null;
    
    private Boolean autoActive = true;
    private final double MAXSPEED = 8;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        mFL  = hardwareMap.get(DcMotor.class, "motorFL");
        mFR = hardwareMap.get(DcMotor.class, "motorFR");
        mBL = hardwareMap.get(DcMotor.class, "motorBL");
        mBR = hardwareMap.get(DcMotor.class, "motorBR");
        mSpinner = hardwareMap.get(DcMotor.class, "spinner");

        mFL.setDirection(DcMotor.Direction.REVERSE);
        mBL.setDirection(DcMotor.Direction.FORWARD);
        mFR.setDirection(DcMotor.Direction.FORWARD);
        mBR.setDirection(DcMotor.Direction.FORWARD);
        
        mFL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        mBL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        mFR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        mBR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        
        
        

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            
            Test(mFR, 1000);
            Test(mFL, 1000);
            Test(mBL, 1000);
            Test(mBR, 1000);
            
            
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Motors", "left (%.2f), right (%.2f)");
            telemetry.update();
        }
    }
        public void Test(DcMotor motor, long duration){
            motor.setPower(0.5);
            sleep(duration);
            motor.setPower(0);
        }
}
