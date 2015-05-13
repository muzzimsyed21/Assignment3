package ir.assignments.four.database;

public class Snippet {
	/*
		private static final String DATABASE = "jdbc:mysql:///movies";
		private static final String USERNAME = "";
		private static final String PASSWORD = "";

		protected static boolean isConnected;
		protected static Connection connection;
		
		private static final String selectMovieQuery = "SELECT * FROM MOVIES.MOVIES WHERE "
				+ "TITLE=? AND YEAR=?;";

		private static final String insertMovieQuery = "INSERT INTO MOVIES.MOVIES"
				+ "(TITLE, YEAR, STREAMING_URL) VALUES (?,?,?);";
		
		public static int addMovies(HashSet<Movie> set) {
			connect();

			PreparedStatement insertPreparedStatement = null;
			PreparedStatement selectPreparedStatement = null;
			ResultSet rs = null;

			int result = 0;

			try {
				insertPreparedStatement = connection.prepareStatement(insertMovieQuery);
				selectPreparedStatement = connection.prepareStatement(selectMovieQuery);
				for (Movie m : set) {
					selectPreparedStatement.setString(1, m.getTitle());
					selectPreparedStatement.setInt(2, m.getYear());
					rs = selectPreparedStatement.executeQuery();
					if (!rs.next()) {
						insertPreparedStatement.setString(1, m.getTitle());
						insertPreparedStatement.setInt(2, m.getYear());
						insertPreparedStatement.setString(3, m.getStreamingUrlCompressed());
						insertPreparedStatement.addBatch();
					}
					rs.close();
				}
				int[] batch = insertPreparedStatement.executeBatch();
				result = batch.length;
				insertPreparedStatement.close();
				selectPreparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return result;
		}
	*/
}
