package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by Kashyap Panda on 9/28/15.
 */

import com.qualcomm.robotcore.hardware.DcMotor;

public class AutonomousTest extends PushBotTelemetry {

    /**
     * This class member remembers which state is currently active.  When the
     * start method is called, the state will be initialized (0).  When the loop
     * starts, the state will change from initialize to state_1.  When state_1
     * actions are complete, the state will change to state_2.  This implements
     * a state machine for the loop method.
     */
    private int drive_state = 0;

    DcMotor axleFront;
    DcMotor axleBack;

    // this resets the everything  in the robot to 0.
    public void start() {
        super.start();
        reset_drive_encoders();

        axleFront = hardwareMap.dcMotor.get("axle_1");
        axleBack = hardwareMap.dcMotor.get("axle_2");
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
                run_using_encoders();
                if (have_drive_encoders_reached(1000, 1000)) {
                    reset_drive_encoders();
                    set_drive_power(0.0f, 0.0f);
                    drive_state++;
                }
                break;

            case 2:
                if (have_drive_encoders_reset ())
                {
                    drive_state++;
                }
                break;

            case 3:
                run_using_encoders();
                if (have_drive_encoders_reached(0, -1000)) {
                    reset_drive_encoders();
                    set_drive_power(0.0f, 0.0f);
                    drive_state++;
                }

            case 4:
                if (have_drive_encoders_reset()) {
                    drive_state++;
                }
                break;

            case 5:
                run_using_encoders();
                axleFront.setPower(20);
                axleBack.setPower(20);
                if (have_drive_encoders_reached(1000, 1000)) {
                    reset_drive_encoders();
                    set_drive_power(0.0f, 0.0f);
                    axleFront.setPower(0);
                    axleBack.setPower(0);
                    drive_state++;
                }
                break;

            case 6:
                if(have_drive_encoders_reset()) {
                    drive_state++;
                }
                break;



            default:
                break;
        }
    }
}

