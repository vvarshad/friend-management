package com.varshad;

import io.restassured.http.ContentType;
import org.jooby.test.JoobyRule;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

/**
 * @author jooby generator
 */
public class AppTest {
    @Rule
    public JoobyRule app = new JoobyRule(new com.varshad.App());

    /**
     * One app/server for all the test of this class. If you want to start/stop a new server per test,
     * remove the static modifier and replace the {@link ClassRule} annotation with {@link Rule}.
     */


    @Test
    public void integrationTest() {
        given().body("{\"friends\":[\"aaa@aaa.com\",\"\"]}").contentType(ContentType.JSON)
                .post("/api/v1/users/connect")
                .then()
                .assertThat()
                .body("success", equalTo(false))
                .body("message", equalTo("User mail is not in valid format"))
                .statusCode(400)
                .contentType("application/json;charset=UTF-8");
    }
}
