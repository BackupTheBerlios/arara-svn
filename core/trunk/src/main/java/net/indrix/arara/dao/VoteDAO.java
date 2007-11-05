package net.indrix.arara.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.indrix.arara.vo.Photo;
import net.indrix.arara.vo.User;
import net.indrix.arara.vo.Vote;

import org.apache.log4j.Logger;

public class VoteDAO extends AbstractDAO{
    /**
     * Logger object
     */
    static Logger logger = Logger.getLogger("net.indrix.aves");

    private static final String ID_COLUMN = "id";

    private static final String USER_ID_COLUMN = "user_id";

    private static final String DATE_COLUMN = "date";

    private static final String VOTE_COLUMN = "vote";

    /**
     * This sql statement inserts a new vote into database
     */
    private static final String INSERT = "INSERT INTO user_votes_photo (user_id, photo_id, specie_id, vote, date) values (?, ?, ?, ?, ?)";

    /**
     * This sql statement updates a vote into database
     */
    private static final String UPDATE = "UPDATE user_votes_photo set vote = ?, date = ? where id = ?";
    
    /**
     * This sql statement selects all votes for a given photo
     */
    private static final String SELECT_VOTES = "SELECT * FROM user_votes_photo where photo_id = ?";

    /**
     * This sql statement selects all users that had voted an specific photo.
     */
    private static final String SELECT_USERS_FOR_PHOTO = "select distinct u.id, u.login, u.name, u.email, u.language from user_votes_photo v, user u "
            + " where u.emailOnNewComment = 1 and v.user_id = u.id and photo_id = ? and user_id != ?";

    /**
     * This sql statement selects a vote for a given photo and user
     */
    private static final String SELECT_VOTE_FOR_PHOTO_AND_USER = "SELECT id FROM user_votes_photo where photo_id = ? and user_id = ?";
    
    private static final String SELECT_VOTES_FOR_SPECIE = 
        "SELECT photo_id, round(avg(vote)) average, count(vote) numberOfVotes, specie_id " +
        "FROM user_votes_photo " +
        "GROUP by photo_id " +
        "ORDER by average desc";

    private Photo photo = null;

    /**
     * This method inserts a vote to a photo
     * 
     * @param vote The vote object.
     * 
     * @throws DatabaseDownException
     *             If the database is down
     * @throws SQLException
     *             If some SQL Exception occurs
     */
    public void insertVote(Vote vote) throws DatabaseDownException,
            SQLException {
        if (!voteExists(vote)){
            super.insertObject(vote, INSERT);
        } else {
            super.updateObject(vote, UPDATE);
        }
    }

    /**
     * This method retrieves a <code>List</code> object with
     * <code>Vote</code> objects, based on the id of the photo
     * 
     * @param id The id of the <code>List</code>
     * 
     * @return a <code>List</code> object with <code>Vote</code> objects, 
     * based on the id of the photo
     * 
     * @throws DatabaseDownException If the database is down
     * @throws SQLException If some SQL Exception occurs
     */
    public List retrieveVotes(Photo photo) throws DatabaseDownException,
            SQLException {
        this.photo = photo;
        List list = super.retrieveObjects(photo.getId(), SELECT_VOTES);
        return list;
    }

    /**
     * This method retrieves all users that had voted an specific photo. The
     * user given by userId is not included in the list
     * 
     * @param photoId The id of the photo with votes
     * 
     * @return a list with users objects
     * 
     * @throws DatabaseDownException If the database is down
     */
    public List<User> retrieveUsersWithVotesForPhoto(int photoId, int userId)
            throws DatabaseDownException {
        logger.debug("Entering method...");
        List <User>list = new ArrayList<User>();
        Connection conn = DatabaseManager.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            logger.debug("Running SQL " + SELECT_USERS_FOR_PHOTO);
            logger.debug("Running SQL with " + photoId + "," + userId);
            stmt = conn.prepareStatement(SELECT_USERS_FOR_PHOTO);
            stmt.setInt(1, photoId);
            stmt.setInt(2, userId);

            rs = stmt.executeQuery();

            logger.debug("Adding emails to list...");
            while (rs.next()) {
                User u = (User) createUserObject(rs);
                list.add(u);
                logger.debug("Adding user: " + u);
            }
        } catch (SQLException e) {
            logger.debug("SQLException !", e);
        } finally {
            closeResultSet(rs);
            closeStatement(stmt);
            try {
                conn.close();
            } catch (SQLException e1) {
                throw new DatabaseDownException();
            }
        }
        logger.debug("Finishing method...");
        return list;
    }

    /**
     * This method creates a <code>User</code> object with the data from
     * database
     * 
     * @param rs
     *            The <code>ResultSet<code> object to retrieve the data
     * 
     * @return A new <code>User</code> object 
     * 
     * @throws SQLException If an error occur while retrieving data from result set
     */
    protected Object createObject(ResultSet rs) throws SQLException {
        Vote v = new Vote();
        v.setId(rs.getInt(ID_COLUMN));
        v.setVote(rs.getInt(VOTE_COLUMN));
        v.setDate(getDate(rs, DATE_COLUMN));
        v.setPhoto(photo);
        UserDAO userDao = new UserDAO();
        User user;
        try {
            logger.debug("Retrieving user for vote with id = " + v.getId());
            user = userDao.retrieve(rs.getInt(USER_ID_COLUMN));
            v.setUser(user);
        } catch (DatabaseDownException e) {
            logger.fatal("DatabaseDownException", e);
        }
        return v;
    }

    /**
     * This method creates a <code>User</code> object with the data from
     * database
     * 
     * @param rs The <code>ResultSet<code> object to retrieve the data
     * 
     * @return A new <code>User</code> object 
     * 
     * @throws SQLException If an error occur while retrieving data from result set
     */
    protected Object createUserObject(ResultSet rs) throws SQLException {
        User user;
        user = new User();
        user.setId(rs.getInt(ID_COLUMN));
        user.setName(rs.getString(UserDAO.NAME_COLUMN));
        user.setLogin(rs.getString(UserDAO.LOGIN_COLUMN));
        user.setEmail(rs.getString(UserDAO.EMAIL_COLUMN));
        user.setLanguage(rs.getString(UserDAO.LANGUAGE_COLUMN));
        return user;
    }

    /**
     * This method sets the id into the object
     * 
     * @param id The id value
     * @param object The object to set the id
     */
    protected void setObjectId(int id, Object object) throws SQLException {
        Vote vote= (Vote) object;
        vote.setId(id);
    }

    /**
     * This method set the values into statement, before running the SQL in
     * insert method
     * 
     * @param stmt The statement to insert the values to sql
     * @param object The object to retrieve the values from
     */
    protected void setStatementValues(PreparedStatement stmt, Object object)
            throws SQLException {
        Vote vote = (Vote) object;
        stmt.setInt(1, vote.getUser().getId());
        stmt.setInt(2, vote.getPhoto().getId());
        stmt.setInt(3, vote.getPhoto().getSpecie().getId());
        stmt.setInt(4, vote.getVote());
        stmt.setTimestamp(5, getTimestamp(vote.getDate()));

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.indrix.dao.AbstractDAO#setStatementValuesForUpdate(java.sql.PreparedStatement,
     *      java.lang.Object)
     */
    protected void setStatementValuesForUpdate(PreparedStatement stmt,
            Object object) throws SQLException {
        Vote vote = (Vote) object;
        stmt.setInt(1, vote.getVote());
        stmt.setTimestamp(2, getTimestamp(vote.getDate()));
        stmt.setInt(3, vote.getId());
    }

    private boolean voteExists(Vote vote) throws DatabaseDownException, SQLException {
        boolean voteExists = false;
        
        logger.debug("Entering method...");
        Connection conn = DatabaseManager.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.prepareStatement(SELECT_VOTE_FOR_PHOTO_AND_USER);
            stmt.setInt(1, vote.getPhoto().getId());
            stmt.setInt(2, vote.getUser().getId());

            rs = stmt.executeQuery();

            
            if (rs.next()) {
                voteExists = true;
                vote.setId(rs.getInt(ID_COLUMN));
            }
        } finally {
            closeResultSet(rs);
            closeStatement(stmt);
            try {
                conn.close();
            } catch (SQLException e1) {
                throw new DatabaseDownException();
            }
        }
        logger.debug("Finishing method...");
        return voteExists;
    }


}
