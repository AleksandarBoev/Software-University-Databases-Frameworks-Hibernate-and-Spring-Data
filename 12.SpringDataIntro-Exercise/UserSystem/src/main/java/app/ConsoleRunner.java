package app;

import app.models.*;
import app.repositories.AlbumRepository;
import app.services.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@SpringBootApplication
public class ConsoleRunner implements CommandLineRunner {
    private UserService userService;
    private PictureService pictureService;
    private AlbumService albumService;
    private TownService townService;
    private CountryService countryService;

    @Autowired
    public ConsoleRunner(UserService userService, PictureService pictureService, AlbumService albumService,
                         TownService townService, CountryService countryService) {
        this.userService = userService;
        this.pictureService = pictureService;
        this.albumService = albumService;
        this.townService = townService;
        this.countryService = countryService;
    }

    @Override
    public void run(String... args) throws Exception {
//        this.seedData();

        this.removeInactiveUsers("10 Jul 2019");
    }

    private void printUsernamesWithEmailProvider(String emailProvider) {
        this.userService.getUsersByEmailProvider(emailProvider)
                .forEach(u -> {
                    System.out.printf("%s %s%n", u.getUsername(), u.getEmail());
                });
    }

    private void removeInactiveUsers(String dateAfterWhichAUserIsConsideredInactive) {
        String format = "dd MMM yyyy";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        LocalDate localDate = LocalDate.parse(dateAfterWhichAUserIsConsideredInactive, formatter);

        String timeStampDate = String.format("%d-%d-%d 00:00:00",
                localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth());

        this.userService.markUsersForDeletionBeforeDate(Timestamp.valueOf(timeStampDate));

        System.out.println(this.userService.deleteUsersMarkedForDeletion() + " users have been deleted.");
    }

    private void seedData() {
        Country country1 = new Country("Bulgaria");
        Country country2 = new Country("Germany");

        this.countryService.register(country1);
        this.countryService.register(country2);

        Town town1 = new Town("Sofia", country1);
        Town town2 = new Town("Plovdiv", country1);
        Town town3 = new Town("Munchen", country2);
        Town town4 = new Town("Karlsruhe", country2);

        this.townService.register(town1);
        this.townService.register(town2);
        this.townService.register(town3);
        this.townService.register(town4);

        Picture picture1 = new Picture("title1", "caption1", "blabla\\banana.jpg");
        Picture picture2 = new Picture("title2", "caption2", "blabla\\banana.jpg");
        Picture picture3 = new Picture("title3", "caption3", "blabla\\banana.jpg");
        Picture picture4 = new Picture("title4", "caption4", "blabla\\banana.jpg");

        this.pictureService.register(picture1);
        this.pictureService.register(picture2);
        this.pictureService.register(picture3);
        this.pictureService.register(picture4);

        Timestamp dateTime1 = Timestamp.valueOf("2018-09-27 11:00:00");
        Timestamp dateTime2 = Timestamp.valueOf("2018-09-28 11:00:00");
        Timestamp dateTime3 = Timestamp.valueOf("2018-09-29 11:00:00");
        Timestamp dateTime4 = Timestamp.valueOf("2018-09-30 11:00:00");
        User user1 = new User("Destroyer112", "Aleksandar", "Toshkov", "****", "destr@abv.bg", picture1, dateTime1, dateTime2, 23, false, town1, town2);
        User user2 = new User("WeirdName23", "Pesho", "Peshkov", "****", "no@abv.bg", picture2, dateTime2, dateTime3, 44, false, town2, town1);
        User user3 = new User("Somethingblabla", "Richard", "Small", "****", "hwat@gmail.com", picture3, dateTime1, dateTime4, 12, false, town3, town4);

        this.userService.register(user1);
        this.userService.register(user2);
        this.userService.register(user3);

        Album album1 = new Album("Group 1 pictures", "blu", true, user1);
        Album album2 = new Album("Group 2 pictures", "green", true, user1);
        Album album3 = new Album("Group 3 pictures", "red", true, user2);
        Album album4 = new Album("Group 4 pictures", "white", true, user2);
        Album album5 = new Album("Group 5 pictures", "black", true, user3);
        Album album6 = new Album("Group 6 pictures", "purple", true, user3);

        this.albumService.register(album1);
        this.albumService.register(album2);
        this.albumService.register(album3);
        this.albumService.register(album4);
        this.albumService.register(album5);
        this.albumService.register(album6);

        album1.addPicture(picture1);
        album1.addPicture(picture2);
        album2.addPicture(picture1);
        album2.addPicture(picture3);
        album3.addPicture(picture1);
        album3.addPicture(picture4);
        album4.addPicture(picture2);
        album4.addPicture(picture3);
        album5.addPicture(picture2);
        album5.addPicture(picture4);
        album6.addPicture(picture3);
        album6.addPicture(picture4);

        this.albumService.update(album1);
        this.albumService.update(album2);
        this.albumService.update(album3);
        this.albumService.update(album4);
        this.albumService.update(album5);
        this.albumService.update(album6);

        user1.addFriend(user2);
        user2.addFriend(user3);

        this.userService.update(user1);
        this.userService.update(user2);
        this.userService.update(user3);
    }
}
