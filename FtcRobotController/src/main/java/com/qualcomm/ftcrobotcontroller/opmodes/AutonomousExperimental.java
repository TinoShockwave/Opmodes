package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by Kashyap Panda on 9/28/15.
*/
public class AutonomousExperimental extends Autonomous {
    Autonomous robot;

    @Override
    public void init() {
        frontMotorLeft = hardwareMap.dcMotor.get("motor_1");
        frontMotorRight = hardwareMap.dcMotor.get("motor_2");
        backMotorLeft = hardwareMap.dcMotor.get("motor_3");
        backMotorRight = hardwareMap.dcMotor.get("motor_4");
        axleMotorFront = hardwareMap.dcMotor.get("motor_5");
        axleMotorBack = hardwareMap.dcMotor.get("motor_6");

        robot = new Autonomous();
    }

    //Distance is in INCHES
    @Override
    public void loop() {
        robot.moveRobot(24, 0.5, "forward");
    }
}

