package ln.app;

import java.sql.SQLException;

import org.json.JSONException;

import ln.api.UsersService;

public class MainTest {
	public static void main(String[] args) throws SQLException, JSONException {
		System.out.println(UsersService.get().toString());
		System.out.println(UsersService.get("debug").toString());
	}
}
