package teleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "finalTeleOp" , group = "teleOp")
public class teleOp extends OpMode {
    public Robot robot = new Robot();

    @Override
    public void init() {
        robot.init(hardwareMap);

        telemetry.addLine("Initialized, Press A + Start for controller 1");
        telemetry.addLine("Initialized, Press B + Start for controller 2");

    }

    @Override
    public void loop() {
        double vertical = -gamepad1.left_stick_y; // Remember, this is reversed!
        double horizontal = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
        double rotate = gamepad1.right_stick_x;
        //double denominator = Math.max(Math.abs(vertical) + Math.abs(horizontal) + Math.abs(rotate), 1);
        boolean ground = gamepad1.b;
        boolean low = gamepad1.a;
        boolean medium = gamepad1.x;
        boolean high = gamepad1.y;

        double leftPosition = robot.leftElevator.getCurrentPosition();
        double rightPosition = robot.rightElevator.getCurrentPosition();

        double extrude = gamepad1.left_trigger;
        double intrude = gamepad1.right_trigger;

        boolean intake = gamepad1.left_bumper;
        boolean untake = gamepad1.right_bumper;

        double clawPosition = robot.CLAW_HOME;
        final double ARM_SPEED = 0.01;

        robot.mecDrive(vertical, horizontal, rotate);//Mecanum drive


        if(ground){
            robot.elevator(leftPosition,rightPosition,0);

        }

        else if(low){
            robot.elevator(leftPosition,rightPosition,.25);

        }
        else if(medium){
            robot.elevator(leftPosition,rightPosition,.50);

        }
        else if(high){
            robot.elevator(leftPosition,rightPosition,.75);

        }
        //Manually moves the elevator up and down
        if(extrude > .1){
            robot.leftElevator.setPower(.5 * extrude);//add a limit to not go over 100

        }else {
            robot.leftElevator.setPower(0);//May change the double to an boolean since we dont want to motor to go too fast or take pout the extrude variable

        }
        if(intrude > .1){
            robot.leftElevator.setPower(.5 * intrude);

        }else {
            robot.leftElevator.setPower(0);

        }
        //claw
        if(intake){
            clawPosition += ARM_SPEED;

        }else if(untake){
            clawPosition -= ARM_SPEED;
        }
        robot.claw.setPosition(clawPosition);//sets the position of the claw servo

        //telemetry for elevator position
        telemetry.addData("Power_front_left", robot.leftElevator.getCurrentPosition());
        telemetry.addData("Power_front_right", robot.rightElevator.getCurrentPosition());

        //telemetry for mecanum drive
        telemetry.addData("Horizontal", horizontal);
        telemetry.addData("Vertical", vertical);
        telemetry.addData("Rotate", rotate);

        //telemetry for claw position
        telemetry.addData("claw", clawPosition);

        telemetry.update();

    }
}
