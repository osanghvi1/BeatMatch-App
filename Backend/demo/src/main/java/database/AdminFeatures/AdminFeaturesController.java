package database.AdminFeatures;

import database.GenrePreferences.GenrePreferencesController;
import database.User.User;
import database.User.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminFeaturesController {

    @Autowired
    private UserController userController;

    @Autowired
    private GenrePreferencesController genrePreferencesController;


    //ban
    @PostMapping(path = "/admin/ban/{uid}")
    public String banUser(@PathVariable int uid) {
        //set their status so they can't log in
        User user = new User();
        user.setAccountStatus(-1);
        String result1 = userController.editUser(uid, user);
        //delete their genre preferences
        String result2 = genrePreferencesController.deleteGenres(uid);

        return "Ban: " + result1 + " Delete GPF: " + result2;
    }






}
