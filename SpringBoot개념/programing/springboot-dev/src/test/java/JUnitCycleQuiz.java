import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JUnitCycleQuiz {

    @BeforeEach
    public void beforeEach() {
        System.out.println("hello");
    }

    @AfterAll
    public static void afterAll() {
        System.out.println("Bye");
    }

    @Test
    public void junitTest3(){
        System.out.println("this is first test");
    }

    @Test
    public void junitTest4(){
        System.out.println("this is second test");
    }


}
