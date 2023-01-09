package org.firstinspires.ftc.teamcode;
 
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
 
@Autonomous
public class ColorTest extends LinearOpMode {
    // Define a variable for our color sensor
    ColorSensor color;
    
    @Override
    public void runOpMode() {
        // Get the color sensor from hardwareMap
        color = hardwareMap.get(ColorSensor.class, "Color");
        
        // Wait for the Play button to be pressed
        waitForStart();
        boolean inStorage = false;
 
        // While the Op Mode is running, update the telemetry values.
        while (opModeIsActive()) {
            if (color.green() > 50 && color.blue() > 50 && color.red() > 50 && (Math.abs(color.green() - color.blue())) < 20 && (Math.abs(color.green() - color.red())) < 20 && (Math.abs(color.blue() - color.red())) < 20) {
                inStorage = !inStorage;
                telemetry.addData("In Storage:", inStorage);
                telemetry.update();
                sleep(3000);
            }

            
            //telemetry.addData("Red:", color.red());
            //telemetry.addData("Green:", color.green());
            //telemetry.addData("Blue:", color.blue());
            
            // telemetry.update();
            
            
        }
    }
}
        