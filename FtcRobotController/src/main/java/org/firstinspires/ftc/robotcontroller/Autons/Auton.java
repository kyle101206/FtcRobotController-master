package org.firstinspires.ftc.robotcontroller.Autons;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcontroller.teleOp.Robot;
@Disabled
@Autonomous(name = "FinalAutons", group = "Autons")
public class Auton extends LinearOpMode {

    public Robot robot = new Robot();

    @Override
    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap);

        waitForStart();
        robot.frontRight.setPower(.5);//may need to put this.
        robot.frontLeft.setPower(.5);
        robot.backRight.setPower(.5);
        robot.backLeft.setPower(.5);

        this.sleep(2000);

        robot.frontRight.setPower(0);//may need to put this.
        robot.frontLeft.setPower(0);
        robot.backRight.setPower(0);
        robot.backLeft.setPower(0);
        this.sleep(2000);


        this.stop();//dont need this. , perhapos
        telemetry.addData("path","complete");
        telemetry.update();

    }
}
