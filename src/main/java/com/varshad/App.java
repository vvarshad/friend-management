package com.varshad;

import org.jooby.Jooby;

/**
 * @author jooby generator
 */
public class App extends Jooby {

  {
     /* Config conf = require(Config.class);
      String uri = conf.getString("db_url");
      String user = conf.getString("db_user");
      String password = conf.getString("db_password");

      use(new Jackson());
      use(new Jdbc());
      use(new Jdbi());*/

      /*onStart(() -> {
              Config conf = require(Config.class);
              String uri = conf.getString("db_url");
              String user = conf.getString("db_user");
              String password = conf.getString("db_password");
        System.out.println(uri + user + password);
              Driver driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
              try (Connection con = DriverManager.getConnection("jdbc:neo4j:"+uri, user, password)){
                  // Querying
                  String query = "MATCH (u:User)-[:FRIEND]-(f:User) WHERE u.name = {1} RETURN f.name, f.age";
                  try (PreparedStatement stmt = con.prepareStatement(query)) {
                      stmt.setString(1,"John");

                      try (ResultSet rs = stmt.executeQuery()) {
                          while (rs.next()) {
                              System.out.println("Friend: "+rs.getString("f.name")+" is "+rs.getInt("f.age"));
                          }
                      }
                  }
              }
            });*/

    get("/", () -> "Hello World!");
  }

  public static void main(final String[] args) {
    run(App::new, args);
  }

}
