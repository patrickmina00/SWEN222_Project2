package test;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import test.controller_tests.DropTest;
import test.controller_tests.PickUpTest;
import test.controller_tests.UpdateTest;
import test.datastorage_tests.ReadTest;
import test.orientation_tests.TurnLeftTest;
import test.orientation_tests.TurnRightTest;
import test.parser_tests.GenerateGameStateTest;
import test.parser_tests.GenerateGameTest;
import test.room_tests.RoomConstraintsShouldMoveTest;
import test.room_tests.RoomConstraintsShouldNotMoveTest;

public class AllTests {

	public static void main(String[] args) {
		
		System.out.println("RUNNING ALL TESTS");
		
		System.out.println("--------------------");
		
		JUnitCore junit = new JUnitCore();
		Result result = junit.run(DropTest.class, PickUpTest.class,  UpdateTest.class, ReadTest.class, TurnLeftTest.class, TurnRightTest.class, GenerateGameStateTest.class, GenerateGameTest.class, RoomConstraintsShouldMoveTest.class, RoomConstraintsShouldNotMoveTest.class);
		
		
		System.out.println("RUN COUNT: " + result.getRunCount());
		System.out.println("FAILURE COUNT: " + result.getFailureCount());
		if(result.getFailureCount() > 0){
			
			for(Failure fail : result.getFailures()){
				System.out.println(fail.toString());
			}
			
		}

	}

}
