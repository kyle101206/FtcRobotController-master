package org.firstinspires.ftc.robotcontroller.teleOp;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Robot {

    public DcMotor frontRight = null; // setting the dcMotors to private since this is going to be our object
    public DcMotor backRight = null;
    public DcMotor frontLeft = null;
    public DcMotor backLeft = null;
    public DcMotor leftElevator = null;
    public DcMotor rightElevator = null;
    public Servo claw = null;

    ElapsedTime runTime = new ElapsedTime();

    public final static double CLAW_HOME = 0.0;
    public final static double ARM_MIN_RANGE = 0.0;//switch the value depends on the servo position on the robot
    public final  static double ARM_MAX_RANGE = 1.0;//switch the value depends on the servo position on the robot

    public void init(HardwareMap hwMap) { // mapping each motor so the phone can read it
        frontLeft = hwMap.dcMotor.get("front_left");
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);//may need to take out if robot not working or switch to without encoder to work

        frontRight = hwMap.dcMotor.get("front_right");
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        backLeft = hwMap.dcMotor.get("back_left");
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //may need to run without encoder if it doesn't move
       // backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        backRight = hwMap.dcMotor.get("back_right");
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //Reversing one side of the mecanum drive
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE); // setting the motors so the code works and the motors don't spin out
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

        //May need to take out but can leave in for absolute going forward
        frontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeft.setDirection(DcMotorSimple.Direction.FORWARD);

        //Elevator hardwaremapping
        leftElevator = hwMap.dcMotor.get("left_Elevator");
        leftElevator.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        rightElevator = hwMap.dcMotor.get("right_Elevator");
        rightElevator.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        claw = hwMap.servo.get("claw");
        claw.setPosition(CLAW_HOME);//potentially switching to compliant wheels with a touch sensor


        }
        public void mecDrive(double horizontal, double vertical, double rotate){//maybe switching to just void not public
            double denominator = Math.max(Math.abs(vertical) + Math.abs(horizontal) + Math.abs(rotate), 1);

            frontLeft.setPower((vertical + horizontal + rotate) / denominator);
            backLeft.setPower((vertical - horizontal + rotate) / denominator);
            frontRight.setPower((vertical - horizontal - rotate) / denominator);
            backRight.setPower((vertical + horizontal - rotate) / denominator);


        }
        public void elevator(double lPower, double rPower, double motorSpeed) {
            if (lPower == rPower) {//may need to change for later to work
                leftElevator.setPower(motorSpeed);
                rightElevator.setPower(motorSpeed);
            }
        }
        //converts the mili second to be full seconds and make it overall smarter
        public double smartSleep (double secondsToSleep){
                runTime.reset(); //restart set the timer to 0
                while ((runTime.seconds() < secondsToSleep)) {//may need to add opModeIsActive() in teleOP
                    //sleep while the following conditions are true
                    telemetry.addData("Path", "Leg: %2.5f S Elapsed", runTime.seconds());
                    telemetry.update();
                }
            return secondsToSleep;
        }
        //constructors for autonomous
            public void startDrive(double power, double second, Telemetry telemetry) {//may need to put opmode in the constructor
                this.frontRight.setPower(power);//may need to put this.
                this.frontLeft.setPower(power);
                this.backRight.setPower(power);
                this.backLeft.setPower(power);

                runTime.reset();//starts the time at 0
                while(runTime.seconds() < smartSleep(second)) {
                    telemetry.addData("Path", "drive: %2.5f S Elapsed", runTime.seconds());
                    telemetry.update();
                }
            }
            public void stopDrive(){
                this.frontRight.setPower(0);
                this.frontLeft.setPower(0);
                this.backRight.setPower(0);
                this.backLeft.setPower(0);
            }
            public void strafeRight(double power, double second, Telemetry telemetry) {
                this.frontLeft.setPower(power);
                this.backLeft.setPower(-power);
                this.backRight.setPower(power);
                this.frontRight.setPower(-power);

                runTime.reset();//starts the time at 0
                while (runTime.seconds() < smartSleep(second)) {
                    telemetry.addData("Path", "drive: %2.5f S Elapsed", runTime.seconds());
                    telemetry.update();
                }
            }
            public void strafeLeft(double power, double second,Telemetry telemetry) {
                this.frontLeft.setPower(-power);
                this.backLeft.setPower(power);
                this.backRight.setPower(-power);
                this.frontRight.setPower(power);

                runTime.reset();//starts the time at 0
                while (runTime.seconds() < smartSleep(second)) {
                    telemetry.addData("Path", "drive: %2.5f S Elapsed", runTime.seconds());
                    telemetry.update();
                }
            }
            public void diagonaRight(double power, double second,Telemetry telemetry) {
                this.frontLeft.setPower(power);
                this.backRight.setPower(power);

                runTime.reset();//starts the time at 0
                while (runTime.seconds() < smartSleep(second)) {
                    telemetry.addData("Path", "drive: %2.5f S Elapsed", runTime.seconds());
                    telemetry.update();
                }
            }
            public void diagonaLeft(double power, double second,Telemetry telemetry) {
                this.backLeft.setPower(power);
                this.frontRight.setPower(power);

                runTime.reset();//starts the time at 0
                while (runTime.seconds() < smartSleep(second)) {
                    telemetry.addData("Path", "drive: %2.5f S Elapsed", runTime.seconds());
                    telemetry.update();
                }
            }
            public void turnAroundRight(double power, double second, Telemetry telemetry) {
                this.frontLeft.setPower(power);
                this.backLeft.setPower(power);
                this.backRight.setPower(-power);
                this.frontRight.setPower(-power);

                runTime.reset();//starts the time at 0
                while (runTime.seconds() < smartSleep(second)) {
                    telemetry.addData("Path", "drive: %2.5f S Elapsed", runTime.seconds());
                    telemetry.update();
                }
            }
            public void turnAroundLeft(double power, double second, Telemetry telemetry) {
                this.frontLeft.setPower(-power);
                this.backLeft.setPower(-power);
                this.backRight.setPower(power);
                this.frontRight.setPower(power);

                runTime.reset();//starts the time at 0
                while (runTime.seconds() < smartSleep(second)) {
                    telemetry.addData("Path", "drive: %2.5f S Elapsed", runTime.seconds());
                    telemetry.update();
                }
            }
            public void frontConcerning(double power, double second, Telemetry telemetry){
                this.frontLeft.setPower(power);
                this.backLeft.setPower(power);

                runTime.reset();//starts the time at 0
                while (runTime.seconds() < smartSleep(second)) {
                    telemetry.addData("Path", "drive: %2.5f S Elapsed", runTime.seconds());
                    telemetry.update();
                }
            }
            public void frontCounterConcerning(double power, double second, Telemetry telemetry){
                this.backRight.setPower(power);
                this.frontRight.setPower(power);

                runTime.reset();//starts the time at 0
                while (runTime.seconds() < smartSleep(second)) {
                    telemetry.addData("Path", "drive: %2.5f S Elapsed", runTime.seconds());
                    telemetry.update();
                }
            }
            public void backConcerning(double power, double second, Telemetry telemetry){
                this.frontLeft.setPower(-power);
                this.backLeft.setPower(-power);

                runTime.reset();//starts the time at 0
                while (runTime.seconds() < smartSleep(second)) {
                    telemetry.addData("Path", "drive: %2.5f S Elapsed", runTime.seconds());
                    telemetry.update();
                }
            }
            public void backCounterConcerning(double power, double second, Telemetry telemetry){
                this.backRight.setPower(-power);
                this.frontRight.setPower(-power);

                runTime.reset();//starts the time at 0
                while (runTime.seconds() < smartSleep(second)) {
                    telemetry.addData("Path", "drive: %2.5f S Elapsed", runTime.seconds());
                    telemetry.update();
                }
            }
            public void rearAxisLeft(double power, double second, Telemetry telemetry){
                this.frontLeft.setPower(power);
                this.frontRight.setPower(-power);

                runTime.reset();//starts the time at 0
                while (runTime.seconds() < smartSleep(second)) {
                    telemetry.addData("Path", "drive: %2.5f S Elapsed", runTime.seconds());
                    telemetry.update();
                }
            }
            public void counterRearAxisLeft(double power, double second, Telemetry telemetry){
                this.backLeft.setPower(power);
                this.backRight.setPower(-power);

                runTime.reset();//starts the time at 0
                while (runTime.seconds() < smartSleep(second)) {
                    telemetry.addData("Path", "drive: %2.5f S Elapsed", runTime.seconds());
                    telemetry.update();
                }
            }
            public void rearAxisRight(double power, double second, Telemetry telemetry){
                this.frontLeft.setPower(-power);
                this.frontRight.setPower(power);

                runTime.reset();//starts the time at 0
                while (runTime.seconds() < smartSleep(second)) {
                    telemetry.addData("Path", "drive: %2.5f S Elapsed", runTime.seconds());
                    telemetry.update();
                }
            }
            public void counterRearAxisRight(double power, double second, Telemetry telemetry){
                this.backLeft.setPower(-power);
                this.backRight.setPower(power);

                runTime.reset();//starts the time at 0
                while (runTime.seconds() < smartSleep(second)) {
                    telemetry.addData("Path", "drive: %2.5f S Elapsed", runTime.seconds());
                    telemetry.update();
                }
            }
        }
