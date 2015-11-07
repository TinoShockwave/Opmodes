package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;

/**
 * Created by Kashyap Panda on 9/28/15.
*/
public class AutonomousTest extends Autonomous {
    Autonomous robot;

    @Override
    public void init() {
        robot = new Autonomous();
    }

    @Override
    public void loop() {
        robot.moveMotor(robot.frontMotorLeft, 200, 0.5);
    }
}

