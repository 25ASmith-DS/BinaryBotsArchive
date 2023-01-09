package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Servo Tester", group="Linear Opmode")

public class ServoTester extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private Servo leftServo = null;
    private Servo rightServo = null;
    
    private double leftServoTarget = 0.2f;
    private double rightServoTarget = 0.8f;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        leftServo  = hardwareMap.get(Servo.class, "left_servo");
        rightServo  = hardwareMap.get(Servo.class, "right_servo");
        
        leftServo.setDirection(Servo.Direction.FORWARD);
        rightServo.setDirection(Servo.Direction.FORWARD);

        waitForStart();
        runtime.reset();
        while (opModeIsActive()) {
            
            //Handle Claw Logic
            if (gamepad1.y)
            {
                leftServoTarget = 0.8f;
                rightServoTarget = 0.2f;
            }
            if (gamepad1.a)
            {
                leftServoTarget = 0.2f;
                rightServoTarget = 0.8f;
            }
                
            leftServo.setPosition(leftServoTarget);
            rightServo.setPosition(rightServoTarget);
            
            
            telemetry.addData("Left Servo: ", leftServo.getPosition());
            telemetry.addData("Right Servo: ", rightServo.getPosition());
            telemetry.addData("Left Servo Position: ", leftServoTarget);
            telemetry.addData("Right Servo Position: ", rightServoTarget);
            telemetry.update();
        }
    }
}
