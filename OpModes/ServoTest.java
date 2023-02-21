package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


public class ServoTest extends LinearOpMode {

    // todo: write your code here
    Servo coneClaw = null;
    
    public void runOpMode() {
        coneClaw = hardwareMap.get(Servo.class, "coneClaw");
        
        waitForStart();
        
        while (opModeIsActive()) {
            if (gamepad1.right_bumper) {
                coneClaw.setPosition(0.00);

            }
            
            if (gamepad1.left_bumper) {
                coneClaw.setPosition(0.30);
            }
        }
        
    }
    
}