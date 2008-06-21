package net.indrix.arara.servlets.photo;

import javax.servlet.http.HttpServletRequest;

import net.indrix.arara.bean.UploadBean;
import net.indrix.arara.bean.UploadPhotoBean;
import net.indrix.arara.model.PhotoModel;
import net.indrix.arara.servlets.common.MediaFilterServlet;
import net.indrix.arara.utils.LabelValueBean;
import net.indrix.arara.vo.User;

@SuppressWarnings("serial")
public class PhotoFilterServlet extends MediaFilterServlet {
    protected void doSpecificTreatment(UploadBean uploadBean,
            HttpServletRequest req, User user) {
        UploadPhotoBean bean = (UploadPhotoBean) uploadBean;

        String id = bean.getSelectedSpecieId();

        if (id == null || id.trim().length() == 0) {
            id = bean.getSelectedFamilyId();
            if (id != null && id.trim().length() > 0){
                LabelValueBean specieBean = (LabelValueBean)bean.getSpecieList().get(0);
                id = specieBean.getValue();
            }
        }
        // check how many photos for this id the user has
        PhotoModel model = new PhotoModel();
        int specieId = Integer.parseInt(id);
        int userId = user.getId();
        int numberOfPhotos = model.retrieveNumberOfPhotosForUserOfSpecieId(specieId, userId);
        req.setAttribute("numberOfPhotosFromUser", numberOfPhotos);
        

    }

}
