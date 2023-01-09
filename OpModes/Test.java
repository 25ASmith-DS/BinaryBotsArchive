
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Motor Test", group="Linear Opmode")

public class Test extends LinearOpMode{
    
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor Motor = null;
    
    @Override
    public void runOpMode() {
        
        Motor = hardwareMap.get(DcMotor.class, "TestMotor");
        
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        
        Motor.setDirection(DcMotor.Direction.FORWARD);

        waitForStart();
        runtime.reset();
        
        while (opModeIsActive()) {
            telemetry.addData("Status", "Running");
            
            double ForwardPower = gamepad1.left_stick_y;
            Motor.setPower(ForwardPower);

        }
    }
}
