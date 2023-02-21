package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import com.qualcomm.robotcore.hardware.IMU;
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

@Autonomous(name="Distance Sensor Test", group="Linear Opmode")

public class DistanceSensorTest extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private Servo intakeServo = null;
    private DcMotor slideMotor = null;
    private DcMotor frMotor = null;
    private DcMotor flMotor = null;
    private DcMotor brMotor = null;
    private DcMotor blMotor = null;
    private DistanceSensor leftSensor = null;
    private DistanceSensor rightSensor = null;
    private NormalizedColorSensor colorSensor = null;
    private IMU imuSensor = null;
    
    private double servoTarget = 0;
    private final int COUNTS_PER_REV = 1120;
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

        imuSensor = hardwareMap.get(IMU.class, "imu");
        leftSensor = hardwareMap.get(DistanceSensor.class, "left_dsensor");
        rightSensor = hardwareMap.get(DistanceSensor.class, "right_dsensor");

        colorSensor = hardwareMap.get(NormalizedColorSensor.class, "color_sensor");

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
        
        
        
        double margin = 1;
        String telemetryMessage = "Robot is aligned (margin = " + margin + ")";
        YawPitchRollAngles angles;
        
        waitForStart();
        while (opModeIsActive()) {
            alignBot();
            //angles = imuSensor.getRobotYawPitchRollAngles();
            
            telemetry.addData("R Distance Sensor", rightSensor.getDistance(DistanceUnit.CM));
            telemetry.addData("L Distance Sensor", leftSensor.getDistance(DistanceUnit.CM));
            //telemetry.addData(telemetryMessage, botIsAligned(margin));
           
            //telemetry.addData("IMU yaw", angles.getYaw(AngleUnit.DEGREES));
            
            telemetry.update();
        }
        runtime.reset();
        
        
    }
    
    private void alignBot() {
        double dr = rightSensor.getDistance(DistanceUnit.CM);
        double dl = leftSensor.getDistance(DistanceUnit.CM);
        
        double diff = Math.abs(dr - dl);
        
        // if (dr > 200.0 || dl > 200.0) {
        //     RunToCM(-1, -1, -1, -1, 0.5f);
        // }
        
        if (dr > dl){
            RunToCM(-1, -1, 1, 1, 0.2f * (float)diff);
        } else if (dr < dl){
            RunToCM(1, 1, -1, -1, 0.2f * (float)diff);
        }
        return;
    }
    
    private boolean botIsAligned(double margin) {
        double error = Math.abs(rightSensor.getDistance(DistanceUnit.CM) - leftSensor.getDistance(DistanceUnit.CM));
        return error < margin;
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
        slideMotor.setPower(0.5f);
        slideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
}
