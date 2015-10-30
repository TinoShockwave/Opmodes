package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by Kashyap Panda on 9/28/15.
 */

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

public class AutonomousTest extends PushBotTelemetry {

    /**
     * This class member remembers which state is currently active.  When the
     * start method is called, the state will be initialized (0).  When the loop
     * starts, the state will change from initialize to state_1.  When state_1
     * actions are complete, the state will change to state_2.  This implements
     * a state machine for the loop method.
     */
    private int drive_state = 0;

    DcMotor frontMotorLeft;
    DcMotor frontMotorRight;
    DcMotor backMotorLeft;
    DcMotor backMotorRight;
    DcMotor axleMotorFront;
    DcMotor axleMotorBack;

    // this resets the everything  in the robot to 0.
    public void start() {
        super.start();
        reset_drive_encoders();

        frontMotorLeft = hardwareMap.dcMotor.get("motor_1");
        frontMotorRight = hardwareMap.dcMotor.get("motor_2");
        backMotorLeft = hardwareMap.dcMotor.get("motor_3");
        backMotorRight = hardwareMap.dcMotor.get("motor_4");
        axleMotorFront = hardwareMap.dcMotor.get("motor_5");
        axleMotorBack = hardwareMap.dcMotor.get("motor_6");
    }

    /*
      For now, we set the encoder values to random numbers. Later on, we will measure out the
      actual encoder values.

      Case 0: Resets driver controller when swtich to drive_state
      Case 1: Move forward
      Case 2: Makes sure encoders have reset
      Case 3: Turn right
      Case 4: Makes sure encoders have reset
      Case 5: Move forward (go up the ramp)
      Case 6: Makes sure encoders have reset

     */
    public void loop() {
        switch (drive_state) {
            case 0:
                reset_drive_encoders();
                drive_state++;
                break;

            case 1:
                frontMotorLeft.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
                frontMotorRight.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
                backMotorLeft.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
                backMotorLeft.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
                axleMotorFront.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
                axleMotorBack.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);

                break;

            case 2:


                break;

            case 3:

                break;

            case 4:

                break;

            case 5:

                break;

            case 6:

                break;

            default:
                break;
        }
    }
}

