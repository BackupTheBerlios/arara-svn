package net.indrix.arara.servlets.birdlist;

import java.sql.SQLException;

import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.model.BirdListModel;
import net.indrix.arara.vo.BirdList;

@SuppressWarnings("serial")
public class EditBirdListServlet extends CreateBirdListServlet {
    @Override

    protected void doAction(BirdList birdList) throws DatabaseDownException, SQLException {
        BirdListModel model = new BirdListModel();
        model.update(birdList);
    }

    protected String getSuccessMessage() {
        return "birdlist.update.success";
    }

}
