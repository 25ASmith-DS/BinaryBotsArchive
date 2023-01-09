package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import java.lang.annotation.Target;
import java.util.ArrayList;
import com.qualcomm.robotcore.hardware.DcMotor.RunMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.LED;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@Autonomous(name="Fighting Cougars Blue Auto", group="Linear Opmode")

public class FCBlueAuto extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor mFL = null;
    private DcMotor mFR = null;
    private DcMotor mBL = null;
    private DcMotor mBR = null;
    private DcMotor mSpinner = null;
    private Boolean isAutoActive = true;
    // private double clawPosition = 0;
    
    private final double WHEEL_DIAMETER = 9.6;
    private final double TICK_PER_REV = 1120;
    private final double WHEEL_CIRCUM = 8.75 * 3.14;
    
    private ArrayList<DcMotor> motorList = new ArrayList<DcMotor>(); 
    private ArrayList<Integer> targetList = new ArrayList<Integer>();

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize the hardware variables.
        mFL  = hardwareMap.get(DcMotor.class, "motorFL");
        mFR = hardwareMap.get(DcMotor.class, "motorFR");
        mBL = hardwareMap.get(DcMotor.class, "motorBL");
        mBR = hardwareMap.get(DcMotor.class, "motorBR");
        mSpinner = hardwareMap.get(DcMotor.class, "spinner");
        

        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        mFL.setDirection(DcMotor.Direction.FORWARD);
        mFR.setDirection(DcMotor.Direction.REVERSE);
        mBL.setDirection(DcMotor.Direction.FORWARD);
        mBR.setDirection(DcMotor.Direction.FORWARD);
        mSpinner.setDirection(DcMotor.Direction.FORWARD);
        
        mFL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        mFR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        mBL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        mBR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        
        // mFL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // mFR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // mBL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // mBR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // mSpinner.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        
        // mFL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // mFR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // mBL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // mBR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // mSpinner.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        
        // motorList.add(mFL);
        // motorList.add(mFR);
        // motorList.add(mBL);
        // motorList.add(mBR);
        
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            
            if(isAutoActive){
                //FL, FR, BL, BR
                //MoveCM(50, 50, 50, 50, 0.2);
                mFL.setPower(0.2);
                mFR.setPower(0.2);
                mBL.setPower(0.2);
                mBR.setPower(0.2);
                sleep(500);
                StopMotors();
                Spin(0.04);
                isAutoActive = false;
                telemetry.addData("FL Position: ", mFL.getCurrentPosition());
                telemetry.addData("FR Position: ", mFR.getCurrentPosition());
                telemetry.addData("BL Position: ", mBL.getCurrentPosition());
                telemetry.addData("BR Position: ", mBR.getCurrentPosition());
                telemetry.update();
            }
            
            
        }
    }
        
    public void StopMotors(){
        mFL.setPower(0);
        mFR.setPower(0);
        mBL.setPower(0);
        mBR.setPower(0);
    }
    public void Spin(double acceleration){
            double power = 0;
            while(Math.abs(power) < 8){
                power += acceleration;
                telemetry.addData("Power", power);
                mSpinner.setPower(power);
                sleep(8L);
            }
            //sleep(100L);
            mSpinner.setPower(0);
        }
        
    public void MoveCM(int flTarget, int frTarget, int blTarget, int brTarget, double power){
        while(opModeIsActive() && (mFL.getCurrentPosition() <= flTarget || mFR.getCurrentPosition() <= frTarget
        || mBL.getCurrentPosition() <= blTarget || mBR.getCurrentPosition() <= brTarget)){
            mFL.setTargetPosition((int)(flTarget / (WHEEL_CIRCUM / TICK_PER_REV)));
            mFR.setTargetPosition((int)(frTarget / (WHEEL_CIRCUM / TICK_PER_REV)));
            mBL.setTargetPosition((int)(blTarget / (WHEEL_CIRCUM / TICK_PER_REV)));
            mBR.setTargetPosition((int)(brTarget / (WHEEL_CIRCUM / TICK_PER_REV)));
            mFL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            mFR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            mBL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            mBR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            mFL.setPower(power);
            mFR.setPower(power);
            mBL.setPower(power);
            mBR.setPower(power);
        }
        mFL.setPower(0);
        mFR.setPower(0);
        mBL.setPower(0);
        mBR.setPower(0);
        mFL.setMode(DcMotor.RunMode.RESET_ENCODERS);
        mFR.setMode(DcMotor.RunMode.RESET_ENCODERS);
        mBL.setMode(DcMotor.RunMode.RESET_ENCODERS);
        mBR.setMode(DcMotor.RunMode.RESET_ENCODERS);
    }
    
    // public void MoveCM(int flTarget, int frTarget, int blTarget, int brTarget, double power){
    //     targetList.add(flTarget);
    //     targetList.add(frTarget);
    //     targetList.add(blTarget);
    //     targetList.add(brTarget);
        
    //     for(int i = 0; i < 4; i++){
    //         while(motorList.get(i).getCurrentPosition() != targetList.get(i)){
    //             motorList.get(i).setTargetPosition((int)(targetList.get(i) / (WHEEL_CIRCUM / TICK_PER_REV)));
    //             motorList.get(i).setMode(DcMotor.RunMode.RUN_TO_POSITION);
    //             motorList.get(i).setPower(power);
    //         }
    //         motorList.get(i).setPower(0);
    //     }
        
    // }
    
}
