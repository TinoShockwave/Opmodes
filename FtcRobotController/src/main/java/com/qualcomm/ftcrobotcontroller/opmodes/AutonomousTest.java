package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.hardware.DcMotorController;

/**
 * Created by Kashyap on 1/6/16.
 */
public class AutonomousTest extends Autonomous{
    Autonomous robot;

    @Override
    public void runOpMode() throws InterruptedException {
        super.runOpMode();

        frontMotorLeft = hardwareMap.dcMotor.get("motor_1");
        frontMotorRight = hardwareMap.dcMotor.get("motor_2");
        backMotorLeft = hardwareMap.dcMotor.get("motor_3");
        backMotorRight = hardwareMap.dcMotor.get("motor_4");
        axleMotorFront = hardwareMap.dcMotor.get("motor_5");
        axleMotorBack = hardwareMap.dcMotor.get("motor_6");

        robot = new Autonomous();

        frontMotorLeft.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        frontMotorRight.setMode(DcMotorController.RunMode.RESET_ENCODERS);


        gyro.calibrate();
        while (gyro.isCalibrating()) {
            Thread.sleep(500);
        }

        waitForStart();

        robot.moveRobot(72, 0.5, "forward");
    }
}
