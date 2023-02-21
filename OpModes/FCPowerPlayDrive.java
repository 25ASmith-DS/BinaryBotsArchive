package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.SwitchableLight;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="PowerPlay Drive", group="Linear Opmode")

public class FCPowerPlayDrive extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private Servo intakeServo = null;
    private DcMotor slideMotor = null;
    private DcMotor frMotor = null;
    private DcMotor flMotor = null;
    private DcMotor brMotor = null;
    private DcMotor blMotor = null;
    private NormalizedColorSensor colorSensor = null;
    
    private double servoTarget = 0;
    private double driveSensitivity = 1;
    private boolean sensitivityToggle = false;
    // private double slideAccelerationCoefficient = 1.0000001;
    // private double slideAccelerationChange = 1.01;
    private int originalSlidePos = 0;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.addLine("REMINDER");
        telemetry.addLine("Controller 1 = Drive");
        telemetry.addLine("Controller 2 = Lift & Claw");
        telemetry.update();

        intakeServo = hardwareMap.get(Servo.class, "intake_servo");
        slideMotor = hardwareMap.get(DcMotor.class, "slide_motor");
        originalSlidePos = slideMotor.getCurrentPosition();
        
        frMotor = hardwareMap.get(DcMotor.class, "fr_motor");
        flMotor = hardwareMap.get(DcMotor.class, "fl_motor");
        brMotor = hardwareMap.get(DcMotor.class, "br_motor");
        blMotor = hardwareMap.get(DcMotor.class, "bl_motor");

        colorSensor = hardwareMap.get(NormalizedColorSensor.class, "color_sensor");

        intakeServo.setDirection(Servo.Direction.FORWARD);
        slideMotor.setDirection(DcMotor.Direction.REVERSE);
        
        frMotor.setDirection(DcMotor.Direction.REVERSE);
        brMotor.setDirection(DcMotor.Direction.REVERSE);
        
        frMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        brMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        flMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        blMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        slideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        
        frMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        brMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        flMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        blMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        
        
        if (colorSensor instanceof SwitchableLight) {
            ((SwitchableLight)colorSensor).enableLight(false);
        }
        
        waitForStart();
        runtime.reset();
        while (opModeIsActive()) {
            
            double forwardPower = gamepad1.left_stick_y * driveSensitivity;
            double strafePower = -gamepad1.left_stick_x * driveSensitivity;
            double turnPower = -gamepad1.right_stick_x * driveSensitivity;
            frMotor.setPower((forwardPower - turnPower - strafePower));
            flMotor.setPower((forwardPower + turnPower + strafePower)); 
            brMotor.setPower((forwardPower - turnPower + strafePower));
            blMotor.setPower((forwardPower + turnPower - strafePower));
            
            
            //Handle Linear Slide
            float input = -gamepad2.left_stick_y;
            float slidePower = 0f;
            //Power "Zones"
            if (input == 1f){
                slidePower = 1f;
            }
            if (input > 0f && input < 1f){
                slidePower = 0.5f;
            }
            if (input == -1f){
                slidePower = -0.4f;
            }
            if (input < 0f && input > -1f){
                slidePower = -0.2f;
            }
            
            slideMotor.setPower(slidePower);
            
            
            //Functions to Curve Slide Power
            // if (input > 0f && input <= 0.6f){
            //     slidePower = 0.2f * (float)Math.pow((input), 2f) + 0.22f;
            // }
            // if (input > 0.6f){
            //     slidePower = 4f * (float)Math.pow((input - 0.6f), 2f) + 0.3f;
            // }
            // if (input < 0f && input >= -0.6f){
            //     slidePower = -0.2f * (float)Math.pow((input), 2f) - 0.22f;
            // }
            // if (input < -0.6f){
            //     slidePower = -4f * (float)Math.pow((input + 0.6f), 2f) - 0.3f;
            // }
            // slideMotor.setPower(slidePower);
            
            // double linearSlidePower = -gamepad2.left_stick_y;
            // slideMotor.setPower(linearSlidePower);
            
            
            // double linearSlidePower = gamepad2.left_stick_y;
            // if((slideMotor.getCurrentPosition()<(originalSlidePos + 10) && linearSlidePower<0)
            // ||(slideMotor.getCurrentPosition()<(originalSlidePos + 10) && linearSlidePower>0)){
            //     slideMotor.setPower((Math.pow(linearSlidePower, 3)) 
            // * 0.2f + (Math.signum(gamepad2.left_stick_y) * 0.2f));
            // }
            // else{
            //     slideMotor.setPower((Math.pow(linearSlidePower, 3)) 
            // * 0.8f + (Math.signum(gamepad2.left_stick_y) * 0.2f));
            // }
            
            // double linearSlidePower = gamepad2.left_stick_y;
            // if(slideMotor.getCurrentPosition()<50 && linearSlidePower<0){
            //   slideMotor.setPower((Math.pow(linearSlidePower, 3)) 
            //     * 0.2f + (Math.signum(gamepad2.left_stick_y) * 0.2f));
            // }
            // else{
            //     slideMotor.setPower((Math.pow(linearSlidePower, 3)) 
            //     * 0.8f + (Math.signum(gamepad2.left_stick_y) * 0.2f));
            // }
            
            //Handle Intake Claw
            if (gamepad2.y)
                servoTarget = 0;
            if (gamepad2.a)
                servoTarget = 0.3f;
            intakeServo.setPosition(servoTarget);
            
            //Toggle Drive Sensitivity
            if (gamepad1.a && !sensitivityToggle){
                sensitivityToggle = true;
            }
            //Detect Button Up
            if (!gamepad1.a && sensitivityToggle){
                if (driveSensitivity == 1){
                    driveSensitivity = 0.6;
                }
                else {
                    driveSensitivity = 1;
                }
                
                sensitivityToggle = false;
            }
            
            telemetry.addData("Intake Servo: ", intakeServo.getPosition());
            telemetry.addData("Servo Position: ", servoTarget);
            telemetry.addData("Slide Position: ", slideMotor.getCurrentPosition());
            telemetry.addData("Original Slide Position: ", originalSlidePos);
            telemetry.addData("Slide Power: ", slidePower);
            telemetry.addData("Y-Input: ", input);
            telemetry.update();
        }
    }
}
