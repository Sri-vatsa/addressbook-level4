package seedu.address.ui;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";
    private static final String GRAVATAR_URL_FORMAT = "https://www.gravatar.com/avatar/%1$s.jpg";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final ReadOnlyPerson person;

    @FXML
    private HBox cardPane;
    @FXML
    private ImageView gravatar;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;

    public PersonCard(ReadOnlyPerson person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        initPicture(person);
        initTags(person);
        bindListeners(person);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyPerson person) {
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        phone.textProperty().bind(Bindings.convert(person.phoneProperty()));
        address.textProperty().bind(Bindings.convert(person.addressProperty()));
        email.textProperty().bind(Bindings.convert(person.emailProperty()));
        person.tagProperty().addListener((observable, oldValue, newValue) -> {
            tags.getChildren().clear();
            person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        });
    }

    /**
     * Initializes the tags with text values and alternating colours between each index
     * in the list
     */
    private void initTags(ReadOnlyPerson person) {
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            if (Integer.parseInt(id.getText().replace(". " , "")) % 2 == 0) {
                tagLabel.setStyle("-fx-background-color: deepskyblue;"
                        + " -fx-font-size: 15px;"
                        + " -fx-text-fill: black");
            } else {
                tagLabel.setStyle("-fx-background-color: snow;"
                        + " -fx-font-size: 15px;"
                        + " -fx-text-fill: black");
            }
            tags.getChildren().add(tagLabel);
        });
    }

    /**
     * Initializes the profile picture using Gravatar
     */
    private void initPicture(ReadOnlyPerson person) {
        try {
            String email = person.getEmail().value.trim().toLowerCase();
            System.out.println(email);
            byte[] emailBytes = email.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            String hash = DatatypeConverter.printHexBinary(md.digest(emailBytes)).toUpperCase();
            String gravatarUrl = String.format(GRAVATAR_URL_FORMAT, hash.toLowerCase());
            System.out.println(gravatarUrl);
            Image image = new Image(gravatarUrl);
            gravatar.setImage(image);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        PersonCard card = (PersonCard) other;
        return id.getText().equals(card.id.getText())
                && person.equals(card.person);
    }
}
