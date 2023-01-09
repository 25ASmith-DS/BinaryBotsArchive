package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Intake Tester", group="Linear Opmode")

public class IntakeTester extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor intake = null;
    
    private double intakePower = 0;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        

        intake  = hardwareMap.get(DcMotor.class, "intake");

        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // Setup a variable for each drive wheel to save power level for telemetry
            
            if(gamepad1.dpad_up)
                intakePower += 0.001;
            if(gamepad1.dpad_down)
                intakePower -= 0.001;
                
            intake.setPower(intakePower);

            // Show the elapsed game time and wheel power.
            telemetry.addData("Intake Power: ", intakePower);
            telemetry.update();
        }
    }
}
