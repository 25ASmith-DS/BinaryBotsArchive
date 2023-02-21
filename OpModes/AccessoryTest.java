package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Accessory Test", group="Linear Opmode")

public class AccessoryTest extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private Servo rightServo = null;
    private Servo leftServo = null;
    private DcMotor extendMotor = null;
    private DcMotor retractMotor = null;
    
    private double servoTarget = 0;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        rightServo  = hardwareMap.get(Servo.class, "right_servo");
        leftServo = hardwareMap.get(Servo.class, "left_servo");
        extendMotor = hardwareMap.get(DcMotor.class, "extend_motor");
        retractMotor = hardwareMap.get(DcMotor.class, "retract_motor");

        rightServo.setDirection(Servo.Direction.FORWARD);
        leftServo.setDirection(Servo.Direction.REVERSE);
        extendMotor.setDirection(DcMotor.Direction.FORWARD);
        retractMotor.setDirection(DcMotor.Direction.FORWARD);
        
        // rightServo.scaleRange(0.0, 1.0);
        // leftServo.scaleRange(0.0, 1.0);
        

        waitForStart();
        runtime.reset();
        while (opModeIsActive()) {
            
            if (gamepad1.dpad_up)
                servoTarget = 0;
            if (gamepad1.dpad_down)
                servoTarget = 1;
            
            double linearSlidePower = gamepad1.right_trigger - gamepad1.left_trigger;
            
            rightServo.setPosition(servoTarget);
            leftServo.setPosition(servoTarget);
            extendMotor.setPower(linearSlidePower);
            retractMotor.setPower(linearSlidePower);
            
            
            // Show the elapsed game time and wheel power.
            telemetry.addData("Right Servo: ", rightServo.getPosition());
            telemetry.addData("Left Servo: ", leftServo.getPosition());
            telemetry.addData("DPAD_UP: ", gamepad1.dpad_up);
            telemetry.addData("DPAD_DOWN: ", gamepad1.dpad_down);
            telemetry.update();
        }
    }
}
