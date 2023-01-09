// package org.firstinspires.ftc.teamcode;

// import com.qualcomm.robotcore.util.ElapsedTime;


// public class FlyC {

//     // relevant objects
//     public FlyPID controller;
//     public ElapsedTime timer;

//     // constants
//     public final double minDeltaTime = 0.05; //PID minimum time to update (in seconds)
//     public final double Kp = 0.4; // PID proportional weight
//     public final double Ki = 0.1; // PID integral weight
//     public final double Kd = 0;   // PID derivative weight

//     // runtime variables
//     // Bad programming practices! I know! I shouldn't make everything public! I'm doing it anyway!
//     public double setpoint = 0;
//     public double error = 0;
//     public double deltaTime = 0;
//     public double lastTime = 0;
//     public double currAngularVelocity = 0;
//     public double output = 0;
//     public double setIntegral = 6.5;

//     // boolean flags
//     public boolean reachedSetpoint = false;
//     public boolean wasReset = false;


//     public FlyC() {
//         controller = new FlyPID(Kp, Ki, Kd);
//         timer = new ElapsedTime(0); //measures in seconds
//     }

//     public void setSetpoint(double targetAngularVelocity) {
//         setpoint = targetAngularVelocity;
//     }

//     public double getSetpoint() {
//         return setpoint;
//     }

//     public void reset() {
//         reachedSetpoint = false;
//         controller.resetIntegral();
//         resetDeltaTime();
//         wasReset = true;
//     }

//     public double getDeltaTime() {
//         return timer.time() - lastTime;
//     }

//     public void resetDeltaTime() {
//         lastTime = timer.time();
//     }

//     public boolean ready() {
//         //we use a short delay, because the algorithm used to calculate
//         // angular velocity isn't very smart.
//         return getDeltaTime() >= minDeltaTime;
//     }

//     public double doPID(double deltaPosition) {
//         deltaTime = getDeltaTime();
//         resetDeltaTime();

//         if (deltaTime < 0) {
//             // this situation would cause problems if used in calculations.
//             // it won't happen often, so losing some resolution there isn't an issue.
//             return Math.max(output, 0);
//         }

//         // if we were reset, this ratio is not reliable.
//         currAngularVelocity = deltaPosition/deltaTime;
//         if (wasReset) {
//             // in that event, we return an arbitrary value.
//             wasReset = false;
//             // 0 is relatively safe.
//             return 0;
//         }

//         // if currAngularVelocity is slow, error is positive. If it's high, error is negative.
//         error = setpoint - currAngularVelocity;

//         if (reachedSetpoint) {
//             output = controller.doPID(error, deltaTime);
//         } else {
//             if (error < 0.1) {
//                 //this is not the ideal way to write this!
//                 controller.integral = setIntegral;
//                 reachedSetpoint = true;
//             }
//             output = 1;
//         }

//         // Absolute value because the flywheel should only have a positive power applied.
//         return Math.max(output, 0);

//     }

// }
