import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "TestSlide", group="Linear OpMode")
public class TestSlide extends LinearOpMode {
    private DcMotor slide;

    public static double ticksPerRevolution = 537.6;
    public static double numberOfRevolutions = 100.0;

    private static double shortStickLength = 13.375; //Inches
    private static double mediumStickLength = 23.25; //Inches
    private static double longStickLength = 33.25; //Inches

    private static double wheelRadius = 1.413 / 2.0; //Inch

    private static double inchPerTick = wheelRadius * 2 * Math.PI / ticksPerRevolution;

    private static double maximumTicksForLongStick = longStickLength / inchPerTick;
    private static double maximumTicksForMediumStick = mediumStickLength / inchPerTick;
    private static double maximumTicksForShortStick = shortStickLength / inchPerTick;


    @Override
    public void runOpMode() {
        slide = hardwareMap.get(DcMotor.class, "slide");

        //slide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        slide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();

        while (opModeIsActive()) {
            //double slidePower = gamepad1.left_stick_y;
            boolean gamepad1bPressed = false;
            
            if (gamepad1.b) {
                gamepad1bPressed = true;
            } else {
                gamepad1bPressed = false;
            }

            if (gamepad1bPressed) {
                slide.setTargetPosition(100);
                slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                slide.setPower(0.4);
            }
            
            sleep(500);
            
            slide.setPower(0);
            
            if ((-slide.getTargetPosition()) > 1000) {
                slide.setPower(0);
                slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            }
            
            idle();
          
            // Arm Lift Telemetry
            if(slide.isBusy()){
                telemetry.addData("Current Position Is: ", slide.getCurrentPosition());
                telemetry.addData("B is pressed: ", ""+gamepad1bPressed);
                telemetry.update();
             }
          
        }

    }

}
