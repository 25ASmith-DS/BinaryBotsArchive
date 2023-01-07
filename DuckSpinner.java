import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="DuckSpinner", group="Linear Opmode")
public class DuckSpinner extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor spinner;
    private final double MAXSPEED = 10;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        spinner  = hardwareMap.get(DcMotor.class, "spinner");

        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        spinner.setDirection(DcMotor.Direction.REVERSE);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            //Setup a variable for each drive wheel to save power level for telemetry
            if(gamepad1.a){
                Spin();
            }
            // Show the elapsed game time and wheel power.
            
            
            telemetry.update();
        }
    }
    
    public void Spin(){
            double acceleration = 0.05;
            double power = 0;
            while(power < MAXSPEED){
                power += acceleration;
                if (power > MAXSPEED) {
                    power = MAXSPEED;
                }
                telemetry.addData("Power", power);
                spinner.setPower(power);
                sleep(8L);
            }
            //sleep(100L);



            spinner.setPower(0);
        }
}
