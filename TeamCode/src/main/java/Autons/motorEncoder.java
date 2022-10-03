package Autons;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import teleOp.Robot;


@Autonomous(name = "EncoderAuton", group = "Autons")
public class motorEncoder extends LinearOpMode {

    public Robot robot = new Robot();
    public ElapsedTime runTime = new ElapsedTime();

  /*  public void smartSleep (double secondsToSleep){
        runTime.reset(); //restart set the timer to 0
        while(opModeIsActive() && (runTime.seconds() < secondsToSleep)) {//may need to add opModeIsActive() in teleOP

            //sleep while the following conditions are true
            telemetry.addData("Path","Leg: %2.5f S Elapsed", runTime.seconds());
            telemetry.update();
        }
    }

   */

static final int MOTOR_TICK_COUNTS = 1909;//need to figure out the tick count of the wheel
    @Override
    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap);

        waitForStart();
       robot.startDrive(.5, 2,telemetry);

        robot.smartSleep(1);

       robot.stopDrive();
        robot.smartSleep(1);


        this.stop();//dont need this. , perhapos
        telemetry.addData("path","complete");
        telemetry.update();
    }
}
