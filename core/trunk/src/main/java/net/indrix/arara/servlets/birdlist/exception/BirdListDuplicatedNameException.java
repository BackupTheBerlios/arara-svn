package net.indrix.arara.servlets.birdlist.exception;

import java.sql.SQLException;

@SuppressWarnings("serial")
public class BirdListDuplicatedNameException extends SQLException {

    public BirdListDuplicatedNameException() {
        super();
    }

    public BirdListDuplicatedNameException(String arg0) {
        super(arg0);
    }

}
