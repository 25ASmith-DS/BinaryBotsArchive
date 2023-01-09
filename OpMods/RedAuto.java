package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@Autonomous(name="RedAuto", group="Linear Opmode")

public class RedAuto extends LinearOpMode {

    // Declare OpMode members.
    private DcMotor mFL = null;
    private DcMotor mFR = null;
    private DcMotor mBL = null;
    private DcMotor mBR = null;
    private DcMotor mSpinner = null;


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
        mBL.setDirection(DcMotor.Direction.REVERSE);
        mFR.setDirection(DcMotor.Direction.REVERSE);
        mBR.setDirection(DcMotor.Direction.FORWARD);

        waitForStart();
        
        mFR.setPower(1);
        mFL.setPower(1);
        sleep(2000);
        
        mFL.setPower(0);
        mFR.setPower(0);

      
    }
    
    public void Spin(double acceleration){
        double power = 0;
        while(Math.abs(power) < 0.7){
            power += acceleration;
            // if (power > MAXSPEED) {
            //     power = MAXSPEED;
            // }
            telemetry.addData("Power", power);
            mSpinner.setPower(power);
            sleep(8L);
        }
        mSpinner.setPower(0);
    }
}
