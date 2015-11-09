package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by Kashyap Panda on 9/28/15.
*/
public class AutonomousExperimental extends Autonomous {
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

