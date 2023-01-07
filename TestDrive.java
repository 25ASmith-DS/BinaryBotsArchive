
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Basic: Linear OpMode", group="Linear Opmode")

public class TestDrive extends LinearOpMode {

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

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        mFL  = hardwareMap.get(DcMotor.class, "motorFL");
        mFR = hardwareMap.get(DcMotor.class, "motorFR");
        mBL = hardwareMap.get(DcMotor.class, "motorBL");
        mBR = hardwareMap.get(DcMotor.class, "motorBR");

        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        
        mFL.setDirection(DcMotor.Direction.FORWARD);
        mFR.setDirection(DcMotor.Direction.FORWARD);
        mBL.setDirection(DcMotor.Direction.FORWARD);
        mBR.setDirection(DcMotor.Direction.REVERSE);
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            double FLpower = gamepad1.a == true ? 1 : 0;
            double FRpower = gamepad1.b == true ? 1 : 0;
            double BLpower = gamepad1.y == true ? 1 : 0;
            double BRpower = gamepad1.x == true ? 1 : 0;
            
            mFL.setPower(FLpower);
            mFR.setPower(FRpower);
            mBL.setPower(BLpower);
            mBR.setPower(BRpower);

            // Show the elapsed game time and wheel power.
            telemetry.update();
        }
    }
}
