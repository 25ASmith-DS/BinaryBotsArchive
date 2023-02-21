package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;



@TeleOp(name="Binary Bots Mecanum Drive", group="Linear Opmode")

public class MecanumDrive extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor mFL = null;
    private DcMotor mFR = null;
    private DcMotor mBL = null;
    private DcMotor mBR = null;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize the hardware variables.
        mFL  = hardwareMap.get(DcMotor.class, "motorFL");
        mFR = hardwareMap.get(DcMotor.class, "motorFR");
        mBL = hardwareMap.get(DcMotor.class, "motorBL");
        mBR = hardwareMap.get(DcMotor.class, "motorBR");
        

        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        mFL.setDirection(DcMotor.Direction.REVERSE);
        mFR.setDirection(DcMotor.Direction.FORWARD);

       mBL.setDirection(DcMotor.Direction.FORWARD);


        mBR.setDirection(DcMotor.Direction.FORWARD);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            
            //Getting Controller Input

            double forwardPower = gamepad1.left_stick_y;
            double strafePower = gamepad1.left_stick_x;
            double turnPower = gamepad1.right_stick_x;
            
            //Setting Motor Powers
            mFL.setPower(forwardPower - turnPower - strafePower);
            mFR.setPower(forwardPower + turnPower + strafePower); 
            mBL.setPower(forwardPower - turnPower + strafePower);
            mBR.setPower(forwardPower + turnPower - strafePower);
            
            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();
        }
    }
}
