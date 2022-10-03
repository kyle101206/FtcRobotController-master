package Autons;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import teleOp.Robot;


@Autonomous(name = "testAuton", group = "Autons")
public class testAuton extends LinearOpMode {

    public Robot robot = new Robot();
    //private ElapsedTime runTime = new ElapsedTime();

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
