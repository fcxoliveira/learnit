package ufrpe.edu.learnit;

/**
 * Created by Filipe on 19/10/2016.
 */
public class LoginDataBaseAdapter {
    static final String DATABASE_NAME = "member.db";
    static final int DATABASE_VERSION = 1;
    public static final int NAME_COLUMN = 1;
    public static final String DATABASE_CREATE = "create table LOGIN (ID integer primary key autoincrement, USERNAME  text,PASSWORD text, EMAIL text);";

}
