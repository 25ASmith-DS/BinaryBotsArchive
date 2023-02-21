package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp

public class BasicAutoDrive extends LinearOpMode {

    // todo: write your code here
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor slide = null;
    private DcMotor frontRight = null;
    private DcMotor frontLeft = null;
    private DcMotor backRight = null;
    private DcMotor backLeft = null;
    private Servo coneClaw = null;

    @Override
    public void runOpMode() {

        slide = hardwareMap.get(DcMotor.class, "slide");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");


        slide.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {

            double drivePower = gamepad1.left_stick_y;
            double turnPower = gamepad1.right_stick_x;
            double strafePower = gamepad1.left_stick_x;
            double slideSpeed = 0;

            float moveSlideUp = gamepad1.right_trigger;
            float moveSlideDown = gamepad1.left_trigger;
            
            if (moveSlideUp > 0) {
                slideSpeed = moveSlideUp;
            }
            if (moveSlideDown > 0) {
                slideSpeed = -moveSlideDown;
            }

            if (moveSlideUp > 0 && moveSlideDown > 0) {
                slideSpeed = 0;
            }

            if (moveSlideUp == 0 && moveSlideDown == 0) {
                slideSpeed = 0;
            }

            if (gamepad1.right_bumper) {

            }

            if (gamepad1.left_bumper) {

            }

            slide.setPower(slideSpeed);
            frontLeft.setPower((drivePower - turnPower - strafePower)/0.25);
            frontRight.setPower((drivePower + turnPower + strafePower)/0.50);
            backLeft.setPower((drivePower - turnPower + strafePower)/0.50);
            backRight.setPower((drivePower + turnPower - strafePower)/0.50);

            telemetry.addData("Status, Motor Power", "Run Time: " + runtime.toString() + ", Motor Power = " + slide.getPower());
            telemetry.update();
        }
    }
}

