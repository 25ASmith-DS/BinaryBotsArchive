package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.SwitchableLight;

@Autonomous(name="PowerPlay Autonomous", group="Linear Opmode")

public class FCPowerPlayAuto extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private Servo intakeServo = null;
    private DcMotor slideMotor = null;
    private DcMotor frMotor = null;
    private DcMotor flMotor = null;
    private DcMotor brMotor = null;
    private DcMotor blMotor = null;
    private NormalizedColorSensor colorSensor = null;
    private DistanceSensor leftSensor = null;
    private DistanceSensor rightSensor = null;
    
    private double servoTarget = 0;
    private final float COUNTS_PER_REV = 537.6f;
    private final float WHEEL_DIAMETER_CM = 9.5f;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        intakeServo  = hardwareMap.get(Servo.class, "intake_servo");
        slideMotor = hardwareMap.get(DcMotor.class, "slide_motor");
        
        frMotor = hardwareMap.get(DcMotor.class, "fr_motor");
        flMotor = hardwareMap.get(DcMotor.class, "fl_motor");
        brMotor = hardwareMap.get(DcMotor.class, "br_motor");
        blMotor = hardwareMap.get(DcMotor.class, "bl_motor");

        colorSensor = hardwareMap.get(NormalizedColorSensor.class, "color_sensor");
        leftSensor = hardwareMap.get(DistanceSensor.class, "left_dsensor");
        rightSensor = hardwareMap.get(DistanceSensor.class, "right_dsensor");

        intakeServo.setDirection(Servo.Direction.FORWARD);
        slideMotor.setDirection(DcMotor.Direction.FORWARD);
        //retractMotor.setDirection(DcMotor.Direction.FORWARD);
        
        frMotor.setDirection(DcMotor.Direction.REVERSE);
        brMotor.setDirection(DcMotor.Direction.REVERSE);
        
        frMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        brMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        flMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        blMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        slideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        
        colorSensor.setGain(1f);
        
        frMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        brMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        flMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        blMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        
        
        waitForStart();
        runtime.reset();
        
        intakeServo.setPosition(0.3);
        sleep(1000l);
        SlideToCM(5);
        RunToCM(-45, -45, -45, -45, 0.4f);
        
        //Find and Store Sleeve Color
        NormalizedRGBA colors = colorSensor.getNormalizedColors();
        String quantizedColor = FindQuantizedColor(colors);
        RunToCM(-20, -20, -20, -20, 0.4f);
        
        if(quantizedColor == "RED"){
            //Strafe Right
            RunToCM(-70, 70, 70, -70, 0.4f);
        }
        if(quantizedColor == "GREEN"){
            //Do Nothing (Already Parked)
        }
        if(quantizedColor == "BLUE"){
            //Strafe Left
            RunToCM(70, -70, -70, 70, 0.4f);
        }
        
        SlideToCM(-3);
        //FOR SLIDE AUTO
        // RunToCM(-150, -150, -150, -150);
        // RunToCM(10, 10, 10, 10);
        // RunToCM(-30, -30, 30, 30);
        

        telemetry.addData("Intake Servo: ", intakeServo.getPosition());
        telemetry.addData("Servo Position: ", servoTarget);
        telemetry.addLine()
          .addData("Red:", "%.3f", colors.red)
          .addData("Green:", "%.3f", colors.green)
          .addData("Blue:", "%.3f", colors.blue);
        telemetry.addData("Color:", quantizedColor);
        telemetry.addData("Encoder Position", frMotor.getCurrentPosition());
        telemetry.update();
        
        //wait(1000l);
    }
    
    private String FindQuantizedColor(NormalizedRGBA colors){
            if (colors.blue > colors.red && colors.blue > colors.green)
                return "BLUE";
            if (colors.red > colors.blue && colors.red > colors.green)
                return "RED";
            if (colors.green > colors.red && colors.green > colors.blue)
                return "GREEN";
            return "NO COLOR";
    }
    
    private void alignBot() {
        double dr = rightSensor.getDistance(DistanceUnit.CM);
        double dl = leftSensor.getDistance(DistanceUnit.CM);
        
        double diff = Math.abs(dr - dl);
        
        if (dr > 200.0 || dl > 200.0) {
            RunToCM(-1, -1, -1, -1, 0.5f);
        }
        
        if (dr > dl){
            RunToCM(-1, -1, 1, 1, 0.2f * (float)diff);
        } else if (dr < dl){
            RunToCM(1, 1, -1, -1, 0.2f * (float)diff);
        }
        return;
    }
    
    private void RunToCM(float fr, float br, float fl, float bl, float motorPower){
        frMotor.setTargetPosition(frMotor.getCurrentPosition() + (int)(-fr * (COUNTS_PER_REV / (Math.PI * WHEEL_DIAMETER_CM))));
        brMotor.setTargetPosition(brMotor.getCurrentPosition() + (int)(-br * (COUNTS_PER_REV / (Math.PI * WHEEL_DIAMETER_CM))));
        flMotor.setTargetPosition(flMotor.getCurrentPosition() + (int)(-fl * (COUNTS_PER_REV / (Math.PI * WHEEL_DIAMETER_CM))));
        blMotor.setTargetPosition(blMotor.getCurrentPosition() + (int)(-bl * (COUNTS_PER_REV / (Math.PI * WHEEL_DIAMETER_CM))));
        
        frMotor.setPower(motorPower);
        brMotor.setPower(motorPower);
        flMotor.setPower(motorPower);
        blMotor.setPower(motorPower);
        
        frMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        brMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        flMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        blMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        
        while (frMotor.getCurrentPosition() != frMotor.getTargetPosition() &&
                brMotor.getCurrentPosition() != frMotor.getTargetPosition() &&
                flMotor.getCurrentPosition() != frMotor.getTargetPosition() &&
                blMotor.getCurrentPosition() != frMotor.getTargetPosition()){
            //Wait until you reach the target
        }
    }
    
    private void SlideToCM(float h){
        slideMotor.setTargetPosition(frMotor.getCurrentPosition() + (int)(-h * (COUNTS_PER_REV / (Math.PI * WHEEL_DIAMETER_CM))));
        slideMotor.setPower(0.6f);
        slideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
}
